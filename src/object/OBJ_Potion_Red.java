package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = typeConsumable;
        name = "Potion Red";
        value = 5;
        descrption = "[" + name + "]\nTi farà tornare un po di \nsalute";
        price = 50;

        down1 = setup("/objects/potion_red",gp.tileSize,gp.tileSize);
    }

    public boolean use(Entity entity) {

        gp.gameState = gp.dialogueState;

        gp.ui.currentDialogue = "Hai bevuto " + name + "\nhai recuperato " + value + " di vita";
        entity.life += value;

        gp.playSE(2);

        return true;
    }
}
