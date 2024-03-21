package datasource;

import java.util.List;
/**
 *  Sviluppo dell'interfaccia DAO per gestire l'acquisizione,visualizzazione e manipolazione dei dati sul database
 * @author Camillo
 *
 */
public interface DataAccessObject {
	public boolean create_table();
	public Object add(Object e);
	public List<?> readAll();
	public Object read(int id);
	public boolean update_table(Object e);
}
