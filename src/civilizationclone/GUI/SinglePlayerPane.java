package civilizationclone.GUI;

import civilizationclone.GameState;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SinglePlayerPane extends GamePane {

    public SinglePlayerPane(GameState gameState, int resX, int resY, boolean isMuted, Stage primaryStage) {
        super(gameState, resX, resY, isMuted, primaryStage);
    }

    @Override
    public synchronized void requestAction(String s) {
        if (s.startsWith("Next")) {
            nextTurn();
        } else {
            getGameState().decodeAction(s);
        }
        readNotificationFromGame();
        updateInfo();
    }

    public void nextTurn() {
        getGameState().updateCurrentPlayer();
        updateControllingPlayer();
        endGameCheck();
        getGameState().processCurrentPlayerTurn();
    }

    public void endGameCheck() {
        if (getGameState().getCurrentPlayer().isDefeated()) {
            //Play defeat audio and show victory screens
            mp.setVolume(0.5);
            dmp = new MediaPlayer(loss);
            dmp.play();
            dmp.setMute(isIsMuted());
            this.getChildren().add(new DefeatedPrompt(getResX(), getResY(), true));
        } else if (getGameState().getPlayerList().size() == 1) {
            //Play victory audio and show victory screen
            mp.setVolume(0.5);
            wmp = new MediaPlayer(win);
            wmp.play();
            wmp.setMute(isIsMuted());
            this.getChildren().add(new DefeatedPrompt(getResX(), getResY(), false));
        }
    }

    @Override
    public void receiveAction(String s) {
        //unused in single player
    }

}
