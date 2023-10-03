package PointNode_;


/**
 * A utility class only for representing ONE segment
 */
public class SegmentNode
{
	protected PointNode _point1;
	protected PointNode _point2;
	
	public PointNode getPoint1() { return _point1; }
	public PointNode getPoint2() { return _point2; }
	
	public SegmentNode(PointNode pt1, PointNode pt2)
	{			
		this._point1 = pt1;
		this._point2 = pt2;
	}
	@Override
	public boolean equals(Object obj)
	{
		//Checking obj, casting as a SegmentNode
		if (obj == null) return false;
		if (!(obj instanceof SegmentNode)) return false;
		SegmentNode o = (SegmentNode) obj;
		
		if (o._point1.equals(o._point2))return false;
		return ((_point1.equals(o._point1) && _point2.equals(o._point2)) || (_point1.equals(o._point2)) && _point2.equals(o._point1));
	}
	public String toString()
    {
		return  "(" + this._point1 + ", " + this._point2 + ")";
	}
}