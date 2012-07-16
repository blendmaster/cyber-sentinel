package sate.cybersentinel.analysis;

<<<<<<< HEAD
import sate.cybersentinel.analysis.Graph.InteractionGraphEdge;
import sate.cybersentinel.analysis.Graph.InteractionGraph;
import sate.cybersentinel.analysis.Graph.InteractionGraphVertex;
import java.util.LinkedHashMap;
=======
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
>>>>>>> Lots of stuff
import java.util.List;
import java.util.Map;

import sate.cybersentinel.message.user.User;

public class MessageGraph {
<<<<<<< HEAD
    
    private InteractionGraph graph;
    private Map<User, List<ConversationCycle>> interactions;
    ConversationProbabilityFunction convProbFunc;

    public MessageGraph(ConversationProbabilityFunction convProbFunc, Map<User, List<ConversationCycle>> interactions) {
            this.interactions = interactions;
            this.convProbFunc = convProbFunc;
            buildSocialNetworkGraph();
    }

    
    public InteractionGraph getInteractionGraph() {
        return graph;
    }
    
    
    private void buildSocialNetworkGraph()
    {
        if(interactions == null || interactions.isEmpty() || convProbFunc == null)
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
                conversationProbabilities = cycle.getConversationProbability(convProbFunc);
                
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
=======
	private List<Message> messages;
	private Map<String, List<Integer>> responsesMap = new HashMap<>();
	private Map<String, List<Double>> scaledResponsesMap = new HashMap<>();
	private List<Integer> autoResponse = new ArrayList<>();
	private List<Double> scaledAutoResponse = new ArrayList<>();
	
	public MessageGraph(List<Message> messages) {
		this.messages = messages;
	}
	
	public void resolveConversationCycles(String uuid) {
		int personCount = 0;
		int i = 0;
		while(i < messages.size()) {
			Message message = messages.get(i);
			if(message.getSenderUUID().equals(uuid)) {
				if(i + 1 >= messages.size()) {
					break;
				}
				
				i++;
				
				List<String> personsTemp = new ArrayList<>();
				int responseCount = 0;
				
				while(i < messages.size() && !messages.get(i).equals(uuid)) {
					message = messages.get(i);
					
					String other = message.getSenderUUID();
					personsTemp.add(other);
					
					List<Integer> responses;
					if(responsesMap.containsKey(other)) {
						responses = responsesMap.get(other);
					}
					else {
						responses = new ArrayList<Integer>();
						Integer[] zeroFiller = new Integer[personCount];
						Arrays.fill(zeroFiller, 0);
						responses.addAll(Arrays.asList(zeroFiller));
					}
					
					responses.add(1);
					responsesMap.put(other, responses);
				
					responseCount++;
					i++;
				}
				i--;
				
				for(Iterator<String> iter = responsesMap.keySet().iterator(); iter.hasNext();) {
					String person2 = iter.next();
					if(!personsTemp.contains(person2)) {
						List<Integer> responses = responsesMap.get(person2);
						responses.add(0);
						responsesMap.put(person2, responses);
					}
				}
				
				if(responseCount > 0) {
					autoResponse.add(1);
					personCount++;
				}
				
				i++;
			}
			
			autoResponse.remove(personCount - 1);
			for(Iterator<String> iter = responsesMap.keySet().iterator(); iter.hasNext();) {
				String personID = iter.next();
				List<Integer> responses = responsesMap.get(personID);
				responses.remove(personCount - 1);
				responsesMap.put(personID, responses);
			}
			
			personCount--;
			
			scaleResponses(personCount);
		}
		
		
	}

	private void scaleResponses(int personCount) {
		scaledResponsesMap = new HashMap<>();
		for(Iterator<String> iter = scaledResponsesMap.keySet().iterator(); iter.hasNext();) {
			List<Double> responses2 = new ArrayList<>();
			String personID = iter.next();
			List<Integer> responses = responsesMap.get(personID);
			int sum = 0;
			for(int response : responses) {
				sum += response;
				double scaledResponse = Math.tanh((double) sum / (personCount + 1));
				responses2.add(scaledResponse);
			}
			scaledResponsesMap.put(personID, responses2);
		}
		
		scaledAutoResponse = new ArrayList<>();
		for(int j = 01; j <= personCount; j++) {
			scaledAutoResponse.add(Math.tanh((double)(j + 1)/(personCount + 1)));
		}
	}
}
>>>>>>> Lots of stuff
