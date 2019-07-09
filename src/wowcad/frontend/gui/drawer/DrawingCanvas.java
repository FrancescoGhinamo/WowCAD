package wowcad.frontend.gui.drawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JLabel;

import wowcad.backend.beam.drawing.Drawing;
import wowcad.backend.beam.shapes.Circle;
import wowcad.backend.beam.shapes.Ellipse;
import wowcad.backend.beam.shapes.Point;
import wowcad.backend.beam.shapes.Polygon;
import wowcad.backend.beam.shapes.Polyline;
import wowcad.backend.beam.shapes.Primitive;
import wowcad.backend.beam.shapes.Segment;

/**
 * Drawing canvas
 * @author franc
 *
 */
@SuppressWarnings("deprecation")
public class DrawingCanvas extends JLabel implements Observer, MouseListener {


	private static final long serialVersionUID = -5190658018626453048L;
	
	/**
	 * Pixel movement at each movement
	 */
	private static final int MOVE_FACTOR = 5;
	
	private static final double ZOOM_FACTOR = 0.1;
	
	

	/**
	 * Translation over x axis
	 */
	private double transX;

	/**
	 * Translation over y axis
	 */
	private double transY;

	/**
	 * Scale
	 */
	private double scale;

	/**
	 * True to draw axis
	 */
	private boolean axis;

	/**
	 * True to draw grid
	 */
	private boolean grid;

	/**
	 * Reduction factor
	 */
	private int redFact = 5;

	/**
	 * Linked drawing
	 */
	private Drawing drawing;
	
	/**
	 * Associated CoordReceiver
	 */
	private CoordReceiver cRec;
	
