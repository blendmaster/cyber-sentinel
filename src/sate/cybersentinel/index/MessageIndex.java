package sate.cybersentinel.index;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.search.Query;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;

import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.filter.MessageFilter;

public interface MessageIndex {
	public List<Message> query(Query query, int hits)
			throws CorruptIndexException, IOException;

	public List<Message> query(String query, int hits)
			throws CorruptIndexException, IOException, QueryNodeException;
	
	public void addFilter(MessageFilter filter);
}
