package test.input.parser;

import input.components.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



import org.json.JSONObject;

import input.components.ComponentNode;
import input.components.FigureNode;
import input.exception.ParseException;
import input.parser.JSONParser;

import utilities.math.MathUtilities;

class JSONParserTest
{
	public static ComponentNode runFigureParseTest(String filename)
	{
		JSONParser parser = new JSONParser();

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);

		return parser.parse(figureStr);
	}
	@Test
	void empty_json_string_test()
	{
		JSONParser parser = new JSONParser();

		assertThrows(ParseException.class, () -> { parser.parse("{}"); });
	}

	@Test
	void single_triangle_test()
	{
		//
		// The input String ("single_triangle.json") assumes the file is
		// located at the top-level of the project. If you move your input
		// files into a folder, update this String with the path:
		//                                       e.g., "my_folder/single_triangle.json"
		//

		// export the JSON document to the into the ComponentNode 
		ComponentNode node = JSONParserTest.runFigureParseTest("src/single_triangle.json");


		assertTrue(node instanceof FigureNode);

		FigureNode figure = (FigureNode)node; 

		SegmentNodeDatabase segmentNodeDB = figure.getSegments();
		PointNodeDatabase pointNodeDB = figure.getPointsDatabase();

		// check if there is Point A in the Database correction position x, and y
		PointNode pointA = pointNodeDB.getPoint("A");

		assertTrue(MathUtilities.doubleEquals(pointA.getX(),0));
		assertTrue(MathUtilities.doubleEquals(pointA.getY(),0));


		// check if there is Point B in the Database correction position x, and y
		PointNode pointB = pointNodeDB.getPoint("B");

		assertTrue(MathUtilities.doubleEquals(pointB.getX(),1));
		assertTrue(MathUtilities.doubleEquals(pointB.getY(),1));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointC = pointNodeDB.getPoint("C");

		assertTrue(MathUtilities.doubleEquals(pointC.getX(),1));
		assertTrue(MathUtilities.doubleEquals(pointC.getY(),0));

		// description test 
		assertEquals(figure.getDescription(),"Right Triangle in the first quadrant.");

		// Test SegmentNode Database 

		assertEquals(segmentNodeDB.numDirectedEdges(), 3); 

		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());

	}
	@Test
	void fully_connected_irregular_polygon_Test() {

		// export the JSON document to the into the ComponentNode 
		ComponentNode node = JSONParserTest.runFigureParseTest("src/fully_connected_irregular_polygon.json");



		//extract the coponentNode to FigureNode
		FigureNode figure = (FigureNode)node; 

		assertTrue(node instanceof FigureNode);

		// extract the CoponentNode to SegmentNodeDatabase
		SegmentNodeDatabase segmentNodeDB = figure.getSegments();

		// extract the figure to PointNodeDatabase
		PointNodeDatabase pointNodeDB = figure.getPointsDatabase();


		// description test 
		assertEquals(figure.getDescription(),"Irregular pentagon in which each vertex is connected to each other.");

		// check if there is Point A in the Database correction position x, and y
		PointNode pointA = pointNodeDB.getPoint("A");

		assertTrue(MathUtilities.doubleEquals(pointA.getX(),0));
		assertTrue(MathUtilities.doubleEquals(pointA.getY(),0));


		// check if there is Point B in the Database correction position x, and y
		PointNode pointB = pointNodeDB.getPoint("B");

		assertTrue(MathUtilities.doubleEquals(pointB.getX(),4));
		assertTrue(MathUtilities.doubleEquals(pointB.getY(),0));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointC = pointNodeDB.getPoint("C");

		assertTrue(MathUtilities.doubleEquals(pointC.getX(),6));
		assertTrue(MathUtilities.doubleEquals(pointC.getY(),3));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointD = pointNodeDB.getPoint("D");

		assertTrue(MathUtilities.doubleEquals(pointD.getX(),3));
		assertTrue(MathUtilities.doubleEquals(pointD.getY(),7));
		// check if there is Point C in the Database correction position x, and y
		PointNode pointE = pointNodeDB.getPoint("E");

		assertTrue(MathUtilities.doubleEquals(pointE.getX(),-2));
		assertTrue(MathUtilities.doubleEquals(pointE.getY(),4));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointF = pointNodeDB.getPoint("F");

		assertTrue(MathUtilities.doubleEquals(pointF.getX(),26));
		assertTrue(MathUtilities.doubleEquals(pointF.getY(),0));

		// Test SegmentNode Database 

		//	assertEquals(segmentNodeDB.numUndirectedEdges(), 10); 
		SegmentNodeDatabase expectedSegment = new SegmentNodeDatabase();

		expectedSegment.addDirectedEdge(pointA, pointB);
		expectedSegment.addDirectedEdge(pointA, pointC);
		expectedSegment.addDirectedEdge(pointA, pointD);
		expectedSegment.addDirectedEdge(pointA, pointE);


		expectedSegment.addDirectedEdge(pointB, pointC);
		expectedSegment.addDirectedEdge(pointB, pointD);
		expectedSegment.addDirectedEdge(pointB, pointE);


		expectedSegment.addDirectedEdge(pointC, pointD);
		expectedSegment.addDirectedEdge(pointC, pointE);


		expectedSegment.addDirectedEdge(pointD, pointE);


		//assertTrue(expectedSegment.equals(segmentNodeDB));
		assertEquals(segmentNodeDB.numDirectedEdges(), 10); 



		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		//	expectedSegment.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	@Test
	void crossing_symmetric_triangle_Test() {

		ComponentNode node = JSONParserTest.runFigureParseTest("src/crossing_symmetric_triangle.json");


		FigureNode figure = (FigureNode)node; 

		assertTrue(node instanceof FigureNode);

		// extract the CoponentNode to SegmentNodeDatabase
		SegmentNodeDatabase segmentNodeDB = figure.getSegments();

		// extract the figure to PointNodeDatabase
		PointNodeDatabase pointNodeDB = figure.getPointsDatabase();


		// check if there is Point A in the Database correction position x, and y
		PointNode pointA = pointNodeDB.getPoint("A");

		assertTrue(MathUtilities.doubleEquals(pointA.getX(),3));
		assertTrue(MathUtilities.doubleEquals(pointA.getY(),6));


		// check if there is Point B in the Database correction position x, and y
		PointNode pointB = pointNodeDB.getPoint("B");

		assertTrue(MathUtilities.doubleEquals(pointB.getX(),2));
		assertTrue(MathUtilities.doubleEquals(pointB.getY(),4));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointC = pointNodeDB.getPoint("C");

		assertTrue(MathUtilities.doubleEquals(pointC.getX(),4));
		assertTrue(MathUtilities.doubleEquals(pointC.getY(),4));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointD = pointNodeDB.getPoint("D");

		assertTrue(MathUtilities.doubleEquals(pointD.getX(),0));
		assertTrue(MathUtilities.doubleEquals(pointD.getY(),0));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointE = pointNodeDB.getPoint("E");

		assertTrue(MathUtilities.doubleEquals(pointE.getX(),6));
		assertTrue(MathUtilities.doubleEquals(pointE.getY(),0));

		//assertTrue(expectedSegment.equals(segmentNodeDB));
		//assertEquals(segmentNodeDB.numUndirectedEdges(), 8); 

		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}

	@Test
	void collinear_line_segment_Test() {
		ComponentNode node = JSONParserTest.runFigureParseTest("src/collinear_line_segments.json");

		FigureNode figure = (FigureNode)node; 

		assertTrue(node instanceof FigureNode);

		// extract the CoponentNode to SegmentNodeDatabase
		SegmentNodeDatabase segmentNodeDB = figure.getSegments();

		// extract the figure to PointNodeDatabase
		PointNodeDatabase pointNodeDB = figure.getPointsDatabase();


		// description test 
		assertEquals(figure.getDescription(),"A seqeunce of collinear line segments mimicking one line with 6 points.");
		// check if there is Point A in the Database correction position x, and y

		PointNode pointA = pointNodeDB.getPoint("A");

		assertTrue(MathUtilities.doubleEquals(pointA.getX(),0));
		assertTrue(MathUtilities.doubleEquals(pointA.getY(),0));


		// check if there is Point B in the Database correction position x, and y
		PointNode pointB = pointNodeDB.getPoint("B");

		assertTrue(MathUtilities.doubleEquals(pointB.getX(),4));
		assertTrue(MathUtilities.doubleEquals(pointB.getY(),0));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointC = pointNodeDB.getPoint("C");

		assertTrue(MathUtilities.doubleEquals(pointC.getX(),9));
		assertTrue(MathUtilities.doubleEquals(pointC.getY(),0));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointD = pointNodeDB.getPoint("D");

		assertTrue(MathUtilities.doubleEquals(pointD.getX(),11));
		assertTrue(MathUtilities.doubleEquals(pointD.getY(),0));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointE = pointNodeDB.getPoint("E");

		assertTrue(MathUtilities.doubleEquals(pointE.getX(),16));
		assertTrue(MathUtilities.doubleEquals(pointE.getY(),0));

		//assertTrue(expectedSegment.equals(segmentNodeDB));
		assertEquals(segmentNodeDB.numDirectedEdges(), 5); 

		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());

	}
	@Test
	void pentagon_Test() {
		ComponentNode node = JSONParserTest.runFigureParseTest("src/pentagon.json");

		FigureNode figure = (FigureNode)node; 

		assertTrue(node instanceof FigureNode);

		// extract the CoponentNode to SegmentNodeDatabase
		SegmentNodeDatabase segmentNodeDB = figure.getSegments();

		// extract the figure to PointNodeDatabase
		PointNodeDatabase pointNodeDB = figure.getPointsDatabase();


		// description test 
		assertEquals(figure.getDescription(),"Pentagon in the first and second Quatrant");
		// check if there is Point A in the Database correction position x, and y

		PointNode pointA = pointNodeDB.getPoint("A");

		assertTrue(MathUtilities.doubleEquals(pointA.getX(),2));
		assertTrue(MathUtilities.doubleEquals(pointA.getY(),3));


		// check if there is Point B in the Database correction position x, and y
		PointNode pointB = pointNodeDB.getPoint("B");

		assertTrue(MathUtilities.doubleEquals(pointB.getX(),-2));
		assertTrue(MathUtilities.doubleEquals(pointB.getY(),5));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointC = pointNodeDB.getPoint("C");

		assertTrue(MathUtilities.doubleEquals(pointC.getX(),6));
		assertTrue(MathUtilities.doubleEquals(pointC.getY(),5));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointD = pointNodeDB.getPoint("D");

		assertTrue(MathUtilities.doubleEquals(pointD.getX(),2));
		assertTrue(MathUtilities.doubleEquals(pointD.getY(),0));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointE = pointNodeDB.getPoint("E");

		assertTrue(MathUtilities.doubleEquals(pointE.getX(),4));
		assertTrue(MathUtilities.doubleEquals(pointE.getY(),0));

		//assertTrue(expectedSegment.equals(segmentNodeDB));
		assertEquals(segmentNodeDB.numDirectedEdges(), 5); 

		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());

	}



	void testing_Test() {

		ComponentNode node = JSONParserTest.runFigureParseTest("src/testing.json");

		FigureNode figure = (FigureNode)node; 

		assertTrue(node instanceof FigureNode);

		// extract the CoponentNode to SegmentNodeDatabase
		SegmentNodeDatabase segmentNodeDB = figure.getSegments();

		// extract the figure to PointNodeDatabase
		PointNodeDatabase pointNodeDB = figure.getPointsDatabase();


		// description test 
		assertEquals(figure.getDescription(),"Reflected traingle made up of 5 points");
		// check if there is Point A in the Database correction position x, and y

		PointNode pointA = pointNodeDB.getPoint("A");

		assertTrue(MathUtilities.doubleEquals(pointA.getX(),2));
		assertTrue(MathUtilities.doubleEquals(pointA.getY(),2));


		// check if there is Point B in the Database correction position x, and y
		PointNode pointB = pointNodeDB.getPoint("B");

		assertTrue(MathUtilities.doubleEquals(pointB.getX(),6));
		assertTrue(MathUtilities.doubleEquals(pointB.getY(),4));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointC = pointNodeDB.getPoint("C");

		assertTrue(MathUtilities.doubleEquals(pointC.getX(),4));
		assertTrue(MathUtilities.doubleEquals(pointC.getY(),6));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointD = pointNodeDB.getPoint("D");

		assertTrue(MathUtilities.doubleEquals(pointD.getX(),8));
		assertTrue(MathUtilities.doubleEquals(pointD.getY(),2));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointE = pointNodeDB.getPoint("E");

		assertTrue(MathUtilities.doubleEquals(pointE.getX(),10));
		assertTrue(MathUtilities.doubleEquals(pointE.getY(),6));

		//assertTrue(expectedSegment.equals(segmentNodeDB));
		//assertEquals(segmentNodeDB.numDirectedEdges(), 5); 

		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());





	}

