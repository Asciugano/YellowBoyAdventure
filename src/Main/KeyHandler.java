package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed,shootKeyPressed;

    boolean checkDrawTime = false;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {

        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        else if(gp.gameState == gp.playState) {
            playState(code);
        }
        else if(gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        else if(gp.gameState == gp.characterState) {
            characterState(code);
        }
        else if(gp.gameState == gp.optionState) {
            optionState(code);
        }
        if(gp.gameState == gp.gameOverState) {
            gameOverSate(code);
        }
        if(gp.gameState == gp.tradeSate) {
            tradeState(code);
        }

        if(gp.ui.subState == 1) {
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE)
                gp.ui.subState = 0;
        }
        if(gp.ui.subState == 2) {
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE)
                gp.ui.subState = 0;
        }

    }

    public void titleState(int code){

        if (gp.ui.titleScreenState == 0) {

            if (code == KeyEvent.VK_UP) {
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 3;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 3) {
                    gp.ui.commandNumber = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNumber) {
                    case 0:
                        gp.gameState = gp.playState;
                        gp.playMusic(0);
                        break;
                    case 1:
                        break;
                    case 2:
                        gp.ui.titleScreenState = 1;
                        break;
                    case 3:
                        System.exit(0);
                        break;
                }
            }
        }
        if(gp.ui.titleScreenState == 1){
            if(code == KeyEvent.VK_UP){
                gp.ui.commandNumber--;
                if (gp.ui.commandNumber < 0) {
                    gp.ui.commandNumber = 1;
                }
            }
            if (code == KeyEvent.VK_DOWN) {
                gp.ui.commandNumber++;
                if (gp.ui.commandNumber > 1) {
                    gp.ui.commandNumber = 0;
                }
            }
            if(code == KeyEvent.VK_LEFT){
                //cambia personaggio
            }
            if(code == KeyEvent.VK_RIGHT){
                //cambia personaggio
            }
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNumber) {
                    case 0:
                        System.out.println("hai cambiatio il personaggio");
                        gp.ui.titleScreenState = 0;
                        break;
                    case 1:
                        gp.ui.titleScreenState = 0;
                        break;
                }
            }
        }
    }

    public void playState(int code){

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_E) {
            enterPressed = true;
        }
        if(code == KeyEvent.VK_F){
            shootKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionState;
        }

        if (code == KeyEvent.VK_T) {
            if (!checkDrawTime) {
                checkDrawTime = true;
            } else if (checkDrawTime) {
                checkDrawTime = false;
            }
        }
        if(code == KeyEvent.VK_R) {
            String path = "";
            switch(gp.currentMap) {
                case 0: path = "/maps/worldV3.txt"; break;
                case 1: path = "/maps/interior01.txt"; break;
            }
            gp.tileM.loadMap(path, gp.currentMap);
            System.out.println("Mappa ricaricata");
        }
    }

    public void pauseState(int code){

        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }

    }

    public void dialogueState(int code){

        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if(gp.gameState == gp.characterState) {
            if(code == KeyEvent.VK_C){
                gp.gameState = gp.playState;
            }
            if(code == KeyEvent.VK_ENTER){
                gp.player.selectItem();
            }
            playerInventory(code);
        }
    }

    public void optionState(int code) {
        if (code == KeyEvent.VK_ESCAPE)
            gp.gameState = gp.playState;
        if (code == KeyEvent.VK_ENTER)
            gp.player.selectItem();
        if(code == KeyEvent.VK_LEFT)
            if(gp.ui.subState == 0) {
                if (gp.ui.commandNumber == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNumber == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        if(code == KeyEvent.VK_RIGHT) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNumber == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if (gp.ui.commandNumber == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(9);
                }
            }
        }

        int maxCommandNum = switch (gp.ui.subState) {
            case 0 -> 5;
            case 3 -> 1;
            default -> 0;
        };
        if (code == KeyEvent.VK_UP) {
            gp.ui.commandNumber--;
            gp.playSE(9);

            if (gp.ui.commandNumber < 0)
                gp.ui.commandNumber = maxCommandNum;
        }
        if (code == KeyEvent.VK_DOWN) {
            gp.ui.commandNumber++;
            gp.playSE(9);

            if (gp.ui.commandNumber > maxCommandNum)
                gp.ui.commandNumber = 0;
        }

    }

    public void gameOverSate(int code){
        if(code == KeyEvent.VK_ENTER) {
            gp.restart();
            gp.gameState = gp.titleState;
        }
        if(code == KeyEvent.VK_SPACE) {
            gp.retry();
            gp.stopMusic();
            gp.playMusic(0);
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ESCAPE)
            System.exit(0);
    }

    public void tradeState(int code) {
        if(code == KeyEvent.VK_UP) {
            gp.ui.commandNumber--;
            if(gp.ui.commandNumber < 0)
                gp.ui.commandNumber = 2;
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_DOWN) {
            gp.ui.commandNumber++;
            if(gp.ui.commandNumber > 2)
                gp.ui.commandNumber = 0;
            gp.playSE(9);
        }

        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    public void playerInventory(int code) {
        if(code == KeyEvent.VK_UP){
            gp.ui.playerSlotRow --;
            if(gp.ui.playerSlotRow < 0){
                gp.ui.playerSlotRow = 3;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_DOWN){
            gp.ui.playerSlotRow ++;
            if(gp.ui.playerSlotRow > 3){
                gp.ui.playerSlotRow = 0;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_LEFT){
            gp.ui.playerSlotCol --;
            if(gp.ui.playerSlotCol < 0){
                gp.ui.playerSlotCol = 4;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_RIGHT){
            gp.ui.playerSlotCol ++;
            if(gp.ui.playerSlotCol > 4){
                gp.ui.playerSlotCol = 0;
            }
            gp.playSE(9);
        }
    }

    public void npcInventory(int code) {
        if(code == KeyEvent.VK_UP){
            gp.ui.npcSlotRow --;
            if(gp.ui.npcSlotRow < 0){
                gp.ui.npcSlotRow = 3;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_DOWN){
            gp.ui.npcSlotRow ++;
            if(gp.ui.npcSlotRow > 3){
                gp.ui.npcSlotRow = 0;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_LEFT){
            gp.ui.npcSlotCol --;
            if(gp.ui.npcSlotCol < 0){
                gp.ui.npcSlotCol = 4;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_RIGHT){
            gp.ui.npcSlotCol ++;
            if(gp.ui.npcSlotCol > 4){
                gp.ui.npcSlotCol = 0;
            }
            gp.playSE(9);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shootKeyPressed = false;
        }
    }
}
