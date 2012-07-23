/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;

import java.util.*;
import sate.cybersentinel.message.user.User;
import org.gephi.data.attributes.AttributeRowImpl;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeValue;
import org.gephi.graph.api.Attributes;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.partition.api.Part;
import org.gephi.partition.api.Partition;
import org.gephi.partition.api.PartitionController;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.statistics.plugin.ClusteringCoefficient;
import org.gephi.statistics.plugin.EigenvectorCentrality;
import org.gephi.statistics.plugin.GraphDensity;
import org.gephi.statistics.plugin.GraphDistance;
import org.gephi.statistics.plugin.Modularity;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.alg.BlockCutpointGraph;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.graph.AsUndirectedGraph;
import org.openide.util.Lookup;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphVertex;
/**
 *
 * @author Isaac
 */
public class GraphStatsCollection  implements GraphStats{

    InteractionGraph g;
    boolean directed;
    ConnectivityInspector weakConnectInsp;
    StrongConnectivityInspector strongConnectInsp;
    BlockCutpointGraph blockCutpointGraph;
    BronKerboschCliqueFinder clique;
    GraphModel graphModel;
    Modularity modularity;
    GraphDistance graphDistance;
    EigenvectorCentrality eigenvectorCentrality;
    GraphDensity graphDensity;
    AttributeModel attributeModel;
    Partition<Node> partition;
    CentralityComputer centralityComp;
    List<Set<User>> modularityCommunity;
    
    public static Workspace workspace;
    public static ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
    public static PartitionController partitionController = Lookup.getDefault().lookup(PartitionController.class);
    
    ClusteringCoefficient clusteringCoefficient;
    double averageClusteringCoefficient = -1;

