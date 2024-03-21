package ditta;

import datasource.DAODipendente;
import eccezioni.*;

/**
 * Questa classe ha lo scopo di inizializzare gli attributi quali id,nome,cognome,eta ecc e inoltre il metodo vendita inizializza un oggetto
 * vendita ed effettua il controllo sul limite di piastrelle che ogni dipendente puo vendere
 */
public class Dipendente {

	final static int MAX_PIASTRELLE_PER_GIORNO = 20;
	private int id;
	private String nome;
	private String cognome;
	private int eta;
	private int limite_piastrelle_annuali;
	
	/**
	 * Costruttore per instanziare oggetti dal database
	 * @param id
	 * @param nome
	 * @param cognome
	 * @param eta
	 * @param limite_piastrelle_annuali
	 */
	public Dipendente(int id, String nome, String cognome, int eta, int limite_piastrelle_annuali) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.eta = eta;
		this.limite_piastrelle_annuali = limite_piastrelle_annuali;
	}

/**
 * Costruttore per l'acquisizione dati da inserire nel database
 * @param nome
 * @param cognome
 * @param eta
 * @param limite_piastrelle_annuali
 */
	public Dipendente(String nome, String cognome, int eta, int limite_piastrelle_annuali) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.eta = eta;
		this.limite_piastrelle_annuali = limite_piastrelle_annuali;
	}

	public int getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}


	public String getCognome() {
		return cognome;
	}


	public int getEta() {
		return eta;
	}


	public int getLimite_piastrelle_annuali() {
		return limite_piastrelle_annuali;
	}


	@Override
	public String toString() {
		return "Dipendente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", eta=" + eta
				+ ", limite_piastrelle_annuali=" + limite_piastrelle_annuali + "]";
	}
/**
 * Effettua il controllo delle eccezioni riguardanti la vendita del dipendente
 * In caso positivo effettua la vendita con la quantita inserita creando un oggetto di tipo vendita
 * In caso negativo restituisce l'errore
 * @param piastrella = la piastrella selezionata da vendere
 * @param quantita = il numero di piastrelle da vendere
 * @return = un oggetto di classe vendita 
 * @throws DipendenteException = messaggio d'errore 
 */
	public Vendita Vendi (Piastrella piastrella,int quantita) throws DipendenteException {
		int daily_count = DAODipendente.getNumeroVenditeGiornaliere(this.id);
		int year_count = DAODipendente.getNumeroVenditeAnnuali(this.id);
		if((year_count+quantita)<this.limite_piastrelle_annuali) {
			if((daily_count+quantita)<MAX_PIASTRELLE_PER_GIORNO) {
				double prezzo_totale = quantita * piastrella.getPrezzo();
				return new Vendita (piastrella,this,prezzo_totale,quantita);
			} else {
				throw new DipendenteException (CodiciErrore.MAX_DAILY_SALES_EXCEEDED);
				}} else {
					throw new DipendenteException(CodiciErrore.MAX_ANNUAL_SALES_EXCEEDED);
				}
		}
	}



