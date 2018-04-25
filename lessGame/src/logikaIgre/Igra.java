package logikaIgre;

import java.awt.Point;
import java.util.LinkedList;

public class Igra {
	// Work in progress.
	public static final int N = 6; //<- delava samo za dva igralca oz. vs. racunalnik.
	
	private Polje[][] plosca;
	private Igralec naPotezi;
	private int preostale_poteze;
	private LinkedList<Point> zacetna_crna;
	private LinkedList<Point> zacetna_bela;  // uporabni listi pri preverjanju zmagovalca
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
		preostale_poteze = 3;
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
	
	public boolean Odigraj(Poteza p) {
		// dodaj pogoje za legalnost
		return false;
	}
	
	
	
}
