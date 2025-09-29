package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Shield_Wood extends Entity {

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        name = "Wood Shield";
        type = typeShield;
        descrption = "["+ name + "]\nUn vecchio Scudo";

        down1 = setup("/objects/shield_wood",gp.tileSize,gp.tileSize);
        defenseValue = 1;
    }

}
