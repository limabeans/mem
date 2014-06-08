import java.util.*;
import java.io.*;
public class CornerTracer extends Tracer
{
    private int numCornerIterations;
    private String cornerLetterSequence;
    private boolean requiredCornerCycleBreak;
    private int numCornerCycleBreaks;
    int x = 0;
    public CornerTracer(String scramble)
    {
	super(scramble);
	numCornerIterations = 0;
	cornerLetterSequence = "";
	requiredCornerCycleBreak = false;
	numCornerCycleBreaks = 0;
    }
    public boolean getIfCornerCycleBreaks()
    {
	return requiredCornerCycleBreak;
    }
    public String toString()
    {
	return String.format("NO PARITY\nNumber of corner comms used: %s\nCorner letter sequence %s\nNumber of corner cycle breaks: %d",numCornerIterations,cornerLetterSequence,numCornerCycleBreaks);
    }
    public String traceCorners()
    {
	while(!isCornersSolved())
	{
	    solveNextCornerComm();
	}
	return cornerLetterSequence;
    }
    
    public void solveNextCornerComm()
    {

	fixTwistedCorners();
	if(numCornersNotSolved()==2) //PARITY CASE
	{
	    //determining final letter pairs
	    if(cornerMap.get("C").equals("C")
	       && cornerMap.get("O").equals("O")
	       && cornerMap.get("S").equals("S")) //case of 1-2 punch
	    {
		String breaking = findCornerCycleBreak("");
		cornerLetterSequence = cornerLetterSequence +
		    breaking + cornerMap.get(breaking) + " " + breaking;
	    }
	    else //clasic last target
	    {
		cornerLetterSequence = cornerLetterSequence + cornerMap.get("C");
	    }
	    //modifying the entire map to solved, hoho~
	    for(Map.Entry temp : cornerMap.entrySet())
	    {
		cornerMap.put((String)temp.getKey(),(String)temp.getKey());
	    }
	} //END OF PARITY CASE

	if(!isCornersSolved())
	{
	    String[][] afterBuffer = determineNextTarget("C","O","S");
	    String[][] afterFirstTarget = determineNextTarget(afterBuffer[0][0],afterBuffer[1][0],afterBuffer[2][0]);
	    //insert into firstTarget
	    cornerMap.put(afterBuffer[0][0], cornerMap.get("C")); cornerMap.put(afterBuffer[1][0], cornerMap.get("O")); cornerMap.put(afterBuffer[2][0], cornerMap.get("S"));
	    //insert into lastTarget
	    cornerMap.put(afterFirstTarget[0][0],afterBuffer[0][1]); cornerMap.put(afterFirstTarget[1][0],afterBuffer[1][1]); cornerMap.put(afterFirstTarget[2][0],afterBuffer[2][1]);
	    //insert into buffer
	    cornerMap.put("C",afterFirstTarget[0][1]); cornerMap.put("O",afterFirstTarget[1][1]); cornerMap.put("S",afterFirstTarget[2][1]);

	    cornerLetterSequence = cornerLetterSequence + afterBuffer[0][0]+afterFirstTarget[0][0]+ " ";
	    numCornerIterations++;
	}

    }

