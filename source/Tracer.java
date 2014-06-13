import java.util.*;
/* Traces the cube's edges and corners via LinkedHashMaps
 * Angel Lim
 * 
 * The point of this class is to have two HashMaps- one for edges and one for corners, and use them to track every sticker's position
 * on the cube given a standard String scramble.
 */
public class Tracer
{
    protected static final int FLIPPED = 1;
    protected static final int SOLVED = 0;
    protected static final int UNSOLVED = 2;
    protected static final int CW = 1;
    protected static final int CCW = -1;

    protected String scramble;  
    protected boolean hasParity;

    protected Map<String,String> edgeMap;
    protected Map<String,String> cornerMap;
    //used as temp Maps for when the cube states needs to be updated.
    private Map<String,String> newEdgeMap;
    private Map<String,String> newCornerMap;
    
    public Tracer(String inputtedScramble)
    {
	scramble = inputtedScramble;
	genFreshCube();
	scrambleToTurns(inputtedScramble);
	hasParity = Tracer.hasParity(inputtedScramble);
    }
    public String getScramble()
    {
	return scramble;
    }

    //methods used to get the Edge and Corner maps
    public LinkedHashMap<String,String> getEdgeMap()
    {
        return (LinkedHashMap<String,String>)edgeMap;
    }
    public LinkedHashMap<String,String> getCornerMap()
    {
        return (LinkedHashMap<String,String>)cornerMap;
    }
    
