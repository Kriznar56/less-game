package logikaIgre;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Igra {
	// Work in progress.
	public static final int N = 6; //<- delava samo za dva igralca oz. vs. racunalnik.
	
	private Polje[][] plosca;
	private Igralec naPotezi;
	private int preostale_poteze;
	private LinkedList<Point> zacetna_crna;
	private LinkedList<Point> zacetna_bela;  // uporabni listi pri preverjanju zmagovalca
	private LinkedList<LinkedList<String>> ovire;
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
	 * Prav tako vsakemu polju dodelimo ovire, glej dokumentacijo funckije "dobiOvire()".									
	 * @throws IOException 
	 */
	public Igra() throws IOException {
		plosca = new Polje[N][N];
		ovire = dobiOvire();
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
//				//Prvih 6 stevilk v datoteki ovire predstavljajo ovire levo in desno
//				if(ovire.get(i).get(j)==Character.toString('1')) {
//					plosca[i][j].levo = true;
//				}
//				if(ovire.get(i).get(j)==Character.toString('2')) {
//					plosca[i][j].desno = true;
//				}
//				if(ovire.get(i).get(j)==Character.toString('3')) {
//					plosca[i][j].levo = true;
//					plosca[i][j].desno = true;
//				//Drugih 6 stevilk v datoteki ovire predstavljajo ovire gor in dol
//				}
//				if(ovire.get(i*2).get(j*2)==Character.toString('1')) {
//					plosca[i][j].gor = true;
//				}
//				if(ovire.get(i*2).get(j*2)==Character.toString('2')) {
//					plosca[i][j].dol = true;
//				}
//				if(ovire.get(i*2).get(j*2)==Character.toString('3')) {
//					plosca[i][j].gor = true;
//					plosca[i][j].dol = true;
//				}
				
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
		// dodaj pogoje za legalnost,
		// nastavi novega igralca naPotezi in nastavi preostale potezi, spremeni plosco
		return false;
	}
	
	/** 
	 *	Iz tekstovne datoteke, ki je vnaprej napisana, prebere zaporedje stevilk, ki predstavljajo ovire za polja na plosci
	 *	Funkcija vrne seznam seznamov, ki predstavlja zapis za ovire celotnega polja medtem ko podseznami predstavljajo vrstice polj.
	 *	Prvih 6 stevilk predstavlja ovire levo in desno od polja, drugih 6 pa za ovire nad in pod poljem.
	 *	Stevilo '1' pomeni da je ovira na levi oziroma nad poljem, stevilo '2' da je desno in pod poljem, stevilo '3' pa da je na obeh straneh.
	 * @return List listov stevilk, ki predstavljajo vrstice polj na igralni plosci
	 * @throws IOException
	 */
	
	public LinkedList<LinkedList<String>> dobiOvire() throws IOException{
		// dodaj da naklju�no izbere 6 vrstic izmed vseh v txt datoteki
		// da program deluje, si je treba tekstovno datoteki namestiki v mapo projekta/workspaca(kjer je tudi mapa 'src')
		FileReader fileOvire = new FileReader("ovire.txt");
		BufferedReader buffOvire = new BufferedReader(fileOvire);
		LinkedList<LinkedList<String>> ovire = new LinkedList<LinkedList<String>>();
		for(int j=0; j<N; j++) {
			ovire.add(new LinkedList<String>());
		}
		String linija;
		Integer j = 0;
		
		while((linija = buffOvire.readLine()) != null) {
			String[] vrstica = linija.split("");
				for(int i=0; i<N*2; i++) {
					ovire.get(j).add(vrstica[i]);
				}
			j++;
		}
		
		fileOvire.close();
		return ovire;
	}
	
	
	
}
