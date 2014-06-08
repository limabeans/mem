import java.io.*;
public class TracerTester
{
    public static void main(String[] args)
    {
	int solved =0;
	int breaked=0;
	Scrambler scrambler = new Scrambler();
	String testScramble = scrambler.genScramble();
	Tracer tracer = new Tracer(testScramble);
	CornerTracer cornerTracer = new CornerTracer(testScramble);

	testScramble = scrambler.genScramble();
	tracer = new Tracer(testScramble);
	cornerTracer = new CornerTracer(testScramble);
	while(!Tracer.hasParity(testScramble))
	{
	    testScramble = scrambler.genScramble();
	    tracer = new Tracer(testScramble);
	    cornerTracer = new CornerTracer(testScramble);
	}
	System.out.println("SCRAMBLE: " + tracer.getScramble());
	cornerTracer.traceCorners();
	System.out.println(cornerTracer.getCornerMap());

 
    }   




	//TESTING EDGE COMM SOLVER
	/*String testScramble = scrambler.genScramble();
	Tracer tracer = new EdgeTracer(testScramble);
	//will only generate easy scrambles
	while(tracer.hasParity() || tracer.hasFlippedEdges() || tracer.hasTwistedCorners())
	{
	    testScramble = scrambler.genScramble();
	    tracer = new EdgeTracer(testScramble);
	}
	System.out.println("#"+x);
	System.out.println("SCRAMBLE: " + tracer.getScramble());
	System.out.println("INITIAL STATE OF EDGE MAP");
	System.out.println(tracer.getEdgeMap());

	((EdgeTracer)tracer).traceEdges();
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


