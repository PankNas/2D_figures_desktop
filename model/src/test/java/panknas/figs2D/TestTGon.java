package panknas.figs2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTGon {
    private static TGon tgon = null;

    static {
        try {
            tgon = new TGon(
                    new Point2D[]{
                            new Point2D(new double[]{1, 5}),
                            new Point2D(new double[]{2, 10}),
                            new Point2D(new double[]{3, 8})
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void area() throws Exception {
        assertEquals(tgon.square(), 3.5, 1e-6);
    }

}
