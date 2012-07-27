/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import sate.cybersentinel.analysis.Graph.GraphStats.GraphType;
import sate.cybersentinel.analysis.Graph.VertexStats.VertexGroup;
import sate.cybersentinel.message.user.User;

/**
 * This is a container for storing statistics about a given graph
 * @author Isaac
 */
public abstract class GraphStaticticsContainer {

    Map<GraphType, TreeMap<Integer, GraphStats>> graphStatistics;
    Map<VertexGroup, TreeMap<Integer, HashMap<User, VertexStats>>> vertexStatistics;
    String toStringHeader_Graph = null;
    String toStringHeader_Vertex = null;

    public GraphStaticticsContainer() {
        graphStatistics = new EnumMap<GraphType, TreeMap<Integer, GraphStats>>(GraphType.class);
        vertexStatistics = new EnumMap<VertexGroup, TreeMap<Integer, HashMap<User, VertexStats>>>(VertexGroup.class);
    }

    public void addGraphStatistics(GraphType type, int clusterID, GraphStats stats)
    {
        if(stats != null) {
            TreeMap<Integer, GraphStats> clusterID_Stats = graphStatistics.get(type);
            if(clusterID_Stats==null) {
                clusterID_Stats = new TreeMap<Integer, GraphStats>();
                clusterID_Stats.put(clusterID, stats);
                graphStatistics.put(type, clusterID_Stats);
            }
            else {
                clusterID_Stats.put(clusterID, stats);
            }
            if(toStringHeader_Graph == null)
                toStringHeader_Graph = stats.toStringHeader();
        }
    }

    public void addNodeStatistics(VertexGroup type, int clusterID, User player, VertexStats stats)
    {
        if(stats != null) {
            TreeMap<Integer, HashMap<User, VertexStats>> clusterID_User_Stats = vertexStatistics.get(type);
            if(clusterID_User_Stats == null)
            {
                clusterID_User_Stats = new TreeMap<Integer, HashMap<User, VertexStats>>();
                HashMap<User, VertexStats> player_Stats = new HashMap<User, VertexStats>();
                player_Stats.put(player, stats);
                clusterID_User_Stats.put(clusterID, player_Stats);
                vertexStatistics.put(type, clusterID_User_Stats);
            }
            else
            {
                HashMap<User, VertexStats> player_Stats = clusterID_User_Stats.get(clusterID);
                if(player_Stats == null)
                {
                    player_Stats = new HashMap<User, VertexStats>();
                    player_Stats.put(player, stats);
                    clusterID_User_Stats.put(clusterID, player_Stats);
                }
                else
                {
                    player_Stats.put(player, stats);
                }
            }
            if(toStringHeader_Vertex == null)
                toStringHeader_Vertex = stats.toStringHeader();
        }
    }

    public String graphStatsHeader(String preHeader)
    {
        return preHeader + "\t" + "GraphType\tClusterID\t" + toStringHeader_Graph + "\n";
    }

    public String vertexStatsHeader(String preHeader)
    {
        return preHeader + "\t" + "VertexGroup\tClusterID\tUser\t" + toStringHeader_Vertex + "\n";
    }

    public StringBuilder toString_GraphStats(String prefix)
    {
        StringBuilder str = new StringBuilder();
        for(GraphType type:graphStatistics.keySet()) {
            TreeMap<Integer, GraphStats> clusterID_Stats = graphStatistics.get(type);
            for(int clusterID : clusterID_Stats.keySet()) {
                str.append(prefix).append("\t");
                str.append(type).append("\t");
                str.append(clusterID).append("\t");
                str.append( clusterID_Stats.get(clusterID).toString() ).append("\n");
            }
        }
        return str;
    }

    public StringBuilder toString_VertexStats(String prefix)
    {
        StringBuilder str = new StringBuilder();
        for(VertexGroup type:vertexStatistics.keySet()) {
            TreeMap<Integer, HashMap<User, VertexStats>> clusterID_User_Stats = vertexStatistics.get(type);
            for(int clusterID:clusterID_User_Stats.keySet()) {
                HashMap<User, VertexStats> user_Stats = clusterID_User_Stats.get(clusterID);
                for(User user: user_Stats.keySet()) {
                    StringBuilder str1 = new StringBuilder();
                    str1.append(prefix).append("\t");
                    str1.append(type).append("\t");
                    str1.append(clusterID).append("\t");
                    str1.append(user.getUUID()).append("_").append(user.getName()).append("\t");
                    VertexStats stats = user_Stats.get(user);
                    str1.append( stats.toString() ).append("\n");
                    str.append(str1);
//                    System.out.println(str1);
                }
            }
        }
        return str;
    }
}
