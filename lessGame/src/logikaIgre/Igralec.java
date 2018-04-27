package logikaIgre;

public enum Igralec {
	BEL, CRN;
// dodaj metode nasprotnik, getPolje. Vzeto iz primera
// kaj bi blo tu getPolje nisem cist preprican.
	public Igralec nasprotnik(Igralec i) {
		if(i == Igralec.BEL) {
			return Igralec.CRN;
		}
		else return Igralec.BEL;
	}
	
	
}
