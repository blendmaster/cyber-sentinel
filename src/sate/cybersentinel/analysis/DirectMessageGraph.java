package sate.cybersentinel.analysis;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;

import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphEdge;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphVertex;
import sate.cybersentinel.index.MessageIndex;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.user.User;
import sate.cybersentinel.message.user.UserManager;
import sate.cybersentinel.util.ImpossibleCodeExecutionException;

public class DirectMessageGraph {
	private List<Message> messages;
	private InteractionGraph graph;
	private MessageIndex index;

	/**
	 * Creates a DirectMessageGraph not bound by any index.
	 * @param messages
	 */
	public DirectMessageGraph(List<Message> messages) {
		this(messages, null);
	}

	/**
	 * Creates a DirectMessageGraph bound by an index. Faster.
	 * @param messages
	 * @param index The read-only index to use.
	 */
	public DirectMessageGraph(List<Message> messages, MessageIndex index) {
		this.messages = messages;
		this.index = index;

		buildGraph();
	}

	public InteractionGraph getInteractionGraph() {
		return this.graph;
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
		/*if (index != null) {
			try {
				String q = "senderUUID:\"" + from.getUUID() + "\""
						+ " AND contents:\"" + to.getName() + "\"";
				System.out.println(q);
				return index.query(q, 100000).size();
			} catch (IOException | QueryNodeException e) {
				e.printStackTrace();
				throw new ImpossibleCodeExecutionException();
			}
		} else {*/
			int count = 0;
			for (Message message : messages) {
				if (message.getUser().equals(from)) {
					if (message.getContents().contains(to.getName())) {
						count++;
					}
				}
			}
			return count;
		//}
	}
}
