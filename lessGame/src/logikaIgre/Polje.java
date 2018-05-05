package logikaIgre;

public enum Polje {
	PRAZNO, BELO, CRNO;
	// vzeto iz primera.
	// Polje ima lahko na vse stiri strani oviro, ki jih na zacetku nastavimo na false
	public boolean ovira_zgoraj = false;
	public boolean ovira_spodaj = false;
	public boolean ovira_levo = false;
	public boolean ovira_desno = false;

	public String toString() {
		switch(this) {
		case PRAZNO: return " ";
		case BELO: return "BELO";
		case CRNO: return "CRNO";
		default: return "?"; // <- zakaj rabimo to? pojavi se v primeru 
		}
	}
}
