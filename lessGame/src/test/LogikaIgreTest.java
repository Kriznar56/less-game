package test;

import java.io.IOException;

import junit.framework.TestCase;
import logikaIgre.Igra;
import logikaIgre.Polje;
import logikaIgre.Stanje;
import logikaIgre.TipPolja;

public class LogikaIgreTest extends TestCase{
	

	
	
	public void testIgra() throws IOException {
		
	
		
		Igra igra = new Igra();
		//Na zacetku na potezi beli
		assertEquals(Stanje.NA_POTEZI_BEL, igra.stanje());
		//Preverimo da je na zacetku stevilo kreditov res 3
		assertEquals(3, igra.krediti);
		assertEquals(TipPolja.BELO, igra.plosca[1][4].tip);
		System.out.println("______________________");
		System.out.println(igra.jeLegalna(0, 5, 2, 5));
		System.out.println(igra.cenaPoteze(0, 5, 2, 5));
		//Naredimo eno potezo iz seznama potez
		igra.odigraj(igra.seznam_legalnih_potez.get(0));
		//Sedaj mora biti stevilo kreditov manjse
		//Naredimo se dve potezi in potem mora biti na potezi crni	
		igra.odigraj(igra.seznam_legalnih_potez.get(0));
		igra.odigraj(igra.seznam_legalnih_potez.get(0));
		Igra kopijatest = new Igra(igra);
		System.out.print(igra.plosca);
		kopijatest.odigraj(kopijatest.seznam_legalnih_potez.get(0));
		int k = 0;
		for(Polje p: igra.plosca[0]) {
		System.out.print(p == kopijatest.plosca[0][k]);
		k++;
		}
	
		
}
}
