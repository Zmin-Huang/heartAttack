package tw.howard.midProject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.cj.protocol.Resultset;



public class HeartAttack extends JFrame {
	private JButton newGame, start, restart, p1, p2, p3, p4;
	private static player player1, player2, player3, player4;
	private static LinkedList<Integer> collected;
	private JPanel left, right, top, bottom;
	private JLabel counter,ps1,ps2,ps3,ps4,jl1,jl2,jl3,jl4,bg;
	private int count;
	private boolean isSame;
	private boolean pr1, pr2, pr3, pr4;
	private JButton h1, h2, h3, h4,co1,co2,co3,co4;
	private Timer tt;
	private JTextField pl1,pl2,pl3,pl4;
	int a1 = 0;int a2 = 0;int a3 = 0;int a4 = 0;
	public HeartAttack() {
		newGame = new JButton("新局");
		newGame.setEnabled(false);
		start = new JButton("開始");
		start.setEnabled(false);
		restart = new JButton("再開");
		restart.setEnabled(false);
		
		// 按鈕
		p1 = new JButton("玩家一丟牌");
		p1.setEnabled(false);
		p2 = new JButton("玩家二丟牌");
		p2.setEnabled(false);
		p3 = new JButton("玩家三丟牌");
		p3.setEnabled(false);
		p4 = new JButton("玩家四丟牌");
		p4.setEnabled(false);
		// 伸手
		h1 = new JButton("shoot1");
		h2 = new JButton("shoot2");
		h3 = new JButton("shoot3");
		h4 = new JButton("shoot4");
		h1.setEnabled(false);
		h2.setEnabled(false);
		h3.setEnabled(false);
		h4.setEnabled(false);
		// 玩家
		player1 = new player("Brad","M");
		player2 = new player("Howard","M");
		player3 = new player("Mary","F");
		player4 = new player("Tiffany","F");
		collected = new LinkedList<>();
		// 面板
		left = new JPanel();
		right = new JPanel();
		top = new JPanel();
		bottom = new JPanel();
		counter = new JLabel();
		//戰鬥圖
		ps1 = new JLabel();
		ps2 = new JLabel();
		ps3 = new JLabel();
		ps4 = new JLabel();
		//輸入框
		pl1 = new JTextField(4);
		pl2 = new JTextField(4);
		pl3 = new JTextField(4);
		pl4 = new JTextField(4);
		co1 = new JButton("提交");
		//一號面板
		JPanel bt1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bt1.add(p1);
		bt1.add(h1);
		bt1.add(ps1);
		bt1.add(pl1);
		bottom.add(bt1);
		//二號面板
		JPanel bt2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bt2.add(p2);
		bt2.add(h2);
		bt2.add(ps2);
		bt2.add(pl2);
		right.add(bt2);
		//三號面板
		JPanel bt3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bt3.add(newGame);
		bt3.add(start);
		bt3.add(restart);
		bt3.add(p3);
		bt3.add(h3);
		bt3.add(ps3);
		bt3.add(pl3);
		top.add(bt3);
		//四號面板
		JPanel bt4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bt4.add(p4);
		bt4.add(h4);
		bt4.add(ps4);
		bt4.add(pl4);
		bt4.add(co1);
		left.add(bt4);
		
		
		//輸入文字框
//		pl1 = new JTextField(2);
		// 版面配置
		setLayout(new BorderLayout());
		add(bottom, BorderLayout.SOUTH);
		add(right, BorderLayout.EAST);
		add(top, BorderLayout.NORTH);
		add(left, BorderLayout.WEST);
		add(counter, BorderLayout.CENTER);
		
		
		tt = new Timer();
		
		try {			
			jl1 = new JLabel(new ImageIcon(getClass().getResource("/Resource/poker1.png")));
			jl2 = new JLabel(new ImageIcon(getClass().getResource("/Resource/poker1.png")));
			jl3 = new JLabel(new ImageIcon(getClass().getResource("/Resource/poker1.png")));
			jl4 = new JLabel(new ImageIcon(getClass().getResource("/Resource/poker1.png")));
			jl1.setVisible(false);jl2.setVisible(false);jl3.setVisible(false);jl4.setVisible(false);
			bt1.add(jl1);bt2.add(jl2);bt3.add(jl3);bt4.add(jl4);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		setSize(1024, 768);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 事件
		addEventListener();

	}
	
	private void NewGame() {
		int nums = 52;
		int[] poker = new int[nums]; // 洗牌從51到1
		for (int i = 0; i < 4; i++) // poker[i] = i;
		{
			for (int j = 0; j < 13; j++) {
				poker[i * 13 + j] = j + 1;
			}
		}
		for (int k = nums - 1; k > 0; k--) {
			int rand = (int) (Math.random() * (k + 1));
			int temp = poker[rand];
			poker[rand] = poker[k];
			poker[k] = temp;
		}
		for (int a = 0; a < 52; a++) {
			if (a % 4 == 0) {
				player1.add(poker[a]);
			} else if (a % 4 == 1) {
				player2.add(poker[a]);
			} else if (a % 4 == 2) {
				player3.add(poker[a]);
			} else {
				player4.add(poker[a]);
			}
		}
	}

	private void addEventListener() {
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player1.clear();player2.clear();player3.clear();player4.clear();collected.clear();
				counter.setText("計數:\t" +  "\t 牌值:");
				counter.setIcon(null);
				count=0;
				NewGame();
				newGame.setEnabled(false);
				start.setEnabled(true);
				ps1.setText(player1.size()+"");
				ps2.setText(player2.size()+"");
				ps3.setText(player3.size()+"");
				ps4.setText(player4.size()+"");
				start.requestFocusInWindow();
				jl1.setVisible(true);
				jl2.setVisible(true);
				jl3.setVisible(true);
				jl4.setVisible(true);
			}
			
		});
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tt = new Timer();
				start();
				start.setEnabled(false);
			}
		});
		p1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ts1 t = new ts1();
				collected.add(player1.removeFirst());
				ps1.setText(player1.size()+"");
				switch(collected.getLast()) {
				case 1: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Ace.jpg")));break;
				case 2: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/two.png")));break;
				case 3: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/three.jpg")));break;
				case 4: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/four.jpg")));break;
				case 5: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/five.jpg")));break;
				case 6: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/six.jpg")));break;
				case 7: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/seven.jpg")));break;
				case 8: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/eight.jpg")));break;
				case 9: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/nine.jpg")));break;
				case 10: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/ten.jpg")));break;
				case 11: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Jazz.jpg")));break;
				case 12: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Quenn.jpg")));break;
				case 13: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/King.jpg")));break;
				}
				count++;
				if (count > 0) {
					if (count % 13 == 0) {
						counter.setText("計數:\t" + "13" +"\t場面牌數:"+count);
					} else {
						counter.setText("計數:\t" + count % 13 + "\t場面牌數:"+count);
					}
				}
				if (count > 0) {
					if (isSame()) {
						p1.setEnabled(false);
						p2.setEnabled(false);
						p3.setEnabled(false);
						p4.setEnabled(false);
						h1.setEnabled(true);
						h2.setEnabled(true);
						h3.setEnabled(true);
						h4.setEnabled(true);
						h1.requestFocusInWindow();
					} else {
						p1.setEnabled(false);
						tt.schedule(t, 500);
					}
				}
				if(player1.size()==0) {
					tt.cancel();
					p2.setEnabled(false);
					h1.setEnabled(false);
					h2.setEnabled(false);
					h3.setEnabled(false);
					h4.setEnabled(false);
					newGame.setEnabled(true);
					ps1.setText(player1.size()+"\tWinner!!!");
					try {
						Properties prop = new Properties();
						prop.put("user", "root");
						prop.put("password", "root");
						Connection con = DriverManager.getConnection
								("jdbc:mysql://localhost:3306/heartattack", prop);
						Statement stmt = con.createStatement();
						a1++;
							String sql = String.format("update member set win = '%d' where name = '%s'",a1,p1.getText());
							stmt.executeUpdate(sql);
							stmt.close();
							con.close();
					} catch (SQLException e1) {
						System.out.println(e1.toString());
					}
				}
			}
		});
		p2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ts2 t = new ts2();
				collected.add(player2.removeFirst());
				ps2.setText(player2.size()+"");
				count++;
				switch(collected.getLast()) {
				case 1: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Ace.jpg")));break;
				case 2: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/two.png")));break;
				case 3: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/three.jpg")));break;
				case 4: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/four.jpg")));break;
				case 5: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/five.jpg")));break;
				case 6: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/six.jpg")));break;
				case 7: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/seven.jpg")));break;
				case 8: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/eight.jpg")));break;
				case 9: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/nine.jpg")));break;
				case 10: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/ten.jpg")));break;
				case 11: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Jazz.jpg")));break;
				case 12: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Quenn.jpg")));break;
				case 13: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/King.jpg")));break;
				}
				if (count > 0) {
					if (count % 13 == 0) {
						counter.setText("計數:\t" + "13" + "\t場面牌數:"+count);
					} else {
						counter.setText("計數:\t" + count % 13 +"\t場面牌數:"+count);
					}
				}
				if (count > 0) {
					if (isSame()) {
						p1.setEnabled(false);
						p2.setEnabled(false);
						p3.setEnabled(false);
						p4.setEnabled(false);
						h1.setEnabled(true);
						h2.setEnabled(true);
						h3.setEnabled(true);
						h4.setEnabled(true);
						h1.requestFocusInWindow();
					} else {
						p2.setEnabled(false);
						tt.schedule(t, 500);
					}
				}
				if(player2.size()==0) {
					tt.cancel();
					p3.setEnabled(false);
					h1.setEnabled(false);
					h2.setEnabled(false);
					h3.setEnabled(false);
					h4.setEnabled(false);
					ps2.setText(player2.size()+"\tWinner!!!");
					newGame.setEnabled(true);
					try {
						Properties prop = new Properties();
						prop.put("user", "root");
						prop.put("password", "root");
						Connection con = DriverManager.getConnection
								("jdbc:mysql://localhost:3306/heartattack", prop);
						Statement stmt = con.createStatement();
						a2++;
							String sql = String.format("update member set win = '%d' where name = '%s'",a2,p2.getText());
							stmt.executeUpdate(sql);
							stmt.close();
							con.close();
					} catch (SQLException e1) {
						System.out.println(e1.toString());
					}
				}
			}
		});
		p3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ts3 t = new ts3();
				collected.add(player3.removeFirst());
				ps3.setText(player3.size()+"");
				switch(collected.getLast()) {
				case 1: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Ace.jpg")));break;
				case 2: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/two.png")));break;
				case 3: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/three.jpg")));break;
				case 4: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/four.jpg")));break;
				case 5: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/five.jpg")));break;
				case 6: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/six.jpg")));break;
				case 7: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/seven.jpg")));break;
				case 8: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/eight.jpg")));break;
				case 9: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/nine.jpg")));break;
				case 10: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/ten.jpg")));break;
				case 11: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Jazz.jpg")));break;
				case 12: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Quenn.jpg")));break;
				case 13: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/King.jpg")));break;
				}
				count++;
				if (count > 0) {
					if (count % 13 == 0) {
						counter.setText("計數:\t" + "13" + "\t場面牌數:"+count);
					} else {
						counter.setText("計數:\t" + count % 13 + "\t場面牌數:"+count);
					}
				}
				if (count > 0) {
					if (isSame()) {
						p1.setEnabled(false);
						p2.setEnabled(false);
						p3.setEnabled(false);
						p4.setEnabled(false);
						h1.setEnabled(true);
						h2.setEnabled(true);
						h3.setEnabled(true);
						h4.setEnabled(true);
						h1.requestFocusInWindow();
					} else {
						p3.setEnabled(false);
						tt.schedule(t, 500);
					}
				}
				if(player3.size()==0) {
					tt.cancel();
					p4.setEnabled(false);
					h1.setEnabled(false);
					h2.setEnabled(false);
					h3.setEnabled(false);
					h4.setEnabled(false);
					newGame.setEnabled(true);
					ps3.setText(player3.size()+"\tWinner!!!");
					try {
						Properties prop = new Properties();
						prop.put("user", "root");
						prop.put("password", "root");
						Connection con = DriverManager.getConnection
								("jdbc:mysql://localhost:3306/heartattack", prop);
						Statement stmt = con.createStatement();
						a3++;
							String sql = String.format("update member set win= '%d' where name = '%s'",a3,p3.getText());
							stmt.executeUpdate(sql);
							stmt.close();
							con.close();
					} catch (SQLException e1) {
						System.out.println(e1.toString());
					}
				}
			}
		});
		p4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ts4 t  = new ts4();
				collected.add(player4.removeFirst());
				ps4.setText(player4.size()+"");
				switch(collected.getLast()) {
				case 1: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Ace.jpg")));break;
				case 2: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/two.png")));break;
				case 3: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/three.jpg")));break;
				case 4: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/four.jpg")));break;
				case 5: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/five.jpg")));break;
				case 6: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/six.jpg")));break;
				case 7: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/seven.jpg")));break;
				case 8: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/eight.jpg")));break;
				case 9: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/nine.jpg")));break;
				case 10: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/ten.jpg")));break;
				case 11: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Jazz.jpg")));break;
				case 12: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/Quenn.jpg")));break;
				case 13: counter.setIcon(new ImageIcon(getClass().getResource("/Resource/King.jpg")));break;
				}
				count++;
				if (count > 0) {
					if (count % 13 == 0) {
						counter.setText("計數:\t" + "13" + "\t場面牌數:"+count);
					} else {
						counter.setText("計數:\t" + count % 13 + "\t場面牌數:"+count);
					}
				}
				if (count > 0) {
					if (isSame()) {
						p1.setEnabled(false);
						p2.setEnabled(false);
						p3.setEnabled(false);
						p4.setEnabled(false);
						h1.setEnabled(true);
						h2.setEnabled(true);
						h3.setEnabled(true);
						h4.setEnabled(true);
						h1.requestFocusInWindow();
					} else {
						p4.setEnabled(false);
						tt.schedule(t, 500);
					}
				}
				if(player4.size()==0) {
					tt.cancel();
					p1.setEnabled(false);
					h1.setEnabled(false);
					h2.setEnabled(false);
					h3.setEnabled(false);
					h4.setEnabled(false);
					newGame.setEnabled(true);
					ps4.setText(player4.size()+"\tWinner!!!");
					try {
						Properties prop = new Properties();
						prop.put("user", "root");
						prop.put("password", "root");
						Connection con = DriverManager.getConnection
								("jdbc:mysql://localhost:3306/heartattack", prop);
						Statement stmt = con.createStatement();
						a4++;
							String sql = String.format("update member set win= '%d' where name = '%s'",a4,p4.getText());
							stmt.executeUpdate(sql);
							stmt.close();
							con.close();
					} catch (SQLException e1) {
						System.out.println(e1.toString());
					}
				}
			}
		});
		p1.addKeyListener(new shootHand());
		p2.addKeyListener(new shootHand());
		p3.addKeyListener(new shootHand());
		p4.addKeyListener(new shootHand());
		h1.addKeyListener(new shootHand());
		h2.addKeyListener(new shootHand());
		h3.addKeyListener(new shootHand());
		h4.addKeyListener(new shootHand());
		
		h1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter.setIcon(null);
				for(int i = 0;i<count;i++) {
					switch((int)(Math.random()*3)) {
					case 0:player2.add(collected.get(i));break;
					case 1:player3.add(collected.get(i));break;
					case 2:player4.add(collected.get(i));break;
					}
				}
				collected.clear();
				Collections.shuffle(player2);
				Collections.shuffle(player3);
				Collections.shuffle(player4);
				ps2.setText(player2.size()+"");
				ps3.setText(player3.size()+"");
				ps4.setText(player4.size()+"");
				counter.setText("計數:\t" +  "\t 場面牌數:" );
				count=0;
				h1.setEnabled(false);
				h2.setEnabled(false);
				h3.setEnabled(false);
				h4.setEnabled(false);
				start.setEnabled(true);
				start.requestFocusInWindow();
			}
		});
		h2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter.setIcon(null);
				for(int i = 0;i<count;i++) {
					switch((int)(Math.random()*3)) {
					case 0:player1.add(collected.get(i));break;
					case 1:player3.add(collected.get(i));break;
					case 2:player4.add(collected.get(i));break;
					}
				}
				collected.clear();
				Collections.shuffle(player1);
				Collections.shuffle(player3);
				Collections.shuffle(player4);
				ps1.setText(player1.size()+"");
				ps3.setText(player3.size()+"");
				ps4.setText(player4.size()+"");
				counter.setText("計數:\t" +  "\t 場面牌數:" );
				count=0;
				h1.setEnabled(false);
				h2.setEnabled(false);
				h3.setEnabled(false);
				h4.setEnabled(false);
				start.setEnabled(true);
				start.requestFocusInWindow();
			}
		});
		h3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter.setIcon(null);
				for(int i = 0;i<count;i++) {
					switch((int)(Math.random()*3)) {
					case 0:player1.add(collected.get(i));break;
					case 1:player2.add(collected.get(i));break;
					case 2:player4.add(collected.get(i));break;
					}
				}
				collected.clear();
				Collections.shuffle(player1);
				Collections.shuffle(player2);
				Collections.shuffle(player4);
				ps1.setText(player1.size()+"");
				ps2.setText(player2.size()+"");
				ps4.setText(player4.size()+"");
				counter.setText("計數:\t" +  "\t 場面牌數:" );
				count=0;
				h1.setEnabled(false);
				h2.setEnabled(false);
				h3.setEnabled(false);
				h4.setEnabled(false);
				start.setEnabled(true);
				start.requestFocusInWindow();
			}
		});
		h4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter.setIcon(null);
				for(int i = 0;i<count;i++) {
					switch((int)(Math.random()*3)) {
					case 0:player1.add(collected.get(i));break;
					case 1:player2.add(collected.get(i));break;
					case 2:player3.add(collected.get(i));break;
					}
				}
				collected.clear();
				Collections.shuffle(player1);
				Collections.shuffle(player2);
				Collections.shuffle(player3);
				ps1.setText(player1.size()+"");
				ps2.setText(player2.size()+"");
				ps3.setText(player3.size()+"");
				counter.setText("計數:\t" +  "\t 場面牌數:" );
				count=0;
				h1.setEnabled(false);
				h2.setEnabled(false);
				h3.setEnabled(false);
				h4.setEnabled(false);
				start.setEnabled(true);
				start.requestFocusInWindow();
			}
		});
		co1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Date now = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dTime = sdf.format(now);
					Properties prop = new Properties();
					prop.put("user", "root");
					prop.put("password", "root");
					Connection con = DriverManager.getConnection
							("jdbc:mysql://localhost:3306/heartattack", prop);
					Statement stmt = con.createStatement();
					if(pl1.getText().equals("") || pl2.getText().equals("") ||pl3.getText().equals("") || pl4.getText().equals("")) {
				    	  JOptionPane.showInternalMessageDialog(null, "please enter a name");
				      }else {
				    	  String name1 = pl1.getText();
							String name2 = pl2.getText();
							String name3 = pl3.getText();
							String name4 = pl4.getText();
						String sql = String.format("insert into member (`name`,`playtime`,`win`) values ('%s','%s',0), ('%s','%s',0) ,('%s','%s',0) ,('%s','%s',0)",name1,dTime,name2,dTime,name3,dTime,name4,dTime);
						stmt.executeUpdate(sql);
						stmt.close();
						con.close();
				    	  JOptionPane.showInternalMessageDialog(null, "let's start!");

						p1.setText(name1);p2.setText(name2);p3.setText(name3);p4.setText(name4);
						co1.setVisible(false);pl1.setVisible(false);pl2.setVisible(false);pl3.setVisible(false);pl4.setVisible(false);
						newGame.setEnabled(true);
				      }
				} catch (SQLException e1) {
					System.out.println(e1.toString());
				}
			}
		});
		
	}

	private boolean isSame() {
		if (count > 0) {
			if (collected.get(count - 1) == count % 13 || collected.get(count - 1) == count % 13 + 13) {
				return true;
			}
		}
		return false;
	}

	public class shootHand extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_Q:
				p1.doClick();
				break;
			case KeyEvent.VK_E:
				p2.doClick();
				break;
			case KeyEvent.VK_Y:
				p3.doClick();
				break;
			case KeyEvent.VK_O:
				p4.doClick();
				break;
			case KeyEvent.VK_A:
				h1.requestFocusInWindow();
				h1.doClick();
				break;
			case KeyEvent.VK_D:
				h2.requestFocusInWindow();
				h2.doClick();
				break;
			case KeyEvent.VK_H:
				h3.requestFocusInWindow();
				h3.doClick();
				break;
			case KeyEvent.VK_L:
				h4.requestFocusInWindow();
				h4.doClick();
				break;
			}
		}
	}

	private void start() {
		Turn turn = new Turn();
		Countdown1 c1 = new Countdown1();
		Countdown2 c2 = new Countdown2();
		Countdown3 c3 = new Countdown3();
		NoIcon no = new NoIcon();
		tt.schedule(turn, 3000);
		tt.schedule(c1 , 0);
		tt.schedule(c2, 1000);
		tt.schedule(c3, 2000);
		tt.schedule(no, 3000);
	}

	
	
	public class Turn extends TimerTask{
		@Override
		public void run() {
			switch((int)(Math.random()*3)) {
			case 0: p1.setEnabled(true);p1.requestFocusInWindow();break;
			case 1: p2.setEnabled(true);p2.requestFocusInWindow();break;
			case 2: p3.setEnabled(true);p3.requestFocusInWindow();break;
			case 3: p4.setEnabled(true);p4.requestFocusInWindow();break;
			}
		}
		
	}
	public class ts1 extends TimerTask{
		@Override
		public void run() {
			p2.setEnabled(true);
			p2.requestFocusInWindow();	
		}
	}
	public class ts2 extends TimerTask{
		@Override
		public void run() {
			p3.setEnabled(true);
			p3.requestFocusInWindow();	
		}
	}
	public class ts3 extends TimerTask{
		@Override
		public void run() {
			p4.setEnabled(true);
			p4.requestFocusInWindow();	
		}
	}
	public class ts4 extends TimerTask{
		@Override
		public void run() {
			p1.setEnabled(true);
			p1.requestFocusInWindow();	
		}
	}
	public class Countdown1 extends TimerTask{
		@Override
		public void run() {
			counter.setIcon(new ImageIcon(getClass().getResource("/Resource/countthree.jpg")));
		}
		}
	public class Countdown2 extends TimerTask{
		@Override
		public void run() {
			counter.setIcon(new ImageIcon(getClass().getResource("/Resource/counttwo.jpg")));
		}
		}
	public class Countdown3 extends TimerTask{
		@Override
		public void run() {
			counter.setIcon(new ImageIcon(getClass().getResource("/Resource/countone.jpg")));
		}
		}
	public class NoIcon extends TimerTask{

		@Override
		public void run() {
			counter.setIcon(null);
		}}
	public static void main(String[] args) {
		new HeartAttack();
	}
}