    private String[][] determineNextTarget(String loc1, String loc2, String loc3)
    {
	//loc1,loc2,loc3 labeled in a CCW fashion around the corner
	String[][] nextTarget = new String[3][2];
	if (cornerMap.get(loc1).equals("C")||cornerMap.get(loc1).equals("O")||cornerMap.get(loc1).equals("S")) //requires cycle break
	{
	    nextTarget[0][0] = findCornerCycleBreak(loc1);
	    nextTarget[0][1] = cornerMap.get(nextTarget[0][0]);
	    nextTarget[1][0] = returnOtherCornerStickerCCW(nextTarget[0][0]);
	    nextTarget[1][1] = cornerMap.get(nextTarget[1][0]);
	    nextTarget[2][0] = returnOtherCornerStickerCW(nextTarget[0][0]);
	    nextTarget[2][1] = cornerMap.get(nextTarget[2][0]);
	}
	else
	{
	    nextTarget[0][0] = cornerMap.get(loc1);
	    nextTarget[0][1] = cornerMap.get(nextTarget[0][0]);
	    nextTarget[1][0] = cornerMap.get(loc2);
	    nextTarget[1][1] = cornerMap.get(nextTarget[1][0]);
	    nextTarget[2][0] = cornerMap.get(loc3);
	    nextTarget[2][1] = cornerMap.get(nextTarget[2][0]);
	}
	return nextTarget;
    }
    private String findCornerCycleBreak(String blacklist)
    {
	for(Map.Entry<String,String> entry : cornerMap.entrySet())
	{
	    String currentKey = entry.getKey();
	    String currentValue = entry.getValue();
	    if((currentKey.equals("C") || currentKey.equals("O") || currentKey.equals("S")))
	       {
		   //don't put it at the buffer spot!
	       }
	       else if (currentKey.equals(blacklist) || currentKey.equals(returnOtherCornerStickerCW(blacklist)) || currentKey.equals(returnOtherCornerStickerCCW(blacklist)))
	       {
		   //don't pick your own sticker to cycle break into!
	       }
	       else if(!(currentKey.equals(currentValue)))
	       {
		   requiredCornerCycleBreak = true;
		   numCornerCycleBreaks++;
		   return currentKey;
	       }
	}
	return "FAILED TO FIND CYCLE BREAK";
    }
    private String returnOtherCornerStickerCW(String givenSticker)
    {
	switch(givenSticker){
	case "A":
	    return "K";
	case "B":
	    return "N";
	case "C":
	    return "S";
	case "D":
	    return "G";
	case "E":
	    return "L";
	case "F":
	    return "A";
	case "G":
	    return "R";
	case "H":
	    return "Y";
	case "I":
	    return "M";
	case "J":
	    return "B";
	case "K":
	    return "F";
	case "L":
	    return "U";
	case "M":
	    return "V";
	case "N":
	    return "J";
	case "O":
	    return "C";
	case "P":
	    return "T";
	case "Q":
	    return "H";
	case "R":
	    return "D";
	case "S":
	    return "O";
	case "T":
	    return "W";
	case "U":
	    return "E";
	case "V":
	    return "I";
	case "W":
	    return "P";
	case "Y":
	    return "Q";
	default:
	    return "Invalid sticker";
	}
     }
    private String returnOtherCornerStickerCCW(String givenSticker)
    {
	switch(givenSticker){
	case "A":
	    return "F";
	case "B":
	    return "J";
	case "C":
	    return "O";
	case "D":
	    return "R";
	case "E":
	    return "U";
	case "F":
	    return "K";
	case "G":
	    return "D";
	case "H":
	    return "Q";
	case "I":
	    return "V";
	case "J":
	    return "N";
	case "K":
	    return "A";
	case "L":
	    return "E";
	case "M":
	    return "I";
	case "N":
	    return "B";
	case "O":
	    return "S";
	case "P":
	    return "W";
	case "Q":
	    return "Y";
	case "R":
	    return "G";
	case "S":
	    return "C";
	case "T":
	    return "P";
	case "U":
	    return "L";
	case "V":
	    return "M";
	case "W":
	    return "T";
	case "Y":
	    return "H";
	default:
	    return "Invalid sticker";
	}
    }
    public int numCornersNotSolved()
    {
	int notSolved = 0;
	if(checkAFK()!=SOLVED)
	    notSolved++;
	if(checkBJN()!=SOLVED)
	    notSolved++;
	if(checkCOS()!=SOLVED)
	    notSolved++;
	if(checkDGR()!=SOLVED)
	    notSolved++;
	if(checkELU()!=SOLVED)
	    notSolved++;
	if(checkIMV()!=SOLVED)
	    notSolved++;
	if(checkPTW()!=SOLVED)
	    notSolved++;
	if(checkHQY()!=SOLVED)
	    notSolved++;
	return notSolved++;
    }    