    //takes a String scramble and modifies the Maps
    public void scrambleToTurns(String scramble)
    {
        scramble=scramble.trim();
        scramble=scramble+" ";
        ArrayList<String> moves = new ArrayList<String>();
        for(int x=0;x<scramble.length();x++)
        {
            String move = "";
            while(!(Character.toString(scramble.charAt(x)).equals(" ")))
            {
                
                move = move + Character.toString(scramble.charAt(x));
                x++;
            }
            moves.add(move);
            
        }
        
        for(int x=0;x<moves.size();x++)
        {
            
            switch(moves.get(x))
            {
                case "U":
                    turnU();
                    break;
                case "U'":
                    turnUp();
                    break;
                case "U2":
                    turnU2();
                    break;
                case "D":
                    turnD();
                    break;
                case "D'":
                    turnDp();
                    break;
                case "D2":
                    turnD2();
                    break;
                case "L":
                    turnL();
                    break;
                case "L'":
                    turnLp();
                    break;
                case "L2":
                    turnL2();
                    break;
                case "R":
                    turnR();
                    break;
                case "R'":
                    turnRp();
                    break;
                case "R2":
                    turnR2();
                    break;
                case "F":
                    turnF();
                    break;
                case "F'":
                    turnFp();
                    break;
                case "F2":
                    turnF2();
                    break;
                case "B":
                    turnB();
                    break;
                case "B'":
                    turnBp();
                    break;
                case "B2":
                    turnB2();
                    break;
                default:
                    System.out.println("Something went wrong-ArrayList did not have a corresponding move");
                    break;
            }
        }
    }
    /*
     * genFreshCube() generates a fresh cube with default LinkedHashMaps, so A would correspond to A, B-B etc..
     */
    public void genFreshCube()
    {
        edgeMap = new LinkedHashMap<String,String>(); //Linked preserves the order...
        //lettering scheme for edges goes A-W, Y; skipping X and Z. using ascii values to gen. lol.
        for(int x=65;x<=87;x++)
        {
            String temp = Character.toString((char)x);
            edgeMap.put(temp,temp);
        }
        edgeMap.put("Y","Y");
        
        //separating edges and corners just for clarity, I know it could have been done in 1 loop
        cornerMap = new LinkedHashMap<String,String>();
        for(int x=65;x<=87;x++)
        {
            String temp = Character.toString((char)x);
            cornerMap.put(temp,temp);
        }
        cornerMap.put("Y","Y");
        
        newEdgeMap= new LinkedHashMap<String,String>();
        newCornerMap = new LinkedHashMap<String,String>();
    }
    /*
     * Especially in early stages of tracing, LinkedHashMap edges and cornerMap do not have fully
     * updated states with all edges and corners. This can lead to complicated null values.
     * To avoid this, this method is called at the end of a new move to prevent that.
     * NOTE: order will end up not being preserved. I can try to preserve it in the future-
     * hence the point of LinkedHashMaps.
     */
    public void genMissingCubiesToDefault()
    {
        //gen missing edges
        for(int x=65;x<=87;x++)
        {
            String temp = Character.toString((char)x);
            if(!edgeMap.containsKey(temp))
            {
                System.out.print("it doesn't contain: "+ temp+ ", ");
                edgeMap.put(temp,temp);
            }
        }
        if(!edgeMap.containsKey("Y"))
        {
            edgeMap.put("Y","Y");
        }
        
        //gen missing corners
        for(int x=65;x<=87;x++)
        {
            String temp = Character.toString((char)x);
            if(!cornerMap.containsKey(temp))
            {
                cornerMap.put(temp,temp);
            }
            
        }
        if(!cornerMap.containsKey("Y"))
        {
            cornerMap.put("Y","Y");
        }
    }
    /*
     * After changes are reflected in newedges/newCornerMap, these changes must be reflected in edges/cornerMap.
     * This method does just that.
     * 
     * 
     */
    public void updateEdgesCornerMap()
    {
        for(Map.Entry temp : newEdgeMap.entrySet())
        {
            edgeMap.put((String)temp.getKey(),(String)temp.getValue());
        }
        for(Map.Entry temp : newCornerMap.entrySet())
        {
            cornerMap.put((String)temp.getKey(),(String)temp.getValue());
        }
    }
    public void turnU()
    {
        newEdgeMap= new LinkedHashMap<String,String>();
        
        //tracking the edges
        //top-layer-edge changes
        newEdgeMap.put("A",edgeMap.get("B"));
        newEdgeMap.put("B",edgeMap.get("C"));
        newEdgeMap.put("C",edgeMap.get("D"));
        newEdgeMap.put("D",edgeMap.get("A"));
        //"side"-layer-edge changes
        newEdgeMap.put("G",edgeMap.get("K"));
        newEdgeMap.put("K",edgeMap.get("O"));
        newEdgeMap.put("O",edgeMap.get("S"));
        newEdgeMap.put("S",edgeMap.get("G"));
        
        
        newCornerMap = new LinkedHashMap<String,String>();
        //tracking the cornerMap
        //top-layer-corner changes
        newCornerMap.put("A",cornerMap.get("B"));
        newCornerMap.put("B",cornerMap.get("C"));
        newCornerMap.put("C",cornerMap.get("D"));
        newCornerMap.put("D",cornerMap.get("A"));
        //"leftside"-cornerMap
        newCornerMap.put("F",cornerMap.get("J"));
        newCornerMap.put("J",cornerMap.get("O"));
        newCornerMap.put("O",cornerMap.get("R"));
        newCornerMap.put("R",cornerMap.get("F"));
        //"rightside"cornerMap
        newCornerMap.put("K",cornerMap.get("N"));
        newCornerMap.put("N",cornerMap.get("S"));
        newCornerMap.put("S",cornerMap.get("G"));
        newCornerMap.put("G",cornerMap.get("K"));
        
        
        
        updateEdgesCornerMap();
        genMissingCubiesToDefault();
    }
    
    public void turnD()
    {
        newEdgeMap= new LinkedHashMap<String,String>();
        
        //tracking the edges
        //bottom-edges
        newEdgeMap.put("U",edgeMap.get("Y"));
        newEdgeMap.put("V",edgeMap.get("U"));
        newEdgeMap.put("W",edgeMap.get("V"));
        newEdgeMap.put("Y",edgeMap.get("W"));
        //"outer"-edgeMap
        newEdgeMap.put("E",edgeMap.get("Q"));
        newEdgeMap.put("I",edgeMap.get("E"));
        newEdgeMap.put("M",edgeMap.get("I"));
        newEdgeMap.put("Q",edgeMap.get("M"));
        

        
        newCornerMap = new LinkedHashMap<String,String>();
        //tracking the cornerMap
        //bottom cornerMap
        newCornerMap.put("U",cornerMap.get("Y"));
        newCornerMap.put("V",cornerMap.get("U"));
        newCornerMap.put("W",cornerMap.get("V"));
        newCornerMap.put("Y",cornerMap.get("W"));
        //"left"-side cornerMap
        newCornerMap.put("E",cornerMap.get("Q"));
        newCornerMap.put("I",cornerMap.get("E"));
        newCornerMap.put("P",cornerMap.get("I"));
        newCornerMap.put("Q",cornerMap.get("P"));
        //
        newCornerMap.put("L",cornerMap.get("H"));
        newCornerMap.put("M",cornerMap.get("L"));
        newCornerMap.put("T",cornerMap.get("M"));
        newCornerMap.put("H",cornerMap.get("T")); 
        

        updateEdgesCornerMap();
        genMissingCubiesToDefault();
    }
    
