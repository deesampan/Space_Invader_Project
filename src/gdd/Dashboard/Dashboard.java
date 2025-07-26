package gdd.Dashboard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import gdd.sprite.Player;

public class Dashboard {
    private int score;
    private Player player;
    private Font scoreFont;
    private Font statusFont;
    
    public Dashboard(Player player) {
        this.player = player;
        this.score = 0;
        this.scoreFont = new Font("Arial", Font.BOLD, 24);
        this.statusFont = new Font("Arial", Font.BOLD, 16);
    }
    
    public void draw(Graphics g, int boardWidth, int boardHeight) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawScore(g, boardWidth);
        drawPlayerStats(g, boardHeight);
    }
    
    private void drawScore(Graphics g, int boardWidth) {
        g.setFont(scoreFont);
        g.setColor(Color.WHITE);
        
        String scoreText = "SCORE: " + score;
        int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, boardWidth - scoreWidth - 20, 30);

        g.setColor(Color.BLACK);
        g.drawString(scoreText, boardWidth - scoreWidth - 19, 31);
        g.setColor(Color.WHITE);
        g.drawString(scoreText, boardWidth - scoreWidth - 20, 30);
    }
    
    private void drawPlayerStats(Graphics g, int boardHeight) {
        g.setFont(statusFont);

        // Draw Speed text - moved up more
        g.setColor(Color.CYAN);
        String speedText = "SPEED: " + player.getSpeed();
        g.drawString(speedText, 20, boardHeight - 80);

        g.setColor(Color.BLACK);
        g.drawString(speedText, 21, boardHeight - 79);
        g.setColor(Color.CYAN);
        g.drawString(speedText, 20, boardHeight - 80);

        // Draw Shot text - moved up more
        g.setColor(Color.YELLOW);
        String shotText = "SHOTS: " + player.getShotLevel();
        g.drawString(shotText, 20, boardHeight - 50);

        // g.setColor(Color.BLACK);
        // g.drawString(shotText, 21, boardHeight - 49);
        // g.setColor(Color.YELLOW);
        // g.drawString(shotText, 20, boardHeight - 50);

        // Draw visual indicators - moved up
        drawSpeedBar(g, boardHeight);
        drawShotIndicator(g, boardHeight);
    }
    
    private void drawSpeedBar(Graphics g, int boardHeight) {
        // Speed bar background - moved up by 15 pixels
        g.setColor(Color.DARK_GRAY);
        g.fillRect(120, boardHeight - 85, 100, 8);
        
        // Speed bar fill (speed ranges from 2 to 18, so normalize it)
        int maxSpeed = 18;
        int minSpeed = 2;
        float speedPercent = (float)(player.getSpeed() - minSpeed) / (maxSpeed - minSpeed);
        int fillWidth = (int)(100 * speedPercent);
        
        // Color gradient based on speed
        if (speedPercent < 0.5f) {
            g.setColor(Color.GREEN);
        } else if (speedPercent < 0.8f) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(120, boardHeight - 85, fillWidth, 8);
        
        // Speed bar border
        g.setColor(Color.WHITE);
        g.drawRect(120, boardHeight - 85, 100, 8);
    }
    
    private void drawShotIndicator(Graphics g, int boardHeight) {
        // Shot level indicators (circles) - moved up by 15 pixels
        int maxShots = 4;
        int currentShots = player.getShotLevel();
        
        for (int i = 1; i <= maxShots; i++) {
            int x = 120 + (i - 1) * 20;
            int y = boardHeight - 60; // Moved up from -45 to -60
            
            if (i <= currentShots) {
                // Filled circle for active shot levels
                g.setColor(Color.YELLOW);
                g.fillOval(x, y, 12, 12);
                g.setColor(Color.ORANGE);
                g.drawOval(x, y, 12, 12);
            } else {
                // Empty circle for inactive shot levels
                g.setColor(Color.DARK_GRAY);
                g.fillOval(x, y, 12, 12);
                g.setColor(Color.GRAY);
                g.drawOval(x, y, 12, 12);
            }
        }
    }
    
    // Score management methods
    public void addScore(int points) {
        this.score += points;
    }
    
    public void addEnemyKillScore(String enemyType) {
        switch (enemyType) {
            case "Alien1":
                addScore(1);
                break;
            case "ZigZagEnemy":
                addScore(1);
                break;
            case "BossEnemy":
                addScore(10);
                break;
            default:
                addScore(1);
                break;
        }
    }
    
    public int getScore() {
        return score;
    }
    
    public void resetScore() {
        this.score = 0;
    }
}