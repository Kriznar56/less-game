package logikaIgre;

public class Poteza {


	private int x_zacetna;
	private int y_zacetna;
	private int x_koncna;
	private int y_koncna;
	
	public Poteza(int x_start, int y_start, int x_final, int y_final) {
		super();
		this.x_zacetna = x_start;
		this.y_zacetna = y_start;
		this.x_koncna = x_final;
		this.y_koncna = y_final;
	}

	public int getX_start() {
		return x_zacetna;
	}

	public int getY_start() {
		return y_zacetna;
	}

	public int getX_final() {
		return x_koncna;
	}

	public int getY_final() {
		return y_koncna;
	}
	
	
/*	@Override pomoje ne rabva.
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x_koncna;
		result = prime * result + x_zacetna;
		result = prime * result + y_koncna;
		result = prime * result + y_zacetna;
		return result;
	}*/

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poteza other = (Poteza) obj;
		if (x_koncna != other.x_koncna)
			return false;
		if (x_zacetna != other.x_zacetna)
			return false;
		if (y_koncna != other.y_koncna)
			return false;
		if (y_zacetna != other.y_zacetna)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Poteza [x_z=" + x_zacetna + ", y_z=" + y_zacetna + ", x_k=" + x_koncna + ", y_k="
				+ y_koncna + "]";
	}
	
	
}
