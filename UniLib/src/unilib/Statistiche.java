package unilib;

import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class Statistiche
{
	private int sommaVoti = 0;
	private int sommaVotiInf = 0;
	private int crediti = 0;
	private int creditiInf = 0;
	private final int creditiTotali;
	private final int valoreLode;
	private final int votoTesi;
	
	public Statistiche()
	{
		ParametriDiConfigurazione pdc = ParametriDiConfigurazione.ottieniConfigurazione();
		creditiTotali = pdc.corsoDiLaurea.creditiTotali;
		valoreLode = pdc.corsoDiLaurea.valoreLode;
		votoTesi = pdc.studente.votoTesi;
	}
	
	public int creditiRimanenti()
	{
		return creditiTotali - crediti;
	}
	
	public double media()
	{
		if (crediti == 0)
			return Double.NaN;
		return (double) sommaVoti / (double) crediti;
	}
	
	public int votoLaurea()
	{
		if (crediti == 0)
			return -1;
		return (int) Math.round(media()) * 3 + 18 + votoDiCommissione() + votoTesi;
	}
	
	public int votoLaureaMinimo()
	{
		return (int) Math.round(previsioneMedia(18) * 3 + 18 + 1 + votoTesi);
	}
	
	public int votoLaureaMassimo()
	{
		return (int) Math.round(previsioneMedia(valoreLode) * 3 + 18 + 7 + votoTesi);
	}
	
	public ObservableList<Series<String, Number>> mediaEsami() // 01
	{
		HashMap<String, Number> mediaEsami = ArchivioEsami.ottieniIstanza().ottieniMediaEsami();
		ObservableList<Series<String, Number>> listaMedie = FXCollections.observableArrayList();
		XYChart.Series serieEsami = new XYChart.Series();
		mediaEsami.entrySet().stream().forEach((mediaEsame) -> {
			String insegnamento = mediaEsame.getKey();
			Number media = mediaEsame.getValue();
			serieEsami.getData().add(new XYChart.Data(insegnamento, media));
		});
		listaMedie.add(serieEsami);
		return listaMedie;
	}
	
	public void aggiungiEsame(int voto, boolean lode, int crediti, boolean inf)
	{
		sommaVoti += (lode ? valoreLode : voto) * crediti;
		this.crediti += crediti;
		if (inf) {
			sommaVotiInf += (lode ? valoreLode : voto) * crediti;
			creditiInf += crediti;
		}
	}
	
	public void rimuoviEsame(int voto, boolean lode, int crediti, boolean inf)
	{
		sommaVoti -= (lode ? valoreLode : voto) * crediti;
		this.crediti -= crediti;
		if (inf) {
			sommaVotiInf -= (lode ? valoreLode : voto) * crediti;
			creditiInf -= crediti;
		}
	}
	
	private double previsioneMedia(int votoEsamiFuturi)
	{
		return (double) (sommaVoti + votoEsamiFuturi * creditiRimanenti()) / (double) creditiTotali;
	}
	
	private int votoDiCommissione()
	{
		if (creditiInf == 0)
			return 1;
		int vc = (int) Math.min(Math.round((((double) sommaVotiInf / (double) creditiInf) - 18) * 7 / 12), 7);
		return vc > 0 ? vc : 1;
	}
}

/* COMMENTI:
 * (01) Ritorna una lista osservabile che per ogni esame contiene la media dei
 * voti ottenuti da tutti gli studenti. L'oggetto ritornato può essere passato
 * direttamente a GraficoVoti.
 */