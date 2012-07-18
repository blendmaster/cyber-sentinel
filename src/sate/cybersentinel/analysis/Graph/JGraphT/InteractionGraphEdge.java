/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph.JGraphT;

import java.util.Set;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 *
 * @author Isaac
 */
public class InteractionGraphEdge  extends DefaultWeightedEdge {
    private double weight;
    private int cycleCount;
    public InteractionGraphEdge() {
        super();
        weight = 0;
        cycleCount = 0;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public double incrementWeight(Double increment) {
        this.weight = weight + increment;
        return weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public void setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
    }
    
    public Set<org.apache.lucene.index.TermFreqVector> getTermFreqency()
    {
        
    }

    @Override
    public String toString() {
        return "\nWeight:"+weight + "\tCycleCount:"+cycleCount;
    }
}


