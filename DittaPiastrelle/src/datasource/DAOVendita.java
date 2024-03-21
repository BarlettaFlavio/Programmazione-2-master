package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ditta.*;

public class DAOVendita implements DataAccessObject{
	Connection connection=DataSource.connection();
	/**
	 * Dopo aver effettuato la connesione al database, effettua
	 * l'operazione di creazione della tabella Vendita, attraverso
	 * l'uso di query sql definite nella classe  ComandiSQL
	 * 
	 *  @return true = operazione completata con successo/ false = operazione fallita
	 */
	public boolean create_table() {
		Statement create_statement;
		try {
			create_statement=connection.createStatement();
			create_statement.executeUpdate(ComandiSQL.CREATE_VENDITA);
			return true;
			
		}catch(SQLException error) {
			error.printStackTrace();
		}
		return false;
	}
	
	/**
	 *  Inserisce un oggetto di classe Vendita
	 *  nella tabella Vendita all'interno del database.
	 *  Utilizza un comando sql  definito nella classe ComandiSQL.
	 *  
	 *   @param e' un oggetto contenente i dati da inserire
	 *   @return null= in caso di errori/ un oggetto di classe Vendita inserito nel database con ID
	 */
	@Override
	public Object add(Object e) {
		if(e instanceof Vendita) {
			int id;
			double prezzo=((Vendita) e).getPrezzo_vendita();
			int quantita=((Vendita) e).getQuantita();
			Dipendente dipendente=((Vendita) e).getDipendente();
			Piastrella piastrella=((Vendita) e).getPiastrella();
			int FK_Dipendente=dipendente.getId();
			int FK_Piastrella=piastrella.getId();
			
			PreparedStatement prepare_statement;
			
			try {
				
				prepare_statement=connection.prepareStatement(ComandiSQL.INSERT_VENDITA,Statement.RETURN_GENERATED_KEYS);
				prepare_statement.setDouble(1,prezzo);
				prepare_statement.setInt(2, quantita);
				prepare_statement.setInt(3, FK_Dipendente);
				prepare_statement.setInt(4, FK_Piastrella);
				prepare_statement.executeUpdate();
				
				java.util.Date utilDate= new java.util.Date();
				Timestamp data= new Timestamp(utilDate.getTime());
				
				ResultSet generetedKey =prepare_statement.getGeneratedKeys();
				generetedKey.next();
				id=generetedKey.getInt(1);
				
				prepare_statement=connection.prepareStatement(ComandiSQL.UPDATE_PIASTRELLA_QUANTITA);
				prepare_statement.setInt(1, quantita);
				prepare_statement.setInt(2, FK_Piastrella);
				prepare_statement.executeUpdate();
				
				
				
				return new Vendita(id,piastrella,dipendente,prezzo, quantita,data);
			}catch(SQLException error) {
				error.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * Scarica il contenuto dell'intera tabella Vendita nel database
	 * e inserisce i dati in una lista di oggetti di classe Vendita
	 * Utilizza un comando sql definito nella classe ComandiSQL
	 * 
	 * @return null = in caso di errori / una lista di oggetti di classe Dipendente con ID
	 */
	@Override
	public List<?> readAll() {
		List<Vendita> lista= new ArrayList<Vendita>();
		Statement statement;
		try {
			statement=connection.createStatement();
			ResultSet result=statement.executeQuery(ComandiSQL.SELECT_TUTTI_VENDITE);
			while(result.next()) {
				
				int FK_Dipendente=result.getInt("FK_Dipendente");
				int FK_Piastrella=result.getInt("FK_Piastrella");
				
				DataAccessObject database= new DAODipendente();
				
				Dipendente dipendente= (Dipendente) database.read(FK_Dipendente);
				
				database=new DAOPiastrella();
				Piastrella piastrella = (Piastrella) database.read(FK_Piastrella);
				
				lista.add(new Vendita(result.getInt("id_vendita"),
										piastrella,
										dipendente,
										result.getDouble("prezzo_vendita"),
										result.getInt("quantita"),
										result.getTimestamp("data")));
			}
			return lista;
		}catch(SQLException error) {
			error.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Scarica le informazioni riguardanti una specifica Vendita della tabella Vendita
	 * nel database e le istanzia in un oggetto di classe Vendita
	 * Utilizza un comando sql definito nella classe ComandiSQL.
	 * 
	 * @param id = codice identificativo riguardante la locazione della Vendita nel database
	 * @return null = in caso di errori/ un oggetto di classe Vendita con ID
	 */
	@Override
	public Object read(int id) {
		try {
			PreparedStatement prepare_statement=connection.prepareStatement(ComandiSQL.SELECT_VENDITA);
			prepare_statement.setInt(1, id);
			ResultSet row_byID =prepare_statement.executeQuery();
			row_byID.next();
			
			int FK_Dipendente=row_byID.getInt("FK_Dipendente");
			int FK_Piastrella=row_byID.getInt("FK_Piastrella");
			
			DataAccessObject database= new DAODipendente();
			
			Dipendente dipendente= (Dipendente) database.read(FK_Dipendente);
			
			database=new DAOPiastrella();
			Piastrella piastrella = (Piastrella) database.read(FK_Piastrella);
			
			
			return new Vendita(row_byID.getInt("id_vendita"),
									piastrella,
									dipendente,
									row_byID.getDouble("prezzo_vendita"),
									row_byID.getInt("quantita"),
									row_byID.getTimestamp("data"));
		
			
		}catch(SQLException error) {
			error.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Modifica i dati riguardanti una specifica Vendita nel database, eseguendo una rimozione dei dati obsoleti
	 * e inserendone i nuovi
	 * Utilizza gli appositi comandi sql definiti nella classe ComandiSQL.
	 * 
	 * @param e Un oggetto contenente i dati da inserire
	 * @return true= operazione completata con successo/ false= operazione fallita.
	 */
	@Override
	public boolean update_table(Object e) {
		if(e instanceof Vendita) {
			
			int id=((Vendita) e).getId();
			double prezzo=((Vendita) e).getPrezzo_vendita();
			int quantita=((Vendita) e).getQuantita();
			Dipendente dipendente=((Vendita) e).getDipendente();
			Piastrella piastrella=((Vendita) e).getPiastrella();
			int FK_Dipendente=dipendente.getId();
			int FK_Piastrella=piastrella.getId();
			Timestamp data=((Vendita) e).getData();
			
			PreparedStatement prepare_statement;
			try {
				
				prepare_statement=connection.prepareStatement(ComandiSQL.DELETE_VENDITA);
				prepare_statement.setInt(1, id);
				prepare_statement.executeUpdate();
				
				prepare_statement=connection.prepareStatement(ComandiSQL.INSERT_VENDITA_DATA);
				prepare_statement.setTimestamp(1, data);
				prepare_statement.setDouble(2,prezzo);
				prepare_statement.setInt(3, quantita);
				prepare_statement.setInt(4, FK_Dipendente);
				prepare_statement.setInt(5, FK_Piastrella);
				prepare_statement.executeUpdate();
				
				return true;
			}catch(SQLException error) {
				error.printStackTrace();
			}
			
		}	
		return false;
	}
	
}
