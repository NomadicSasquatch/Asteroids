package pkg;

public class Equations {
    public void deflectDiff(Asteroids asteroid1, Asteroids asteroid2) {
        double m1 = asteroid1.mult;
        double m2 = asteroid2.mult == asteroid1.mult? asteroid2.mult + 0.1 : asteroid2.mult;
        double v1 = asteroid1.SPEED;
        double v2 = asteroid2.SPEED;

        double theta1 = asteroid1.direction;
        double theta2 = asteroid2.direction;    
        theta1 = Math.toRadians(theta1);
        theta2 = Math.toRadians(theta2);

        double p1x = m1 * v1 * Math.cos(theta1);
        double p1y = m1 * v1 * Math.sin(theta1);
        double p2x = m2 * v2 * Math.cos(theta2);
        double p2y = m2 * v2 * Math.sin(theta2);

        double theta1Final = Math.atan2(p1y, p1x);
        double theta2Final = Math.atan2(p2y, p2x);

        asteroid1.direction = theta1Final;
        asteroid2.direction = theta2Final;
        asteroid1.speedX = asteroid1.SPEED * Math.cos(theta1Final);
        asteroid1.speedY = asteroid1.SPEED * Math.sin(theta1Final);
        asteroid2.speedX = asteroid2.SPEED * Math.cos(theta2Final);
        asteroid2.speedY = asteroid2.SPEED * Math.sin(theta2Final);
    }

    public void deflectSame(Asteroids asteroid1, Asteroids asteroid2) {
        
    }
}
