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
	private static LinkedList<Point> mojaPolja = new LinkedList<Point>();
	private static LinkedList<Point> nasprotnikovaPolja = new LinkedList<Point>();
/*TO DO
 * upoštevaj sosednje ploščke, več ko jih imaš v smeri cilja bolje je
 * upoštevaj nasprotnikovo stanje in ga dodaj kot 'negativne točke'
 * trenutno maksimizira ceno do svojega doma, namest da bi minimiziral ceno do cilja <- popravi
*/
	private static int vrednostPozicije(boolean b, Point p, Igra igra) {
		//racunamo razdaljo ce smo beli do zgornjega desnega kota in ce smo crni do spodnjega levega
		if(b) {//BELI
			//return (5+(int)p.getX()-(int)p.getY());
			
			return igra.cenaPoteze((int)p.getX(), (int)p.getY(), 0, 5); //test s trenutno ceno poteze <- neoptimalno
		}
		else {//CRNI
			//return (5-(int)p.getX()+(int)p.getY());
			return igra.cenaPoteze((int)p.getX(), (int)p.getY(), 5, 0); //test s trenutno ceno poteze <- neoptimalno
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
			mojaPolja.clear();
			nasprotnikovaPolja.clear();
			int vrednostBELI = 0;
			int vrednostCRNI = 0;
			for(int i = 0; i<Igra.N; i++) {
				for(int j = 0; j<Igra.N; j++) {
					if(jaz == Igralec.CRN) {
						if(plosca[i][j].tip==TipPolja.CRNO) {
							mojaPolja.add(new Point(i, j));
						}
						else if(plosca[i][j].tip == TipPolja.BELO) {
							nasprotnikovaPolja.add(new Point(i, j));
						}
					}
					if(jaz == Igralec.BEL) {
						if(plosca[i][j].tip==TipPolja.BELO) {
							mojaPolja.add(new Point(i, j));
						}
						else if(plosca[i][j].tip == TipPolja.CRNO) {
							nasprotnikovaPolja.add(new Point(i, j));
						}
					}
				}
			}
			//Najprej bomo ocenili samo tako da bomo pogledali koliko ima vsak ploscek x in y razdaljo do zgornjega ali spodnjega kota
			for(Point p: mojaPolja) {
				vrednostCRNI += vrednostPozicije(false, p, igra);
			}
			for(Point p: nasprotnikovaPolja) {
				vrednostBELI += vrednostPozicije(true, p, igra);
			}
			//System.out.println("ocena je "+(jaz==Igralec.BEL ? vrednostBELI : vrednostCRNI));
			return (jaz==Igralec.BEL ? vrednostBELI : vrednostCRNI);
			
		}
		assert false;
		return 666;
	}
}
