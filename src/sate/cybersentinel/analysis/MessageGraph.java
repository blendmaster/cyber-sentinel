package sate.cybersentinel.analysis;

<<<<<<< HEAD
import sate.cybersentinel.analysis.Graph.InteractionGraphEdge;
import sate.cybersentinel.analysis.Graph.InteractionGraph;
import sate.cybersentinel.analysis.Graph.InteractionGraphVertex;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import sate.cybersentinel.message.User;

public class MessageGraph {
    
    private InteractionGraph graph;
    private Map<User, List<ConversationCycle>> interactions;

    public MessageGraph(Map<User, List<ConversationCycle>> interactions) {
            this.interactions = interactions;
    }
	
    public void buildSocialNetworkGraph()
    {
        if(interactions == null || interactions.isEmpty())
            return;
        
        graph = new InteractionGraph(InteractionGraphEdge.class);
        
        InteractionGraphVertex userVertex, userVertex2;
        Map<User, Double> conversationProbabilities;
        double conversation_probability;
        InteractionGraphEdge edge;
        Map<User, Integer> conversationCycleCount = new LinkedHashMap<User, Integer>();
        for(User user: interactions.keySet()) 
        {
            if(user == null)
                continue;
            
            userVertex = new InteractionGraphVertex(user);
            graph.addVertex( userVertex );
            
            List<ConversationCycle> conversationCycles = interactions.get(user);            
            if(conversationCycles == null || conversationCycles.isEmpty())
                continue;
            
            conversationCycleCount.clear();
            for(ConversationCycle cycle : conversationCycles)
            {
                if(cycle == null || cycle.getUserCount()==0)
                    continue;
                conversationProbabilities = cycle.getConversationProbability(null);
                
                if(conversationProbabilities==null || conversationProbabilities.isEmpty())
                    continue;
                
                for(User user2 : conversationProbabilities.keySet())
                {
                    if(user2 == null)
                        continue;
                    conversation_probability = conversationProbabilities.get(user2);
                    
                    userVertex2 = new InteractionGraphVertex(user2); 
                    graph.addVertex( userVertex2 );
                    
                    addInteractionGraphVertex( userVertex, userVertex2, conversation_probability);
                    
                    if(conversationCycleCount.containsKey(user2))
                    {
                        int count = conversationCycleCount.get(user2) + 1;
                        conversationCycleCount.put(user2, count);
                    }
                    else
                    {
                        conversationCycleCount.put(user2, 1);
                    }
                }
            }
                
             for(User user2 : conversationCycleCount.keySet())
             {
                userVertex2 = new InteractionGraphVertex(user2);
                edge = graph.getEdge(userVertex, userVertex2);
                if(edge == null)
                    continue;
                
                int count = conversationCycleCount.get(user2);
                edge.setCycleCount( count );
             }
            
        }
    }
    
    
    public void addInteractionGraphVertex(InteractionGraphVertex v1, InteractionGraphVertex v2, double weight) 
    {

        //if interactionWeightWithUser is not up to threshold then do not add vertex
//        if(weight <= threshold) {
//            return;
//        }
        
        //add link between current user and other user
        InteractionGraphEdge edge = graph.getEdge(v1, v2);
        if(edge==null) 
        {
            edge = graph.addEdge(v1, v2);
           
            edge.setWeight(weight);
        }
        else 
        {
            double newWeight = (weight + edge.getWeight())/(double)2;
            edge.setWeight(newWeight);
        }
        
    }

}
