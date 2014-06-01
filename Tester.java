public class Tester
{
    public static void main(String[] args)
    {
	Scrambler scrambler = new Scrambler();
	ScrambleTracer scrambleTracer = new ScrambleTracer();

	String testscramble = scrambler.scramble();
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
