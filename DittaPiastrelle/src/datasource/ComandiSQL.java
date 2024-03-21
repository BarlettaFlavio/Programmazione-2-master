package datasource;
/**
 * Questa classe e' una lista di comandi SQL formata da stringhe costanti quali creazione delle tabelle,comandi di inserimento,selezione,
 * di aggiornamento, e di cancellazione.
 * @author Camillo
 *
 */
public class ComandiSQL {
	public static final String CREATE_DIPENDENTE = "Create TABLE Dipendente (" 
													+ "ID_Dipendente int NOT NULL AUTO_INCREMENT,"
													+ "Nome varchar(20),"
													+ "Cognome varchar(20),"
													+ "Eta int(2),"
													+ "Limite_piastrelle_annuali int(4),"
													+ "PRIMARY KEY (ID_Dipendente))";
	public static final String CREATE_PIASTRELLA = "Create TABLE Piastrella (" 
													+ "ID_Piastrella int NOT NULL AUTO_INCREMENT,"
													+ "Nome varchar(50),"
													+ "Tinta varchar(20),"
													+ "Misure varchar(20),"
													+ "Quantita int(5),"
													+ "Prezzo DECIMAL(6,2),"
													+ "Tipo varchar(20),"
													+ "PRIMARY KEY (ID_Piastrella))";
	public static final String CREATE_VENDITA = "Create TABLE Vendita (" 
													+ "ID_Vendita int NOT NULL AUTO_INCREMENT,"
													+ "Data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
													+ "Prezzo_Vendita DECIMAL(8,2),"
													+ "Quantita int(2),"
													+ "FK_Dipendente int REFERENCES Dipendente(ID_Dipendente),"
													+ "FK_Piastrella int REFERENCES Piastrella(ID_Piastrella),"
													+ "PRIMARY KEY (ID_Vendita))";
	
	public static final String INSERT_DIPENDENTE = "INSERT INTO Dipendente (Nome,Cognome,Eta,Limite_piastrelle_annuali) VALUES(?,?,?,?)";
	public static final String INSERT_PIASTRELLA = "INSERT INTO Piastrella (Nome,Tinta,Misure,Quantita,Prezzo,Tipo) VALUES(?,?,?,?,?,?)";
	public static final String INSERT_VENDITA = "INSERT INTO Vendita (Prezzo_Vendita,Quantita,FK_Dipendente,FK_Piastrella) VALUES(?,?,?,?)";
	public static final String INSERT_VENDITA_DATA = "INSERT INTO Vendita (Data,Prezzo_Vendita,Quantita,FK_Dipendente,FK_Piastrella) VALUES(?,?,?,?,?)";
	
	public static final String SELECT_TUTTI_DIPENDENTI = "SELECT * FROM Dipendente ";
	public static final String SELECT_TUTTE_PIASTRELLE = "SELECT * FROM Piastrella ";
	public static final String SELECT_TUTTI_VENDITE = "SELECT * FROM Vendita ";
	
	public static final String SELECT_DIPENDENTE = "SELECT * FROM Dipendente WHERE ID_Dipendente=? ";
	public static final String SELECT_PIASTRELLA = "SELECT * FROM Piastrella WHERE ID_Piastrella=? ";
	public static final String SELECT_VENDITA = "SELECT * FROM Vendita WHERE ID_Vendita=? ";
	
	public static final String COUNT_VENDITE_ANNUALI_DIPENDENTE = "SELECT SUM(Quantita) AS Stato_vendite_annuali FROM Vendita WHERE FK_Dipendente=? AND YEAR(Data) = YEAR(CURDATE())";
	public static final String COUNT_VENDITE_GIORNALIERE_DIPENDENTE = "SELECT SUM(Quantita) AS Stato_vendite_giornaliere FROM Vendita WHERE FK_Dipendente=? AND date(Data) = CURRENT_DATE()";
	
	public static final String UPDATE_PIASTRELLA_QUANTITA = "UPDATE Piastrella SET Quantita=Quantita-? WHERE ID_Piastrella=?";
	public static final String UPDATE_DIPENDENTE_MAX_PIASTRELLE_ANNUALI = "UPDATE Dipendente SET Limite_piastrelle_annuali=? WHERE ID_Dipendente=?";
	
	public static final String DELETE_DIPENDENTE = "DELETE FROM Dipendente WHERE ID_Dipendente=?";
	public static final String DELETE_PIASTRELLA = "DELETE FROM Piastrella WHERE ID_Piastrella=?";
	public static final String DELETE_VENDITA = "DELETE FROM Vendita WHERE ID_Vendita=?";
	
	public static final String DROP_TABLE_DIPENDENTE = "DROP TABLE Dipendente";
	public static final String DROP_TABLE_PIASTRELLA = "DROP TABLE Piastrella";
	public static final String DROP_TABLE_VENDITA = "DROP TABLE Vendita";
	
	
	

}
