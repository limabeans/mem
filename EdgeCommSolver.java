import java.util.*;
public class EdgeCommSolver extends Tracer 
{
    private String givenScramble;


    public EdgeCommSolver(String scramble)
    {
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
	if(checkAG()==SOLVED
	   && checkBK()==SOLVED
	   && checkCO()==SOLVED
	   && checkDS()==SOLVED
	   && checkEU()==SOLVED
	   && checkIV()==SOLVED
	   && checkMW()==SOLVED
	   && checkQY()==SOLVED
	   && checkFL()==SOLVED
	   && checkJN()==SOLVED
	   && checkPT()==SOLVED
	   && checkHR()==SOLVED)
	    return true;
	return false;
    }
    public boolean flippedEdgesLeft()
    {
	if(!isEdgesSolved())
	{
	    if(checkAG()!=UNSOLVED
	       && checkBK()!=UNSOLVED
	       && checkCO()!=UNSOLVED
	       && checkDS()!=UNSOLVED
	       && checkEU()!=UNSOLVED
	       && checkIV()!=UNSOLVED
	       && checkMW()!=UNSOLVED
	       && checkQY()!=UNSOLVED
	       && checkFL()!=UNSOLVED
	       && checkJN()!=UNSOLVED
	       && checkPT()!=UNSOLVED
	       && checkHR()!=UNSOLVED)
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
    

