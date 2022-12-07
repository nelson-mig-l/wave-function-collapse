package nelson.wfc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridTest {

    @Test
    void test() {
        final Grid grid = new Grid();
        final List<Tiles.Tile> arr = new ArrayList<>(Arrays.asList(Tiles.Tile.BLANK, Tiles.Tile.UP, Tiles.Tile.RIGHT, Tiles.Tile.LEFT, Tiles.Tile.DOWN));
        final List<Tiles.Tile> valid = Arrays.asList(Tiles.Tile.BLANK, Tiles.Tile.RIGHT);
        grid.checkValid(arr, valid);
        System.out.println(valid);
    }

}
