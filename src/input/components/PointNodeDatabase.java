package input.components;

import java.util.*;
public class PointNodeDatabase implements ComponentNode {
	protected Set<PointNode> ptDatabase;

	public PointNodeDatabase() 
	{

		ptDatabase = new LinkedHashSet<PointNode>();

	}

	public PointNodeDatabase(Set <PointNode> points)
	{
		ptDatabase = new LinkedHashSet<PointNode>(points);	
	}

	public void put(PointNode point)
	{
		ptDatabase.add(point);
	}

	public boolean contains(PointNode point)
	{
		return ptDatabase.contains(point);
	}

	public boolean contains(double x, double y)
	{
		
		return ptDatabase.contains(new PointNode(x,y));
		
	}

	public String getName(PointNode point)
	{
		return getPoint(point).getName();
	}

	public String getName(double x, double y)
	{
		return getPoint(x, y).getName();
	}

	public PointNode getPoint(PointNode point)
	{
		return getPoint(point.getX(), point.getY());
	}

	public PointNode getPoint(double x, double y)
	{
		PointNode pt = new PointNode(x, y);
		for (PointNode point: ptDatabase)
		{
			if (point.equals(pt))return point;
		}
		ptDatabase.add(pt);
		return pt;
	}
	
	public PointNode getPoint(String name) {
		for(PointNode points : ptDatabase) {
			if(points != null && points.getName().equals(name)) {
				return points;
			}
		}
		return null; 
	}

	@Override
	public void unparse(StringBuilder sb, int level) {
		// TODO Auto-generated method stub
		for(PointNode point: ptDatabase) {
			point.unparse(sb, level);
		}
		
		
	}
}