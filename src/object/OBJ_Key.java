package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Key extends Entity {

    GamePanel gp;
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeConsumable;
        name = "Key";
        descrption = "["+ name + "]\nUna Chiave...\nchissà cosa aprirà?";
        price = 20;

        down1 = setup("/objects/key",gp.tileSize,gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.gameState = gp.dialogueState;

        int objIndex = getDetected(entity, gp.obj, "Door");
        if(objIndex != -1) {
            gp.ui.currentDialogue = "Hai usato la tua " + name + " é hai aperto la porta";
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;

            return true;
        } else {
            gp.ui.currentDialogue = "Cosa stai facendo esattamente?";
            return false;
        }
    }
}
