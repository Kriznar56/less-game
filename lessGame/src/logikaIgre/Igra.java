package logikaIgre;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;



public class Igra {
	// Work in progress.
	public static final int N = 6; //<- delava samo za dva igralca oz. vs. racunalnik.
	
	public static Polje[][] plosca;
	private Igralec naPotezi;
	public int krediti;
	public boolean testing;
	private static LinkedList<Point> zacetna_crna = new LinkedList<Point>();
	private static LinkedList<Point> zacetna_bela =  new LinkedList<Point>();  // uporabni listi pri preverjanju zmagovalca
	public LinkedList<Poteza> seznam_legalnih_potez = new LinkedList<Poteza>();
	public LinkedList<LinkedList<String>> ovire =  new LinkedList<LinkedList<String>>(); 

	static { 		
		// Iniciraliziramo zacetne bele in crne in inicializiramo plosco
		plosca = new Polje[N][N];
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(i<= 1 && j>=4) {
					zacetna_bela.add(new Point(i,j));

				}
				if(i>=4 && j<= 1) {
					zacetna_crna.add(new Point(i,j));
				
				}
			
			}	
		}

	}
	
	
	
	/**
	 * Generira zacetno stanje igre, na potezi bel igralec in ima 3 preostale poteze.
	 * Beli zacne desno spodaj, crni levo zgoraj.
	 * Kasneje bomo dodali se stene/ovire, kot so v polni igri.
	 * ko bodo dodane ovire je treba tudi nakljucno generirati plosco.
	 * Prav tako vsakemu polju dodelimo ovire, glej dokumentacijo funckije "dobiOvire()".									
	 * @throws IOException 
	 */
	public Igra() throws IOException {
		testing = true;
		plosca = new Polje[N][N];
		ovire = dobiOvire();
		// Nastavi zgornja-leva polja na bela, ter spodnja desna na crna
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				plosca[i][j] = new Polje();
				if(i<= 1 && j>=4) {
					plosca[i][j].tip = TipPolja.BELO;
				}
				else if(i>=4 && j<= 1) {
					plosca[i][j].tip = TipPolja.CRNO;
				}
				else {
					plosca[i][j].tip = TipPolja.PRAZNO;
					}
			}
		}
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				//Prvih 6 stevilk v datoteki ovire predstavljajo ovire levo in desno
				if(Integer.parseInt(ovire.get(i).get(j))==1) {
					plosca[i][j].ovira_levo = true;
				}
				if(Integer.parseInt(ovire.get(i).get(j))==2) {
					plosca[i][j].ovira_desno = true;
				}
				if(Integer.parseInt(ovire.get(i).get(j))==3) {
					plosca[i][j].ovira_levo = true;
					plosca[i][j].ovira_desno = true;
				//Drugih 6 stevilk v datoteki ovire predstavljajo ovire gor in dol
				}
				if(Integer.parseInt(ovire.get(i).get(j+6))==1) {
					plosca[i][j].ovira_zgoraj = true;
				}
				if(Integer.parseInt(ovire.get(i).get(j+6))==2) {
					plosca[i][j].ovira_spodaj = true;
				}
				if(Integer.parseInt(ovire.get(i).get(j+6))==3) {
					plosca[i][j].ovira_zgoraj = true;
					plosca[i][j].ovira_spodaj = true;
				}
			}
		}
		naPotezi = Igralec.BEL;
		krediti = 3;
		posodobi_legalne_poteze();
	}
	
	public Igra(Igra igra) {
		this.plosca = igra.plosca;
		this.naPotezi = igra.naPotezi;
		this.krediti = igra.krediti;
		this.ovire = igra.ovire;
		this.seznam_legalnih_potez = igra.seznam_legalnih_potez;
		this.testing = igra.testing; // <- kriznar, kaj je ze ta param?
		
	}
	
	// vrne plosco, trenutno uporabljeno v IgralnoPolje
	public  Polje[][] getPlosca(){
		return plosca;
	}
	
	public Stanje stanje() {
		int crne = 0;
		int bele = 0;
		for(Point p: zacetna_bela) {
			if (plosca[p.x][p.y].tip == TipPolja.CRNO) {
				crne++;
				continue;
			}
			else {break;}
		}
		if(crne == 4) {
			return Stanje.ZMAGAL_CRN;
		}
		for(Point p: zacetna_crna) {
			if (plosca[p.x][p.y].tip == TipPolja.BELO) {
				bele++;
				continue;
			}
			else {break;}
		}
		if (bele == 4) {
			return Stanje.ZMAGAL_BEL;
		}
		// Ce smo do sem prisli, ni zmagal ce nihce in je nekdo na potezi.
		if(naPotezi == Igralec.BEL) {
			return Stanje.NA_POTEZI_BEL;
		}
		else {
			return Stanje.NA_POTEZI_CRN;
		}	
	}
	
	/**
	 * Ce je poteza na seznamu legalnih jo odigras, ter posodobis plosco in zamenjas igralca ce je trenutnemu igralcu zmanjkalo kreditov
	 * Ta funkcija bi pomoje lahko bla void?
	 * V to funkcijo je treba vpeljati se pregled Stanja
	 */
	
	public boolean odigraj(Poteza p) {
		// poglej ce je na seznamu leganih potez.
		if(seznam_legalnih_potez.contains(p)) {
			// izracunaj ceno poteze in odstej to ceno od ''kredita''.
			krediti -= cenaPoteze(p.getX_start(), p.getY_start(), p.getX_final(), p.getY_final());
			// spremeni plosco
			if(plosca[p.getX_start()][p.getY_start()].tip == TipPolja.BELO) {
				plosca[p.getX_final()][p.getY_final()].tip = TipPolja.BELO;
			}
			if(plosca[p.getX_start()][p.getY_start()].tip == TipPolja.CRNO){
				plosca[p.getX_final()][p.getY_final()].tip = TipPolja.CRNO;
			}
			plosca[p.getX_start()][p.getY_start()].tip= TipPolja.PRAZNO;
			// nastavi novega igralca naPotezi(ce potrebno)
			if(krediti == 0) {
				if(naPotezi == Igralec.BEL) {
					naPotezi = Igralec.CRN;
				}
				else {naPotezi = Igralec.BEL;}
				krediti = 3;
			}
			posodobi_legalne_poteze();
			return true;
		}
		else {
			return false;	
		}
	}
	
	
	/** 
	 *	Iz tekstovne datoteke, ki je vnaprej napisana, prebere zaporedje stevilk, ki predstavljajo ovire za polja na plosci
	 *	Funkcija vrne seznam seznamov, ki predstavlja zapis za ovire celotnega polja medtem ko podseznami predstavljajo vrstice polj.
	 *	Prvih 6 stevilk predstavlja ovire levo in desno od polja, drugih 6 pa za ovire nad in pod poljem.
	 *	Stevilo '1' pomeni da je ovira na levi oz. nad poljem, stevilo '2' da je desno oz. pod poljem, stevilo '3' pa da je na obeh straneh.
	 * @return List listov stevilk, ki predstavljajo vrstice polj na igralni plosci
	 * @throws IOException
	 */
	
	public LinkedList<LinkedList<String>> dobiOvire() throws IOException{
		// dodaj da nakljucno izbere 6 vrstic izmed vseh v txt datoteki
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
	
	
	
	
	/**
	 * posodobi seznam legalnih potez
	 */
	public void posodobi_legalne_poteze() {
		seznam_legalnih_potez.clear();
		TipPolja checkpolje = TipPolja.PRAZNO;
		if(naPotezi == Igralec.BEL) {
			checkpolje = TipPolja.BELO;
			}
		else {
			checkpolje = TipPolja.CRNO;
		}
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(plosca[i][j].tip == checkpolje) {
					for(int k = 0; k<krediti+1; k++) {
						int premik = k+1;
						//preverimo, ce so poteze v okolici ''stevilo kreditov'' legalne, saj dalje zagotovo ne moremo.
						if(jeLegalna(i, j, i+premik, j)) {
							seznam_legalnih_potez.add(new Poteza(i, j, i+premik, j));
							}
						if(jeLegalna(i, j, i-premik, j)) {
							seznam_legalnih_potez.add(new Poteza(i, j, i-premik, j));
							}
						if(jeLegalna(i, j, i, j+premik)) {
							seznam_legalnih_potez.add(new Poteza(i, j, i, j+premik));
							}
						if(jeLegalna(i, j, i, j-premik)) {
							seznam_legalnih_potez.add(new Poteza(i, j, i, j-premik));
							}
						
						}		
					}
				}
			}
		}
	
	//Se zavedava, da sta funkciji jeLegalna in cenaPoteze med bolj gr�imi, namerava popraviti
	
	/**
	 * Preveri, ce je poteza legalna.
	 * @param x_start
	 * @param y_start
	 * @param x_final
	 * @param y_final
	 * @return
	 */
	public boolean jeLegalna(int x_start,int y_start,int x_final, int y_final) {
		boolean jePreskocil = false;
		//Figurica je padla iz plosce.
		if(x_final < 0 || x_final > 5 || y_final < 0 || y_final > 5) {
			return false;
		}
		//Poteza je predraga
		if(cenaPoteze(x_start, y_start, x_final, y_final) > krediti) {
			return false;
		}
		//Ne moremo skokici na polje na katerem je ze figurica
		if(plosca[x_final][y_final].tip == TipPolja.BELO || plosca[x_final][y_final].tip == TipPolja.CRNO) {
			return false;
		}
		else {
			if(Math.abs(x_final-x_start) >= 1 && Math.abs(y_final-y_start) >= 1) {
				//Premike diagonalno ne dovolimo, saj pot npr. gor-levo oz. levo-gor lahko razlicno staneta v odvisnosti od ovir
				return false;
			}
			else {
				int cena_premika = Math.abs(x_final-x_start) + Math.abs(y_final-y_start);
				int cena_ovir = 0;
				//premiki po vrsti, desno, levo, gor in dol
				if(x_final-x_start > 0) { 
					//desno
					for(int i =x_start; i<x_final;  i++) {
						//ce preskakujemo ploscek
						if(plosca[i][y_final].tip == TipPolja.BELO || plosca[i][y_final].tip == TipPolja.CRNO) {
							//ce je levo oz. desno od ploscka ki ga preskakujemo polje ovira, vrni false ker to ni dovoljeno
							if(i!=x_start) {
								if(plosca[i][y_final].ovira_desno || plosca[i+1][y_final].ovira_levo) {
									return false;
								}
								else {
									//ker smo preskocili eno polje moramo odsteti 1 od cene premika, in prestavimo se na naslednje polje
									jePreskocil = true;
									cena_premika--;
									i++;
								}
							}
						}
						else {
							//ce sta dve oviri na meji 
							if(plosca[i][y_final].ovira_desno && plosca[i+1][y_final].ovira_levo) {
								cena_ovir += 2;
							}
							else {
								//ce je ena ovira na meji
								if(plosca[i][y_final].ovira_desno || plosca[i+1][y_final].ovira_levo) {
									cena_ovir++;
								}
							}
						}
					}
				}
				if(x_final-x_start < 0) {
					//levo
					for(int i =x_start; i>x_final;  i--) {
						if(plosca[i][y_final].tip == TipPolja.BELO || plosca[i][y_final].tip == TipPolja.CRNO) {
							// ko gremo levo je vse isto le parametri se obrnejo
							if(i!=x_start) {
								if(plosca[i][y_final].ovira_levo || plosca[i-1][y_final].ovira_desno) {
									return false;
								}
								else {
									jePreskocil = true;
									cena_premika--;
									i--;
								}
							}
						}
						else {
							if(plosca[i][y_final].ovira_levo && plosca[i-1][y_final].ovira_desno) {
								cena_ovir += 2;
							}
							else {
								if(plosca[i][y_final].ovira_levo || plosca[i-1][y_final].ovira_desno) {
									cena_ovir++;
								}
							}
						}
					}
				}
				if(y_final-y_start > 0) { 
					//dol
					for(int i =y_start; i<y_final;  i++) {
						if(plosca[x_final][i].tip == TipPolja.BELO || plosca[x_final][i].tip == TipPolja.CRNO) {
							if(i!=y_start) {
								if(plosca[x_final][i].ovira_spodaj || plosca[x_final][i+1].ovira_zgoraj) {
									return false;
								}
								else {
									jePreskocil = true;
									cena_premika--;
									i++;
								}
							}
						}
						else {
							if(plosca[x_final][i].ovira_spodaj && plosca[x_final][i+1].ovira_zgoraj) {
								cena_ovir += 2;
							}
							else {
								if(plosca[x_final][i].ovira_spodaj || plosca[x_final][i+1].ovira_zgoraj) {
									cena_ovir++;
								}
							}
						}
					}
				}
				
				if(y_final-y_start < 0) {
					//gor

					for(int i =y_start; i>y_final;  i--) {
						if(plosca[x_final][i].tip == TipPolja.BELO || plosca[x_final][i].tip == TipPolja.CRNO) {
							if(i!=y_start) {
								if(plosca[x_final][i].ovira_zgoraj || plosca[x_final][i-1].ovira_spodaj) {
									return false;
								}
								else {
									jePreskocil = true;
									cena_premika--;
									i--;
								}
							}
						}
						else {
							if(plosca[x_final][i].ovira_zgoraj && plosca[x_final][i-1].ovira_spodaj) {
								cena_ovir += 2;
							}
							else {
								if(plosca[x_final][i].ovira_zgoraj || plosca[x_final][i-1].ovira_spodaj) {
									cena_ovir++;
								}
							}
						} 
					}
				}
				
				if(jePreskocil && cena_premika > 1) {
					return false;
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
	}
	
	/**
	 * Skoraj identicna metoda kot jeLegalna(), le da sedaj vracamo koliko poteza stane.
	 */
	public int cenaPoteze(int x_start,int y_start,int x_final, int y_final) {
		int cena_premika = Math.abs(x_final-x_start) + Math.abs(y_final-y_start);
		int cena_ovir = 0;
		if(x_final-x_start > 0) { 
			for(int i =x_start; i<x_final;  i++) {
				if(plosca[i][y_final].tip == TipPolja.BELO || plosca[i][y_final].tip == TipPolja.CRNO) {
					if(plosca[i][y_final].ovira_desno && plosca[i+1][y_final].ovira_levo) {
						cena_ovir += 2;
					}
					else if(plosca[i][y_final].ovira_desno || plosca[i+1][y_final].ovira_levo) {
						cena_ovir++;
					}
					else if(i!=x_start) {
						cena_premika--;
						i++;
					}
					//ploscek preskocimo v primeru ko ni to ta ploscek
				}
				else {
					if(plosca[i][y_final].ovira_desno && plosca[i+1][y_final].ovira_levo) {
						cena_ovir += 2;
						break;
					}
					else if(plosca[i][y_final].ovira_desno || plosca[i+1][y_final].ovira_levo) {
						cena_ovir++;
					}
				}
			}
		}
		if(x_final-x_start < 0) {
			for(int i =x_start; i>x_final;  i--) {
				if(plosca[i][y_final].tip == TipPolja.BELO || plosca[i][y_final].tip == TipPolja.CRNO) {
					if(plosca[i][y_final].ovira_levo && plosca[i-1][y_final].ovira_desno) {
						cena_ovir += 2;
					}
					else if(plosca[i][y_final].ovira_levo || plosca[i-1][y_final].ovira_desno) {
						cena_ovir++;
					}
					else if(i!=x_start) {
						cena_premika--;
						i--;
					}
				}
				else {
					if(plosca[i][y_final].ovira_levo && plosca[i-1][y_final].ovira_desno) {
						cena_ovir += 2;
						break;
					}
					if(plosca[i][y_final].ovira_levo || plosca[i-1][y_final].ovira_desno) {
						cena_ovir++;
					}
				}
			}
		}
		if(y_final-y_start > 0) { 
			for(int i =y_start; i<y_final;  i++) {
				if(plosca[x_final][i].tip == TipPolja.BELO || plosca[x_final][i].tip == TipPolja.CRNO) {
					if(plosca[x_final][i].ovira_spodaj && plosca[x_final][i+1].ovira_zgoraj) {
						cena_ovir += 2;
					}
					else if(plosca[x_final][i].ovira_spodaj || plosca[x_final][i+1].ovira_zgoraj) {
						cena_ovir++;
					}
					else if(i!=y_start) {
						cena_premika--;
						i++;
					}
				}
				else {
					if(plosca[x_final][i].ovira_spodaj && plosca[x_final][i+1].ovira_zgoraj) {
						cena_ovir += 2;
						break;
					}
					if(plosca[x_final][i].ovira_spodaj || plosca[x_final][i+1].ovira_zgoraj) {
						cena_ovir++;
					}
				}
			}
		}
		
		if(y_final-y_start < 0) {
			for(int i =y_start; i>y_final;  i--) {
				if(plosca[x_final][i].tip == TipPolja.BELO || plosca[x_final][i].tip == TipPolja.CRNO) {
					if(plosca[x_final][i].ovira_zgoraj && plosca[x_final][i-1].ovira_spodaj) {
						cena_ovir += 2;
					}
					else if(plosca[x_final][i].ovira_zgoraj || plosca[x_final][i-1].ovira_spodaj) {
						cena_ovir++;
					}
					else if(i!=y_start) {
						cena_premika--;
						i--;
					}
				}
				else {
					if(plosca[x_final][i].ovira_zgoraj && plosca[x_final][i-1].ovira_spodaj) {
						cena_ovir += 2;
						break;
					}
					if(plosca[x_final][i].ovira_zgoraj || plosca[x_final][i-1].ovira_spodaj) {
						cena_ovir++;
					}
				} 
			}
		}
		return cena_ovir + cena_premika;
	}
}