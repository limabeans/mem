public class ASolve implements Comparable<ASolve>
{

    //pre-action
    private String scramble;
    private CommSolver solver;

    //post-action
    private String time;
    private boolean isDNF;
    private String comment;


    public ASolve(String scramble)
    {
	solver = new CommSolver(scramble);
	time = null;
	isDNF = false;
	comment = "";
    }

    public int getEdgeMetric()
    {
	return solver.getEdgeMetric();
    }
    public int getCornerMetric()
    {
	return solver.getCornerMetric();
    }    

    public void setComment(String newComment)
    {
	comment = newComment;
    }

    public String getComment()
    {
	return comment;
    }
			     
    public String getAnalysis()
    {
	if(isDNF && hasComment())
	{
	    return("dnf(" +  getTime() + ")\n//" + getComment() + "\n" + solver.toString());
	}
	if(isDNF && !hasComment())
	{
	    return("dnf(" +  getTime() + ")\n" + solver.toString());
	}
	if(!isDNF && hasComment())
	{
	    return(getTime() + "\n//" + getComment() + "\n" + solver.toString());
	}
	else //!isDNF && !hasComment()
	{
	    return(getTime() + "\n" + solver.toString());
	}
    }
    public boolean hasFlippedEdges()
    {
	return solver.hasFlippedEdges();
    }
    public boolean hasTwistedCorners()
    {
	return solver.hasTwistedCorners();
    }
    public boolean hasParity()
    {
	return solver.getHasParity();
    }
    public boolean hasGivenCornerComm(String comm)
    {
	return solver.hasGivenCornerComm(comm);
    }
    public boolean hasGivenEdgeComm(String comm)
    {
	return solver.hasGivenEdgeComm(comm);
    }
    public void refresh(String newScramble)
    {
	setScramble(newScramble);
	solver.refresh(newScramble);
    }

    public void setScramble(String newScramble)
    {
	scramble = newScramble;
    }
    public String getScramble()
    {
	return scramble;
    }

    public int compareTo(ASolve s)
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

    public void setTime(String time)
    {
	this.time = time;
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



