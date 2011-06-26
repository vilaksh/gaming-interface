package gameEngine;

import geometryUtilities.Point;
import geometryUtilities.Vector;

public class Car 
{
	private Point _center;
	private Vector _velocity;
	private int _i_length, _i_breadth;
	
	public int length, breadth;
	
	public Car(Point c, Vector v, int l, int b)
	{
		_center = c;
		_velocity = v;
		
		_i_length = l;
		_i_breadth = b;
		
		length = 2*l;
		breadth = 2*b;
	}
	
	public void MoveCar(double time)
	{
		long newx = Math.round(_center.xCord + _velocity.GetPolarForm().xCord * time);
		long newy = Math.round(_center.yCord + _velocity.GetPolarForm().yCord * time);
		
		_center = new Point(newx, newy);
	}
	
	public void ChangeVelocity(Vector v)
	{
		_velocity.AddVector(v);
	}
	
	public Boolean IsInRange(Point p)
	{
		p.LinerTransform(_center.xCord, _center.yCord);
		p.AngularTransformation(_center.xCord, _center.yCord, (360 - _velocity.directionDegree));
		
		return ((Math.abs(p.xCord)<_i_length) && (Math.abs(p.yCord) < _i_breadth));
	}
}
