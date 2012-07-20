/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis;

/**
 *
 * @author Isaac
 */
public class LogLogisticDistribution implements ConversationProbabilityFunction {
    private double alpha;
    private double beta;

    public LogLogisticDistribution(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    public double getProbability(long timeDiff) {
        double num = (beta/alpha)* Math.pow((double)timeDiff/alpha, beta-1);
        double den = Math.pow(1 + Math.pow((double)timeDiff/alpha, beta), 2);
        return num/den;
    }

    public String getFuncName() {
        return this.getClass().getName();
    }

}