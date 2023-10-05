package input.parser;

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




	public PointNodeDatabase extractPointNodeDB(JSONObject figureObj) {
		// Retrieve the JSONarray from the JSONObj: Point
		JSONArray pointArray = figureObj.getJSONArray("Points");
		
		//created a PointNodeDatabase to retreive the PointNode for return 
		PointNodeDatabase pointNodeDB = new PointNodeDatabase();

		//iterate over each point object and add to the database 
		for(int i =0; i< pointArray.length(); i++) {
			// literate the to all the points and reteive each point
			JSONObject pointObj = pointArray.getJSONObject(i); 

			// Retrieve name and x, y value
			String pointName = pointObj.getString("name"); 
			Double pointX = pointObj.getDouble("x");
			Double pointY = pointObj.getDouble("y");

			// Initialize the a PointNode 
			PointNode pointNode = new PointNode(pointName, pointX, pointY);
			
			//put that PointNode into PointNote Database
			pointNodeDB.put(pointNode);
		}
		// return the PointNodeDatabase
		return pointNodeDB; 


	}


	public SegmentNodeDatabase extractSegmentNodeDB(JSONObject figureObj, PointNodeDatabase _pointNodeDB) {

		// Copy the segments into segmentArray
		JSONArray segmentArray = figureObj.getJSONArray("Segments");
		SegmentNodeDatabase _segmentNodeDb = new SegmentNodeDatabase();

		// Iterate over each segment object and add to the database
		for (int i = 0; i < segmentArray.length() ; i++) {
			// Getting each heading in segment
			JSONObject segmentObj = segmentArray.getJSONObject(i);
			// Adjacency list implementation
			List<PointNode> AdjPointNodeList = new ArrayList<>();

			//getting a firstPoiintKey
			String startPointName = segmentObj.keys().next();

			//getting the key Array JSONObject to extract
			JSONArray endPointNames = segmentObj.getJSONArray(startPointName);

			// Retrieve the start pointNode from the database
			PointNode startPointNode = _pointNodeDB.getPoint(startPointName);

			// If the start point node from the database is null, throw an exception
			if (startPointNode == null) {
				throw new ParseException("Invalid Segment:" + startPointName);
			}

			// Iterate over the array of the end points name and create edge
			for (int j = 0; j < endPointNames.length(); j++) {
				
				//getting the PointNode Name from the JSONArray
				String endPointName = endPointNames.getString(j);
				
				//retrieve the pointNode usingName
				PointNode endPointNode = _pointNodeDB.getPoint(endPointName);

				// If the end point could not be found, throw an exception
				if (endPointNode == null) {
					throw new ParseException("Invalid Segment:" + endPointName);
				}

				// Store the segments into AdjPointNode
				AdjPointNodeList.add(endPointNode);
				
			}
				//Throw key_pointNode and its value into Database that retrieve the JSONArray
			_segmentNodeDb.addAdjacencyList(startPointNode, AdjPointNodeList);

		}

		// Return the segment database after processing all segments
		return _segmentNodeDb;
	}


}



