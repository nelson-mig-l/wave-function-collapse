package nelson.wfc;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.*;
import java.util.stream.Collectors;

public class Grid {

    private static final int DIM = 10;
    private final Cell[] cells = new Cell[DIM * DIM];

    long seed = new Random().nextLong(); // ∙
    private final Random random = new Random(seed);

    static class Cell {
        List<Tiles.Tile> options = new ArrayList<>(Arrays.asList(Tiles.Tile.values()));
        private boolean collapsed = false;

        boolean isCollapsed() {
            //return options.size() == 1;
            return collapsed;
        }

        @Override
        public String toString() {
            return options.toString();
        }

        public void collapse() {
            collapsed = true;
        }
    }

    Grid() {
        System.out.println("SEED: " + seed);
        for (int i = 0; i < DIM * DIM; i++) {
            cells[i] = new Cell();
        }
        //cells[0].collapsed = true;
        //cells[2].options = new int[2];
    }

    void draw(Tiles tiles, GraphicsContext gc) {
        propagate(new Rules().definition);
        collapse();

        System.out.println("=== Draw ==========================");


        final int w = tiles.getWidth();
        final int h = tiles.getHeight();
        for (int j = 0; j < DIM; j++) {
            for (int i = 0; i < DIM; i++) {
                final Cell cell = cells[i + j * DIM];
                if (cell.isCollapsed()) {
                    final Tiles.Tile index = cell.options.get(0);
                    final Image image = tiles.get(index);
                    gc.drawImage(image, i * w, j * h);
                } else {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(i * w, j * h, w, h);
                    gc.setStroke(Color.GRAY);
                    gc.setLineWidth(4);
                    gc.strokeRect(i * w, j * h, w, h);
                    gc.setFont(new Font(12));
                    gc.setFill(Color.WHITE);
                    gc.fillText(cell.options.size()+"\n"+i+","+j, i * w + 5, j * h + 11);
                }
            }
        }
    }

    private void collapse() {
        final Cell[] clone = cells.clone();
        final List<Cell> possible = Arrays.stream(clone).filter(c -> !c.isCollapsed()).collect(Collectors.toList());
        //if (possible.isEmpty()) return; // short-circuit
        // pick the cell with the least entropy
        Collections.sort(possible, Comparator.comparingInt(a -> a.options.size()));
        final int min = possible.get(0).options.size();
        final List<Cell> candidates = possible.stream()
                .filter(c -> c.options.size() == min)
                .collect(Collectors.toList());

        final Cell cell1 = candidates.get(random.nextInt(candidates.size()));
        Tiles.Tile[] opts = {cell1.options.get(random.nextInt(cell1.options.size()))};
        cell1.options = Arrays.asList(opts);
        cell1.collapse();
    }

    void checkValid(List<Tiles.Tile> arr, List<Tiles.Tile> valid) {
        arr.retainAll(valid);
    }

    private void propagate(Map<Tiles.Tile, Map<Rules.Direction, List<Tiles.Tile>>> rules) {
        for (int j = 0; j < DIM; j++) {
            for (int i = 0; i < DIM; i++) {
                final Cell cell = cells[i + j * DIM];
                System.out.println(i + "," + j + "-> " + cell);
                if (!cell.isCollapsed()) {
                    //List<Tiles.Tile> next = Arrays.asList(Tiles.Tile.values());
                    List<Tiles.Tile> options = new ArrayList<>(Arrays.asList(Tiles.Tile.values()));
                    //final Supplier<List<Tiles.Tile>> all = () -> new ArrayList<>(Arrays.asList(Tiles.Tile.values()));
                    // UP
                    if (j > 0) {
                        final Cell up = cells[i + (j - 1) * DIM];
                        final ArrayList<Tiles.Tile> validOptions = new ArrayList<>();
                        for (Tiles.Tile option : up.options) {
                            final List<Tiles.Tile> valid = rules.get(option).get(Rules.Direction.DOWN);
                            //final List<Tiles.Tile> valid = rules.get(option).get(Rules.Direction.UP);
                            validOptions.addAll(valid);
                        }
                        checkValid(options, validOptions);
                    }
                    // RIGHT
                    if (i < DIM - 1) {
                        final Cell right = cells[i + 1 + j * DIM];
                        final ArrayList<Tiles.Tile> validOptions = new ArrayList<>();
                        for (Tiles.Tile option : right.options) {
                            final List<Tiles.Tile> valid = rules.get(option).get(Rules.Direction.LEFT);
                            //final List<Tiles.Tile> valid = rules.get(option).get(Rules.Direction.RIGHT);
                            validOptions.addAll(valid);
                        }
                        checkValid(options, validOptions);
                    }
                    // DOWN
                    if (j < DIM -1) {
                        final Cell down = cells[i + (j+1) * DIM];
                        final ArrayList<Tiles.Tile> validOptions = new ArrayList<>();
                        for (Tiles.Tile option : down.options) {
                            final List<Tiles.Tile> valid = rules.get(option).get(Rules.Direction.UP);
                            //final List<Tiles.Tile> valid = rules.get(option).get(Rules.Direction.DOWN);
                            validOptions.addAll(valid);
                        }
                        checkValid(options, validOptions);
                    }
                    // LEFT
                    if (i >0) {
                        final Cell left = cells[i -1 +j * DIM];
                        final ArrayList<Tiles.Tile> validOptions = new ArrayList<>();
                        for (Tiles.Tile option : left.options) {
                            final List<Tiles.Tile> valid = rules.get(option).get(Rules.Direction.RIGHT);
                            //final List<Tiles.Tile> valid = rules.get(option).get(Rules.Direction.LEFT);
                            validOptions.addAll(valid);
                        }
                        checkValid(options, validOptions);
                    }
                    cell.options = options;
                }
                System.out.println(i + "," + j + "=> " + cell);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\n");
        for (int j = 0; j < DIM; j++) {
            for (int i = 0; i < DIM; i++) {
                final Cell cell = cells[i + j * DIM];
                if (cell.isCollapsed()) {
                    final Tiles.Tile tile = cell.options.get(0);
                    switch (tile) {
                        case BLANK: sb.append(" "); break;
                        case UP: sb.append("┴"); break;
                        case RIGHT: sb.append("├"); break;
                        case DOWN: sb.append("┬"); break;
                        case LEFT: sb.append("┤"); break;
                    }
                } else {
                    sb.append("∙");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}
