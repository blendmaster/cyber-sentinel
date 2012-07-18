/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph.JGraphT;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.lucene.index.Term;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 *
 * @author Isaac
 */
public class ContextGraph extends SimpleWeightedGraph<ContextGraphVertex, InteractionGraphEdge> {

    private Map<Term, ContextGraphVertex> vertexMap;
    
    public ContextGraph(Class<? extends InteractionGraphEdge> type) {
        super(type);
        mapVertex();
    }

    public ContextGraph(EdgeFactory<ContextGraphVertex, InteractionGraphEdge> ef) {
        super(ef);
        mapVertex();
    }

    @Override
     public boolean addVertex(ContextGraphVertex v) {
        if(containsVertex(v))
            return false;
        boolean addVertex = super.addVertex(v);
        vertexMap.put(v.getTerm(), v);
        return addVertex;
    }

    @Override
    public boolean removeVertex(ContextGraphVertex v) {
        ContextGraphVertex get = vertexMap.get(v.getTerm());
        if(get==null) {
            return false;
        }
        boolean removeVertex = super.removeVertex(get);
        if(removeVertex) {
            vertexMap.remove(get.getTerm());
        }
        return removeVertex;
    }

    public ContextGraphVertex getVertex(ContextGraphVertex v) {
        return vertexMap.get(v.getTerm());
//        for( ContextGraphVertex v1: this.vertexSet()) {
//            if(v1.compareTo(v)==1)
//                return v1;
//        }
//        return null;
    }

    private void mapVertex() {
        vertexMap = new LinkedHashMap<Term, ContextGraphVertex>();
        for(ContextGraphVertex v: this.vertexSet()) {
            vertexMap.put(v.getTerm(), v);
        }
    }

    @Override
    public boolean containsVertex(ContextGraphVertex v) {
        ContextGraphVertex get = vertexMap.get(v.getTerm());
         if(get!=null) {
            return true;
        }
        return false;
    }


    @Override
    public InteractionGraphEdge addEdge(ContextGraphVertex v, ContextGraphVertex v1) {
        ContextGraphVertex vertex = vertexMap.get(v.getTerm());
        ContextGraphVertex vertex1 = vertexMap.get(v1.getTerm());
//        if(vertex==null) {
//            vertex = v;
//        }
//        if(vertex1==null) {
//            vertex1 = v1;
//        }
        return super.addEdge(vertex, vertex1);
    }

    @Override
    public InteractionGraphEdge getEdge(ContextGraphVertex v, ContextGraphVertex v1) {
        ContextGraphVertex vertex = vertexMap.get(v.getTerm());
        ContextGraphVertex vertex1 = vertexMap.get(v1.getTerm());
        return super.getEdge(vertex, vertex1);
    }

    public Map<Term, ContextGraphVertex> getVertexMap() {
        return vertexMap;
    }

    

}
