package catMouseGame;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Milk {
	private Image milkImage;
	private String imgName;
	private int x, y;
	private int width, height;
	private boolean state = true;

	public Milk(int x, int y, String imgName) {
		this.imgName = imgName;
		this.milkImage = new ImageIcon("images/milk" + imgName + ".png").getImage();
		width = milkImage.getWidth(null);
		height = milkImage.getHeight(null);
		this.x = x - width / 2;
		this.y = y - height;

	}

	public void setState(boolean state) {
		this.state = state;
	}

	public Image getImage() {
		return milkImage;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
