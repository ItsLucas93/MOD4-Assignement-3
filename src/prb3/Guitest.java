import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;


public class Guitest extends Application {

    // Parameters
    final static int maxEntity = 200, capSpeed = 5, maxSize = 20;
    private Paint[] colorGame = {Color.BLUE, Color.RED, Color.ORANGE};

    // Various class variables and constants
    private Pane root = new Pane();
    private Random rand = new Random();
    private static int circleID = 0, rectangleID = 0;
    private boolean pause = false, agario = false;
    private Text t = new Text();

    // Various tables
    private CircleGame[] circles = new CircleGame[maxEntity];
    private Rectangle[] rectangles = new Rectangle[maxEntity/2];
    private StatText[] statTexts = new StatText[colorGame.length];    

    public static void main(String[] args) {
        launch(args);
    }

    private Parent createContent() {
        root = new Pane(); // Create a root Pane for the JavaFX application
        return root;
    }

    // Create a new CircleGame object at the specified position
    private void createCircleGame(double posx, double posy){
        boolean nouv = false;
        int team = rand.nextInt(colorGame.length);
        if (circles[circleID] == null){
            circles[circleID] = new CircleGame(rand.nextInt(maxSize)+1, colorGame[team]);
            circles[circleID].team = team;
            nouv = true;
        }
        circles[circleID].setCenterX(posx);
        circles[circleID].setCenterY(posy);

        circles[circleID].dx = rand.nextInt(capSpeed)-capSpeed/2;
        circles[circleID].dy = rand.nextInt(capSpeed)-capSpeed/2;
        bounce(circleID);
        if (nouv){
            root.getChildren().add(circles[circleID]);
        }
        circleID = (circleID + 1)%maxEntity;
    }

    // Create a new Rectangle object at the specified position
    private void createBrickGame(double posx, double posy){

        double size = maxSize*2;

        rectangles[rectangleID] = new Rectangle(size*2,size, new Color(Math.random(), Math.random(), Math.random(), 1));

        rectangles[rectangleID].setX(posx);
        rectangles[rectangleID].setY(posy);

        root.getChildren().add(rectangles[rectangleID]);

        rectangleID = (rectangleID + 1)%maxEntity;
    }

    // Handle collision between two CircleGame objects in agario gamerule
    private void agarioCollision(CircleGame circle1, CircleGame circle2, int id){
        if(circle2.getRadius()<=maxSize/2){
            circle1.setRadius(circle1.getRadius() + circle2.getRadius()/circle1.getRadius()*2);
            root.getChildren().remove(circle2);
            circles[id] = null;
        }else{
            circle1.setRadius(circle1.getRadius() + maxSize/circle1.getRadius()*2);

            circle2.setRadius(circle2.getRadius() - maxSize/2);
        }

        if (circle2.getRadius() < maxSize/4){
            root.getChildren().remove(circle2);
            circles[id] = null;
        }
    }

