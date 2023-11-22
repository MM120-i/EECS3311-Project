package gui;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import controller.UIController;
import dataObjects.Nutrient;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * The type Pie chart example.
 */
public class CaloriePie extends JFrame {


    /**
     * Instantiates a new Pie chart example.
     *
     * @param title the title
     */
    public CaloriePie(String title){
        super(title);

        // Create dataset
        PieDataset dataset = createDataset();


        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Your Diet",
                dataset,
                true,
                true,
                false);
        TextTitle legendText = new TextTitle("This is LEGEND: ");
        legendText.setPosition(RectangleEdge.BOTTOM);
        chart.addSubtitle(legendText);

        //Format Label
        PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                " {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);

        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);

        //Format Label
        PieSectionLabelGenerator labelGenerator2 = new StandardPieSectionLabelGenerator(
                " {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);

        // Create Panel
        ChartPanel panel2 = new ChartPanel(chart);
        setContentPane(panel);
    }

    /**
     * Creates a PieDataset for the chart by retrieving nutrient information from the database.
     *
     * @return A PieDataset containing nutrient names and their corresponding amounts.
     */
    private PieDataset createDataset(){
        int sum = 0;

        DefaultPieDataset dataset=new DefaultPieDataset();
        UIController uic = new UIController();

        List<Nutrient> nutrients = uic.getXNutrients(10);
        for (Nutrient n : nutrients) {

            if (n.getName() == "ENERGY (KILOJOULES)") {
                sum = (int) (sum + n.getAmount());
            }
            System.out.println(n.getName());
            System.out.println(n.getAmount());
            dataset.setValue(n.getName(), n.getAmount());
        }

        dataset.setValue("Other", uic.getRemainingNutrients(10));
        return dataset;
    }
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public void start() {
        SwingUtilities.invokeLater(() -> {
            CaloriePie example = null;
            example = new CaloriePie("Nutrients");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
