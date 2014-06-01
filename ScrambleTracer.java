import java.util.*;
/* Traces the cube's edges and corners via LinkedHashMaps
 * Angel Lim
 * 
 * The point of this class is to have two HashMaps- one for edges and one for corners, and use them to track every sticker's position
 * on the cube given a standard String scramble.
 * For now for my convenience I will just be using my own lettering scheme.
 * The Key will have the letter that "should" be in the correct position,
 * and the Value will have what is "actually" there after a turn.
 * I will program each turn manually (UDFRLB) super inefficient I know! and have double turns 
 * be two iteration of one of those commands, and ccw turns be three iterations. So every turn will
 * modify the edges and corners HashMaps instance variables.
 * This program will take a String scramble and return two HashMaps of where the edges and corners end up.
 * I used <String,String> instead of characters just in case I wanted to support edge/corner notation
 * in the future (UF/UBL) kind of stuff, so I wouldn't have to change "" to ''. I guess I could have done
 * some grep linux stuff to fix that easily, but whatever.
 * 
 * Get ready for some really inefficient/bad code, but it works. Definitely not reuseable (since I hard encoded my letteringscheme)
 * ,unless I do some regexping in the future.
 * 
 * Minor note: left and right sides when referring to corners: I am referring to the left and right sides relative to the corner pieces themselves,
 * NOT to relative to the faces.
 */
public class ScrambleTracer
{
    private Map<String,String> edges;
    private Map<String,String> corners;
    //used as temp Maps for when the cube states needs to be updated.
    //I don't really think these are necessary as instance variables, but it works.
    private Map<String,String> newedges;
    private Map<String,String> newcorners;
    
    public ScrambleTracer()
    {
        genFreshCube();
       
    }
    /*
     * printEverything and checkTurns are mainly used by me for debugging.
     */
    public void printEverything()
    {
        System.out.println("edges- "+edges);
        System.out.println("corners- "+corners);
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
    //methods used to get the Edge and Corner maps
    public LinkedHashMap<String,String> getEdgesMap()
    {
        return (LinkedHashMap<String,String>)edges;
    }
    public LinkedHashMap<String,String> getCornersMap()
    {
        return (LinkedHashMap<String,String>)corners;
    }
    
    /*
     * Takes a String scramble and then converts this to moves. So something like L2 would be converted to turnL2(), etc.
     * I'm not that good at manipulate String input so I do this in sort of roundabout way.
     * 
     * Originally I was considering generating scrambles randomly with my methods (like turnU turnF turnRp etc) but then I realized that
     * my string Scrambler as it is is pretty inefficient. If in the future I can just use someone else's who provides truly random
     * String scrambles, I could just plug their class into my program without everything breaking.
     */
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
        edges = new LinkedHashMap<String,String>(); //Linked preserves the order...
        //lettering scheme for edges goes A-W, Y; skipping X and Z. using ascii values to gen. lol.
        for(int x=65;x<=87;x++)
        {
            String temp = Character.toString((char)x);
            edges.put(temp,temp);
        }
        edges.put("Y","Y");
        
        //separating edges and corners just for clarity, I know it could have been done in 1 loop
        corners = new LinkedHashMap<String,String>();
        for(int x=65;x<=87;x++)
        {
            String temp = Character.toString((char)x);
            corners.put(temp,temp);
        }
        corners.put("Y","Y");
        
        newedges= new LinkedHashMap<String,String>();
        newcorners = new LinkedHashMap<String,String>();
    }
    /*
     * Especially in early stages of tracing, LinkedHashMap edges and corners do not have fully
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
            if(!edges.containsKey(temp))
            {
                System.out.print("it doesn't contain: "+ temp+ ", ");
                edges.put(temp,temp);
            }
        }
        if(!edges.containsKey("Y"))
        {
            edges.put("Y","Y");
        }
        
        //gen missing corners
        for(int x=65;x<=87;x++)
        {
            String temp = Character.toString((char)x);
            if(!corners.containsKey(temp))
            {
                corners.put(temp,temp);
            }
            
        }
        if(!corners.containsKey("Y"))
        {
            corners.put("Y","Y");
        }
    }
    /*
     * After changes are reflected in newedges/newcorners, these changes must be reflected in edges/corners.
     * This method does just that.
     * 
     * 
     */
    public void updateEdgesCorners()
    {
        for(Map.Entry temp : newedges.entrySet())
        {
            edges.put((String)temp.getKey(),(String)temp.getValue());
        }
        for(Map.Entry temp : newcorners.entrySet())
        {
            corners.put((String)temp.getKey(),(String)temp.getValue());
        }
    }
    public void turnU()
    {
        newedges= new LinkedHashMap<String,String>();
        
        //tracking the edges
        //top-layer-edge changes
        newedges.put("A",edges.get("B"));
        newedges.put("B",edges.get("C"));
        newedges.put("C",edges.get("D"));
        newedges.put("D",edges.get("A"));
        //"side"-layer-edge changes
        newedges.put("G",edges.get("K"));
        newedges.put("K",edges.get("O"));
        newedges.put("O",edges.get("S"));
        newedges.put("S",edges.get("G"));
        
        
        newcorners = new LinkedHashMap<String,String>();
        //tracking the corners
        //top-layer-corner changes
        newcorners.put("A",corners.get("B"));
        newcorners.put("B",corners.get("C"));
        newcorners.put("C",corners.get("D"));
        newcorners.put("D",corners.get("A"));
        //"leftside"-corners
        newcorners.put("F",corners.get("J"));
        newcorners.put("J",corners.get("O"));
        newcorners.put("O",corners.get("R"));
        newcorners.put("R",corners.get("F"));
        //"rightside"corners
        newcorners.put("K",corners.get("N"));
        newcorners.put("N",corners.get("S"));
        newcorners.put("S",corners.get("G"));
        newcorners.put("G",corners.get("K"));
        
        
        
        updateEdgesCorners();
        genMissingCubiesToDefault();
    }
    
