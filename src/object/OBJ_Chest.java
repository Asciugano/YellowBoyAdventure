package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Chest extends Entity {

    GamePanel gp;
    Entity loot;
    boolean opened = false;

    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;

        type = type_obstacle;
        name = "Chest";

        image = setup("/objects/chest",gp.tileSize,gp.tileSize);
        image2 = setup("/objects/chest_opened",gp.tileSize,gp.tileSize);

        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void interact() {
        gp.gameState = gp.dialogueState;

        if(!opened) {
            gp.playSE(3);

            StringBuilder sb = new StringBuilder();
            sb.append("Hai aperto un fonziere e hai trovato: " + loot.name + "!");

            if(!gp.player.canObtainItem(loot)) {
                sb.append("\n... Ma non puoi piú portare nulla");
            } else {
                sb.append("\n...Hai ottenuto: " + loot.name + "!");
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        } else {
            gp.ui.currentDialogue = "Il forziere é vuoto";
        }
    }
}