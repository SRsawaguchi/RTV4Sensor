package ns.rtv4sensor;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main extends JFrame{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		JFrame mainFrame = new Main();
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(10, 10, 500, 500);
		mainFrame.setTitle("JFreeChart Test");
		mainFrame.setVisible(true);
	}

	Main(){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		JFreeChart chart =
				ChartFactory.createLineChart("ColorSensor Reflect",
											 "Reflect",
											 "ms",
											 dataset,
											 PlotOrientation.HORIZONTAL,
											 true,
											 false,
											 false);
		ChartPanel cPanel = new ChartPanel(chart);
		this.getContentPane().add(cPanel, BorderLayout.CENTER);
	}
}