    public void turnD()
    {
        newedges= new LinkedHashMap<String,String>();
        
        //tracking the edges
        //bottom-edges
        newedges.put("U",edges.get("Y"));
        newedges.put("V",edges.get("U"));
        newedges.put("W",edges.get("V"));
        newedges.put("Y",edges.get("W"));
        //"outer"-edges
        newedges.put("E",edges.get("Q"));
        newedges.put("I",edges.get("E"));
        newedges.put("M",edges.get("I"));
        newedges.put("Q",edges.get("M"));
        

        
        newcorners = new LinkedHashMap<String,String>();
        //tracking the corners
        //bottom corners
        newcorners.put("U",corners.get("Y"));
        newcorners.put("V",corners.get("U"));
        newcorners.put("W",corners.get("V"));
        newcorners.put("Y",corners.get("W"));
        //"left"-side corners
        newcorners.put("E",corners.get("Q"));
        newcorners.put("I",corners.get("E"));
        newcorners.put("P",corners.get("I"));
        newcorners.put("Q",corners.get("P"));
        //
        newcorners.put("L",corners.get("H"));
        newcorners.put("M",corners.get("L"));
        newcorners.put("T",corners.get("M"));
        newcorners.put("H",corners.get("T")); 
        

        updateEdgesCorners();
        genMissingCubiesToDefault();
    }
    
