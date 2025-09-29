package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Axe extends Entity {

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        name = "Axe";
        type = typeAxe;
        descrption = "[" + name + "]\nUn'ascia...puoi usarla\n(non solo sugli alberi)";
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;

        price = 75;

        down1 = setup("/objects/axe",gp.tileSize,gp.tileSize);
    }
}
