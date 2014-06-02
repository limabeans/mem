import java.util.*;
import java.text.*;
public class TimerStats
{
    private final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("m:ss.SSS");
    private ArrayList<Long> longDatabase;
    public TimerStats()
    {
	longDatabase = new ArrayList<Long>();
    }
    public String toString()
    {
	String formatted = "Best Time: " + getBestTime() + "\n" +
	    "Worst Time: " + getWorstTime() + "\n";
	return formatted;
    }
    public void push(long newTime)
    {
	longDatabase.add(newTime);
	Collections.sort(longDatabase);
    }
    public String getBestTime()
    {
	if(longDatabase.size()==0)
	    return "DNS";
	return SIMPLE_DATE_FORMAT.format(longDatabase.get(0));
    }
    public String getWorstTime()
    {
	if(longDatabase.size()==0)
	    return "DNS";
	return SIMPLE_DATE_FORMAT.format(longDatabase.get(longDatabase.size()-1));
    }
}
