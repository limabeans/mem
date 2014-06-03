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
	EdgeTracer edgeTracer = new EdgeTracer(testScramble);

	for(int x=1; x<=1000;x++)
	{
	    testScramble = scrambler.genEasyScramble();
	    //testScramble = "U R L B' D F D' R D2 L2 R2 U B' F L2 B2 R F' D2 F R2";
	    tracer = new Tracer(testScramble);
	    cornerTracer = new CornerTracer(testScramble);
	    edgeTracer = new EdgeTracer(testScramble);
	    System.out.println("SCRAMBLE: " + tracer.getScramble());

	    edgeTracer.traceEdges();
	    if(edgeTracer.requiredEdgeCycleBreak)
	    {
		breaked++;
	    }
	    System.out.println(edgeTracer.toString());
	    System.out.println(edgeTracer.getEdgeMap());
	    
	    if(edgeTracer.getEdgeMap().get("E").equals("E") && edgeTracer.getEdgeMap().get("U").equals("U"))
	    {
		solved++;
	    }
	    
	try(PrintWriter out = new PrintWriter(new FileWriter("log.txt",true))){
		out.print(String.format("%s\n",testScramble));
	    }catch (IOException e){}
	}
	System.out.println(solved);
	System.out.println(breaked);



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
}
