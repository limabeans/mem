import java.util.*;

public class CornerTracer extends Tracer
{
    private int numCornerIterations;
    private String cornerLetterSequence;
    private boolean requiredCornerCycleBreak;
    private int numCornerCycleBreaks;
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
	return String.format("NO TWISTED CORNERS\nNO PARITY\nNumber of corner comms used: %s\nCorner letter sequence %s\nNumber of corner cycle breaks: %d",numCornerIterations,cornerLetterSequence,numCornerCycleBreaks);
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


}

