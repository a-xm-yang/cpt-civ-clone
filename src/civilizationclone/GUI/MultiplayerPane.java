package civilizationclone.GUI;

import civilizationclone.GameState;
import civilizationclone.Player;
import java.util.HashMap;
import javafx.stage.Stage;

public abstract class MultiplayerPane extends GamePane {

    private HashMap<String, Boolean> activeMap;

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

    @Override
    public void nextTurn() {
        endGameCheck();
        getGameState().processAllPlayersTurn();
        setActivityLocked(false);
        updateInfo();
        initActiveList();
    }

//  @Override
//    public void endGameCheck() {
//
//        for (Player p : getGameState().getPlayerList()) {
//
//            if (p == getGameState().getCurrentPlayer()) {
//                if (getGameState().getCurrentPlayer().isDefeated()) {
//                    //Play defeat audio and show victory screens
//                    mp.setVolume(0.5);
//                    dmp = new MediaPlayer(loss);
//                    dmp.play();
//                    dmp.setMute(isIsMuted());
//                    this.getChildren().add(new DefeatedPrompt(getResX(), getResY(), true));
//                } else if (getGameState().getPlayerList().size() == 1) {
//                    //Play victory audio and show victory screen
//                    mp.setVolume(0.5);
//                    wmp = new MediaPlayer(win);
//                    wmp.play();
//                    wmp.setMute(isIsMuted());
//                    this.getChildren().add(new DefeatedPrompt(getResX(), getResY(), false));
//                }
//
//                mp.setVolume(1.0);
//
//            } else {
//                if (p.isDefeated()) {
//                    getStatusBar().removeHead(p.getLeader().name());
//                }
//            }
//        }
//    }
    public HashMap<String, Boolean> getActiveMap() {
        return activeMap;
    }

}