    public GraphStatsCollection(InteractionGraph g, boolean directed) {
        
        pc.deleteWorkspace(pc.getCurrentWorkspace());
        pc.newProject();
        workspace = pc.getCurrentWorkspace();
        
        if(g != null)
        {
            this.g = g;
            this.directed = directed;
            org.gephi.graph.api.Graph convert = GraphConverter.convert(g, directed);
            graphModel = convert.getGraphModel();
            attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        }
    }

    
    public void computeAll() {
        getWeakConnectInsp();
        getStrongConnectInsp();
//        getCentralityStats(g);
        getBlockCutpointGraph();
        getClique();
        getGraphModularity();
        getClusteringCoefficient();
        getGraphDistance();
        getEigenvectorCentrality();
        getGraphDensity();
    }

    
    public ConnectivityInspector getWeakConnectInsp() {
        
        if(weakConnectInsp!=null)
            return weakConnectInsp;
        
        pc.cleanWorkspace(workspace);
        
        DirectedGraph g0 = (DirectedGraph) g;
        weakConnectInsp = new ConnectivityInspector(g0);
        return weakConnectInsp;
    }

    
    public StrongConnectivityInspector getStrongConnectInsp() {
        
        if(strongConnectInsp!=null)
            return strongConnectInsp;
        
        pc.cleanWorkspace(workspace);
        
        DirectedGraph g0 = (DirectedGraph) g;
        strongConnectInsp = new StrongConnectivityInspector(g0);
        return strongConnectInsp;
    }

    
    public BlockCutpointGraph getBlockCutpointGraph() {
        
        if(blockCutpointGraph!=null)
            return blockCutpointGraph;
        
        pc.cleanWorkspace(workspace);
        
       DirectedGraph g0 = (DirectedGraph)g;
        blockCutpointGraph = new BlockCutpointGraph(new AsUndirectedGraph(g0));        
        return blockCutpointGraph;
    }

    
    public CentralityComputer getCentralityStats() {
        
        if(centralityComp!=null)
            return centralityComp;
        
        pc.cleanWorkspace(workspace);
        
        centralityComp = new CentralityComputer(g);
        return centralityComp;
    }

    
    public void getClique() {
        
        pc.cleanWorkspace(workspace);
        
        clique = new BronKerboschCliqueFinder(g);
    }

    
    public double getClusteringCoefficient() {
        
        if(graphModel==null)
            return -1;
        
        if(clusteringCoefficient!=null)
            return averageClusteringCoefficient;
        
        pc.cleanWorkspace(workspace);
        
        System.runFinalization();
        System.gc();
        clusteringCoefficient = new ClusteringCoefficient();
        clusteringCoefficient.execute(graphModel, attributeModel);
        averageClusteringCoefficient = clusteringCoefficient.getAverageClusteringCoefficient();
        return averageClusteringCoefficient;
    }

    
    public GraphDistance getGraphDistance() {
        
        if(graphModel==null)
            return null;
        
        if(graphDistance!=null)
            return graphDistance;
        
        pc.cleanWorkspace(workspace);
        
        System.runFinalization();
        System.gc();
        graphDistance = new GraphDistance();
        graphDistance.setDirected(graphModel.isDirected());
        graphDistance.setNormalized(true);
        graphDistance.execute(graphModel, attributeModel);
        
        return graphDistance;
    }

    
    public Modularity getGraphModularity() {
        
        if(graphModel==null || attributeModel==null)
            return null;
        
        if(modularity!=null)
            return modularity;
        /*
        pc.cleanWorkspace(workspace);
        
        System.runFinalization();
        System.gc();*/
        if(graphModel != null) {
            modularity = new Modularity();
            modularity.setRandom(true);
            modularity.execute(graphModel, attributeModel);
            getModularityPartition();
        }
        
        return modularity;
    }

    
    private void getModularityPartition() {
        System.runFinalization();
        System.gc();
        //Partition with 'modularity_class', just created by Modularity algorithm
        if(attributeModel != null) {            
            AttributeColumn modColumn = attributeModel.getNodeTable().getColumn(Modularity.MODULARITY_CLASS);
            partition = partitionController.buildPartition(modColumn, graphModel.getDirectedGraph());
            getModularityPartitionVertex();
        }
    }

    
    private void getModularityPartitionVertex() {
        modularityCommunity = new ArrayList<Set<User>>();
        Iterator<InteractionGraphVertex> vertex_iterator = g.vertexSet().iterator();
        
        for(Part<Node> p:partition.getParts()) {
            Node[] nodes = p.getObjects();
            Set<User> community = new HashSet<User>();
            
            for(Node n:nodes) {
                
                if(n.getNodeData() == null)
                    continue;
                User user = null;
                while( vertex_iterator.hasNext() )
                {
                    InteractionGraphVertex next = vertex_iterator.next();
                    if( next.getUser().getUUID().equals(n.getNodeData().getId()) )
                        user = next.getUser();
                }
                if(user != null)
                    community.add(user);
            }
            modularityCommunity.add(community);
        }

    }

    
    public EigenvectorCentrality getEigenvectorCentrality() {
        
         if(graphModel==null)
            return null;
         
         if(eigenvectorCentrality!=null)
             return eigenvectorCentrality;
         
         pc.cleanWorkspace(workspace);
         
        System.runFinalization();
        System.gc();
        eigenvectorCentrality = new EigenvectorCentrality();
        eigenvectorCentrality.execute(graphModel, attributeModel);
        return eigenvectorCentrality;
    }

    
    public GraphDensity getGraphDensity() {
        
         if(graphModel==null)
            return null;
         
         if(graphDensity!=null)
             return graphDensity;
         
         pc.cleanWorkspace(workspace);
         
        System.runFinalization();
        System.gc();
        graphDensity = new GraphDensity();
        graphDensity.execute(graphModel, attributeModel);
        
        return graphDensity;
    }

    
    public AttributeModel getAttributeModel() {
        return attributeModel;
    }


    public InteractionGraph getGraph_JGrapht() {
        return g;
    }

    
    public GraphModel getGraphModel() {
        return graphModel;
    }


