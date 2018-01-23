package civilizationclone.GUI;

import civilizationclone.City;
import civilizationclone.CityProject;
import civilizationclone.Tile.Improvement;
import civilizationclone.Tile.Resource;
import civilizationclone.Tile.Tile;
import civilizationclone.Unit.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CityMenu extends Pane {

    private City city;
    private ZoomMap zoomMapRef;

    //graphics related members
    private Rectangle border;
    private int resX, resY;
    private Text statusText, cityNameText;
    private Circle closeButton, expansionIcon;
    private ImageView productionIcon, purchaseIcon, citizenIcon, productionDisplay;
    private boolean hasOptionOpen;

    public CityMenu(City city, int resX, int resY, ZoomMap zoomMapRef) {

        this.city = city;
        this.zoomMapRef = zoomMapRef;
        this.resX = resX;
        this.resY = resY;

        hasOptionOpen = false;

        border = new Rectangle(0, 0, 460, resY + 10);
        setTranslateX(resX - 450);
        ImagePattern cityBackground = new ImagePattern(ImageBuffer.getImage(MiscAsset.CITY_BACKGROUND), 0, 0, 1, 1, true);
        border.setFill(cityBackground);

        statusText = new Text(displayCityInfo());
        statusText.setFont(Font.font("Times New Roman", 20));
        statusText.setFill(Color.WHITE);
        statusText.setTranslateY(80);
        statusText.setTranslateX(25);

        cityNameText = new Text(city.getName());
        if (city.getName().length() < 8) {
            cityNameText.setFont(Font.font("Oswald", 50));
        } else {
            cityNameText.setFont(Font.font("Oswald", 35));
        }
        cityNameText.setFill(Color.CORNSILK);
        cityNameText.setTranslateX(25);
        cityNameText.setTranslateY(60);

        closeButton = new Circle(420, 40, 30);
        closeButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
        closeButton.setOnMouseClicked((e) -> {
            close();
        });

        productionDisplay = new ImageView();
        productionDisplay.setTranslateX(300);
        productionDisplay.setTranslateY(160);
        updateProductionDisplay();

        productionIcon = new ImageView(ImageBuffer.getImage(MiscAsset.PRODUCTION_ICON));
        productionIcon.setTranslateX(175);
        productionIcon.setTranslateY(340);
        productionIcon.setOnMouseClicked((MouseEvent e) -> {
            openProductionMenu();
            e.consume();
        });

        purchaseIcon = new ImageView(ImageBuffer.getImage(MiscAsset.GOLD_ICON));
        purchaseIcon.setTranslateX(175);
        purchaseIcon.setTranslateY(540);
        purchaseIcon.setOnMouseClicked((MouseEvent e) -> {
            openPurchaseMenu();
            e.consume();
        });

        citizenIcon = new ImageView(ImageBuffer.getImage(MiscAsset.CITIZEN_ICON));
        citizenIcon.setTranslateX(175);
        citizenIcon.setTranslateY(740);
        citizenIcon.setOnMouseClicked((MouseEvent e) -> {
            openExpansionMenu();
            e.consume();
        });

        expansionIcon = new Circle(32);
        expansionIcon.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.EXPANSION_ICON)));
        expansionIcon.setTranslateX(370);
        expansionIcon.setTranslateY(115);
        expansionIcon.setOnMouseClicked(e -> {
            activateExpansion();
            e.consume();
        });

        getChildren().addAll(border, statusText, cityNameText, closeButton, productionDisplay, productionIcon, purchaseIcon, citizenIcon);

        if (city.canExpand()) {
            getChildren().add(expansionIcon);
            statusText.setTranslateY(120);
            productionDisplay.setTranslateY(200);
        }

    }

    private void activateExpansion() {
        zoomMapRef.activateExpansion();
        close();
    }

    private void openProductionMenu() {
        if (!hasOptionOpen()) {
            getChildren().add(new ProductionMenu(city, resX, resY));
            setHasOptionOpen(true);
        }
    }

    private void openPurchaseMenu() {
        if (!hasOptionOpen()) {
            getChildren().add(new PurchaseMenu(city, resX, resY));
            setHasOptionOpen(true);
        }
    }

    private void openExpansionMenu() {
        if (!hasOptionOpen()) {
            getChildren().add(new CitizenMenu(city, resX, resY));
            setHasOptionOpen(true);
        }
    }

    private void setHasOptionOpen(boolean has) {
        hasOptionOpen = has;
    }

    private void updateProductionDisplay() {
        Image productionImage;

        if (city.getCurrentUnit() != null) {
            productionImage = ImageBuffer.getImage(city.getCurrentUnit());
        } else if (city.getCurrentProject() != null) {
            productionImage = ImageBuffer.getImage(city.getCurrentProject());
        } else {
            productionImage = ImageBuffer.getImage(MiscAsset.WARNING);
        }

        productionDisplay.setImage(productionImage);
        statusText.setText(displayCityInfo());
    }

    private String displayCityInfo() {

        String msg = "";

        if (city.canExpand()) {
            msg = msg + "City can expand! Click to expand - >";
        }

        msg = msg + "\n\nPopulation: " + city.getPopulation();
        msg = msg + "\nFood income: " + city.getFoodIncome();
        msg = msg + "\nTech income: " + city.getTechIncome();
        msg = msg + "\nGold income: " + city.getGoldIncome();
        msg = msg + "\nProduction income: " + city.getProductionIncome();

        if (city.getCurrentProject() != null) {
            msg = msg + "\n\nCurrent Project: " + city.getCurrentProject().name();
            msg = msg + "\nProgress: " + city.getCurrentProduction() + "/" + city.getCurrentProject().getProductionCost();
        } else if (city.getCurrentUnit() != null) {
            msg = msg + "\n\nCurrent Project: " + city.getCurrentUnit().name();
            msg = msg + "\nProgress: " + city.getCurrentProduction() + "/" + city.getCurrentUnit().getProductionCost();
        } else {
            msg = msg + "\n\nNothing is being produced!";
        }

        return msg;
    }

    public void removeOption(Node n) {
        getChildren().remove(n);
        setHasOptionOpen(false);

        updateProductionDisplay();
    }

    public boolean hasOptionOpen() {
        return hasOptionOpen;
    }

    private void close() {

        city.getPlayer().calculateHappiness();
        city.getPlayer().calcGoldIncome();
        city.getPlayer().calcTechIncome();

        //remove this pane
        if (this.getParent() instanceof GamePane) {
            ((GamePane) this.getParent()).removeCityMenu();
        }
    }

    //MENU OPTION PRIVATE CLASSES -------------------------------------------------------------------
    //<editor-fold>
    private class ProductionMenu extends Pane {

        private Rectangle border;
        private ComboBox comboBox;
        private Circle closeButton, confirmButton;
        private Text title, info;
        private ImageView display;

        private City city;
        private boolean canConfirm;

        public ProductionMenu(City city, int resX, int resY) {

            this.city = city;

            canConfirm = false;

            border = new Rectangle(600, 450);
            border.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CITY_OPTION_BACKGROUND), 0, 0, 1, 1, true));
            setTranslateX(resX / 2 - border.getWidth() / 2 - (resX - 450));
            setTranslateY(resY / 2 - border.getHeight() / 2);

            border.setStrokeWidth(5);
            border.setStroke(Color.BROWN);

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
                }

            });

            title = new Text("SELECT CITY PRODUCTION");
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
            info.setFont(Font.font("Times New Roman", 18));
            info.setFill(Color.WHITESMOKE);
            info.setWrappingWidth(200);
            info.setTranslateX(350);
            info.setTranslateY(160);

            display = new ImageView();
            display.setTranslateX(110);
            display.setTranslateY(210);

            getChildren().addAll(border, closeButton, confirmButton, title, comboBox, info, display);
        }

        private void updateInfo() {

            String selection = (String) comboBox.getValue();
            System.out.println(selection);

            //If the value is one of the fillers
            if (comboBox.getValue().equals("----- UNITS -----") || comboBox.getValue().equals("----- PROJECTS -----")) {
                confirmButton.setOpacity(0.25);
                info.setVisible(false);
                display.setVisible(false);
                canConfirm = false;
                return;
            }

            //Determine what exactly did the person click
            for (UnitType t : city.getPlayer().getBuildableUnit()) {
                if (t.name().equals(selection)) {
                    info.setText(getInfo(t));
                    display.setImage(ImageBuffer.getImage(t));

                    canConfirm = true;
                    confirmButton.setOpacity(1);
                    info.setVisible(true);
                    display.setVisible(true);
                    return;
                }
            }

            for (CityProject c : city.getPlayer().getOwnedCityProject()) {
                if (selection.startsWith(c.name())) {
                    info.setText(getInfo(c));
                    display.setImage(ImageBuffer.getImage(c));
                    info.setVisible(true);
                    display.setVisible(true);

                    if (selection.equals(c.name())) {
                        canConfirm = true;
                        confirmButton.setOpacity(1);
                    }

                    return;
                }
            }

        }

        private String getInfo(Enum e) {

            String s = "";

            if (e instanceof UnitType) {
                s = s + "Produciton Cost: " + ((UnitType) e).getProductionCost() + "\n";

                if (BuilderUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nCivilian unit that can build improvements on tiles to increase resource outputs.";
                } else if (MeleeUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nMelee fighting unit that attacks at a close range and has decent health.";
                } else if (RangeUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nRanged unit that attack can from afar without taking any damage. Relatively weak in health and close-ranged combats.";
                } else if (CalvaryUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nFast-moving melee units that can roam around the battlefield with incredible speed. Strong against ranged units yet weak against melee. Weak in sieging.";
                } else if (SiegeUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nA slow-moving unit that specializes in taking down enemy cities.";
                } else if (ScoutUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nVery fast-moving civilian unit that scouts the map.";
                } else if (SettlerUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nA civilian unit that allows you to settle another city elsewhere.";
                }

            } else if (e instanceof CityProject) {

                //add descriptions later
                s = s + "Production Cost: " + ((CityProject) e).getProductionCost();

                if (((CityProject) e).getFoodBonus() > 0) {
                    s = s + "\n\nFood Bonus: " + ((CityProject) e).getFoodBonus();
                }

                if (((CityProject) e).getTechBonus() > 0) {
                    s = s + "\nTech Bonus: " + ((CityProject) e).getTechBonus();
                }

                if (((CityProject) e).getProductionBonus() > 0) {
                    s = s + "\nProduction Bonus: " + ((CityProject) e).getProductionBonus();
                }

                if (((CityProject) e).getGoldBonus() > 0) {
                    s = s + "\nGold Bonus: " + ((CityProject) e).getGoldBonus();
                }

            }

            return s;

        }

        private void confirm() {

            String selection = (String) comboBox.getValue();

            //Determine what exactly did the person click
            for (UnitType t : city.getPlayer().getBuildableUnit()) {
                if (t.name().equals(selection)) {
                    city.setProduction(t);
                    close();
                }
            }

            for (CityProject c : city.getPlayer().getOwnedCityProject()) {
                if (c.name().equals(selection)) {
                    city.setProduction(c);
                    close();
                }
            }

            close();
        }

        private void close() {
            if (getParent() instanceof CityMenu) {
                ((CityMenu) getParent()).removeOption(this);
            }
        }

        private void initializeComboBox() {
            ObservableList<String> options = FXCollections.observableArrayList();

            options.add("----- UNITS -----");

            for (UnitType t : city.getPlayer().getBuildableUnit()) {
                if (NavalUnit.class.isAssignableFrom(t.getClass())) {
                    if (city.isCostal()){
                        options.add(t.name());
                    }
                } else {
                    options.add(t.name());
                }
            }

            options.add("----- PROJECTS -----");

            for (CityProject c : city.getPlayer().getOwnedCityProject()) {
                if (!city.getBuiltProjects().contains(c)) {
                    options.add(c.name());
                } else {
                    options.add(c.name() + " (Owned)");
                }
            }

            comboBox = new ComboBox(options);
            comboBox.setTranslateX(70);
            comboBox.setTranslateY(80);
            comboBox.setPromptText("--- Please Select ---");
            comboBox.setVisibleRowCount(12);

        }
    }

    private class PurchaseMenu extends Pane {

        private Rectangle border;
        private ComboBox comboBox;
        private Circle closeButton, confirmButton;
        private Text title, info;
        private ImageView display;

        private City city;
        private boolean canConfirm;

        public PurchaseMenu(City city, int resX, int resY) {

            this.city = city;

            canConfirm = false;

            border = new Rectangle(600, 450);
            border.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CITY_OPTION_BACKGROUND), 0, 0, 1, 1, true));
            setTranslateX(resX / 2 - border.getWidth() / 2 - (resX - 450));
            setTranslateY(resY / 2 - border.getHeight() / 2);

            border.setStrokeWidth(5);
            border.setStroke(Color.GOLD);

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
                }

            });

            title = new Text("PURCHASE WITH GOLD");
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
            info.setFont(Font.font("Times New Roman", 18));
            info.setFill(Color.WHITESMOKE);
            info.setWrappingWidth(200);
            info.setTranslateX(350);
            info.setTranslateY(160);

            display = new ImageView();
            display.setTranslateX(110);
            display.setTranslateY(210);

            getChildren().addAll(border, closeButton, confirmButton, title, comboBox, info, display);
        }

        private void updateInfo() {

            String selection = (String) comboBox.getValue();
            System.out.println(selection);

            //If the value is one of the fillers
            if (comboBox.getValue().equals("----- UNITS -----") || comboBox.getValue().equals("----- PROJECTS -----")) {
                confirmButton.setOpacity(0.25);
                info.setVisible(false);
                display.setVisible(false);
                canConfirm = false;
                return;
            }

            //Determine what exactly did the person click
            for (UnitType t : city.getPlayer().getBuildableUnit()) {
                if (t.name().equals(selection)) {

                    if (city.getPlayer().getCurrentGold() >= t.getPurchaseCost()) {
                        canConfirm = true;
                        confirmButton.setOpacity(1);
                    } else {
                        confirmButton.setOpacity(0.25);
                        canConfirm = false;
                    }

                    info.setText(getInfo(t));
                    display.setImage(ImageBuffer.getImage(t));
                    info.setVisible(true);
                    display.setVisible(true);
                    return;
                }
            }

            for (CityProject c : city.getPlayer().getOwnedCityProject()) {
                if (selection.startsWith(c.name())) {

                    if (city.getPlayer().getCurrentGold() >= c.getPurchaseCost() && selection.equals(c.name())) {
                        canConfirm = true;
                        confirmButton.setOpacity(1);
                    } else {
                        confirmButton.setOpacity(0.25);
                        canConfirm = false;
                    }

                    info.setText(getInfo(c));
                    display.setImage(ImageBuffer.getImage(c));
                    info.setVisible(true);
                    display.setVisible(true);
                    return;
                }
            }

        }

        private String getInfo(Enum e) {

            String s = "";

            if (e instanceof UnitType) {
                s = s + "Purchase Cost: " + ((UnitType) e).getPurchaseCost() + "\n";

                if (BuilderUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nCivilian unit that can build improvements on tiles to increase resource outputs.";
                } else if (MeleeUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nMelee fighting unit that attacks at a close range and has decent health.";
                } else if (RangeUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nRanged unit that attack can from afar without taking any damage. Relatively weak in health and close-ranged combats.";
                } else if (CalvaryUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nFast-moving melee units that can roam around the battlefield with incredible speed. Strong against ranged units yet weak against melee. Weak in sieging.";
                } else if (SiegeUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nA slow-moving unit that specializes in taking down enemy cities.";
                } else if (ScoutUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nVery fast-moving civilian unit that scouts the map.";
                } else if (SettlerUnit.class.isAssignableFrom(((UnitType) e).getCorrespondingClass())) {
                    s = s + "\nA civilian unit that allows you to settle another city elsewhere.";
                }

            } else if (e instanceof CityProject) {

                //add descriptions later
                s = s + "Purchase Cost: " + ((CityProject) e).getPurchaseCost();

                if (((CityProject) e).getFoodBonus() > 0) {
                    s = s + "\n\nFood Bonus: " + ((CityProject) e).getFoodBonus();
                }

                if (((CityProject) e).getTechBonus() > 0) {
                    s = s + "\nTech Bonus: " + ((CityProject) e).getTechBonus();
                }

                if (((CityProject) e).getProductionBonus() > 0) {
                    s = s + "\nProduction Bonus: " + ((CityProject) e).getProductionBonus();
                }

                if (((CityProject) e).getGoldBonus() > 0) {
                    s = s + "\nGold Bonus: " + ((CityProject) e).getGoldBonus();
                }

            }

            return s;

        }

        private void confirm() {

            String selection = (String) comboBox.getValue();

            //Determine what exactly did the person click
            for (UnitType t : city.getPlayer().getBuildableUnit()) {
                if (t.name().equals(selection)) {

                    if (!city.getCityTile().hasUnit()) {
                        city.getPlayer().setCurrentGold(city.getPlayer().getCurrentGold() - t.getPurchaseCost());
                        try {
                            city.getPlayer().addUnit((Unit) t.getCorrespondingClass().getConstructor(City.class).newInstance(city));
                            city.getPlayer().getUnitList().get(city.getPlayer().getUnitList().size() - 1).setMovement(5);
                        } catch (Exception e) {
                            System.out.println("Construcing unit from purchase failed!");
                        }

                    } else {
                        System.out.println("Cannot purchase! Tile occupied!");
                    }

                    close();
                }
            }

            for (CityProject c : city.getPlayer().getOwnedCityProject()) {
                if (c.name().equals(selection)) {
                    city.getPlayer().setCurrentGold(city.getPlayer().getCurrentGold() - c.getPurchaseCost());
                    city.addCityProject(c);
                    close();
                }
            }

            close();
        }

        private void close() {
            if (getParent() instanceof CityMenu) {
                ((CityMenu) getParent()).zoomMapRef.repaint();
                ((CityMenu) getParent()).removeOption(this);
            }
        }

        private void initializeComboBox() {
            ObservableList<String> options = FXCollections.observableArrayList();

            options.add("----- UNITS -----");

            for (UnitType t : city.getPlayer().getBuildableUnit()) {
                options.add(t.name());
            }

            options.add("----- PROJECTS -----");

            for (CityProject c : city.getPlayer().getOwnedCityProject()) {
                if (!city.getBuiltProjects().contains(c)) {
                    options.add(c.name());
                } else {
                    options.add(c.name() + " (Owned)");
                }
            }

            comboBox = new ComboBox(options);
            comboBox.setTranslateX(70);
            comboBox.setTranslateY(80);
            comboBox.setPromptText("--- Please Select ---");
            comboBox.setVisibleRowCount(12);

        }
    }

    private class CitizenMenu extends Pane {

        private Rectangle border;
        private Text title, workerDisplay;
        private Circle closeButton;
        private ScrollPane scrollSelection;

        private Group collection;
        private City city;

        public CitizenMenu(City city, int resX, int resY) {
            this.city = city;

            border = new Rectangle(600, 450);
            border.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CITY_OPTION_BACKGROUND), 0, 0, 1, 1, true));
            setTranslateX(resX / 2 - border.getWidth() / 2 - (resX - 450));
            setTranslateY(resY / 2 - border.getHeight() / 2);
            border.setStrokeWidth(5);
            border.setStroke(Color.WHEAT);

            title = new Text("SELECT WORKED TILES");
            title.setFont(Font.font("Times New Roman", 25));
            title.setFill(Color.WHITESMOKE);
            title.setTranslateY(25);
            title.setTranslateX(5);

            workerDisplay = new Text();
            updateText();
            workerDisplay.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
            workerDisplay.setFill(Color.WHITESMOKE);
            workerDisplay.setTranslateX(320);
            workerDisplay.setTranslateY(80);

            closeButton = new Circle(530, 385, 30);
            closeButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
            closeButton.setOnMouseClicked((e) -> {
                e.consume();
                close();
            });

            scrollSelection = new ScrollPane();
            scrollSelection.setPrefWidth(500);
            scrollSelection.setPrefHeight(185);
            scrollSelection.setTranslateX(50);
            scrollSelection.setTranslateY(130);
            scrollSelection.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
            scrollSelection.setVbarPolicy(ScrollBarPolicy.NEVER);
            initializeScrollPane();

            updateCheckBox();

            getChildren().addAll(border, title, workerDisplay, closeButton, scrollSelection);

        }

        public void changeWorkedTile(Tile tile, boolean change) {

            //add or remove tiles to worked tiles according to the change
            if (change) {
                city.getWorkedTiles().add(tile);
            } else {
                city.getWorkedTiles().remove(tile);
            }

            //update things of this checkbox
            updateText();
            updateCheckBox();

            //reset city income
            city.calcIncome();
            updateProductionDisplay();
            updateDisplayOrder();
        }

        private void updateCheckBox() {
            //Selections should be capped if you can select n omore workers
            if (city.getWorkedTiles().size() >= city.getPopulation()) {
                for (Node n : collection.getChildren()) {
                    if (n instanceof TileSelection) {
                        if (!((TileSelection) n).getCheckBox().selectedProperty().getValue()) {
                            ((TileSelection) n).disableCheckBox(true);
                        }
                    }
                }
            } else {
                //Allow all selections
                for (Node n : collection.getChildren()) {
                    if (n instanceof TileSelection) {
                        ((TileSelection) n).disableCheckBox(false);
                    }
                }
            }
        }

        private void updateText() {
            workerDisplay.setText("Available Worker: " + Integer.toString(city.getPopulation() - city.getWorkedTiles().size()));
        }

        private void updateDisplayOrder() {

            FXCollections.sort(collection.getChildren(), (Node o1, Node o2) -> {
                return ((TileSelection) o1).compareTo((TileSelection) o2);
            });

            for (int i = 0; i < collection.getChildren().size(); i++) {
                collection.getChildren().get(i).setTranslateX(i * 180);
            }
        }

        private void initializeScrollPane() {

            collection = new Group();

            for (Tile t : city.getOwnedTiles()) {
                if (city.getWorkedTiles().contains(t)) {
                    collection.getChildren().add(new TileSelection(t, true));
                } else {
                    collection.getChildren().add(new TileSelection(t, false));
                }
            }

            updateDisplayOrder();
            scrollSelection.setContent(collection);

        }

        private void close() {
            if (getParent() instanceof CityMenu) {
                ((CityMenu) getParent()).removeOption(this);
            }
        }

        private class TileSelection extends Group implements Comparable<TileSelection> {

            Tile tile;
            City city;

            Rectangle border;
            Polygon tileShape;
            Canvas canvas;
            Text infoText;
            CheckBox checkBox;

            public TileSelection(Tile tile, boolean selected) {
                this.tile = tile;
                city = tile.getControllingCity();

                border = new Rectangle(180, 180);
                border.setFill(Color.BLACK);
                border.setStrokeWidth(5);
                border.setStroke(Color.WHEAT);

                infoText = new Text(getInfo());
                infoText.setFill(Color.WHITESMOKE);
                infoText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
                infoText.setTranslateY(140);
                infoText.setTranslateX(10);

                tileShape = new Polygon();
                tileShape.getPoints().addAll(new Double[]{50.0, 0.0, 100.0, 30.0, 100.0, 80.0, 50.0, 110.0, 0.0, 80.0, 0.0, 30.0});
                tileShape.setFill(new ImagePattern(ImageBuffer.getImage(tile)));
                tileShape.setTranslateX(5);
                tileShape.setTranslateY(5);

                checkBox = new CheckBox("WORK");
                checkBox.setSelected(selected);
                checkBox.setTranslateX(115);
                checkBox.setTranslateY(25);
                checkBox.setTextFill(Color.WHITE);
                checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    checkBoxAction(old_val, new_val);
                });

                canvas = new Canvas(100, 110);
                canvas.setTranslateX(5);
                canvas.setTranslateY(5);
                updateCanvas();

                getChildren().addAll(border, infoText, tileShape, canvas, checkBox);

            }

            private void checkBoxAction(boolean oldValue, boolean newValue) {
                if (oldValue != newValue) {
                    changeWorkedTile(tile, newValue);
                }
            }

            private void updateCanvas() {
                GraphicsContext gc = canvas.getGraphicsContext2D();

                if (tile.getResource() != Resource.NONE) {
                    gc.drawImage(ImageBuffer.getImage(tile.getResource()), getTranslateX() + 55, getTranslateY() + 35);
                }

                if (tile.getImprovement() != Improvement.NONE) {
                    gc.drawImage(ImageBuffer.getImage(tile.getImprovement()), 5 + getTranslateX(), 20 + getTranslateY());
                }

            }

            private String getInfo() {

                String s = "";

                s = s + "Food: " + tile.getFoodOutput();
                s = s + "\t\t Gold: " + tile.getGoldOutput();
                s = s + "\nProduction: " + tile.getProductionOutput();
                s = s + "\t Tech: " + tile.getScienceOutput();

                return s;
            }

            public void disableCheckBox(boolean enable) {
                checkBox.setDisable(enable);
            }

            public CheckBox getCheckBox() {
                return checkBox;
            }

            @Override
            public int compareTo(TileSelection o) {

                boolean thisSelected = getCheckBox().selectedProperty().getValue();
                boolean thatSelected = o.getCheckBox().selectedProperty().getValue();

                if (thisSelected == thatSelected) {
                    return 0;
                } else if (thisSelected) {
                    return -1;
                }

                return 1;
            }
        }

    }
    //<e/ditor-fold>
}
