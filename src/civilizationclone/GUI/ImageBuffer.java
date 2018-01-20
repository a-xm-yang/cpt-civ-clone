package civilizationclone.GUI;

import civilizationclone.CityProject;
import civilizationclone.Tile.*;
import civilizationclone.Unit.*;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.image.Image;

enum MiscAsset {
    CLOSE_ICON, CONFIRM_ICON, CITY_BACKGROUND, CITY_OPTION_BACKGROUND, WARNING, CITIZEN_ICON, MONEY_ICON, PRODUCTION_ICON, CLOUD, EXPANSION_ICON;
}

public class ImageBuffer {

    private static Image desert;
    private static Image hills;
    private static Image mountain;
    private static Image ocean;
    private static Image plains;
    private static Image city;

    private static Image archer;
    private static Image builder;
    private static Image catapult;
    private static Image destroyer;
    private static Image galley;
    private static Image scout;
    private static Image settler;
    private static Image swordsman;
    private static Image slinger;
    private static Image warrior;

    private static Image closeIcon;
    private static Image confirmIcon;
    private static Image cityBackground;
    private static Image cityOptionBackground;
    private static Image warning;
    private static Image citizenIcon;
    private static Image productionIcon;
    private static Image moneyIcon;
    private static Image expansionIcon;
    private static Image cloud;

    private static Image farm;
    private static Image mine;

    static {
        try {
            desert = new Image(new FileInputStream("src/Assets/Tiles/Desert.png"), 100, 110, false, false);
            hills = new Image(new FileInputStream("src/Assets/Tiles/Hills.png"), 100, 110, false, false);
            mountain = new Image(new FileInputStream("src/Assets/Tiles/Mountain.png"), 100, 110, false, false);
            ocean = new Image(new FileInputStream("src/Assets/Tiles/Ocean.png"), 100, 110, false, false);
            plains = new Image(new FileInputStream("src/Assets/Tiles/Plains.png"), 100, 110, false, false);
            city = new Image(new FileInputStream("src/Assets/Tiles/City.png"), 90, 90, false, false);

            archer = new Image(new FileInputStream("src/Assets/Units/Archer.png"), 70, 70, false, false);
            builder = new Image(new FileInputStream("src/Assets/Units/Builder.png"), 70, 70, false, false);
            catapult = new Image(new FileInputStream("src/Assets/Units/Catapult.png"), 70, 70, false, false);
            destroyer = new Image(new FileInputStream("src/Assets/Units/Destroyer.png"), 70, 70, false, false);
            galley = new Image(new FileInputStream("src/Assets/Units/Galley.png"), 70, 70, false, false);
            scout = new Image(new FileInputStream("src/Assets/Units/Scout.png"), 70, 70, false, false);
            settler = new Image(new FileInputStream("src/Assets/Units/Settler.png"), 70, 70, false, false);
            swordsman = new Image(new FileInputStream("src/Assets/Units/Swordsman.png"), 70, 70, false, false);
            slinger = new Image(new FileInputStream("src/Assets/Units/Slinger.png"), 70, 70, false, false);
            warrior = new Image(new FileInputStream("src/Assets/Units/Warrior.png"), 70, 70, false, false);

            closeIcon = new Image(new FileInputStream("src/Assets/Misc/close_button.png"), 60, 60, false, false);
            confirmIcon = new Image(new FileInputStream("src/Assets/Misc/confirm_icon.png"), 60, 60, true, false);
            cityBackground = new Image(new FileInputStream("src/Assets/Misc/city_background.jpg"), 1080, 1920, false, false);
            cityOptionBackground = new Image(new FileInputStream("src/Assets/Misc/city_option_background.jpg"), 1280, 800, false, false);
            warning = new Image(new FileInputStream("src/Assets/Misc/warning.png"), 70, 70, false, false);
            citizenIcon = new Image(new FileInputStream("src/Assets/Misc/citizen_icon.png"), 120, 120, false, false);
            productionIcon = new Image(new FileInputStream("src/Assets/Misc/production_icon.png"), 120, 120, false, false);
            moneyIcon = new Image(new FileInputStream("src/Assets/Misc/money_icon.png"), 120, 120, false, false);
            cloud = new Image(new FileInputStream("src/Assets/Misc/Cloud.jpg"), 225, 225, false, false);
            expansionIcon = new Image(new FileInputStream("src/Assets/Misc/expand_icon.png"), 75, 75, false, false);

            farm = new Image(new FileInputStream("src/Assets/Improvement/farm.png"), 70, 70, false, false);
            mine = new Image(new FileInputStream("src/Assets/Improvement/mine.png"), 70, 70, false, false);

        } catch (IOException e) {
            System.out.println("Image loading failed");
            System.out.println(e.getStackTrace());
        }
    }

    public static Image getImage(Tile tile) {
        if (tile instanceof Ocean) {
            return ocean;
        } else if (tile instanceof Plains) {
            return plains;
        } else if (tile instanceof Hills) {
            return hills;
        } else if (tile instanceof Desert) {
            return desert;
        } else {
            return mountain;
        }

    }

    public static Image getImage(Unit unit) {
        //Must be added more
        if (unit instanceof BuilderUnit) {
            return builder;
        } else if (unit instanceof ScoutUnit) {
            return scout;
        } else if (unit instanceof WarriorUnit) {
            return warrior;
        } else if (unit instanceof SettlerUnit) {
            return settler;
        } else if (unit instanceof SlingerUnit) {
            return slinger;
        } else if (unit instanceof ArcherUnit) {
            return archer;
        }

        return destroyer;
    }

    public static Image getImage(UnitType unit) {
        switch (unit) {
            case BUILDER:
                return builder;
            case WARRIOR:
                return warrior;
            case SCOUT:
                return scout;
            case SLINGER:
                return slinger;
            case SETTLER:
                return settler;
            case ARCHER:
                return archer;

        }

        return destroyer;
    }

    public static Image getImage(Improvement i) {
        switch (i) {
            case FARM:
                return farm;
            case MINE:
                return mine;
        }

        return destroyer;
    }

    public static Image getImage(Resource r) {
        //Must be added more
        return destroyer;
    }

    public static Image getImage(CityProject c) {
        //need to add more
        return destroyer;

    }

    public static Image getImage(MiscAsset m) {
        switch (m) {
            case CLOSE_ICON:
                return closeIcon;
            case CITY_BACKGROUND:
                return cityBackground;
            case CITY_OPTION_BACKGROUND:
                return cityOptionBackground;
            case WARNING:
                return warning;
            case MONEY_ICON:
                return moneyIcon;
            case PRODUCTION_ICON:
                return productionIcon;
            case CITIZEN_ICON:
                return citizenIcon;
            case CONFIRM_ICON:
                return confirmIcon;
            case CLOUD:
                return cloud;
            case EXPANSION_ICON:
                return expansionIcon;
        }

        return destroyer;
    }

    public static Image getCityImage() {
        return city;
    }

}
