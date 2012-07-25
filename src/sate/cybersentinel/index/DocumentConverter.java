package sate.cybersentinel.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

import sate.cybersentinel.message.Location;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.MutableMessage;
import sate.cybersentinel.message.filter.MessageFilter;

public class DocumentConverter {
	private IndexSearcher searcher;
	private IndexReader reader;
	private List<MessageFilter> filters;
	
	public DocumentConverter(IndexReader reader, IndexSearcher searcher) {
		this.reader = reader;
		this.searcher = searcher;
		this.filters = new ArrayList<>();
	}
	
	public void addFilter(MessageFilter filter) {
		this.filters.add(filter);
	}
	
	public List<Message> convert(ScoreDoc[] docs) throws CorruptIndexException, IOException {
		List<Message> messages = new ArrayList<Message>();
		
		for(ScoreDoc scoreDoc : docs) {
			MutableMessage message = new MutableMessage();
			
			Document doc = searcher.doc(scoreDoc.doc);
			message.setDocID(scoreDoc.doc);
			
			boolean fRegion = false;
			boolean fX = false;
			boolean fY = false;
			boolean fZ = false;
			
			String region = null;
			double x = 0;
			double y = 0;
			double z = 0;
			
			for(IndexableField field : doc.getFields()) {
				switch(field.name()) {
				case "id":
					break; // Nothing
				case "timestamp":
					break; // Nothing
				case "time":
					Date time = new Date(field.numericValue().intValue());
					message.setTime(time);
					break;
				case "channel":
					message.setChannel(field.numericValue().intValue());
					break;
				case "contents":
					message.setContents(field.stringValue());
					break;
				case "region":
					region = field.stringValue();
					fRegion = true;
					break;
				case "x":
					x = field.numericValue().doubleValue();
					fX = true;
					break;
				case "y":
					y = field.numericValue().doubleValue();
					fY = true;
					break;
				case "z":
					z = field.numericValue().doubleValue();
					fZ = true;
					break;
				case "senderName":
					message.setSenderName(field.stringValue());
					break;
				case "senderUUID":
					message.setSenderUUID(field.stringValue());
					break;
				case "receiverName":
					message.setReceiverName(field.stringValue());
					break;
				case "receiverUUID":
					message.setReceiverUUID(field.stringValue());
					break;
				}
			}
			
			if(fX && fY && fZ && fRegion)
				message.setLocation(new Location(region, x, y, z));
			
			messages.add(message);
		}
		
		for(MessageFilter filter : filters) {
			messages = filter.filter(messages);
		}
		
		return messages;
	}
}
