/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph.JGraphT;

import org.jgrapht.graph.ClassBasedVertexFactory;
import sate.cybersentinel.message.user.User;

/**
 * 
 * @author Isaac
 */
public class InteractionGraphVertex extends
		ClassBasedVertexFactory<InteractionGraphVertex> implements
		Comparable<InteractionGraphVertex> {
	private User user;

	public InteractionGraphVertex(User user) {
		super(InteractionGraphVertex.class);
		this.user = user;
	}

	public int compareTo(InteractionGraphVertex v) {
		return user.compareTo(v.user);
	}

	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return user.toString();
	}

}
