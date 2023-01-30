package panknas.figs2D;

// треугольник
public class TGon extends NGon {
    public TGon(Point2D[] p) throws Exception {
        super(p, String.valueOf(TGon.class));
        if (p.length != 3)
            throw new Exception("Incorrect size array in TGon");
    }

    public double square() throws Exception {
        // S = 0.5 * |-a x -b|
        Point help;
        help = Point.sub(getP(1), getP(0));
        Point3D p1 = new Point3D(new double[] {help.getX(0), help.getX(1), 0});
        help = Point.sub(getP(2), getP(0));
        Point3D p2 = new Point3D(new double[] {help.getX(0), help.getX(1), 0});

        Point3D temp = Point3D.cross_prod(p1, p2); // векторное произведение
        double scalar = Math.abs(Point.mult(temp, temp)); // модуль вектора ^ 2
        return 0.5 * Math.sqrt(scalar);
    }

    public String getNameFig(int i) {
        return (i == 0) ? "TGon" : "Треугольник";
    }
}
