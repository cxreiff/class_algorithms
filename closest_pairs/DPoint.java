


public class DPoint
{
	private double x;
	private double y;
	
	public DPoint()
	{
		x=0.0;
		y=0.0;
	}
	
	public DPoint(double x, double y)
	{
		this.x=x;
		this.y=y;
	}
	
	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}
	
	public String toString()
	{
		return "("+x+","+y+")";
	}
}
