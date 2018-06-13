package inteligenca;

//import java.util.LinkedList;
//import java.util.Random;


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

	private OcenjenaPoteza minimax(int g, Igra igra) {
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
			// dosegli smo najvecjo dovoljeno globino, zato
			// ne vrnemo poteze, ampak samo oceno pozicije
			return new OcenjenaPoteza(null, Ocena.oceniPozicijo(jaz, igra));
		}
		//Naredimo prazen list najboljsih potez
		Poteza najboljsa = null;
		int ocenaNajboljse = 0;
		//gremo cez vse trenutno dovoljene poteze in jih v kopiji odigramo
		for(Poteza p: igra.seznam_legalnih_potez) { 
			Igra kopija = new Igra(igra);
			kopija.odigraj(p);
			if(kopija.naPotezi != naPotezi) {g++;}
			int ocenaP = minimax(g, kopija).vrednost;
			
			if (najboljsa == null // še nimamo kandidata za najboljšo potezo
					|| (naPotezi == jaz && ocenaP > ocenaNajboljse) // maksimiziramo
					|| (naPotezi != jaz && ocenaP < ocenaNajboljse) // minimiziramo
					) {
					najboljsa = p;
					ocenaNajboljse = ocenaP;
			}
		}
		assert (najboljsa != null);
		
		//if(ocenaNajboljse > (10 << 19 )) {
			System.out.println(ocenaNajboljse);
			System.out.println(jaz);
		//}
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
	}


	}
	
