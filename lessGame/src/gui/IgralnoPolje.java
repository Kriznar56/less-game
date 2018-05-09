package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logikaIgre.Igra;

public class IgralnoPolje extends JPanel implements MouseListener {
	//mogoce bi se lahko odlocila za en jezik
	private Okno master;
	
	//line width nekoliko manjsi kot v tictactoe primeru
	private final static double LINE_WIDTH = 0.025;
	// cez padding bodo prisle ovire, kjer bodo pac generirane.
	private final static double PADDING = 0.1;
	

	public IgralnoPolje(Okno master) {
		super();
		this.master = master;
		setBackground(Color.GRAY);
		this.addMouseListener(this);
		}

	//naslikaj CRNO in BELO vzeto iz tictactoe, tako kot vecina tega dela kode.
	private void naslikajCRNO(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
	}
	
	private void naslikajBELO(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
	}
	
	// manjka metoda za ovire
	
	private void naslikajOVIRO(Graphics2D g2, int i, int j) {
		double h = squareWidth();
		double w = PADDING;
		g2.setColor(Color.blue); //morda bi lahko zamenjala barvo.
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH))); //rabiva tu sploh stroke, pravokotnik bo itak nafilan?
		g2.fillRect((int)i, (int)j, (int)w, (int)h);
		
	}
	

	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}

	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / Igra.N;
}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
