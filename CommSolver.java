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
	cornerTracer = new CornerTracer(givenScramble);
	edgeTracer = new EdgeTracer(givenScramble);
	scramble = givenScramble;
	hasParity = Tracer.hasParity(givenScramble);

	edgeCycles = edgeTracer.traceEdges();
	//hasEdgeFlips =
	hasEdgeCycleBreaks = edgeTracer.getIfEdgeCycleBreaks();

	cornerCycles = cornerTracer.traceCorners();
	//hasCornerTwists =
	hasCornerCycleBreaks = cornerTracer.getIfCornerCycleBreaks();
    }
    public String toString()
    {
	String reported = "scramble: " + scramble + "\n"
	    + "parity: " + hasParity + "\n"
	    + "edge cycle: " + edgeCycles + "\n"
	    + "edge flips: " + hasEdgeFlips + " / edge cycle breaks: " + hasEdgeCycleBreaks + "\n"
 	    + "corner flips: " + hasCornerTwists + " / corner cycle breaks: " + hasCornerCycleBreaks + "\n";
	
	return reported;
    }
}
