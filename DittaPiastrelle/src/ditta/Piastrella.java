package ditta;
/**
 * Questa classe ha lo scopo di inizializzare gli attributi quali id, nome, tinta, misura, tipo, quantita, prezzo
 * @author Camillo
 *
 */
public class Piastrella {
	
	private int id;
	private String nome;
	/**
	 * Per l'attributo tinta ci riferiamo al colore che puo' avere una piastrella
	 */
	private String tinta;
	/**
	 * Per misure invece ci riferiamo alla lunghezza, larghezza e altezza sotto forma di Stringa 
	 */
	private String misure;
	/**
	 * Per tipo invece ci riferiamo al tipo della piastrella contenuta nella classe enum TipoPiastrella
	 */
	private TipoPiastrella tipo;
	/**
	 * Per quantita indichiamo la quantita' disponibile in magazzino
	 */
	private int quantita;
	private double prezzo;
	
	/**
	 * Costruttore per istanziare oggetti dal database
	 * @param id
	 * @param nome
	 * @param tinta
	 * @param misure
	 * @param tipo
	 * @param quantita
	 * @param prezzo
	 */
	public Piastrella(int id, String nome, String tinta, String misure, TipoPiastrella tipo, int quantita,
			double prezzo) {
		super();
		this.id = id;
		this.nome = nome;
		this.tinta = tinta;
		this.misure = misure;
		this.tipo = tipo;
		this.quantita = quantita;
		this.prezzo = prezzo;
	}

/**
 * Costruttore per l'acquisizione dati da inserire nel database
 * @param nome
 * @param tinta
 * @param misure
 * @param tipo
 * @param quantita
 * @param prezzo
 */
	public Piastrella(String nome, String tinta, String misure, TipoPiastrella tipo, int quantita, double prezzo) {
		super();
		this.nome = nome;
		this.tinta = tinta;
		this.misure = misure;
		this.tipo = tipo;
		this.quantita = quantita;
		this.prezzo = prezzo;
	}


	@Override
	public String toString() {
		return "Piastrella [id=" + id + ", nome=" + nome + ", tinta=" + tinta + ", misure=" + misure + ", quantita="
				+ quantita + ", prezzo=" + prezzo + "]";
	}


	public int getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}


	public String getTinta() {
		return tinta;
	}


	public String getMisure() {
		return misure;
	}


	public TipoPiastrella getTipo() {
		return tipo;
	}


	public int getQuantita() {
		return quantita;
	}


	public double getPrezzo() {
		return prezzo;
	}
	
	
	
	
}
