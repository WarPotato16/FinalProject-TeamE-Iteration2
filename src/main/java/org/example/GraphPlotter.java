package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphPlotter implements ActionListener {
    private List<Point> pointsToPlot;
    private Dimension graphSize;

    public GraphPlotter() {
        this.pointsToPlot = new ArrayList<>();
    }

    public GraphPlotter(Dimension graphSize)
    {
        this.pointsToPlot = new ArrayList<>();
        this.graphSize = graphSize;
    }

    public void setGraphSize(Dimension newSize)
    {
        this.graphSize = newSize;
    }

    public List<Point> getPointsToPlot() {
        return pointsToPlot;
    }

    // this method will change the function into (x,y) coordinate shape based on the size of displayPanel
    public void computePlotPoints(String expressionStr, double minX, double maxX, double step)
    {
        pointsToPlot.clear();
        Expression expression = new ExpressionBuilder(expressionStr)
                .variable("x")
                .build();

        // put the variable in 'y' based on the expression that the user inputted
        for (double x = minX; x <= maxX; x += step) {
            double y = expression.setVariable("x", x).evaluate();
            // Convert (x,y) coordinate values into screen coordinate
            // This means that the (x,y) coordinate values will be converted into coordinates based on the displayPanel so that the computer can understand the coordinates
            Point screenPoint = convertToScreenCoordinate(new Point((int) x, (int) y));
            pointsToPlot.add(screenPoint);
        }
    }

    // Convert (x,y) coordinate values into screen coordinate
    private Point convertToScreenCoordinate(Point mathPoint)
    {
        int centerX = CalculatorUI.displayPanel.getSize().width / 2;
        int centerY = CalculatorUI.displayPanel.getSize().height / 2;

        // functions that convert a mathematical point to a screen coordinate point
        int screenX = centerX + mathPoint.x;
        int screenY = centerY - mathPoint.y;

        return new Point(screenX, screenY);
    }

    // draw the graph by connecting all the plots
    public void drawPlot (Graphics2D g)
    {
        g.setColor(Color.BLUE); // set the color of the plot as BLUE

        // check if there are points to plot
        if (pointsToPlot != null && !pointsToPlot.isEmpty())
        {
            // Loop through the whole ArrayList from i = 0
            for (int i = 0; i < pointsToPlot.size() - 1; i++)
            {
                Point current = pointsToPlot.get(i); // get current point
                Point next = pointsToPlot.get(i + 1); // get next point to connect with the current point

                g.drawLine(current.x, current.y, next.x, next.y); // draw a line between the current point and the next point
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Dimension currentGraphsize = CalculatorUI.displayPanel.getSize();
        CalculatorUI.plotter.setGraphSize(currentGraphsize);

        System.out.println("Current Graph Panel Size: " + currentGraphsize);

        String expression = CalculatorUI.textDisplay.getText();
        CalculatorUI.plotter.computePlotPoints(expression, -((double) CalculatorUI.displayPanel.getSize().height / 2), ((double) CalculatorUI.displayPanel.getSize().height / 2), 1);
        CalculatorUI.displayPanel.repaint();
        CalculatorUI.plotter = new GraphPlotter(CalculatorUI.displayPanel.getSize());
    }
}