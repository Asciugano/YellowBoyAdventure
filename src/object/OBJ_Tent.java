package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Tent extends Entity {
    GamePanel gp;
    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeConsumable;
        name = "Tent";
        down1 = setup("/objects/tent", gp.tileSize, gp.tileSize);
        descrption = "[Tent]\nUsala per dormire\n...fino a domani mattina";
        price = 300;
        stackable = true;
    }

    public boolean use(Entity entity) {
        gp.gameState = gp.sleepState;
        gp.playSE(14);

        gp.player.mana = gp.player.maxMana;
        if(gp.player.life >= gp.player.maxLife / 2)
            gp.player.life = gp.player.maxLife;
        else
            gp.player.life += gp.player.maxLife / 2;

        gp.player.getSleepingImage(down1);

        return true;
    }
}