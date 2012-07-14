/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.graph.ClassBasedVertexFactory;
import sate.cybersentinel.message.User;

/**
 *
 * @author Isaac
 */
public class HybridInteractionGraphVertex  extends ClassBasedVertexFactory implements Comparable, Serializable{
    private User user;
    public static enum VertexSource { RESPONSETIME, DIRECTADDRESSING, COMMONWORDUSAGE };
    private Set<VertexSource> sourceList;

    public HybridInteractionGraphVertex(User user, Set<VertexSource>  sourceList) {
        super(HybridInteractionGraphVertex.class);
        this.user = user;
        this.sourceList = sourceList;
    }

    public HybridInteractionGraphVertex(User user, VertexSource  source) {
        super(HybridInteractionGraphVertex.class);
        this.user = user;
        sourceList = new HashSet<VertexSource>();
        sourceList.add(source);
    }

    public void addSource(VertexSource source) {
        sourceList.add(source);
    }

    public void addAllSource(Set<VertexSource>  sourceList) {
        this.sourceList.addAll(sourceList);
    }

    public Set<VertexSource> getSourceList() {
        return sourceList;
    }

    @Override
    public int compareTo(Object o) {
        HybridInteractionGraphVertex oo = (HybridInteractionGraphVertex)o;
        Object u = (User) oo.user;
        return user.compareTo(u);
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.toString() + ":" + sourceList;
    }

}
