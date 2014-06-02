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
    private boolean hasCornerTwists;
    private boolean hasCornerCycleBreaks;
    public static void main (String[] args)
    {
	System.out.println(new CommSolver(Scrambler.genEasyScramble()).toString());
    }
    public CommSolver(String givenScramble)
    {
	refresh(givenScramble);
    }
    public void refresh(String newScramble)
    {
	cornerTracer = new CornerTracer(newScramble);
	edgeTracer = new EdgeTracer(newScramble);
	scramble = newScramble;
	hasParity = Tracer.hasParity(newScramble);

	edgeCycles = edgeTracer.traceEdges();
	hasEdgeFlips = edgeTracer.hasFlippedEdges();
	hasEdgeCycleBreaks = edgeTracer.getIfEdgeCycleBreaks();

	cornerCycles = cornerTracer.traceCorners();
	hasCornerTwists = cornerTracer.hasTwistedCorners();
	hasCornerCycleBreaks = cornerTracer.getIfCornerCycleBreaks();
    }
    public String toString()
    {
	String reported = "scramble: " + scramble + "\n"
	    + "parity: " + hasParity + "\n"
	    + "edge cycle: " + edgeCycles + "\n"
	    + "corner cycle: " + cornerCycles + "\n"
	    + "edge flips: " + hasEdgeFlips + " / edge cycle breaks: " + hasEdgeCycleBreaks + "\n"
 	    + "corner flips: " + hasCornerTwists + " / corner cycle breaks: " + hasCornerCycleBreaks + "\n";
	
	return reported;
    }
}
