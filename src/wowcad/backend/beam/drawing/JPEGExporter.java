package wowcad.backend.beam.drawing;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.imageio.ImageIO;

import wowcad.backend.beam.shapes.Ellipse;
import wowcad.backend.beam.shapes.Point;
import wowcad.backend.beam.shapes.Primitive;
import wowcad.backend.beam.shapes.Segment;

/**
 * Provides functions to create a BufferedImage containing the shapes of the Drawing
 * @author franc
 *
 */
public class JPEGExporter {

	/**
	 * Format of the exported picture
	 */
	private static final String FORMAT = "jpeg";

	/**
	 * 1 millimeter is represented by RED_FACT pixels
	 */
	private int redFact = 5;

	/**
	 * Primitives to add
	 */
	private ArrayList<Primitive> primitives;

	/**
	 * Final image
	 */
	private BufferedImage resImg;

	/**
	 * Translation on x axis
	 */
	private double xTranslation;

	/**
	 * Translation on y axis
	 */
	private double yTranslation;

	/**
	 * Constructor
	 * @param drawing: source Drawing
	 */
	public JPEGExporter(Drawing drawing, int redFact) {
		super();
		this.redFact = redFact;
		primitives = new ArrayList<Primitive>();
		Set<String> keys = drawing.getPrimitives().keySet();
		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;		

		//determines the dimension of the resulting picture, where a millimeter corresponds to a pixel
		for(String k: keys) {
			Primitive _p = drawing.getPrimitives().get(k);
			primitives.add(_p);
			Point _c = _p.getCenter();
			if(Math.abs(_c.getX() * redFact) > maxX) {
				maxX = Math.abs(_c.getX() * redFact);
			}
			if(Math.abs(_c.getY() * redFact) > maxY) {
				maxY = Math.abs(_c.getY() * redFact);
			}
		}

		maxX += 20 * redFact;
		maxY += 20 * redFact;

		xTranslation = maxX;
		yTranslation = maxY;

		resImg = new BufferedImage((int) (2 * maxX), (int) (2 * maxY), BufferedImage.TYPE_3BYTE_BGR);

	}

	/**
	 * Draws cartesian axis on the image
	 */
	public void drawCartesianAxis() {

		Graphics2D g = resImg.createGraphics();
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		//x axis
		g.drawLine(0, (int) yTranslation, resImg.getWidth(), (int) yTranslation);
		for(int i = 0; i < (int) xTranslation; i += 1) {
			if(i != 0) {
				if(i % 10 == 0) {
					g.drawLine((int) xTranslation + i * redFact, (int) yTranslation - 2 , (int) xTranslation + i * redFact, (int) yTranslation + 2);
					g.drawString(String.valueOf(i), (int) xTranslation + i * redFact, (int) yTranslation + 10);
				}
				else {
					g.drawLine((int) xTranslation + i * redFact, (int) yTranslation - 1, (int) xTranslation + i * redFact, (int) yTranslation + 1);
				}
			}

		}		
		for(int i = 0; i > - (int) xTranslation; i -= 1) {
			if(i % 10 == 0) {
				g.drawLine((int) xTranslation + i * redFact, (int) yTranslation - 2, (int) xTranslation + i * redFact, (int) yTranslation + 2);
				g.drawString(String.valueOf(i), (int) xTranslation + i * redFact - 5, (int) yTranslation + 10);
			}
			else {
				g.drawLine((int) xTranslation + i * redFact, (int) yTranslation - 1, (int) xTranslation + i * redFact, (int) yTranslation + 1);
			}
		}

		//y axis
		g.drawLine((int) xTranslation, 0, (int) xTranslation, resImg.getHeight());
		for(int i = 0; i < (int) xTranslation; i += 1) {
			if(i != 0) {
				if(i % 10 == 0) {
					g.drawLine((int) xTranslation - 2, (int) yTranslation - i * redFact, (int) xTranslation + 2, (int) yTranslation - i * redFact);
					g.drawString(String.valueOf(i), (int) xTranslation - 20, (int) yTranslation - i * redFact);
				}
				else {
					g.drawLine((int) xTranslation + 1, (int) yTranslation - i * redFact, (int) xTranslation + 1, (int) yTranslation - i * redFact);
				}
			}

		}		
		for(int i = 0; i > - (int) xTranslation; i -= 1) {
			if(i != 0) {
				if(i % 10 == 0) {
					g.drawLine((int) xTranslation - 2, (int) yTranslation - i * redFact, (int) xTranslation + 2, (int) yTranslation - i * redFact);
					g.drawString(String.valueOf(i), (int) xTranslation - 20, (int) yTranslation - i * redFact);
				}
				else {
					g.drawLine((int) xTranslation + 1, (int) yTranslation - i * redFact, (int) xTranslation + 1, (int) yTranslation - i * redFact);
				}
			}

		}
	}

