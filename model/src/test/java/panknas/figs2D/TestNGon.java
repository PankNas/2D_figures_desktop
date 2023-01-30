package panknas.figs2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestNGon {
    private static NGon ngon = null;

    static {
        try {
            ngon = new NGon(
                    new Point2D[]{
                            new Point2D(new double[]{-1.0, 4.0}),
                            new Point2D(new double[]{1.0, 10.0}),
                            new Point2D(new double[]{5.0, 9.0}),
                            new Point2D(new double[]{2.0, 0.0}),
                            new Point2D(new double[]{4.0, 0.0})
                    }, "NGon"

            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void area() throws Exception {
        assertEquals(ngon.square(), 28.5, 1e-6);
    }

    @Test
    void length() throws Exception {
        assertEquals(ngon.length(), 28.3376181, 1e-6);
    }

    @Test
    void cross() throws Exception {
        assertTrue(ngon.cross(new NGon(
                new Point2D[]{
                        new Point2D(new double[]{0.0, 3.0}),
                        new Point2D(new double[]{1.0, 5.0}),
                        new Point2D(new double[]{5.0, 9.0})
                }, "NGon")));

        assertFalse(ngon.cross(new NGon(
                new Point2D[]{
                        new Point2D(new double[]{0.0, 0.0}),
                        new Point2D(new double[]{0.0, 1.0}),
                        new Point2D(new double[]{1.0, 1.0})
                }, "NGon")));
    }

}
