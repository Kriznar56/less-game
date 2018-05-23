package inteligenca;

import java.util.LinkedList;
import java.util.Random;

import javax.swing.SwingWorker;

import gui.Okno;
import logikaIgre.Igra;
import logikaIgre.Igralec;
import logikaIgre.Poteza;

public class Minimax extends SwingWorker<Poteza, Object> {
	
	private Okno master;
	private int globina;
	//Crni ali Beli igralec
	private Igralec jaz;
	
	public Minimax(Okno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
}

	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = minimax(0, igra);
		assert (p.poteza != null);
		return p.poteza;
	}

	private OcenjenaPoteza minimax(int g, Igra igra) {
		Igralec naPotezi = null;
		switch (igra.stanje()) {
		case NA_POTEZI_BEL: naPotezi = Igralec.BEL; break;
		case NA_POTEZI_CRN: naPotezi = Igralec.CRN; break;
		case ZMAGAL_BEL:
			return new OcenjenaPoteza(
					null,
					(jaz == Igralec.BEL ? Ocena.ZMAGA : Ocena.ZGUBA));
		case ZMAGAL_CRN:
			return new OcenjenaPoteza(
					null,
					(jaz == Igralec.CRN ? Ocena.ZMAGA : Ocena.ZGUBA));
		}
		assert (naPotezi != null);
		if (g >= globina) {
			// dosegli smo najvecjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(null, Ocena.oceniPozicijo(jaz, igra));
		}
		LinkedList<Poteza> najboljsePoteze = new LinkedList<Poteza>();
		int ocenaNajboljse = 0;
		for(Poteza p: igra.seznam_legalnih_potez) {
			Igra kopija = new Igra(igra);
			kopija.odigraj(p);
			int ocenaP = minimax(g+1, kopija).vrednost;
			// Èe je p boljša poteza, si jo zabeležimo
			if (najboljsePoteze == null // še nimamo kandidata za najboljšo potezo
				|| (naPotezi == jaz && ocenaP >= ocenaNajboljse) // maksimiziramo
				|| (naPotezi != jaz && ocenaP <= ocenaNajboljse) // minimiziramo
				) {
				if(ocenaP==ocenaNajboljse)
					najboljsePoteze.add(p);
				else {
					najboljsePoteze.clear();
					najboljsePoteze.add(p);
					ocenaNajboljse = ocenaP;	
				}
			}
		}
		assert (najboljsePoteze != null);
		Random rand = new Random();
		return new OcenjenaPoteza(najboljsePoteze.get(rand.nextInt(najboljsePoteze.size())), ocenaNajboljse);
	}

}
