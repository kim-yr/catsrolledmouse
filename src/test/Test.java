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
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();// 총알을 저장할 배열
	int bulletSpeed = 5;

	public Test() {
		img = Toolkit.getDefaultToolkit().getImage("f15k.png");
		pos = new Point(250, 250);
		mouse = new Point(250, 250);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e)// 마우스 클릭하면
			{
				Point p = new Point(0, 0);

				p.x += bulletSpeed * Math.cos(dAngle * (Math.PI / 180));
				p.y += bulletSpeed * Math.sin(dAngle * (Math.PI / 180));// 각도에 따라 총알 이동값 계산

				Bullet b = new Bullet(new Point(250, 250), p);// 총알 생성

				bullets.add(b);// 배열에 저장
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e)// 마우스를 움직이면
			{
				mouse = e.getPoint();// 마우스 위치 얻어오기
			}
		});

		Timer timer = new Timer();// 타이머(게임 루프)
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
// TODO Auto-generated method stub
				Process();
				repaint();
			}
		}, 10, 10);
	}

	public double getAngle(Point start, Point end)// 화면 중심과 마우스 사이의 각도 계산
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

		for (int i = 0; i < bullets.size(); i++)// 배열에 총알이 들어있다면
		{
			bullets.get(i).move();// 총알 움직이기

			if (bullets.get(i).getPos().x < 0 || bullets.get(i).getPos().x > 500)// 화면에서 벗어면 총알 삭제
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

		AffineTransform old = g2.getTransform();// 현재 상태 저장하기

		g2.rotate(Math.toRadians(dAngle), 250, 250);// 회전, 회전 중심x, 회전중심y
		g2.drawImage(img, pos.x - (PLANE_X / 2), pos.y - (PLANE_Y / 2), PLANE_X, PLANE_Y, this);
		g2.setTransform(old);// 원래 상태로(위에 drawImage함수만 회전상태가 적용됨)

		g.drawString(dAngle + "", 10, 10);// 각도 출력

		for (int i = 0; i < bullets.size(); i++)// 배열에 총알이 들어있다면 그려주기
		{
			bullets.get(i).draw(g2);
		}
	}

	class Bullet// 총알 클래스
	{
		Point pos;// 위치
		Point vel;// 움직일 값

		public Bullet(Point p, Point v) {
			pos = p;
			vel = v;
		}

		public void move()// 총알 움직이기
		{
			pos.x += vel.x;
			pos.y += vel.y;
		}

		public Point getPos() {
			return pos;
		}

		public void draw(Graphics2D g)// 총알 그려주기
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