	/**
	 * Constructor with default values
	 */
	public DrawingCanvas() {
		this.transX = 0;
		this.transY = 0;
		this.scale = 1;
		this.axis = true;
		this.grid = true;
		this.drawing = null;
		this.addMouseListener(this);
	}
	
	


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(scale, scale);
		g2d.translate(transX, -transY);
		this.setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		if(axis) {
			drawCartesianAxis(g);
		}
		if(grid) {
			drawGrid(g);
		}
		drawShapes(g);
		
		
		
	}

	
	/**
	 * Draws cartesian axis on the image
	 */
	private void drawCartesianAxis(Graphics gr) {

		int xTranslation = this.getWidth() / 2;
		int yTranslation = this.getHeight() / 2;
		int dx = 0;
		int dy = 0;
		try {
			dx = (int) (Math.abs(transX) * redFact + this.getWidth() / scale);
			dy = (int) (Math.abs(transY) * redFact + this.getHeight() / scale);
		}
		catch(ArithmeticException e) {}

		Graphics2D g = (Graphics2D) gr;
		g.setFont(new Font("Arial", Font.PLAIN, 10));
		//x axis
		g.drawLine(-dx, (int) yTranslation, this.getWidth() + dx, (int) yTranslation);
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
		g.drawLine((int) xTranslation, -dy, (int) xTranslation, this.getHeight() + dy);
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
	private void drawGrid(Graphics gr) {
		int xTranslation = this.getWidth() / 2;
		int yTranslation = this.getHeight() / 2;
		
		int dx = 0;
		int dy = 0;
		try {
			dx = (int) (Math.abs(transX) * redFact + this.getWidth() / scale);
			dy = (int) (Math.abs(transY) * redFact + this.getHeight() / scale);
		}
		catch(ArithmeticException e) {}

		Graphics2D g = (Graphics2D) gr;

		Graphics2D g2d = (Graphics2D) g.create();

		//set the stroke of the copy, not the original 
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{2}, 0);
		g2d.setStroke(dashed);

		//horizontal lines
		for(int i = 0; i < (int) xTranslation; i += 10) {
			if(i != 0) {
				g2d.drawLine(-dx, (int) yTranslation - i * redFact, this.getWidth() + dx, (int) yTranslation - i * redFact);
			}
		}
		for(int i = 0; i > - (int) xTranslation; i -= 10) {
			if(i != 0) {
				g2d.drawLine(-dx, (int) yTranslation - i * redFact, this.getWidth() + dx, (int) yTranslation - i * redFact);
			}

		}


		//vertical lines
		for(int i = 0; i < (int) xTranslation; i += 10) {
			if(i != 0) {
				g2d.drawLine((int) xTranslation + i * redFact, -dy, (int) xTranslation + i * redFact, this.getHeight() + dy);
			}

		}		
		for(int i = 0; i > - (int) xTranslation; i -= 10) {
			if(i != 0) {
				g2d.drawLine((int) xTranslation + i * redFact, -dy, (int) xTranslation + i * redFact, this.getHeight() + dy);
			}
		}

		//gets rid of the copy
		g2d.dispose();
	}

	/**
	 * Draws all the primitives
	 */
	private void drawShapes(Graphics gr) {
		int xTranslation = this.getWidth() / 2;
		int yTranslation = this.getHeight() / 2;

		if(drawing != null) {

			ArrayList<Primitive> primitives = new ArrayList<Primitive>();

			Set<String> keys = drawing.getPrimitives().keySet();
			
			for(String k: keys) {				
				primitives.add(drawing.getPrimitives().get(k));				
			}

			Graphics2D g = (Graphics2D) gr;
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
				if(prim instanceof Circle) {
					Circle circle = (Circle) prim;
					int x = (int) (circle.getCenter().getX() * redFact + xTranslation);
					int y = (int) ( - (circle.getCenter().getX() * redFact) + yTranslation);
					x -= circle.getRadius() * redFact;
					y -= circle.getRadius() * redFact;
					g.drawOval(x, y, (int) circle.getRadius() * redFact * 2, (int) circle.getRadius() * redFact * 2); 

				}
				else if(prim instanceof Ellipse) {
					Ellipse ellipse = (Ellipse) prim;
					int x = (int) (ellipse.getCenter().getX() * redFact + xTranslation);
					int y = (int) ( - (ellipse.getCenter().getX() * redFact) + yTranslation);
					x -= ellipse.getxRadius() * redFact;
					y -= ellipse.getyRadius() * redFact;
					g.drawOval(x, y, (int) ellipse.getxRadius() * redFact * 2, (int) ellipse.getyRadius() * redFact * 2); 
				}
				if(prim instanceof Polygon) {
					Polygon pg = (Polygon) prim;
					ArrayList<Point> points = pg.getPoints();
					Point prev = points.get(0);
					for(int i = 1; i < points.size(); i++) {
						Point newP = points.get(i);
						int x1 = (int) (prev.getX() * redFact + xTranslation);
						int y1 = (int) ( - (prev.getY() * redFact) + yTranslation);
						int x2 = (int) (newP.getX() * redFact + xTranslation);
						int y2 = (int) ( - (newP.getY() * redFact) + yTranslation);
						g.drawLine(x1,  y1, x2, y2);
						prev = newP;
					}
					prev = points.get(points.size() - 1);
					Point newP = points.get(0);
					int x1 = (int) (prev.getX() * redFact + xTranslation);
					int y1 = (int) ( - (prev.getY() * redFact) + yTranslation);
					int x2 = (int) (newP.getX() * redFact + xTranslation);
					int y2 = (int) ( - (newP.getY() * redFact) + yTranslation);
					g.drawLine(x1,  y1, x2, y2);

				}
				else if(prim instanceof Polyline) {
					Polyline pl = (Polyline) prim;
					ArrayList<Point> points = pl.getPoints();
					Point prev = points.get(0);
					for(int i = 1; i < points.size(); i++) {
						Point newP = points.get(i);
						int x1 = (int) (prev.getX() * redFact + xTranslation);
						int y1 = (int) ( - (prev.getY() * redFact) + yTranslation);
						int x2 = (int) (newP.getX() * redFact + xTranslation);
						int y2 = (int) ( - (newP.getY() * redFact) + yTranslation);
						g.drawLine(x1,  y1, x2, y2);
						prev = newP;
					}
				}
			}
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Drawing) {
			drawing = (Drawing) o;
			repaint();
		}

	}
	
	public void parseAction(ActionCommand c) {
		switch(c) {
		case HIDE_AXIS:
			axis = false;
			break;
		case HIDE_GRID:
			grid = false;
			break;
		case MOVE_DOWN:
			transY += MOVE_FACTOR;
			break;
		case MOVE_LEFT:
			transX += MOVE_FACTOR;
			break;
		case MOVE_UP:
			transY -= MOVE_FACTOR;
			break;
		case MOVE_RIGHT:
			transX -= MOVE_FACTOR;
			break;
		case SHOW_AXIS:
			axis = true;
			break;
		case SHOW_GRID:
			grid = true;
			break;
		case ZOOM_IN:
			scale += ZOOM_FACTOR;
			break;
		case ZOOM_OUT:
			scale -= ZOOM_FACTOR;
			break;
			
		case RESET_VIEW:
			transX = 0;
			transY = 0;
			scale = 1;
		default:
			break;
		
		}
		repaint();
	}

	public void setAxis(boolean axis) {
		this.axis = axis;
	}

	public void setGrid(boolean grid) {
		this.grid = grid;
	}




	public void setcRec(CoordReceiver cRec) {
		this.cRec = cRec;
	}




	@Override
	public void mouseClicked(MouseEvent e) {
		int rawX = e.getX();
		int rawT = e.getY();
		
		//x component
		double fX = rawX;
		fX -= this.getWidth() / 2;
		fX /= redFact;
		
		
		//y component
		double fY = 0;
		
		
		if(cRec != null) {
			cRec.onReceive(fX, fY);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	

}
