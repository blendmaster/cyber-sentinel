/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sate.cybersentinel.analysis;

/**
 *
 * @author tudoreanudb
 */
public interface ConversationProbabilityFunction {
    
    public double getProbability(long time);

    public String getFuncName();
    
}
