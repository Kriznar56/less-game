package inteligenca;

import logikaIgre.Igra;
import logikaIgre.Igralec;
import logikaIgre.Polje;

public class Ocena {
	public static final int ZMAGA = (1 << 20); // vrednost zmage, to neki nardi
	public static final int ZGUBA = -ZMAGA;

	
	public static int oceniPozicijo(Igralec jaz, Igra igra) {
		switch (igra.stanje()) {
		case ZMAGAL_BEL:
			return (jaz == Igralec.BEL ? ZMAGA : ZGUBA);
		case ZMAGAL_CRN:
			return (jaz == Igralec.CRN ? ZMAGA : ZGUBA);
		case NA_POTEZI_BEL:
		case NA_POTEZI_CRN:
			Polje[][] plosca = igra.getPlosca();
			//Tuki naprej pride prava koda, morm pogledat se predavanje
			
		}
		assert false;
		return 666;
	}
}
