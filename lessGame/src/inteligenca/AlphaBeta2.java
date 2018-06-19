package inteligenca;

import javax.swing.SwingWorker;


import gui.Okno;
import logikaIgre.Igra;
import logikaIgre.Igralec;
import logikaIgre.Poteza;

public class AlphaBeta2 extends SwingWorker<Poteza, Object> {
	
	private Okno master;
	private int globina;
	//Crni ali Beli igralec
	private Igralec jaz;
	
	public AlphaBeta2(Okno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
}

	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		OcenjenaPoteza p = alphaBeta2(0, igra, Ocena.ZGUBA*10, Ocena.ZMAGA*10 );
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

	private OcenjenaPoteza alphaBeta2(int g, Igra igra, int alpha, int beta) {
		Igralec naPotezi = null;
		switch (igra.stanje()) {
		case NA_POTEZI_BEL: naPotezi = Igralec.BEL; break;
		case NA_POTEZI_CRN: naPotezi = Igralec.CRN; break;
		case ZMAGAL_BEL: //ko zmaga, zeli zmagati v cim manj potezah in imeti cim vec kreditov, ko izgubiš želiš imeti cim boljsi polozaj, in zelis izgubiti cim kasneje.
			return new OcenjenaPoteza(null, (jaz == Igralec.BEL ? Ocena.ZMAGA+g*1000000+igra.krediti*100 : Ocena.ZGUBA-g*1000000-10*Ocena.oceniPozicijo(Igralec.CRN, igra)));
		case ZMAGAL_CRN:
			return new OcenjenaPoteza(null, (jaz == Igralec.CRN ? Ocena.ZMAGA+g*1000000+igra.krediti*100 : Ocena.ZGUBA-g*1000000-10*Ocena.oceniPozicijo(Igralec.BEL, igra)));
		}

		assert (naPotezi != null);
		if (g >= globina) {
			// dosegli smo najvecjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(null, Ocena.oceniPozicijo(jaz, igra));
		}
		//Naredimo prazen list najboljsih potez
		Poteza najboljsa = null;
		if(naPotezi == jaz) {//maksimiziramo
			int ocenaNajboljse = Ocena.ZGUBA*10;
			for(Poteza p: igra.seznam_legalnih_potez()) {
				Igra kopija = new Igra(igra);
				kopija.odigraj(p);
				if(kopija.naPotezi != naPotezi) {g++;}
				int trenutnaOcena = alphaBeta2(g, kopija, alpha, beta).vrednost;
				if(ocenaNajboljse < trenutnaOcena) {
					ocenaNajboljse = trenutnaOcena;
					najboljsa = p;
				}
				alpha = Math.max(alpha, ocenaNajboljse);
				if(beta <= alpha) {break;}
			}
			assert(najboljsa != null);
			return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
		}
			
		else{//na potezi nisem jaz in minimiziramo
			int ocenaNajslabse = Ocena.ZMAGA*10;
			for(Poteza p: igra.seznam_legalnih_potez()) {
				Igra kopija = new Igra(igra);
				kopija.odigraj(p);
				if(kopija.naPotezi != naPotezi) {g++;}
				int trenutnaOcena = alphaBeta2(g, kopija, alpha, beta).vrednost;
				if(ocenaNajslabse > trenutnaOcena) {
					ocenaNajslabse = trenutnaOcena;
					najboljsa = p;
				}
				beta = Math.min(beta, ocenaNajslabse);
				if(beta <= alpha) {break;}
			}
			assert(najboljsa != null);
			return new OcenjenaPoteza(najboljsa, ocenaNajslabse);
		}
		//gremo cez vse trenutno dovoljene poteze in jih v kopiji odigramo
	
			
		}
	}


	
	
