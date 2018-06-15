package civilizationclone.GUI;

import civilizationclone.GameState;
import civilizationclone.Network.ClientSocket;
import civilizationclone.Player;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class ClientPane extends MultiplayerPane {

    private ClientSocket clientSocket;

    public ClientPane(GameState gameState, int resX, int resY, boolean isMuted, Stage primaryStage, ClientSocket clientSocket) {
        super(gameState, resX, resY, isMuted, primaryStage);
        this.clientSocket = clientSocket;
        clientSocket.setPane(this);
    }

    @Override
    public void requestAction(String s) {

        if (s.startsWith("Chat")) {
            clientSocket.sendMessage(s);
            return;
        }

        if (!isActivityLocked() || s.startsWith("Next")) {
            clientSocket.sendMessage(s);
        }

        readNotificationFromGame();
        updateInfo();
    }

    @Override
    public void receiveAction(String s) {

        if (s.startsWith("Chat")) {
            getChatroom().receiveMessage(s.substring(4));
            return;
        }

        if (s.startsWith("Next")) {
            String[] msg = s.split("/");
            setActivity(msg[1], false);
            checkNextTurn();
        } else if (s.startsWith("Cancel")) {
            String[] msg = s.split("/");
            setActivity(msg[1], true);
        } else {
            getGameState().decodeAction(s);
        }

        readNotificationFromGame();
        updateInfo();
    }

    @Override
    public void endGameCheck() {
        for (Player p : getGameState().getPlayerList()) {
            if (p == getGameState().getCurrentPlayer()) {
                if (getGameState().getCurrentPlayer().isDefeated()) {
                    //Play defeat audio and show victory screens
                    mp.setVolume(0.5);
                    dmp = new MediaPlayer(loss);
                    dmp.play();
                    dmp.setMute(isIsMuted());
                    this.getChildren().add(new DefeatedPrompt(getResX(), getResY(), true));

                    getPrimaryStage().setScene(new Scene(new TitleMenu(getResX(), getResY(), getPrimaryStage()), getResX(), getResY()));
                } else if (getGameState().getPlayerList().size() == 1) {
                    //Play victory audio and show victory screen
                    mp.setVolume(0.5);
                    wmp = new MediaPlayer(win);
                    wmp.play();
                    wmp.setMute(isIsMuted());
                    this.getChildren().add(new DefeatedPrompt(getResX(), getResY(), false));
                }
                mp.setVolume(1.0);
            } else {
                if (p.isDefeated()) {
                    getStatusBar().removeHead(p.getLeader().name());
                }
            }
        }
    }

}
