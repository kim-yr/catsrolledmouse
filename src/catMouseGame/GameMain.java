package catMouseGame;

import javax.swing.JFrame;

public class GameMain extends JFrame {
	public GameMain() {
		this.setTitle("The Cat Rolled THe Mouse");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		GamePanel gamePanel = new GamePanel();
		this.setContentPane(gamePanel);

//		this.setLocationRelativeTo(null); // ȭ�� �߾ӿ� ����
		this.setVisible(true);
		this.pack(); // ũ�⸦ ���⼭ ������ ����
	}

	public static void main(String[] args) {
		new GameMain();
	}

}
