package civilizationclone.GUI;

import civilizationclone.GameState;
import civilizationclone.Player;
import java.util.HashMap;
import javafx.stage.Stage;

public abstract class MultiplayerPane extends GamePane {

    public MultiplayerPane(GameState gameState, int resX, int resY, boolean isMuted, Stage primaryStage) {
        super(gameState, resX, resY, isMuted, primaryStage);
        initActiveList();
    }

    public void initActiveList() {
        for (Player p : getPlayerList()) {
            getActiveMap().put(p.getName(), Boolean.TRUE);
        }
        getStatusBar().updateCurrentHeads();
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

}
