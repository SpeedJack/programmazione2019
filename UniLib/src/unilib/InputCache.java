package unilib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;

public class InputCache implements Serializable
{
	public String codiceEsame = "";
	public String insegnamento = "";
	public boolean inf = false;
	public int crediti = 0;
	public String voto = "";
	public LocalDate data = null;
	public String codiceEsameSelezionato = null;
	
	public InputCache(Esame input, String esameSelezionato)
	{
		codiceEsame = input.getCodiceEsame();
		insegnamento = input.getInsegnamento();
		inf = input.isInf();
		crediti = input.getCrediti();
		voto = input.getVoto();
		data = input.getData();
		codiceEsameSelezionato = esameSelezionato;
	}
	
	public Esame ottieniInputEsame()
	{
		Esame esame = new Esame(codiceEsame, insegnamento, inf, crediti, voto, data);
		return esame;
	}
	
	public void serializza()
	{
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("inputCache.bin"))) {
			oos.writeObject(this);
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	public static InputCache deserializza()
	{
		InputCache cache;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("inputCache.bin"))) {
			cache = (InputCache)ois.readObject();
		} catch (FileNotFoundException ex) {
			return null;
		} catch (ClassNotFoundException | IOException ex) {
			System.err.println(ex.getMessage());
			return null;
		}
		return cache;
	}
}
