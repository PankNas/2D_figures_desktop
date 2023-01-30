package panknas.figs2D;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Point {
    private int dim; // размерность пространства
    private double[] x; // массив координат раззмерности

    public Point(int dim) throws Exception {
        if (dim == 2 || dim == 3) {
            this.dim = dim;
            x = new double[dim];
        }
        else throw new Exception("Incorrect dim in " + getClass().toString());
    }

    public Point(int dim, double[] x) throws Exception {
        if (dim == 2 || dim == 3) {
            this.dim = dim;
            if (x.length == dim)
                this.x = x;
            else throw new Exception("Incorrect size array in " + getClass().toString());
        }
        else throw new Exception("Incorrect dim in " + getClass().toString());
    }

    public double getX(int i) {
        return x[i];
    }

    public void setX(double x, int i) {
        this.x[i] = x;
    }

    // расстояние от точки до начала координат
    double abs() {
        double len = 0;
        for (int i = 0; i < dim; i++)
            len += Math.abs(Math.pow(x[i], 2));
        return Math.sqrt(len);
    }

    // сложение двух точек
    public static Point add(Point a, Point b) throws Exception {
        return a.add(b);
    }

    public Point add(Point b) throws Exception {
        Point new_point = new Point(this.getDim());
        for (int i = 0; i < this.getDim(); i++)
            new_point.setX(this.getX(i) + b.getX(i), i);
        return new_point;
    }

    // находит разность по координатам двух точек
    public static Point sub(Point a, Point b) throws Exception {
        return a.sub(b);
    }

    public Point sub(Point b) throws Exception {
        Point new_point = new Point(this.getDim());
        for (int i = 0; i < this.getDim(); i++) {
            double x = this.getX(i) - b.getX(i);
            new_point.setX(x, i);
        }
        return new_point;
    }

    // умножение точки на дробное число – произведение по координатам
    public static Point mult(Point a, double r) throws Exception {
        return a.mult(r);
    }

    public Point mult(double r) throws Exception {
        Point new_point = new Point(this.getDim());
        for (int i = 0; i < this.getDim(); i++)
            new_point.setX((this.getX(i) * r), i);
        return new_point;
    }

    // умножение точки на точку - скалярное произведение
    public static double mult(Point a, Point b) {
        return a.mult(b);
    }

    public double mult(Point b) {
        double scalar = 0;
        for (int i = 0; i < this.getDim(); i++)
            scalar += this.getX(i) * b.getX(i);
        return scalar;
    }

    // симметрия точки относительно оси под заданным номером
    //(0 – Ox, 1 – Oy, 2 – Oz, …)
    public static Point symAxis(Point a, int i) throws Exception {
        return a.symAxis(i);
    }

    public Point symAxis(int i) throws Exception {
        Point new_p = this;
        switch (i) {
            case 0 -> new_p.setX((getX(1) * -1), 1);
            case 1 -> new_p.setX((getX(0) * -1), 0);
            default -> throw new Exception("Incorrect type move");
        }
        return new_p;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("( ");
        for (int i = 0; i < dim; i++) {
            str.append(getX(i)).append(" ");
        }
        str.append(")");
        return str.toString();
    }
}
