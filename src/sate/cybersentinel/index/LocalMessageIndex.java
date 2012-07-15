package sate.cybersentinel.index;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.Version;

import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.filter.MessageFilter;

public class LocalMessageIndex implements MessageIndex {
	private IndexReader reader;
	private IndexSearcher searcher;
	private StandardQueryParser parser;
	private DocumentConverter converter;

	/**
	 * Creates a 'MessageIndex' from a Lucene directory. The Lucene directory is
	 * assumed to already have been created and is a MMapDirectory. This is for
	 * compatibility with the assumed Solr server running over the Lucene index.
	 * 
	 * @param location
	 *            The location of the Lucene index managed by the Solr server
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public LocalMessageIndex(File location) throws CorruptIndexException,
			IOException {
		this.reader = DirectoryReader.open(new MMapDirectory(location));
		this.parser = new StandardQueryParser(new StandardAnalyzer(
				Version.LUCENE_40));
		this.searcher = new IndexSearcher(reader);
		this.converter = new DocumentConverter(searcher);
	}

	public List<Message> query(Query query, int hits)
			throws CorruptIndexException, IOException {
		TopScoreDocCollector collector = TopScoreDocCollector
				.create(hits, true);
		try {
			searcher.search(query, collector);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return converter.convert(collector.topDocs().scoreDocs);
	}

	public List<Message> query(String query, int hits)
			throws QueryNodeException, CorruptIndexException, IOException {
		return this.query(parser.parse(query, "contents"), hits);
	}
	
	public void addFilter(MessageFilter filter) {
		converter.addFilter(filter);
	}
}
