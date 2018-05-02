package test;

import java.io.IOException;

import junit.framework.TestCase;
import logikaIgre.Igra;
import logikaIgre.Polje;
import logikaIgre.Stanje;

public class LogikaIgreTest extends TestCase{

	public void testIgra() throws IOException {
		Igra igra = new Igra();
		//Na zacetku na potezi beli
		assertEquals(Stanje.NA_POTEZI_BEL, igra.stanje());
		//Preverimo da je na zacetku stevilo kreditov res 3
		assertEquals(3, igra.krediti);
		assertEquals(Polje.BELO, igra.plosca[1][4]);
		//Naredimo eno potezo iz seznama potez
		igra.odigraj(igra.seznam_legalnih_potez.get(0));
		//Sedaj mora biti stevilo kreditov manjse
		assertEquals(2, igra.krediti);
		//Naredimo se dve potezi in potem mora biti na potezi crni
		igra.odigraj(igra.seznam_legalnih_potez.get(0));
		igra.odigraj(igra.seznam_legalnih_potez.get(0));
		assertEquals(Stanje.NA_POTEZI_CRN, igra.stanje());
	}
}
