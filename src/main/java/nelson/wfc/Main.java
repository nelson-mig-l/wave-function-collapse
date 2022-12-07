package nelson.wfc;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    public static void main(String[] args) {
        //final Images images = new Images();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Group root = new Group();
        final Scene scene = new Scene(root, 400, 400, Color.BLACK);

        final Canvas canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        //


        final Tiles tiles = new Tiles();
//        gc.drawImage(tiles.get(0), 0, 0);
//        gc.drawImage(tiles.get(1), 30, 0);
//        gc.drawImage(tiles.get(2), 60, 0);
//        gc.drawImage(tiles.get(3), 90, 0);
//        gc.drawImage(tiles.get(4), 120, 0);

        final Grid grid = new Grid();

//        for (int i = 0; i < 100; i++)
//            grid.draw(tiles, gc);

        canvas.setOnMouseClicked(event -> {
            //double x = event.getX(), y = event.getY();
            //for (int i = 0; i < 100; i++)
                grid.draw(tiles, gc);
        });
        Timer tr = new Timer() ;
        tr.scheduleAtFixedRate(new TimerTask(){
            //override run methid
            @Override
            public void run(){
                //System.out.println("Timer working. . . .");
                grid.draw(tiles, gc);
                System.out.println(grid.toString());
            }
        }, 0, 100);

        //

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
