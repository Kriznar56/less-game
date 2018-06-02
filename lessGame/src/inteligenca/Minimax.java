package inteligenca;

import java.util.LinkedList;
import java.util.Random;

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
	//private LinkedList<Poteza> zeOdigrane = new LinkedList<Poteza>();
//	private LinkedList<Poteza> najboljseOdigrane = new LinkedList<Poteza>();
	//private int ocenaNajboljse = 36;
	//private boolean zamenjalo = false;
	
	public Minimax(Okno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
}

	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.copyIgra();
		//Thread.sleep(500);
		//System.out.println("__________________________________________________________");
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
		LinkedList<Poteza> najboljsePoteze= new LinkedList<Poteza>();
		int ocenaNajboljse = 0;
		//gremo cez vse trenutno dovoljene poteze in jih v kopiji odigramo
		for(Poteza p: igra.seznam_legalnih_potez) { 
			Igra kopija = new Igra(igra);
			kopija.odigraj(p);
			if(kopija.naPotezi != naPotezi) {g++;}
			int ocenaP = minimax(g, kopija).vrednost;
			if (najboljsePoteze.isEmpty() // še nimamo kandidata za najboljšo potezo
					|| (naPotezi == jaz && ocenaP >= ocenaNajboljse) // maksimiziramo
					|| (naPotezi != jaz && ocenaP <= ocenaNajboljse) // minimiziramo
					) {
				if((naPotezi == jaz && ocenaP > ocenaNajboljse)
						|| (naPotezi != jaz && ocenaP < ocenaNajboljse)) {
					najboljsePoteze.clear();
					najboljsePoteze.add(p);
					ocenaNajboljse = ocenaP;
				}
				else {najboljsePoteze.add(p);}
			}
		}
		assert (!najboljsePoteze.isEmpty());
		Random r = new Random();
		return new OcenjenaPoteza(najboljsePoteze.get(r.nextInt(najboljsePoteze.size())), ocenaNajboljse);
	}


	}
	
