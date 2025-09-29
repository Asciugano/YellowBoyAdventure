package entity;

import Main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp){
        super(gp);

        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setup("/NPC/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/NPC/oldman_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/NPC/oldman_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/NPC/oldman_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/NPC/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/NPC/oldman_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/NPC/oldman_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/NPC/oldman_right_2",gp.tileSize,gp.tileSize);

    }

    public void update() {
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if(onPath && tileDistance > 7) {
            onPath = false;
        }
    }

    public void setAction() {

        if(onPath) {
//            int goalCol = 12;
//            int goalRow = 9;
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter ++;

            if(actionLockCounter == 120){
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if(i <= 25){
                    direction = "up";
                }
                if(i > 25 && i <= 50){
                    direction = "down";
                }
                if(i > 50 && i <= 75){
                    direction = "left";
                }
                if(i > 75 && i <= 100){
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }

    public void setDialogue() {
        dialogues[0] = "Buongiorno giovanotto";
        dialogues[1] = "Sei venuto su quest'isola \nper trovare il tesoro?";
        dialogues[2] = "Una volta ero un grande mago...\nma adesso sono troppo vecchio...\nper queste avventure...";
        dialogues[3] = "...Non importa, buona fortuna";

    }

    public void speak() {
        super.speak();
        onPath = true;
    }

}
