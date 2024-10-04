package pkg;
import java.awt.*;
import java.util.Random;

import static pkg.Constants.*;

public class ShipPiece {
    public int x, y, speed = 3, angle;
    public final int SHIP_WIDTH = 10, SHIP_HEIGHT = 20;
    int displace = 0;

    public ShipPiece(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void drawShipPiece(Graphics g, int i) {
        if(i == 0) {
            int[] xPoints = {
                (int) (x),
                (int) (x + SHIP_WIDTH * Math.cos(Math.toRadians(displace + angle + 60 - 135))),
                (int) (x + SHIP_HEIGHT * Math.cos(Math.toRadians(displace + angle + 60))),
            };
            int[] yPoints = {
                (int) (y),
                (int) (y + SHIP_WIDTH * Math.sin(Math.toRadians(displace + angle + 60 - 135))),
                (int) (y + SHIP_HEIGHT * Math.sin(Math.toRadians(displace + angle + 60)))
            };
            g.setColor(Color.RED);
            g.fillPolygon(xPoints, yPoints, 3);
        }
        else if(i == 1) {
            int[] xPoints = {
                (int) (x),
                (int) (x + SHIP_WIDTH * Math.cos(Math.toRadians(displace + angle - 60 + 135))),
                (int) (x + SHIP_HEIGHT * Math.cos(Math.toRadians(displace + angle - 60))),
            };
            int[] yPoints = {
                (int) (y),
                (int) (y + SHIP_WIDTH * Math.sin(Math.toRadians(displace + angle - 60 + 135))),
                (int) (y + SHIP_HEIGHT * Math.sin(Math.toRadians(displace + angle - 60)))
            };
            g.setColor(Color.RED);
            g.fillPolygon(xPoints, yPoints, 3);
        }
        else {
            int[] xPoints = {
                (int) (x),
                (int) (x + SHIP_WIDTH * Math.cos(Math.toRadians(displace + angle - 180 + 135))),
                (int) (x + SHIP_WIDTH * Math.cos(Math.toRadians(displace + angle - 180 - 135))),
            };
            int[] yPoints = {
                (int) (y),
                (int) (y + SHIP_WIDTH * Math.sin(Math.toRadians(displace + angle - 180 + 135))),
                (int) (y + SHIP_WIDTH * Math.sin(Math.toRadians(displace + angle - 180 + 135))),
            };
            g.setColor(Color.RED);
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }

    public void moveShipPiece() {
        Random random = new Random();
        x += speed * Math.cos(Math.toRadians(angle));
        y += speed * Math.sin(Math.toRadians(angle));
        angle += (random.nextDouble() * 2 * Math.PI) / 3;
        if(x <= 0) {
            x = -100;
        }
        else if(x >= WIDTH - 1) {
            x = WIDTH + 100;
        }
        if(y <= 0) {
            y = -100;
        }
        else if(y >= HEIGHT - 1) {
            y = HEIGHT + 100;
        }

        if (speed > 0) speed *= 0.9999999999999999999999999999999999999999999999999999999999;
    }
}
