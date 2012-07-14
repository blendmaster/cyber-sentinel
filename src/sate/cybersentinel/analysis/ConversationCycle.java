/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sate.cybersentinel.analysis;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.User;

/**
 * Conversation cycle interface for organizing individual conversation cycle.
 * A user's entire interactions would be captured in multiple conversation cycles 
 * @author Isaac Osesina
 */
public interface ConversationCycle extends Serializable, Comparable{
    
    /**
     * Subject Message of conversation
     * @return 
     */
    public Message getMessage();
    
    /**
     * User (Subject) of the conversation cycle
     * @return 
     */
    public User getUser();
    
    /**
     * Adds message to conversation Cycle
     * @param message 
     */
    public void addMessage(Message message);
    
    /**
     * Returns true if this conversation cycle contains this user
     * @param user
     * @return 
     */
    public boolean containsUser(User user);
    
    
    /**
     * Returns true if this conversation cycle contains this message
     * @param message
     * @return 
     */
    public boolean contiansMessage(Message message);
    
    /**
     * First Message of this conversation cycle
     * @return first Message in the conversation cycle. Returns null if there are 
     * no messages.
     */
    public Message getFirstMessage();
    
    /**
     * Time the first message was sent
     * @return time (long) the first message was sent
     */
    public Date getFirstMessageTime();
    
    /**
     * Last Message of this conversation cycle
     * @return last Message in the conversation cycle. Returns null if no 
     * message is contained in the conversation cycle.
     */
    public Message getLastMessage();
    
    /**
     * Time the last message was sent
     * @return time (long) the last message was sent
     */
    public Date getLastMessageTime();
    
    /**
     * All the Messages in this conversation cycle
     * @return List<Message> or null if there are no messages.
     */
    public List<Message> getMessages();
    
    /**
     * Number of Messages in the conversation cycle
     * @return 
     */
    public int getMessageCount();
    
    /**
     * All the Users in the conversation cycle
     * @return List<User> or null if there are no message
     */
    public List<User> getUsers();
    
    /**
     * Number of Users in the conversation cycle
     * @return 
     */
    public int getUserCount();
    
    /**
     * Calculates the probability that user is interacting with the conversation
     * cycle subject
     * @param user for whom to calculate interaction probability
     * @param func function to be used for probability calculation
     * @return double. 0 if user is not in this conversation cycle.
     */
    public double getConversationProbability(User user, ConversationProbabilityFunction func);
    
    /**
     * Calculates the probability that each user in the conversation cycle is 
     * interacting with the subject
     * @param func function to be used for probability calculation
     * @return Map<User, Double> or null if there are no messages or users in
     * conversation cycle
     */
    public Map<User, Double> getConversationProbability(ConversationProbabilityFunction func);
    
}
