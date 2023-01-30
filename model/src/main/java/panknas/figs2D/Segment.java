package panknas.figs2D;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// отрезок
@Getter
@Setter
@ToString
public class Segment extends OpenFigure {
    private String type;
    private Point2D start;
    private Point2D finish;

    public Segment(Point2D s, Point2D f) {
        start = s;
        finish = f;
        type = "Segment";
    }

    public Segment(Point2D[] p) {
        start = p[0];
        finish = p[1];
        type = String.valueOf(this.getClass());
    }

    @Override
    public double length() {
        double len = 0;
        len += Math.pow(start.getX(0) - finish.getX(0), 2);
        len += Math.pow(start.getX(1) - finish.getX(1), 2);
        return Math.sqrt(len);
    }

    private boolean cross_segments(Point2D a1, Point2D a2, Point2D b1, Point2D b2) {
        double v1, v2, v3, v4;
        double ax1 = a1.getX(0), ax2 = a2.getX(0), ay1 = a1.getX(1), ay2 = a2.getX(1);
        double bx1 = b1.getX(0), bx2 = b2.getX(0), by1 = b1.getX(1), by2 = b2.getX(1);

        v1 = (bx2 - bx1) * (ay1 - by1) - (by2 - by1) * (ax1 - bx1);
        v2 = (bx2 - bx1) * (ay2 - by1) - (by2 - by1) * (ax2 - bx1);
        v3 = (ax2 - ax1) * (by1 - ay1) - (ay2 - ay1) * (bx1 - ax1);
        v4 = (ax2 - ax1) * (by2 - ay1) - (ay2 - ay1) * (bx2 - ax1);

        return (v1 * v2 < 0) && (v3 * v4 < 0);
    }

    @Override
    public boolean cross(IShape i) {
        return cross_segments(start, finish, ((Segment) i).getStart(), ((Segment) i).getFinish());
    }

    @Override
    public IMoveable shift(Point2D a) throws Exception {
        start.setX(Point.add(start, a).getX());
        finish.setX(Point.add(finish, a).getX());
        return this;
    }

    @Override
    public IMoveable rot(double phi) throws Exception {
        start = Point2D.rot(start, phi);
        finish = Point2D.rot(finish, phi);
        return this;
    }

    @Override
    public IMoveable symAxis(int i) throws Exception {
        Segment segment = this;
        Point.symAxis(segment.getStart(), i);
        Point.symAxis(segment.getFinish(), i);
        return segment;
    }

//    public String toString() {
//        String str = "Segment ";
//        str += "[" + start.toString() + "; " + finish.toString() + "]\n";
//        return str;
//    }

    public String getNameFig(int i) {
        return (i == 0) ? "Segment" : "Отрезок";
    }

    public Segment[] intoSegments() {
        return new Segment[]{this};
    }
}
