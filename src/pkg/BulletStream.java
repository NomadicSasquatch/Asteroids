package pkg;
import java.util.ArrayList;
import java.awt.Graphics;

public class BulletStream {
    ArrayList<Bullet> bulletsList = new ArrayList<>();
    public boolean canFire = true;

    public void fire(Ship ship) {
        if(canFire == true) {
            bulletsList.add(new Bullet(ship));
        }
    }

    public void moveBullets() {
        for(int i = 0; i < bulletsList.size(); i++) {
            Bullet bullet = bulletsList.get(i);
            if(bullet.checkBoundary() == false) {
                bulletsList.remove(i);
                i--;
            }
            else {
                bullet.moveBullet();
            }
        }
    }

    public void drawBulletStream(Graphics g) {
        for(int i = 0; i < bulletsList.size(); i++) {
            Bullet bullet = bulletsList.get(i);
            bullet.drawBullet(g);
        }
    }

    public void toggleFire() {
        canFire = false;
    }
}
