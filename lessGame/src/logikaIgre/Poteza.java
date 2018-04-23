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
	
	public int cena() {
		// Koliko moramo odsteti od preostalih potez za to potezo.
		int cena = Math.abs(y_koncna-y_zacetna) + Math.abs(x_koncna-x_zacetna);
		// dodaj odvisnost od tega, ce kaj preskoci -> preverjamo plosco?. Tu ne preverjamo legalnosti poteze.
		return cena; 
	}
	
}
