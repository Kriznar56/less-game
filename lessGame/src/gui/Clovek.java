package gui;
import java.util.LinkedList;

import logikaIgre.Igra;
import logikaIgre.Igralec;
import logikaIgre.Poteza;

public class Clovek extends Strateg {
	
	private Okno master;
	private Igralec jaz;
	
	public Clovek(Okno master, Igralec jaz) {
			this.master = master;
			this.jaz = jaz;
	}
	@Override
	public void na_potezi() {
	}

	@Override
	public void prekini() {
	}
	
	@Override
	public void tezavnost(int k) {

	}

	@Override
	public void klik(int i, int j) {
		//Ce je polje ze oznaceno potem odigra potezo iz oznacenega polja na kliknega
		if(master.polje.oznaceno) { //nisem preprican glede vidnosti polja.
			//Pogledamo ce je polje na katerega hocemo se ni zasedeno
			if(!master.jePloscek(i, j)) {
				LinkedList<Poteza> poteze = master.dobiPoteze();
				if(poteze.contains(new Poteza(IgralnoPolje.oznaceno_i, IgralnoPolje.oznaceno_j, i, j))) {
					master.odigraj(new Poteza(IgralnoPolje.oznaceno_i, IgralnoPolje.oznaceno_j, i, j));
					master.polje.oznaceno = false;
					master.polje.oznaceno_i = 7; 
					master.polje.oznaceno_j = 7; 
					
				}
			}
		}
	}


}
