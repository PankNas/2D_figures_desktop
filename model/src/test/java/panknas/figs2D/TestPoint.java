package panknas.figs2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPoint {
    private Point point1;
    private Point point2;

    @BeforeEach
    public void createPoints() throws Exception {
        this.point1 = new Point(3, new double[]{1.0, 2.0, -5.0});
        this.point2 = new Point(3, new double[]{2, 1, 0});
    }

    @Test
    public void pointShouldHaveDimensions() {
        assertEquals(point1.getDim(), 3);
    }

    @Test
    public void pointShouldHaveProvidedCoordinates() {
        assertArrayEquals(point1.getX(), new double[] {1d, 2d, -5d});
    }

    @Test
    public void shouldCreateZeroedByDefault() throws Exception {
        assertArrayEquals(new Point(2).getX(), new double[]{0, 0});
    }

    @Test
    public void shouldComputeAbs(){
        assertEquals(point1.abs(), 5.4772, 1e-4);
    }

    @Test
    public void shouldAdd() throws Exception {
        assertArrayEquals(point1.add(point2).getX(), new double[]{3, 3, -5});
    }

    @Test
    public void shouldMultiply() throws Exception {
        assertArrayEquals(point1.mult(2).getX(), new double[]{2, 4, -10});
    }

    @Test
    public void shouldMultiplyWithPoint(){
        assertEquals(point1.mult(point2), 4, 1e-4);
    }
}