    public List<Set<User>> getModularityCommunity() {
        
        if(modularityCommunity==null)            
            getModularity();
        
        return modularityCommunity;
    }

    
    public double getAverageClusteringCoefficient() {
        if(averageClusteringCoefficient==-1)
            getClusteringCoefficient();
        return averageClusteringCoefficient;
    }
    
    
    
    /**
     * GraphStats Interface Implementation
     */
  
    public double getDiameter() {
        
        if(getGraphDistance()==null)
            return -1;
        
        return getGraphDistance().getDiameter();
    }

    
    public Graph getG() {
        return g;
    }


    public double getPathLength() {
        
        if(getGraphDistance()==null)
            return -1;
        
        return getGraphDistance().getPathLength();
    }


    public StrongConnectivityInspector getStrongConnectedSets() {        
        
        return getStrongConnectInsp();
    }
    

    @Override
    public double getClusterCoefficient() {        
        
        return getAverageClusteringCoefficient();
    }

    @Override
    public double getModularity() {
        
        if(getGraphModularity()==null)
            return -1;
        
        return getGraphModularity().getModularity();
    }
    

    @Override
    public double getDensity() {
        
        if(getGraphDensity()==null)
            return -1;
        
        return getGraphDensity().getDensity();
    }
    
    
    @Override
    public int getNodeCount() {
        
        if(g==null)
            return -1;
        
        return g.vertexSet().size();
    }

    
    @Override
    public int getEdgeCount() {
        
        if(g==null)
            return -1;
        
        return g.edgeSet().size();
    }

    
    @Override
    public List<Set<User>> getStronglyConnectedNodes() {
        
        if( strongConnectInsp==null )
            return null;
        
        return strongConnectInsp.stronglyConnectedSets();
    }

    
    @Override
    public List<Set<User>> getWeaklyConnectedNodes() {
        
        if(getWeakConnectInsp()==null)
            return null;
        
        return getWeakConnectInsp().connectedSets();
        
    }

    
    @Override
    public Set<User> getCutPoints() {
        
        if(blockCutpointGraph==null)
            return null;
        
        return blockCutpointGraph.getCutpoints();
    }

    
    @Override
    public VertexStats getVertexStats(GraphStatsCollection stats, User v)
    {
        VertexStats vStat = new VertexStatImpl(v, stats);
        return vStat;
    }

    
    @Override
    public List<Set<User>> getModularityCommunityList() {
        return getModularityCommunity();
    }

    
    @Override
    public GraphStatsCollection getStatsCollection() {
        return this;
    }

    
    @Override
    public String toStringHeader()
    {
        return  "clusteringCoefficient\tmodularity\tnodeCount\tedgeCount" +
                   "\tstrongConnect\tstrongConnectSize" +
                   "\tweakConnect\tweakConnectSize" +
                   "\tcutPoints\tcutPointsSize\tfastModuCommunity"
                   + "\tfastModuCommunitySize\tgraphDensity"
                   + "\tdiameter\tpathLength";
    }

    @Override
    public String toString()
    {
        String s = getClusteringCoefficient() + "\t" + getModularity() + "\t" + getNodeCount() + 
                   "\t" + getEdgeCount() + 
                   "\t" + getStronglyConnectedNodes() + 
                   "\t" + (getStronglyConnectedNodes()==null ? -1:getStronglyConnectedNodes().size()) +
                   "\t" + getWeaklyConnectedNodes() + 
                   "\t" + (getWeaklyConnectedNodes()==null ? -1:getWeaklyConnectedNodes().size()) +
                   "\t" + getCutPoints() + 
                   "\t" + (getCutPoints()==null ? -1 : getCutPoints().size()) +
                   "\t" + getModularityCommunityList() + 
                   "\t" + (getModularityCommunityList()==null ? -1:getModularityCommunityList().size() + 
                   "\t" + getDensity()) +
                   "\t" + getDiameter() + "\t" + getPathLength();
        return s;
    }
    
}
