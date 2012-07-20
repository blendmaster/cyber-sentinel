package sate.cybersentinel.analysis.technique;

import java.util.List;

import sate.cybersentinel.analysis.PrivateMessageGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.message.Message;

public class PrivateMessageAnalysisTechnique implements AnalysisTechnique {
	@Override
	public InteractionGraph analyze(List<Message> messages) {
		PrivateMessageGraph g = new PrivateMessageGraph(messages);
		return g.getInteractionGraph();
	}
}
