/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.lucene.index.Term;
import org.jgrapht.graph.ClassBasedVertexFactory;

/**
 *
 * @author Isaac
 */
public class ContextGraphVertex extends ClassBasedVertexFactory implements Comparable{

    private Term term;
            //User, countofWordUsage
    private Map<InteractionGraphVertex, Integer> userVertexSet;
    
    public ContextGraphVertex(Term term) {
        super(ContextGraphVertex.class);
        this.term = term;
        userVertexSet = new LinkedHashMap<InteractionGraphVertex, Integer>();
    }

    public ContextGraphVertex(Term term, Map<InteractionGraphVertex, Integer> userVertexSet) {
        super(ContextGraphVertex.class);
        this.term = term;
        this.userVertexSet = userVertexSet;
    }

    public ContextGraphVertex(Term term, InteractionGraphVertex userVertex) {
        super(ContextGraphVertex.class);
        this.term = term;
        userVertexSet = new LinkedHashMap<InteractionGraphVertex, Integer>();
        putUser(userVertex);
    }

    public void addUser(InteractionGraphVertex userVertex) {
        putUser(userVertex);
    }

    private void putUser(InteractionGraphVertex userVertex) {
        Integer get = userVertexSet.get(userVertex);
        if(get==null) {
            userVertexSet.put(userVertex, 1);
        }
        else {
            get++;
            userVertexSet.put(userVertex, get);
        }
    }

    public int compareTo(Object o) {
        
        if(o instanceof ContextGraphVertex)
        {            
            ContextGraphVertex v = (ContextGraphVertex) o;
            return term.compareTo(v.term);
        }
        return Integer.MIN_VALUE;
    }

    public Term getTerm() {
        return term;
    }

    public Map<InteractionGraphVertex, Integer> getUserVertexMap() {
        return userVertexSet;
    }

    @Override
    public String toString() {
        return term + " <" + userVertexSet + ">";
    }

}
