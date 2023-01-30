package panknas.figs2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestQGon {
    private static QGon qgon = null;

    static {
        try {
            qgon = new QGon(
                    new Point2D[]{
                            new Point2D(new double[]{-1.0, 4.0}),
                            new Point2D(new double[]{1.0, 10.0}),
                            new Point2D(new double[]{5.0, 9.0}),
                            new Point2D(new double[]{2.0, 0.0})
                    }

            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void area() throws Exception {
        assertEquals(qgon.square(), 32.5, 1e-6);
    }
}
