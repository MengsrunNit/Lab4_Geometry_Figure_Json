package input.parser;

import java.util.ArrayList;
import input.components.PointNode;

import java.util.*;

import org.json.JSONObject;

import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONException;

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

	public ComponentNode parse(String str) throws ParseException {
		try{
			// Parsing is accomplished via the JSONTokenizer class.
			JSONTokener tokenizer = new JSONTokener(str);
			JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();
	
			// TODO: Build the whole AST, check for return class object, and return the root
			//checking the JSON file 
			if(!JSONroot.has("Figure")) {
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
			return new FigureNode(description, pointNodeDB, segmentNodeDB); 
	
		} catch(JSONException e) {
			throw new ParseException("Error parsing JSONS: " );
		}
	}

	// TODO: implement supporting functionality
	
	/*public PointNodeDatabase extractPointNodeDB(JSONObject figureObj) {
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
	*/
	
	public PointNodeDatabase extractPointNodeDB(JSONObject figureObj) {
		JSONArray pointArray = figureObj.getJSONArray("Points");
		
		PointNodeDatabase pointNodeDB = new PointNodeDatabase();
		
		//iterate over each point object and add to the database 
		for(int i =0; i< pointArray.length(); i++) {
			JSONObject pointObj = pointArray.getJSONObject(i); 
			
			String pointName = pointObj.getString("name"); 
			Double pointX = pointObj.getDouble("x");
			Double pointY = pointObj.getDouble("y");
			
			PointNode pointNode = new PointNode(pointName, pointX, pointY);
			pointNodeDB.put(pointNode);
		}
		return pointNodeDB; 
		
		
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
	

/*public SegmentNodeDatabase extractSegmentNodeDB(JSONObject figureObj , PointNodeDatabase _pointNodeDB) {
		//copy the segments into segmentArray
		JSONArray segmentArray = figureObj.getJSONArray("Segments");
		SegmentNodeDatabase _segmentNodeDb = new SegmentNodeDatabase(); 
		
	    //iterate over each segment object and add to the databse
		for(int i =0; i < segmentArray.length()-1; i++) {
			//getting each heading in segment
			JSONObject segmentObj = segmentArray.getJSONObject(i); 
			
			
			// adjlist implementation
			ArrayList<PointNode> AdjPointNodeList = new ArrayList<>(); 
			
			String startPointName = segmentObj.keys().next();
			
			JSONArray endPointNames = segmentObj.getJSONArray(startPointName); 
			
			// retrieve the start pointNode from the databse
			PointNode startPointNode = _pointNodeDB.getPoint(startPointName);
			
			//if the start point node from the database 
			if(startPointNode == null) {
				throw new ParseException("Invalid Segment:" + startPointName);
			}
			
			//iteratre over the arry of the end points name and create edge: 
			for(int j =0; j< endPointNames.length(); j++) {
				String endPointName = endPointNames.getString(j); 
				PointNode endPointNode = _pointNodeDB.getPoint(endPointName);
				
				// if the end point could not be found, throw an exception: 
				if(endPointNode == null) {
					throw new ParseException("Invalid Segment:" + endPointName);
				}
				
				//store the segments
				_segmentNodeDb.addUndirectedEdge(startPointNode, endPointNode);
			}
			
		
		return _segmentNodeDb; 
	} 
}
*/
}
	

	
