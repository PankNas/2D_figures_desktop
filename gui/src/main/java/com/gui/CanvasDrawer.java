package com.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import panknas.figs2D.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CanvasDrawer {
    private final GraphicsContext context;
    private final FigureDrawer figureDrawer;

    double width;
    double height;

    public CanvasDrawer(Canvas canvas) {
        context = canvas.getGraphicsContext2D();
        context.setLineWidth(1);

        width = canvas.getWidth();
        height = canvas.getHeight();

        figureDrawer = new FigureDrawer(canvas);
    }

    private void drawAxes(){
        context.setStroke(Color.GRAY);
        context.strokeLine(width / 2, 0, width / 2, height);
        context.strokeLine(0, height / 2, width, height / 2);
    }

    private void reset(){
        this.context.clearRect(0, 0, width + height, width + height);
        this.drawAxes();
    }

    public void update() throws Exception {
        this.reset();
        for (IShape fig : HelloApplication.listFigures) {
            figureDrawer.drawShape((IShape) fig.symAxis(0));
            fig.symAxis(0);
        }
    }
}
