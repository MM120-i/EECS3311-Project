package gui;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import controller.DBAccess;
import dataObjects.Exercise;
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
public class ExercisePie extends JFrame {

    int sum = 0;
    long total;
    int totalCals = 0;
    /**
     * Instantiates a new Pie chart example.
     *
     * @param title the title
     */
    public ExercisePie(String title){
        super(title);

        // Create dataset
        PieDataset dataset = createDataset();


        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Calories Burnt",
                dataset,
                true,
                true,
                false);
        TextTitle legendText = new TextTitle("Total Calories Burnt: " + sum + "TOTAL OVER TIME: " + totalCals);
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
        List<Exercise> exercises = da.findBetween(new User("Testing12223", 1, LocalDate.of(2003,05,28), 150,  100, 0, 0), LocalDate.of(2021, 05, 28), LocalDate.of(2023, 11,20), new Exercise());
        DefaultPieDataset dataset=new DefaultPieDataset();
        int counter = 0;
        for (int i = 0; i < exercises.size(); i++) {
            dataset.setValue(exercises.get(i).getType(), exercises.get(i).getCalBurned());
        }

        total = LocalDate.of(2021, 5, 28).until(LocalDate.of(2023, 11,20), ChronoUnit.DAYS);
        System.out.println(total);
        totalCals = (int) (total * 2000);
        System.out.println(totalCals);
        for (int i = 0; i < exercises.size(); i++) {
            sum += exercises.get(i).getCalBurned();
            System.out.println(sum);
        }
        return dataset;
    }
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExercisePie example = null;
            example = new ExercisePie("Nutrients");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
