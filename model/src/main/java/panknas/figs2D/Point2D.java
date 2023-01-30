package panknas.figs2D;

public class Point2D extends Point {
    public Point2D() throws Exception {
        super(2);
    }

    public Point2D(double[] x) throws Exception {
        super(2, x);
    }

    // rot – функция поворота точки на угол phi в радианах относительно
    // начала координат. Положительное направление – против часовой стрелки.
    public static Point2D rot(Point2D p, double phi) throws Exception {
        return p.rot(phi);
    }

    public Point2D rot(double phi) throws Exception {
        // X = x * cos(phi) - y * sin(phi)
        // Y = x * sin(phi) + y * cos(phi)
        Point p1 = mult(this, Math.cos(phi)); // умножение на cos(phi)
        Point p2 = mult(this, Math.sin(phi)); // умножение на sin(phi)
        double X = p1.getX(0) - p2.getX(1);
        double Y = p2.getX(0) + p1.getX(1);
        return new Point2D(new double[]{X, Y});
    }
}
