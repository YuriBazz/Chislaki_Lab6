package diffCalc;

import org.jfree.data.xy.XYSeries;

import java.util.ArrayList;
import java.util.List;

public class Shooting {
    private static final double epsilon = 0.5 * Math.pow(10,-3);
    private final double alpha, h,a,b;
    private final int N;
    public Shooting(int N, double a, double b){
        this.alpha = 2 + 0.1 * N;
        this.a = a; this.b = b;
        this.h = (b - a) / N ;
        this.N = N;
    }
    private double f(double x, double y){
        return y + 2 * alpha + 2 + alpha * x * (1 - x);
    }

    private XYSeries Cauchy(double mu){
        XYSeries result = new XYSeries("Метод стрельбы", false);
        double w = mu;
        double x = a, y = 0;
        for(int i = 0; i < N + 1; i++){
            if(i == 0) {result.add(a,y); continue;}
            var oldY = y;
            y+= w * (h*h + 2 * h)/ 2;
            w+= h*f(x + h/2, oldY +f(x, oldY) * h/2);
            x+=h;
            result.add(x,y);
        }
        return result;
    }

    private XYSeries bisection(double left, double right){
        double middle = (left + right) / 2;
        XYSeries middleSeries = Cauchy(middle);
        if(Math.abs(F(middleSeries)) < epsilon) return middleSeries;
        if(Math.signum(F(Cauchy(left))) != Math.signum(F(middleSeries))) return bisection(left, middle);
        else return bisection(middle, right);
    }

    public XYSeries getSeries(){
        return bisection(-4,-2);
    }
    private double F(XYSeries temp){
        return (double) temp.getY(temp.getItemCount() - 1) + 2 -Math.E -1 / Math.E;
    }
}
