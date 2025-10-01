package entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
            attackRight2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    public String[] dialogues = new String[20];
    int dialogueIndex = 0;

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int typePlayer = 0;
    public final int typeNpc = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeAxe = 4;
    public final int typeShield = 5;
    public final int typeConsumable = 6;
    public final int typePickupOnly = 7;

    public int maxLife;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public int price;

    public String descrption = null;

    public int actionLockCounter;

    public boolean invincible = false;
    public int invincibleCounter = 0;

    public boolean attacking = false;

    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter = 0;

    public boolean hpBarOn = false;
    public int hpBarCounter = 0;

    public int maxMana;
    public int mana;
    public int useCost;
    public Projectile projectile;
    public int shotAvailableCounter;
    public int ammo;

    public boolean onPath = false;
    public boolean knockBack = false;
    public int defaultSpeed;
    int knockBackCounter = 0;
    public int knockBackPower = 0;
    public final int type_obstacle = 8;

    public boolean stackable = false;
    public int amount = 1;
    public static int maxAmount = 16;

    GamePanel gp;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool ut = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = ut.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        BufferedImage image = null;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                    break;
                case "down":
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                    break;
                case "left":
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                    break;
                case "right":
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                    break;
            }

            if (type == typeMonster && hpBarOn) {

                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 11, gp.tileSize + 2, 7);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 10, (int) hpBarValue, 5);

                hpBarCounter++;

                if (hpBarCounter > 360) {
                    hpBarOn = false;
                    hpBarCounter = 0;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.5f);
            }
            if (dying) {
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, null);
            changeAlpha(g2, 1);
        }
    }

    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i)
            changeAlpha(g2, 0);
        if (dyingCounter > i && dyingCounter <= i * 2)
            changeAlpha(g2, 1);
        if (dyingCounter > i * 2 && dyingCounter <= i * 3)
            changeAlpha(g2, 0);
        if (dyingCounter > i * 3 && dyingCounter <= i * 4)
            changeAlpha(g2, 1);
        if (dyingCounter > i * 4 && dyingCounter <= i * 5)
            changeAlpha(g2, 0);
        if (dyingCounter > i * 5 && dyingCounter <= i * 6)
            changeAlpha(g2, 1);
        if (dyingCounter > i * 6 && dyingCounter <= i * 7)
            changeAlpha(g2, 0);
        if (dyingCounter > i * 7 && dyingCounter <= i * 8)
            changeAlpha(g2, 1);
        if (dyingCounter > i * 8) {

            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public void setAction() {
    }

    public void damageReaction() {
    }

    public  void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkOBJ(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == typeMonster && contactPlayer) {
            damagePlayer(attack);
        }
    }
    public void update() {

        if(knockBack) {
            checkCollision();

            if(collisionOn) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            knockBackCounter++;
            if(knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        } else {
            setAction();
            checkCollision();

            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;

                }
            }
        }
        spriteCounter++;
        if (spriteCounter > 24) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincibleCounter = 0;
                invincible = false;
            }
        }
        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack) {
        if (!gp.player.invincible) {
            gp.playSE(6);

            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
            case "down":
                direction = "up";
                break;
        }
    }
    public void interact() {}

    public boolean use(Entity entity) { return false; }

    public void checkDrop() {
    }

    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    public Color getParticleColor() {
        return null;
    }

    public int getPaticleSize() {
        return 0;
    }

    public int getParticleSpeed() {
        return 0;
    }

    public int getParticleMaxLife() {
        return 0;
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getPaticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        gp.particleList.add(new Particle(gp, target, color, size, speed, maxLife, -2, -1));
        gp.particleList.add(new Particle(gp, target, color, size, speed, maxLife, 2, -1));
        gp.particleList.add(new Particle(gp, target, color, size, speed, maxLife, -2, 1));
        gp.particleList.add(new Particle(gp, target, color, size, speed, maxLife, 2, 1));
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNode(startCol, startRow, goalCol, goalRow, this);

        if(gp.pFinder.search()) {
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if(enLeftX > nextX) {
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }
            }

//          int nextCol = gp.pFinder.pathList.get(0).col;
//          int nextRow = gp.pFinder.pathList.get(0).row;
//          if(nextRow == goalCol && nextCol == goalCol) {
//              onPath = false;
//          }
        }
    }

    public int getDetected(Entity user, Entity target[][], String targetName) {
        int index = -1;

        int nextWorldX = user.getLeftX() ;
        int nextWorldY = user.getTopY();

        switch(user.direction) {
            case "up":
                nextWorldY = user.getTopY() - 1;
                break;
            case "down":
                nextWorldY = user.getBottomY() + 1;
                break;
            case "left":
                nextWorldX = user.getLeftX() - 1;
                break;
            case "right":
                nextWorldX = user.getRightX() + 1;
                break;
        }
        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        for(int i = 0; i < target[1].length; i++) {
            if(target[gp.currentMap][i] != null) {
                if(
                        target[gp.currentMap][i].getCol() == col &&
                        target[gp.currentMap][i].getRow() == row &&
                        target[gp.currentMap][i].name.equals(targetName)
                ) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }

    public int getLeftX() {
        return worldX + solidArea.x;
    }

    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY() {
        return worldY + solidArea.y;
    }
    public int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }

    public int getCol() {
        return (worldX + solidArea.x) / gp.tileSize;
    }
    public int getRow() {
        return (worldY + solidArea.y) / gp.tileSize;
    }
}