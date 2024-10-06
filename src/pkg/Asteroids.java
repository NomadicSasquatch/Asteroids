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

    private double xCoordinate = 5;
    private double yCoordinate = 5;
    private double diameter = 10;
    private double speedX = 10;
    private double speedY = 10;
    private double SPEED = 4; //change back to 4
    private double direction;
    private int mult;
    private int baseHealth = 2;
    private int health = 2;
    private int score = 100;

    public Asteroids() {
        Random random = new Random();
        int rand = (int)(Math.random() * RANDOM_ASTEROID_INT_RANGE);
        if(rand <= 150 && rand > 140) this.mult = 8;
        else if(rand <= 140 && rand > 90) this.mult = 4;
        else if(rand <= 90 && rand > 30) this.mult = 2;
        else this.mult = 1;
        // this.mult = 14;

        this.xCoordinate = /*400;*/(int)(Math.random() * 600); //can remove this
        this.yCoordinate = /*300;*/ (int)(Math.random() * 900);
        // this.xCoordinate = 600;
        // this.yCoordinate = 300;
        this.diameter *= this.mult;
        this.SPEED *= 16/this.mult; //remove 0
        this.health *= this.mult;
        this.direction = random.nextDouble() * 2 * Math.PI;

        this.speedX = SPEED * Math.cos(direction);
        this.speedY = SPEED * Math.sin(direction);

        try {
            asteroidImage = ImageIO.read(new File("C:\\Users\\deez nuts\\OneDrive\\Pictures\\Asteroid_Sprite(1).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Asteroids(Asteroids original, int i) {
        this.xCoordinate = original.xCoordinate;
        this.yCoordinate = original.yCoordinate;
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
        xCoordinate = original.xCoordinate - (original.diameter * Math.cos(original.direction)) + (this.diameter * Math.cos(this.direction));
        yCoordinate = original.yCoordinate - (original.diameter * Math.sin(original.direction)) + (this.diameter * Math.sin(this.direction));
    }

    public double interpolate(double start, double end, double factor) {
        return start + (end - start) * factor;
    }

    public void move() {
        double targetX = xCoordinate + speedX;
        double targetY = yCoordinate + speedY;
    
        xCoordinate = interpolate(xCoordinate, targetX, 0.1);
        yCoordinate = interpolate(yCoordinate, targetY, 0.1);

        if(xCoordinate + diameter <= 0) {
            xCoordinate = WIDTH;
        }
        else if(xCoordinate >= WIDTH) {
            xCoordinate = -diameter;
        }
        if(yCoordinate + diameter <= 0) {
            yCoordinate = HEIGHT;
        }
        else if(yCoordinate >= HEIGHT) {
            yCoordinate = -diameter;
        }
    }
    

    public void drawAsteroid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Shape originalClip = g2d.getClip();
        AffineTransform originalTransform = g2d.getTransform();

        Ellipse2D oval = new Ellipse2D.Double(xCoordinate, yCoordinate, diameter, diameter);
        g2d.setClip(oval);

        g2d.translate(xCoordinate + diameter / 2, yCoordinate + diameter / 2);
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
            xCoordinate += diameter * Math.cos(direction);
            yCoordinate += diameter * Math.sin(direction);

            return true;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void reduceHealth() {
        health--;
    }

    public double getXCoordinate() {
        return xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getDiameter() {
        return diameter;
    }

    public int getScore() {
        return score;
    }
}
