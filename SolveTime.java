public class SolveTime implements Comparable<SolveTime>
{
    private String time;
    private boolean isDNF;
    private String comment;
    
    public SolveTime(String time)
    {
	this.time = time;
	isDNF = false;
	comment = "";
    }
    public SolveTime(String time, boolean isDNF)
    {
	this.time = time;
	this.isDNF = isDNF;
	comment = "";
    }

    public int compareTo(SolveTime s)
    {
	if(this.getIsDNF() && s.getIsDNF())
	    return 0;
	if(this.getIsDNF() && !s.getIsDNF())
	    return -1;
	if(!this.getIsDNF() && s.getIsDNF())
	    return 1;
	else
	{
	    //if the other time is larger, this will return a positive number, meaning this time is better(lower)!
	    return ((int)(convertToMillis(s.getTime())-convertToMillis(this.getTime())));
	}
    }
    public static long convertToMillis(String time)
    {
	String minutes = "";
	String seconds = "";
	String millis = "";
	int index = 0;
	while(!time.substring(index,index+1).equals(":"))
	{
	    minutes+=time.substring(index,index+1);
	    index++;
	}
	index++;
	while(!time.substring(index,index+1).equals("."))
	{
	    seconds+=time.substring(index,index+1);
	    index++;
	}
	index++;
	while(index<time.length())
	{
	    millis+=time.substring(index,index+1);
	    index++;
	}
	return 60000*Long.parseLong(minutes) + 1000*Long.parseLong(seconds) + Long.parseLong(millis);
    }
    public String getTime()
    {
	return time;
    }
    public boolean hasComment()
    {
	return (comment.length()>0);
    }
    public void setToDNF()
    {
	isDNF = true;
    }
    public boolean getIsDNF()
    {
	return isDNF;
    }
    public void setComment(String newComment)
    {
	comment = newComment;
    }
    public String toString()
    {
	String returnString = "";
	if(isDNF)
	{
	    returnString += "dnf(" + time + ")";
	}
	else
	{
	    returnString += time;
	}
	if(hasComment())
	{
	    returnString = returnString + " //" + comment;
	}
	return returnString;
    }
}
