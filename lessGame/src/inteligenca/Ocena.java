package inteligenca;

import java.awt.Point;
import java.util.LinkedList;

import logikaIgre.Igra;
import logikaIgre.Igralec;
import logikaIgre.Polje;
import logikaIgre.TipPolja;

public class Ocena {
	public static final int ZMAGA = (1 << 20); // vrednost zmage, 1 prestavi za 20 mest binomsko to pride 1048576
	public static final int ZGUBA = -ZMAGA;
	private static LinkedList<Point> crnaPolja = new LinkedList<Point>();
	private static LinkedList<Point> belaPolja = new LinkedList<Point>();
/*TO DO
 * upoštevaj sosednje ploščke, več ko jih imaš v smeri cilja bolje je
 * upoštevaj nasprotnikovo stanje in ga dodaj kot 'negativne točke'
 * trenutno maksimizira ceno do svojega doma, namest da bi minimiziral ceno do cilja <- popravi
*/
	private static int vrednostPozicije(boolean b, Point p, Igra igra) {
		//racunamo razdaljo ce smo beli do zgornjega desnega kota in ce smo crni do spodnjega levega
		if(b) {//BELI
			return (5+(int)p.getX()-(int)p.getY());
			//return igra.cenaPoteze((int)p.getX(), (int)p.getY(), 0, 5); //test s trenutno ceno poteze <- neoptimalno
		}
		else {//CRNI
			return (5-(int)p.getX()+(int)p.getY());
			//return igra.cenaPoteze((int)p.getX(), (int)p.getY(), 5, 0); //test s trenutno ceno poteze <- neoptimalno
		}
	}
	//Funkcija ki doda konstanto 0.1 za vsak sosednji ploscek
	private static int sosednjiPloscki( boolean igralec, Point p, Polje[][] plosca) {
		int vrednost = 0;
		if(igralec) {
			if((int)p.getX()==5 && (int)p.getY()==0) {}
			else if((int)p.getX()==5) {
				if(plosca[(int)p.getX()][(int)p.getY()-1].tip==TipPolja.CRNO) { vrednost+=1/2;}
			}
			else if((int)p.getY()==0) {
				if(plosca[(int)p.getX()+1][(int)p.getY()].tip==TipPolja.CRNO) { vrednost+=1/2;}
			}
			else {
				if(plosca[(int)p.getX()+1][(int)p.getY()].tip==TipPolja.CRNO) { vrednost+=1/2;}
				if(plosca[(int)p.getX()][(int)p.getY()-1].tip==TipPolja.CRNO) { vrednost+=1/2;}
			}
	
		}
		else {
			if((int)p.getX()==0 && (int)p.getY()==5) {}
			else if((int)p.getX()==0) {
				if(plosca[(int)p.getX()][(int)p.getY()+1].tip==TipPolja.CRNO) { vrednost+=1/2;}
			}
			else if((int)p.getY()==5) {
				if(plosca[(int)p.getX()-1][(int)p.getY()].tip==TipPolja.CRNO) { vrednost+=1/2;}
			}
			else {
				if(plosca[(int)p.getX()-1][(int)p.getY()].tip==TipPolja.CRNO) { vrednost+=1/2;}
				if(plosca[(int)p.getX()][(int)p.getY()+1].tip==TipPolja.CRNO) { vrednost+=1/2;}
			}
		}
		return vrednost;
	}
	
