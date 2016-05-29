package ns.rtv4sensor;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnit;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main extends JFrame{

	private static final long serialVersionUID = 1L;
	JFreeChart chart;
	ChartPanel cPanel;
	XYSeriesCollection dataset;

	public static void main(String[] args) {
		Main mainFrame = new Main();
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(10, 10, 1200, 500);
		mainFrame.setTitle("JFreeChart Test");	
		
		mainFrame.loop();
	}
	
	private void loop(){
		dataset = new XYSeriesCollection();
		refreshChart();
		ChartPanel cPanel = new ChartPanel(chart);
		XYSeries series = new XYSeries("reflect");
		dataset.addSeries(series);
		this.getContentPane().add(cPanel, BorderLayout.CENTER);	
		this.setVisible(true);
		for(int i=0;i<100;i++){
			double r = Math.floor(Math.random()*10);
//			String l = (i%5)==0 ? String.valueOf(i) : "";
			series.add(i*100,r);
			
			refreshChart();
			this.getContentPane().remove(cPanel);
			cPanel = new ChartPanel(chart);
			this.getContentPane().add(cPanel, BorderLayout.CENTER);
			this.repaint();
			this.setVisible(true);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//end for
	}
	
	private void refreshChart(){
		chart = ChartFactory.createXYLineChart("ColorSensor reflect", "ms", "reflect", dataset);
		XYPlot plot = chart.getXYPlot();
		NumberAxis axis = (NumberAxis)plot.getRangeAxis();
		axis.setLowerBound(0);
		axis.setUpperBound(10);
		
		ValueAxis xAxis = plot.getDomainAxis();
		TickUnits xUnits = new TickUnits();
		TickUnit xUnit = new NumberTickUnit(500);
		xUnits.add(xUnit);
		xAxis.setStandardTickUnits(xUnits);
		
		
		
	}

}
