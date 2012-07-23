package sate.cybersentinel.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphEdge;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphVertex;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.user.User;
import sate.cybersentinel.message.user.UserManager;

public class DirectMessageGraph {
	private List<Message> messages;
	private InteractionGraph graph;
	
	private IndexReader reader;
	private IndexSearcher searcher;

	/**
	 * Creates a DirectMessageGraph.
	 * @param messages
	 */
	public DirectMessageGraph(List<Message> messages) {
		this.messages = messages;
		
		buildIndex();
		buildGraph();
	}


	public InteractionGraph getInteractionGraph() {
		return this.graph;
	}
	
	private void buildIndex() {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40, analyzer);
		Directory directory = new RAMDirectory();
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Message message : messages) {
			List<Field> fields = new ArrayList<Field>(1);
			fields.add(new TextField("contents", message.getContents(), Store.NO));
			fields.add(new StringField("senderUUID", message.getSenderUUID(), Store.NO));
			try {
				writer.addDocument(fields);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			this.reader = DirectoryReader.open(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.searcher = new IndexSearcher(reader);
	}

	private void buildGraph() {
		this.graph = new InteractionGraph(InteractionGraphEdge.class);

		for (User user : UserManager.getAllUsers()) {
			InteractionGraphVertex vertex = new InteractionGraphVertex(user);
			this.graph.addVertex(vertex);
		}

		for (User from : UserManager.getAllUsers()) {
			for (User to : UserManager.getAllUsers()) {
				if (from == to)
					continue;

				int count = getMessages(from, to);
				if (count != 0) {
					InteractionGraphVertex vFrom = graph.getUserVertex(from);
					InteractionGraphVertex vTo = graph.getUserVertex(to);
					InteractionGraphEdge existingEdge = graph.getEdge(vFrom, vTo);
					if(existingEdge == null) {
						InteractionGraphEdge edge = graph.addEdge(vFrom, vTo);
						edge.setWeight((double) count);
					}
					else {
						existingEdge.incrementWeight((double) count);
					}
				}
			}
		}
	}

	private int getMessages(User from, User to) {
		FuzzyQuery fuzzy = new FuzzyQuery(new Term("contents", to.getName()));
		TermQuery term = new TermQuery(new Term("senderUUID", from.getUUID()));
		BooleanQuery query = new BooleanQuery();
		query.add(fuzzy, Occur.MUST);
		query.add(term, Occur.MUST);
		
		try {
			return searcher.search(query, 10000).scoreDocs.length;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
/*
		int count = 0;
		for (Message message : messages) {
			if (message.getUser().equals(from)) {
				if (message.getContents().contains(to.getName())) {
					count++;
				}
			}
		}
		return count;*/
	}
}
