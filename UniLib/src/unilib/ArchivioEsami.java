package unilib;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArchivioEsami
{
	private static ArchivioEsami istanzaSingleton;
	
	private final String databaseUri = "jdbc:mysql://localhost:3306/unilib";
	private final String username = "root";
	private final String password = "";
	
	private Connection connessione;
	
	public static ArchivioEsami ottieniIstanza()
	{
		if (istanzaSingleton == null)
			istanzaSingleton = new ArchivioEsami();
		return istanzaSingleton;
	}
	
	public void disconnetti()
	{
		try {
			connessione.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			connessione = null;
		}
	}
	
	public Esame ottieniEsame(String codiceEsame)
	{
		Esame esame = null;
		connetti();
		try (PreparedStatement st = connessione.prepareStatement("SELECT * FROM esami WHERE codiceEsame = ? ORDER BY data DESC LIMIT 1;")) {
			st.setString(1, codiceEsame);
			ResultSet rs = st.executeQuery();
			if (rs.first())
				esame = new Esame(
					rs.getString("codiceEsame"),
					rs.getString("insegnamento"),
					rs.getBoolean("INF"),
					rs.getInt("crediti"),
					rs.getString("voto"),
					SqlDateToLocalDate(rs.getDate("data"))
				);
			rs.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return esame;
	}
	
	public ObservableList<Esame> ottieniEsami(int matricola)
	{
		ObservableList<Esame> esami = FXCollections.observableArrayList();
		connetti();
		try (PreparedStatement st = connessione.prepareStatement("SELECT * FROM esami WHERE matricolaStudente = ? ORDER BY data;")) {
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			while (rs.next())
				esami.add(new Esame(
					rs.getString("codiceEsame"),
					rs.getString("insegnamento"),
					rs.getBoolean("INF"),
					rs.getInt("crediti"),
					rs.getString("voto"),
					SqlDateToLocalDate(rs.getDate("data"))
				));
			rs.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return esami;
	}
	
	public HashMap<String, Number> ottieniMediaEsami()
	{
		HashMap<String, Number> mediaEsami = new HashMap<>();
		connetti();
		int valoreLode = ParametriDiConfigurazione.ottieniConfigurazione().corsoDiLaurea.valoreLode;
		try (PreparedStatement st = connessione.prepareStatement("SELECT insegnamento, AVG(IF(voto LIKE '%L', ?, CAST(voto AS UNSIGNED))) AS media FROM esami GROUP BY insegnamento ORDER BY insegnamento;")) {
			st.setInt(1, valoreLode);
			ResultSet rs = st.executeQuery();
			while (rs.next())
				mediaEsami.put(rs.getString("insegnamento"), rs.getDouble("media"));
			rs.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return mediaEsami;
	}
	
	public void aggiungiEsame(int matricola, Esame esame)
	{
		connetti();
		try (PreparedStatement ps = connessione.prepareStatement("INSERT INTO esami(matricolaStudente, codiceEsame, insegnamento, INF, crediti, voto, data) VALUES(?, ?, ?, ?, ?, ?, ?);")) {
			ps.setInt(1, matricola);
			ps.setString(2, esame.getCodiceEsame());
			ps.setString(3, esame.getInsegnamento());
			ps.setBoolean(4, esame.isInf());
			ps.setInt(5, esame.getCrediti());
			ps.setString(6, esame.getVoto());
			ps.setDate(7, LocalDateToSqlDate(esame.getData()));
			ps.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	public void modificaEsame(int matricola, String codiceEsame, Esame esame)
	{
		connetti();
		try (PreparedStatement ps = connessione.prepareStatement("UPDATE esami SET codiceEsame = ?, insegnamento = ?, INF = ?, crediti = ?, voto = ?, data = ? WHERE matricolaStudente = ? AND codiceEsame = ?;")) {
			ps.setString(1, esame.getCodiceEsame());
			ps.setString(2, esame.getInsegnamento());
			ps.setBoolean(3, esame.isInf());
			ps.setInt(4, esame.getCrediti());
			ps.setString(5, esame.getVoto());
			ps.setDate(6, LocalDateToSqlDate(esame.getData()));
			ps.setInt(7, matricola);
			ps.setString(8, codiceEsame);
			ps.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	public void eliminaEsame(int matricola, Esame esame)
	{
		connetti();
		try (PreparedStatement ps = connessione.prepareStatement("DELETE FROM esami WHERE matricolaStudente = ? AND codiceEsame = ?;")) {
			ps.setInt(1, matricola);
			ps.setString(2, esame.getCodiceEsame());
			ps.executeUpdate();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	private ArchivioEsami() { }
	
	private void connetti()
	{
		if (connessione == null)
			try {
				connessione = DriverManager.getConnection(databaseUri, username, password);
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
				connessione = null;
			}
	}
	
	private static LocalDate SqlDateToLocalDate(Date data) // 01
	{
		return new Date(data.getTime()).toLocalDate();
	}
	
	private static Date LocalDateToSqlDate(LocalDate data) // 01
	{
		return Date.valueOf(data);
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		try {
			disconnetti();
		} finally {
			super.finalize();
		}
	}
}

/* COMMENTI:
 * (01) È necessario convertire il tipo java.time.LocalDate (utilizzato dalla
 * classe Esame) in java.sql.Date (utilizzato da MySQL).
 */
