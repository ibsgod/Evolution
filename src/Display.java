
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class Display extends JPanel
{
	boolean showSense;
	Display d = this;
	Options o;
	JFrame f;
	String title;
	int width;
	int height;
	Game ga;
	public JLabel dayLbl;
	public JLabel speedLbl;
	public JLabel sizeLbl;
	public JLabel senseLbl;
	public JLabel pplLbl;
	public JLabel totspeedLbl;
	public JLabel totsizeLbl;
	public JLabel totsenseLbl;
	public JLabel totpplLbl;
	public JButton nextDay;
	
	public Display (String title, int width, int height, Game ga)
	{
	    this.ga = ga;
		this.title = title;
		this.width = width;
		this.height = height;

	    f = new JFrame( title );		
	    setLayout(null);
	    f.setBounds(0, 0, width, height);
	    f.setLocationRelativeTo(null);
	    f.setResizable(false);
	  	setFocusable(true);
	  	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setContentPane( this );
	    f.setVisible(true);
	    
	    JButton what = new JButton("What's this?");
	    what.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
	    what.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				new WhatThis();
			}
		});
	    what.setBounds(5, 5, 120, 30);
		add(what);
		
	    dayLbl = new JLabel("Day 0");
		dayLbl.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 35));
		dayLbl.setHorizontalAlignment(SwingConstants.CENTER);
		dayLbl.setBounds(100, 50, 200, 80);
		add(dayLbl);
		
		speedLbl = new JLabel("Avg speed:");
		speedLbl.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		speedLbl.setBounds(74, 134, 171, 30);
		add(speedLbl);
		
		sizeLbl = new JLabel("Avg size:");
		sizeLbl.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		sizeLbl.setBounds(74, 175, 221, 30);
		add(sizeLbl);
		
		senseLbl = new JLabel("Avg sense:");
		senseLbl.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		senseLbl.setBounds(74, 216, 221, 30);
		add(senseLbl);
		
		pplLbl = new JLabel("Population:");
		pplLbl.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		pplLbl.setBounds(74, 257, 221, 30);
		add(pplLbl);
		
		totspeedLbl = new JLabel("20 days avg speed:");
		totspeedLbl.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		totspeedLbl.setBounds(74, 298, 221, 30);
		add(totspeedLbl);
		
		totsizeLbl = new JLabel("20 days avg size:");
		totsizeLbl.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		totsizeLbl.setBounds(74, 339, 221, 30);
		add(totsizeLbl);
		
		totsenseLbl = new JLabel("20 days avg sense:");
		totsenseLbl.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		totsenseLbl.setBounds(74, 380, 221, 30);
		add(totsenseLbl);
		
		totpplLbl = new JLabel("20 days avg population:");
		totpplLbl.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		totpplLbl.setBounds(74, 421, 221, 30);
		add(totpplLbl);
		
		JToggleButton tglbtnPoop = new JToggleButton("Autoplay");
		tglbtnPoop.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		tglbtnPoop.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) 
			{
				if (tglbtnPoop.isSelected())
				{
					ga.autoplay = true;
				}
				else
				{
					ga.autoplay = false;
				}
			}
		});
		tglbtnPoop.setBounds(80, 472, 100, 30);
		add(tglbtnPoop);
		
		JButton options = new JButton("Options");
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				if (o == null)
				{
					o = new Options(d);
				}
				else
				{
					o.toFront();
					o.requestFocus();
				}
			}
		});
		options.setBounds(210, 472, 100, 30);
		options.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
		add(options);
		
		nextDay = new JButton("Start Day -->");
		nextDay.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 18));
		nextDay.setBounds(200, 530, 150, 50);
		nextDay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				ga.startRound();
			}
		});
		add(nextDay);
		JButton reset = new JButton("Reset");
		reset.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 18));
		reset.setBounds(40, 530, 150, 50);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				ga.playerDudes.clear();
				ga.foods.clear();
				ga.day = 0;
				ga.removedFood = 0;
				ga.dayCounter = 0;
				ga.playing = false;
				ga.avgsns = 0;
				ga.avgspd = 0;
				ga.avgppl = 0;
				ga.avgsze = 0;
				ga.lastdays.clear();
				ga.startGame();
				nextDay.setText("Start Day -->");
				nextDay.setVisible(true);
				dayLbl.setText("Day 0");
				speedLbl.setText("Avg speed:");
				sizeLbl.setText("Avg size:");
				senseLbl.setText("Avg sense:");
				pplLbl.setText("Population:");
				totspeedLbl.setText("20 days avg speed:");
				totsizeLbl.setText("20 days avg size:");
				totsenseLbl.setText("20 days avg sense:");
				totpplLbl.setText("20 days avg population:");

			}
		});
		add(reset);
	}
	public void paintComponent( Graphics g ) 
	{
		super.paintComponent(g);
		//g.clearRect(0, 0, width, height);
		g.setColor(Color.green);
		g.fillRect(ga.minx, ga.miny, ga.size, ga.size);
		for (int i = 0; i < ga.playerDudes.size(); i++)
		{
			g.setColor(new Color(Math.max(0, Math.min(255, 128 + ga.playerDudes.get(i).speed * 8 - 64)), 0, 0));
			g.fillOval(ga.playerDudes.get(i).x, ga.playerDudes.get(i).y, ga.playerDudes.get(i).size * 10, ga.playerDudes.get(i).size * 10);
			if (ga.playerDudes.get(i).centx != 0 && showSense)
			{
				g.drawOval(ga.playerDudes.get(i).centx - ga.playerDudes.get(i).sense * 30, ga.playerDudes.get(i).centy - ga.playerDudes.get(i).sense * 30, ga.playerDudes.get(i).sense * 60, ga.playerDudes.get(i).sense * 60);
			}
		}
		for (int i = 0; i < ga.foods.size(); i++)
		{
			g.setColor(Color.yellow);
			g.fillRect(ga.foods.get(i).x, ga.foods.get(i).y, 16, 16);
		}
	}
}
	  