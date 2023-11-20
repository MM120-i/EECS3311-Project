package gui;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
public class Case4 extends JFrame {

    int sum = 0;

    /**
     * Instantiates a new Pie chart example.
     *
     * @param title the title
     */
    public Case4(String title, LocalDate l1, LocalDate l2){
        super(title);
        // Create dataset
        PieDataset dataset = createDataset(l1, l2);


        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Your Diet",
                dataset,
                true,
                true,
                false);
        TextTitle legendText = new TextTitle("Burned: " + sum);
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
    private PieDataset createDataset(LocalDate l1, LocalDate l2){

        DefaultPieDataset dataset=new DefaultPieDataset();
        UIController uic = new UIController();

        sum = uic.getCaloriesConsumed(10, l1, l2);


        System.out.println(sum);

        dataset.setValue("Burned", sum);
        return dataset;
    }
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public void start(LocalDate l1, LocalDate l2) {
        SwingUtilities.invokeLater(() -> {
            Case4 example = null;
            example = new Case4("Nutrients", l1, l2);
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
