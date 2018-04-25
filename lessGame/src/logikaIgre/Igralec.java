package logikaIgre;

public enum Igralec {
	BEL, CRN;
// dodaj metode nasprotnik, getPolje. Vzeto iz primera
// kaj bi blo tu getPolje nisem čist prepričan.
	public Igralec nasprotnik(Igralec i) {
		if(i == Igralec.BEL) {
			return Igralec.CRN;
		}
		else return Igralec.BEL;
	}
	
	
}
