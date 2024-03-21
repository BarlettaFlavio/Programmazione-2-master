package datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ditta.Dipendente;


/**
 * Implementa la classe DataAccessObject e
 * svolge le operazioni di elaborazione dei dati degli oggetti
 * appartenendi alla classe Dipendente.
 * 
 *
 */
public class DAODipendente implements DataAccessObject{
	static Connection connection=DataSource.connection();
	
	/**
	 * Dopo aver effettuato la connesione al database, effettua
	 * l'operazione di creazione della tabella Dipendente, attraverso
	 * l'uso di query sql definite nella classe  ComandiSQL
	 * 
	 *  @return true = operazione completata con successo/ false = operazione fallita
	 */
	public boolean create_table() {
		
		Statement create_statement;
		try {
			create_statement=connection.createStatement();
			create_statement.executeUpdate(ComandiSQL.CREATE_DIPENDENTE);
			return true;
			
		}catch(SQLException error) {
			error.printStackTrace();
		}
		return false;
	}
	
	/**
	 *  Inserisce un oggetto di classe Dipendente
	 *  nella tabella Dipendente all'interno del database.
	 *  Utilizza un comando sql  definito nella classe ComandiSQL.
	 *  
	 *   @param e' un oggetto contenente i dati da inserire
	 *   @return null= in caso di errori/ un oggetto di classe Dipendente inserito nel database con ID
	 */
	@Override
	public Object add(Object e) {
		if(e instanceof Dipendente) {
			int id;
			String nome = ((Dipendente) e).getNome();
			String cognome= ((Dipendente) e).getCognome();
			int eta= ((Dipendente) e).getEta();
			int limite_piastrelle= ((Dipendente) e).getLimite_piastrelle_annuali();
			
			PreparedStatement prepare_statement;
			
			try {
				prepare_statement=connection.prepareStatement(ComandiSQL.INSERT_DIPENDENTE,Statement.RETURN_GENERATED_KEYS);
				prepare_statement.setString(1, nome);
				prepare_statement.setString(2, cognome);
				prepare_statement.setInt(3, eta);
				prepare_statement.setInt(4, limite_piastrelle);
				prepare_statement.executeUpdate();
				
				ResultSet generetedKey =prepare_statement.getGeneratedKeys();
				generetedKey.next();
				id=generetedKey.getInt(1);
				
				return new Dipendente(id,nome,cognome,eta,limite_piastrelle);
			}catch(SQLException error) {
				error.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * Scarica il contenuto dell'intera tabella Dipendente nel database
	 * e inserisce i dati in una lista di oggetti di classe Dipendente
	 * Utilizza un comando sql definito nella classe ComandiSQL
	 * 
	 * @return null = in caso di errori / una lista di oggetti di classe Dipendente con ID
	 */
	@Override
	public List<?> readAll() {
		List<Dipendente> lista= new ArrayList<Dipendente>();
		Statement statement;
		try {
			statement=connection.createStatement();
			ResultSet result=statement.executeQuery(ComandiSQL.SELECT_TUTTI_DIPENDENTI);
			while(result.next()) {
				lista.add(new Dipendente(result.getInt("id_dipendente"),
											result.getString("nome"),
											result.getString("cognome"),
											result.getInt("eta"),
											result.getInt("limite_piastrelle_annuali")));
			}
			return lista;
		}catch(SQLException error) {
			error.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Scarica le informazioni riguardanti un specifico Dipendente della tabella Dipendente
	 * nel database e le istanzia in un oggetto di classe Dipendente
	 * Utilizza un comando sql definito nella classe ComandiSQL.
	 * 
	 * @param id = codice identificativo riguardante la locazione del Dipendente nel database
	 * @return null = in caso di errori/ un oggetto di classe Dipendente con ID
	 */
	@Override
	public Object read(int id) {
		try {
			PreparedStatement prepare_statement=connection.prepareStatement(ComandiSQL.SELECT_DIPENDENTE);
			prepare_statement.setInt(1, id);
			ResultSet row_byID =prepare_statement.executeQuery();
			row_byID.next();
			
			return new Dipendente(row_byID.getInt("id_dipendente"),
									row_byID.getString("nome"),
									row_byID.getString("cognome"),
									row_byID.getInt("eta"),
									row_byID.getInt("limite_piastrelle_annuali"));
		
			
		}catch(SQLException error) {
			error.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Modifica i dati riguardanti un specifico Dipendente nel database, eseguendo una rimozione dei dati obsoleti
	 * e inserendone i nuovi
	 * Utilizza gli appositi comandi sql definiti nella classe ComandiSQL.
	 * 
	 * @param e Un oggetto contenente i dati da inserire
	 * @return true= operazione completata con successo/ false= operazione fallita.
	 */
	@Override
	public boolean update_table(Object e) {
		if(e instanceof Dipendente) {
			
			int id=((Dipendente) e).getId();
			String nome = ((Dipendente) e).getNome();
			String cognome= ((Dipendente) e).getCognome();
			int eta= ((Dipendente) e).getEta();
			int limite_piastrelle= ((Dipendente) e).getLimite_piastrelle_annuali();
			
			PreparedStatement prepare_statement;
			try {
				
				prepare_statement=connection.prepareStatement(ComandiSQL.DELETE_DIPENDENTE);
				prepare_statement.setInt(1, id);
				prepare_statement.executeUpdate();
				
				prepare_statement=connection.prepareStatement(ComandiSQL.INSERT_DIPENDENTE);
				prepare_statement.setString(1, nome);
				prepare_statement.setString(2, cognome);
				prepare_statement.setInt(3, eta);
				prepare_statement.setInt(4, limite_piastrelle);
				prepare_statement.executeUpdate();
				
				return true;
			}catch(SQLException error) {
				error.printStackTrace();
			}
			
		}	
		return false;
	}
	
	/**
	 * Interagisce con i dati della tabella Vendita all'interno del database,
	 * eseguendo un operazione di somma giornaliera del numero di vendite effettuate da un
	 * determinato dipendente, tramite id.
	 * 
	 * @param id Identificativo del dipendente
	 * @return il numero di vendite effettuate o 0 in caso di nessuna vendita
	 */
	public static int getNumeroVenditeGiornaliere(int id) {
		PreparedStatement prepare_statement;
		ResultSet result;
		try {
			prepare_statement=connection.prepareStatement(ComandiSQL.COUNT_VENDITE_GIORNALIERE_DIPENDENTE);
			prepare_statement.setInt(1, id);
			result=prepare_statement.executeQuery();
			result.next();
			return result.getInt(1);//stato_vendite_giornaliere
		}catch(SQLException error) {
			error.printStackTrace();
		}
		return 0;
	}
	/**
	 * Interagisce con i dati della tabella Vendita all'interno del database,
	 * eseguendo un operazione di somma annuale del numero di vendite effettuate da un
	 * determinato dipendente, tramite id.
	 * 
	 * @param id Identificativo del dipendente
	 * @return il numero di vendite effettuate o 0 in caso di nessuna vendita
	 */
	public static int getNumeroVenditeAnnuali(int id) {
		PreparedStatement prepare_statement;
		ResultSet result;
		try {
			prepare_statement=connection.prepareStatement(ComandiSQL.COUNT_VENDITE_ANNUALI_DIPENDENTE);
			prepare_statement.setInt(1, id);
			result=prepare_statement.executeQuery();
			result.next();
			return result.getInt(1);//stato_vendite_annuali
		}catch(SQLException error) {
			error.printStackTrace();
		}
		return 0;
	}
}
