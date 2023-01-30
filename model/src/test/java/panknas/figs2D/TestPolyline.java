package panknas.figs2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPolyline {
    private static Polyline polyline = null;

    static {
        try {
            polyline = new Polyline(
                    new Point2D[]{
                            new Point2D(new double[]{0, 3}),
                            new Point2D(new double[]{2, 1}),
                            new Point2D(new double[]{1, 1})
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void length() throws Exception {
        assertEquals(polyline.length(), Math.sqrt(8)+1, 1e-6);
    }

}
