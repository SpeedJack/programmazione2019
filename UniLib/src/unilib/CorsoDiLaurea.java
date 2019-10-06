package unilib;

import java.io.Serializable;

public class CorsoDiLaurea implements Serializable
{
	public final String nome;
	public final int valoreLode;
	public final int creditiTotali;
	
	private CorsoDiLaurea()
	{
		this("???", 33, 180);
	}
	
	private CorsoDiLaurea(String nome, int valoreLode, int crediti)
	{
		this.nome = nome;
		this.valoreLode = valoreLode;
		creditiTotali = crediti;
	}
}
