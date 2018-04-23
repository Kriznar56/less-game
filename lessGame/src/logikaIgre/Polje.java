package logikaIgre;

public enum Polje {
	PRAZNO, BELO, CRNO;
	// vzeto iz primera.
	public String toString() {
		switch(this) {
		case PRAZNO: return " ";
		case BELO: return "BELO";
		case CRNO: return "CRNO";
		default: return "?"; // <- zakaj rabimo to? pojavi se v primeru 
		}
	}
}
