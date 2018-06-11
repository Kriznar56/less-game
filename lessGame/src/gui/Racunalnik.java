package gui;

import javax.swing.SwingWorker;

import inteligenca.AlphaBeta;
import inteligenca.Minimax;
import logikaIgre.Igralec;
import logikaIgre.Poteza;

// samo stub. morava se vse dodati.

public class Racunalnik extends Strateg {
	private Okno master;
	private Igralec jaz;
	private SwingWorker<Poteza, Object> mislec;
	private int stopnja = 3;
	public Racunalnik(Okno master, Igralec jaz) {
		this.master = master;
		this.jaz = jaz;
	}
	@Override
	public void na_potezi() {
		// Začnemo razmišljati
		
		mislec = new Minimax(master, stopnja, jaz);
		mislec.execute();
	}

	@Override
	public void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
}

	@Override
	public void klik(int i, int j) {
	}
	
	@Override
	public void tezavnost(int k) {
		stopnja = k;
	}

}
