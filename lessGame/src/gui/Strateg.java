package gui;
//povzeto po primeru tictactoe, po potrebi prilagojeno za igro less
public abstract class Strateg {
	
	public abstract void na_potezi();
	
	public abstract void prekini();
	
	public abstract void klik(int i, int j);

	public abstract void tezavnost(int k);
	

}
