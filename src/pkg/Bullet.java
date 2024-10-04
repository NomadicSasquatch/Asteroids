package pkg;
import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
    private final int SHIP_HEIGHT = 20, WIDTH = 1200, HEIGHT = 800, MAX_SPEED = 10;
    private int SPEED;
    public double x;
    public double y;
    private double speedX;
    private double speedY;
    public double diameter = 2;

    public Bullet(Ship ship) {
        this.SPEED = MAX_SPEED * 2; //make code cleaner here
        this.x = ship.xCoordinate + (double) SHIP_HEIGHT * Math.cos(Math.toRadians(ship.angle)) - diameter / 2;
        this.y = ship.yCoordinate + (double) SHIP_HEIGHT * Math.sin(Math.toRadians(ship.angle)) - diameter / 2; 
        this.speedX = (double) (SPEED * (double) Math.cos(Math.toRadians(ship.angle)));
        this.speedY = (double) (SPEED * (double) Math.sin(Math.toRadians(ship.angle)));
    }
    
    public void moveBullet() {
        x += speedX;
        y += speedY;
        //see if interpolation is needed (seems to slow things down drastically)
    }

    public boolean checkBoundary() {
        if(x < 0 || x > WIDTH || y < 0 || y > HEIGHT) return false;
        else return true;
    }

    public void drawBullet(Graphics g) {
        g.setColor(Color.white);
        g.fillOval((int) x, (int) y, (int) diameter, (int) diameter);
    }

    public boolean asteroidHit(Asteroids asteroid) {
        double dx = (asteroid.x + asteroid.diameter/2) - (this.x + this.diameter/2);
        double dy = (asteroid.y + asteroid.diameter/2) - (this.y + this.diameter/2);
        double distance = (double) Math.sqrt((double) (dx * dx) + (double) (dy * dy));
        if(distance <= (double) ((double) this.diameter/2 + (double) asteroid.diameter/2)) {
            asteroid.health--;
            return true;
        }
        else return false;
    }
}
