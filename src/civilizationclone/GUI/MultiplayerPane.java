package civilizationclone.GUI;

import civilizationclone.GameState;
import civilizationclone.Player;
import java.util.HashMap;
import javafx.stage.Stage;

public abstract class MultiplayerPane extends GamePane {

    private Chatroom chatroom;

    public MultiplayerPane(GameState gameState, int resX, int resY, boolean isMuted, Stage primaryStage) {

        super(gameState, resX, resY, isMuted, primaryStage);
        initActiveList();
        getStatusBar().updateCurrentHeads();

        chatroom = new Chatroom(getResX(), getResY(), this);
        getChildren().add(getChildren().size() - 2, chatroom);
       
      
    }

    public void initActiveList() {
        for (Player p : getPlayerList()) {
            getActiveMap().put(p.getName(), Boolean.TRUE);
        }
    }

    public void checkNextTurn() {
        for (String s : getActiveMap().keySet()) {
            if (getActiveMap().get(s)) {
                return;
            }
        }
        nextTurn();
    }

    @Override
    public void nextTurn() {
        endGameCheck();
        getGameState().processAllPlayersTurn();
        setActivityLocked(false);
        initActiveList();
        updateInfo();
    }

    public void setActivity(String name, boolean active) {
        getActiveMap().put(name, active);
        getStatusBar().updateCurrentHeads();
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

}
