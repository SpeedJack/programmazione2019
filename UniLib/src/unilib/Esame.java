package unilib;

import java.time.LocalDate;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Esame
{
	private final SimpleStringProperty codiceEsame;
	private final SimpleStringProperty insegnamento;
	private final SimpleBooleanProperty inf;
	private final SimpleIntegerProperty crediti;
	private final SimpleStringProperty voto;
	private final SimpleObjectProperty<LocalDate> data;
	
	public Esame(String codiceEsame, String insegnamento, boolean inf, int crediti, String voto, LocalDate data)
	{
		this.codiceEsame = new SimpleStringProperty(codiceEsame);
		this.insegnamento = new SimpleStringProperty(insegnamento);
		this.inf = new SimpleBooleanProperty(inf);
		this.crediti = new SimpleIntegerProperty(crediti);
		this.voto = new SimpleStringProperty(voto);
		this.data = new SimpleObjectProperty<>(data);
	}
	
	public String getCodiceEsame()
	{
		return codiceEsame.get();
	}
	
	public void setCodiceEsame(String codiceEsame)
	{
		this.codiceEsame.set(codiceEsame);
	}
	
	public String getInsegnamento()
	{
		return insegnamento.get();
	}
	
	public void setInsegnamento(String insegnamento)
	{
		this.insegnamento.set(insegnamento);
	}
	
	public boolean isInf()
	{
		return inf.get();
	}
	
	public void setInf(boolean inf)
	{
		this.inf.set(inf);
	}
	
	public int getCrediti()
	{
		return crediti.get();
	}
	
	public void setCrediti(int crediti)
	{
		this.crediti.set(crediti);
	}
	
	public String getVoto()
	{
		return voto.get();
	}
	
	public int getVotoNumerico()
	{
		if (isLode())
			return 30;
		return Integer.parseInt(voto.get());
	}
	
	public boolean isLode()
	{
		return voto.get().endsWith("L");
	}	
	
	public void setVoto(String voto)
	{
		this.voto.set(voto);
	}
	
	public LocalDate getData()
	{
		return data.get();
	}
	
	public void setData(LocalDate data)
	{
		this.data.set(data);
	}
	
	public static Esame ottieniEsame(String codiceEsame)
	{
		return ArchivioEsami.ottieniIstanza().ottieniEsame(codiceEsame);
	}
}
