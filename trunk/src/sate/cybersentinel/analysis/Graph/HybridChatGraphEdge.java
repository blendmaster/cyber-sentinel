/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;

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
    private WordPairMap commonWords;
    public HybridChatGraphEdge() {
        super();
        responseTimeWeight = 0;
        countOfDirectMessaging = 0;
        commonWords = new WordPairMap();
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

    public void addCommonWord(WordPair word, Number count) {
        commonWords.put(word, count);
    }

    //Need to modify this word is always a new object
    public boolean containsCommonWord(WordPair word) {
        return commonWords.containsKey(word);
    }

    public WordPairMap getCommonWords() {
        return commonWords;
    }

    @Override
    public String toString() {
        String s = "\nRT Wght:"+responseTimeWeight + "\tDirectAddr Wght:" + countOfDirectMessaging;
        if(!commonWords.isEmpty())
                s += "\tcommonWords:" + commonWords.size();
        return s;
    }
}
