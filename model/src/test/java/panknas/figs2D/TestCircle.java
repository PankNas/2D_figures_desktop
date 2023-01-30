package panknas.figs2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCircle {
    private static Circle circle = null;

    static {
        try {
            circle = new Circle(new Point2D(new double[] {0, 1}), 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void area() { assertEquals(Math.PI*3*3, circle.square(), 1e-6); }

    @Test
    void length() { assertEquals(Math.PI*2*3, circle.length(), 1e-6);}

    @Test
    void cross() throws Exception {
        assertFalse(circle.cross(new Circle(new Point2D(), 5)));
        assertTrue(circle.cross(new Circle(new Point2D(), 4)));
    }

    @Test
    void symAxis() throws Exception {
        circle.symAxis(0);
        assertArrayEquals(new double[] {0, -1}, circle.getP().getX());
    }

}
