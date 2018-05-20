package gui;
import java.util.LinkedList;

import logikaIgre.Igra;
import logikaIgre.Poteza;

public class Clovek extends Strateg {
	
	private Okno master;
	
	public Clovek(Okno master) {
			this.master = master;
	}
	@Override
	public void na_potezi() {
		// TODO Auto-generated method stub

	}

	@Override
	public void prekini() {
		// TODO Auto-generated method stub

	}

	@Override
	public void klik(int i, int j) {
		//Ce je polje ze oznaceno potem odigra potezo iz oznacenega polja na kliknega
		if(master.polje.oznaceno) { //nisem prepričan glede vidnosti polja.
			//Pogledamo ce je polje na katerega hocemo se ni zasedeno
			if(!master.jePloscek(i, j)) {
				LinkedList<Poteza> poteze = master.dobiPoteze();
				if(poteze.contains(new Poteza(IgralnoPolje.oznaceno_i, IgralnoPolje.oznaceno_j, i, j))) {
					master.odigraj(new Poteza(IgralnoPolje.oznaceno_i, IgralnoPolje.oznaceno_j, i, j));
					master.polje.oznaceno = false;
					master.polje.oznaceno_i = null; //nevem kako to popravt
					master.polje.oznaceno_j = null; // nevem kako to poprvt
					
				}
			}
		}
	}

}
