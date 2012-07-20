/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph.JGraphT;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleWeightedGraph;
import sate.cybersentinel.message.user.User;

/**
 *
 * @author Isaac
 */
public class HybridInteractionGraph 
        extends SimpleWeightedGraph<HybridInteractionGraphVertex, HybridChatGraphEdge>
        implements Serializable {

    private Map<User, HybridInteractionGraphVertex> vertexMap;
    
    public HybridInteractionGraph(Class type) {
        super(type);
        mapVertex();
    }

    public HybridInteractionGraph(EdgeFactory ef) {
        super(ef);
        mapVertex();
    }

    @Override
    public boolean addVertex(HybridInteractionGraphVertex v) {
        if(containsVertex(v)) {
            HybridInteractionGraphVertex get = vertexMap.get(v.getUser());
            get.addAllSource(v.getSourceList());
            return false;
        }
        boolean addVertex = super.addVertex(v);
        if(addVertex)
            vertexMap.put(v.getUser(), v);
        return addVertex;
    }

    @Override
    public boolean removeVertex(HybridInteractionGraphVertex v) {
        HybridInteractionGraphVertex get = vertexMap.get(v.getUser());
        if(get==null) {
            return false;
        }
        boolean removeVertex = super.removeVertex(get);
        if(removeVertex) {
            vertexMap.remove(get.getUser());
        }
        return removeVertex;
    }

    private void mapVertex() {
        vertexMap = new LinkedHashMap<User, HybridInteractionGraphVertex>();
        for(HybridInteractionGraphVertex v: this.vertexSet()) {
            vertexMap.put(v.getUser(), v);
        }
    }

//    public HybridInteractionGraphVertex getVertex(HybridInteractionGraphVertex v) {
//        for( HybridInteractionGraphVertex v1: this.vertexSet()) {
//
//            if(v1.compareTo(v)==1) {
////                System.out.println("v1"+v1);
////                System.out.println("v"+v);
//                return v1;
//            }
//        }
//        return null;
//    }

    @Override
    public boolean containsVertex(HybridInteractionGraphVertex v) {
        HybridInteractionGraphVertex get = vertexMap.get(v.getUser());
         if(get!=null) {
            return true;
        }
        return false;
    }


    @Override
    public HybridChatGraphEdge addEdge(HybridInteractionGraphVertex v, HybridInteractionGraphVertex v1) {
        HybridInteractionGraphVertex vertex = vertexMap.get(v.getUser());
        HybridInteractionGraphVertex vertex1 = vertexMap.get(v1.getUser());
        return super.addEdge(vertex, vertex1);
    }

    @Override
    public HybridChatGraphEdge getEdge(HybridInteractionGraphVertex v, HybridInteractionGraphVertex v1) {
        HybridInteractionGraphVertex vertex = vertexMap.get(v.getUser());
        HybridInteractionGraphVertex vertex1 = vertexMap.get(v1.getUser());
        return super.getEdge(vertex, vertex1);
    }

    

}
