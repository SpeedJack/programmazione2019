package unilib;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataOraEvento implements Serializable // 01
{
	public final String formato;
	public final String valore;
	
	public DataOraEvento(String formato, String valore)
	{
		this.formato = formato;
		this.valore = valore;
	}
	
	public DataOraEvento(String formato, LocalDateTime data)
	{
		this(formato, data.format(DateTimeFormatter.ofPattern(formato)));
	}
}

/* COMMENTI:
 * (01) La classe rappresenta un oggetto data oppure ora, con relativo formato,
 * di un EventoDiNavigazione.
 */