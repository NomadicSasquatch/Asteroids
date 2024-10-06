package pkg;
import java.awt.*;
import java.util.ArrayList;

public class AsteroidsCluster {
    private final int MAX_ASTEROIDS = 10; //change back to 10
    private int asteroidsCount;

    private ArrayList<Asteroids> asteroidsList = new ArrayList<>();

    public AsteroidsCluster() {
        asteroidsCount = 0;
    }
    public int updateAsteroids(BulletStream bullets) {
        int scoreUpdate = 0;
        ArrayList<Asteroids> newAsteroids = new ArrayList<>();

        for(int i = 0; i < bullets.getBulletStream().size(); i++) {
            Bullet bullet = bullets.getBulletStream().get(i);
            for(int j = 0; j < asteroidsList.size(); j++) {
                Asteroids asteroid = asteroidsList.get(j);
                if(bullet.asteroidHit(asteroid) == true) {
                    bullets.getBulletStream().remove(i);
                    i--;
                    if(asteroid.isAlive() == false) {
                        scoreUpdate += asteroid.getScore();
                        if(asteroid.splitWithPhysics() == true) {
                            for(int k = 0; k < 3; k++) {
                                Asteroids copy = new Asteroids(asteroid, k+1, 1);
                                newAsteroids.add(copy);
                            }
                            asteroidsCount += 3;
                        }
                        // if(asteroid.split() == true) {
                        //     for(int k = 0; k < 3; k++) {
                        //         Asteroids copy = new Asteroids(asteroid, k+1);
                        //         newAsteroids.add(copy);
                        //     }
                        //     asteroidsCount += 3;
                        // }
                        else {
                            asteroidsList.remove(j);
                            j--;
                            asteroidsCount--;
                        }
                    }
                    break;
                }
            }
        }
        asteroidsList.addAll(newAsteroids);
        // /* WITH CONSERVATION OF MOMENTUM AND KE */
        for(int i = 0; i < asteroidsList.size(); i++) {
            for(int j = i + 1; j < asteroidsList.size(); j++) {
                Asteroids asteroid1 = asteroidsList.get(i);
                Asteroids asteroid2 = asteroidsList.get(j);
                if(asteroidHit(asteroid1, asteroid2) == true) {
                    deflect(asteroid1, asteroid2);
                }
            }
        }
        if(asteroidsCount < MAX_ASTEROIDS) {
            Asteroids newAsteroid = new Asteroids();
            asteroidsList.add(newAsteroid);
            asteroidsCount++;
        }
        return scoreUpdate;
    }
    public boolean asteroidHit(Asteroids asteroid1, Asteroids asteroid2) {
        double dx = (asteroid1.getXCoordinate() + asteroid1.getDiameter()/2) - (asteroid2.getXCoordinate() + asteroid2.getDiameter()/2);
        double dy = (asteroid1.getYCoordinate() + asteroid1.getDiameter()/2) - (asteroid2.getYCoordinate() + asteroid2.getDiameter()/2);
        double distance = (double) Math.sqrt((double) (dx * dx) + (double) (dy * dy));
        if(distance <= (double) ((double) asteroid2.getDiameter()/2 + (double) asteroid1.getDiameter()/2)) {
            return true;
        }
        else return false;
    }

    public void moveAsteroids() {
        for(Asteroids asteroid : asteroidsList) {
            asteroid.move();
        }
    }

    public void drawAsteroids(Graphics g) {
        for(int i = 0; i < asteroidsList.size(); i++) {
            Asteroids asteroid = asteroidsList.get(i);
            asteroid.drawAsteroid(g);
        }
    }

    public void checkShipCollision(Ship ship) {
        if(ship.vulnerabilityStatus() == false) {
            return;
        }
        for(int i = 0; i < asteroidsList.size(); i++) {
            Asteroids asteroid = asteroidsList.get(i);
            if(isShipCollision(ship, asteroid) == true && ship.vulnerabilityStatus() == true) {
                ship.loseHealth();
                if(ship.isAlive() == true) {
                    ship.triggerInvulnerability();
                }
                else {
                    ship.toggleVulnerabilityStatus();
                    ship.deathSplit();
                }
                break;
            }
        }
    }

