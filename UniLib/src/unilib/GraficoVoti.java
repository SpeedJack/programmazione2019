package unilib;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class GraficoVoti extends BarChart<String, Number>
{
	private GraficoVoti(CategoryAxis asseEsami, NumberAxis asseMedia)
	{
		super(asseEsami, asseMedia);
		asseEsami.setLabel("Esami");
		asseMedia.setLabel("Media");
		
		setLegendVisible(false);
	}
	
	public GraficoVoti()
	{
		this(new CategoryAxis(), new NumberAxis());
	}
}
