package civilizationclone.GUI;

import civilizationclone.City;
import civilizationclone.CityProject;
import civilizationclone.Unit.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CityMenu extends Pane {

    private City city;
    private ZoomMap zoomMapRef;

    //graphics related members
    private Rectangle border;
    private Text statusText, cityNameText;
    private Circle closeButton;
    private ImageView productionIcon, purchaseIcon, citizenIcon, productionDisplay;
    private boolean hasOptionOpen;

    public CityMenu(City city, int resX, int resY, ZoomMap zoomMapRef) {

        this.city = city;
        this.zoomMapRef = zoomMapRef;

        boolean hasOptionOpen = false;

        border = new Rectangle(0, 0, 460, resY + 10);
        setTranslateX(resX - 450);
        ImagePattern cityBackground = new ImagePattern(ImageBuffer.getImage(MiscAsset.CITY_BACKGROUND), 0, 0, 1, 1, true);
        border.setFill(cityBackground);

        statusText = new Text(displayCityInfo());
        statusText.setFont(Font.font("Times New Roman", 15));
        statusText.setFill(Color.WHITE);
        statusText.setTranslateY(120);
        statusText.setTranslateX(25);

        cityNameText = new Text(city.getName());
        cityNameText.setFont(Font.font("Oswald", 50));
        cityNameText.setFill(Color.CORNSILK);
        cityNameText.setTranslateX(30);
        cityNameText.setTranslateY(60);

        closeButton = new Circle(420, 40, 30);
        closeButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
        closeButton.setOnMouseClicked((e) -> {
            e.consume();
            close();
        });

        productionDisplay = new ImageView();
        productionDisplay.setTranslateX(300);
        productionDisplay.setTranslateY(150);
        updateProductionDisplay();

        productionIcon = new ImageView(ImageBuffer.getImage(MiscAsset.PRODUCTION_ICON));
        productionIcon.setTranslateX(175);
        productionIcon.setTranslateY(340);
        productionIcon.setOnMouseClicked((MouseEvent e) -> {
            if (!hasOptionOpen()) {
                getChildren().add(new ProductionMenu(city, resX, resY));
                setHasOptionOpen(true);
            }
            e.consume();
        });

        purchaseIcon = new ImageView(ImageBuffer.getImage(MiscAsset.MONEY_ICON));
        purchaseIcon.setTranslateX(175);
        purchaseIcon.setTranslateY(540);
        purchaseIcon.setOnMouseClicked((MouseEvent e) -> {
            if (!hasOptionOpen()) {
                getChildren().add(new PurchaseMenu(city, resX, resY));
                setHasOptionOpen(true);
            }
            e.consume();
        });

        citizenIcon = new ImageView(ImageBuffer.getImage(MiscAsset.CITIZEN_ICON));
        citizenIcon.setTranslateX(175);
        citizenIcon.setTranslateY(740);

        setOnMouseClicked(e -> {
            System.out.println("X: " + e.getX());
            System.out.println("Y: " + e.getY());
            e.consume();
        });

        getChildren().addAll(border, statusText, cityNameText, closeButton, productionDisplay, productionIcon, purchaseIcon, citizenIcon);

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

        msg = msg + "Population: " + city.getPopulation();
        msg = msg + "\nFood income: " + city.getFoodIncome();
        msg = msg + "\nTech income: " + city.getTechIncome();
        msg = msg + "\nGold income: " + city.getGoldIncome();
        msg = msg + "\nProduction income: " + city.getProductionIncome();

        if (city.getCurrentProject() != null) {
            msg = msg + "\n\n     Current Project: " + city.getCurrentProject().name();
            msg = msg + "\n    Progress: " + city.getCurrentProduction() + "/" + city.getCurrentProject().getProductionCost();
        } else if (city.getCurrentUnit() != null) {
            msg = msg + "\n\n     Current Project: " + city.getCurrentUnit().name();
            msg = msg + "\n     Progress: " + city.getCurrentProduction() + "/" + city.getCurrentUnit().getProductionCost();
        } else {
            msg = msg + "\n\n    Nothing is being produced!";
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

        //should be able to start dragginag again after menu is closed
        //  zoomMapRef.enableDragging(true);
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

            closeButton = new Circle(530, 385, 30);
            closeButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
            closeButton.setOnMouseClicked((e) -> {
                e.consume();
                close();
            });

            confirmButton = new Circle(450, 385, 22);
            confirmButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CONFIRM_ICON)));
            confirmButton.setOpacity(0.1);
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
                confirmButton.setOpacity(0.1);
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
                if (c.name().equals(selection)) {
                    info.setText(getInfo(c));
                    display.setImage(ImageBuffer.getImage(c));

                    canConfirm = true;
                    confirmButton.setOpacity(1);
                    info.setVisible(true);
                    display.setVisible(true);
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
                options.add(t.name());
            }

            options.add("----- PROJECTS -----");

            for (CityProject c : city.getPlayer().getOwnedCityProject()) {
                options.add(c.name());
            }

            comboBox = new ComboBox(options);
            comboBox.setTranslateX(70);
            comboBox.setTranslateY(80);
            comboBox.setPromptText("--- Please Select ---");
            comboBox.setVisibleRowCount(8);

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

            closeButton = new Circle(530, 385, 30);
            closeButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CLOSE_ICON)));
            closeButton.setOnMouseClicked((e) -> {
                e.consume();
                close();
            });

            confirmButton = new Circle(450, 385, 22);
            confirmButton.setFill(new ImagePattern(ImageBuffer.getImage(MiscAsset.CONFIRM_ICON)));
            confirmButton.setOpacity(0.1);
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
                confirmButton.setOpacity(0.1);
                info.setVisible(false);
                display.setVisible(false);
                canConfirm = false;
                return;
            }

            //Determine what exactly did the person click
            for (UnitType t : city.getPlayer().getBuildableUnit()) {
                if (t.name().equals(selection)) {

                    if (city.getPlayer().getCurrentGold() >= t.getProductionCost()) {
                        canConfirm = true;
                        confirmButton.setOpacity(1);
                    } else {
                        confirmButton.setOpacity(0.1);
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
                if (c.name().equals(selection)) {

                    if (city.getPlayer().getCurrentGold() >= c.getProductionCost()) {
                        canConfirm = true;
                        confirmButton.setOpacity(1);
                    } else {
                        confirmButton.setOpacity(0.1);
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
                            city.getPlayer().getUnitList().get(city.getPlayer().getUnitList().size()-1).setMovement(5);
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
                ((CityMenu) getParent()).close();
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
                options.add(c.name());
            }

            comboBox = new ComboBox(options);
            comboBox.setTranslateX(70);
            comboBox.setTranslateY(80);
            comboBox.setPromptText("--- Please Select ---");
            comboBox.setVisibleRowCount(8);

        }
    }

    private class CitizenMenu extends Pane {

        Rectangle border;
    }

    //<e/ditor-fold>
}
