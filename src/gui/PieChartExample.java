package gui;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import controller.DBAccess;
import dataObjects.Nutrient;
import dataObjects.User;
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
public class PieChartExample extends JFrame {


    /**
     * Instantiates a new Pie chart example.
     *
     * @param title the title
     */
    public PieChartExample(String title){
        super(title);

        // Create dataset
        PieDataset dataset = createDataset();
        PieDataset dataset2 = createDefaultSet();


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

        DBAccess da = new DBAccess();
        List<Nutrient> nutrients = da.breakdownMeal(new User("Bob Smith", 1, LocalDate.of(2003,05,28), 150,  100, 0, 0), LocalDate.of(2023, 05, 28), LocalDate.of(2023, 9,20));
        DefaultPieDataset dataset=new DefaultPieDataset();
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            if (nutrients.get(i).getName().equals("ENERGY (KILOCALORIES)")) {
                nutrients.remove(i);
            }
            dataset.setValue(nutrients.get(i).getName(), nutrients.get(i).getAmount());
        }

        int sum = 0;
        for (int i = 10; i < nutrients.size(); i++) {
            sum += nutrients.get(i).getAmount();
        }
        dataset.setValue("Other", sum);
        return dataset;
    }

    private PieDataset createDefaultSet(){

        DBAccess da = new DBAccess();
        List<Nutrient> nutrients = da.breakdownMeal(new User("Bob Smith", 1, LocalDate.of(2003,05,28), 150,  100, 0, 0), LocalDate.of(2023, 05, 28), LocalDate.of(2023, 9,20));
        DefaultPieDataset dataset=new DefaultPieDataset();
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            if (nutrients.get(i).getName().equals("ENERGY (KILOCALORIES)")) {
                nutrients.remove(i);
            }
            dataset.setValue(nutrients.get(i).getName(), nutrients.get(i).getAmount());
        }

        int sum = 0;
        for (int i = 10; i < nutrients.size(); i++) {
            sum += nutrients.get(i).getAmount();
        }
        dataset.setValue("Other", sum);
        DefaultPieChart dpc = new DefaultPieChart("Nutrients", 50);
        dpc.start(50);
        return dataset;
    }
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PieChartExample example = null;
            example = new PieChartExample("Nutrients");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
