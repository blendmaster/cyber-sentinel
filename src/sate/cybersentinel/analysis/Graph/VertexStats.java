/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;

import java.io.Serializable;

/**
 *
 * @author Isaac
 */
public interface VertexStats extends Serializable, Comparable<VertexStats>{

    public static enum VertexGroup{MAIN_GRAPH, WEAK_CONNECTED, STRONG_CONNECTED, COMMUNITY_MODULARITY};
//    public void setClusterCoefficient(double val);
    
    public String getId();
    
    public double getClusterCoefficient();

//    public void setBetweenessCentrality(double val);
    public double getBetweenessCentrality();

//    public void setCutPoint(boolean bool);
    public boolean isCutPoint();

    public int inDegree();

    public int inDegreeWeight();

    public int outDegree();

    public int outDegreeWeight();

    public int Degree();

    public int DegreeWeight();

    public String toStringHeader();

    public String toString();
}