    public void turnL()
    {
        newEdgeMap = new LinkedHashMap<String,String>();
        //tracking the edges
        //outer-edges
        newEdgeMap.put("D",edgeMap.get("P"));
        newEdgeMap.put("H",edgeMap.get("D"));
        newEdgeMap.put("Y",edgeMap.get("H"));
        newEdgeMap.put("P",edgeMap.get("Y"));
        //L-side-edgeMap
        newEdgeMap.put("S",edgeMap.get("T"));
        newEdgeMap.put("R",edgeMap.get("S"));
        newEdgeMap.put("Q",edgeMap.get("R"));
        newEdgeMap.put("T",edgeMap.get("Q"));
        

        
        newCornerMap = new LinkedHashMap<String,String>();
        
        //tracking the cornerMap
        //tops-bots
        newCornerMap.put("C",cornerMap.get("P"));
        newCornerMap.put("D",cornerMap.get("O"));
        newCornerMap.put("W",cornerMap.get("H"));
        newCornerMap.put("Y",cornerMap.get("G"));
        //L-side
        newCornerMap.put("S",cornerMap.get("T"));
        newCornerMap.put("R",cornerMap.get("S"));
        newCornerMap.put("Q",cornerMap.get("R"));
        newCornerMap.put("T",cornerMap.get("Q"));
        //fronts-backs
        newCornerMap.put("G",cornerMap.get("C"));
        newCornerMap.put("H",cornerMap.get("D"));
        newCornerMap.put("O",cornerMap.get("W"));
        newCornerMap.put("P",cornerMap.get("Y")); 
        

        updateEdgesCornerMap();
        genMissingCubiesToDefault();
    }
    public void turnR()
    {
        newEdgeMap= new LinkedHashMap<String,String>();
        
        //tracking the edges
        //outer-edges
        newEdgeMap.put("B",edgeMap.get("F"));
        newEdgeMap.put("F",edgeMap.get("V"));
        newEdgeMap.put("V",edgeMap.get("N"));
        newEdgeMap.put("N",edgeMap.get("B"));
        //R-slice-edges
        newEdgeMap.put("K",edgeMap.get("L"));
        newEdgeMap.put("J",edgeMap.get("K"));
        newEdgeMap.put("I",edgeMap.get("J"));
        newEdgeMap.put("L",edgeMap.get("I"));
        
       
        
        newCornerMap = new LinkedHashMap<String,String>();
        //tracking the cornerMap
        //tops-bots
        newCornerMap.put("A",cornerMap.get("E"));
        newCornerMap.put("B",cornerMap.get("F"));
        newCornerMap.put("U",cornerMap.get("M"));
        newCornerMap.put("V",cornerMap.get("N"));
        //R-side
        newCornerMap.put("K",cornerMap.get("L"));
        newCornerMap.put("J",cornerMap.get("K"));
        newCornerMap.put("I",cornerMap.get("J"));
        newCornerMap.put("L",cornerMap.get("I"));
        //fronts-backs
        newCornerMap.put("E",cornerMap.get("V"));
        newCornerMap.put("F",cornerMap.get("U"));
        newCornerMap.put("M",cornerMap.get("B"));
        newCornerMap.put("N",cornerMap.get("A")); 
        
        updateEdgesCornerMap();
        
        genMissingCubiesToDefault();
    }
    
