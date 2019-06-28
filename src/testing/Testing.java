package testing;

import java.io.File;
import java.io.IOException;

import wowcad.backend.beam.drawing.Drawing;
import wowcad.backend.beam.drawing.JPEGExporter;
import wowcad.backend.beam.shapes.Ellipse;
import wowcad.backend.beam.shapes.Point;
import wowcad.backend.beam.shapes.Polygon;
import wowcad.backend.beam.shapes.Polyline;
import wowcad.backend.beam.shapes.Segment;

public class Testing {

	public static void main(String[] args) {
		Drawing d = new Drawing("pippo");
		d.addPrimitive(new Point("", 500, 500));
		d.addPrimitive(new Segment("test", new Point("", 5, 5), new Point("", 10, 7)));
		d.addPrimitive(new Ellipse("pippo", new Point("", 10, 10), 20, 10));
		Polyline p = new Polygon("boh");
		p.addPoint(new Point("", 30, 30));
		p.addPoint(new Point("", -50, 0));
		p.addPoint(new Point("", 0, 0));
		d.addPrimitive(p);
		JPEGExporter exp = new JPEGExporter(d, 5);
		exp.drawCartesianAxis();
		exp.drawGrid();
		exp.drawShapes();
		try {
			exp.exportJpegImage(new File("C:\\Users\\franc\\OneDrive\\Desktop\\test.jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
