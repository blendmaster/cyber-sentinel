/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sate.cybersentinel.analysis.Graph;


import java.util.Set;
import org.gephi.data.attributes.AttributeRowImpl;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeRow;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeValue;
import org.gephi.graph.api.Node;
import org.gephi.statistics.plugin.ClusteringCoefficient;
import org.gephi.statistics.plugin.EigenvectorCentrality;
import org.gephi.statistics.plugin.GraphDistance;
import org.gephi.statistics.plugin.Modularity;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.BlockCutpointGraph;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import sate.cybersentinel.analysis.Graph.JGraphT.InteractionGraphEdge;
import sate.cybersentinel.message.user.User;

/**
 *
 * @author Isaac
 */
public class VertexStatImpl implements VertexStats{

    Graph g;
    User v;
    GraphStatsCollection statsCollection;
//    CentralityComputer nodeCentralityStats;
    BlockCutpointGraph blockCutpointGraph;
    double clusterCoefficient;
    double betweenessCentrality;
    double closnessCentrality;
    double eccentricity;
    double eigenvectorCentrality;
    boolean isCutPoint;
    int inDegree;
    int outDegree;
    int degree;
    int inDegreeWeight;
    int outDegreeWeight;
    int degreeWeight;

    public VertexStatImpl(User v, GraphStatsCollection statsCollection) {
        this.v = v;
        this.statsCollection = statsCollection;
        g = statsCollection.getGraph_JGrapht();
        blockCutpointGraph = statsCollection.blockCutpointGraph;
//        nodeCentralityStats = statsCollection.centralityComp;
        getValues();
    }
    

    private void getValues()
    {
        AttributeTable nodeTable = statsCollection.attributeModel.getNodeTable();
        Node node = statsCollection.graphModel.getGraph().getNode( v.getUUID() );
        AttributeRowImpl row = (AttributeRowImpl) node.getNodeData().getAttributes();
        AttributeColumn col = nodeTable.getColumn(ClusteringCoefficient.CLUSTERING_COEFF);
        if(col != null)
            clusterCoefficient = (Double) row.getValue(ClusteringCoefficient.CLUSTERING_COEFF);
        col = nodeTable.getColumn(GraphDistance.BETWEENNESS);
        if(col != null)
            betweenessCentrality = (Double)row.getValue(GraphDistance.BETWEENNESS);
        col = nodeTable.getColumn(GraphDistance.CLOSENESS);
        if(col != null)
            closnessCentrality = (Double)row.getValue(GraphDistance.CLOSENESS);
        col = nodeTable.getColumn(GraphDistance.ECCENTRICITY);
        if(col != null)
            eccentricity = (Double)row.getValue(GraphDistance.ECCENTRICITY);
        col = nodeTable.getColumn(EigenvectorCentrality.EIGENVECTOR);
        if(col != null)
            eigenvectorCentrality = (Double)row.getValue(EigenvectorCentrality.EIGENVECTOR);
//        clusterCoefficient = nodeCentralityStats.findClusteringOf(v);
//        betweenessCentrality = nodeCentralityStats.findBetweennessOf(v);
        if(blockCutpointGraph != null)
            isCutPoint = blockCutpointGraph.isCutpoint(v);
//        if( g.getClass().isInstance(DirectedGraph.class) )
            try
        {
            DirectedGraph d = (DirectedGraph)g;
            inDegree = d.inDegreeOf(v);
            outDegree = d.outDegreeOf(v);
            degree = inDegree + outDegree;

//            if(g.getClass().isInstance(WeightedGraph.class))
                try
            {
//                DefaultDirectedWeightedGraph dwg = (DefaultDirectedWeightedGraph) g;
                DirectedWeightedMultigraph dwg = (DirectedWeightedMultigraph) g;
                Set<InteractionGraphEdge> incomingEdgesOf = dwg.incomingEdgesOf(v);
                int incomingWeight = 0;
                for(InteractionGraphEdge e:incomingEdgesOf) {
                    incomingWeight += e.getWeight();
                }
                inDegreeWeight = incomingWeight;

                Set<InteractionGraphEdge> outcomingEdgesOf = dwg.outgoingEdgesOf(v);
                int outgoingWeight = 0;
                for(InteractionGraphEdge e:outcomingEdgesOf) {
                    outgoingWeight += e.getWeight();
                }
                outDegreeWeight = outgoingWeight;
                degreeWeight = inDegreeWeight + outDegreeWeight;
                
            }
                catch(Exception ex) {}
        }
        catch(Exception ex)
        {
            AsUndirectedGraph u = new AsUndirectedGraph( (DirectedGraph) g);
            degree = u.degreeOf(v);
            if(g.getClass().isInstance(WeightedGraph.class)) {
                SimpleWeightedGraph swg = (SimpleWeightedGraph) g;
                Set<InteractionGraphEdge>  edges = swg.edgesOf(v);
                int degrWeight = 0;
                for(InteractionGraphEdge e:edges) {
                    degrWeight += e.getWeight();
                }
                degreeWeight = degrWeight;
            }
        }
    }
    

    @Override
    public double getClusterCoefficient() {
        return this.clusterCoefficient;
    }

    @Override
    public double getBetweenessCentrality() {
        return this.betweenessCentrality;
    }

    @Override
    public boolean isCutPoint() {
        return this.isCutPoint;
    }

    @Override
    public int inDegree() {        
        return inDegree;
    }

    @Override
    public int inDegreeWeight() {
        return inDegreeWeight;
    }

    @Override
    public int outDegree() {
        return outDegree;
    }

    @Override
    public int outDegreeWeight() {
        return outDegreeWeight;
    }

    @Override
    public int Degree() {
        return degree;
    }

    @Override
    public int DegreeWeight() {
        return degreeWeight;
    }


    @Override
    public String toStringHeader()
    {
        return "clusterCoefficient\tbetweenessCentrality\tisCutPoint\tinDegree\tinDegreeWeight"
                + "\toutDegree\toutDegreeWeight\tdegree\tdegreeWeight"
                + "\tclosnessCentrality\teccentricity";
    }

    @Override
    public String toString()
    {
        String s = clusterCoefficient + "\t" + betweenessCentrality + "\t" + isCutPoint +
                   "\t" + inDegree + "\t" + inDegreeWeight + "\t" + outDegree + "\t" + outDegreeWeight +
                   "\t" + degree + "\t" + degreeWeight + "\t" + closnessCentrality + "\t" + eccentricity;
        return s;
    }
}
