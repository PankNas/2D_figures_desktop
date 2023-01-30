package panknas.figs2D;

public interface IShape extends IMoveable {
    double square() throws Exception; // площадь
    double length() throws Exception; // периметр/длина
    boolean cross(IShape i) throws Exception; // пересечение фигур

    String getNameFig(int i);
    Segment[] intoSegments();
}
