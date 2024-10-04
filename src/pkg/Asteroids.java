package pkg;
import java.awt.*;
import java.util.Random;

import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.geom.Ellipse2D;

import static pkg.Constants.*;

public class Asteroids {
    private Image asteroidImage;

    public double x = 5;
    public double y = 5;
    public double diameter = 10;
    public double speedX = 10;
    public double speedY = 10;
    public double SPEED = 4; //change back to 4
    public double direction;
    public int mult;
    public int baseHealth = 2;
    public int health = 2;
    public int score = 100;

    public Asteroids() {
        Random random = new Random();
        int rand = (int)(Math.random() * RANDOM_ASTEROID_INT_RANGE);
        if(rand <= 150 && rand > 140) this.mult = 8;
        else if(rand <= 140 && rand > 90) this.mult = 4;
        else if(rand <= 90 && rand > 30) this.mult = 2;
        else this.mult = 1;
        //this.mult = 8;

        this.x = /*400;*/(int)(Math.random() * 600); //can remove this
        this.y = /*300;*/ (int)(Math.random() * 900);
        // this.x = 600;
        // this.y = 300;
        this.diameter *= this.mult;
        this.SPEED *= 16/this.mult; //remove 0
        this.health *= this.mult;
        this.direction = random.nextDouble() * 2 * Math.PI;

        this.speedX = SPEED * Math.cos(direction);
        this.speedY = SPEED * Math.sin(direction);

        try {
            asteroidImage = ImageIO.read(new File("C:\\Users\\deez nuts\\OneDrive\\Pictures\\Asteroid_Sprite.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Asteroids(Asteroids original, int i) {
        this.x = original.x;
        this.y = original.y;
        this.diameter = original.diameter;
        this.SPEED = original.SPEED;
        this.mult = original.mult;
        this.health = original.health;
        this.score = original.score;
        this.direction = original.direction + i * Math.toRadians(90);
        this.asteroidImage = original.asteroidImage;

        this.speedX = SPEED * Math.cos(this.direction);
        this.speedY = SPEED * Math.sin(this.direction);
    }

    public Asteroids(Asteroids original, int i, int physics) {
        this.diameter = original.diameter;
        this.SPEED = original.SPEED;
        this.mult = original.mult;
        this.health = original.health;
        this.score = original.score;
        this.direction = original.direction + i * Math.toRadians(90);
        this.asteroidImage = original.asteroidImage;

        this.speedX = SPEED * Math.cos(this.direction);
        this.speedY = SPEED * Math.sin(this.direction);
        x = original.x - (original.diameter * Math.cos(original.direction)) + (this.diameter * Math.cos(this.direction));
        y = original.y - (original.diameter * Math.sin(original.direction)) + (this.diameter * Math.sin(this.direction));
    }

    public double interpolate(double start, double end, double factor) {
        return start + (end - start) * factor;
    }

    public void move() {
        double targetX = x + speedX;
        double targetY = y + speedY;
    
        x = interpolate(x, targetX, 0.1);
        y = interpolate(y, targetY, 0.1);

        if(x + diameter <= 0) {
            x = WIDTH;
        }
        else if(x >= WIDTH) {
            x = -diameter;
        }
        if(y + diameter <= 0) {
            y = HEIGHT;
        }
        else if(y >= HEIGHT) {
            y = -diameter;
        }
    }

    public void drawAsteroid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Shape originalClip = g2d.getClip();

        Ellipse2D oval = new Ellipse2D.Double(x, y, diameter, diameter);
        g2d.setClip(oval);
        AffineTransform originalTransform = g2d.getTransform();

        g2d.translate(x + diameter / 2, y + diameter / 2);
        g2d.rotate(direction);
        g2d.drawImage(asteroidImage, -((int)diameter / 2), -((int)diameter / 2), (int)diameter, (int)diameter, null);
        g2d.setTransform(originalTransform);
        g2d.setClip(originalClip);
    }
    
    public boolean split() {
        if(mult / 2 == 0) { //should be mult, so diameter can vary
            return false;
        }
        else {
            Random random = new Random();
            mult /= 2; //divide by 2
            diameter /= 2; //divide by 2;
            health = baseHealth * mult;
            SPEED *= 2;
            score /= 2;
            direction = random.nextDouble() * 2 * Math.PI;
            speedX = SPEED * Math.cos(direction);
            speedY = SPEED * Math.sin(direction);

            return true;
        }
    }

    public boolean splitWithPhysics() {
        if(mult / 2 == 0) { //should be mult, so diameter can vary
            return false;
        }
        else {
            //Random random = new Random();
            mult /= 2; //divide by 2
            diameter /= 2; //divide by 2;
            health = baseHealth * mult;
            SPEED *= 2;
            score /= 2;
            //direction = random.nextDouble() * 2 * Math.PI;
            speedX = SPEED * Math.cos(direction);
            speedY = SPEED * Math.sin(direction);
            x += diameter * Math.cos(direction);
            y += diameter * Math.sin(direction);

            return true;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public double getX() {
        return x + diameter/2;
    }

    public double getY() {
        return y + diameter/2;
    }
}
