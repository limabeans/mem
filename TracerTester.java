public class TracerTester
{
    public static void main(String[] args)
    {
	boolean desire = true;
	Scrambler scrambler = new Scrambler();
	String testscramble = scrambler.genScramble();
	Tracer tracer = new Tracer(testscramble);
	tracer.printEverything();
	while(!(tracer.hasFlippedEdges()&&tracer.hasTwistedCorners()))
	{
	    System.out.println("fail");
	    testscramble = scrambler.genScramble();
	    tracer = new Tracer(testscramble);


	}

	System.out.println(testscramble);
	/*
	System.out.println("maps BEFORE scramble is applied-");
	System.out.println("(everything should be matching up)");
	tracer.printEverything();
	System.out.println("-------------------------");

      	tracer.scrambleToTurns(testscramble);

	System.out.println("maps AFTER the scramble is appied-");
	tracer.printEverything();*/

    }
}
