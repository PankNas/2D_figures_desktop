package panknas.figs2D;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Circle implements IShape{
    @Getter
    private final String type;

    @Getter
    @Setter
    private Point2D p;

    @Getter
    @Setter
    private double r;

    public Circle(Point2D p, double r) throws Exception {
        this.p = p;
        if (r > 0)
            this.r = r;
        else throw new Exception("Incorrect r in Circle");
        type = String.valueOf(this.getClass());
    }

    @Override
    public IMoveable shift(Point2D a) throws Exception {
        p.setX(Point.add(p, a).getX());
        return this;
    }

    @Override
    public IMoveable rot(double phi) throws Exception {
        p = Point2D.rot(p, phi);
        return this;
    }

    @Override
    public IMoveable symAxis(int i) throws Exception {
        Circle circle = this;
        Point.symAxis(circle.getP(), i);
        return circle;
    }

    @Override
    public double square() {
        return Math.PI * Math.pow(r, 2);
    }

    @Override
    public double length() {
        return 2 * Math.PI * r;
    }

    @Override
    public boolean cross(IShape i) {
        double dis = new Segment(p, ((Circle) i).p).length();
        return dis >= Math.abs(r - ((Circle) i).r) &&
                dis <= r + ((Circle) i).r;
    }

    @Override
    public String getNameFig(int i) {
        return (i == 0) ? "Circle" : "Окружность";
    }

    @Override
    public Segment[] intoSegments() {
        return new Segment[0];
    }
}