	/**
	 * Draws the grid on the drawing
	 */
	public void drawGrid() {
		Graphics2D g = resImg.createGraphics();

		Graphics2D g2d = (Graphics2D) g.create();

		//set the stroke of the copy, not the original 
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
		g2d.setStroke(dashed);

		//horizontal lines
		for(int i = 0; i < (int) xTranslation; i += 10) {
			if(i != 0) {
				g2d.drawLine(0, (int) yTranslation - i * redFact, resImg.getWidth(), (int) yTranslation - i * redFact);
			}
		}
		for(int i = 0; i > - (int) xTranslation; i -= 10) {
			if(i != 0) {
				g2d.drawLine(0, (int) yTranslation - i * redFact, resImg.getWidth(), (int) yTranslation - i * redFact);
			}

		}


		//vertical lines
		for(int i = 0; i < (int) xTranslation; i += 10) {
			if(i != 0) {
				g2d.drawLine((int) xTranslation + i * redFact, 0, (int) xTranslation + i * redFact, resImg.getHeight());
			}

		}		
		for(int i = 0; i > - (int) xTranslation; i -= 10) {
			if(i != 0) {
				g2d.drawLine((int) xTranslation + i * redFact, 0, (int) xTranslation + i * redFact, resImg.getHeight());
			}
		}

		//gets rid of the copy
		g2d.dispose();
	}
	
	/**
	 * Draws all the primitives
	 */
	public void drawShapes() {
		Graphics2D g = resImg.createGraphics();
		for(Primitive prim: primitives) {
			if(prim instanceof Point) {
				Point point = (Point) prim;
				int x = (int) (point.getX() * redFact + xTranslation);
				int y = (int) ( - (point.getY() * redFact) + yTranslation);
				g.drawString(".", x, y); 
			}
			if(prim instanceof Segment) {
				Segment s = (Segment) prim;
				int x1 = (int) (s.getP1().getX() * redFact + xTranslation);
				int y1 = (int) ( - (s.getP1().getY() * redFact) + yTranslation);
				int x2 = (int) (s.getP2().getX() * redFact + xTranslation);
				int y2 = (int) ( - (s.getP2().getY() * redFact) + yTranslation);
				g.drawLine(x1,  y1, x2, y2);
			}
			if(prim instanceof Ellipse) {
				Ellipse ellipse = (Ellipse) prim;
				int x = (int) (ellipse.getCenter().getX() * redFact + xTranslation);
				int y = (int) ( - (ellipse.getCenter().getX() * redFact) + yTranslation);
				//disgnare oval
			}
			//continuare con le altre primitive, discriminando tra poliliena e poligono e ellisse e cerchio
			//ricordarsi del riferimento nell'oval e altre robe...
			//polilinee e poligoni farli come sequenza di segnemti
		}
	}

	/**
	 * Finally exports the image to the selected location
	 * @param location: location where to export
	 * @throws IOException 
	 */
	public void exportJpegImage(File location) throws IOException {
		try {
			ImageIO.write(resImg, FORMAT, location);
		} catch (IOException e) {
			throw e;
		}
	}

	public static String getFormat() {
		return FORMAT;
	}

	public BufferedImage getResImg() {
		return resImg;
	}






}
