package testing;

import java.io.File;
import java.io.IOException;

import wowcad.backend.beam.drawing.Drawing;
import wowcad.backend.beam.drawing.JPEGExporter;
import wowcad.backend.beam.shapes.Point;
import wowcad.backend.beam.shapes.Segment;

public class Testing {

	public static void main(String[] args) {
		Drawing d = new Drawing("pippo");
		d.addPrimitive(new Point("", 10, 10));
		d.addPrimitive(new Segment("test", new Point("", 5, 5), new Point("", 10, 5)));
		JPEGExporter exp = new JPEGExporter(d, 5);
		exp.drawCartesianAxis();
		exp.drawShapes();
		try {
			exp.exportJpegImage(new File("C:\\Users\\franc\\OneDrive\\Desktop\\test.jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
