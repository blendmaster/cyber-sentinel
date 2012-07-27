/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;

import java.util.List;
import java.util.Set;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.DirectedWeightedSubgraph;
import sate.cybersentinel.analysis.Graph.GraphStats.GraphType;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphVertex;
import sate.cybersentinel.analysis.Graph.VertexStats.VertexGroup;
import sate.cybersentinel.message.user.User;

/**
 *
 * @author Isaac
 */
public class CalculateGraphStatistics extends GraphStaticticsContainer{

    InteractionGraph g;
    boolean directed;
    
    GraphStatsCollection stats;

    
    public CalculateGraphStatistics(InteractionGraph g, boolean directed) {
        
        this.directed = directed;
        this.g = g;
        
        if(g != null)
        {                   
            stats = new GraphStatsCollection(g, directed);
        }
    }

    
    
    public GraphStatsCollection mainGraphStats()
    {
        if(stats == null)
            return stats;
        
        stats.computeAll();
        addGraphStatistics(GraphType.MAIN, -1, stats);

        Set<InteractionGraphVertex> mainGraphVertex = g.vertexSet();
        for(InteractionGraphVertex node:mainGraphVertex) {
            VertexStats vertexStats = stats.getVertexStats(stats, node.getUser());
            vertexStats.toString();
            addNodeStatistics(VertexGroup.MAIN_GRAPH, -1, node.getUser(), vertexStats);
        }
        
        return stats;
    }
    
    public GraphStatsCollection allGraphs()
    {    
        mainGraphStats();
         weakConnectedGraph();
         stronglyConnectedGraph();
         modularityGraph();
         
         return stats;
    }

    
    public GraphStatsCollection weakConnectedGraph()
    {   
        if(stats == null)
            return stats;
        //get weakly connected components
        
        ConnectivityInspector connectedInspector = stats.getWeakConnectInsp();
        List<Set<InteractionGraphVertex>> connectedSets = connectedInspector.connectedSets();
        int weakClusterID = 1;
        
        GraphStatsCollection subGraphStat_Weak;
        for(Set<InteractionGraphVertex> weakConnection:connectedSets) {
            
            if(weakConnection.size()<2)
                continue;
            
            WeightedGraph subGr = new DirectedWeightedSubgraph(g, weakConnection, null);
            
            subGraphStat_Weak = new GraphStatsCollection(subGr, directed);
            subGraphStat_Weak.getGraphModularity();
            subGraphStat_Weak.getClusteringCoefficient();
            subGraphStat_Weak.getGraphDistance();
            subGraphStat_Weak.getEigenvectorCentrality();
            subGraphStat_Weak.getGraphDensity();
            
            addGraphStatistics(GraphType.WEAK_CONNECTED, weakClusterID, subGraphStat_Weak);
            
            Set<InteractionGraphVertex> vertexSet = subGr.vertexSet();
            for(InteractionGraphVertex v:vertexSet)
            {
                VertexStats vertexStats = subGraphStat_Weak.getVertexStats(subGraphStat_Weak.getStatsCollection(), v.getUser());
                addNodeStatistics(VertexGroup.WEAK_CONNECTED, weakClusterID, v.getUser(), vertexStats);
            }
            weakClusterID++;
        }
        
        return stats;
    }
    
    
    public GraphStatsCollection stronglyConnectedGraph()
    {
        if(stats == null)
            return stats;
        
        //get strongly connected components
        StrongConnectivityInspector strongConnectedSets = stats.getStrongConnectInsp();
        List<Set<InteractionGraphVertex>> stronglyConnectedSets = strongConnectedSets.stronglyConnectedSets();
        int strongClusterID = 1;
        
        GraphStatsCollection subGraphStat_Strong;
        for(Set<InteractionGraphVertex> strongConnection:stronglyConnectedSets) {
            
            if(strongConnection.size()<2)
                continue;
            
            WeightedGraph subGr = new DirectedWeightedSubgraph(g, strongConnection, null);
            
            subGraphStat_Strong = new GraphStatsCollection(subGr, directed);
            subGraphStat_Strong.getGraphModularity();
            subGraphStat_Strong.getClusteringCoefficient();
            subGraphStat_Strong.getGraphDistance();
            subGraphStat_Strong.getEigenvectorCentrality();
            subGraphStat_Strong.getGraphDensity();
            
            addGraphStatistics(GraphType.STRONG_CONNECTED, strongClusterID, subGraphStat_Strong);
            
            Set<InteractionGraphVertex> vertexSet = subGr.vertexSet();
            for(InteractionGraphVertex v:vertexSet)
            {
                VertexStats vertexStats = subGraphStat_Strong.getVertexStats(subGraphStat_Strong.getStatsCollection(), v.getUser());
                addNodeStatistics(VertexGroup.STRONG_CONNECTED, strongClusterID, v.getUser(), vertexStats);
            }
            strongClusterID++;
        }
         return stats;
    }
    
    
    public GraphStatsCollection modularityGraph()
    {
        if(stats == null)
            return stats;
        
        //get communities based on Modularity
        List<Set<User>> modularityCommunity = stats.getModularityCommunity();
            int commuID = 1;
            for(Set<User> commu:modularityCommunity) {
                
                if(commu.size()<2)
                    continue;
                
                WeightedGraph subGr = new DirectedWeightedSubgraph(g, commu, null);
                
                GraphStatsCollection subGraphStat_Commu = new GraphStatsCollection(subGr, directed);
                subGraphStat_Commu.getClusteringCoefficient();
                subGraphStat_Commu.getGraphDistance();
                subGraphStat_Commu.getEigenvectorCentrality();
                subGraphStat_Commu.getGraphDensity();
            
                addGraphStatistics(GraphType.COMMUNITY_MODULARITY, commuID, subGraphStat_Commu);
                
                Set<InteractionGraphVertex> vertexSet = subGr.vertexSet();
                for(InteractionGraphVertex v:vertexSet)
                {
                    VertexStats vertexStats = subGraphStat_Commu.getVertexStats(subGraphStat_Commu.getStatsCollection(), v.getUser());
                    addNodeStatistics(VertexGroup.COMMUNITY_MODULARITY, commuID, v.getUser(), vertexStats);
                }
                commuID++;
            }
            return stats;
        }

}
