package inteligenca;

import javax.swing.SwingWorker;


import gui.Okno;
import logikaIgre.Igra;
import logikaIgre.Igralec;
import logikaIgre.Poteza;

//Algoritem alpha beta pruning
public class AlphaBeta extends SwingWorker<Poteza, Object> {
	
	private Okno master;
	private int globina;
	private Igralec jaz;
	
	public AlphaBeta(Okno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
}

	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = alphaBeta(0, igra, -(1 << 20), (1 << 20), true);
		assert (p.poteza != null);
		return p.poteza;
	}
	
	@Override
	public void done() {
		try {
			Poteza p = this.get();
			if (p != null) { 
				master.odigraj(p);
				}
		} catch (Exception e) {
		}
	}

	private OcenjenaPoteza alphaBeta(int g, Igra igra, int alpha, int beta, boolean maksimiziramo) {
		Igralec naPotezi = null;
		switch (igra.stanje()) {
		case NA_POTEZI_BEL: naPotezi = Igralec.BEL; break;
		case NA_POTEZI_CRN: naPotezi = Igralec.CRN; break;
		case ZMAGAL_BEL:
			return new OcenjenaPoteza(null, (jaz == Igralec.BEL ? Ocena.ZMAGA : Ocena.ZGUBA));
		case ZMAGAL_CRN:
			return new OcenjenaPoteza(null, (jaz == Igralec.CRN ? Ocena.ZMAGA : Ocena.ZGUBA));
		}
		assert (naPotezi != null);
		if (g >= globina) {
			return new OcenjenaPoteza(null, Ocena.oceniPozicijo(jaz, igra));
		}
		Poteza najboljsa = null;
		int ocenaNajboljse = 0;
		if(maksimiziramo) {
			int ocenaP = -(1 << 20);
			for(Poteza p: igra.seznam_legalnih_potez) {
				Igra kopija = new Igra(igra);
				kopija.odigraj(p);
				if(kopija.naPotezi != naPotezi) {
					g++;
					maksimiziramo = !maksimiziramo;
				}
				ocenaP = Math.max(ocenaP, alphaBeta(g, kopija, alpha, beta, maksimiziramo).vrednost);
				alpha = Math.max(alpha, ocenaP);
				if (najboljsa == null || ocenaP > ocenaNajboljse) {
					najboljsa = p;
					ocenaNajboljse = ocenaP;
				}
				if(beta<=alpha) {
					break;
				}
			}
		}
		else{
			int ocenaP = (1 << 20);
			for(Poteza p: igra.seznam_legalnih_potez) {
				Igra kopija = new Igra(igra);
				kopija.odigraj(p);
				if(kopija.naPotezi != naPotezi) {
					g++;
					maksimiziramo = !maksimiziramo;
				}
				ocenaP = Math.min(ocenaP, alphaBeta(g, kopija, alpha, beta, maksimiziramo).vrednost);
				beta = Math.min(beta, ocenaP);
				if (najboljsa == null || ocenaP < ocenaNajboljse) {
					najboljsa = p;
					ocenaNajboljse = ocenaP;
				}
				if(beta<=alpha) {
					break;
				}
			}
		}
		assert (najboljsa != null);
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
	}


	}
