package logikaIgre;

public class Igra {
	// Work in progress.
	public static final int N = 6; //<- delava samo za dva igralca oz. vs. racunalnik.
	
	private Polje[][] plosca;
	private Igralec naPotezi;
	private int preostale_poteze;
	/**
	 * List manjkajocih funkcij
	 * - Odigraj <- preveris, ce je poteza legalna in jo odigras.
	 * - Stanje <- vrne, kdo je na potezi oziroma ce je igre konec in kdo je zmagal.
	 * - List potez <- preverjas, katere legalne glede na ceno/ preostale poteze in preverjas ce ostane na plosci.
	 */
	
	
	
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
	
	
	
}
