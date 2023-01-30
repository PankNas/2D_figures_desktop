package panknas.figs2D;

public class Trapeze extends NGon{
    public Trapeze(Point2D[] p) throws Exception {
        super(p, String.valueOf(Trapeze.class));

        Point l, r;
        l = Point.sub(getP(0), getP(1));
        r = Point.sub(getP(2), getP(3));
        boolean flag1 = l.getX(1) / l.getX(0) == r.getX(1) / r.getX(0);

        l = Point.sub(getP(0), getP(3));
        r = Point.sub(getP(1), getP(2));
        boolean flag2 = l.getX(1) / l.getX(0) == r.getX(1) / r.getX(0);

        if ((flag1 && !flag2) || (!flag1 && flag2));
        else
            throw new Exception("Incorrect Trapeze");
    }

    public double square() throws Exception {
        return super.square();
    }

    public String getNameFig(int i) {
        return (i == 0) ? "Trapeze" : "Трапеция";
    }
}
