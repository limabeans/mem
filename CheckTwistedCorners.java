public class CheckTwistedCorners extends CheckSpecialCases
{


    protected static final int CW = 1;
    protected static final int CCW = -1;

    public CheckTwistedCorners(String inputtedScramble)
    {
	super(inputtedScramble);
    }
     /*for all intents and purposes, i will hard encode all 
     *possible outcomes. of course, it is impossible for only
     *one corner to be twisted. in the future, for more efficient
     *code, i should calculate all the possible corner twists.
     */
    public boolean hasTwistedCorners()
    {
	if(checkAFK()==CW || checkAFK()==CCW
	   ||checkBJN()==CW || checkBJN()==CCW
	   ||checkCOS()==CW || checkCOS()==CCW
	   ||checkDGR()==CW || checkDGR()==CCW
	   ||checkELU()==CW || checkELU()==CCW
	   ||checkIMV()==CW || checkIMV()==CCW
	   ||checkPTW()==CW || checkPTW()==CCW
	   ||checkHQY()==CW || checkHQY()==CCW)
	    return true;
	return false;
    }

    public int checkAFK()
    {
	if(cornerMap.get("A").equals("A")
	   && cornerMap.get("F").equals("F")
	   && cornerMap.get("K").equals("K"))
	{
	    return ORIENTED;
	}
	if(cornerMap.get("A").equals("F")
	   && cornerMap.get("F").equals("K")
	   && cornerMap.get("K").equals("A"))
	{
	    return CW;
	}
	if(cornerMap.get("A").equals("K")
	   && cornerMap.get("F").equals("A")
	   && cornerMap.get("K").equals("F"))
	{
	    return CCW;
	}	    
	return UNORIENTED;
    }

    public int checkBJN()
    {
	if(cornerMap.get("B").equals("B")
	   && cornerMap.get("J").equals("J")
	   && cornerMap.get("N").equals("N"))
	{
	    return ORIENTED;
	}
	if(cornerMap.get("B").equals("J")
	   && cornerMap.get("J").equals("N")
	   && cornerMap.get("N").equals("B"))
	{
	    return CW;
	}
	if(cornerMap.get("B").equals("N")
	   && cornerMap.get("J").equals("B")
	   && cornerMap.get("N").equals("J"))
	{
	    return CCW;
	}
	return UNORIENTED;
    }

    public int checkCOS()
    {
	if(cornerMap.get("C").equals("C")
	   && cornerMap.get("O").equals("O")
	   && cornerMap.get("S").equals("S"))
	{
	    return ORIENTED;
	}
	if(cornerMap.get("C").equals("O")
	   && cornerMap.get("O").equals("S")
	   && cornerMap.get("S").equals("C"))
	{
	    return CW;
	}
	if(cornerMap.get("C").equals("S")
	   && cornerMap.get("O").equals("C")
	   && cornerMap.get("S").equals("O"))
	{
	    return CCW;
	}
	return UNORIENTED;
    }
 
    public int checkDGR()
    {
	if(cornerMap.get("D").equals("D")
	   && cornerMap.get("G").equals("G")
	   && cornerMap.get("R").equals("R"))
	{
	    return ORIENTED;
	}
	if(cornerMap.get("D").equals("R")
	   && cornerMap.get("G").equals("D")
	   && cornerMap.get("R").equals("G"))
	{
	    return CW;
	}
	if(cornerMap.get("D").equals("G")
	   && cornerMap.get("G").equals("R")
	   && cornerMap.get("R").equals("D"))
	{
	    return CCW;
	}
	return UNORIENTED;
    }

    public int checkELU()
    {
	if(cornerMap.get("E").equals("E")
	   && cornerMap.get("L").equals("L")
	   && cornerMap.get("U").equals("U"))
	{
	    return ORIENTED;
	}
	if(cornerMap.get("E").equals("U")
	   && cornerMap.get("L").equals("E")
	   && cornerMap.get("U").equals("L"))
	{
	    return CW;
	}
	if(cornerMap.get("E").equals("L")
	   && cornerMap.get("L").equals("U")
	   && cornerMap.get("U").equals("E"))
	{
	    return CCW;
	}
	return UNORIENTED;
    }

    public int checkIMV()
    {
	if(cornerMap.get("I").equals("I")
	   && cornerMap.get("M").equals("M")
	   && cornerMap.get("V").equals("V"))
	{
	    return ORIENTED;
	}
	if(cornerMap.get("I").equals("V")
	   && cornerMap.get("M").equals("I")
	   && cornerMap.get("V").equals("M"))
	{
	    return CW;
	}
	if(cornerMap.get("I").equals("M")
	   && cornerMap.get("M").equals("V")
	   && cornerMap.get("V").equals("I"))
	{
	    return CCW;
	}
	return UNORIENTED;
    }

    public int checkPTW()
    {
	if(cornerMap.get("P").equals("P")
	   && cornerMap.get("T").equals("T")
	   && cornerMap.get("W").equals("W"))
	{
	    return ORIENTED;
	}
	if(cornerMap.get("P").equals("W")
	   && cornerMap.get("T").equals("Q")
	   && cornerMap.get("W").equals("T"))
	{
	    return CW;
	}
	if(cornerMap.get("P").equals("T")
	   && cornerMap.get("T").equals("W")
	   && cornerMap.get("W").equals("P"))
	{
	    return CCW;
	}
	return UNORIENTED;
    }

    public int checkHQY()
    {
	if(cornerMap.get("H").equals("H")
	   && cornerMap.get("Q").equals("Q")
	   && cornerMap.get("Y").equals("Y"))
	{
	    return ORIENTED;
	}
	if(cornerMap.get("H").equals("Q")
	   && cornerMap.get("Q").equals("Y")
	   && cornerMap.get("Y").equals("H"))
	{
	    return CW;
	}
	if(cornerMap.get("H").equals("Y")
	   && cornerMap.get("Q").equals("H")
	   && cornerMap.get("Y").equals("Q"))
	{
	    return CCW;
	}
	return UNORIENTED;
    }
}
