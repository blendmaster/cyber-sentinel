package sate.cybersentinel.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.BytesRef;

import sate.cybersentinel.analysis.Graph.JGraphT.ContextGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.ContextGraphVertex;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphEdge;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.user.User;
import sate.cybersentinel.message.user.UserManager;

public class ContextGraphBuilder {
	private static Logger logger = Logger.getLogger(ContextGraphBuilder.class
			.getName());

	private IndexReader reader;

	public ContextGraphBuilder(IndexReader reader) {
		this.reader = reader;
	}

	public ContextGraph build(List<Message> messages) {
		ContextGraph graph = new ContextGraph(InteractionGraphEdge.class);
		Map<Term, Map<User, Integer>> map = new HashMap<>();
		Map<Term, ContextGraphVertex> vmap = new HashMap<>();

		Set<String> stopWords = new HashSet<String>();
		File f = new File("stopwords.txt");
		try(BufferedReader r = new BufferedReader(new InputStreamReader(
				new FileInputStream(f)))) {
			String line;
			while((line = r.readLine()) != null) {
				stopWords.add(line.toLowerCase());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("Generating term to vertexmap map");
		for (Message message : messages) {
			try {
				Terms messageTerms = reader.getTermVector(message.getDocID(),
						"contents");
				if (messageTerms == null)
					continue;

				TermsEnum e = messageTerms.iterator(null);
				BytesRef next;
				while ((next = e.next()) != null) {
					if(stopWords.contains(next.utf8ToString().toLowerCase())) continue;
					Term term = new Term("contents", BytesRef.deepCopyOf(next));

					User user = message.getUser();
					if (map.containsKey(term)) {
						Map<User, Integer> innerMap = map.get(term);
						if (innerMap.containsKey(user)) {
							innerMap.put(user, innerMap.get(user) + 1);
						} else {
							innerMap.put(user, 1);
						}
					} else {
						Map<User, Integer> innerMap = new HashMap<User, Integer>();
						innerMap.put(user, 1);
						map.put(term, innerMap);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		logger.info("Finished generating map, now creating vertices");

		for (Entry<Term, Map<User, Integer>> entry : map.entrySet()) {
			Term term = entry.getKey();
			Map<User, Integer> innerMap = entry.getValue();

			ContextGraphVertex vertex = new ContextGraphVertex(term, innerMap);
			graph.addVertex(vertex);

			vmap.put(term, vertex);
		}

		logger.info("All vertices for context map created successfully");

		// The use of Entry here is a complete hack
		logger.info("Creating weight map");
		Map<Entry<Term, Term>, Double> weightMap = new HashMap<>();
		for (Message message : messages) {
			try {
				Terms messageTerms = reader.getTermVector(message.getDocID(),
						"contents");
				if (messageTerms == null)
					continue;

				TermsEnum e = messageTerms.iterator(null);
				BytesRef next;
				while ((next = e.next()) != null) {
					if(stopWords.contains(next.utf8ToString().toLowerCase())) continue;
					
					Term term1 = new Term("contents", BytesRef.deepCopyOf(next));

					TermsEnum e2 = messageTerms.iterator(null);
					BytesRef next2;

					while ((next2 = e2.next()) != null) {
						if(stopWords.contains(next2.utf8ToString().toLowerCase())) continue;
						Term term2 = new Term("contents",
								BytesRef.deepCopyOf(next2));
						if (!term1.equals(term2)) {
							Entry<Term, Term> terms = new AbstractMap.SimpleEntry<>(
									term1, term2);
							// logger.info(terms.toString());

							if (weightMap.containsKey(terms)) {
								weightMap.put(terms, weightMap.get(terms) + 1);
							} else {
								weightMap.put(terms, 1.0);
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (Entry<Entry<Term, Term>, Double> entry : weightMap.entrySet()) {
			Term t1 = entry.getKey().getKey();
			Term t2 = entry.getKey().getValue();

			// if(t1.toString().equals(t2.toString()))

			ContextGraphVertex v1 = vmap.get(t1);
			ContextGraphVertex v2 = vmap.get(t2);

			InteractionGraphEdge e = graph.addEdge(v1, v2);
			if (e != null) {
				e.setWeight(entry.getValue());
			}
		}

		return graph;
	}
}