    // Handle collision between two CircleGame objects
    private void collisionBehaviourCircle(){
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.006), event -> {
                if(pause){return;}
                for (int i = 0; i < circles.length; i++) {
                    for (int id = 0; id < circles.length; id++) {
                        if(circles[i] != null && circles[id] != null && i!=id){
                            if (circles[id].getCenterX() + circles[id].dx + circles[id].getRadius() > circles[i].getCenterX() + circles[i].dx - circles[i].getRadius() && 
                                circles[id].getCenterX() + circles[id].dx + circles[id].getRadius() < circles[i].getCenterX() + circles[i].dx + circles[i].getRadius() &&
                                circles[id].getCenterY() + circles[id].dy + circles[id].getRadius() > circles[i].getCenterY() + circles[i].dy - circles[i].getRadius() &&
                                circles[id].getCenterY() + circles[id].dy + circles[id].getRadius() < circles[i].getCenterY() + circles[i].dy + circles[i].getRadius() ||
                                circles[id].getCenterX() + circles[id].dx - circles[id].getRadius() < circles[i].getCenterX() + circles[i].dx + circles[i].getRadius() && 
                                circles[id].getCenterX() + circles[id].dx - circles[id].getRadius() > circles[i].getCenterX() + circles[i].dx - circles[i].getRadius() &&
                                circles[id].getCenterY() + circles[id].dy - circles[id].getRadius() < circles[i].getCenterY() + circles[i].dy + circles[i].getRadius() &&
                                circles[id].getCenterY() + circles[id].dy - circles[id].getRadius() > circles[i].getCenterY() + circles[i].dy - circles[i].getRadius() ){

                                double tempx=circles[id].dx, tempy=circles[id].dy;
                                circles[id].dx = circles[i].dx + rand.nextInt(capSpeed)-capSpeed/2;
                                circles[i].dx = tempx + rand.nextInt(capSpeed)-capSpeed/2;
                                circles[id].dy = circles[i].dy + rand.nextInt(capSpeed)-capSpeed/2;
                                circles[i].dy = tempy + rand.nextInt(capSpeed)-capSpeed/2;
                                try{

                                    if(circles[id].getColor() != circles[i].getColor()){

                                        if((circles[id].team+1)%colorGame.length == circles[i].team){
                                            
                                            if(agario){
                                                agarioCollision(circles[i],circles[id],id);
                                            }else{
                                                circles[i].team = circles[id].team;
                                                circles[i].setColor(circles[id].getColor());
                                            }
                                        }
                                        if((((circles[id].team-1)%colorGame.length)+colorGame.length)%colorGame.length == circles[i].team){
                                            
                                            if(agario){
                                                agarioCollision(circles[id],circles[i],i);
                                            }else{
                                                circles[id].team = circles[i].team;
                                                circles[id].setColor(circles[i].getColor());
                                            }
                                        }
                                    }
                                }catch(NullPointerException e){}
                            }
                        }
                    }
                }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
                
    }

    // Handle collision behavior for Rectangle objects
    private void collisionBehaviourRectangle(){
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.006), event -> {
                if(pause){return;}
                for (int i = 0; i < rectangles.length; i++) {
                    for (int id = 0; id < circles.length; id++) {
                        if(rectangles[i] != null && circles[id] != null){

                            // Calculate the center of the circle taking into account its current position and motion
                            double circleCenterX = circles[id].getCenterX() + circles[id].dx;
                            double circleCenterY = circles[id].getCenterY() + circles[id].dy;
                            double circleRadius = circles[id].getRadius();

                            // Calculate the center of the rectangle
                            double rectCenterX = rectangles[i].getX() + rectangles[i].getWidth() / 2;
                            double rectCenterY = rectangles[i].getY() + rectangles[i].getHeight() / 2;

                            // Calculate the minimum and maximum distances between the centers of the circle and rectangle
                            double deltaX = circleCenterX - rectCenterX;
                            double deltaY = circleCenterY - rectCenterY;
                            double minDistX = rectangles[i].getWidth() / 2 + circleRadius;
                            double minDistY = rectangles[i].getHeight() / 2 + circleRadius;

                            // Check if any part of the circle intersects with the rectangle
                            if (Math.abs(deltaX) <= minDistX && Math.abs(deltaY) <= minDistY) {


                                // Determine the collision direction
                                double overlapX = minDistX - Math.abs(deltaX);
                                double overlapY = minDistY - Math.abs(deltaY);

                                if (overlapX < overlapY) {
                                    // Collision is primarily horizontal (from left or right)
                                    circles[id].dx = -circles[id].dx;
                                } else {
                                    // Collision is primarily vertical (from top or bottom)
                                    circles[id].dy = -circles[id].dy;
                                }
                                root.getChildren().remove(rectangles[i]);
                                rectangles[i] = null;

                            }
                        }
                    }
                }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
                
    }

