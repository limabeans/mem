import java.util.*;
public class EdgeCommSolver extends Tracer 
{
    public EdgeCommSolver(String scramble)
    {
	super(scramble);
    }
    //currently dealing with scrambles with no flipped edges
    //and no parity

    public void solveEdges()
    {
	while(!isEdgesSolved())
	{
	    System.out.println("---------------------------------------------------------");
	    solveNextEdgeComm();
	    System.out.println("---------------------------------------------------------");
	}

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
    public void solveNextEdgeComm()
    {
	String[][] afterBuffer = determineNextTarget("U","E");
	//System.out.println("afterBuffer-" + afterBuffer[0][0]+"-"+afterBuffer[0][1]+"/"+afterBuffer[1][0]+"-"+afterBuffer[1][1]);
	String[][] afterFirstTarget = determineNextTarget(afterBuffer[0][0],afterBuffer[1][0]);
	//System.out.println("afterFirstTarget-" + afterFirstTarget[0][0]+"-"+afterFirstTarget[0][1]+"/"+afterFirstTarget[1][0]+"-"+afterFirstTarget[1][1]);
	//insert into firstTarget
	edgeMap.put(afterBuffer[0][0], edgeMap.get("U")); edgeMap.put(afterBuffer[1][0], edgeMap.get("E"));
	//insert into lastTarget
	edgeMap.put(afterFirstTarget[0][0],afterBuffer[0][1]); edgeMap.put(afterFirstTarget[1][0],afterBuffer[1][1]);
	//insert into buffer
	edgeMap.put("U",afterFirstTarget[0][1]); edgeMap.put("E",afterFirstTarget[1][1]);

	printEverything();

    }

    public String[][] determineNextTarget(String loc1, String loc2)
    {
	String[][] nextTarget = new String[2][2];
	//[0][0]-location, [0][1]-initial value
	//[1][0]-location of other sticker, [1][1]-initial value at other sticker
	//based on current edgeMap
	if(edgeMap.get(loc1).equals("U")||edgeMap.get(loc1).equals("E")) //requires cycle break
	{
	    System.out.println("about to call find cycle break");
	    nextTarget[0][0] = findEdgeCycleBreak(loc1);
	    System.out.println("just called find cycle break");
	    nextTarget[0][1] = edgeMap.get(nextTarget[0][0]);
	    nextTarget[1][0] = returnOtherSticker(nextTarget[0][0]);
	    nextTarget[1][1] = edgeMap.get(nextTarget[1][0]); 
	}
	else
	{
	    nextTarget[0][0] = edgeMap.get(loc1);
	    nextTarget[0][1] = edgeMap.get(nextTarget[0][0]);
	    nextTarget[1][0] = edgeMap.get(loc2);
	    nextTarget[1][1] = edgeMap.get(nextTarget[1][0]);
	}
	return nextTarget;
	
    }
    public String findEdgeCycleBreak(String blacklist)
    {
	System.out.println("blacklist: " + blacklist);
	for(Map.Entry<String,String> entry : edgeMap.entrySet())
	{
	    String currentKey = entry.getKey();
	    String currentValue = entry.getValue();
	    if((currentKey.equals("U")) || (currentKey.equals("E")))
	    {
		//don't put it at its buffer spot!
	    }
	    else if(currentKey.equals(blacklist) || currentKey.equals(returnOtherSticker(blacklist)) )
	    {
		//don't pick yourself as a break!
	    }
	    else if(!(currentKey.equals(currentValue)))
	    {
		System.out.println("CURRENTKEY USED (BREAK TO) " + currentKey);
		return currentKey;
	    }
	    
	}
	return "Something went wrong";
    }

    public String returnOtherSticker(String firstSticker)
    {
	switch(firstSticker){
	case "A":
	    return "G";
	case "B":
	    return "K";
	case "C":
	    return "O";
	case "D":
	    return "S";
	case "E":
	    return "U";
	case "F":
	    return "L";
	case "G":
	    return "A";
	case "H":
	    return "R";
	case "I":
	    return "V";
	case "J":
	    return "N";
	case "K":
	    return "B";
	case "L":
	    return "F";
	case "M":
	    return "W";
	case "N":
	    return "J";
	case "O":
	    return "C";
	case "P":
	    return "T";
	case "Q":
	    return "Y";
	case "R":
	    return "H";
	case "S":
	    return "D";
	case "T":
	    return "P";
	case "U":
	    return "E";
	case "V":
	    return "I";
	case "W":
	    return "M";
	case "Y":
	    return "Q";
	default:
	    return "Invalid sticker";
	}
    }
}
    

