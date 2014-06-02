//returns scrambles in String format
//scrambles are not truly random (Math.random() seeded)..

public class Scrambler
{
    static String[] moves = {"F","R","B","L","U","D" };
    static String[] note = {" ", "2 ", "' "};

    public static void main(String[] args)
    {
	Scrambler tester = new Scrambler();
	System.out.println(tester.genScramble());
    }
    /*
     *Call this method if you want a scramble in String format.
     */
    public static String genEasyScramble()
    {
	Scrambler scrambler = new Scrambler();
	String testScramble = scrambler.genScramble();
	Tracer tracer = new Tracer(testScramble);
	EdgeTracer edgeTracer = new EdgeTracer(testScramble);
	CornerTracer cornerTracer = new CornerTracer(testScramble);

	while(tracer.hasParity() || edgeTracer.hasFlippedEdges() || cornerTracer.hasTwistedCorners())
	{
	    testScramble = scrambler.genScramble();
	    tracer = new Tracer(testScramble);
	    edgeTracer = new EdgeTracer(testScramble);
	    cornerTracer = new CornerTracer(testScramble);

	}
	return testScramble;
    }
    public static String genScramble()
    {
        String scramble = "";
        scramble = scramble + moves[(int)(Math.random()*moves.length)] + note[(int)(Math.random()*note.length)];
        String prevMove = scramble;
        String nextMove = "";
        for (int x = 2; x<=21;x++)
        {
            nextMove = nextMove(prevMove);
            scramble = scramble + nextMove;
            prevMove = nextMove;
        }
        return scramble.trim();
    }
    
    /*
     *Generates the next "random" move
     */

    public static String nextMove(String prev)
    {
        String move = "";
        int index = (int)(Math.random()*moves.length);
        while(moves[index].charAt(0)==prev.charAt(0))
        {
            index = (int)(Math.random()*moves.length);
        }
        move = moves[index] + note[(int)(Math.random()*note.length)];
        return move;
        
    }
    
    
    
}
