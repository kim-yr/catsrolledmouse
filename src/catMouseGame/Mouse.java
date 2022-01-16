package catMouseGame;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Mouse {
	private Image mouseImage = new ImageIcon("images/mouse.png").getImage();
	private String imgName;
	private int x, y;
	private int width, height;
	private int power = 0;
	private int limit = 0;

	public Mouse(int x, int y, int powerScore) {
		this.width = mouseImage.getWidth(null);
		this.height = mouseImage.getHeight(null);
		this.x = x - width / 2;
		this.y = y - height / 2;
		if (powerScore >= 475 && powerScore < 575) {
			power = 50;
			limit = 250 - (100 - (powerScore - 475));
		} else if (powerScore >= 575 && powerScore < 675) {
			power = 40;
			limit = 300 - (int) (100 - (powerScore - 575)) / 2;
		} else if (powerScore >= 675 && powerScore <= 775) {
			power = 30;
			limit = 450 - (int) (100 - (powerScore - 675)) / 2;
		}
	}

	public Image getImage() {
		return mouseImage;
	}

	public void shoot() {
		y -= power;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getLimit() {
		return limit;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
