package civilizationclone.GUI;

import civilizationclone.CityProject;
import civilizationclone.Player;
import civilizationclone.TechType;
import civilizationclone.Tile.Improvement;
import civilizationclone.Unit.UnitType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SciencePane extends Pane {

    private int resX, resY;
    private Arc progress;
    private Circle progressBackground;
    private Circle techImage;
    private Rectangle textBackground;
    private Text infoText;

    private GamePane gamePaneRef;
    private Player player;

    private boolean hasMenuOpen;

    public SciencePane(Player player, int resX, int resY, GamePane gamePaneRef) {
        this.player = player;
        this.resX = resX;
        this.resY = resY;
        this.gamePaneRef = gamePaneRef;

        hasMenuOpen = false;

        setTranslateY(43);

        progressBackground = new Circle();
        progressBackground.setRadius(70);
        progressBackground.setFill(Color.BLACK);
        progressBackground.setStrokeWidth(5);
        progressBackground.setStroke(Color.BEIGE);
        progressBackground.setTranslateX(70);
        progressBackground.setTranslateY(70);

        techImage = new Circle();
        techImage.setRadius(40);
        techImage.setTranslateX(70);
        techImage.setTranslateY(70);
        techImage.setStrokeWidth(4);
        techImage.setStroke(Color.BEIGE);

        progress = new Arc();
        progress.setType(ArcType.ROUND);
        progress.setTranslateX(70);
        progress.setTranslateY(70);
        progress.setRadiusX(68);
        progress.setRadiusY(68);
        progress.setStartAngle(90);
        progress.setFill(Color.CADETBLUE);

        textBackground = new Rectangle(400, 35);
        textBackground.setTranslateX(110);
        textBackground.setTranslateY(10);
        textBackground.setFill(Color.DARKSLATEBLUE);
        textBackground.setStroke(Color.BEIGE);
        textBackground.setStrokeLineJoin(StrokeLineJoin.ROUND);
        textBackground.setStrokeLineCap(StrokeLineCap.ROUND);
        textBackground.setStrokeWidth(5);

        infoText = new Text();
        infoText.setFont(Font.font("Oswald", 16));
        infoText.setFill(Color.BEIGE);
        infoText.setTranslateY(34);
        infoText.setTranslateX(145);

        setOnMouseClicked(e -> {
            clickEvent(e);
        });

        updateInfo();

        getChildren()
                .addAll(textBackground, progressBackground, progress, techImage, infoText);
    }

    public void clickEvent(MouseEvent e) {
        if (!hasMenuOpen) {
            getChildren().add(new ScienceMenu(player, resX, resY));
            hasMenuOpen = true;
            e.consume();
        }
    }

    public void setCurrentPlayer(Player player) {
        this.player = player;
        updateInfo();
    }

    public void updateInfo() {
        techImage.setFill(new ImagePattern(ImageBuffer.getImage(player.getResearch())));
        progress.setLength(-360 * ((player.getTechProgress() * 1.0) / player.getResearch().getTechCost()));

        if (player.getResearch() != TechType.NONE) {
            String turnInfo = "";
            if (player.getTechIncome() > 0) {
                turnInfo = "(" + Integer.toString((player.getResearch().getTechCost() - player.getTechProgress()) / player.getTechIncome()) + " turns left)";
            } else {
                turnInfo = "(settle a city to start research)";
            }
            infoText.setText(player.getResearch().name() + " " + turnInfo);
        } else {
            infoText.setText("Select new research!");
        }
    }

    public void removeMenu(ScienceMenu sm) {
        getChildren().remove(sm);
        hasMenuOpen = false;
        updateInfo();
        gamePaneRef.updateInfo();

    }

    private class ScienceMenu extends Pane {

        private Rectangle border;
        private ComboBox comboBox;
        private Circle closeButton, confirmButton;
        private Text title, info;
        private ImageView display;
        private boolean canConfirm;

        private Player player;

        public ScienceMenu(Player player, int resX, int resY) {

            this.player = player;

            border = new Rectangle(600, 450);
            border.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CITY_OPTION_BACKGROUND), 0, 0, 1, 1, true));

            setTranslateX(resX / 2 - border.getWidth() / 2);
            setTranslateY(resY / 2 - border.getHeight() / 2 - 43);

            border.setStrokeWidth(5);
            border.setStroke(Color.BEIGE);

            closeButton = new Circle(530, 385, 30);
            closeButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
            closeButton.setOnMouseClicked((e) -> {
                e.consume();
                close();
            });

            confirmButton = new Circle(450, 385, 22);
            confirmButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CONFIRM_ICON)));
            confirmButton.setOpacity(0.25);
            confirmButton.setOnMouseClicked((e) -> {
                e.consume();
                if (canConfirm) {
                    confirm();
                    close();
                }
            });

            title = new Text("SELECT RESEARCH PROJECT");
            title.setFont(Font.font("Times New Roman", 25));
            title.setFill(Color.WHITESMOKE);
            title.setTranslateY(25);
            title.setTranslateX(5);
            initializeComboBox();
            comboBox.setOnAction(e -> {
                updateInfo();
                e.consume();
            });

            //to be changed later
            info = new Text();
            info.setFont(Font.font("Times New Roman", 15));
            info.setFill(Color.WHITESMOKE);
            info.setWrappingWidth(200);
            info.setTranslateX(350);
            info.setTranslateY(120);

            display = new ImageView();
            display.setTranslateX(110);
            display.setTranslateY(210);

            getChildren().addAll(border, closeButton, confirmButton, title, comboBox, info, display);
        }

        private void updateInfo() {

            String selection = (String) comboBox.getValue();
            System.out.println(selection);

            for (TechType t : player.getResearchableTech()) {
                if (selection.equals(t.name())) {
                    info.setText(getInfo(t));
                    display.setImage(ImageBuffer.getImage(t));
                    if (player.getResearch() != t) {
                        canConfirm = true;
                        confirmButton.setOpacity(1);
                    } else {
                        canConfirm = false;
                        confirmButton.setOpacity(0.25);
                    }
                    break;
                }
            }

        }

        private String getInfo(Enum e) {

            String s = "";

            if (e instanceof TechType) {
                TechType t = (TechType) e;

                s = s + "Research Cost: " + t.getTechCost();

                if (t.getMessage() != null) {
                    s = s + "\n\n" + t.getMessage();
                }

                if (t.getUnlockUnit() != null) {
                    s = s + "\n\nUnlock Unit: ";
                    for (UnitType u : t.getUnlockUnit()) {
                        s = s + u.name() + "  ";
                    }
                }

                if (t.getUnlockProject() != null) {
                    s = s + "\n\nUnlock City Project: ";
                    for (CityProject c : t.getUnlockProject()) {
                        s = s + c.name() + "  ";
                    }
                }

                if (t.getUnlockImprovement() != null) {
                    s = s + "\n\nUnlock Tile Improvement: ";
                    for (Improvement i : t.getUnlockImprovement()) {
                        s = s + i.name() + "  ";
                    }
                }

                s = s + "\n\nUnlock Science Project: ";

                for (TechType tech : TechType.values()) {
                    if (tech.getPrerequisites().contains(t)) {
                        s = s + tech.name() + "  ";
                    }
                }

            }

            return s;

        }

        private void confirm() {

            String selection = (String) comboBox.getValue();

            for (TechType t : player.getResearchableTech()) {
                if (selection.equals(t.name())) {
                    player.setResearch(t);
                    break;
                }
            }

            close();
        }

        private void close() {
            if (getParent() instanceof SciencePane) {
                ((SciencePane) getParent()).removeMenu(this);
            }
        }

        private void initializeComboBox() {
            ObservableList<String> options = FXCollections.observableArrayList();

            for (TechType t : player.getResearchableTech()) {
                options.add(t.name());
            }

            comboBox = new ComboBox(options);
            comboBox.setTranslateX(70);
            comboBox.setTranslateY(80);
            comboBox.setPromptText("--- Please Select ---");
            comboBox.setVisibleRowCount(5);

        }
    }

}
