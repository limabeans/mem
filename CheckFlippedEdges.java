public class CheckFlippedEdges extends CheckSpecialCases
{
    protected static final int FLIPPED = 1;
    public CheckFlippedEdges(String inputtedScramble)
    {
	super(inputtedScramble);
    }
    public boolean hasFlippedEdges()
    {
	if(checkAG()==FLIPPED
	   ||checkBK()==FLIPPED
	   ||checkCO()==FLIPPED
	   ||checkDS()==FLIPPED
	   ||checkEU()==FLIPPED
	   ||checkIV()==FLIPPED
	   ||checkMW()==FLIPPED
	   ||checkQY()==FLIPPED
	   ||checkFL()==FLIPPED
	   ||checkJN()==FLIPPED
	   ||checkPT()==FLIPPED
	   ||checkHR()==FLIPPED)
	    return true;
	return false;
    }

    public int checkAG()
    {
	if(edgeMap.get("A").equals("A")
	   && edgeMap.get("G").equals("G"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("A").equals("G")
	   && edgeMap.get("G").equals("A"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkBK()
    {
	if(edgeMap.get("B").equals("B")
	   && edgeMap.get("K").equals("K"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("B").equals("K")
	   && edgeMap.get("K").equals("B"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkCO()
    {
	if(edgeMap.get("C").equals("C")
	   && edgeMap.get("O").equals("O"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("C").equals("O")
	   && edgeMap.get("O").equals("C"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkDS()
    {
	if(edgeMap.get("D").equals("D")
	   && edgeMap.get("S").equals("S"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("D").equals("S")
	   && edgeMap.get("S").equals("D"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkEU()
    {
	if(edgeMap.get("E").equals("E")
	   && edgeMap.get("U").equals("U"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("E").equals("U")
	   && edgeMap.get("U").equals("E"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkIV()
    {
	if(edgeMap.get("I").equals("I")
	   && edgeMap.get("V").equals("V"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("I").equals("V")
	   && edgeMap.get("V").equals("I"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkMW()
    {
	if(edgeMap.get("M").equals("M")
	   && edgeMap.get("W").equals("W"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("M").equals("W")
	   && edgeMap.get("W").equals("M"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkQY()
    {
	if(edgeMap.get("Q").equals("Q")
	   && edgeMap.get("Y").equals("Y"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("Q").equals("Y")
	   && edgeMap.get("Y").equals("Q"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkFL()
    {
	if(edgeMap.get("F").equals("F")
	   && edgeMap.get("L").equals("L"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("F").equals("L")
	   && edgeMap.get("L").equals("F"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkJN()
    {
	if(edgeMap.get("J").equals("J")
	   && edgeMap.get("N").equals("N"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("J").equals("N")
	   && edgeMap.get("N").equals("J"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkPT()
    {
	if(edgeMap.get("P").equals("P")
	   && edgeMap.get("T").equals("T"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("P").equals("T")
	   && edgeMap.get("T").equals("P"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
    public int checkHR()
    {
	if(edgeMap.get("H").equals("H")
	   && edgeMap.get("R").equals("R"))
	{
	    return ORIENTED;
	}
	if(edgeMap.get("H").equals("R")
	   && edgeMap.get("R").equals("H"))
	{
	    return FLIPPED;
	}
	return UNORIENTED;
    }
}
