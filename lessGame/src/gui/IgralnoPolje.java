package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JPanel;

import logikaIgre.Igra;
import logikaIgre.Polje;
import logikaIgre.Stanje;
import logikaIgre.TipPolja;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {
	//mogoce bi se lahko odlocila za en jezik
	private Okno master;
	//line width nekoliko manjsi kot v tictactoe primeru
	private final static double LINE_WIDTH = 0.025;
	// cez padding bodo prisle ovire, kjer bodo pac generirane.
	private final static double PADDING = 0.1;
	static int oznaceno_i; //mogoce bols point?
	static int oznaceno_j;
	private boolean oznaci = false;
	protected boolean oznaceno = false;
	private boolean ovire = true;
	

	public IgralnoPolje(Okno master) {
		super();
		this.master = master;
		setBackground(new Color(0.5f, 0.5f, 0.5f));
		this.addMouseListener(this);
		}

	//naslikaj CRNO in BELO vzeto iz tictactoe, tako kot vecina tega dela kode.
	private void naslikajCRNO(Graphics2D g2, int i, int j) {
		double w = sirina_polja();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
	}
	
	private void naslikajBELO(Graphics2D g2, int i, int j) {
		double w = sirina_polja();
		double r = w * (1.0 - LINE_WIDTH - 2.0 * PADDING); // premer O
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		g2.fillOval((int)x, (int)y, (int)r , (int)r);
	}
	
	
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}

	private double sirina_polja() {
		return Math.min(getWidth(), getHeight()) / Igra.N;
}
	
	protected void paintComponent(Graphics g) {
		Polje[][] plosca = master.getPlosca();
		// se nepopravljeno za najino igro.
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		// sirina kvadratka
		double w = sirina_polja();
		// črte
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		// naraisi polje
		g2.drawLine(0, 0, 0, (int)((Igra.N - LINE_WIDTH) * w));
		g2.drawLine(0, 0, (int)((Igra.N - LINE_WIDTH) * w), 0);
		g2.drawLine((int)((Igra.N - LINE_WIDTH/4) * w), 0, (int)((Igra.N - LINE_WIDTH/4) * w), (int)((Igra.N - LINE_WIDTH) * w));
		g2.drawLine(0, (int)((Igra.N - LINE_WIDTH/4) * w), (int)((Igra.N - LINE_WIDTH) * w), (int)((Igra.N - LINE_WIDTH/4) * w));
		for (int k = 1; k < Igra.N; k++) {
			g2.drawLine((int)(k * w),
					    (int)(LINE_WIDTH * w),
					    (int)(k * w),
					    (int)((Igra.N - LINE_WIDTH) * w));
			g2.drawLine((int)(LINE_WIDTH * w),
					    (int)(k * w),
					    (int)((Igra.N - LINE_WIDTH) * w),
					    (int)(k * w));
		}
		if(oznaci) {
			g2.setColor(Color.orange);
			//y crta
			g2.drawLine((int)(oznaceno_i * w),
				    (int)(oznaceno_j * w),
				    (int)(oznaceno_i * w),
				    (int)((oznaceno_j + 1) * w));
			//x crta
			g2.drawLine((int)(oznaceno_i * w),
				    (int)(oznaceno_j * w),
				    (int)((oznaceno_i + 1) * w),
				    (int)(oznaceno_j * w));
			g2.drawLine((int)((oznaceno_i+1) * w),
				    (int)(oznaceno_j * w),
				    (int)((oznaceno_i+1) * w),
				    (int)((oznaceno_j + 1) * w));
			g2.drawLine((int)(oznaceno_i * w),
				    (int)((oznaceno_j+1) * w),
				    (int)((oznaceno_i + 1) * w),
				    (int)((oznaceno_j+1) * w));
			oznaceno = true;
			LinkedList<Integer> moznaPolja = master.mozniPremiki(oznaceno_i, oznaceno_j);
			g2.setColor(new Color(1.000f, 0.647f, 0.000f, 0.3f));
			for(int i = 0; i < moznaPolja.size(); i+=2){
				g2.fillRect((int)((LINE_WIDTH + moznaPolja.get(i))*w), (int)((LINE_WIDTH + moznaPolja.get(i+1))*w), (int)(w * (1- LINE_WIDTH)), (int)(w * (1- LINE_WIDTH)));
			}
			// verjetno se oznaci na false?
		}
		//ovire
		g2.setColor(Color.red);
		for(int i = 0; i < Igra.N; i++) {
			for(int j = 0; j < Igra.N; j++) {
				if(plosca[i][j].ovira_zgoraj) {
					g2.drawLine((int)(i * w + w*LINE_WIDTH),
						    (int)(j * w + w*LINE_WIDTH),
						    (int)((i+1) * w - w*LINE_WIDTH),
						    (int)(j * w + w*LINE_WIDTH));
				}
				if(plosca[i][j].ovira_desno) {
					g2.drawLine((int)((i+1) * w - w/2*LINE_WIDTH),
						    (int)(j * w + w*LINE_WIDTH),
						    (int)((i+1) * w - w/2*LINE_WIDTH),
						    (int)((j+1) * w - w*LINE_WIDTH));
				}
				if(plosca[i][j].ovira_spodaj) {
					g2.drawLine((int)(i * w + w*LINE_WIDTH),
						    (int)((j+1) * w - 2*w/3*LINE_WIDTH),
						    (int)((i+1) * w - w*LINE_WIDTH),
						    (int)((j+1) * w - 2*w/3*LINE_WIDTH));
				}
				if(plosca[i][j].ovira_levo) {
					g2.drawLine((int)(i * w + w*LINE_WIDTH),
						    (int)(j * w + w*LINE_WIDTH),
						    (int)(i * w + w*LINE_WIDTH),
						    (int)((j+1) * w - w*LINE_WIDTH));
				}
			}
		}
		// bele crne
		if (plosca != null) {
			g2.setColor(Color.BLUE);
			for (int k = 0; k < Igra.N; k++) {
				for (int l = 0; l < Igra.N; l++) {
					//risanje ovir
					switch(plosca[k][l].tip) {
					case BELO: naslikajBELO(g2, k, l); break;
					case CRNO: naslikajCRNO(g2, k, l); break;
					default: break;
					}
				}
			}
		}
		// dodati morava se ovire in neko metodo da popravi aktivno polje., verjetno mora biti torej tu nekje aktivno?
	}
	@Override // Isto kot pri TicTacToe
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int w = (int)(sirina_polja());
		int i = x / w;
		int j = y / w;
		double di = (x % w) / sirina_polja() ;
		double dj = (y % w) / sirina_polja() ;
		if(0<=i && i<Igra.N &&
		   0<=j && j<Igra.N &&
		   0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
		   0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
			master.klikniPolje(i, j);
			oznaci_polje(i, j);
		}
		
	}
	//Oznaci polje
	private void oznaci_polje(int i, int j) {
		Polje[][] plosca = master.getPlosca();
		if(master.trenutnoStanje()==Stanje.NA_POTEZI_BEL) {
			if(plosca[i][j].tip == TipPolja.BELO) {
				if(i!=oznaceno_i || j!=oznaceno_j) {
					oznaceno_i = i;
					oznaceno_j = j;
					oznaci = true;
					repaint();
				}
			}
		}
		if(master.trenutnoStanje()==Stanje.NA_POTEZI_CRN) {
			if(plosca[i][j].tip == TipPolja.CRNO) {
				if(i!=oznaceno_i || j!=oznaceno_j) {
					oznaceno_i = i;
					oznaceno_j = j;
					oznaci = true;
					repaint();
				}
			}
		}
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
