package testing;

import wowcad.backend.beam.drawing.Drawing;

public class Testing {

	public static void main(String[] args) {
		Drawing d = new Drawing("pippo");
		d.exportAsJPEG("C:\\Users\\franc\\OneDrive\\Desktop\\test.jpeg");
	}

}
