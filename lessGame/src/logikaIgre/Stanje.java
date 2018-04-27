package logikaIgre;

public enum Stanje {
	NA_POTEZI_BEL,
	NA_POTEZI_CRN,
	ZMAGAL_CRN,
	ZMAGAL_BEL;
	public boolean ovira_zgoraj = false;
	public boolean ovira_spodaj = false;
	public boolean ovira_levo = false;
	public boolean ovira_desno = false;
	//Neodloceno se mi zdi da ni možno? + razlog za zmago vedno enak, torej tudi tisti drug del v primeru ni proteben
}