	public static int cenaDoCilja(int x1, int y1, Igra igra, boolean Bela) {
		int[][] cene = new int[6][6];// naredimo matriko 6x6 polno ničel
		if(Bela) {
			for(int i = x1; i<=5; i++){
				for(int j = y1; j>=0; j--) {
					if(i== x1 && j == y1) {
					}
					else{
						int dol;
						int levo;
						if(j!=5 && j+1 <=y1) {
						dol = cene[i][j+1] + 1; // koliko bi stal premik s polja pod poljem
						if(igra.plosca[i][j].ovira_spodaj) {dol++;} //ima polje oviro spodaj?
						if(igra.plosca[i][j+1].ovira_zgoraj) {dol++;}
						}//ima polje spodaj oviro zgoraj?
						else {dol=(1 << 20);}
						if(i!=0 && i-1 >= x1) {
						levo = cene[i-1][j] + 1; //podobno za levo
						if(igra.plosca[i][j].ovira_levo) {levo++;}
						if(igra.plosca[i-1][j].ovira_desno) {levo++;} 
						}
						else {levo = (1 << 20);}
						cene[i][j] = Math.min(dol, levo); //vrni najnižjo ceno
					}
				}	
			}
			//System.out.println(cene[5][5]);
			//return cene
			return cene[5][0]; //vrni najnižjo ceno premika do ciljnega kota

		}
		else {//ce nismo beli smo crni in gledamo ceno do spodnjega levega kota
			for(int i = x1; i>=0; i--) {
				
				for(int j = y1; j<=5; j++) {
					
					if(i== x1 && j == y1) {
					}
					else {
					int gor;
					int desno;
					if(j!=0 && j-1 >=y1) {
					gor = cene[i][j-1] + 1;
					if(igra.plosca[i][j].ovira_zgoraj) {gor++;}
					if(igra.plosca[i][j-1].ovira_spodaj) {gor++;}
					}
					else {gor = (1 << 20);}
				
					if(i!=5 && (i+1 <=x1)) {
					desno = cene[i+1][j] +1;
					
					if(igra.plosca[i][j].ovira_desno) {desno++;}
					if(igra.plosca[i+1][j].ovira_levo) {desno++;}
					}
					else {desno = (1 << 20);}
				
					
					cene[i][j] = Math.min(gor, desno);
					//if(gor == (1 << 20)) {System.out.println("cena"+cene[i][j]+"gor"+gor+"desno"+desno );
					
					
					}
				}
			}
			//return cene
			return cene[0][5];
			
		}
		
	}
	
	
	
	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		switch (igra.stanje()) {
		case ZMAGAL_BEL:
			return (jaz == Igralec.BEL ? ZMAGA : ZGUBA);
		case ZMAGAL_CRN:
			return (jaz == Igralec.CRN ? ZMAGA : ZGUBA);
		case NA_POTEZI_BEL:
		case NA_POTEZI_CRN:
			Polje[][] plosca = igra.getPlosca();
			crnaPolja.clear();
			belaPolja.clear();
			int vrednostBELI = 0;
			int vrednostCRNI = 0;
			for(int i = 0; i<Igra.N; i++) {
				for(int j = 0; j<Igra.N; j++) {
					if(plosca[i][j].tip==TipPolja.CRNO) {
						crnaPolja.add(new Point(i, j));
					}
					else if(plosca[i][j].tip == TipPolja.BELO) {
						belaPolja.add(new Point(i, j));
					}
				}
			}
			//Najprej bomo ocenili samo tako da bomo pogledali koliko ima vsak ploscek x in y razdaljo do zgornjega ali spodnjega kota
			for(Point p: crnaPolja) {
				vrednostCRNI -= cenaDoCilja(p.x, p.y, igra, false);
				//vrednostCRNI += vrednostPozicije(false, p, igra);
				vrednostCRNI += sosednjiPloscki(false, p, plosca);
			}
			for(Point p: belaPolja) {
				vrednostBELI -= cenaDoCilja(p.x, p.y, igra, true);
				//vrednostBELI += vrednostPozicije(true, p, igra);
				vrednostBELI += sosednjiPloscki(true, p, plosca);
			}
			return (jaz==Igralec.BEL ? (vrednostBELI-vrednostCRNI/4) : (vrednostCRNI-vrednostBELI/4));
			
		}
		assert false;
		return 666;
	}

}
