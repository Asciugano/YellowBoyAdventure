package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Shield_Blue extends Entity {

    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        name = "Blue Shield";
        type = typeShield;
        descrption = "[" + name + "]\nUn nuovo Scudo";
        defenseValue = 2;
        price = 250;

        down1 = setup("/objects/shield_blue",gp.tileSize,gp.tileSize);

    }
}
