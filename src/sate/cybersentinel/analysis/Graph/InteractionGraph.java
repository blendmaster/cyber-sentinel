/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;

import java.util.LinkedHashMap;
import java.util.Map;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleWeightedGraph;

import sate.cybersentinel.message.user.User;

/**
 *
 * @author Isaac Osesina
 */
public class InteractionGraph extends SimpleWeightedGraph<InteractionGraphVertex, InteractionGraphEdge>{

    private Map<User, InteractionGraphVertex> vertexMap;
    
    public InteractionGraph(Class type) {
        super(type);
        mapVertex();
    }

    public InteractionGraph(EdgeFactory ef) {
        super(ef);
        mapVertex();
    }

    @Override
    public boolean addVertex(InteractionGraphVertex v) {
        if(containsVertex(v))
            return false;
        vertexMap.put(v.getUser(), v);
        return super.addVertex(v);
    }

    @Override
    public boolean removeVertex(InteractionGraphVertex v) {
        InteractionGraphVertex get = vertexMap.get(v.getUser());
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
        vertexMap = new LinkedHashMap<User, InteractionGraphVertex>();
        for(InteractionGraphVertex v: this.vertexSet()) {
            vertexMap.put(v.getUser(), v);
        }
    }


    @Override
    public boolean containsVertex(InteractionGraphVertex v) {
        InteractionGraphVertex get = vertexMap.get(v.getUser());
         if(get!=null) {
            return true;
        }
        return false;
    }


    @Override
    public InteractionGraphEdge addEdge(InteractionGraphVertex v, InteractionGraphVertex v1) {
        InteractionGraphVertex vertex = vertexMap.get(v.getUser());
        InteractionGraphVertex vertex1 = vertexMap.get(v1.getUser());
        return super.addEdge(vertex, vertex1);
    }

    @Override
    public InteractionGraphEdge getEdge(InteractionGraphVertex v, InteractionGraphVertex v1) {
        InteractionGraphVertex vertex = vertexMap.get(v.getUser());
        InteractionGraphVertex vertex1 = vertexMap.get(v1.getUser());
        return super.getEdge(vertex, vertex1);
    }

    public Map<User, InteractionGraphVertex> getVertexMap() {
        return vertexMap;
    }
    

}
