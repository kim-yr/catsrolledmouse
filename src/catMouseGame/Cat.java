package catMouseGame;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Cat {
	private Image catImg;
	private int x, y;
	private int width, height;

	public Cat(int x, int y, String imgName) {
		this.x = x;
		this.y = y;
		catImg = new ImageIcon("images/" + imgName + ".png").getImage();
		width = catImg.getWidth(null);
		height = catImg.getHeight(null);
	}

	public Image getImg() {
		return catImg;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