    public void turnL()
    {
        newedges = new LinkedHashMap<String,String>();
        //tracking the edges
        //outer-edges
        newedges.put("D",edges.get("P"));
        newedges.put("H",edges.get("D"));
        newedges.put("Y",edges.get("H"));
        newedges.put("P",edges.get("Y"));
        //L-side-edges
        newedges.put("S",edges.get("T"));
        newedges.put("R",edges.get("S"));
        newedges.put("Q",edges.get("R"));
        newedges.put("T",edges.get("Q"));
        

        
        newcorners = new LinkedHashMap<String,String>();
        
        //tracking the corners
        //tops-bots
        newcorners.put("C",corners.get("P"));
        newcorners.put("D",corners.get("O"));
        newcorners.put("W",corners.get("H"));
        newcorners.put("Y",corners.get("G"));
        //L-side
        newcorners.put("S",corners.get("T"));
        newcorners.put("R",corners.get("S"));
        newcorners.put("Q",corners.get("R"));
        newcorners.put("T",corners.get("Q"));
        //fronts-backs
        newcorners.put("G",corners.get("C"));
        newcorners.put("H",corners.get("D"));
        newcorners.put("O",corners.get("W"));
        newcorners.put("P",corners.get("Y")); 
        

        updateEdgesCorners();
        genMissingCubiesToDefault();
    }
    public void turnR()
    {
        newedges= new LinkedHashMap<String,String>();
        
        //tracking the edges
        //outer-edges
        newedges.put("B",edges.get("F"));
        newedges.put("F",edges.get("V"));
        newedges.put("V",edges.get("N"));
        newedges.put("N",edges.get("B"));
        //R-slice-edges
        newedges.put("K",edges.get("L"));
        newedges.put("J",edges.get("K"));
        newedges.put("I",edges.get("J"));
        newedges.put("L",edges.get("I"));
        
       
        
        newcorners = new LinkedHashMap<String,String>();
        //tracking the corners
        //tops-bots
        newcorners.put("A",corners.get("E"));
        newcorners.put("B",corners.get("F"));
        newcorners.put("U",corners.get("M"));
        newcorners.put("V",corners.get("N"));
        //R-side
        newcorners.put("K",corners.get("L"));
        newcorners.put("J",corners.get("K"));
        newcorners.put("I",corners.get("J"));
        newcorners.put("L",corners.get("I"));
        //fronts-backs
        newcorners.put("E",corners.get("V"));
        newcorners.put("F",corners.get("U"));
        newcorners.put("M",corners.get("B"));
        newcorners.put("N",corners.get("A")); 
        
        updateEdgesCorners();
        
        genMissingCubiesToDefault();
    }
    
    public void turnF()
    {
        newedges = new LinkedHashMap<String,String>();
        //tracking the edges
        //outer
        newedges.put("A",edges.get("R"));
        newedges.put("R",edges.get("U"));
        newedges.put("U",edges.get("L"));
        newedges.put("L",edges.get("A"));
        //F-face
        newedges.put("E",edges.get("F"));
        newedges.put("F",edges.get("G"));
        newedges.put("G",edges.get("H"));
        newedges.put("H",edges.get("E"));
        
        
        newcorners = new LinkedHashMap<String,String>();
        
        //tracking the corners
        //tops-bots
        newcorners.put("A",corners.get("R"));
        newcorners.put("D",corners.get("Q"));
        newcorners.put("U",corners.get("K"));
        newcorners.put("Y",corners.get("L"));
        //L-R
        newcorners.put("K",corners.get("D"));
        newcorners.put("L",corners.get("A"));
        newcorners.put("R",corners.get("Y"));
        newcorners.put("Q",corners.get("U"));
        //F-face
        newcorners.put("E",corners.get("F"));
        newcorners.put("F",corners.get("G"));
        newcorners.put("G",corners.get("H"));
        newcorners.put("H",corners.get("E")); 
        
        updateEdgesCorners();
        
        genMissingCubiesToDefault();
    }
    
    public void turnB()
    {
        newedges = new LinkedHashMap<String,String>();
        //tracking the edges
        //outer
        newedges.put("C",edges.get("J"));
        newedges.put("T",edges.get("C"));
        newedges.put("W",edges.get("T"));
        newedges.put("J",edges.get("W"));
        //B-face
        newedges.put("M",edges.get("P"));
        newedges.put("N",edges.get("M"));
        newedges.put("O",edges.get("N"));
        newedges.put("P",edges.get("O"));
        
        
        newcorners = new LinkedHashMap<String,String>();
        
        //tracking the corners
        //top-bot
        newcorners.put("B",corners.get("I"));
        newcorners.put("C",corners.get("J"));
        newcorners.put("V",corners.get("T"));
        newcorners.put("W",corners.get("S"));
        //left-right
        newcorners.put("I",corners.get("W"));
        newcorners.put("J",corners.get("V"));
        newcorners.put("S",corners.get("B"));
        newcorners.put("T",corners.get("C"));
        //b-face
        newcorners.put("M",corners.get("P"));
        newcorners.put("N",corners.get("M"));
        newcorners.put("O",corners.get("N"));
        newcorners.put("P",corners.get("O")); 
        
        updateEdgesCorners();
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
    
        
        
}
