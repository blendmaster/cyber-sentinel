/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph.JGraphT;

import java.util.Objects;
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
        
        @Override
        public int hashCode()
        {
            return user.hashCode();
        }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() == obj.getClass()) 
        {
            final InteractionGraphVertex other = (InteractionGraphVertex) obj;
            return Objects.equals(this.user, other.user);
        }
        else if(obj.getClass() == User.class)
        {
            final User other = (User) obj;
            return Objects.equals(this.user, other);
        }
        
        return false;
    }

}
