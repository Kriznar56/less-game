package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logikaIgre.Igra;
import logikaIgre.Igralec;
import logikaIgre.Polje;
import logikaIgre.Poteza;
import logikaIgre.Stanje;
import logikaIgre.TipPolja;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener{
	IgralnoPolje polje;
	private Igra igra;
	private JLabel status;
	private Strateg strategBELI;
	private Strateg strategCRNI; // ce se spomnes boljse ime za strateg povej.
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	boolean oznacenoPolje = false;
	private JMenuItem razveljavi;
	private LinkedList<Igra> stanja = new LinkedList<Igra>();

	
	// nekje bova morala upeljati nekaksno 'Point aktivno_polje = null, k' saj sta 2 klika potebna za definicijo poteze.
	// tam ko repaintas pogledas ce je koordinata polja aktivno in ce je aktivno ga obrobis z neko barvo, ali jo spremenis.
	// zdi se mi da je bolje, da je point ker je plosca[i][j] Polje, v katerem ne shranjujeva njegove trenutne koordinate.
	

	public Okno() throws HeadlessException, IOException {
		this.setTitle("Less");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// menu, sem lahko se kaj dodava, naprimer prekini igro, zacni novo igro za 2 igralca / za eno igro
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);
		JMenu uredi = new JMenu("Uredi");
		menu_bar.add(uredi);
		
		
		igraClovekRacunalnik = new JMenuItem("Človek – Računalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("Računalnik – Človek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);

		igraRacunalnikRacunalnik = new JMenuItem("Računalnik – Računalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);

		igraClovekClovek = new JMenuItem("Človek – Človek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		razveljavi = new JMenuItem("Razveljavi");
		uredi.add(razveljavi);
		razveljavi.addActionListener(this);
	
		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		// statusna vrstica za sporocila
		status = new JLabel();
		status.setFont(new Font("SansSerif",
							    Font.PLAIN,
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		// zacnemo novo igro
		nova_igra(new Clovek(this, Igralec.BEL), new Racunalnik(this, Igralec.CRN) );
	}
	
	public Polje[][] getPlosca() {
		return (igra == null ? null : igra.getPlosca());
	}
	
	
	

	public void nova_igra(Strateg noviStrategBELI, Strateg noviStrategCRNI) throws IOException {
		if(strategBELI != null) {strategBELI.prekini();}
		if(strategCRNI != null) {strategCRNI.prekini();}
		if(IgralnoPolje.oznaci) {IgralnoPolje.oznaci=false;}
		this.igra = new Igra(); //IO exception zaradi ovir
		strategBELI = noviStrategBELI;
		strategCRNI = noviStrategCRNI;
		
		switch (igra.stanje()) {
		case NA_POTEZI_BEL: strategBELI.na_potezi(); break;
		case NA_POTEZI_CRN: strategCRNI.na_potezi(); break;
		default: break;
		}
		osveziGUI();
		repaint();
	
	}
	
	public void odigraj(Poteza p) {
		stanja.add(copyIgra());
		igra.odigraj(p);
		osveziGUI();
		switch (igra.stanje()) {
		case NA_POTEZI_BEL: strategBELI.na_potezi(); break;
		case NA_POTEZI_CRN: strategCRNI.na_potezi(); break;
		case ZMAGAL_BEL: break;
		case ZMAGAL_CRN: break;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		// morava dodati se ostale opcije v menuju
		if (e.getSource() == igraClovekRacunalnik) {
			try {
				nova_igra(new Clovek(this, Igralec.BEL),
						  new Racunalnik(this, Igralec.CRN));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == igraRacunalnikClovek) {
			try {
				nova_igra(new Racunalnik(this, Igralec.BEL),
						  new Clovek(this, Igralec.CRN));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == igraRacunalnikRacunalnik) {
			try {
				nova_igra(new Racunalnik(this, Igralec.BEL),
						  new Racunalnik(this, Igralec.CRN));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (e.getSource() == igraClovekClovek) {
			try {
				nova_igra(new Clovek(this, Igralec.BEL),
				          new Clovek(this, Igralec.CRN));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == razveljavi){
			razveljavi();
		}
	}
	
	private void osveziGUI() {
		//isto kot v kodi tictactoe, samo da sem odstranil case NEODLOCENO
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(igra.stanje()) {
			case NA_POTEZI_BEL: status.setText("Na potezi je BEL"); break;
			case NA_POTEZI_CRN: status.setText("Na potezi je CRN"); break;
			case ZMAGAL_BEL: status.setText("Zmagal je BELI"); break;
			case ZMAGAL_CRN: status.setText("Zmagal je CRNI"); break;
			}
		}
		polje.repaint();
		
	}

	public void klikniPolje(int i, int j) {
		if (igra != null) {
			switch (igra.stanje()) {
			case NA_POTEZI_BEL:
				strategBELI.klik(i, j);
				break;
			case NA_POTEZI_CRN:
				strategCRNI.klik(i, j);
				break;
			default:
				break;
			}
		}
	}
	
	int oznacenoPolje_i() {
		return IgralnoPolje.oznaceno_i;
	}
	
	//metoda ki vrne seznam polja v obliki koordinate x in y, da se narisejo polja na katera se uporabnik lahko premakne
	LinkedList<Integer> mozniPremiki(int i, int j) {
		LinkedList<Integer> moznaPolja = new LinkedList<Integer>();
		for(Poteza p: igra.seznam_legalnih_potez){
			if(p.getX_start()==i && p.getY_start()==j){
				moznaPolja.add(p.getX_final());
				moznaPolja.add(p.getY_final());
			}
		}
		return moznaPolja;
	}
	
	int oznacenoPolje_j() {
		return IgralnoPolje.oznaceno_j;
	}
	
	boolean jePloscek(int i, int j) {
		if(igra.plosca[i][j].tip != TipPolja.PRAZNO) {
			return true;
		}
		else {return false;}
	}
	
	LinkedList<Poteza> dobiPoteze() {
		return igra.seznam_legalnih_potez;
	}
	
	Stanje trenutnoStanje() {
		return igra.stanje();
	}
	
	// vrne novo kopijo igre
	
	public Igra copyIgra() {
		return new Igra(igra);
	}
	
	private void razveljavi() {
		if(!stanja.isEmpty()) {
			strategBELI.prekini();
			strategCRNI.prekini();
			if(IgralnoPolje.oznaci) {IgralnoPolje.oznaci=false;}
			igra = stanja.getLast();
			switch (igra.stanje()) {
			case NA_POTEZI_BEL: strategBELI.na_potezi(); break;
			case NA_POTEZI_CRN: strategCRNI.na_potezi(); break;
			default: break;
			}
			stanja.remove(stanja.getLast());
			osveziGUI();
			repaint();
		}
	}

}
