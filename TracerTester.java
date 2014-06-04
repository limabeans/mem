import java.io.*;
public class TracerTester
{
    public static void main(String[] args)
    {
	int solved =0;
	int breaked=0;
	Scrambler scrambler = new Scrambler();
	String testScramble = scrambler.genDangerousScramble();
	Tracer tracer = new Tracer(testScramble);
	CornerTracer cornerTracer = new CornerTracer(testScramble);
	EdgeTracer edgeTracer = new EdgeTracer(testScramble);


	for (int x=0;x<500;x++)
	{
	    testScramble = scrambler.genFriendlyScramble();
	    //testScramble = "U R L B' D F D' R D2 L2 R2 U B' F L2 B2 R F' D2 F R2";
	    tracer = new Tracer(testScramble);
	    cornerTracer = new CornerTracer(testScramble);
	    try(PrintWriter out = new PrintWriter(new FileWriter("log.txt",true))){
		    out.print(String.format("%s\n",testScramble));
		    out.close();
		}catch (IOException e){}
	
    
	    System.out.println("SCRAMBLE: " + tracer.getScramble());
	    if(cornerTracer.hasTwistedCorners())
	    {
		System.out.println("HAS TWISTED CORNERS");
	    }
	    cornerTracer.traceCorners();

	    System.out.println(cornerTracer.toString());
	    System.out.println(cornerTracer.getCornerMap());

	}	 
    }   




	//TESTING EDGE COMM SOLVER
	/*String testScramble = scrambler.genDangerousScramble();
	Tracer tracer = new EdgeTracer(testScramble);
	//will only generate easy scrambles
	while(tracer.hasParity() || tracer.hasFlippedEdges() || tracer.hasTwistedCorners())
	{
	    testScramble = scrambler.genDangerousScramble();
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


