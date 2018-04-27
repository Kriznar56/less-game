package logikaIgre;

public enum Polje {
	PRAZNO, BELO, CRNO;
	// vzeto iz primera.
	// polje potrebno prilagodit za ovire. vsakemu polju bi dodal atribute zgoraj, spodaj, levo in desno, ki bo boolean, ki pove, ali je tam ovira. posledično polje ne bo več enum ampak ??
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
