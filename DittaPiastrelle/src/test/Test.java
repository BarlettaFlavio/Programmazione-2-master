package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import datasource.*;
import ditta.*;
import eccezioni.DipendenteException;
/**
 * 
 *Verifica che ogni classe coinvolta si comporti correttamente
 *testando i metodi principali che le compongono, con dati realistici 
 *e sollecitando la comparsa degli errori da gestire.
 */
public class Test {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Connection connection=DataSource.connection();
		Statement create_statement;
		 try {
			 System.out.println("INIZIO TEST");
			 create_statement=connection.createStatement();
			 
			 create_statement.executeUpdate(ComandiSQL.DROP_TABLE_DIPENDENTE);
			 create_statement.executeUpdate(ComandiSQL.DROP_TABLE_PIASTRELLA);
			 create_statement.executeUpdate(ComandiSQL.DROP_TABLE_VENDITA);
			 
			 DataAccessObject database;
			 //*************************************************************************************************************
			 System.out.println("TEST DIPENDENTI");
			 database=new DAODipendente();
			 System.out.println("1. Creazione tabella dipendente:"+database.create_table());
			 
			 System.out.println("\n2. Inserimento 2 dipendenti");
			 Dipendente dipendente= new Dipendente("Mario","Rossi",26,1254);
			 Dipendente dipendente_normale=(Dipendente) database.add(dipendente);
			 print(dipendente_normale);
			 dipendente= new Dipendente("Gino","Paoli",77,1300);
			 dipendente=(Dipendente) database.add(dipendente);
			 print(dipendente);
			 System.out.println("3. Modifica limite piastrelle del dipendente gino paoli");
			 dipendente=new Dipendente(dipendente.getId(),dipendente.getNome(),dipendente.getCognome(),dipendente.getEta(),1);
			 System.out.println("Modifica dipendente:"+database.update_table(dipendente));
			 System.out.println("4.Lettura dipendente modificato");
			 Dipendente dipendente_test_piastrelle_anno=(Dipendente) database.read(3);//3 perchè dopo aver inserito 2 dipendenti quello modificato è il successivo id
			 print(dipendente_test_piastrelle_anno);
			 //*************************************************************************************************************
			 System.out.println("\nTEST PIASTRELLE");
			 database=new DAOPiastrella();
			 System.out.println("1. Creazione tabella piastrella:"+database.create_table());
			 System.out.println("\n2. Inserimento 2 piastrelle");
			 Piastrella piastrella= new Piastrella("ES23", "nero", "10x10x2", TipoPiastrella.MARMOREA, 5000, 2.3f);
			 print(database.add(piastrella));
			 piastrella= new Piastrella("ikea", "bianca", "12x12x2", TipoPiastrella.MARMOREA, 2546, 10.23);
			 piastrella=(Piastrella) database.add(piastrella);
			 print(piastrella);
			 System.out.println("3. Modifica tinta piastrella ikea");
			 piastrella= new Piastrella(piastrella.getId(),
					 					piastrella.getNome(),
					 					"grigia",
					 					piastrella.getMisure(),
					 					piastrella.getTipo(),
					 					piastrella.getQuantita(),
					 					piastrella.getPrezzo());
			 System.out.println("Modifica piastrella:"+database.update_table(piastrella));
			 System.out.println("\n4.Lettura piastrella modificata");
			 piastrella=(Piastrella) database.read(3);//3 perchè dopo aver inserito 2 dipendenti quello modificato è il successivo id
			 print(piastrella);
			 //*************************************************************************************************************
			System.out.println("\nTEST VENDITE");
			database = new DAOVendita();
			System.out.println("1. Creazione tabella Vendita ="+ database.create_table());
			Vendita vendita = null;
			System.out.println("\n2. Inserimento vendita standard");
			try {
				vendita=(Vendita) database.add(dipendente_normale.Vendi(piastrella, 5));
				print(vendita);
			}catch(DipendenteException error) {
				error.printStackTrace();
			}
			System.out.println("\n3. Inserimento vendita con errore giornaliero");
			try {
				vendita=(Vendita)database.add(dipendente_normale.Vendi(piastrella, 21));
				print(vendita);
			}catch(DipendenteException error) {
				error.printStackTrace();
			}
			System.out.println("\n4. Inserimento vendita con errore annuale");
			try {
				vendita=(Vendita)database.add(dipendente_test_piastrelle_anno.Vendi(piastrella, 5));
			} catch(DipendenteException error) {
				error.printStackTrace();
			}
			System.out.println("\n5. Modifica vendita aumentando la quantita e di conseguenza il prezzo vendita");
			int quantita_nuova=vendita.getQuantita()+1;
			double prezzo_nuovo=(vendita.getPrezzo_vendita()/vendita.getQuantita())*quantita_nuova;
			vendita= new Vendita(vendita.getId(),
									vendita.getPiastrella(),
									vendita.getDipendente(),
									prezzo_nuovo,quantita_nuova,
									vendita.getData());
			
			System.out.println("Modifica vendita="+database.update_table(vendita));
			System.out.println("\n6. Lettura vendita modificata");
			vendita=(Vendita) database.read(2);//perchè dopo aver modificato il successivo id è 2
			print(vendita);
			
			System.out.println("\nLettura di tutte le tabelle nel database");
			List<Dipendente> lista_dipendenti = new ArrayList<Dipendente>();
			List<Piastrella> lista_piastrelle = new ArrayList<Piastrella>();
			List<Vendita> lista_vendita = new ArrayList<Vendita>();
			database= new DAODipendente();
			lista_dipendenti = (List<Dipendente>) database.readAll();
			
			database= new DAOPiastrella();
			lista_piastrelle = (List<Piastrella>) database.readAll();
			
			database= new DAOVendita();
			lista_vendita = (List<Vendita>) database.readAll();
			System.out.println("----------------------------------------LISTA DIPENDENTI---------------------------------\n");
			printList(lista_dipendenti);
			
			System.out.println("----------------------------------------LISTA PIASTRELLE---------------------------------\n");
			printList(lista_piastrelle);
			
			System.out.println("----------------------------------------LISTA VENDITE---------------------------------\n");
			printList(lista_vendita);
			
			
			 System.out.println("FINE TEST");
		 }catch(SQLException error) {
			 error.printStackTrace();
		 }

	}
	/**
	 * metodo di ausilio per la visualizzazione dei dati di classe lista in console
	 * @param list
	 */
	public static void printList(List<?> list) {
		for(Object e : list)System.out.println(e.toString()+"\n");
	}
	
	/**
	 * metodo di ausilio per la visualizzazione dei dati di un oggetto generico in console
	 * @param e
	 */
	static void print(Object e) {
		System.out.println(e.toString()+'\n');
	}
}
