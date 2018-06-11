package logikaIgre;

	
public class Polje {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ovira_desno ? 1231 : 1237);
		result = prime * result + (ovira_levo ? 1231 : 1237);
		result = prime * result + (ovira_spodaj ? 1231 : 1237);
		result = prime * result + (ovira_zgoraj ? 1231 : 1237);
		result = prime * result + ((tip == null) ? 0 : tip.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Polje other = (Polje) obj;
		if (ovira_desno != other.ovira_desno)
			return false;
		if (ovira_levo != other.ovira_levo)
			return false;
		if (ovira_spodaj != other.ovira_spodaj)
			return false;
		if (ovira_zgoraj != other.ovira_zgoraj)
			return false;
		if (tip != other.tip)
			return false;
		return true;
	}


}
