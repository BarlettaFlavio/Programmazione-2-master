package datasource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ditta.Piastrella;
import ditta.TipoPiastrella;

/**
 * Implementa la classe DataAccessObject e svolge le operazioni.
 * di elaborazione dei dati degli oggetti appartenenti alla classe Piastrella.
 * @author Camillo
 *
 */
public class DAOPiastrella implements DataAccessObject  {
	Connection connection = DataSource.connection();
	/**
 * Dopo aver effettuato la connesione al database, effettua
 * l'operazione di creazione della tabella Piastrella, attraverso
 * l'uso di query sql definite nella classe  ComandiSQL
 * 
 *  @return true = operazione completata con successo/ false = operazione fallita
 */
	@Override
	public boolean create_table() {
		Statement create_statement;
		try {
			create_statement=connection.createStatement();
			create_statement.executeUpdate(ComandiSQL.CREATE_PIASTRELLA);
			return true;
		}catch(SQLException e) {
			System.out.println("errore: " + e.getMessage());
		}
		return false;
	}
/**
 *  Inserisce un oggetto di classe Piastrella
 *  nella tabella Piastrella all'interno del database.
 *  Utilizza un comando sql  definito nella classe ComandiSQL.
 *  
 *   @param e' un oggetto contenente i dati da inserire
 *   @return null= in caso di errori/ un oggetto di classe Piastrella inserito nel database con ID
 */
	@Override
	public Object add(Object e) {
		if(e instanceof Piastrella) {
			int id,quantita;
			String nome,tinta,misure;
			double prezzo;
			TipoPiastrella tipo;
			
			nome=((Piastrella) e).getNome();
			tinta=((Piastrella) e).getTinta();
			misure=((Piastrella) e).getMisure();
			quantita=((Piastrella)e).getQuantita();
			prezzo=((Piastrella)e).getPrezzo();
			tipo=((Piastrella)e).getTipo();
			PreparedStatement prepare_statement;
			
			try {
				prepare_statement = connection.prepareStatement(ComandiSQL.INSERT_PIASTRELLA,Statement.RETURN_GENERATED_KEYS);
				prepare_statement.setString(1, nome);
				prepare_statement.setString(2, tinta);
				prepare_statement.setString(3, misure);
				prepare_statement.setString(6, tipo.toString());
				prepare_statement.setInt(4, quantita);
				prepare_statement.setDouble(5, prezzo);
				prepare_statement.executeUpdate();
				ResultSet generatedKeys = prepare_statement.getGeneratedKeys();
				generatedKeys.next();
				id=generatedKeys.getInt(1);
				return new Piastrella(id,nome,tinta,misure,tipo,quantita,prezzo);
			} catch (SQLException err) {
				System.out.println("errore : " + err.getMessage());
			}}
		// TODO Auto-generated method stub
		return null;
	}
/**
 * Scarica il contenuto dell'intera tabella Piastrella nel database
 * e inserisce i dati in una lista di oggetti di classe Piastrella
 * Utilizza un comando sql definito nella classe ComandiSQL
 * 
 * @return null = in caso di errori / una lista di oggetti di classe Piastrella con ID
 */
	@Override
	public List<?> readAll() {
		List<Piastrella> Lista = new ArrayList<Piastrella>();
		Statement create_statement;
		try {
			create_statement=connection.createStatement();
			ResultSet rs = create_statement.executeQuery(ComandiSQL.SELECT_TUTTE_PIASTRELLE);
			while (rs.next()) {
				Lista.add(new Piastrella (rs.getInt("id_piastrella"), rs.getString("nome"), rs.getString("tinta"), rs.getString("misure"), TipoPiastrella.valueOf(rs.getString("tipo")),rs.getInt("quantita"),rs.getDouble("prezzo")));
				}
			return Lista;
		} catch (SQLException error) {
			System.out.println("errore : " + error.getMessage());
		}
		// TODO Auto-generated method stub
		return null;
	}
/**
 * Scarica le informazioni riguardanti una specifica Piastrella della tabella Piastrella
 * nel database e le istanzia in un oggetto di classe Piastrella
 * Utilizza un comando sql definito nella classe ComandiSQL.
 * 
 * @param id = codice identificativo riguardante la locazione della Piastrella nel database
 * @return null = in caso di errori/ un oggetto di classe Piastrella con ID
 */
	@Override
	public Object read(int id) {
		try {
			PreparedStatement prepare_statement = connection.prepareStatement(ComandiSQL.SELECT_PIASTRELLA);
			prepare_statement.setInt(1, id);
			ResultSet row_byID = prepare_statement.executeQuery();
			row_byID.next();
			Piastrella piastrella = new Piastrella(row_byID.getInt("id_piastrella"),
													row_byID.getString("nome"),
													row_byID.getString("tinta"),
													row_byID.getString("misure"),
													TipoPiastrella.valueOf(row_byID.getString("tipo")),
													row_byID.getInt("quantita"),
													row_byID.getDouble("prezzo"));
			return piastrella;
		}catch(SQLException e) {
			System.out.println("errore: " + e.getMessage());
		}
		return null;
	}
/**
 * Modifica i dati riguardanti una specifica Piastrella nel database, eseguendo una rimozione dei dati obsoleti
 * e inserendone i nuovi
 * Utilizza gli appositi comandi sql definiti nella classe ComandiSQL.
 * 
 * @param e Un oggetto contenente i dati da inserire
 * @return true= operazione completata con successo/ false= operazione fallita.
 */
	@Override
	public boolean update_table(Object e) {
		if(e instanceof Piastrella) {
			int id=((Piastrella) e).getId();
			String nome=((Piastrella) e). getNome();
			String tinta=((Piastrella) e).getTinta();
			String misure=((Piastrella) e).getMisure();
			int quantita=((Piastrella)e).getQuantita();
			double prezzo=((Piastrella)e).getPrezzo();
			TipoPiastrella tipo=((Piastrella)e).getTipo();
			PreparedStatement prepare_statement;
			try {
				prepare_statement=connection.prepareStatement(ComandiSQL.DELETE_PIASTRELLA);
				prepare_statement.setInt(1, id);
				prepare_statement.executeUpdate();
				
				prepare_statement=connection.prepareStatement(ComandiSQL.INSERT_PIASTRELLA);
				prepare_statement.setString(1, nome);
				prepare_statement.setString(2, tinta);
				prepare_statement.setString(3, misure);
				prepare_statement.setString(6, tipo.toString());
				prepare_statement.setInt(4, quantita);
				prepare_statement.setDouble(5, prezzo);
				prepare_statement.executeUpdate();
				return true;
			}catch(SQLException error) {
				System.out.println("errore:" + error.getMessage());
			}
		}

		return false;
	}

}
