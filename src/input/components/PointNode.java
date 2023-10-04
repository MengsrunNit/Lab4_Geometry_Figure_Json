package input.components;

import utilities.io.StringUtilities;
import utilities.math.MathUtilities;
/**
 * A 2D Point (x, y).
 */
public class PointNode implements ComponentNode
{
	protected static final String ANONYMOUS = "__UNNAMED";
	protected double _x;
	public double getX() { return this._x; }
	protected double _y;
	public double getY() { return this._y; }
	protected String _name;
	public String getName() { return _name; }
	/**
	 * Create a new Point with the specified coordinates.
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 */
	public PointNode(double x, double y)
	{
		this._x = x;
		this._y = y;
	}
	/**
	 * Create a new Point with the specified coordinates.
	 * @param name -- The name of the point. (Assigned by the UI)
	 * @param x -- The X coordinate
	 * @param y -- The Y coordinate
	 */
	public PointNode(String name, double x, double y)
	{
		this(x, y);
		this._name = name;
	}
	@Override
	public int hashCode()
	{
		return Double.valueOf(_x).hashCode() + Double.valueOf(_y).hashCode();
	}
	@Override
	public boolean equals(Object obj)
	{
		//Checking object, casting it as a PointNode
		if (obj == null) return false;
		if (!(obj instanceof PointNode)) return false;
		PointNode o = (PointNode) obj;
		
		//Compares the corresponding coordinates for each PointNode
		return (MathUtilities.doubleEquals(o._x, this._x) && MathUtilities.doubleEquals(o._y, this._y));
	}
    @Override
    public String toString()
    {
		return _name + " (" + this._x + ", " + this._y + ")";
	}
	@Override
	public void unparse(StringBuilder sb, int level) {
		sb.append(toString());
		sb.append(StringUtilities.indent(level));
	}
}