package geometryUtilities;

public class Vector 
{
	public double magnitude;
	public double directionDegree; 
	
	public Vector(double p, double dir)
	{
		magnitude = p;
		directionDegree = dir;
	}
	
	public Point GetPolarForm()
	{
		long x = Math.round(magnitude * Math.cos(directionDegree));
		long y = Math.round(magnitude * Math.sin(directionDegree));
		
		return new Point(x, y);
	}
	
	public Vector AddVector(Vector v)
	{
		Vector ret;
		
		double retxc = v.magnitude * Math.cos(v.directionDegree) +
		magnitude * Math.cos(directionDegree);
		
		double retyc = v.magnitude * Math.sin(v.directionDegree) +
		magnitude * Math.sin(directionDegree);
		
		double retxsq = Math.pow(retxc, 2);
		double retysq = Math.pow(retyc, 2);
		
		ret = new Vector(Math.sqrt(retxsq + retysq), Math.atan2(retyc, retxc));
		
		return ret;
	}	
}
