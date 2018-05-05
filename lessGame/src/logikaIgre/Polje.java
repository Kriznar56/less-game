package logikaIgre;

	
public class Polje {
	
	// vzeto iz primera.
	// Polje ima lahko na vse stiri strani oviro, ki jih na zacetku nastavimo na false
	public TipPolja tip;
	public boolean ovira_zgoraj = false;
	public boolean ovira_spodaj = false;
	public boolean ovira_levo = false;
	public boolean ovira_desno = false;
	@Override
	public String toString() {
		return "Polje [tip=" + tip + ", ovira_zgoraj=" + ovira_zgoraj + ", ovira_spodaj=" + ovira_spodaj
				+ ", ovira_levo=" + ovira_levo + ", ovira_desno=" + ovira_desno + "]";
	}


}
