package gdd.scene;

import gdd.AudioPlayer;
import gdd.Game;
import static gdd.Global.*;
import gdd.SpawnDetails;
import gdd.powerup.PowerUp;
import gdd.powerup.SpeedUp;
import gdd.sprite.Alien1;
import gdd.sprite.BossEnemy;
import gdd.sprite.Enemy;
import gdd.sprite.Explosion;
import gdd.sprite.Player;
import gdd.sprite.Shot;
import gdd.sprite.ZigZagEnemy;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Image;

public class Scene1 extends JPanel {

    private int frame = 0;
    private List<PowerUp> powerups;
    private List<Enemy> enemies;
    private List<Explosion> explosions;
    private List<Shot> shots;
    private Player player;
    // private Shot shot;

    final int BLOCKHEIGHT = 50;
    final int BLOCKWIDTH = 50;

    final int BLOCKS_TO_DRAW = BOARD_HEIGHT / BLOCKHEIGHT;

    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private String message = "Game Over";

    private final Dimension d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    private final Random randomizer = new Random();

    private Timer timer;
    private final Game game;

    private int currentRow = -1;
    // TODO load this map from a file
    private int mapOffset = 0;
    private final int[][] MAP = {
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}
    };

    private HashMap<Integer, SpawnDetails> spawnMap = new HashMap<>();
    private AudioPlayer audioPlayer;
    private int lastRowToShow;
    private int firstRowToShow;

    private static final int FADE_START_FRAME = 10000;
    private static final int FADE_DURATION_FRAMES = 120; // black to white
    private static final int RED_FADE_START_FRAME = 20000;
    private static final int RED_FADE_DURATION_FRAMES = 120; // white to red

    private static final int FADE_START_FRAME_2 = 1000;

    private int bossWinTimer = -1;

    private Image winImage = new ImageIcon("src/images/win.png").getImage();
    private Image lossImage = new ImageIcon("src/images/loss.png").getImage();

    public Scene1(Game game) {
        this.game = game;
        // initBoard();
        // gameInit();
        loadSpawnDetails();
    }

    private void initAudio() {
        try {
            String filePath = "src/audio/scene1.wav";
            audioPlayer = new AudioPlayer(filePath);
            audioPlayer.play();
        } catch (Exception e) {
            System.err.println("Error initializing audio player: " + e.getMessage());
        }
    }

    private void loadSpawnDetails() {
        // TODO load this from a file
        // spawnMap.put(50, new SpawnDetails("PowerUp-SpeedUp", 100, 0));

        // for(int k = 1; k < 50; k++){
        //     for (int i = 0; i < 10; i++) {
        //         if(k % 2 == 0){
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("Alien1", 100 + (i * 60), 0));
        //         }else{
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("Alien1", 600 - (i * 60), 0));
        //         }
        //     }
        // }

        // for(int k = 51; k < 100; k++){
        //     for (int i = 0; i < 10; i++) {
        //         if(i % 2 == 0){
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("Alien1", 100 + (i * 60), 0));
        //         }else{
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("Alien1", 600 - (i * 60), 0));
        //         }
        //     }
        // }


        // for(int k = 101; k < 150; k++){
        //     for (int i = 0; i < 10; i++) {
        //         if(k % 2 == 0){
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("ZigZagEnemy", 100 + (i * 60), 0));
        //         }else{
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("ZigZagEnemy", 600 - (i * 60), 0));
        //         }
        //     }
        // }

        // for(int k = 151; k < 200; k++){
        //     for (int i = 0; i < 10; i++) {
        //         if(i % 2 == 0){
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("ZigZagEnemy", 100 + (i * 60), 0));
        //         }else{
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("ZigZagEnemy", 600 - (i * 60), 0));
        //         }
        //     }
        // }




        // spawnMap.put(20020, new SpawnDetails("BossEnemy", 250, 50)); // Boss spawns at frame 120
        // for(int k = 201; k < 300; k++){
        //     for (int i = 0; i < 10; i++) {
        //         if(i % 2 == 0){
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("ZigZagEnemy", 100 + (i * 60), 0));
        //         }else{
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("ZigZagEnemy", 600 - (i * 60), 0));
        //         }
        //     }
        // }
        // for(int k = 201; k < 300; k++){
        //     for (int i = 0; i < 10; i++) {
        //         if(i % 2 == 0){
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("Alien1", 100 + (i * 60), 0));
        //         }else{
        //             spawnMap.put((k * 100) +(i*3) , new SpawnDetails("Alien1", 600 - (i * 60), 0));
        //         }
        //     }
        // }


        spawnMap.put(120, new SpawnDetails("BossEnemy", 250, 50)); // Boss spawns at frame 120
        for(int k = 1; k < 100; k++){
            for (int i = 0; i < 10; i++) {
                if(i % 2 == 0){
                    spawnMap.put((k * 100) +(i*3) , new SpawnDetails("ZigZagEnemy", 100 + (i * 60), -100));
                }else{
                    spawnMap.put((k * 100) +(i*3) , new SpawnDetails("ZigZagEnemy", 600 - (i * 60), -100));
                }
            }
        }
        for(int k = 1; k < 100; k++){
            for (int i = 0; i < 10; i++) {
                if(i % 2 == 0){
                    spawnMap.put((k * 101) +(i*3) , new SpawnDetails("Alien1", 100 + (i * 60), -100));
                }else{
                    spawnMap.put((k * 101) +(i*3) , new SpawnDetails("Alien1", 600 - (i * 60), -100));
                }
            }
        }
    }

    private void initBoard() {

    }

    public void start() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.white);

        timer = new Timer(1000 / 60, new GameCycle());
        timer.start();

        gameInit();
        initAudio();
    }

    public void stop() {
        timer.stop();
        try {
            if (audioPlayer != null) {
                audioPlayer.stop();
            }
        } catch (Exception e) {
            System.err.println("Error closing audio player.");
        }
    }

    private void gameInit() {

        enemies = new ArrayList<>();
        powerups = new ArrayList<>();
        explosions = new ArrayList<>();
        shots = new ArrayList<>();

        // for (int i = 0; i < 4; i++) {
        // for (int j = 0; j < 6; j++) {
        // var enemy = new Enemy(ALIEN_INIT_X + (ALIEN_WIDTH + ALIEN_GAP) * j,
        // ALIEN_INIT_Y + (ALIEN_HEIGHT + ALIEN_GAP) * i);
        // enemies.add(enemy);
        // }
        // }
        player = new Player();
        // shot = new Shot();
    }

    private void drawMap(Graphics g) {
        // Draw scrolling starfield background

        // Calculate smooth scrolling offset (1 pixel per frame)
        int scrollOffset = (frame) % BLOCKHEIGHT;

        // Calculate which rows to draw based on screen position
        int baseRow = (frame) / BLOCKHEIGHT;
        int rowsNeeded = (BOARD_HEIGHT / BLOCKHEIGHT) + 2; // +2 for smooth scrolling

        // Loop through rows that should be visible on screen
        for (int screenRow = 0; screenRow < rowsNeeded; screenRow++) {
            // Calculate which MAP row to use (with wrapping)
            int mapRow = (baseRow + screenRow) % MAP.length;

            // Calculate Y position for this row
            // int y = (screenRow * BLOCKHEIGHT) - scrollOffset;
            int y = BOARD_HEIGHT - ( (screenRow * BLOCKHEIGHT) - scrollOffset );

            // Skip if row is completely off-screen
            if (y > BOARD_HEIGHT || y < -BLOCKHEIGHT) {
                continue;
            }

            // Draw each column in this row
            for (int col = 0; col < MAP[mapRow].length; col++) {
                if (MAP[mapRow][col] == 1) {
                    // Calculate X position
                    int x = col * BLOCKWIDTH;

                    // Draw a cluster of stars
                    drawStarCluster(g, x, y, BLOCKWIDTH, BLOCKHEIGHT);
                }
            }
        }

    }

    private void drawStarCluster(Graphics g, int x, int y, int width, int height) {
        // Set star color to white
        g.setColor(Color.WHITE);

        // Draw multiple stars in a cluster pattern
        // Main star (larger)
        int centerX = x + width / 2;
        int centerY = y + height / 2;
        g.fillOval(centerX - 2, centerY - 2, 4, 4);

        // Smaller surrounding stars
        g.fillOval(centerX - 15, centerY - 10, 2, 2);
        g.fillOval(centerX + 12, centerY - 8, 2, 2);
        g.fillOval(centerX - 8, centerY + 12, 2, 2);
        g.fillOval(centerX + 10, centerY + 15, 2, 2);

        // Tiny stars for more detail
        g.fillOval(centerX - 20, centerY + 5, 1, 1);
        g.fillOval(centerX + 18, centerY - 15, 1, 1);
        g.fillOval(centerX - 5, centerY - 18, 1, 1);
        g.fillOval(centerX + 8, centerY + 20, 1, 1);
    }

    private void drawAliens(Graphics g) {
        for (Enemy enemy : enemies) {
    
            if (enemy.isVisible()) {
                if (enemy instanceof BossEnemy) {
                    // Shift boss image to the left by 20 pixels
                    g.drawImage(enemy.getImage(), enemy.getX() - 20, enemy.getY(), this);
                } else {
                    g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
                }
            }
    
            if (enemy.isDying()) {
                enemy.die();
            }
        }
    }
    

    private void drawPowreUps(Graphics g) {

        for (PowerUp p : powerups) {

            if (p.isVisible()) {

                g.drawImage(p.getImage(), p.getX(), p.getY(), this);
            }

            if (p.isDying()) {

                p.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {

            player.die();
            inGame = false;
        }
    }

    private void drawShot(Graphics g) {

        for (Shot shot : shots) {

            if (shot.isVisible()) {
                g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            }
        }
    }

    private void drawBombing(Graphics g) {

        // for (Enemy e : enemies) {
        //     Enemy.Bomb b = e.getBomb();
        //     if (!b.isDestroyed()) {
        //         g.drawImage(b.getImage(), b.getX(), b.getY(), this);
        //     }
        // }
    }

    private void drawExplosions(Graphics g) {

        List<Explosion> toRemove = new ArrayList<>();

        for (Explosion explosion : explosions) {

            if (explosion.isVisible()) {
                g.drawImage(explosion.getImage(), explosion.getX(), explosion.getY(), this);
                explosion.visibleCountDown();
                if (!explosion.isVisible()) {
                    toRemove.add(explosion);
                }
            }
        }

        explosions.removeAll(toRemove);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        Color bgColor;
        if (frame < FADE_START_FRAME) {
            bgColor = Color.black;
        } else if (frame < FADE_START_FRAME + FADE_DURATION_FRAMES) {
            // Fade black to white
            float progress = (frame - FADE_START_FRAME) / (float) FADE_DURATION_FRAMES;
            int value = (int)(130 * progress);
            bgColor = new Color(value, value, value);
        } else if (frame < RED_FADE_START_FRAME) {
            bgColor = Color.gray;
        } else if (frame < RED_FADE_START_FRAME + RED_FADE_DURATION_FRAMES) {
            // Fade white to red
            float progress = (frame - RED_FADE_START_FRAME) / (float) RED_FADE_DURATION_FRAMES;
            int greenBlue = (int)(255 * (1 - progress));
            bgColor = new Color(255, greenBlue, greenBlue);
        } else {
            bgColor = Color.red;
        }

        g.setColor(bgColor);
        g.fillRect(0, 0, d.width, d.height);

        g.setColor(Color.white);
        g.drawString("FRAME: " + frame, 10, 10);

        g.setColor(Color.green);

        if (inGame) {

            drawMap(g);  // Draw background stars first
            drawExplosions(g);
            drawPowreUps(g);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        Image imgToShow = null;
        if ("Game won!".equals(message)) {
            imgToShow = winImage;
        } else {
            imgToShow = lossImage;
        }
        if (imgToShow != null) {
            g.drawImage(imgToShow, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, this);
        }
        // Optionally, still show the message as overlay text
        g.setColor(Color.white);
        var small = new Font("Helvetica", Font.BOLD, 32);
        var fontMetrics = this.getFontMetrics(small);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2, BOARD_HEIGHT / 2);
    }

    private void update() {


        // Check enemy spawn
        // TODO this approach can only spawn one enemy at a frame
        SpawnDetails sd = spawnMap.get(frame);
        if (sd != null) {
            // Create a new enemy based on the spawn details
            switch (sd.type) {
                case "Alien1":
                    Enemy enemy = new Alien1(sd.x, sd.y);
                    enemies.add(enemy);
                    break;
                case "ZigZagEnemy":
                    Enemy zigzag = new ZigZagEnemy(sd.x, sd.y);
                    enemies.add(zigzag);
                    break;
                // Add more cases for different enemy types if needed
                case "Alien2":
                    // Enemy enemy2 = new Alien2(sd.x, sd.y);
                    // enemies.add(enemy2);
                    break;
                case "BossEnemy":
                    Enemy boss = new gdd.sprite.BossEnemy(sd.x, sd.y);
                    enemies.add(boss);
                    break;
                case "PowerUp-SpeedUp":
                    // Handle speed up item spawn
                    PowerUp speedUp = new SpeedUp(sd.x, sd.y);
                    powerups.add(speedUp);
                    break;
                default:
                    System.out.println("Unknown enemy type: " + sd.type);
                    break;
            }
        }

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        player.act();

        // Power-ups
        List<PowerUp> powerupsToRemove = new ArrayList<>();
        for (PowerUp powerup : powerups) {
            if (powerup.isVisible()) {
                powerup.act();
                if (powerup.collidesWith(player)) {
                    powerup.upgrade(player);
                    powerupsToRemove.add(powerup);
                }
            } else {
                powerupsToRemove.add(powerup);
            }
        }
        powerups.removeAll(powerupsToRemove);

        // Enemies
        for (Enemy enemy : enemies) {
            if (enemy.isVisible()) {
                enemy.act(direction);
                if (enemy instanceof gdd.sprite.BossEnemy) {
                    gdd.sprite.BossEnemy boss = (gdd.sprite.BossEnemy) enemy;
                    if (boss.canShoot()) {
                        shots.add(boss.createShot());
                        boss.resetShootCooldown();
                    }
                }
                // Player-enemy collision (do not damage boss here)
                int playerX = player.getX();
                int playerY = player.getY();
                int enemyX = enemy.getX();
                int enemyY = enemy.getY();
                if (player.isVisible()
                    && playerX < enemyX + ALIEN_WIDTH
                    && playerX + PLAYER_WIDTH > enemyX
                    && playerY < enemyY + ALIEN_HEIGHT
                    && playerY + PLAYER_HEIGHT > enemyY) {
                    var ii = new ImageIcon(IMG_EXPLOSION);
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    // Do NOT damage boss here
                }
            }
        }

        // shot
        List<Shot> shotsToRemove = new ArrayList<>();
        for (Shot shot : shots) {

            if (shot.isVisible()) {
                shot.act();
                int shotX = shot.getX();
                int shotY = shot.getY();

                for (Enemy enemy : enemies) {
                    // Collision detection: shot and enemy
                    int enemyX = enemy.getX();
                    int enemyY = enemy.getY();

                    if (shot.getDirection() == -1 && enemy.isVisible() && shot.isVisible()
                        && shotX >= (enemyX)
                        && shotX <= (enemyX + ALIEN_WIDTH)
                        && shotY >= (enemyY)
                        && shotY <= (enemyY + ALIEN_HEIGHT)) {
                        var ii = new ImageIcon(IMG_EXPLOSION);
                        enemy.setImage(ii.getImage());
                        if (enemy instanceof gdd.sprite.BossEnemy) {
                            ((gdd.sprite.BossEnemy)enemy).takeDamage(1);
                            if (enemy.isDying()) {
                                explosions.add(new Explosion(enemyX, enemyY));
                                deaths++;
                            }
                        } else {
                            enemy.setDying(true);
                            explosions.add(new Explosion(enemyX, enemyY));
                            deaths++;
                        }
                        shot.die();
                        shotsToRemove.add(shot);
                    }
                }
                

                int y = shot.getY();
                // y -= 4;
                // Remove shot if out of bounds
                if (shot.getDirection() == -1 && y < 0) {
                    shot.die();
                    shotsToRemove.add(shot);
                } else if (shot.getDirection() == 1 && y > BOARD_HEIGHT) {
                    shot.die();
                    shotsToRemove.add(shot);
                } else if (shot.getDirection() == 1) {
                    // Boss shot: check collision with player
                    int playerX = player.getX();
                    int playerY = player.getY();
                    if (player.isVisible()
                        && shotX >= playerX
                        && shotX <= playerX + PLAYER_WIDTH
                        && shotY >= playerY
                        && shotY <= playerY + PLAYER_HEIGHT) {
                        var ii = new ImageIcon(IMG_EXPLOSION);
                        player.setImage(ii.getImage());
                        player.setDying(true);
                        shot.die();
                        shotsToRemove.add(shot);
                    }
                }
            }
        }
        shots.removeAll(shotsToRemove);

        // Check if boss is defeated for game win
        boolean bossDefeated = false;
        for (Enemy enemy : enemies) {
            if (enemy instanceof gdd.sprite.BossEnemy && enemy.isDying()) {
                bossDefeated = true;
            }
        }
        if (bossDefeated && bossWinTimer < 0) {
            bossWinTimer = 100; // 5 seconds at 60 FPS
        }
        if (bossWinTimer >= 0) {
            bossWinTimer--;
            if (bossWinTimer == 0) {
                inGame = false;
                timer.stop();
                message = "Game won!";
            }
        }

        // enemies
        // for (Enemy enemy : enemies) {
        //     int x = enemy.getX();
        //     if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
        //         direction = -1;
        //         for (Enemy e2 : enemies) {
        //             e2.setY(e2.getY() + GO_DOWN);
        //         }
        //     }
        //     if (x <= BORDER_LEFT && direction != 1) {
        //         direction = 1;
        //         for (Enemy e : enemies) {
        //             e.setY(e.getY() + GO_DOWN);
        //         }
        //     }
        // }
        // for (Enemy enemy : enemies) {
        //     if (enemy.isVisible()) {
        //         int y = enemy.getY();
        //         if (y > GROUND - ALIEN_HEIGHT) {
        //             inGame = false;
        //             message = "Invasion!";
        //         }
        //         enemy.act(direction);
        //     }
        // }
        // bombs - collision detection
        // Bomb is with enemy, so it loops over enemies
        /*
        for (Enemy enemy : enemies) {

            int chance = randomizer.nextInt(15);
            Enemy.Bomb bomb = enemy.getBomb();

            if (chance == CHANCE && enemy.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(enemy.getX());
                bomb.setY(enemy.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()
                    && bombX >= (playerX)
                    && bombX <= (playerX + PLAYER_WIDTH)
                    && bombY >= (playerY)
                    && bombY <= (playerY + PLAYER_HEIGHT)) {

                var ii = new ImageIcon(IMG_EXPLOSION);
                player.setImage(ii.getImage());
                player.setDying(true);
                bomb.setDestroyed(true);
            }

            if (!bomb.isDestroyed()) {
                bomb.setY(bomb.getY() + 1);
                if (bomb.getY() >= GROUND - BOMB_HEIGHT) {
                    bomb.setDestroyed(true);
                }
            }
        }
         */
    }

    private void doGameCycle() {
        frame++;
        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Scene2.keyPressed: " + e.getKeyCode());

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE && inGame) {
                System.out.println("Shots: " + shots.size());
                if (shots.size() < 4) {
                    // Create a new shot and add it to the list
                    Shot shot = new Shot(x, y);
                    shots.add(shot);
                }
            }

        }
    }
}
