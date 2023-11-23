package view;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import controller.UIController;
import model.dataObjects.Exercise;
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
 * The ExercisePie class extends JFrame and represents a pie chart displaying calories burnt through exercises.
 */
public class ExercisePie extends JFrame {

    /**
     * The total sum of calories burnt through exercises.
     */
    int sum = 0;
    /**
     * The total number of days for which exercises are considered.
     */
    long total;
    /**
     * The total calories burnt over time.
     */
    int totalCals = 0;

    /**
     * Instantiates a new ExercisePie chart.
     *
     * @param title The title of the chart.
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
        DefaultPieDataset dataset=new DefaultPieDataset();
        UIController uic = new UIController();


        List<Exercise> exercises = uic.getExercises();

        for (int i = 0; i < exercises.size(); i++) {
            dataset.setValue(exercises.get(i).getType(), exercises.get(i).getCalBurned());
            sum += exercises.get(i).getCalBurned();
        }

        sum = uic.getCalsBurned(LocalDate.of(2023, 01, 10), LocalDate.of(2023,9, 10));
        totalCals = (int) uic.getRegularBurnOverTime(exercises);

        return dataset;
    }

    /**
     * The main method, the entry point of the ExercisePie application.
     *
     * @param args The input arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExercisePie example = null;
            example = new ExercisePie("Exercise");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
