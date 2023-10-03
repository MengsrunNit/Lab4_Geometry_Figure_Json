package input.parser;

import java.util.ArrayList;
import 
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
		SegmentNodeDatabase segmentNodeDB = extractSegmentNodeDB(figureObj, pointNodeDB); 

		// extract figure Node
		FigureNode figureNode = new FigureNode(description, pointNodeDB, segmentNodeDB); 

		return figureNode; 
	}

	// TODO: implement supporting functionality
	
	public PointNodeDatabase extractPointNodeDB(JSONObject figureObj) {
		PointNodeDatabase _pointNodeDatabase = new PointNodeDatabase(); 
		
		JSONArray pointArray = figureObj.getJSONArray("Points");
		
		for(Object item: pointArray) {
			
			JSONObject jObj = (JSONObject) item;
			String name = jObj.getString("name"); 
			Double x = jObj.getDouble("x"); 
			Double y = jObj.getDouble("y"); 
			PointNode _pointNode = new PointNode(name, x, y); 
			_pointNodeDatabase.put(_pointNode); 
		}
	return _pointNodeDatabase; 
	}
	
	
	public SegmentNodeDatabase extractSegmentNodeDB(JSONObject figureObj , PointNodeDatabase _pointNodeDB) {
		
		SegmentNodeDatabase _segmentNodeDb = new SegmentNodeDatabase(); 
		  // Extract the "Segments" array from the JSON
		JSONArray segmentArray = figureObj.getJSONArray("Segments");
		
	    
		for(int i =0; i < segmentArray.length()-1; i++) {
			//getting each heading in segment
			JSONObject segmentObj = segmentArray.getJSONObject(i); 
	
			// adjlist implementation
			ArrayList<PointNode> AdjPointNodeList = new ArrayList<>(); 
			
			String startPointName = segmentObj.keys().toString();
			
			for(String key : segmentObj.keySet()) {
				
	          
	            PointNode valueNode = _pointNodeDB.getPoint(key);
	            AdjPointNodeList.add(_pointNodeDB.getPoint(valueNode));
	 
	           
			}
			_segmentNodeDb.addAdjacencyList(_pointNodeDB.getPoint(startPointName), AdjPointNodeList);
			
		}
		
		return _segmentNodeDb; 
	}
	
}