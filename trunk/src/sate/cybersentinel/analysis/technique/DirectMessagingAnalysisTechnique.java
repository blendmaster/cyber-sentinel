package sate.cybersentinel.analysis.technique;

import java.util.List;

import sate.cybersentinel.analysis.DirectMessageGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.message.Message;

public class DirectMessagingAnalysisTechnique implements AnalysisTechnique {
	public InteractionGraph analyze(List<Message> messages) {
		return new DirectMessageGraph(messages).getInteractionGraph();
	}
}
