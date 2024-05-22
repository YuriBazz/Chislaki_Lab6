package diffCalc;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class Application {
    public static void run(){
        int N = 20; //в джаве ультра неудобное считывание с консоли, поэтому N изменяется тут
        var shooting = new Shooting(N, 0, 1);
        var running = new Running(N, 0,1);
        var field = new XYSeriesCollection(shooting.getSeries());
        field.addSeries(running.getSeries());
        var chart =
                ChartFactory.
                        createXYLineChart
                                ("y'' = y+2a+2+ax(1-x): y(0) = 0, y(1) = e + 1/e - 2", "", "",
                                        field,
                                        PlotOrientation.VERTICAL,
                                        true,true,true);

        JFrame frame = new JFrame("Краевая задача");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(900,800);
        frame.setVisible(true);
    }
}
