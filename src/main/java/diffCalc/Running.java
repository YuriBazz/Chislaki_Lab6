package diffCalc;

import org.jfree.data.xy.XYSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Running {
    private final double a,b, alpha, h;
    private final int N;

    public Running(int N, double a, double b){
        this.a = a; this.b = b; this.N = N; this.h = (b - a) / N;
        this.alpha = 2 + 0.1 * N;
    }

    private double q(double x){
        return 2 * alpha + 2 + alpha * x * (1-x);
    }


    public XYSeries getSeries(){
        ArrayList<Double> v = new ArrayList<>(), u = new ArrayList<>();
        double y = Math.E + 1 / Math.E - 2, x = a;
        for(int i = 0; i < N + 1; i++){
            if(i == 0) {v.add(1/(2 + h * h)); u.add(q(x)*h*h); continue;}
            if(i == N) {v.add(0.0); u.add((u.getLast() - q(x)*h*h) / (2 + h* h -v.get(v.size() - 2))); continue;}
            u.add((u.getLast() - q(x)*h*h) / (2 + h* h + v.getLast()));
            v.add(1 / (2 + h*h -v.getLast()));
            x+=h;
        }
        XYSeries result = new XYSeries("Метод прогонки", false);
        for(int i = N; i >-h; i-=h){
            result.add(x, y);
            if(i == 0) break;
            y = v.getLast() * y + u.getLast();
            v.removeLast(); u.removeLast();
            x-=h;
        }
        return result;
    }

}
