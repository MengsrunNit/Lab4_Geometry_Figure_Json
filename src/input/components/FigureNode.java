package input.components;

import java.util.Set;
import utilities.io.*;
/**
 * A basic figure consists of points, segments, and an optional description
 * 
 * Each figure has distinct points and segments (thus unique database objects).
 * 
 */
public class FigureNode implements ComponentNode
{
	protected String              _description;
	protected PointNodeDatabase   _points;
	protected SegmentNodeDatabase _segments;

	public String              getDescription()    { return _description; }
	public PointNodeDatabase   getPointsDatabase() { return _points; }
	public SegmentNodeDatabase getSegments()       { return _segments; }
	
	public FigureNode(String description, PointNodeDatabase points, SegmentNodeDatabase segments)
	{
		_description = description;
		_points = points;
		_segments = segments;
	}

	@Override
	public void unparse(StringBuilder sb, int level)
	{
		//calls unparse in point and in segment to and output a list point in segment. 
        // TODO
		sb.append("Figure: "+ "\n ");
		sb.append(StringUtilities.indent(level));
		sb.append("Description:" + getDescription() + "\n");
		sb.append("PointNode:" + " \n{"); 
		_points.unparse(sb, level);
		
    }
	
	
	// create a string that output exactly amount of point and segement 
	 public static void main(String[] args) {
		 PointNode point1 = new PointNode("A", 3, 2); 
		 PointNode point2 = new PointNode("B", 4, 2); 
		 PointNode point3 = new PointNode("C", 4,22);
		 
		 PointNodeDatabase pointDB = new PointNodeDatabase(); 
		 pointDB.put(point3);
		 pointDB.put(point2);
		 pointDB.put(point1);
		 
		 SegmentNodeDatabase segmentDB = new SegmentNodeDatabase(); 
		 segmentDB.addDirectedEdge(point1, point2);
		 segmentDB.addUndirectedEdge(point1, point3);
		 
		 FigureNode test = new FigureNode("Test" , pointDB, segmentDB ); 
		 StringBuilder mystring = new StringBuilder(); 
		 test.unparse(mystring, 0);
		 System.out.println(mystring);
		 
	 }
}