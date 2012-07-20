package sate.cybersentinel.analysis.technique;

import java.util.List;

import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.message.Message;

public interface AnalysisTechnique {
	public InteractionGraph analyze(List<Message> messages);
}
