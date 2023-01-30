package panknas.figs2D;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.stream.IntStream;

@ToString
public class NGon implements IPolyPoint, IShape {
    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private int n;

    @Getter
    @Setter
    private Point2D[] p;

    public NGon(Point2D[] p, String type) throws Exception {
        if (p.length > 2) {
            n = p.length;

            if (n == 3) {
                Segment t1, t2, t3;
                t1 = new Segment(p[0], p[1]);
                t2 = new Segment(p[1], p[2]);
                t3 = new Segment(p[0], p[2]);
                if (t1.length() + t2.length() < t3.length() ||
                    t1.length() + t3.length() < t2.length() ||
                    t2.length() + t3.length() < t1.length())
                    throw new Exception(getNameFig(0) + " not exist");
            }

            this.p = p;
            this.type = type;
        }
        else throw new Exception("Incorrect size array in NGon");
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
    public double square() throws Exception {
        double s = 0;
        for(int i = 0; i < n - 1; i++)
        {
            s += p[i].getX(0) * p[i + 1].getX(1);
            s -= p[i + 1].getX(0) * p[i].getX(1);
        }
        s += p[n - 1].getX(0) * p[0].getX(1);
        s -= p[0].getX(0) * p[n - 1].getX(1);
        s = Math.abs(s) / 2;
        return s;
    }

    @Override
    public double length() throws Exception {
        double len = 0;
        for (int i = 0; i < n - 1; i++) {
            Point help = Point.sub(p[i + 1], p[i]);
            len += Math.sqrt(Math.pow(help.getX(0), 2) + Math.pow(help.getX(1), 2));
        }
        Point help = Point.sub(p[n - 1], p[0]);
        len += Math.sqrt(Math.pow(help.getX(0), 2) + Math.pow(help.getX(1), 2));
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
            for (int k = 0; k < ((NGon) i).getN() - 1; k++)
                if (cross_segments(p[j], p[j + 1], ((NGon) i).p[k], ((NGon) i).p[k + 1]))
                    return true;
            if (cross_segments(p[j], p[j + 1], ((NGon) i).p[0], ((NGon) i).p[((NGon) i).getN() - 1]))
                return true;
        }
        return cross_segments(p[0], p[n - 1], ((NGon) i).p[0], ((NGon) i).p[((NGon) i).getN() - 1]);
    }

    @Override
    public NGon shift(Point2D a) throws Exception {
        for (int i = 0; i < n; i++) {
            p[i].setX(Point.add(p[i], a).getX());
        }
        return this;
    }

    @Override
    public NGon rot(double phi) throws Exception {
        for (int i = 0; i < n; i++) {
            p[i] = Point2D.rot(p[i], phi);
        }
        return this;
    }

    @Override
    public NGon symAxis(int i) throws Exception {
        for (int j = 0; j < n; j++) {
            Point.symAxis(p[j], i);
        }
        return this;
    }

    public String getNameFig(int i) {
        return (i == 0) ? "NGon" : "Многоугольник";
    }

//    public String toString() {
//        StringBuilder name = new StringBuilder();
//
//        name.append(this.getNameFig(0)).append(" {");
//        for (int i = 0; i < n - 1; i++) {
//            name.append(p[i].toString()).append("; ");
//        }
//        name.append(p[n - 1].toString()).append("}\n");
//        return name.toString();
//    }

    public Segment[] intoSegments() {
        return IntStream.range(0, this.getN()).mapToObj(
                idx -> new Segment(this.getP(idx), this.getP((idx+1) % this.getN()))
        ).toArray(Segment[]::new);
    }
}
