package sate.cybersentinel.display;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import org.gephi.graph.api.Graph;
import org.gephi.preview.api.*;
import org.gephi.preview.types.DependantOriginalColor;
import org.openide.util.Lookup;
import processing.core.PApplet;
import sate.cybersentinel.analysis.Graph.GraphConverter;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;

public class InteractionGraphDisplay extends JFrame {
	private InteractionGraph graph;
	private Graph gephiGraph;
	
	public InteractionGraphDisplay(InteractionGraph graph) {
		this.setSize(600, 600);
		
		this.graph = graph;
                
		boolean directed = true;
		this.gephiGraph = GraphConverter.convert(graph, directed);
		
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
