package panknas.figs2D;

public class QGon extends NGon {
    public QGon(Point2D[] p) throws Exception {
        super(p, String.valueOf(QGon.class));
        if (p.length != 4)
            throw new Exception("Incorrect size array in " + getClass().toString());
    }

    public double square() throws Exception {
        return super.square();
    }

    public String getNameFig(int i) {
        return (i == 0) ? "QGon" : "Четырёхугольник";
    }
}
