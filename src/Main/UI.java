package Main;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;

    Font pixlelFont;

    BufferedImage heart_full,heart_half,heart_blank, crystal_full,crystal_blank, coin;

    //BufferedImage keyImage;

    public boolean messageOn = false;
    //    public String message = "";
//    int messageCounter = 0;
    ArrayList<String>message = new ArrayList<>();
    ArrayList<Integer>messageCounter = new ArrayList<>();

    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;

    public boolean gameFinished = false;

    public String currentDialogue = null;

    public int commandNumber = 0;

    public int titleScreenState = 0;

    //double playTime;
    //DecimalFormat df = new DecimalFormat("#0.00");

    int subState = 0;
    int counter = 0;

    public Entity npc;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/PixelifySans-Regular.ttf");
            pixlelFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;

        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
        //OBJ_Key key = new OBJ_Key(gp);
        //keyImage = key.image;

        //playTime = 0.0;
    }

//    public void showMessage(String text) {
//
//        message = text;
//        messageOn = true;
//
//    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(pixlelFont);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.white);

        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }
        if(gp.gameState == gp.optionState) {
            drawOptionScreen();
        }
        if(gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        if(gp.gameState == gp.transitionState) {
            drawTransition();
        }
        if(gp.gameState == gp.tradeSate) {
            drawTradeScreen();
        }

        /*if(gameFinished) {

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text = "Hai trovato il tesoro, Congratulazioni";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenHeight / 2 - (gp.tileSize * 3);

            g2.drawString(text, x, y);

            text = "Tempo: " + df.format(playTime) + " sec";
            textLength = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 4);

            g2.drawString(text, x, y);

            g2.setFont(arial_80);
            g2.setColor(Color.yellow);

            text = "HAI VINTO!!!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);

            g2.drawString(text, x, y);

            gp.gameThread = null;
        }
        else {

            g2.setFont(arial_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 76, 58);

            playTime += (double) 1/60;
            g2.drawString("Time: " + df.format(playTime), gp.tileSize * 12, 58);

            if (messageOn) {

                g2.setFont(g2.getFont().deriveFont(20f));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);

                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }*/
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        int messageX, messageY;
        String text = "GAME OVER";

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        g2.setColor(Color.black);
        messageX = getXForCenteredText(text);
        messageY = gp.screenHeight / 2;
        g2.drawString(text, messageX, messageY);
        g2.setColor(new Color(220, 0, 0));
        g2.drawString(text, messageX - 4, messageY - 4);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28f));
        text = "press ENTER to return to the title screen\nSPACE to retry\nESC to quit";
        messageY += gp.tileSize * 2;
        for(String line : text.split("\n")) {
            messageX = getXForCenteredText(line);
            messageY += 30;
            g2.drawString(line, messageX, messageY);
        }
    }

    public void drawMessage() {

        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,32f));

        for(int i = 0; i< message.size(); i++){

            if(message.get(i) != null){

                g2.setColor(Color.black);
                g2.drawString(message.get(i),messageX + 2,messageY + 2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i),messageX,messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i,counter);

                messageY += 50;

                if(messageCounter.get(i) >= 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawPlayerLife(){
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;

        int i = 0;

        while(i < gp.player.maxLife / 2){

            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;

        i = 0;
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while(i < gp.player.mana){
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawDialogueScreen() {

        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28f));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 35;
        }
    }

    public void drawCharacterScreen() {

        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 7;
        final int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 34;

        g2.drawString("Level: ", textX, textY);
        textY += lineHeight;
        g2.drawString("Life: ", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana: ", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength: ", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity: ", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack: ", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense: ", textX, textY);
        textY += lineHeight;
        g2.drawString("EXP: ", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level: ", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin: ", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon: ", textX, textY);
        textY += lineHeight + 5;
        g2.drawString("Shield: ", textX, textY);

        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value = null;

        value = String.valueOf(gp.player.level);
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.exp);
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        value = String.valueOf(gp.player.coin);
        textX = getXForAlignToRightText(value,tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;
        g2.drawImage(gp.player.currentWeapon.down1,textX - gp.tileSize / 2, textY - 24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1,textX - gp.tileSize / 2, textY - 24, null);

    }

    public void drawInventory(Entity entity, boolean cursor){

        int frameX = 0, frameY = 0;
        int frameWidth = 0, frameHeight = 0;
        int slotCol = 0, slotRow = 0;

        if(entity == gp.player) {
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;

            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;

            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart, slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        for (int i = 0; i < entity.inventory.size(); i++) {

            if(entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX,slotY,slotSize,slotSize,10,10);
            }

            g2.drawImage(entity.inventory.get(i).down1,slotX,slotY,null);

            slotX += slotSize;

            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        if(cursor) {
            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);

            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, slotSize, slotSize, 10,10);

            int dFrameY = frameY + frameHeight;
            int dFrameHeight = gp.tileSize * 3;

            int textX = frameX + 20,textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(20f));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if(itemIndex < entity.inventory.size()) {

                drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight);

                for(String line : entity.inventory.get(itemIndex).descrption.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    public void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));

        int frameX = gp.tileSize * 6, frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 10, frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState) {
            case 0: option_top(frameX, frameY); break;
            case 1: option_fullScreenNotification(frameX, frameY); break;
            case 2: option_controls(frameX, frameY); break;
            case 3: option_endGameConfirmation(frameX, frameY); break;
        }
        gp.keyH.enterPressed = false;
    }

    public void option_top(int frameX, int frameY) {
        int textX, textY;

        String text = "OPTIONS";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize + gp.tileSize / 2;
        g2.drawString("Full Screen", textX, textY);
        if(commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                gp.fullScreenOn = !gp.fullScreenOn;
                subState = 1;
            }
        }

        textY += gp.tileSize + gp.tileSize / 4;
        g2.drawString("Music", textX, textY);
        if(commandNumber == 1)
            g2.drawString(">", textX - 25, textY);

        textY += gp.tileSize + gp.tileSize / 4;
        g2.drawString("SE", textX, textY);
        if(commandNumber == 2)
            g2.drawString(">", textX - 25, textY);

        textY += gp.tileSize + gp.tileSize / 4;
        g2.drawString("Control", textX, textY);
        if(commandNumber == 3) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                subState = 2;
                commandNumber = 0;
            }
        }

        textY += gp.tileSize + gp.tileSize / 4;
        g2.drawString("Quit", textX, textY);
        if(commandNumber == 4) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                subState = 3;
                commandNumber = 0;
            }
        }

        textY += gp.tileSize + gp.tileSize / 2;
        g2.drawString("Back", textX, textY);
        if(commandNumber == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.playState;
                commandNumber = 0;
            }
        }

        //CheckBox
        textX = frameX + gp.tileSize * 6;
        textY = gp.tileSize * 3;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, gp.tileSize / 2, gp.tileSize / 2);
        if(gp.fullScreenOn)
            g2.fillRect(textX, textY, gp.tileSize / 2, gp.tileSize / 2);

        //Music
        textY += gp.tileSize + gp.tileSize / 4;
        g2.drawRect(textX, textY, 120, gp.tileSize / 2);
        int volumeWidth = 120 / 5 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, gp.tileSize / 2);

        //SE
        textY += gp.tileSize + gp.tileSize / 4;
        g2.drawRect(textX, textY, 120, gp.tileSize / 2);
        volumeWidth = 120 / 5 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, gp.tileSize / 2);

        gp.keyH.enterPressed = false;

        gp.config.saveConfig();
    }

    public void option_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize, textY = frameY + gp.tileSize;

        currentDialogue = "The change will \ntake effect \nafter restarting \nthe game";

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        textY += gp.tileSize * 5;
        g2.drawString("Back", textX, textY);
        if(commandNumber == 0) {
            g2.drawString(">", frameX + gp.tileSize / 3, textY);
            if(gp.keyH.enterPressed)
                subState = 0;
        }
    }

    public void option_controls(int frameX, int frameY) {
        int textX, textY;
        String text = "CONTROLS";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm", textX, textY); textY += gp.tileSize;
        g2.drawString("Attack/Dialogue", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Inventory", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Settings", textX, textY); textY += gp.tileSize;
        g2.drawString("Back", textX, textY);
        if(commandNumber == 0) {
            g2.drawString(">", frameX + gp.tileSize / 2, textY);
            if(gp.keyH.enterPressed) {
                subState = 0;
                commandNumber = 3;
            }
        }

        textX = frameX + gp.tileSize * 7;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("W-A-S-D", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("E", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;
    }

    public void option_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize, textY = frameY + gp.tileSize;

        currentDialogue = "Quit the game ad \nreturn to the \ntitle screen?";
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        String text = "YES";
        textX = getXForCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if(commandNumber == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                subState = 0;
                gp.gameState = titleScreenState;
            }
        }

        text = "NO";
        textX = getXForCenteredText(text);
        textY += gp.tileSize + gp.tileSize / 2;
        g2.drawString(text, textX, textY);
        if(commandNumber == 1) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed) {
                subState = 0;
                commandNumber = 4;
            }
        }
    }

    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0,0,0,counter * 5));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        if(counter == 50) {
            counter = 0;

            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.eHandler.tempCol * gp.tileSize;
            gp.player.worldY = gp.eHandler.tempRow * gp.tileSize;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }

    public void drawTradeScreen() {
        switch(subState) {
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
        }
        gp.keyH.enterPressed = false;
    }

    public void trade_select() {
        drawDialogueScreen();

        int x = gp.tileSize * 13, y = gp.tileSize * 5;
        int width = gp.tileSize * 4, height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        x += gp.tileSize + gp.tileSize / 2;
        y += gp.tileSize;

        g2.drawString("Buy", x, y);
        if(commandNumber == 0) {
            g2.drawString(">", x - 35, y);
            if(gp.keyH.enterPressed) {
                subState = 1;
            }
        }
        y += gp.tileSize;

        g2.drawString("Sell", x, y);
        if(commandNumber == 1) {
            g2.drawString(">", x - 35, y);
            if(gp.keyH.enterPressed) {
                subState = 2;
            }
        }
        y += gp.tileSize;

        g2.drawString("Leave", x, y);
        if(commandNumber == 2) {
            g2.drawString(">", x - 35, y);
            if(gp.keyH.enterPressed) {
                commandNumber = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Torna presto... he he he";
            }
        }
    }

    public void trade_buy() {
        drawInventory(gp.player, false);
        drawInventory(gp.npc[1][0], true);

        int x = gp.tileSize * 2, y = gp.tileSize * 9;
        int width = gp.tileSize * 6, height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);

        g2.drawString("[ESC] Back", x + 24, y + 60);

        x = gp.tileSize * 12;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()) {
            x = (int)(gp.tileSize * 5.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = ""+price;
            x = getXForAlignToRightText(text, gp.tileSize * 8 - 20);
            g2.drawString(text, x, y + 32);

            if(gp.keyH.enterPressed) {
                if(npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "Ti servono piú monete per questo articolo";
                    drawDialogueScreen();
                }
                else if(gp.player.inventory.size() == gp.player.maxInventorySize) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "Non puoi portare altri oggetti";
                }
                else {
                    gp.player.coin -= npc.inventory.get(itemIndex).price;
                    gp.player.inventory.add(npc.inventory.get(itemIndex));
                }
            }
        }
    }

    public void trade_sell() {
        drawInventory(gp.player, true);

        int x = gp.tileSize * 2, y = gp.tileSize * 9;
        int width = gp.tileSize * 6, height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);

        g2.drawString("[ESC] Back", x + 24, y + 60);

        x = gp.tileSize * 12;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()) {
            x = (int)(gp.tileSize * 15.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = ""+price;
            x = getXForAlignToRightText(text, gp.tileSize * 18 - 20);
            g2.drawString(text, x, y + 32);

            if(gp.keyH.enterPressed) {
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon || gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "Non puoi vendere gli oggetti equipaggiati";
                }
                else {
                    gp.player.coin += gp.player.inventory.get(itemIndex).price / 2;
                    gp.player.inventory.remove(itemIndex);
                }
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5);
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawPauseScreen() {

        String text = "Pausa";

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60));
        int x = getXForCenteredText(text);
        int y = gp.screenHeight / 2 - gp.tileSize;

        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }

    public int getXForAlignToRightText(String text,int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;

        return x;
    }

    public void drawTitleScreen() {

        if (titleScreenState == 0) {

            g2.setColor(Color.black);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70f));
            String text = "Boy Adventure";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 2;

            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);

            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            x = gp.screenWidth / 2 - gp.tileSize;
            y += gp.tileSize;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 35f));
            g2.setColor(Color.white);

            text = "NUOVA PARTITA";
            x = getXForCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }

            text = "CARICA PARTITA";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 1) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }

            text = "SELEZIONA PERSONAGGIO";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 2) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }
            text = "ESCI";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 3) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }
        }
        else if(titleScreenState == 1) {

            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));

            String text = "Seleziona Personaggio";
            int x = getXForCenteredText(text);
            int y = gp.tileSize;
            g2.drawString(text, x, y);

            x = gp.screenWidth / 2 - gp.tileSize;
            y += gp.tileSize;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            text = "<";
            x -= gp.tileSize;
            y += (int) (gp.tileSize * 1.5);
            g2.drawString(text, x, y);

            text = ">";
            x += (int) (gp.tileSize * 3.5);
            g2.drawString(text, x, y);

            text = "SELEZIONA";
            x = getXForCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if (commandNumber == 0) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }

            text = "ESCI";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNumber == 1) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }
        }
    }

}
