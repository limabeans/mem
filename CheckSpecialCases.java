/*
 *Special cases include:
 *1. Flipped Edges- requires the LinkedHashMaps
 *2. Twisted Corners- requires the LinkedHashMaps
 *3. Parity- requires String scramble
 */
import java.util.*;
public class CheckSpecialCases
{
    protected static final int PERMED = 0;
    protected static final int UNORIENTED = 2;

    protected Tracer tracer;
    protected String scramble;
    protected LinkedHashMap<String,String> edgeMap;
    protected LinkedHashMap<String,String> cornerMap;

    public static void main(String[] args)
    {
	Scrambler scrambler = new Scrambler();
	CheckSpecialCases test = new CheckSpecialCases(scrambler.genScramble());
	System.out.println(test);
    }

    public CheckSpecialCases(String inputtedScramble)
    {
	scramble = inputtedScramble;
	tracer = new Tracer(inputtedScramble);
	edgeMap = tracer.getEdgeMap();
	cornerMap = tracer.getCornerMap();
    }
    public void setScramble(String newScramble)
    {
	scramble=newScramble;
	tracer = new Tracer(scramble);
	edgeMap = tracer.getEdgeMap();
	cornerMap = tracer.getCornerMap();
    }
    public String getScramble()
    {
	return scramble;
    }
    public String toString()
    {
	return "broken";
    }

 
    //3. PARITY
    public static boolean hasParity(String scramble)
    {
        scramble = scramble.trim();
        scramble = scramble + " ";
        String currentTwist = "";
        int loc = 0;
        int parityCounter = 0;
        while (loc < scramble.length())
        {
            while(scramble.charAt(loc)!=' ')
            {
                currentTwist = currentTwist + scramble.charAt(loc);
                loc++;
            }
            if(parityContributor(currentTwist))
            {
                parityCounter++;
            }
            currentTwist = "";
            if (scramble.charAt(loc)== ' ')
            {
                loc++;
            }
        }
        if (parityCounter%2==1)
        {
            return true;
        }
        return false;        
    }
    //helper method for checkParity()
    public static boolean parityContributor(String input)
    {
        if(input.length()==0)
        {
            return false;
        }
        else if(input.length()==1)
        {
            return true;
        }
        else if (input.charAt(1)=='\'')
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
