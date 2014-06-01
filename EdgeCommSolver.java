import java.util.*;
public class EdgeCommSolver extends CheckFlippedEdges implements CubeCommSolver
{
    private ScrambleTracer tracer;
    private String givenScramble;
    private LinkedHashMap<String,String> edgeMap;
    public static void main(String[] args)
    {
	Scrambler scrambler = new Scrambler();
	String tempScramble = scrambler.genScramble();
	System.out.println(tempScramble);
	EdgeCommSolver edgeSolver = new EdgeCommSolver(tempScramble);
	CheckSpecialCases csc = new CheckSpecialCases(tempScramble);

	System.out.println(edgeSolver.idNextComm());
    }
    public EdgeCommSolver(String scramble)
    {
	givenScramble = scramble;
	tracer = new ScrambleTracer(scramble);
	edgeMap = tracer.getEdgeMap();
    }
    public String idNextComm()
    {
	String comm = "";
	comm+= edgeMap.get("U");
	comm+= edgeMap.get(edgeMap.get("U"));
	return comm;
    }
    public void solveNextComm()
    {
	
    }
    public boolean isEdgesSolved()
    {
	if(checkAG()==PERMED
	   && checkBK()==PERMED
	   && checkCO()==PERMED
	   && checkDS()==PERMED
	   && checkEU()==PERMED
	   && checkIV()==PERMED
	   && checkMW()==PERMED
	   && checkQY()==PERMED
	   && checkFL()==PERMED
	   && checkJN()==PERMED
	   && checkPT()==PERMED
	   && checkHR()==PERMED)
	    return true;
	return false;
    }
    public boolean flippedEdgesLeft()
    {
	if(!isEdgesSolved)
	{
	    if(checkAG()!=UNORIENTED
	       && checkBK()!=UNORIENTED
	       && checkCO()!=UNORIENTED
	       && checkDS()!=UNORIENTED
	       && checkEU()!=UNORIENTED
	       && checkIV()!=UNORIENTED
	       && checkMW()!=UNORIENTED
	       && checkQY()!=UNORIENTED
	       && checkFL()!=UNORIENTED
	       && checkJN()!=UNORIENTED
	       && checkPT()!=UNORIENTED
	       && checkHR()!=UNORIENTED)
	    {
		//not solved, in the right place--> flipped!
		return true;
	    }
	    //not solved, not in the right place
	    return false;
	}
	//they're actually all solved
	return false;

    }
    
    
}
