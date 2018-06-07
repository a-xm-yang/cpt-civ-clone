package civilizationclone.GUI;

import civilizationclone.GameState;
import civilizationclone.Player;
import java.util.HashMap;
import javafx.stage.Stage;

public abstract class MultiplayerPane extends GamePane {

    private HashMap<String, Boolean> activeMap = new HashMap<String, Boolean>();

    public MultiplayerPane(GameState gameState, int resX, int resY, boolean isMuted, Stage primaryStage) {
        super(gameState, resX, resY, isMuted, primaryStage);
        initActiveList();
    }

    public void initActiveList() {
        activeMap = new HashMap<>();
        for (Player p : getPlayerList()) {
            activeMap.put(p.getName(), Boolean.FALSE);
        }
    }

    public void checkNextTurn() {

        for (String s : activeMap.keySet()) {
            if (!activeMap.get(s)) {
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
        updateInfo();
        initActiveList();
    }

    public void setActivity(String name, boolean active) {
        activeMap.put(name, active);
        getStatusBar().updateCurrentHeads();
    }

    public HashMap<String, Boolean> getActiveMap() {
        return activeMap;
    }

}