    private boolean isCornersSolved()
    {
	if(checkAFK()==SOLVED
	   &&checkBJN()==SOLVED 
	   &&checkCOS()==SOLVED 
	   &&checkDGR()==SOLVED 
	   &&checkELU()==SOLVED 
	   &&checkIMV()==SOLVED 
	   &&checkPTW()==SOLVED 
	   &&checkHQY()==SOLVED)         
	{
	    return true;
	}
	return false;
    }

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
    public void fixTwistedCorners()
    {
	//afk,bjn,cos,dgr,elu,imv,ptw,hqy
	if(checkAFK() == CW || checkAFK() == CCW)
	{
	    cornerMap.put("A","A");
	    cornerMap.put("F","F");
	    cornerMap.put("K","K");
	}
	if(checkBJN() == CW || checkBJN() == CCW)
	{
	    cornerMap.put("B","B");
	    cornerMap.put("J","J");
	    cornerMap.put("N","N");
	}
	//the last target will be incorrect if there is a cycle break on a twisted corner during a parity case
	if(!hasParity())
	{
	    if(checkCOS() == CW || checkCOS() == CCW)
	    {
		cornerMap.put("C","C");
		cornerMap.put("O","O");
		cornerMap.put("S","S");
	    }
	}
	if(checkDGR() == CW || checkDGR() == CCW)
	{
	    cornerMap.put("D","D");
	    cornerMap.put("G","G");
	    cornerMap.put("R","R");
	}
	if(checkELU() == CW || checkELU() == CCW)
	{
	    cornerMap.put("E","E");
	    cornerMap.put("L","L");
	    cornerMap.put("U","U");
	}
	if(checkIMV() == CW || checkIMV() == CCW)
	{
	    cornerMap.put("I","I");
	    cornerMap.put("M","M");
	    cornerMap.put("V","V");
	}
	if(checkPTW() == CW || checkPTW() == CCW)
	{
	    cornerMap.put("P","P");
	    cornerMap.put("T","T");
	    cornerMap.put("W","W");
	}
	if(checkHQY() == CW || checkHQY() == CCW)
	{
	    cornerMap.put("H","H");
	    cornerMap.put("Q","Q");
	    cornerMap.put("Y","Y");
	}
    }
    public int checkAFK()
    {
	if(cornerMap.get("A").equals("A")
	   && cornerMap.get("F").equals("F")
	   && cornerMap.get("K").equals("K"))
	{
	    return SOLVED;
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
	return UNSOLVED;
    }

    public int checkBJN()
    {
	if(cornerMap.get("B").equals("B")
	   && cornerMap.get("J").equals("J")
	   && cornerMap.get("N").equals("N"))
	{
	    return SOLVED;
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
	return UNSOLVED;
    }

    public int checkCOS()
    {
	if(cornerMap.get("C").equals("C")
	   && cornerMap.get("O").equals("O")
	   && cornerMap.get("S").equals("S"))
	{
	    return SOLVED;
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
	return UNSOLVED;
    }
 
    public int checkDGR()
    {
	if(cornerMap.get("D").equals("D")
	   && cornerMap.get("G").equals("G")
	   && cornerMap.get("R").equals("R"))
	{
	    return SOLVED;
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
	return UNSOLVED;
    }

    public int checkELU()
    {
	if(cornerMap.get("E").equals("E")
	   && cornerMap.get("L").equals("L")
	   && cornerMap.get("U").equals("U"))
	{
	    return SOLVED;
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
	return UNSOLVED;
    }

    public int checkIMV()
    {
	if(cornerMap.get("I").equals("I")
	   && cornerMap.get("M").equals("M")
	   && cornerMap.get("V").equals("V"))
	{
	    return SOLVED;
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
	return UNSOLVED;
    }

    public int checkPTW()
    {
	if(cornerMap.get("P").equals("P")
	   && cornerMap.get("T").equals("T")
	   && cornerMap.get("W").equals("W"))
	{
	    return SOLVED;
	}
	if(cornerMap.get("P").equals("W")
	   && cornerMap.get("T").equals("P")
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
	return UNSOLVED;
    }

    public int checkHQY()
    {
	if(cornerMap.get("H").equals("H")
	   && cornerMap.get("Q").equals("Q")
	   && cornerMap.get("Y").equals("Y"))
	{
	    return SOLVED;
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
	return UNSOLVED;
    }

}

