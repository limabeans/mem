import java.util.*;
import java.text.*;
public class TimerStats
{
    private final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("m:ss.SSS");
    private ArrayList<SolveTime> database;
    public TimerStats()
    {
	database = new ArrayList<SolveTime>();
    }
    public String toString()
    {
	String formatted = "Best Time: " + getBestTime() + "\n" +
	    "Worst Time: " + getWorstTime() + "\n";
	return formatted;
    }
    public String getBestTime()
    {
	if(database.size()==0)
	    return null;
	if(database.size()==1)
	    return database.get(0).toString();
	else
	{
	    SolveTime best = database.get(0);
	    for(int x = 0; x < database.size(); x++)
	    {
		if(database.get(x).compareTo(best)>0)
		    best = database.get(x);
	    }
	    return best.getTime();
	}
    }
    public String getWorstTime()
    {
	if(database.size()==0)
	    return null;
	if(database.size()==1)
	    return database.get(0).toString();
	else
	{
	    SolveTime worst = database.get(0);
	    for(int x = 0; x < database.size(); x++)
	    {
		if(database.get(x).compareTo(worst)<0)
		    worst = database.get(x);
	    }
	    if(worst.getIsDNF())
	    {
		return "DNF";
	    }
	    return worst.getTime();
	}
    }
    public void update(ArrayList<SolveTime> newData)
    {
	database = newData;
    }
}
