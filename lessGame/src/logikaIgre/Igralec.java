package logikaIgre;

public enum Igralec {
	BEL, CRN;
	public Igralec nasprotnik(Igralec i) {
		if(i == Igralec.BEL) {
			return Igralec.CRN;
		}
		else return Igralec.BEL;
	}
	
	
}
