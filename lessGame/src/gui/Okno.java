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
	private JLabel krediti;
	private JLabel stopnja_tez;
	private Strateg strategBELI;
	private Strateg strategCRNI; // ce se spomnes boljse ime za strateg povej.
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	private JMenuItem stopnja1;
	private JMenuItem stopnja2;
	private JMenuItem stopnja3;
	private JMenuItem stopnja4;
	private JMenuItem stopnja5;
	private JMenuItem stopnja6;
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
		JMenu uredi = new JMenu("Uredi potezo");
		menu_bar.add(uredi);
		JMenu stopnja = new JMenu("Stopnja težavnosti");
		menu_bar.add(stopnja);
		
		
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
		
		stopnja1 = new JMenuItem("1");
		stopnja.add(stopnja1);
		stopnja1.addActionListener(this);
		
		stopnja2 = new JMenuItem("2");
		stopnja.add(stopnja2);
		stopnja2.addActionListener(this);
		
		stopnja3 = new JMenuItem("3");
		stopnja.add(stopnja3);
		stopnja3.addActionListener(this);
		
		stopnja4 = new JMenuItem("4");
		stopnja.add(stopnja4);
		stopnja4.addActionListener(this);
		
		stopnja5 = new JMenuItem("5");
		stopnja.add(stopnja5);
		stopnja5.addActionListener(this);
	
		stopnja6 = new JMenuItem("6");
		stopnja.add(stopnja6);
		stopnja6.addActionListener(this);
		
		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		// statusna vrstica za sporocila, preostale kredite in stopnjo računalnika
		//status igre
		status = new JLabel();
		status.setFont(new Font("SansSerif",
							    Font.PLAIN,
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		//krediti
		krediti = new JLabel();
		krediti.setFont(new Font("SansSerif",
			    Font.PLAIN,
			    20));
		GridBagConstraints krediti_layout = new GridBagConstraints();
		krediti_layout.gridx = 0;
		krediti_layout.gridy = 1;
		krediti_layout.anchor = GridBagConstraints.WEST;
		getContentPane().add(krediti, krediti_layout);
		
		//stopnja težavnosti
		stopnja_tez = new JLabel();
		stopnja_tez.setFont(new Font("SansSerif",
			    Font.PLAIN,
			    20));
		GridBagConstraints stopnja_tez_layout = new GridBagConstraints();
		stopnja_tez_layout.gridx = 0;
		stopnja_tez_layout.gridy = 1;
		stopnja_tez_layout.anchor = GridBagConstraints.EAST;
		getContentPane().add(stopnja_tez, stopnja_tez_layout);
		
		
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
		
		else if(e.getSource() == razveljavi){razveljavi(); }
		
		else if(strategBELI.getClass().getName() == "gui.Racunalnik") {
			if(e.getSource() == stopnja1){strategBELI.tezavnost(1); osveziGUI();}
			else if(e.getSource() == stopnja2){strategBELI.tezavnost(2); osveziGUI();}
			else if(e.getSource() == stopnja3){strategBELI.tezavnost(3); osveziGUI();}
			else if(e.getSource() == stopnja4){strategBELI.tezavnost(4); osveziGUI();}
			else if(e.getSource() == stopnja5){strategBELI.tezavnost(5); osveziGUI();}
			else if(e.getSource() == stopnja6){strategBELI.tezavnost(6); osveziGUI();}
		}
		else if(strategCRNI.getClass().getName() == "gui.Racunalnik") {
			if(e.getSource() == stopnja1){strategCRNI.tezavnost(1); osveziGUI();}
			else if(e.getSource() == stopnja2){strategCRNI.tezavnost(2); osveziGUI();}
			else if(e.getSource() == stopnja3){strategCRNI.tezavnost(3); osveziGUI();}
			else if(e.getSource() == stopnja4){strategCRNI.tezavnost(4); osveziGUI();}
			else if(e.getSource() == stopnja5){strategCRNI.tezavnost(5); osveziGUI();}
			else if(e.getSource() == stopnja6){strategCRNI.tezavnost(6); osveziGUI();}
		}
		
	}
	
	private void osveziGUI() {
		//isto kot v kodi tictactoe, samo da sem odstranil case NEODLOCENO
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			krediti.setText("Preostali krediti: "+ String.valueOf(igra.krediti));
			if(strategCRNI.getClass().getName() != "gui.Racunalnik" && strategBELI.getClass().getName() != "gui.Racunalnik") {
				stopnja_tez.setText("");
			}
			else {
				stopnja_tez.setText(String.valueOf("Stopnja težavnosti: "+Racunalnik.stopnja));
				
			}
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
