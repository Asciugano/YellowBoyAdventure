package Main;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.Interacting_Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize;
    public final int screenHeight = maxScreenRow * tileSize;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = maxWorldCol * tileSize;
    public final int worldHeight = maxWorldRow * tileSize;
    public final int maxMap = 10;
    public int currentMap = 0;

    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    int FPS = 60;

    public TileManager tileM = new TileManager(this);

    public KeyHandler keyH = new KeyHandler(this);

    Sound music = new Sound();
    Sound se = new Sound();

    public UI ui = new UI(this);

    public EventHandler eHandler = new EventHandler(this);

    Thread gameThread;

    public CollisionChecker cChecker = new CollisionChecker(this);

    public AssetSetter aSetter = new AssetSetter(this);

    public Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);

    public Player player = new Player(this, keyH);
    public Entity[][] obj = new Entity[maxMap][20];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public Interacting_Tile iTile[][] = new Interacting_Tile[maxMap][50];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeSate = 8;


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNpc();
        aSetter.setMonster();
        aSetter.setInteractiveTiles();

        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth2, screenHeight2, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();
    }

    public void retry() {
        player.restoreLifeMana();
        player.setDefaultPosition();
        aSetter.setNpc();
        aSetter.setMonster();
    }

    public void restart() {
        player.setDefaultValues();
        player.setItems();
        aSetter.setObject();
        aSetter.setNpc();
        aSetter.setMonster();
        aSetter.setInteractiveTiles();
        music.stop();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
//                drawToTempScreen();
//                drawToScreen();
//                if(fullScreenOn)
//                    setFullScreen();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }

    }

    public void update() {

        if (gameState == playState) {
            player.update();
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            for(int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying){
                        monster[currentMap][i].update();
                    }
                    if(!monster[currentMap][i].alive){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    if(projectileList.get(i).alive){
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive){
                        projectileList.remove(i);
                    }
                }

            }
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive){
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive){
                        particleList.remove(i);
                    }
                }

            }
            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null)
                    iTile[currentMap][i].update();
            }
        }
        if (gameState == pauseState) {
            //nothing
        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.dispose();
    }

    public void drawToTempScreen() {
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        if (gameState == titleState) {
            ui.draw(g2);
        }
        else {
            tileM.draw(g2);

            for(int i = 0; i < iTile.length; i++)
                if(iTile[1][i] != null)
                    iTile[currentMap][i].draw(g2);

            entityList.add(player);
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for(int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            for (Entity p : projectileList){
                if (p != null) {
                    entityList.add(p);
                }
            }

            for(Entity p : particleList)
                if(p != null) {
                    entityList.add(p);
                }

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            entityList.clear();

        }

        ui.draw(g2);

        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            System.out.println("tempo per disegnare: " + passed);
            g2.setColor(Color.white);
            g2.drawString("tempo per disegnare: " + passed, 10, 400);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        if (gameState == titleState) {
            ui.draw(g2);
        }
        else {
            tileM.draw(g2);

            for(int i = 0; i < iTile[0].length; i++)
                if(iTile[currentMap][i] != null)
                    iTile[currentMap][i].draw(g2);

            entityList.add(player);
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for(int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }

            for (Entity p : projectileList){
                if (p != null) {
                    entityList.add(p);
                }
            }

            for(Entity p : particleList)
                if(p != null) {
                    entityList.add(p);
                }

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            entityList.clear();

        }

        ui.draw(g2);

        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            System.out.println("tempo per disegnare: " + passed);
            g2.setColor(Color.white);
            g2.drawString("tempo per disegnare: " + passed, 10, 400);
        }
        g2.dispose();
    }

//    public void drawToScreen() {
//        Graphics g = getGraphics();
//        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeigth2, null);
//        g.dispose();
//    }
//
//    public void drawToTempScreen() {
//        long drawStart = 0;
//        if (keyH.checkDrawTime) {
//            drawStart = System.nanoTime();
//        }
//
//        if (gameState == titleState) {
//            ui.draw(g2);
//        }
//        else {
//            tileM.draw(g2);
//
//            for(int i = 0; i < iTile.length; i++)
//                if(iTile[i] != null)
//                    iTile[i].draw(g2);
//
//            entityList.add(player);
//            for (Entity e : npc) {
//                if (e != null) {
//                    entityList.add(e);
//                }
//            }
//            for (Entity e : obj) {
//                if (e != null) {
//                    entityList.add(e);
//                }
//            }
//            for(Entity e : monster) {
//                if (e != null) {
//                    entityList.add(e);
//                }
//            }
//
//            for (Entity p : projectileList){
//                if (p != null) {
//                    entityList.add(p);
//                }
//            }
//
//            for(Entity p : particleList)
//                if(p != null) {
//                    entityList.add(p);
//                }
//
//            Collections.sort(entityList, new Comparator<Entity>() {
//                @Override
//                public int compare(Entity e1, Entity e2) {
//                    return Integer.compare(e1.worldY, e2.worldY);
//                }
//            });
//
//            for (int i = 0; i < entityList.size(); i++) {
//                entityList.get(i).draw(g2);
//            }
//            entityList.clear();
//
//        }
//
//        ui.draw(g2);
//
//        if (keyH.checkDrawTime) {
//            long drawEnd = System.nanoTime();
//            long passed = drawEnd - drawStart;
//
//            System.out.println("tempo per disegnare: " + passed);
//            g2.setColor(Color.white);
//            g2.drawString("tempo per disegnare: " + passed, 10, 400);
//        }
//    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public void setFullScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        gd.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

}
