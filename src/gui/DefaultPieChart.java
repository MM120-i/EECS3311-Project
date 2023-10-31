package gui;

import controller.DBAccess;
import dataObjects.Nutrient;
import dataObjects.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * The type Pie chart example.
 */
public class DefaultPieChart extends JFrame {


    /**
     * Instantiates a new Pie chart example.
     *
     * @param title the title
     */
    public DefaultPieChart(String title, int days){
        super(title);

        // Create dataset
        PieDataset dataset = createDataset(days);


        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Balanced Diet for an Average Adult",
                dataset,
                true,
                true,
                false);

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
    private PieDataset createDataset(int days){

       DefaultPieDataset dataset=new DefaultPieDataset();
       dataset.setValue("ENERGY (KILOJOULES)", 8700);
       dataset.setValue("PROTEIN", 50);
       dataset.setValue("FAT", 70);
       dataset.setValue("FATTY ACIDS", 24);
       dataset.setValue("CARBOHYDRATES", 310);
       dataset.setValue("SUGARS", 90);
       dataset.setValue("SODIUM", 2.3);
       dataset.setValue("DIETARY FIBRE", 30);
        return dataset;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public void start(int days) {
        SwingUtilities.invokeLater(() -> {
            DefaultPieChart example = null;
            example = new DefaultPieChart("Nutrients2", days);
            example.setSize(800, 400);
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            example.setLocation((int)d.getWidth()/4 - (int)example.getPreferredSize().getWidth()/3,
                    (int)d.getHeight()/4 - (int)example.getPreferredSize().getHeight()/3);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
