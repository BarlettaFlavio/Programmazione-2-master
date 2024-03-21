package eccezioni;

/**
 * 
 * Classe per la gestione degli errori associati alla classe Dipendente.
 * Estende la classe Exception
 *
 */
public class DipendenteException extends Exception{

	public DipendenteException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DipendenteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DipendenteException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DipendenteException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DipendenteException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
		
}
