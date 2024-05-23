package diffCalc;

import org.jfree.data.xy.XYSeries;

public class Solution implements IGetSeries {
    private final double a,b,h, alpha;
    private final int N;

    public Solution(int N, double a, double b){
        this.alpha = 2 + 0.1 * N;
        this.a = a; this.b = b;
        this.h = (this.b - a) / N ;
        this.N = N;
    }

    private double exp(double x){
        return Math.pow(Math.E, x);
    }

    private double f(double x){
        return exp(x) + exp(-x) + alpha * x * (x - 1) - 2;
    }

    public XYSeries getSeries() {
        XYSeries result = new XYSeries("Точное решение", false);
        double x = a;
        for(int i = 0; i < N + 1; i++){
            result.add(x,f(x));
            x+=h;
        }
        return result;
    }
}
