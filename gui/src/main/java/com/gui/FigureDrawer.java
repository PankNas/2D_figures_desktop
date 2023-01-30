package com.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import panknas.figs2D.*;

import java.util.Objects;

public class FigureDrawer {
    private final GraphicsContext context;
    double width;
    double height;

    public FigureDrawer(Canvas canvas) {
        context = canvas.getGraphicsContext2D();
        width = canvas.getWidth();
        height = canvas.getHeight();
    }

    public void drawShape(IShape s) {
        context.setStroke(Color.BLACK);
        if ("Circle".equals(s.getNameFig(0)))
            drawCircle((Circle) s);
        else
            draw(s);
    }

    void drawCircle(Circle c) {
        color(c);
        this.context.strokeOval(width / 2 + c.getP().getX(0) - c.getR(),
                height / 2 + c.getP().getX(1) - c.getR(),
                c.getR() * 2,
                c.getR() * 2);
    }

    void draw(IShape fig){
        color(fig);
        Segment[] segments = fig.intoSegments();
        for (Segment s: segments){
            context.strokeLine(width / 2 + s.getStart().getX(0), height / 2 + s.getStart().getX(1),
                                width / 2 + s.getFinish().getX(0), height / 2 + s.getFinish().getX(1));
        }
    }

    private void color(IShape fig) {
        context.setStroke(Color.BLACK);
        for (int i = 1; i < MainController.value.size(); i++) {
            if (Objects.equals(fig, MainController.value.get(i)))
                context.setStroke(Color.RED);
        }
    }
}
