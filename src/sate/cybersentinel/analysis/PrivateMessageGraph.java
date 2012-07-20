package sate.cybersentinel.analysis;

import java.util.List;

import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphEdge;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphVertex;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.user.User;
import sate.cybersentinel.message.user.UserManager;

public class PrivateMessageGraph {
	private List<Message> messages;
	private InteractionGraph graph;
	
	public PrivateMessageGraph(List<Message> messages) {
		this.messages = messages;
		this.graph = new InteractionGraph(InteractionGraphEdge.class);
		
		buildGraph();
	}
	
	public InteractionGraph getInteractionGraph() {
		return this.graph;
	}
	
	private void buildGraph() {
		for(User user : UserManager.getAllUsers()) {
			this.graph.addVertex(new InteractionGraphVertex(user));
		}
		
		for(Message m : messages) {
			User from = m.getUser();
			User to = UserManager.getUser(m.getReceiverUUID());
			
			InteractionGraphVertex vFrom = graph.getUserVertex(from);
			InteractionGraphVertex vTo = graph.getUserVertex(to);
			
			InteractionGraphEdge existing = graph.getEdge(vFrom, vTo);
			if(existing != null) {
				existing.incrementWeight(1.0);
			}
			else {
				InteractionGraphEdge edge = graph.addEdge(vFrom, vTo);
				edge.setWeight(1.0);
			}
		}
	}
}
