package input.parser;

import java.util.ArrayList;
import java.util.List;


import PointNode_.PointNodeDatabase;
import PointNode_.*; 
import PointNode_.SegmentNode;
import PointNode_.SegmentNodeDatabase;
import org.json.JSONObject;
import org.json.HTTPTokener;
import org.json.JSONTokener;
import org.json.JSONArray;

import input.components.*;
import input.exception.ParseException;

public class JSONParser
{
	protected ComponentNode  _astRoot;

	public JSONParser()
	{
		_astRoot = null;
	}

	private void error(String message)
	{

		throw new ParseException("Parse error: " + message);
	}

	public ComponentNode parse(String str) throws ParseException
	{
		// Parsing is accomplished via the JSONTokenizer class.
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();

		// TODO: Build the whole AST, check for return class object, and return the root
		//checking the JSON file 
		if(!JSONroot.has("figure")) {
			throw new ParseException("Invalid JSON: missing 'figure'");
		}

		// extracting figure object 
		JSONObject figureObj = JSONroot.getJSONObject("Figure"); 

		// extract figure description
		String description = figureObj.getString("Description"); 

		//extract Point 
		PointNodeDatabase pointNodeDB = extractPointNodeDB(figureObj); 

		// extract the segment 
		SegmentNodeDatabase segmentNodeDB = extractSegmentNodeDB(figureObj, PointNodeDB); 

		// extract figure Node
		FigureNode figureNode = new FigureNode(description, pointNodeDB, segmentNodeDB); 

		return figureNode; 
	}

	// TODO: implement supporting functionality
	
	public PointNodeDatabase extractPointNodeDB(JSONArray jArray) {
		PointNodeDatabase _pointNodeDatabase = new PointNodeDatabase(); 
		for(Object item: jArray) {
			JSONObject jObj = (JSONObject) item;
			String name = jObj.getString("name"); 
			Double x = jObj.getDouble("x"); 
			Double y = jObj.getDouble("y"); 
			PointNode _pointNode = new PointNode(name, x, y); 
			_pointNodeDatabase.put(_pointNode); 
		}
	return _pointNodeDatabase; 
	}
	
	
	public SegmentNodeDatabase extractSegmentNodeDB(JSONArray jArray , PointNodeDatabase _pointNodeDB) {
		
		SegmentNodeDatabase _segmentNodeDb = new SegmentNodeDatabase(); 
		
		JSONArray segmentArray = jArray.getJSONArray("Segments"); // big picture: controlling the whole array
		
		for(Object item: jArray) { // access to each array
			//Cast the current ite to  JSONObject 
			JSONObject jObj = (JSONObject) item;  //individual item
			// 
			for(String key: jObj.keySet()){ // individual item has more element. 
				JSONArray pointNameArray = jObj.getJSONArray(key); 
				List<PointNode> points = new ArrayList<>(): 
				
				for (Object pointName: pointNameArray) {
					String name = (String)pointName; 
					
					// need to access the name to get point???
					pointNode point = _pointNodeDB.get(name); 
					if(point != null) {
						points.add(point);
					}
					else {
					//	handle the case where a refereence point is missing
					}
				}
			}
			if(!point.isEmpty()) {
				SegmentNode segmentNode = new SegmentNode(key, points); 
				_segmentNodeDb.put(segmentNode); 
			}
		}
		
		return _segmentNodeDb; 
	}
	
}