    public void deflect(Asteroids a1, Asteroids a2) {
        while(asteroidHit(a1, a2) == true) {
            double dx = a2.getXCoordinate() - a1.getXCoordinate();
            double dy = a2.getYCoordinate() - a1.getYCoordinate();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double nx = dx / distance;
            double ny = dy / distance;
            double a1SpeedX = a1.getSpeedX();
            double a1SpeedY = a1.getSpeedY();
            double a2SpeedX = a2.getSpeedX();
            double a2SpeedY = a2.getSpeedY();
            double v1n = a1SpeedX * nx + a1SpeedY * ny;
            double v2n = a2SpeedX * nx + a2SpeedY * ny;
            double v1nAfter = v2n;
            double v2nAfter = v1n;

            a1.setSpeedX(v1nAfter * nx + (a1SpeedX - v1n * nx));
            a1.setSpeedY(v1nAfter * ny + (a1SpeedY - v1n * ny));

            a2.setSpeedX(v2nAfter * nx + (a2SpeedX - v2n * nx));
            a2.setSpeedY(v2nAfter * ny + (a2SpeedY - v2n * ny));
            while(asteroidHit(a1, a2)) {
                a1.move();
                a2.move();
            }
        }
    }
    //this is recomputed for the drawShip function, see if can overlay without stretching logic
    public boolean isShipCollision(Ship ship, Asteroids asteroid) {
        int[] xPoints = {
            (int) (ship.xCoordinate + ship.SHIP_HEIGHT * Math.cos(Math.toRadians(ship.angle))),
            (int) (ship.xCoordinate + ship.SHIP_WIDTH * Math.cos(Math.toRadians(ship.angle + 135))),
            (int) (ship.xCoordinate + ship.SHIP_WIDTH * Math.cos(Math.toRadians(ship.angle - 135)))
        };
        int[] yPoints = {
            (int) (ship.yCoordinate + ship.SHIP_HEIGHT * Math.sin(Math.toRadians(ship.angle))),
            (int) (ship.yCoordinate + ship.SHIP_WIDTH * Math.sin(Math.toRadians(ship.angle + 135))),
            (int) (ship.yCoordinate + ship.SHIP_WIDTH * Math.sin(Math.toRadians(ship.angle - 135)))
        };

        return isCollision(xPoints, yPoints, 3, asteroid.getXCoordinate() + asteroid.getDiameter()/2, asteroid.getYCoordinate() + asteroid.getDiameter()/2, asteroid.getDiameter()/2);
    }

    public boolean isCollision(int[] xPoints, int[] yPoints, int numPoints, double circleX, double circleY, double radius) {
        for(int i = 0; i < numPoints; i++) {
            if(isPointInCircle(xPoints[i], yPoints[i], circleX, circleY, radius)) {
                return true;
            }
        }

        if(isPointInTriangle(circleX, circleY, xPoints, yPoints)) {
            return true;
        }
    
        for(int i = 0; i < numPoints; i++) {
            int next = (i + 1) % numPoints;
            if(isLineCircleIntersection(xPoints[i], yPoints[i], xPoints[next], yPoints[next], circleX, circleY, radius)) {
                return true;
            }
        }
    
        return false;
    }
    
    private boolean isPointInCircle(double px, double py, double cx, double cy, double radius) {
        double distSq = (px - cx) * (px - cx) + (py - cy) * (py - cy);
        return distSq <= radius * radius;
    }
    
    private boolean isPointInTriangle(double px, double py, int[] xPoints, int[] yPoints) {
        double d1, d2, d3;
        boolean hasNeg, hasPos;
    
        d1 = sign(px, py, xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        d2 = sign(px, py, xPoints[1], yPoints[1], xPoints[2], yPoints[2]);
        d3 = sign(px, py, xPoints[2], yPoints[2], xPoints[0], yPoints[0]);
    
        hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);
    
        return !(hasNeg && hasPos);
    }
    
    private double sign(double px, double py, double x1, double y1, double x2, double y2) {
        return (px - x2) * (y1 - y2) - (x1 - x2) * (py - y2);
    }
    
    private boolean isLineCircleIntersection(double x1, double y1, double x2, double y2, double cx, double cy, double radius) {
        double dx = x2 - x1;
        double dy = y2 - y1;
    
        double fx = x1 - cx;
        double fy = y1 - cy;
    
        double a = dx * dx + dy * dy;
        double b = 2 * (fx * dx + fy * dy);
        double c = (fx * fx + fy * fy) - radius * radius;
    
        double discriminant = b * b - 4 * a * c;
    
        if(discriminant >= 0) {
            discriminant = Math.sqrt(discriminant);
    
            double t1 = (-b - discriminant) / (2 * a);
            double t2 = (-b + discriminant) / (2 * a);
    
            if(t1 >= 0 && t1 <= 1 || t2 >= 0 && t2 <= 1) {
                return true;
            }
        }
    
        return false;
    }
}
