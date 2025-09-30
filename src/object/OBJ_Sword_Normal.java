package object;

import Main.GamePanel;
import entity.Entity;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp){
        super(gp);

        name = "Normal Sword";
        type = typeSword;
        descrption = "["+ name + "]\nUna vecchia Spada";
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;

        price = 80;
        knockBackPower = 10;

        down1 = setup("/objects/sword_normal",gp.tileSize,gp.tileSize);

    }
}
