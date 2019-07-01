package testing;

import java.awt.Color;

import javax.swing.JFrame;

import wowcad.frontend.gui.drawer.DrawingRegion;

public class TestFrame extends JFrame {

	
	private static final long serialVersionUID = -8850567906056092003L;
	
	public TestFrame() {
		super("Test");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(new DrawingRegion(this, null));
		this.setBackground(Color.BLACK);
	}

	public static void main(String[] args) {
		new TestFrame().setVisible(true);

	}

}
