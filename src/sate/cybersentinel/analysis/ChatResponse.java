/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sate.cybersentinel.analysis;

import java.util.*;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.user.User;

/**
 *
 * @author tudoreanudb
 */
public final class ChatResponse {
    
    List<Message> messages;
    Map<User, List<sate.cybersentinel.analysis.ConversationCycle>> interactions;

    public ChatResponse(List<Message> messages) {
        this.messages = messages;
        resolveInteractions();
    }

    public Map<User, List<sate.cybersentinel.analysis.ConversationCycle>> getInteractions() {
        return interactions;
    }

    public List<Message> getMessages() {
        return messages;
    }   
    
    
    private void resolveInteractions()
    {
        if(messages == null || messages.isEmpty())
            return;
        
        interactions = new LinkedHashMap<User, List<sate.cybersentinel.analysis.ConversationCycle>>();
        
        for(Message msg : messages)
        {
            if(msg == null || msg.getUser() == null)
                continue;
            
            resolveInteractions( msg.getUser() );            
        }
    }
    
    
    private void resolveInteractions(User user)
    {
        if(user == null || interactions.containsKey(user))
            return;
        
        List<sate.cybersentinel.analysis.ConversationCycle> 
                            userConversationCycleList = interactions.get(user);
        if(userConversationCycleList == null)
        {
            userConversationCycleList = 
                    new ArrayList<sate.cybersentinel.analysis.ConversationCycle>();
            interactions.put(user, userConversationCycleList);
        }

        Message prevMessage = null;
        Message currMessage = null;
        sate.cybersentinel.analysis.ConversationCycle convCycle = null;
        for(Message message:messages) 
        {
            if(message == null /*|| message.getType() != ChatType.NORMAL*/)  //Message needs method to check type
                continue;
            
            if(prevMessage == null || prevMessage.getUser() != message.getUser()) 
            {
                if(message.getUser() == user) 
                {
                    currMessage = message;
                    convCycle = new ConversationCycle(message);
                    userConversationCycleList.add(convCycle);
                }
                else if(currMessage != null)
                {
                    convCycle.addMessage(message);
                }
            }
            prevMessage = message;
        }
    }

    
    class ConversationCycle implements sate.cybersentinel.analysis.ConversationCycle{
        
        Message message;
        List<Message> messages;
        
        public ConversationCycle(Message message) {
            this.message = message;
        }
        
        @Override
        public Message getMessage()
        {
            return message;
        }

        @Override
        public User getUser() {
            if(message== null)
                return null;
            
            return message.getUser();
        }

        @Override
        public void addMessage(Message message) {
            
            if(message == null)
                return;
            
            if( messages == null && isAddable(message) )
            {
                messages = new ArrayList<Message>();
            }
            
            if( isAddable(message) )
                messages.add(message);
        }
        
        private boolean isAddable(Message message)
        {
            if( this.message == null || message == null || this.message == message || 
                    message.getUser() == getUser() )
                return false;
            
            if( messages.contains(message) )
                return false;
            
            return message.getTime().getTime() > this.message.getTime().getTime();
        }

        @Override
        public Message getFirstMessage() {
            if(messages == null)
                return null;
            return messages.get(0);
        }

        @Override
        public Date getFirstMessageTime() {
            Message message = getFirstMessage();
            if(message == null)
                return null;
            return message.getTime();
        }

        @Override
        public Message getLastMessage() {
            if(messages == null)
                return null;
            return messages.get( messages.size()-1 );
        }

        @Override
        public Date getLastMessageTime() {
            Message message = getLastMessage();
            if(message == null)
                return null;
            return message.getTime();
        }

        @Override
        public List<Message> getMessages() {
            return messages;
        }

        @Override
        public int getMessageCount() {
            if(messages == null)
                return 0;
            return messages.size();
        }

        @Override
        public List<User> getUsers() {
            if(messages == null || messages.isEmpty())
                return null;
            
            List<User> userList = new ArrayList<User>();
            for(Message m : messages)
            {
                if(m == null || m.getUser()==null)
                    continue;
                
                if(!userList.contains(m.getUser()))
                    userList.add( m.getUser() );
            }
            
            return userList;
        }

        @Override
        public int getUserCount() {
            
            List<User> users = getUsers();
            
            if(users == null)
                return 0;
            
            return users.size();
        }
        

        @Override
        public boolean containsUser(User user) {
            if(messages == null)
                return false;
            
            for(Message m : messages)
            {
                if(m == null)
                    continue;
                if(user == m.getUser())
                    return true;
            }
            
            return false;
        }

        @Override
        public boolean contiansMessage(Message message) {
            if(messages == null)
                return false;
            
            for(Message m : messages)
            {
                if(m == message)
                    return true;
            }
            
            return false;
        }
        
        
        @Override
        public double getConversationProbability(User user, ConversationProbabilityFunction func) {
            if( user == null || func == null || messages == null || user == getUser() )
                return 0;
            
            //get first message sent by user
            boolean found = false;
            Enumeration<Message> enume = Collections.enumeration(messages);
            Message message = null;
            while(enume.hasMoreElements() && !found)
            {
                message = enume.nextElement();
                if(message != null && message.getUser()==user)
                {
                    found = true;
                }
            }
            
            if(message == null)
                return 0;
            
            long time_difference = this.message.getTime().getTime() - message.getTime().getTime();
            
            return func.getProbability(time_difference);
        }

        @Override
        public Map<User, Double> getConversationProbability(ConversationProbabilityFunction func) {
            if( func == null || messages == null)
                return null;
            
            List<User> users = getUsers();
            if(users == null || users.isEmpty())
                return null;
            
            Map<User, Double> probabilities = new LinkedHashMap<User, Double>();
            double prob;
            for(User user : users)
            {
                prob = getConversationProbability(user, func);
                if(prob > 0)
                    probabilities.put(user, prob);
            }
            
            if(probabilities.isEmpty())
                probabilities = null;
            
            return probabilities;
        }

        @Override
        public int compareTo(sate.cybersentinel.analysis.ConversationCycle o) {
            
            sate.cybersentinel.analysis.ConversationCycle oo = (sate.cybersentinel.analysis.ConversationCycle)o;
                return (int) (oo.getMessage().getTime().getTime() - message.getTime().getTime());
        }
        
    }
    
    
}
