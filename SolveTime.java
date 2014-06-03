public class SolveTime
{
    private long time;
    private boolean isDNF;
    private String comment;
    
    public SolveTime(long time)
    {
	this.time = time;
	isDNF = false;
	comment = "";
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
