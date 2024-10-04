package pkg;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;

import static pkg.Constants.*;

public class AsteroidsGame extends JPanel implements ActionListener, KeyListener, Runnable {
    public boolean isRunning = false;
    private int score = 0;

    @SuppressWarnings("unused")
    private Timer timer;
    private JLabel scoreLabel;
    private JLabel endMessageLabel;
    private Timer gameTimer;
    private Thread gameThread;
    

    Ship ship = new Ship(Constants.WIDTH, Constants.HEIGHT, 0);
    AsteroidsCluster cluster = new AsteroidsCluster();
    BulletStream bullets = new BulletStream();
    boolean forwardPress = false, leftRotate = false, rightRotate = false, downPress = false, fire = false;

    public AsteroidsGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        scoreLabel.setForeground(Color.WHITE);
        this.add(scoreLabel);

        setLayout(null);
        scoreLabel.setBounds(10, 3, 200, 40);
        endMessageLabel = new JLabel("", SwingConstants.CENTER);
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\deez nuts\\Downloads\\xinyi (1).ttf")).deriveFont(24f);
            endMessageLabel.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        endMessageLabel.setForeground(Color.RED);
        this.add(endMessageLabel);
        endMessageLabel.setBounds(175, 100, 900, 300);
    }

    public void startGame() {
        isRunning = true;
        gameThread = new Thread(this);
        timer = new Timer(DELAY, this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double nsPerUpdate = 1000000000.0 / 60.0;

        double unprocessedTime = 0;

        while(isRunning) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / nsPerUpdate;
            lastTime = now;
            unprocessedTime += deltaTime;

            while (unprocessedTime >= 1) {
                update();
                handleInput();
                checkEndCondition();
                unprocessedTime--;
            }

            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ship.drawShip(g);
        cluster.drawAsteroids(g);
        bullets.drawBulletStream(g);
        if(ship.isAlive() == true) {
            scoreLabel.setText("Score: " + score);
        }
        else {
            if(scoreLabel != null) {
                this.remove(scoreLabel);
                this.revalidate();
                this.repaint();
                scoreLabel = null;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W) {
            forwardPress = true;
        }
        if(key == KeyEvent.VK_A) {
            leftRotate = true;
        }
        if(key == KeyEvent.VK_D) {
            rightRotate = true;
        }
        if(key == KeyEvent.VK_S) {
            downPress = true;
        }
        if(key == KeyEvent.VK_SPACE) {
            fire = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }

    public void update() {
        ship.moveShip();
        score += cluster.updateAsteroids(bullets);
        cluster.moveAsteroids();
        cluster.checkShipCollision(ship);
        bullets.moveBullets();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W) {
            ship.speed = 0;
            forwardPress = false;
        }
        if(key == KeyEvent.VK_A) {
            leftRotate = false;
        }
        if(key == KeyEvent.VK_D) {
            rightRotate = false;
        }
        if(key == KeyEvent.VK_S) {
            ship.speed = 0;
            downPress = false;
        }
        if(key == KeyEvent.VK_SPACE) {
            fire = false;
        }
    }
    
    public void handleInput() {
        if(forwardPress == true) {
            ship.speed = MAX_SPEED;
        }
        if(leftRotate == true) {
            ship.angle -= ROTATION_SPEED;
            if(ship.angle < 0) ship.angle += 360;
        }
        if(rightRotate == true) {
            ship.angle += ROTATION_SPEED;
            if (ship.angle >= 360) ship.angle -= 360;
        }
        if(downPress == true) {
            ship.speed = -MAX_SPEED;
        }
        if(fire == true) {
            bullets.fire(ship);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    public void displayEndMessage() {
        // Stop the game
        if(gameTimer != null) {
            gameTimer.stop();
        }
        endMessageLabel.setText("GAME OVER! FINAL SCORE:" + score);
    }

    public void checkEndCondition() {
        if(ship.isAlive() == false) {
            bullets.toggleFire();
            endGame();
        }
    }

    public void endGame() {
        //Stop game and display end game message after 3s delay
        new Timer(3000, e -> {
            displayEndMessage();
        }).start();
    }
}
