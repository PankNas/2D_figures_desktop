package panknas.figs2D;

public class Point3D extends Point{
    public Point3D() throws Exception {
        super(3);
    }

    public Point3D(double[] x) throws Exception {
        super(3, x);
    }

    // векторное произведение двух точек
    public static Point3D cross_prod(Point3D p1, Point3D p2) throws Exception {
        return p1.cross_prod(p2);
    }

    public Point3D cross_prod(Point3D p) throws Exception {
        // -a x -b = {ay*bz - az*by; az*bx - ax*bz; ax*by - ay*bx}
        double x = p.getX(2) * this.getX(1) - p.getX(1) * this.getX(2);
        double y = p.getX(0) * this.getX(2) - p.getX(2) * this.getX(0);
        double z = p.getX(1) * this.getX(0) - p.getX(0) * this.getX(1);
        return new Point3D(new double[] {x, y, z});
    }

    // смешанное произведение двух точек
    public static double mix_prod(Point3D p1, Point3D p2, Point3D p3) throws Exception {
        return p1.mix_prod(p2, p3);
    }

    public double mix_prod(Point3D p1, Point3D p2) throws Exception {
        // смешанное произведение - this * (-p1 x -p2)
        Point3D p = p1.cross_prod(p2); // векторное произведение
        return this.mult(p); // скалярное произведение
    }
}
