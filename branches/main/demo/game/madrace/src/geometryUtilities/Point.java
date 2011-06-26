package geometryUtilities;

public class Point 
{
	public long xCord, yCord;
	
	public Point(long x, long y)
	{
		xCord = x;
		yCord = y;
	}
	
	public Point LinerTransform(long xmag, long ymag)
	{
		xCord -= xmag;
		yCord -= ymag;
		
		return this;
	}	
	
	public Point AngularTransformation(long aboutx, long abouty, double degrees)
	{		
		// transforming the origin to the pivot point
		xCord -= aboutx;
		yCord -= abouty;
		
		// carry out the angular rotation
		long xrot = Math.round(xCord * Math.cos(degrees) - yCord * Math.sin(degrees));
		long yrot = Math.round(xCord * Math.sin(degrees) + yCord * Math.cos(degrees)); 
		
		// transform back the origin to original position
		xCord = xrot + aboutx;
		yCord = yrot + abouty;		
		
		return this;
	}
}
