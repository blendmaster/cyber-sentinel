package sate.cybersentinel.analysis.Graph;



import java.util.Map;
import java.util.TreeMap;

import org.gephi.graph.api.*;
import org.openide.util.Lookup;

import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphEdge;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphVertex;

public class GraphConverter {
    
	public static Graph convert(InteractionGraph graph, boolean directed) {
            
            if(graph == null)
                return null;
            
            Graph gephiGraph;
		GraphModel model = Lookup.getDefault().lookup(GraphController.class).getModel();
                if(directed) {
                    gephiGraph = (org.gephi.graph.api.Graph)model.getDirectedGraph();
                }
                else {
                    gephiGraph =  model.getUndirectedGraph();
                }                
		
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
