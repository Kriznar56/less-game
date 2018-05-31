package inteligenca;

import java.util.LinkedList;
import java.util.Random;
//import java.util.concurrent.TimeUnit;

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
			if (p != null) { master.odigraj(p); }
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
		LinkedList<Poteza> najboljsePoteze = new LinkedList<Poteza>();
		int ocenaNajboljse = 0;
		//gremo cez vse trenutno dovoljene poteze in jih v kopiji odigramo
		
		for(Poteza p: igra.seznam_legalnih_potez) { 						//ERROR: Zakaj ne pride ven iz for loopa in gre samo 1x vanj?
			Igra kopija = new Igra(igra);
			kopija.odigraj(p);
			int ocenaP;
			//ce se je po odigrani potezi zamenjal igralec na potezi povecamo globino, drugace ne
			if(kopija.naPotezi == naPotezi) {
				System.out.println("sem na potezi"+g);
				ocenaP = minimax(g, kopija).vrednost;
				
			}
			else {
				ocenaP = minimax(g+1, kopija).vrednost;
				System.out.println("nisem na potezi"+g);
				
			}
			// �e je p bolj�a poteza, si jo zabele�imo
			if ((naPotezi == jaz && ocenaP >= ocenaNajboljse) // maksimiziramo
				|| (naPotezi != jaz && ocenaP <= ocenaNajboljse) // minimiziramo
				) {
				System.out.println("maksimiziramo");
				System.out.println(ocenaP);
				//ce je ocena trenutne enaka oceni najboljse jo dodamo na seznam najboljsih potez
				if(ocenaP==ocenaNajboljse) {
					najboljsePoteze.add(p);
				}
				//ce najdemo boljso potezo kot trenutne najboljse pobrisemo trenutne najboljse ter dodamo to potezo
				else if((najboljsePoteze.isEmpty()
						|| (naPotezi == jaz && ocenaP > ocenaNajboljse) 
						|| (naPotezi != jaz && ocenaP < ocenaNajboljse))){
					najboljsePoteze.clear();
					najboljsePoteze.add(p);
					ocenaNajboljse = ocenaP;	
				}
			}
			
		}
		System.out.println(najboljsePoteze);
		assert (najboljsePoteze != null);
		Random rand = new Random();
		return new OcenjenaPoteza(najboljsePoteze.get(rand.nextInt(najboljsePoteze.size())), ocenaNajboljse);
	}

}
