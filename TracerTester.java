public class TracerTester
{
    public static void main(String[] args)
    {
	Scrambler scrambler = new Scrambler();
	String testScramble = scrambler.genScramble();
	Tracer tracer = new Tracer(testScramble);
	CornerCommSolver cornerTracer = new CornerCommSolver(testScramble);
	EdgeCommSolver edgeTracer = new EdgeCommSolver(testScramble);
	for (int x = 1; x<=50; x++)
	{
	    System.out.println("#"+x);

	    //will only generate easy scrambles
	    while(tracer.hasParity() || tracer.hasFlippedEdges() || tracer.hasTwistedCorners())
	    {
		testScramble = scrambler.genScramble();
		tracer = new Tracer(testScramble);
		cornerTracer = new CornerCommSolver(testScramble);
		edgeTracer = new EdgeCommSolver(testScramble);
	    }
	    System.out.println("SCRAMBLE: " + tracer.getScramble());
	    System.out.println("INITIAL STATE");
	    tracer.printEverything();

	    cornerTracer.solveCorners();
	    System.out.println(cornerTracer.toString());

	    edgeTracer.solveEdges();
	    System.out.println(edgeTracer.toString());

	    System.out.println("FINAL STATE");
	    System.out.println(cornerTracer.getCornerMap());
	    System.out.println(edgeTracer.getEdgeMap());
	}

	//TESTING EDGE COMM SOLVER
	/*String testScramble = scrambler.genScramble();
	Tracer tracer = new EdgeCommSolver(testScramble);
	//will only generate easy scrambles
	while(tracer.hasParity() || tracer.hasFlippedEdges() || tracer.hasTwistedCorners())
	{
	    testScramble = scrambler.genScramble();
	    tracer = new EdgeCommSolver(testScramble);
	}
	System.out.println("#"+x);
	System.out.println("SCRAMBLE: " + tracer.getScramble());
	System.out.println("INITIAL STATE OF EDGE MAP");
	System.out.println(tracer.getEdgeMap());

	((EdgeCommSolver)tracer).solveEdges();
	System.out.println(tracer.toString());
	System.out.println("FINAL STATE OF EDGE MAP");
	System.out.println(tracer.getEdgeMap());
	System.out.println();*/
 	
	/* TESTING TRACER SCRAMBLE TO TURNS
	System.out.println("maps BEFORE scramble is applied-");
	System.out.println("(everything should be matching up)");
	tracer.printEverything();
	System.out.println("-------------------------");

      	tracer.scrambleToTurns(testscramble);

	System.out.println("maps AFTER the scramble is appied-");
	tracer.printEverything();*/

    }
}
