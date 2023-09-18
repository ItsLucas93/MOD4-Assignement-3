import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class CircleGame extends Circle {
    public double dx, dy;
    public int team;
    private Paint color;
    
    public CircleGame(){
        super();
    }

    public CircleGame(int size, Paint color){
        super(size, color);
        this.color = color;
    }

    public void setColor(Paint color){
        this.color = color;
        this.setFill(color);
    }

    public Paint getColor(){
        return color;
    }
}
