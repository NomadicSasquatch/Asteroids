package pkg;
import java.awt.*;

public class HealthBar {
    int health;
    int[][] xVertices;
    int[][] yVertices;

    public HealthBar(Ship ship) {
        this.health = ship.health;
        xVertices = new int[this.health][3];
        yVertices = new int[this.health][3];
        for(int i = 0; i < this.health; i++) {
            xVertices[i][0] = (int) (18 + 2 * i * ship.SHIP_WIDTH + ship.SHIP_HEIGHT * Math.cos(Math.toRadians(-90)));
            xVertices[i][1] = (int) (18 + 2 * i * ship.SHIP_WIDTH  + ship.SHIP_WIDTH * Math.cos(Math.toRadians(45)));
            xVertices[i][2] = (int) (18 + 2 * i * ship.SHIP_WIDTH  + ship.SHIP_WIDTH * Math.cos(Math.toRadians(-225)));

            yVertices[i][0] = (int) (65 + ship.SHIP_HEIGHT * Math.sin(Math.toRadians(-90)));
            yVertices[i][1] = (int) (65 + ship.SHIP_WIDTH * Math.sin(Math.toRadians(45)));
            yVertices[i][2] = (int) (65 + ship.SHIP_WIDTH * Math.sin(Math.toRadians(-225)));
        }
    }

    public void drawHealthBar(Graphics g) {
        if(health == 0) return;
        g.setColor(Color.WHITE);
        for(int i = 0; i < health; i++) {
            g.fillPolygon(xVertices[i], yVertices[i], 3);
        }
    }

    public void loseHealth() {
        health--;
    }
}
