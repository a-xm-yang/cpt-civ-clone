
package civilizationclone.GUI;

import static civilizationclone.GUI.MiscAsset.GOLD;
import static civilizationclone.GUI.MiscAsset.HAPPY;
import static civilizationclone.GUI.MiscAsset.SCIENCE;
import civilizationclone.Player;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StatusBarPane extends Group{
    
    private Rectangle rect;
    private Text goldT;
    private Text sciT;
    private Text happyT;
    private Text turnT;
    
    private ImageView goldI;
    private ImageView sciI;
    private ImageView happyI;
    private ArrayList<ImageView> playerI;
    
    private int resX;
    private int resY;
    private Player player;
    private static GamePane gamePaneRef;
    
    public StatusBarPane(Player player, int resX, int resY, GamePane gamePaneRef){
        goldT = new Text();
        sciT = new Text();
        happyT = new Text();
        turnT = new Text();
        
        goldI = new ImageView(ImageBuffer.getImage(GOLD));
        sciI = new ImageView(ImageBuffer.getImage(SCIENCE));
        happyI = new ImageView(ImageBuffer.getImage(HAPPY));
        
        this.player = player;
        this.resX = resX;
        this.resY = resY;
        this.gamePaneRef = gamePaneRef;
        this.rect = new Rectangle(0, 0, resX + 50, 40);
        rect.setFill(Color.BLACK);
        
        initHeads();
        
        updateTexts();
        goldT.setFill(Color.YELLOW);
        sciT.setFill(Color.AQUA);
        happyT.setFill(Color.LAWNGREEN);
        turnT.setFill(Color.WHITE);
                
        goldT.setFont(Font.font("Oswald", 20));
        sciT.setFont(Font.font("Oswald", 20));
        happyT.setFont(Font.font("Oswald", 20));
        turnT.setFont(Font.font("Oswald", 20));
        
        sciT.setTranslateX(45);
        goldT.setTranslateX(sciT.getTranslateX()+sciT.getLayoutBounds().getWidth() + 75);
        happyT.setTranslateX(goldT.getTranslateX()+goldT.getLayoutBounds().getWidth() + 75);
        turnT.setTranslateX(resX - turnT.getLayoutBounds().getWidth());
        
        sciI.setTranslateX(5);
        goldI.setTranslateX(sciT.getTranslateX() + sciT.getLayoutBounds().getWidth() + 40);
        happyI.setTranslateX(goldT.getTranslateX() + goldT.getLayoutBounds().getWidth() + 40);
        sciI.setTranslateY(7);
        goldI.setTranslateY(7);
        happyI.setTranslateY(7);
        
        sciT.setTranslateY(sciT.getLayoutBounds().getHeight());
        goldT.setTranslateY(goldT.getLayoutBounds().getHeight());
        happyT.setTranslateY(happyT.getLayoutBounds().getHeight());
        turnT.setTranslateY(turnT.getLayoutBounds().getHeight());
        
        for(ImageView i: playerI){
            i.setTranslateY(60);
        }
        
        getChildren().addAll(rect, sciT, goldT, happyT, turnT, sciI, goldI, happyI);
        
        
        updateCurrentHeads();
    }
    
    public void initHeads(){
        
        playerI = new ArrayList<ImageView>();
        
        for(int i=0;i<gamePaneRef.getPlayerList().size();i++){
            playerI.add(new ImageView(ImageBuffer.getImage(gamePaneRef.getPlayerList().get(i).getLeader())));
            
        }
        
        arrangeHeads();
        
        for(ImageView i: playerI){
            getChildren().add(i);
        }
    }
    
    public void arrangeHeads(){
        for(int i=playerI.size()-1;i>=0;i--){
            playerI.get(i).setTranslateX(resX - (playerI.size() - i) * 70);
        }
    }
    
    public void removeHead(Player p){
        for(ImageView i: playerI){
            if(i.getImage().equals(ImageBuffer.getImage(p.getLeader()))){
                getChildren().remove(i);
                playerI.remove(i);
                break;
            }
        }
        arrangeHeads();
    }
    
    public void setCurrentPlayer(Player p){
        player = p;
        updateTexts();
        
        updateCurrentHeads();
    }
    
    public void updateTexts(){
        goldT.setText(Integer.toString(player.getCurrentGold()) + "(" + Integer.toString(player.getGoldIncome()) + ")");
        sciT.setText(Integer.toString(player.getTechIncome()));
        happyT.setText(Integer.toString(player.getHappiness()));
        turnT.setText("Turn: " + player.getTurnNumber() + " | " + Integer.toString(Math.abs(getYear())));
        if (getYear() < 0){
            turnT.setText(turnT.getText() + "BC");
        }else{
            turnT.setText(turnT.getText() + "AD");
        }
        
        if(player.getHappiness()>=0){
            happyT.setFill(Color.LAWNGREEN);
        }else{
            happyT.setFill(Color.RED);
        }
        
        sciT.setTranslateX(45);
        goldT.setTranslateX(sciT.getTranslateX()+sciT.getLayoutBounds().getWidth() + 75);
        happyT.setTranslateX(goldT.getTranslateX()+goldT.getLayoutBounds().getWidth() + 75);
        turnT.setTranslateX(resX - turnT.getLayoutBounds().getWidth());
        
        sciI.setTranslateX(5);
        goldI.setTranslateX(sciT.getTranslateX() + sciT.getLayoutBounds().getWidth() + 40);
        happyI.setTranslateX(goldT.getTranslateX() + goldT.getLayoutBounds().getWidth() + 40);
        
        updateCurrentHeads();
        
    }
    
    public void updateCurrentHeads(){
        
        
        for(int i=0;i<playerI.size();i++){
            if(player.equals(gamePaneRef.getPlayerList().get(i))){
                playerI.get(i).setTranslateY(90);
            }else{
                playerI.get(i).setTranslateY(50);
            }
                
        }
    }
    
    private int getYear(){
        int turns = player.getTurnNumber();
        int year = -4040;
        
        if (turns > 225){
            year += (1 * (turns - 225));
            turns = 225;
        }
        if (turns > 175){
            year += (5 * (turns - 175));
            turns = 175;
        }
        if (turns > 150){
            year += (10 * (turns - 150));
            turns = 150;
        }
        if (turns > 125){
            year += (20 * (turns - 125));
            turns = 125;
        }
        year += 40 * turns;
        return year;
        
    }
}
