package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp){
        super(gp);

        name = "Boots";

        price = 100;

        descrption = "[" + name + "] " + "Con questi \ncorrerai veloce \ncome il vento";

        down1 = setup("/objects/boots",gp.tileSize,gp.tileSize);
    }
}
