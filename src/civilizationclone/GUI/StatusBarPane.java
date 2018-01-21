
package civilizationclone.GUI;

import static civilizationclone.GUI.MiscAsset.GOLD;
import static civilizationclone.GUI.MiscAsset.HAPPY;
import static civilizationclone.GUI.MiscAsset.SCIENCE;
import civilizationclone.Player;
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
    
    private int resX;
    private int resY;
    private Player player;
    private static ZoomMap zMapRef;
    
    public StatusBarPane(Player player, int resX, int resY, ZoomMap zMapRef){
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
        this.zMapRef = zMapRef;
        this.rect = new Rectangle(0, 0, resX + 50, 40);
        this.setTranslateX(0);
        this.setTranslateY(0);
        rect.setFill(Color.BLACK);
        
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
        
        getChildren().addAll(rect, sciT, goldT, happyT, turnT, sciI, goldI, happyI);
    }
    
    void updateTexts(){
        goldT.setText(Integer.toString(player.getCurrentGold()) + "(" + Integer.toString(player.getGoldIncome()) + ")");
        sciT.setText(Integer.toString(player.getTechProgress()));
        happyT.setText(Integer.toString(player.getHappiness()));
        turnT.setText("Turn: 0 | 4000 BC");
        
        if(player.getHappiness()>=0){
            happyT.setFill(Color.GREEN);
        }else{
            happyT.setFill(Color.RED);
        }
        
    }
}
