package pkg;
import java.awt.Color;
import java.awt.Graphics;

import static pkg.Constants.*;

public class Bullet {
    private int SPEED;
    private double x;
    private double y;
    private double speedX;
    private double speedY;
    private final double DIAMETER = 2;

    public Bullet(Ship ship) {
        this.SPEED = MAX_SPEED * 2; //make code cleaner here
        this.x = ship.xCoordinate + (double) ship.SHIP_HEIGHT * Math.cos(Math.toRadians(ship.angle)) - DIAMETER / 2;
        this.y = ship.yCoordinate + (double) ship.SHIP_HEIGHT * Math.sin(Math.toRadians(ship.angle)) - DIAMETER / 2; 
        this.speedX = (double) (SPEED * (double) Math.cos(Math.toRadians(ship.angle)));
        this.speedY = (double) (SPEED * (double) Math.sin(Math.toRadians(ship.angle)));
    }
    
    public void moveBullet() {
        x += speedX;
        y += speedY;
    }

    public boolean checkBoundary() {
        if(x < 0 || x > WIDTH || y < 0 || y > HEIGHT) return false;
        else return true;
    }

    public void drawBullet(Graphics g) {
        g.setColor(Color.white);
        g.fillOval((int) x, (int) y, (int) DIAMETER, (int) DIAMETER);
    }

    public boolean asteroidHit(Asteroids asteroid) {
        double dx = (asteroid.getXCoordinate() + asteroid.getDiameter()/2) - (this.x + this.DIAMETER/2);
        double dy = (asteroid.getYCoordinate() + asteroid.getDiameter()/2) - (this.y + this.DIAMETER/2);
        double distance = (double) Math.sqrt((double) (dx * dx) + (double) (dy * dy));

        if(distance <= (double) ((double) this.DIAMETER/2 + (double) asteroid.getDiameter()/2)) {
            asteroid.reduceHealth();;
            return true;
        }
        else {
            return false;
        }
    }
}
