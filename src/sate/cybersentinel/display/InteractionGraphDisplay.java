package sate.cybersentinel.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import sate.cybersentinel.analysis.Graph.InteractionGraph;
import sate.cybersentinel.analysis.Graph.InteractionGraphEdge;
import sate.cybersentinel.analysis.Graph.InteractionGraphVertex;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.api.ProcessingTarget;
import org.gephi.preview.api.RenderTarget;
import org.gephi.preview.types.DependantOriginalColor;
import org.openide.util.Lookup;

import processing.core.PApplet;
import processing.core.PGraphics;

public class InteractionGraphDisplay extends JFrame {
	private InteractionGraph graph;
	private UndirectedGraph gephiGraph;
	private GraphModel model;
	
	public InteractionGraphDisplay(InteractionGraph graph) {
		this.setSize(600, 600);
		
		this.graph = graph;
		
		this.model = Lookup.getDefault().lookup(GraphController.class).getModel();
		
		buildGraph();
		buildLayout();
		
		this.pack();
		this.setVisible(true);
	}
	
	public void buildGraph() {
		this.gephiGraph = model.getUndirectedGraph();
		
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
	}

	public void buildLayout() {
		PreviewController pController = Lookup.getDefault().lookup(PreviewController.class);
		PreviewModel pModel = pController.getModel();
		pModel.getProperties().putValue(PreviewProperty.SHOW_EDGE_LABELS, true);
		pModel.getProperties().putValue(PreviewProperty.NODE_LABEL_COLOR, new DependantOriginalColor(Color.WHITE));
		pModel.getProperties().putValue(PreviewProperty.EDGE_CURVED, true);
		pModel.getProperties().putValue(PreviewProperty.EDGE_OPACITY, 50);
		pModel.getProperties().putValue(PreviewProperty.EDGE_RADIUS, 10.0f);
		pModel.getProperties().putValue(PreviewProperty.BACKGROUND_COLOR, Color.BLACK);
		pController.refreshPreview();
		
		ProcessingTarget target = (ProcessingTarget) pController.getRenderTarget(RenderTarget.PROCESSING_TARGET);
		PApplet applet = target.getApplet();
		applet.init();
		
		pController.render(target);
		target.refresh();
		target.resetZoom();
		
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(applet, BorderLayout.CENTER);
	}
}
