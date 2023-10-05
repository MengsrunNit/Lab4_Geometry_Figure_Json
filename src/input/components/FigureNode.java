package input.components;


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

	public void unparse(StringBuilder sb, int level)
	{
		//calls unparse in point and in segment to and output a list point in segment.
        // TODO
		sb.append("Figure"+ "\n{\n");
		sb.append(StringUtilities.indent(1) + "Description : \"" + getDescription() + "\"\n");
		sb.append(StringUtilities.indent(1) + "Points:" + " \n" + StringUtilities.indent(1) + "{\n");
		_points.unparse(sb, 1);
		sb.append(StringUtilities.indent(1) + "}\n");
		sb.append(StringUtilities.indent(1) + "Segments:" + " \n" + StringUtilities.indent(1) + "{\n");
		_segments.unparse(sb, 1);
		sb.append(StringUtilities.indent(1) + "}\n}");
		
    }
	
	
	
}