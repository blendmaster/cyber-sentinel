package sate.cybersentinel.analysis.technique;

import java.util.List;

import sate.cybersentinel.analysis.ChatResponse;
import sate.cybersentinel.analysis.LogLogisticDistribution;
import sate.cybersentinel.analysis.MessageGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.message.Message;

public class ConversationCycleAnalysisTechnique implements AnalysisTechnique {
	public InteractionGraph analyze(List<Message> messages) {
		ChatResponse response = new ChatResponse(messages);
		MessageGraph messageGraph = new MessageGraph(new LogLogisticDistribution(), response.getInteractions());
		return messageGraph.getInteractionGraph();
	}
}
