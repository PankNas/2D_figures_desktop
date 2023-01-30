package panknas.figs2D;

public interface IMoveable {
    IMoveable shift(Point2D a) throws Exception; // сдвиг фигуры на вектор
    IMoveable rot(double phi) throws Exception; // поворот фигуры на угол
    IMoveable symAxis(int i) throws Exception; // симметрия точки относительно оси
}
