package sate.cybersentinel.analysis.Graph;

import java.util.Map;
import java.util.TreeMap;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.openide.util.Lookup;
import sate.cybersentinel.analysis.Graph.JGraphT.ContextGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.ContextGraphVertex;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphEdge;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphVertex;

public class GraphConverter {

	public static org.gephi.graph.api.Graph convert(org.jgrapht.Graph<InteractionGraphVertex, InteractionGraphEdge> graph, boolean directed) {

		if (graph == null)
			return null;

		org.gephi.graph.api.Graph gephiGraph;
		GraphModel model = Lookup.getDefault().lookup(GraphController.class)
				.getModel();
		if (directed) {
			gephiGraph = (org.gephi.graph.api.Graph) model.getDirectedGraph();
		} else {
			gephiGraph = model.getUndirectedGraph();
		}

		Map<InteractionGraphVertex, org.gephi.graph.api.Node> vertexNodeMap = new TreeMap<>();

		for (InteractionGraphVertex v : graph.vertexSet()) {
			org.gephi.graph.api.Node n = model.factory().newNode(v.getUser().getUUID());
			n.getNodeData().setLabel(v.getUser().getName());
			gephiGraph.addNode(n);

			vertexNodeMap.put(v, n);
		}

		for (InteractionGraphEdge e : graph.edgeSet()) {
			InteractionGraphVertex s = graph.getEdgeSource(e);
			InteractionGraphVertex t = graph.getEdgeTarget(e);
			org.gephi.graph.api.Node s2 = vertexNodeMap.get(s);
			org.gephi.graph.api.Node t2 = vertexNodeMap.get(t);
			
			org.gephi.graph.api.Edge e2 = model.factory().newEdge(s2, t2, (float) e.getWeight(),
					directed);
			gephiGraph.addEdge(e2);
		}

		return gephiGraph;
	}
	
	public static org.gephi.graph.api.Graph convert(ContextGraph graph, boolean directed) {

		if (graph == null)
			return null;

		org.gephi.graph.api.Graph gephiGraph;
		GraphModel model = Lookup.getDefault().lookup(GraphController.class)
				.getModel();
		if (directed) {
			gephiGraph = (org.gephi.graph.api.Graph) model.getDirectedGraph();
		} else {
			gephiGraph = model.getUndirectedGraph();
		}

		Map<ContextGraphVertex, org.gephi.graph.api.Node> vertexNodeMap = new TreeMap<>();

		for (ContextGraphVertex v : graph.vertexSet()) {
			org.gephi.graph.api.Node n = model.factory().newNode();
			n.getNodeData().setLabel(v.getTerm().text());
			gephiGraph.addNode(n);

			vertexNodeMap.put(v, n);
		}

		for (InteractionGraphEdge e : graph.edgeSet()) {
			ContextGraphVertex s = graph.getEdgeSource(e);
			ContextGraphVertex t = graph.getEdgeTarget(e);
			org.gephi.graph.api.Node s2 = vertexNodeMap.get(s);
			org.gephi.graph.api.Node t2 = vertexNodeMap.get(t);

			org.gephi.graph.api.Edge e2 = model.factory().newEdge(s2, t2, (float) e.getWeight(),
					directed);
			gephiGraph.addEdge(e2);
		}

		return gephiGraph;
	}
}