@Test
	void reflectedtraingle_Test() {
		ComponentNode node = JSONParserTest.runFigureParseTest("src/start.json");

		FigureNode figure = (FigureNode)node; 

		//	assertTrue(node instanceof FigureNode);

		// extract the CoponentNode to SegmentNodeDatabase
		SegmentNodeDatabase segmentNodeDB = figure.getSegments();

		// extract the figure to PointNodeDatabase
		PointNodeDatabase pointNodeDB = figure.getPointsDatabase();


		// description test 
		assertEquals(figure.getDescription(),"Reflected traingle made up of 5 points");
		// check if there is Point A in the Database correction position x, and y

		PointNode pointA = pointNodeDB.getPoint("A");

		assertTrue(MathUtilities.doubleEquals(pointA.getX(),2));
		assertTrue(MathUtilities.doubleEquals(pointA.getY(),2));


		// check if there is Point B in the Database correction position x, and y
		PointNode pointB = pointNodeDB.getPoint("B");

		assertTrue(MathUtilities.doubleEquals(pointB.getX(),4));
		assertTrue(MathUtilities.doubleEquals(pointB.getY(),6));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointC = pointNodeDB.getPoint("C");

		assertTrue(MathUtilities.doubleEquals(pointC.getX(),10));
		assertTrue(MathUtilities.doubleEquals(pointC.getY(),6));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointD = pointNodeDB.getPoint("D");

		assertTrue(MathUtilities.doubleEquals(pointD.getX(),8));
		assertTrue(MathUtilities.doubleEquals(pointD.getY(),2));

		// check if there is Point C in the Database correction position x, and y
		PointNode pointE = pointNodeDB.getPoint("E");

		assertTrue(MathUtilities.doubleEquals(pointE.getX(),6));
		assertTrue(MathUtilities.doubleEquals(pointE.getY(),4));

		//assertTrue(expectedSegment.equals(segmentNodeDB));
		//assertEquals(segmentNodeDB.numDirectedEdges(), 5); 

		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());

	}


}
