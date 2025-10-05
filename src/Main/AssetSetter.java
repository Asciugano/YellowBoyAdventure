package Main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.*;
import tile_interactive.IT_DryTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        int i = 0;

        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Tent(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 67;
        gp.obj[mapNum][i].worldY = gp.tileSize * 65;
        i++;

        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 37;
        gp.obj[mapNum][i].worldY = gp.tileSize * 62;
        i++;

        gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 36;
        gp.obj[mapNum][i].worldY = gp.tileSize * 43;
        i++;

        gp.obj[mapNum][i] = new OBJ_Lantern(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 79;
        gp.obj[mapNum][i].worldY = gp.tileSize * 52;
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 66;
        gp.obj[mapNum][i].worldY = gp.tileSize * 53;
        i++;
    }

    public void setNpc() {
        int mapNum = 0;
        int i = 0;

        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 21;
        gp.npc[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 52;
        gp.npc[mapNum][i].worldY = gp.tileSize * 46;
        i++;
    }

    public void setMonster() {
        int mapNum = 0;
        int i = 0;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 69;
        gp.monster[mapNum][i].worldY = gp.tileSize * 65;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 63;
        gp.monster[mapNum][i].worldY = gp.tileSize * 73;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 58;
        gp.monster[mapNum][i].worldY = gp.tileSize * 73;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 51;
        gp.monster[mapNum][i].worldY = gp.tileSize * 73;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 40;
        gp.monster[mapNum][i].worldY = gp.tileSize * 67;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 30;
        gp.monster[mapNum][i].worldY = gp.tileSize * 60;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 34;
        gp.monster[mapNum][i].worldY = gp.tileSize * 48;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 55;
        gp.monster[mapNum][i].worldY = gp.tileSize * 46;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 71;
        gp.monster[mapNum][i].worldY = gp.tileSize * 52;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 73;
        gp.monster[mapNum][i].worldY = gp.tileSize * 54;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 70;
        gp.monster[mapNum][i].worldY = gp.tileSize * 55;
        i++;
    }

    public void setInteractiveTiles() {
        int mapNum = 0;
        int i = 0;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 61, 58);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 62, 58);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 66, 63);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 66, 62);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 67, 62);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 68, 62);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 69, 62);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 70, 62);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 71, 62);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 72, 62);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 73, 62);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 74, 62);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 69, 63);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 69, 64);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 68, 64);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 71, 63);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 71, 64);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 72, 63);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 72, 64);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 73, 63);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 73, 64);
        i++;
    }
}
