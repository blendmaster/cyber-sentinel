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
	
	public InteractionGraphDisplay(InteractionGraph graph) {
		this.setSize(600, 600);
		
		this.graph = graph;
		
		this.gephiGraph = GraphConverter.convert(graph);
		
		buildLayout();
		
		this.pack();
		this.setVisible(true);
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
