package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {
        super(gp);

        name = "Key";
        descrption = "["+ name + "]\nUna Chiave...\nchissà cosa aprirà?";
        price = 20;

        down1 = setup("/objects/key",gp.tileSize,gp.tileSize);
    }

}
