package tile_interactive;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;

public class IT_DryTree extends Interacting_Tile{

    GamePanel gp;

    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;

        down1 = setup("/tiles_interactive/drytree", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon.type == typeAxe;
    }

    public Interacting_Tile getDestroyedForm() {
        return new IT_Trunk(gp, worldX / gp.tileSize, worldY / gp.tileSize);
    }

    public void playSe() {
        gp.playSE(11);
    }

    public Color getParticleColor() {
        return new Color(65,50,30);
    }

    public int getPaticleSize() {
        return 6;
    }

    public int getParticleSpeed() {
        return 1;
    }

    public int getParticleMaxLife() {
        return 20;
    }

}
