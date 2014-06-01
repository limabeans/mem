import java.io.*;
public class GenParityFlippedTwistedScrambles
{
    public static void main(String[] args)
    {
	GenParityFlippedTwistedScrambles tester = new GenParityFlippedTwistedScrambles();
	
	for (int x = 1; x <=50 ; x++)
	{
	    try(PrintWriter out = new PrintWriter(new FileWriter("hardscrambles.txt",true))) {
	    out.print(String.format("%s. %s \n", x, tester.generateHardScramble()));
		} catch(IOException e) { }
	}
    }
	   
	  
   
    public String generateHardScramble()
    {
	Scrambler scrambler = new Scrambler();
	String newScramble = scrambler.genScramble();
	CheckSpecialCases csc = new CheckSpecialCases(newScramble);
	CheckFlippedEdges cfe = new CheckFlippedEdges(newScramble);
	CheckTwistedCorners ctc = new CheckTwistedCorners(newScramble);
	while(!(csc.hasParity(newScramble)
		&&cfe.hasFlippedEdges()
		&&ctc.hasTwistedCorners()))
	      {
		  newScramble = scrambler.genScramble();
		  cfe = new CheckFlippedEdges(newScramble);
		  ctc = new CheckTwistedCorners(newScramble);
	      }
	      return newScramble;
	      
    }
	
}
