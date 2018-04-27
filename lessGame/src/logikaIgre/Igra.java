package logikaIgre;

import java.awt.Point;
import java.util.LinkedList;



public class Igra {
	// Work in progress.
	public static final int N = 6; //<- delava samo za dva igralca oz. vs. racunalnik.
	
	private Polje[][] plosca;
	private Igralec naPotezi;
	private int krediti;
	private LinkedList<Point> zacetna_crna;
	private LinkedList<Point> zacetna_bela;  // uporabni listi pri preverjanju zmagovalca
	public LinkedList<Poteza> seznam_legalnih_potez;
	/**
	 * List manjkajocih funkcij
	 * - Odigraj <- preveris, ce je poteza legalna in jo odigras.
	 * - Stanje <- vrne, kdo je na potezi oziroma ce je igre konec in kdo je zmagal.
	 * - List potez <- preverjas, katere legalne glede na ceno/ preostale poteze in preverjas ce ostane na plosci.
	 */
	
	{ 		
		// Iniciraliziramo zacetne bele in crne
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(i>= 4 && j<=1) {
					zacetna_bela.add(new Point(i,j));
				}
				if(i<=1 && j>= 4) {
					zacetna_crna.add(new Point(i,j));
				}
			}	
		}

	}
	
	
	
	/**
	 * Generira začetno stanje igre, na potezi bel igralec in ima 3 preostale poteze.
	 * Beli zacne desno spodaj, crni levo zgoraj.
	 * Kasneje bomo dodali se stene/ovire, kot so v polni igri.
	 * ko bodo dodane ovire je treba tudi naključno generirati ploščo.
	 */
	public Igra() {
		plosca = new Polje[N][N];
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(i>= 4 && j<=1) {
					plosca[i][j] = Polje.BELO;
				}
				if(i<=1 && j>= 4) {
					plosca[i][j] = Polje.CRNO;
				}
				else {
					plosca[i][j] = Polje.PRAZNO;
					}
			}
		}
		naPotezi = Igralec.BEL;
		krediti = 3;
		posodobi_legalne_poteze();
	}
	
	
	public Stanje stanje() {
		int crne = 0;
		int bele = 0;
		for(Point p: zacetna_bela) {
			if (plosca[p.x][p.y] == Polje.CRNO) {
				crne++;
				continue;
			}
			else {break;}
		}
		if(crne == 4) {
			return Stanje.ZMAGAL_CRN;
		}
		for(Point p: zacetna_crna) {
			if (plosca[p.x][p.y] == Polje.BELO) {
				bele++;
				continue;
			}
			else {break;}
		}
		if (bele == 4) {
			return Stanje.ZMAGAL_BEL;
		}
		// če smo do sem prišli, ni zmagal še nihče in je nekdo na potezi.
		if(naPotezi == Igralec.BEL) {
			return Stanje.NA_POTEZI_BEL;
		}
		else {
			return Stanje.NA_POTEZI_CRN;
		}
			
		
	}
	
	public boolean odigraj(Poteza p) {
		// poglej če je na seznamu leganih potez.
		// izračunaj ceno poteze in odštej to ceno od ''credita''.
		// nastavi novega igralca naPotezi(če potrebno) in nastavi preostale potezi, spremeni plosco
		return false;
	}
	
	
	
	
	/**
	 * posodobi seznam legalnih potez
	 */
	private void posodobi_legalne_poteze() {
		seznam_legalnih_potez.clear();
		Polje checkpolje = Polje.PRAZNO;
		if(naPotezi == Igralec.BEL) {
			checkpolje = Polje.BELO;
			}
		else {
			checkpolje = Polje.CRNO;
		}
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(plosca[i][j] == checkpolje) {
					for(int k = 0; k<krediti; k++) {
						int premik = k+1;
						//preverimo, če so poteze v okolici ''stevilo kreditov'' legalne, saj dalje zagotovo ne moremo.
						if(jeLegalna(i, j, i+premik, j)) {
							seznam_legalnih_potez.add(new Poteza(i, j, i+premik, j));
							}
						if(jeLegalna(i, j, i-premik, j)) {
							seznam_legalnih_potez.add(new Poteza(i, j, i-premik, j));
							}
						if(jeLegalna(i, j, i, j+premik)) {
							seznam_legalnih_potez.add(new Poteza(i, j, i, j+premik));
							}
						if(jeLegalna(i, j, i, j+premik)) {
							seznam_legalnih_potez.add(new Poteza(i, j, i, j+premik));
							}
						}
						
						
					}
				}
				
				
			}
		}
	

	/**
	 * Preveri, ce je poteza legalna, ni najbolj elegantno, ideje dobrodosle.
	 * @param x_start
	 * @param y_start
	 * @param x_final
	 * @param y_final
	 * @return
	 */
	private boolean jeLegalna(int x_start,int y_start,int x_final, int y_final) {
		if(x_final < 0 || x_final > 5 || y_final < 0 || y_final > 5) {
			// figurica je padla iz plosce.
			return false;
		}
		
		int cena_premika = Math.abs(x_final-x_start) + Math.abs(y_final-y_start);
		int cena_ovir = 0;
		//gordol
		if(x_final-x_start > 0) { 
			for(int i =x_start; i<=x_final;  i++) {
			cena_ovir += 
			}
		}
		if(x_final-x_start < 0) {
			for(int i =x_start; i>=x_final;  i--) {
				cena_ovir += 
			}
		}
		//levodesno
		if(y_final-y_start > 0) { 
			for(int i =y_start; i<=y_final;  i++) {
				cena_ovir += 
			}
		}
		
		if(y_final-y_start < 0) {
			for(int i =y_start; i>=y_final;  i--) {
					cena_ovir += 
			}
		}
		
		if(cena_ovir + cena_premika <= krediti) {
			return true;
		}
		else {
			// poteza je predraga
			return false;
		}	
	}
}