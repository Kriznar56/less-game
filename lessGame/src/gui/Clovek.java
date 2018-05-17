package gui;
import java.util.LinkedList;

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
	public void klik(int i, int j, boolean oznaceno) {
		//Ce je polje ze oznaceno potem odigra potezo iz oznacenega polja na kliknega
		if(oznaceno) {
			//Pogledamo ce je polje na katerega hocemo jiti ze zaseden
			if(master.jePloscek(i, j)) {
				LinkedList<Poteza> poteze = master.dobiPoteze();
				//Tuki je treba razrest, potezo mors povlect iz seznama legalnih potez, nemors naresto nove poteze ker je to nov objekt in ni enak objektom v seznamu legalnih potez
				//Nism cist zihr kako bi to naredu, ce mas idejo dej poprav. Tole spodi ni prav!
				master.odigraj(new Poteza(master.oznacenoPolje_i(), master.oznacenoPolje_j(), i, j));
			}
		}
	}

}
