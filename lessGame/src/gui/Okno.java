package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logikaIgre.Igra;

public class Okno extends JFrame implements ActionListener{
	private IgralnoPolje polje;
	private Igra igra;
	private JLabel status;

	public Okno() throws HeadlessException {
		this.setTitle("Less");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// menu
				JMenuBar menu_bar = new JMenuBar();
				this.setJMenuBar(menu_bar);
				JMenu igra_menu = new JMenu("Igra");
				menu_bar.add(igra_menu);
				nova_igra = new JMenuItem("Nova igra");
				igra_menu.add(nova_igra);
				nova_igra.addActionListener(this);
			
				// igralno polje
				polje = new IgralnoPolje(this);
				GridBagConstraints polje_layout = new GridBagConstraints();
				polje_layout.gridx = 0;
				polje_layout.gridy = 0;
				polje_layout.fill = GridBagConstraints.BOTH;
				polje_layout.weightx = 1.0;
				polje_layout.weighty = 1.0;
				getContentPane().add(polje, polje_layout);
				
				// statusna vrstica za sporoèila
				status = new JLabel();
				status.setFont(new Font("SansSerif",
									    Font.PLAIN,
									    20));
				GridBagConstraints status_layout = new GridBagConstraints();
				status_layout.gridx = 0;
				status_layout.gridy = 1;
				status_layout.anchor = GridBagConstraints.CENTER;
				getContentPane().add(status, status_layout);
				
				// zaènemo novo igro
		nova_igra();
	}
	
	

}
