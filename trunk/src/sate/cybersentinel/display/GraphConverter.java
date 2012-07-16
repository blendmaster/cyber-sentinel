package sate.cybersentinel.display;

import java.util.Map;
import java.util.TreeMap;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;
import org.openide.util.Lookup;

import sate.cybersentinel.analysis.Graph.InteractionGraph;
import sate.cybersentinel.analysis.Graph.InteractionGraphEdge;
import sate.cybersentinel.analysis.Graph.InteractionGraphVertex;

public class GraphConverter {
	public static UndirectedGraph convert(InteractionGraph graph) {
		GraphModel model = Lookup.getDefault().lookup(GraphController.class).getModel();
		UndirectedGraph gephiGraph = model.getUndirectedGraph();
		
		Map<InteractionGraphVertex, Node> vertexNodeMap = new TreeMap<>();
		
		for(InteractionGraphVertex v : graph.vertexSet()) {
			Node n = model.factory().newNode(v.getUser().getUUID());
			n.getNodeData().setLabel(v.getUser().getName());
			gephiGraph.addNode(n);
			
			vertexNodeMap.put(v, n);
		}
		
		for(InteractionGraphEdge e : graph.edgeSet()) {
			InteractionGraphVertex s = graph.getEdgeSource(e);
			InteractionGraphVertex t = graph.getEdgeTarget(e);
			Node s2 = vertexNodeMap.get(s);
			Node t2 = vertexNodeMap.get(t);
			
			Edge e2 = model.factory().newEdge(s2, t2, (float) e.getWeight(), false);
			gephiGraph.addEdge(e2);
		}
		
		return gephiGraph;
	}
}
