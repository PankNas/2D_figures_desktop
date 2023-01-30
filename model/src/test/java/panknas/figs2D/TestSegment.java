package panknas.figs2D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSegment {
    private final Segment segment = new Segment(
            new Point2D(),
            new Point2D(new double[]{1, 1})
    );

    public TestSegment() throws Exception {}

    @Test
    public void shouldHaveLength(){
        assertEquals(segment.length(), Math.sqrt(2), 1e-6);
    }

    @Test
    public void shouldCross() throws Exception {
        assertTrue(segment.cross(
                new Segment(
                        new Point2D(new double[]{0, 0.5}),
                        new Point2D(new double[]{0.5, 0})
                )
        ));

        assertFalse(
                segment.cross(
                        new Segment(
                                new Point2D(new double[]{2, 2}),
                                new Point2D(new double[]{2, 3})
                        )
                )
        );
    }
}
