package pkg;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static pkg.Constants.*;

public class Ship {
    public final int SHIP_WIDTH = 10, SHIP_HEIGHT = 20;
    HealthBar healthbar;
    ShipPieces deathShipPieces = new ShipPieces();
    int xCoordinate, yCoordinate, angle, speed, health;
    private boolean canHit = true;

    private final int INVULNERABILITY_DURATION = 3000;
    private Timer invulnerabilityTimer;


    public Ship(int xCoordinate, int yCoordinate, int speed) {
        this.xCoordinate = xCoordinate / 2;
        this.yCoordinate = yCoordinate / 2;
        this.angle = -90;
        this.speed = speed;
        this.health = 5;
        healthbar = new HealthBar(this);
    }
    // public void drawDot(Graphics g) {
    //     g.setColor(Color.RED);
    //     g.fillOval(xCoordinate -2, yCoordinate - 2, 4,4);
    // }

    public void drawShip(Graphics g) {
        if(isAlive() == true) {
            int[] xPoints = {
                (int) (xCoordinate + SHIP_HEIGHT * Math.cos(Math.toRadians(angle))),
                (int) (xCoordinate + SHIP_WIDTH * Math.cos(Math.toRadians(angle + 135))),
                (int) (xCoordinate + SHIP_WIDTH * Math.cos(Math.toRadians(angle - 135)))
            };
            int[] yPoints = {
                (int) (yCoordinate + SHIP_HEIGHT * Math.sin(Math.toRadians(angle))),
                (int) (yCoordinate + SHIP_WIDTH * Math.sin(Math.toRadians(angle + 135))),
                (int) (yCoordinate + SHIP_WIDTH * Math.sin(Math.toRadians(angle - 135)))
            };
            if(canHit == true) {
                g.setColor(Color.WHITE);
            }
            else {
                g.setColor(Color.RED);
            }
            g.fillPolygon(xPoints, yPoints, 3);
            healthbar.drawHealthBar(g);
        }
        else {
            deathShipPieces.drawShipPieces(g);
        }
    }

    public void moveShip() {
        if(isAlive() == true) {
            xCoordinate += speed * Math.cos(Math.toRadians(angle));
            yCoordinate += speed * Math.sin(Math.toRadians(angle));

            if(xCoordinate < 0) xCoordinate = WIDTH;
            if(xCoordinate > WIDTH) xCoordinate = 0;
            if(yCoordinate < 0) yCoordinate = HEIGHT;
            if(yCoordinate > HEIGHT) yCoordinate = 0;
        }
        else {
            deathShipPieces.moveShipPieces();
        }
    }

    public void loseHealth() {
        this.health--;
        healthbar.setHealth(health);
    }

    public void triggerInvulnerability() {
        canHit = false;
        invulnerabilityTimer = new Timer(INVULNERABILITY_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canHit = true;
                invulnerabilityTimer.stop();
            }
        });
        invulnerabilityTimer.setRepeats(false);
        invulnerabilityTimer.start();
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void deathSplit() {
        deathShipPieces.splitPieces(this);
    }

    public boolean vulnerabilityStatus() {
        return canHit;
    }

    public void toggleVulnerabilityStatus() {
        if(canHit == true) {
            canHit = false;
        }
        else {
            canHit = true;
        }
    }
}
