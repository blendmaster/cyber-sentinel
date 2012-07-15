package sate.cybersentinel.output.client;

import java.io.IOException;

import sate.cybersentinel.output.protocol.AnalysisData;

public interface AnalysisOutputConnection {
	public AnalysisData receiveData() throws IOException;
}
