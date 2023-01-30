package panknas.figs2D;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.stream.IntStream;

// ломаная линия
@Getter
@Setter
@ToString
public class Polyline extends OpenFigure implements IPolyPoint {
    private String type;
    private int n;
    private Point2D[] p;

    public Polyline(Point2D[] p) throws Exception {
        if (p.length > 2) {
            n = p.length;
            this.p = p;
            type = String.valueOf(this.getClass());
        }
        else throw new Exception("Incorrect size array in Polyline");
    }

    @Override
    public Point2D getP(int i) {
        return p[i];
    }

    @Override
    public void setP(Point2D p, int i) {
        this.p[i] = p;
    }

    @Override
    public double length() throws Exception {
        double len = 0;
        for (int i = 0; i < n - 1; i++) {
            Point help = Point.sub(p[i + 1], p[i]);
            len += Math.sqrt(Math.pow(help.getX(0), 2) + Math.pow(help.getX(1), 2));
        }
        return len;
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
        for (int j = 0; j < n - 1; j++) {
            for (int k = 0; k < ((Polyline) i).getN() - 1; k++)
                if (cross_segments(p[j], p[j + 1], ((Polyline) i).getP(k), ((Polyline) i).getP((k + 1))))
                    return true;
        }
        return false;
    }

    @Override
    public String getNameFig(int i) {
        return (i == 0) ? "Polyline" : "Ломаная";
    }

    @Override
    public IMoveable shift(Point2D a) throws Exception {
        for (int i = 0; i < n; i++) {
            p[i].setX(Point.add(p[i], a).getX());
        }
        return this;
    }

    @Override
    public IMoveable rot(double phi) throws Exception {
        for (int i = 0; i < n; i++) {
            p[i] = Point2D.rot(p[i], phi);
        }
        return this;
    }

    @Override
    public IMoveable symAxis(int i) throws Exception {
        Polyline new_p = this;
        for (int j = 0; j < n; j++) {
            Point.symAxis(new_p.getP(j), i);
        }
        return new_p;
    }

//    public String toString() {
//        StringBuilder str = new StringBuilder(getNameFig(0) + " [");
//        for (int i = 0; i < n - 1; i++) {
//            str.append(p[i].toString()).append("; ");
//        }
//        str.append(p[n - 1].toString()).append("]\n");
//        return str.toString();
//    }

    public Segment[] intoSegments() {
        return IntStream.range(0, this.getN()-1).mapToObj(
                idx -> new Segment(this.getP(idx), this.getP((idx+1)%this.getN()))
        ).toArray(Segment[]::new);
    }
}