    public void turnF()
    {
        newEdgeMap = new LinkedHashMap<String,String>();
        //tracking the edges
        //outer
        newEdgeMap.put("A",edgeMap.get("R"));
        newEdgeMap.put("R",edgeMap.get("U"));
        newEdgeMap.put("U",edgeMap.get("L"));
        newEdgeMap.put("L",edgeMap.get("A"));
        //F-face
        newEdgeMap.put("E",edgeMap.get("F"));
        newEdgeMap.put("F",edgeMap.get("G"));
        newEdgeMap.put("G",edgeMap.get("H"));
        newEdgeMap.put("H",edgeMap.get("E"));
        
        
        newCornerMap = new LinkedHashMap<String,String>();
        
        //tracking the cornerMap
        //tops-bots
        newCornerMap.put("A",cornerMap.get("R"));
        newCornerMap.put("D",cornerMap.get("Q"));
        newCornerMap.put("U",cornerMap.get("K"));
        newCornerMap.put("Y",cornerMap.get("L"));
        //L-R
        newCornerMap.put("K",cornerMap.get("D"));
        newCornerMap.put("L",cornerMap.get("A"));
        newCornerMap.put("R",cornerMap.get("Y"));
        newCornerMap.put("Q",cornerMap.get("U"));
        //F-face
        newCornerMap.put("E",cornerMap.get("F"));
        newCornerMap.put("F",cornerMap.get("G"));
        newCornerMap.put("G",cornerMap.get("H"));
        newCornerMap.put("H",cornerMap.get("E")); 
        
        updateEdgesCornerMap();
        
        genMissingCubiesToDefault();
    }
    
    public void turnB()
    {
        newEdgeMap = new LinkedHashMap<String,String>();
        //tracking the edges
        //outer
        newEdgeMap.put("C",edgeMap.get("J"));
        newEdgeMap.put("T",edgeMap.get("C"));
        newEdgeMap.put("W",edgeMap.get("T"));
        newEdgeMap.put("J",edgeMap.get("W"));
        //B-face
        newEdgeMap.put("M",edgeMap.get("P"));
        newEdgeMap.put("N",edgeMap.get("M"));
        newEdgeMap.put("O",edgeMap.get("N"));
        newEdgeMap.put("P",edgeMap.get("O"));
        
        
        newCornerMap = new LinkedHashMap<String,String>();
        
        //tracking the cornerMap
        //top-bot
        newCornerMap.put("B",cornerMap.get("I"));
        newCornerMap.put("C",cornerMap.get("J"));
        newCornerMap.put("V",cornerMap.get("T"));
        newCornerMap.put("W",cornerMap.get("S"));
        //left-right
        newCornerMap.put("I",cornerMap.get("W"));
        newCornerMap.put("J",cornerMap.get("V"));
        newCornerMap.put("S",cornerMap.get("B"));
        newCornerMap.put("T",cornerMap.get("C"));
        //b-face
        newCornerMap.put("M",cornerMap.get("P"));
        newCornerMap.put("N",cornerMap.get("M"));
        newCornerMap.put("O",cornerMap.get("N"));
        newCornerMap.put("P",cornerMap.get("O")); 
        
        updateEdgesCornerMap();
        genMissingCubiesToDefault();
    }
    public void turnUp()
    {
        turnU();turnU();turnU();
    }
    public void turnDp()
    {
        turnD();turnD();turnD();
    }
    public void turnLp()
    {
        turnL();turnL();turnL();
    }
    public void turnRp()
    {
        turnR();turnR();turnR();
    }
    public void turnFp()
    {
        turnF();turnF();turnF();
    }
    public void turnBp()
    {
        turnB();turnB();turnB();
    }
    
    
    public void turnU2()
    {
        turnU();turnU();
    }
    public void turnD2()
    {
        turnD();turnD();
    }
    public void turnL2()
    {
        turnL();turnL();
    }
    public void turnR2()
    {
        turnR();turnR();
    }
    public void turnF2()
    {
        turnF();turnF();
    }
    public void turnB2()
    {
        turnB();turnB();
    }




    public static boolean hasParity(String testScramble)
    {
        testScramble = testScramble.trim();
        testScramble = testScramble + " ";
        String currentTwist = "";
        int loc = 0;
        int parityCounter = 0;
        while (loc < testScramble.length())
        {
            while(testScramble.charAt(loc)!=' ')
            {
                currentTwist = currentTwist + testScramble.charAt(loc);
                loc++;
            }
            if(parityContributor(currentTwist))
            {
                parityCounter++;
            }
            currentTwist = "";
            if (testScramble.charAt(loc)== ' ')
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
    public boolean hasParity()
    {
        return Tracer.hasParity(scramble);
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

    /*
     * printEverything and checkTurns are mainly used by me for debugging.
     */
    public void printEverything()
    {
        System.out.println("edges- "+edgeMap);
        System.out.println("cornerMap- "+cornerMap);
    }
    public void checkTurns()
    {
        for (int x = 1;x<=6;x++)
        {
            turnBp();
            turnRp();
            turnB();
            turnR();
            System.out.println(x);
            printEverything();
        }
        System.out.println("----");
        printEverything();
        
    }
        
        
}
