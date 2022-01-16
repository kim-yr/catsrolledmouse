package test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JPanel {
	int PLANE_X = 150;
	int PLANE_Y = 50;

	Image img;
	Point pos;
	Point mouse;
	double dAngle;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();// �Ѿ��� ������ �迭
	int bulletSpeed = 5;

	public Test() {
		img = Toolkit.getDefaultToolkit().getImage("f15k.png");
		pos = new Point(250, 250);
		mouse = new Point(250, 250);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e)// ���콺 Ŭ���ϸ�
			{
				Point p = new Point(0, 0);

				p.x += bulletSpeed * Math.cos(dAngle * (Math.PI / 180));
				p.y += bulletSpeed * Math.sin(dAngle * (Math.PI / 180));// ������ ���� �Ѿ� �̵��� ���

				Bullet b = new Bullet(new Point(250, 250), p);// �Ѿ� ����

				bullets.add(b);// �迭�� ����
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e)// ���콺�� �����̸�
			{
				mouse = e.getPoint();// ���콺 ��ġ ������
			}
		});

		Timer timer = new Timer();// Ÿ�̸�(���� ����)
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
// TODO Auto-generated method stub
				Process();
				repaint();
			}
		}, 10, 10);
	}

	public double getAngle(Point start, Point end)// ȭ�� �߽ɰ� ���콺 ������ ���� ���
	{
		double dy = end.y - start.y;
		double dx = end.x - start.x;

		if (dy < 0)
			return (Math.atan2(dy, dx) * (180.0 / Math.PI)) + 360;
		else
			return Math.atan2(dy, dx) * (180.0 / Math.PI);
	}

	public void Process() {
		dAngle = getAngle(pos, mouse);

		for (int i = 0; i < bullets.size(); i++)// �迭�� �Ѿ��� ����ִٸ�
		{
			bullets.get(i).move();// �Ѿ� �����̱�

			if (bullets.get(i).getPos().x < 0 || bullets.get(i).getPos().x > 500)// ȭ�鿡�� ����� �Ѿ� ����
			{
				bullets.remove(i);
				break;
			}

			if (bullets.get(i).getPos().y < 0 || bullets.get(i).getPos().y > 500) {
				bullets.remove(i);
				break;
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.clearRect(0, 0, 500, 500);

		AffineTransform old = g2.getTransform();// ���� ���� �����ϱ�

		g2.rotate(Math.toRadians(dAngle), 250, 250);// ȸ��, ȸ�� �߽�x, ȸ���߽�y
		g2.drawImage(img, pos.x - (PLANE_X / 2), pos.y - (PLANE_Y / 2), PLANE_X, PLANE_Y, this);
		g2.setTransform(old);// ���� ���·�(���� drawImage�Լ��� ȸ�����°� �����)

		g.drawString(dAngle + "", 10, 10);// ���� ���

		for (int i = 0; i < bullets.size(); i++)// �迭�� �Ѿ��� ����ִٸ� �׷��ֱ�
		{
			bullets.get(i).draw(g2);
		}
	}

	class Bullet// �Ѿ� Ŭ����
	{
		Point pos;// ��ġ
		Point vel;// ������ ��

		public Bullet(Point p, Point v) {
			pos = p;
			vel = v;
		}

		public void move()// �Ѿ� �����̱�
		{
			pos.x += vel.x;
			pos.y += vel.y;
		}

		public Point getPos() {
			return pos;
		}

		public void draw(Graphics2D g)// �Ѿ� �׷��ֱ�
		{
			g.drawOval(pos.x, pos.y, 5, 5);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Test test = new Test();

		frame.add(test);

		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
