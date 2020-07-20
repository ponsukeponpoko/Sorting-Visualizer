package Sort;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Rectangles {
	private Rectangle _rect;

	public Rectangles(Pane pane2, double height, double width) {
		_rect = new Rectangle(width, height);
		_rect.setFill(Color.PINK);
		_rect.setStroke(Color.BLACK);
		pane2.getChildren().addAll(_rect);
		this.setXLoc(0);
		this.setYLoc(0);
	}

	public void setXLoc(double x) {
		_rect.setX(x);
	}

	public void setYLoc(double y) {
		_rect.setY(y);
	}

	public void setColor(Color color) {
		_rect.setFill(color);
	}

	public double getXLoc() {
		return _rect.getX();
	}

	public double getYLoc() {
		return _rect.getY();
	}

	public Rectangle getRects() {
		// getRects method gets the shape rectangle.
		return _rect;
	}

	public void setHeight(double h) {
		_rect.setHeight(h);
	}
}
