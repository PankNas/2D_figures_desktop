package panknas.figs2D;

public class Rectangle extends NGon{
    public Rectangle(Point2D[] p) throws Exception {
        super(p, String.valueOf(Rectangle.class));

        Segment a, b;
        for (int i = 0; i < p.length - 2; i++) {
            if (Scalar(i, (i + 1), (i + 2)) != 0)
                throw new Exception("Incorrect Rectangle");
        }
        if (Scalar(0, (p.length - 1), (p.length - 2)) != 0)
            throw new Exception("Incorrect Rectangle");
    }

    private double Scalar(int one, int two, int three) throws Exception {
        Segment a = new Segment(getP(one), getP(two));
        Segment b = new Segment(getP(two), getP(three));
        return Point.mult(Point.sub(a.getFinish(), a.getStart()), Point.sub(b.getFinish(), b.getStart()));
    }

    public double square() throws Exception {
        Segment a = new Segment(getP(0), getP(1));
        Segment b = new Segment(getP(0), getP(3));
        return a.length() * b.length();
    }

    public String getNameFig(int i) {
        return (i == 0) ? "Rectangle" : "Прямоугольник";
    }
}
