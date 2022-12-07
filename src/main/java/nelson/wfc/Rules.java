package nelson.wfc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nelson.wfc.Tiles.Tile.*;

public class Rules {
    enum Direction {
        UP, RIGHT, DOWN, LEFT;
    }

    Map<Tiles.Tile, Map<Direction, List<Tiles.Tile>>> definition = new HashMap<>();

    Rules() {
        definition.put(BLANK, ruleOf( //
                arr(BLANK, UP),    // ┴
                arr(BLANK, RIGHT), // ├
                arr(BLANK, DOWN),  // ┬
                arr(BLANK, LEFT)   // ┤
        ));
        definition.put(UP, ruleOf( // ┴
                arr(RIGHT, LEFT, DOWN),// ├ ┤ ┬
                arr(LEFT, UP, DOWN),   // ┤ ┴ ┬
                arr(BLANK, DOWN),      // ┬
                arr(RIGHT, UP, DOWN)   // ├ ┴ ┬
        ));
        definition.put(RIGHT, ruleOf( // ├
                arr(RIGHT, LEFT, DOWN), // ├ ┤ ┬
                arr(LEFT, UP, DOWN),    // ┤ ┴ ┬
                arr(RIGHT, LEFT, UP),   // ├ ┤ ┴
                arr(BLANK, LEFT)        // ┤
        ));
        definition.put(DOWN, ruleOf( // ┬
                arr(BLANK, UP),       // ┴
                arr(LEFT, UP, DOWN),  // ┤ ┴ ┬
                arr(RIGHT, LEFT, UP), // ├ ┤ ┴
                arr(RIGHT, UP, DOWN)  // ├ ┴ ┬
        ));
        definition.put(LEFT, ruleOf( // ┤
                arr(RIGHT, LEFT, DOWN), // ├ ┤ ┬
                arr(BLANK, RIGHT),      // ├
                arr(RIGHT, LEFT, UP),   // ├ ┤ ┴
                arr(UP, DOWN, RIGHT)    // ┴ ┬ ├
        ));
    }

    private List<Tiles.Tile> arr(Tiles.Tile... values) {
        return Arrays.asList(values);
    }

    private Map<Direction, List<Tiles.Tile>> ruleOf(List<Tiles.Tile> up, List<Tiles.Tile> right, List<Tiles.Tile> down, List<Tiles.Tile> left) {
        Map<Direction, List<Tiles.Tile>> result = new HashMap<>();
        result.put(Direction.UP, up);
        result.put(Direction.RIGHT, right);
        result.put(Direction.DOWN, down);
        result.put(Direction.LEFT, left);
        return result;
    }
}
