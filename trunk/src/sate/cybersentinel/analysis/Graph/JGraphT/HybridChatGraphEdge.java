/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph.JGraphT;

import WholeNetworkAnalysis.WordPair;
import WholeNetworkAnalysis.WordPairMap;
import java.io.Serializable;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 *
 * @author Isaac
 */
public class HybridChatGraphEdge  extends DefaultWeightedEdge implements Serializable {
    private double responseTimeWeight;
    private double countOfDirectMessaging;
    
    public HybridChatGraphEdge() {
        super();
        responseTimeWeight = 0;
        countOfDirectMessaging = 0;
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

    @Override
    public Object getTarget() {
        return super.getTarget();
    }

    public void setResponseTimeWeight(Double weight) {
        this.responseTimeWeight = weight;
    }

    public double getCountOfDirectMessaging() {
        return countOfDirectMessaging;
    }

    public void setCountOfDirectMessaging(double countOfDirectMessaging) {
        this.countOfDirectMessaging = countOfDirectMessaging;
    }

    public double incrementResponseTimeWeight(Double increment) {
        this.responseTimeWeight = responseTimeWeight + increment;
        return responseTimeWeight;
    }

    public double incrementDirectMessagingWeight(Double increment) {
        this.countOfDirectMessaging = countOfDirectMessaging + increment;
        return countOfDirectMessaging;
    }

    //@Override
    public double getResponseTimeWeight() {
        return responseTimeWeight;
    }

    @Override
    public String toString() {
        String s = "\nRT Wght:"+responseTimeWeight + "\tDirectAddr Wght:" + countOfDirectMessaging;
        return s;
    }
}
