package rendering;

import java.awt.*;
import javax.swing.*;


public class GraphicsDemo extends JPanel{

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        this.setBackground(Color.BLACK);

        Graphics2D g2D = (Graphics2D) g;

        int[] xPoints = {50,100,150,200,250,300,350};
        int[] yPoints = {350, 250, 275, 200, 150, 100,100};
        int nPoints = xPoints.length;

        g2D.setColor(Color.WHITE);
        //g2D.setStroke(new BasicStroke(10));
        //g2D.drawLine(10, 10, 310, 175);

        //g2D.setColor(Color.GREEN);
        //g2D.setStroke(new BasicStroke((2)));
        //g2D.drawPolyline(xPoints, yPoints, nPoints);

        //g2D.setFont(new Font("Arial", Font.ITALIC, 30));
        //g2D.setColor(Color.GREEN);
        //g2D.drawString("Random Text", 100, 100);


        //int[] xPoly = {100, 200, 300};
        //int[] yPoly = {300, 127, 300};
        //g2D.setColor(Color.YELLOW);
        //g2D.fillPolygon(xPoly, yPoly, 3);

        g2D.drawRect(50, 50, 300, 300);

    }

}
