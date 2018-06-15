package civilizationclone.GUI;

import civilizationclone.GameMap.MapSize;
import civilizationclone.GameState;
import civilizationclone.Leader;
import civilizationclone.Player;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TitleMenu extends Group {

    //STAGE HANDLING COMPONENTS
    private int resX, resY;
    private Stage primaryStage;
    private Pane titlePane;
    private Pane startPane;
    private Pane optionPane;
    //GENERAL NODES
    private Canvas canvas;
    private MediaPlayer mp;
    //MAP LAUNCHING NODES
    private ChoiceBox mapOption;
    private ChoiceBox resolutionChoice;
    private ArrayList<PlayerBox> playerList;
    private Button startButton;
    private Button addLeader;
    private ComboBox leaderOption;
    private CheckBox cheat;
    private CheckBox music;
    private boolean cheatOn;
    //EFFFECTS
    private Effect shadow = new DropShadow(5, Color.WHITE);
    private Effect blur = new BoxBlur(1, 1, 5);

    public TitleMenu(int resX, int resY, Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.resX = resX;
        this.resY = resY;

        //background music
        Media media = new Media(getClass().getClassLoader().getResource("Assets/Misc/Sogno_di_Volare.mp3").toExternalForm());
        mp = new MediaPlayer(media);
        mp.setOnEndOfMedia(() -> {
            mp.seek(Duration.ZERO);
        });
        mp.play();

        //  mp.setMute(true);
        cheatOn = false;
        playerList = new ArrayList<>();

        Font.loadFont(TitleMenu.class.getClassLoader().getResourceAsStream("Assets/Misc/menuFont.ttf"), 50);
        initialize();
    }

    public TitleMenu(int resX, int resY, Stage primaryStage, MediaPlayer mp) {

        //A overloaded constructor for when player changes the resolution of the screen (music will keep playing although a new title object is constructed, because a reference is passed)
        this.primaryStage = primaryStage;
        this.resX = resX;
        this.resY = resY;

        this.mp = mp;

        cheatOn = false;
        playerList = new ArrayList<>();

        Font.loadFont(TitleMenu.class.getClassLoader().getResourceAsStream("Assets/Misc/menuFont.ttf"), 50);
        initialize();
    }

    public void initialize() {

        //Initialize the title by creating three differnt panes needed, and add the background
        getChildren().clear();

        canvas = new Canvas(resX + 50, resY + 50);
        canvas.getGraphicsContext2D().drawImage(ImageBuffer.getImage(MiscAsset.TITLE_BACKGROUND), 0, 0, resX + 50, resY + 50);

        titlePane = initTitle();
        startPane = initPlay();
        optionPane = initOption();

        getChildren().add(canvas);
        getChildren().add(titlePane);
    }

    private Pane initTitle() {

        Pane root = new Pane();

        //shift the whole pane by using fixX and fixY, thus making it stil in the same positon even after resolution change
        int fixX = 10 + (resX - 1200) / 2;
        int fixY = 125 + (resY - 800) / 2;

        CivTitle title = new CivTitle("C O L O N I Z A T I O N  I I", 48);
        title.setTranslateX(300 + fixX);
        title.setTranslateY(230 + fixY);

        CivTitle subtitle = new CivTitle("A L E X A N D E R   Y A N G ` S", 11);
        subtitle.setTranslateX(521 + fixX);
        subtitle.setTranslateY(175 + fixY);

        CivMenuItems word1 = new CivMenuItems("PLAY");
        word1.setTranslateX(535 + fixX);
        word1.setTranslateY(325 + fixY);
        word1.setOnMouseClicked(e -> {
            openPlay();
        });

        CivMenuItems word2 = new CivMenuItems("OPTIONS");
        word2.setTranslateX(535 + fixX);
        word2.setTranslateY(375 + fixY);
        word2.setOnMouseClicked(e -> {
            openOption();
        });

        Line line = new Line(525 + fixX, 300 + fixY, 525 + fixX, 380 + fixY);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));

        root.getChildren().addAll(title, subtitle, word1, word2, line);

        return root;
    }

    private Pane initPlay() {

        Pane root = new Pane();

        //shift the whole pane by using fixX and fixY, thus making it stil in the same positon even after resolution change
        int fixX = 62 + (resX - 1200) / 2;
        int fixY = 50 + (resY - 800) / 2;

        Rectangle rect1 = new Rectangle(270 + fixX, 30 + fixY, 550, 680);
        rect1.setFill(Color.BLACK);
        rect1.setOpacity(0.45);

        Rectangle rect2 = new Rectangle(280 + fixX, 42 + fixY, 530, 80);
        rect2.setFill(Color.BLACK);
        rect2.setOpacity(0.5);

        Rectangle rect3 = new Rectangle(280 + fixX, 165 + fixY, 530, 532);
        rect3.setFill(Color.BLACK);
        rect3.setOpacity(0.5);

        Rectangle rect4 = new Rectangle(345 + fixX, 195 + fixY, 400, 470);
        rect4.setFill(Color.BLACK);
        rect4.setOpacity(0.55);

        Line line1 = new Line(271 + fixX, 128 + fixY, 818 + fixX, 128 + fixY);
        line1.setStrokeWidth(3);
        line1.setStroke(Color.ROSYBROWN);

        Line line2 = new Line(271 + fixX, 160 + fixY, 818 + fixX, 160 + fixY);
        line2.setStrokeWidth(3);
        line2.setStroke(Color.TAN);

        CivTitle back = new CivTitle("RETURN", 14);
        back.setTranslateX(290 + fixX);
        back.setTranslateY(188 + fixY);
        back.effectProperty().bind(
                Bindings.when(back.hoverProperty()).then(shadow).otherwise(line1.getEffect())
        );
        back.setOnMouseClicked(e -> {
            openTitle();
        });

        CivTitle heading = new CivTitle("COLONIZATION II", 45);
        heading.setTranslateX(345 + fixX);
        heading.setTranslateY(113 + fixY);

        CivTitle subheading = new CivTitle("ALEXANDER YANG'S", 11);
        subheading.setTranslateX(490 + fixX);
        subheading.setTranslateY(65 + fixY);

        CivTitle text1 = new CivTitle("CREATE GAME", 17);
        text1.setTranslateX(485 + fixX);
        text1.setTranslateY(150 + fixY);

        CivTitle map = new CivTitle("MAP : ", 15);
        map.setTranslateX(400 + fixX);
        map.setTranslateY(645 + fixY);

        mapOption = new ChoiceBox();
        mapOption.setItems(FXCollections.observableArrayList(
                "SMALL", new Separator(), "MEDIUM",
                new Separator(), "LARGE")
        );
        mapOption.setTranslateX(455 + fixX);
        mapOption.setTranslateY(627.5 + fixY);
        mapOption.setValue("MEDIUM");

        startButton = new Button("Start");
        startButton.setFont(Font.font("Penumbra HalfSerif Std", 18));
        startButton.setTranslateX(625 + fixX);
        startButton.setTranslateY(622 + fixY);
        startButton.setDisable(true);
        startButton.setOnMouseClicked(e -> {
            startGame();
        });

        addLeader = new Button("Add");
        addLeader.setFont(Font.font("Penumbra HalfSerif Std", 15));
        addLeader.setTranslateX(580 + fixX);
        addLeader.setTranslateY(207 + fixY);
        addLeader.setOnMouseClicked(e -> {
            addLeader();
        });

        leaderOption = new ComboBox();
        for (Leader l : Leader.values()) {
            leaderOption.getItems().add(l.name());
        }
        leaderOption.setTranslateX(435 + fixX);
        leaderOption.setTranslateY(210 + fixY);

        root.getChildren().addAll(rect3, rect4, rect1, rect2, line1, line2, heading, subheading, text1, map, startButton, mapOption, leaderOption, addLeader, back);

        return root;
    }

    private Pane initOption() {

        Pane root = new Pane();

        //shift the whole pane by using fixX and fixY, thus making it stil in the same positon even after resolution change
        int fixX = 62 + (resX - 1200) / 2;
        int fixY = 50 + (resY - 800) / 2;

        Rectangle rect1 = new Rectangle(270 + fixX, 30 + fixY, 550, 680);
        rect1.setFill(Color.BLACK);
        rect1.setOpacity(0.45);

        Rectangle rect2 = new Rectangle(280 + fixX, 42 + fixY, 530, 80);
        rect2.setFill(Color.BLACK);
        rect2.setOpacity(0.5);

        Rectangle rect3 = new Rectangle(280 + fixX, 165 + fixY, 530, 532);
        rect3.setFill(Color.BLACK);
        rect3.setOpacity(0.5);

        Rectangle rect4 = new Rectangle(345 + fixX, 195 + fixY, 400, 470);
        rect4.setFill(Color.BLACK);
        rect4.setOpacity(0.55);

        Line line1 = new Line(271 + fixX, 128 + fixY, 818 + fixX, 128 + fixY);
        line1.setStrokeWidth(3);
        line1.setStroke(Color.ROSYBROWN);

        Line line2 = new Line(271 + fixX, 160 + fixY, 818 + fixX, 160 + fixY);
        line2.setStrokeWidth(3);
        line2.setStroke(Color.TAN);

        CivTitle heading = new CivTitle("COLONIZATION II", 45);
        heading.setTranslateX(345 + fixX);
        heading.setTranslateY(113 + fixY);

        CivTitle subheading = new CivTitle("ALEXANDER YANG'S", 11);
        subheading.setTranslateX(490 + fixX);
        subheading.setTranslateY(65 + fixY);

        CivTitle text1 = new CivTitle("OPTIONS", 17);
        text1.setTranslateX(500 + fixX);
        text1.setTranslateY(150 + fixY);

        CivTitle back = new CivTitle("RETURN", 14);
        back.setTranslateX(290 + fixX);
        back.setTranslateY(188 + fixY);
        back.effectProperty().bind(
                Bindings.when(back.hoverProperty()).then(shadow).otherwise(subheading.getEffect())
        );
        back.setOnMouseClicked(e -> {
            openTitle();
        });

        CivTitle text2 = new CivTitle("Cheats : ", 15);
        text2.setTranslateX(493 + fixX);
        text2.setTranslateY(303 + fixY);

        CivTitle text3 = new CivTitle("Mute :  ", 15);
        text3.setTranslateX(502 + fixX);
        text3.setTranslateY(383 + fixY);

        CivTitle resolution = new CivTitle("Resolution : ", 15);
        resolution.setTranslateX(455 + fixX);
        resolution.setTranslateY(470 + fixY);

        resolutionChoice = new ChoiceBox();
        resolutionChoice.getItems().addAll("1200x800", "1500x1000");
        String resolutionMessage = Integer.toString(resX) + "x" + Integer.toString(resY);
        resolutionChoice.setValue(resolutionMessage);

        cheat = new CheckBox();
        music = new CheckBox();

        resolutionChoice.setTranslateX(570 + fixX);
        cheat.setTranslateX(570 + fixX);
        music.setTranslateX(570 + fixX);
        resolutionChoice.setTranslateY(455 + fixY);
        cheat.setTranslateY(290 + fixY);
        music.setTranslateY(370 + fixY);

        CivMenuItems save = new CivMenuItems("     SAVE");
        save.setTranslateX(485 + fixX);
        save.setTranslateY(560 + fixY);
        save.setOnMouseClicked(e -> {
            saveOption();
        });

        // save.setTranslateX(420+ fixX); 
        //save.setTranslateY(500 + fixY);
        root.getChildren().addAll(canvas, rect1, rect2, rect3, rect4, line1, line2, heading, subheading, resolution, text1, text2, text3, resolutionChoice, cheat, music, save, back);

        return root;
    }

    private void addLeader() {

        //check if the current number of leaders is legal and that it is not an invalid input
        if (playerList.size() > 6 || leaderOption.getValue() == null) {
            return;
        }

        //fetch the selected leader
        String selection = (String) leaderOption.getValue();
        leaderOption.getItems().remove(leaderOption.getValue());
        leaderOption.setVisibleRowCount(leaderOption.getItems().size());

        //add the selected leader into the list of leaders selected, and reformat their positions accordingly
        PlayerBox pb = new PlayerBox(selection);
        playerList.add(pb);
        startPane.getChildren().add(pb);
        formatPlayerBoxes();

        //if leader number is greater than 2, a game can be created
        if (playerList.size() >= 2) {
            startButton.setDisable(false);
            if (playerList.size() == 6) {
                //disallow the player to add more leaders once the number is 6
                addLeader.setVisible(false);
                leaderOption.setVisible(false);
            }
        }
    }

    private void removeLeader(PlayerBox pb) {

        //remove leader from the list and reformat leader positions accordingly
        leaderOption.getItems().add(pb.leader.name());
        leaderOption.setVisibleRowCount(leaderOption.getItems().size());
        playerList.remove(pb);
        startPane.getChildren().remove(pb);
        formatPlayerBoxes();

        //if leader number falls below 2, one cannot create a game
        if (playerList.size() < 2) {
            startButton.setDisable(true);
        }

        //allow the player to add more leader
        addLeader.setVisible(true);
        leaderOption.setVisible(true);
    }

    private void formatPlayerBoxes() {
        int fixX = 62 + (resX - 1200) / 2;
        int fixY = 50 + (resY - 800) / 2;

        //format all the leaders according to their seleciton order in the pane
        for (int i = 0; i < playerList.size(); i++) {
            playerList.get(i).setTranslateX(345 + fixX);
            playerList.get(i).setTranslateY(195 + fixY + i * 62);
        }

        addLeader.setTranslateY(207 + fixY + 62.0 * playerList.size());
        leaderOption.setTranslateY(210 + fixY + 62.0 * playerList.size());
        addLeader.toFront();
        leaderOption.toFront();
    }

    private void startGame() {
        //prevent a game from beginnign if there is no leader
        if (playerList.isEmpty()) {
            return;
        }

        //convert the selected leader boxes to actual Player objects
        ArrayList<Player> list = new ArrayList<>();
        for (PlayerBox pb : playerList) {
            switch (pb.getLeader()) {
                case STALIN:
                    list.add(new Player("Joseph Stalin"));
                    break;
                case ZEDONG:
                    list.add(new Player("Mao Zedong"));
                    break;
                case HITLER:
                    list.add(new Player("Adolf Hitler"));
                    break;
                case MUSSOLINI:
                    list.add(new Player("Bennito Mussolini"));
                    break;
                case CHURCHILL:
                    list.add(new Player("Winston Churchill"));
                    break;
                case ROOSEVELT:
                    list.add(new Player("Franklin Roosevelt"));
                    break;
            }
        }

        //turn on G-MODE
        if (cheatOn) {
            for (Player p : list) {
                p.setgMode(true);
            }
        }

        //create a random procedurally-generated game map using a random seed
        MapSize ms = MapSize.MEDIUM;
        for (MapSize m : MapSize.values()) {
            if (m.name().equalsIgnoreCase((String) mapOption.getValue())) {
                ms = m;
                break;
            }
        }

        //start the game by changing scenes
        primaryStage.setScene(new Scene(new SinglePlayerPane(new GameState(list, ms), resX, resY, mp.isMute(), primaryStage)));
        primaryStage.getScene().getStylesheets().add(getClass().getResource("/civilizationclone/GUI/Setting.css").toExternalForm());
        mp.pause();
    }

    //open play pane
    private void openPlay() {
        getChildren().clear();
        getChildren().add(canvas);
        getChildren().add(startPane);
    }

    //open option pane
    private void openOption() {
        getChildren().clear();
        getChildren().add(canvas);
        getChildren().add(optionPane);

        //change the option to how things are
        cheat.setSelected(cheatOn);
        music.setSelected(mp.isMute());
        String resolutionMessage = Integer.toString(resX) + "x" + Integer.toString(resY);
        resolutionChoice.setValue(resolutionMessage);
    }

    //open title pane
    private void openTitle() {
        getChildren().clear();
        getChildren().add(canvas);
        getChildren().add(titlePane);
    }

    private void saveOption() {

        //change the variables such as mute and G-Mode according to option selection
        String chosen = (String) resolutionChoice.getValue();

        mp.setMute(music.isSelected());
        cheatOn = cheat.isSelected();

        //if the resolution option chosen is not the one it has right now, get the resolution by dissecting string, and then create a new Scene with a new TitleMenu
        if (!chosen.startsWith(Integer.toString(resX))) {
            int width = Integer.parseInt(chosen.substring(0, chosen.indexOf("x")));
            int height = Integer.parseInt(chosen.substring(chosen.indexOf("x") + 1));

            resX = width;
            resY = height;

            primaryStage.setScene(new Scene(new TitleMenu(resX, resY, primaryStage, mp), resX, resY));
            primaryStage.getScene().getStylesheets().add(getClass().getResource("/civilizationclone/GUI/Setting.css").toExternalForm());
        } else {
            openTitle();
        }

    }

    private class PlayerBox extends Group {

        private Text text;
        private Leader leader;
        private Circle removeIcon;

        //An object used for the display of each leader, with leader name and image set accordingly
        public PlayerBox(String name) {

            ImageView leaderHead = new ImageView();
            for (Leader l : Leader.values()) {
                if (l.name().equalsIgnoreCase(name)) {
                    leader = l;
                    break;
                }
            }
            leaderHead.setImage(ImageBuffer.getImage(leader));
            leaderHead.setTranslateX(20);
            leaderHead.setTranslateY(1);

            Rectangle rect = new Rectangle(400, 62);
            rect.setFill(Color.STEELBLUE);
            rect.setOpacity(0.75);
            rect.setStrokeWidth(3);
            rect.setStroke(Color.BISQUE);

            text = new Text(name);
            text.setFont(Font.font("Penumbra HalfSerif Std", 25));
            text.setFill(Color.WHITE);
            text.setTranslateX(115);
            text.setTranslateY(41);

            removeIcon = new Circle(360, 30, 20);
            removeIcon.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
            removeIcon.effectProperty().bind(
                    Bindings.when(hoverProperty()).then(shadow).otherwise(blur)
            );
            removeIcon.setOnMouseClicked(e -> {
                removeLeader(this);
            });

            getChildren().addAll(rect, leaderHead, text, removeIcon);

        }

        public Leader getLeader() {
            return leader;
        }

    }

    private class CivTitle extends Group {

        private Text text = new Text();

        //Object for artistic title
        public CivTitle(String title, int size) {

            text.setText(title);

            text.setFont(Font.font("Penumbra HalfSerif Std", size));

            text.setFill(Color.WHITE);
            text.setEffect(new DropShadow(30, Color.BLACK));

            getChildren().addAll(text);
        }
    }

    private class CivMenuItems extends Group {

        private Text text;
        private Effect shadow = new DropShadow(5, Color.WHITE);
        private Effect blur = new BoxBlur(1, 1, 5);

        //Object for artistic item name
        public CivMenuItems(String words) {

            Rectangle rect = new Rectangle(-8, -30, 150, 40);
            rect.setStroke(Color.color(0, 0, 0, 0.5));
            rect.setEffect(new GaussianBlur());

            rect.fillProperty().bind(
                    Bindings.when(pressedProperty()).then(Color.color(0.5, 0.5, 0.5, 1)).otherwise(Color.color(0, 0, 0, 0.25))
            );

            text = new Text(words);

            text.setFont(Font.font("Penumbra HalfSerif Std", 25));
            text.setFill(Color.WHITE);

            text.effectProperty().bind(
                    Bindings.when(hoverProperty()).then(shadow).otherwise(blur)
            );

            getChildren().addAll(rect, text);

        }

    }

}
