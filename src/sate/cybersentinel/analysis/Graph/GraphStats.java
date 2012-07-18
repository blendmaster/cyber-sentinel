/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;

import java.util.List;
import java.util.Set;
import sate.cybersentinel.message.user.User;

/**
 *
 * @author Isaac
 */
public interface GraphStats<V,E> {

    public static enum GraphType{MAIN, WEAK_CONNECTED, STRONG_CONNECTED, COMMUNITY_MODULARITY};

//    public void setClusterCoefficient(double val);
    public double getClusterCoefficient();

//    public void setModularity(double val);
    public double getModularity();

//    public void setNodeCount(int val);
    public int getNodeCount();

//    public void setEdgeCount(int val);
    public int getEdgeCount();

//    public void setStronglyConnectedNodes(Set<V> set);
    public List<Set<V>> getStronglyConnectedNodes();

//    public void setWeaklyConnectedNodes(Set<V> set);
    public List<Set<V>> getWeaklyConnectedNodes();

    public List<Set<V>> getModularityCommunityList();

    public Set<User> getCutPoints();

    public VertexStats getVertexStats(GraphStatsCollection stats, User v);

    public String toStringHeader();

    public double getDensity();

    public GraphStatsCollection getStatsCollection();

    @Override
    public String toString();
}
