package ns.rtv4sensor;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;

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

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

public class Main extends JFrame{
	private static final String COM_PORT = "COM13";

	private static final long serialVersionUID = 1L;
	JFreeChart chart;
	ChartPanel cPanel;
	XYSeriesCollection dataset;
	XYSeries dataSeries;

	Main(){
		dataSeries = new XYSeries("reflect");
		dataset = new XYSeriesCollection(dataSeries);
		this.onDatasetChanged();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Main mainRoutine = new Main();
		mainRoutine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainRoutine.setBounds(10, 10, 1200, 500);
		mainRoutine.setTitle("JFreeChart Test");
		mainRoutine.loop();
	}

	private void onDatasetChanged(){
		refreshChart();
	}

	private void loop(){
		CommPortIdentifier comID = null;
		try {
			comID = CommPortIdentifier.getPortIdentifier(COM_PORT);
		} catch (NoSuchPortException e) {
			System.out.println("そのようなポートはありません。");
			e.printStackTrace();
			return;
		}

		SerialPort port  = null;
		try {
			port = (SerialPort)comID.open("EV3Naoya", 2000);
		} catch (PortInUseException e) {
			e.printStackTrace();
			return;
		}

		try {
			port.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			port.setDTR(true);
			port.setRTS(false);
		} catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
			return;
		}

        InputStream in = null;
		try {
			in = port.getInputStream();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return;
		}

		ChartPanel cPanel = new ChartPanel(chart);
        int cnt = 0;
        int reflect = 0;
        while (true) {
			try {
				char ch = (char)in.read();
				if(ch == ','){
					dataSeries.add(cnt++,reflect);
					System.out.println(reflect);
					reflect = 0;
					refreshChart();
					this.getContentPane().remove(cPanel);
					cPanel = new ChartPanel(chart);
					this.getContentPane().add(cPanel, BorderLayout.CENTER);
					this.repaint();
					this.setVisible(true);

				}else{
					if (Character.isDigit(ch)) {
						reflect = (reflect * 10) + (int) (ch^0x30);
					}
				}

			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				return;
			}
        }//end while
	}

	private void refreshChart(){
		chart = ChartFactory.createXYLineChart("ColorSensor reflect", "ms", "reflect", dataset);
		XYPlot plot = chart.getXYPlot();
		NumberAxis axis = (NumberAxis)plot.getRangeAxis();
		axis.setLowerBound(0);
		axis.setUpperBound(100);

		ValueAxis xAxis = plot.getDomainAxis();
		TickUnits xUnits = new TickUnits();
		TickUnit xUnit = new NumberTickUnit(50);
		xUnits.add(xUnit);
		xAxis.setStandardTickUnits(xUnits);
	}
}
