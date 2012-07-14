/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;

import org.jgrapht.graph.ClassBasedVertexFactory;
import sate.cybersentinel.message.User;

/**
 *
 * @author Isaac
 */
public class InteractionGraphVertex  extends ClassBasedVertexFactory implements Comparable{
    private User user;

    public InteractionGraphVertex(User user) {
        super(InteractionGraphVertex.class);
        this.user = user;
    }

    public int compareTo(Object o) {
//        System.out.println("o   :"+o.getClass());
//        System.out.println("this:"+this.getClass());
        InteractionGraphVertex oo = (InteractionGraphVertex)o;
        Object u = (User) oo.user;
        return user.compareTo(u);
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.toString();
    }

}
