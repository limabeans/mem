import java.util.*;

public class CornerCommSolver extends Tracer
{
    private int numCornerIterations;
    private String cornerLetterSequence;
    private boolean requiredCornerCycleBreak;
    private int numCornerCycleBreaks;
    public CornerCommSolver(String scramble)
    {
	super(scramble);
	numCornerIterations = 0;
	cornerLetterSequence = "";
	requiredCornerCycleBreak = false;
	numCornerCycleBreaks = 0;
    }
    public void solveCorners()
    {
    }
    private void solveNextCornerComm()
    {
    }
    private String[][][] determineNextTarget(String loc1, String loc2, String loc3)
    {
	return null;
    }
    private String findCornerCycleBreak(String blacklist)
    {
	return null;
    }
    private String[] returnOtherCornerStickers(String firstSticker)
    {
	return null;
    }
    private boolean isCornersSolved()
    {
	return false;
    }

}

