public class TracerTester
{
    public static void main(String[] args)
    {
	Scrambler scrambler = new Scrambler();
	String testScramble = scrambler.genScramble();
	Tracer tracer = new EdgeCommSolver(testScramble);
	//will only generate easy scrambles
	while(tracer.hasParity() || tracer.hasFlippedEdges() || tracer.hasTwistedCorners())
	{
	    testScramble = scrambler.genScramble();
	    tracer = new EdgeCommSolver(testScramble);
	}
	System.out.println(tracer.getScramble());

	tracer.printEverything();

	((EdgeCommSolver)tracer).solveEdges();
	



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
