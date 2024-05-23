package diffCalc;

import org.jfree.data.xy.XYSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Running implements IGetSeries {
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
        double[] up = new double[N-1], main = new double[N-1], down = new double[N-1], left = new double[N-1];
        double x = a + h;
        for(int i = 0; i < N - 1 ; i++){
            if(i == N - 2) up[i] = 0; else up[i] = 1;
            main[i] = -(2 + h * h);
            if(i == 0) down[i] = 0; else down[i] = 1;
            left[i] = (q(x) * h * h);
            x+=h;
        }
        double[] solution = new double[N-1];
        double m;
        for(int i = 1; i < N - 1; i++){
            m = down[i] / main[i-1];
            main[i] -= m * up[i-1];
            left[i] -= m * left[i-1];
        }

        solution[N-2] = left[N-2] / main[N-2];
        for(int i = N- 3; i > -1; i--){
            solution[i] = (left[i] -up[i] * solution[i+1])/ main[i];
        }
        XYSeries result = new XYSeries("Метод прогонки", false);
        x = a;
        result.add(x, 0);
        x+=h;
        for(int i = 0; i < N - 1; i++){
            result.add(x, solution[i]);
            x+=h;
        }
        result.add(x, Math.E + 1/Math.E - 2);
        return result;
    }

}
