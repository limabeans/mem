import java.util.*;

public class CommSolver
{
    private CornerTracer cornerTracer;
    private EdgeTracer edgeTracer;

    private String scramble;
    private boolean hasParity;

    private String edgeCycles;
    private boolean hasEdgeFlips;
    private boolean hasEdgeCycleBreaks;

    private String cornerCycles;
    private boolean hasTwistedCorners;
    private boolean hasCornerCycleBreaks;


    public boolean getHasParity()
    {
	return hasParity;
    }

    public int getEdgeMetric()
    {
	int edgeMetric = 0;
	for(int x = 0; x<edgeCycles.length(); x++)
	{
	    if(Character.isLetter(edgeCycles.charAt(x)))
	    {
		edgeMetric++;
	    }
	}
	return edgeMetric;
    }

    public int getCornerMetric()
    {
	int cornerMetric = 0;
	for(int x = 0; x<cornerCycles.length(); x++)
	{
	    if(Character.isLetter(cornerCycles.charAt(x)))
	    {
		cornerMetric++;
	    }
	}
	return cornerMetric;
    }

    public void refresh(String newScramble)
    {
	cornerTracer = new CornerTracer(newScramble);
	edgeTracer = new EdgeTracer(newScramble);
	scramble = newScramble;
	hasParity = Tracer.hasParity(newScramble);

	hasEdgeFlips = edgeTracer.hasFlippedEdges();
	edgeCycles = edgeTracer.traceEdges();	
	hasEdgeCycleBreaks = edgeTracer.getIfEdgeCycleBreaks();

	hasTwistedCorners = cornerTracer.hasTwistedCorners();	
	cornerCycles = cornerTracer.traceCorners();
	hasCornerCycleBreaks = cornerTracer.getIfCornerCycleBreaks();
    }
    public String toString()
    {
	String reported = scramble + "\n"
	    + "parity: " + hasParity + "\n"
	    + "edge cycle: " + edgeCycles + "(" + 
	    getEdgeMetric() + ")\n"
	    + "corner cycle: " + cornerCycles + "(" + 
	    getCornerMetric() + ")\n"
	    + "edge flips: " + hasEdgeFlips + " / edge cycle breaks: " + hasEdgeCycleBreaks + "\n"
 	    + "corner twists: " + hasTwistedCorners + " / corner cycle breaks: " + hasCornerCycleBreaks + "\n";
	
	return reported;
    }

    public CommSolver(String givenScramble)
    {
	refresh(givenScramble);
    }
    public String getEdgeCycles()
    {
	return edgeCycles;
    }
    public String getCornerCycles()
    {
	return cornerCycles;
    }
    public boolean hasGivenEdgeComm(String comm)
    {
	if(edgeCycles.contains(comm))
	{
	    return true;
	}
	return false;
    }
    public boolean hasGivenCornerComm(String comm)
    {
	if(cornerCycles.contains(comm))
	{
	    return true;
	}
	return false;
    }
    public boolean hasFlippedEdges()
    {
	return hasEdgeFlips;
    }
    public boolean hasTwistedCorners()
    {
	return hasTwistedCorners;
    }

}
