package unilib;

import javafx.collections.ObservableList;

public class Studente
{
	public final int matricola;
	public final String nome;
	public final int votoTesi;
	public ObservableList<Esame> libretto;
	private Statistiche statistiche;
	
	public Studente(int matricola, String nome, int votoTesi)
	{
		this.matricola = matricola;
		this.nome = nome;
		this.votoTesi = votoTesi;
		
	}
	
	public Statistiche ottieniStatistiche()
	{
		if (statistiche == null)
			inizializzaStatistiche();
		return statistiche;
	}
	
	public final ObservableList<Esame> caricaEsami()
	{
		libretto = ArchivioEsami.ottieniIstanza().ottieniEsami(matricola);
		return libretto;
	}
	
	public void aggiungiEsame(Esame esame)
	{
		ArchivioEsami.ottieniIstanza().aggiungiEsame(matricola, esame);
		ottieniStatistiche().aggiungiEsame(esame.getVotoNumerico(),
			esame.isLode(), esame.getCrediti(), esame.isInf());
	}
	
	public void modificaEsame(Esame vecchioEsame, Esame nuovoEsame) // 01
	{
		ArchivioEsami.ottieniIstanza().modificaEsame(matricola,
			vecchioEsame.getCodiceEsame(), nuovoEsame);
		ottieniStatistiche().rimuoviEsame(vecchioEsame.getVotoNumerico(),
			vecchioEsame.isLode(), vecchioEsame.getCrediti(),
			vecchioEsame.isInf());
		ottieniStatistiche().aggiungiEsame(nuovoEsame.getVotoNumerico(),
			nuovoEsame.isLode(), nuovoEsame.getCrediti(),
			nuovoEsame.isInf());
	}
	
	public void eliminaEsame(Esame esame)
	{
		ArchivioEsami.ottieniIstanza().eliminaEsame(matricola, esame);
		ottieniStatistiche().rimuoviEsame(esame.getVotoNumerico(),
			esame.isLode(), esame.getCrediti(), esame.isInf());
	}
	
	private void inizializzaStatistiche()
	{
		if (statistiche == null)
			statistiche = new Statistiche();
		if (libretto == null)
			return;
		libretto.stream().forEach((esame) -> {
			statistiche.aggiungiEsame(esame.getVotoNumerico(),
				esame.isLode(), esame.getCrediti(), esame.isInf());
		});
	}
}

/* COMMENTI:
 * (01) A fini dell'aggiornamento delle Statistiche, la modifica di un Esame
 * viene vista come rimozione dell'esame pre-modifica e successiva aggiunta
 * dell'esame post-modifica.
 */
