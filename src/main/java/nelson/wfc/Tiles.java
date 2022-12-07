package nelson.wfc;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Tiles {

    enum Tile {
        BLANK, UP, RIGHT, DOWN, LEFT
    }

    Map<Tile, Image> images = new HashMap<>();

    Tiles() {
        images.put(Tile.BLANK,  load("blank"));
        images.put(Tile.UP,     load("up"));
        images.put(Tile.RIGHT,  load("right"));
        images.put(Tile.DOWN,   load("down"));
        images.put(Tile.LEFT,   load("left"));
    }

    Image get(Tile index) {
        return images.get(index);
    }

    int getWidth() {
        return 30;
    }

    int getHeight() {
        return 30;
    }

    private Image load(String name) {
        final InputStream stream = Tiles.class.getClassLoader().getResourceAsStream(name + ".png");
        return new Image(stream);
    }

}
