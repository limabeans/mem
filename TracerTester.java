public class TracerTester
{
    public static void main(String[] args)
    {
	boolean desire = true;
	Scrambler scrambler = new Scrambler();
	String testscramble = scrambler.genScramble();
	CheckTwistedCorners ctc = new CheckTwistedCorners(testscramble);
	CheckFlippedEdges cfe = new CheckFlippedEdges(testscramble);
	while(desire)
	{
	    testscramble=scrambler.genScramble();
	    ctc.setScramble(testscramble);
	    cfe.setScramble(testscramble);
	    if(cfe.hasFlippedEdges()&&ctc.hasTwistedCorners())
	    {
		desire = false;
	    }
	}
	ScrambleTracer scrambleTracer = new ScrambleTracer(testscramble);

	System.out.println(testscramble);

	System.out.println("maps BEFORE scramble is applied-");
	System.out.println("(everything should be matching up)");
	scrambleTracer.printEverything();
	System.out.println("-------------------------");

      	scrambleTracer.scrambleToTurns(testscramble);

	System.out.println("maps AFTER the scramble is appied-");
	scrambleTracer.printEverything();

    }
}
