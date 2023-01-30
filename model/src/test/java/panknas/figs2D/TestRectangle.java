package panknas.figs2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRectangle {
    private static Rectangle rectangle = null;

    static {
        try {
            rectangle = new Rectangle(
                    new Point2D[]{
                            new Point2D(new double[]{0, 4}),
                            new Point2D(new double[]{8, 10}),
                            new Point2D(new double[]{11, 6}),
                            new Point2D(new double[]{3, 0})
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void area() throws Exception {
        assertEquals(rectangle.square(), 50, 1e-6);
    }

}
