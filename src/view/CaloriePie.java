package view;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import controller.UIController;
import model.dataObjects.Nutrient;
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
     * The Uic.
     */
    UIController uic;

    /**
     * Instantiates a new Pie chart example.
     *
     * @param uic the uic
     * @param l1  the l 1
     * @param l2  the l 2
     */
    public CaloriePie(UIController uic, String l1, String l2){
        super("Nutrient");
        this.uic = uic;

        // Create dataset
        PieDataset dataset = createDataset(l1, l2);


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
    private PieDataset createDataset(String l1, String l2){
        int sum = 0;

        DefaultPieDataset dataset=new DefaultPieDataset();


        List<Nutrient> nutrients = uic.getXNutrients(10, LocalDate.parse(l1), LocalDate.parse(l2));

        int counter = 0;
        for (Nutrient n : nutrients) {


            if (n.getName() == "ENERGY (KILOJOULES)") {
                sum = (int) (sum + n.getAmount());
            }
            dataset.setValue(n.getName(), (int) n.getAmount());
        }

        return dataset;
    }

    /**
     * The entry point of application.
     *
     * @param uic the uic
     * @param l1  the l 1
     * @param l2  the l 2
     */
    public void start(UIController uic, String l1, String l2) {
        SwingUtilities.invokeLater(() -> {
            CaloriePie example = null;
            example = new CaloriePie(uic, l1, l2);
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
