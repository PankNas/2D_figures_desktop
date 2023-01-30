package panknas.figs2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTrapeze {
    private static Trapeze trapeze = null;

    static {
        try {
            trapeze = new Trapeze(
                    new Point2D[]{
                            new Point2D(new double[]{-1, 4}),
                            new Point2D(new double[]{1, 10}),
                            new Point2D(new double[]{5, 9}),
                            new Point2D(new double[]{2, 0}),
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void area() throws Exception {
        assertEquals(trapeze.square(), 32.5, 1e-6);
    }

}