    // Implement bouncing behavior for CircleGame objects
    private void bounce(int id){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.016), event -> {
                    if(pause || circles [id] == null){return;}
                    double newX = circles[id].getCenterX() + circles[id].dx;
                    double newY = circles[id].getCenterY() + circles[id].dy;

                    if (newX - circles[id].getRadius() < 0 || newX + circles[id].getRadius() > root.getWidth()) {
                        circles[id].dx = -circles[id].dx + rand.nextInt(capSpeed)-capSpeed/2;
                        //circles[id].setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
                    }
                    if (newY - circles[id].getRadius() < 0 || newY + circles[id].getRadius() > root.getHeight()) {
                        circles[id].dy = -circles[id].dy + rand.nextInt(capSpeed)-capSpeed/2;
                        //circles[id].setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
                    }

                    if(circles[id].dx==0 || circles[id].dx>capSpeed || circles[id].dx<-capSpeed){
                        if(rand.nextBoolean()){
                            circles[id].dx=1;
                        }else{
                            circles[id].dx=-1;
                        }
                        
                    }
                    if(circles[id].dy==0 || circles[id].dy>capSpeed || circles[id].dy<-capSpeed){
                        if(rand.nextBoolean()){
                            circles[id].dy=1;
                        }else{
                            circles[id].dy=-1;
                        }
                    }

                    circles[id].setCenterX(circles[id].getCenterX() + circles[id].dx);
                    circles[id].setCenterY(circles[id].getCenterY() + circles[id].dy);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // Toggle the pause state of the game
    private void pauseState(){
        if(!pause){
            t.setText("PAUSED");
            t.setFont(Font.font ("Verdana", 50));
            t.setFill(Color.RED);
            t.setX(root.getWidth()/2-80);
            t.setY(root.getHeight()/2);
            root.getChildren().add(t);
        }else{
            root.getChildren().remove(t);
        }   
        pause = !pause;
    }

    // Display statistics for each color team
    private void statsText(){
        for (int i = 0; i < statTexts.length; i++) {
            statTexts[i] = new StatText();
            statTexts[i].setText("Color: ");
            statTexts[i].colorStat = colorGame[i].toString();
            statTexts[i].setFont(Font.font ("Verdana", 20));
            statTexts[i].setFill(Color.BLACK);
            statTexts[i].setX(10);
            statTexts[i].setY(100);
            root.getChildren().add(statTexts[i]);
        }
        int scores[] = new int[colorGame.length];
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.030), event -> {
                for (int i = 0; i < scores.length; i++)
                    scores[i] = 0;
                
                for (CircleGame circl : circles) {
                    if(circl != null){
                        scores[circl.team]++;
                    }
                }
                for (int i = 0; i < statTexts.length; i++) {
                    if(statTexts[i] != null){
                        statTexts[i].setText(statTexts[i].colorStat +": "+String.valueOf(scores[i]));
                        statTexts[i].setX(10);
                        statTexts[i].setY(100+20*i);
                    }
                }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent(), 600, 600, Color.GRAY);
        stage.setScene(scene);

        // Initialize collision behaviors, stats display, and event handling
        collisionBehaviourCircle();
        collisionBehaviourRectangle();
        statsText();

        // Handle mouse clicks for creating CircleGame and Rectangle objects
        scene.setOnMouseClicked(event -> {
            if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
                double mouseX = event.getSceneX();
                double mouseY = event.getSceneY();
                if (event.getButton() == MouseButton.PRIMARY) { // Check if it's a left-click event

                    createCircleGame(mouseX, mouseY);
                }else if(event.getButton() == MouseButton.SECONDARY){ // Check if it's a right-click event
                    
                    createBrickGame(mouseX, mouseY);
                }
            }
        });

        // Handle keyboard events for pausing, resetting, adding circles, and toggling game mode
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.SPACE){
                    pauseState();
                }
                if(event.getCode() == KeyCode.R){
                    for (int i = 0; i < circles.length; i++) {
                        root.getChildren().remove(circles[i]);
                        circles[i] = null;
                    }
                }
                if(event.getCode() == KeyCode.A){
                    for (int i = 0; i < 10; i++) {
                        createCircleGame(rand.nextDouble(root.getWidth()), rand.nextDouble(root.getHeight()));
                    }
                }
                if(event.getCode() == KeyCode.T){
                    agario = !agario;
                }
            }
        });

        stage.show();
    }

}

