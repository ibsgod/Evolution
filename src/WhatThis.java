import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;

public class WhatThis extends JPanel {

	JFrame f;
	JSlider slider;
	JSlider slider_1;
	JSlider slider_2;
	private JLabel lblSpeede;
	private JLabel lblSize;
	private JLabel lblSense;
	
	public WhatThis() 
	{
		f = new JFrame();
		f.setVisible (true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setBounds(100, 100, 900, 650);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		f.setContentPane(this);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(0, 0, 890, 300);
		textPane.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		textPane.setBackground(getBackground());
		textPane.setEditable(false);
		add(textPane);
		String s = "This is a simple simulation of natural selection. With creatures being represented by red circles and food being represented by yellow squares, it shows the process of evolution through the changes in their speed, size, and sense, caused by mutations during reproduction.";
		s += "\n\nCreatures will randomly roam around looking for food, using up energy. They will survive until the next day if they obtain 1 piece of food before running out of energy. If they obtain 2, they will reproduce, creating a copy of themselves.";
		s += "\n\nWhen reproducing, there is a chance of mutation, which will cause the offspring to have a slightly higher/lower speed, size, and/or sense.";
		textPane.setText(s);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(0, 296, 501, 265);
		textPane_1.setBackground(getBackground());
		String t = "Speed makes you move faster. Size lets you eat smaller creatures (if you can catch them). Sense allows you to detect food/creatures in a radius, and move directly towards or away from them.";
		t += "\n\nThe equation of energy usage for movement is: \nspeed^2 + size^2 + sense";
		t += "\n\nVisual representation of traits can  be seen here -->";
		textPane_1.setText(t);
		textPane_1.setEditable(false);
		textPane_1.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
		add(textPane_1);
		
		slider = new JSlider();
		slider.setBounds(627, 450, 200, 40);
		slider.setValue(100);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				repaint();
			}
		});
		slider.setMaximum(200);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(50); 
	    slider.setMinorTickSpacing(10);
		add(slider);
		
		Hashtable<Integer, JLabel> labels = new Hashtable <Integer, JLabel>();
		labels.put(0, new JLabel("0"));
		labels.put(50, new JLabel("0.5"));
		labels.put(100, new JLabel("1"));
		labels.put(150, new JLabel("1.5"));
		labels.put(200, new JLabel("2"));
		slider.setLabelTable(labels);
		
		slider_1 = new JSlider();
		slider_1.setBounds(627, 500, 200, 40);
		slider_1.setValue(100);
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				repaint();
			}
		});
		slider_1.setMaximum(200);
		slider_1.setMajorTickSpacing(50);
		slider_1.setMinorTickSpacing(10);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		slider_1.setLabelTable(labels);
		add(slider_1);
		
		slider_2 = new JSlider();
		slider_2.setBounds(627, 550, 200, 40);
		slider_2.setValue(100);
		slider_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				repaint();
			}
		});
		slider_2.setMaximum(200);
		slider_2.setMinorTickSpacing(10);
		slider_2.setMajorTickSpacing(50);
		slider_2.setPaintTicks(true);
		slider_2.setPaintLabels(true);
		add(slider_2);
		
		lblSpeede = new JLabel("Speed:");
		lblSpeede.setBounds(541, 450, 57, 32);
		lblSpeede.setVerticalAlignment(SwingConstants.TOP);
		lblSpeede.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblSpeede);
		
		lblSize = new JLabel("Size:");
		lblSize.setBounds(541, 500, 57, 32);
		lblSize.setVerticalAlignment(SwingConstants.TOP);
		lblSize.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblSize);
		
		lblSense = new JLabel("Sense:");
		lblSense.setBounds(541, 550, 57, 32);
		lblSense.setVerticalAlignment(SwingConstants.TOP);
		lblSense.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblSense);
		slider_2.setLabelTable(labels);
		
	}
	
	public void paintComponent( Graphics g ) 
	{
		super.paintComponent(g);
		g.setColor(new Color(Math.max(0, Math.min(255, 128 + (int)(slider.getValue() * 0.16) * 8 - 64)), 0, 0));
		g.fillOval(670 + (20 - (int)(slider_1.getValue() * 0.2)), 340 + (20 - (int)(slider_1.getValue() * 0.2)), (int)(slider_1.getValue() * 0.4), (int)(slider_1.getValue() * 0.4));
		g.drawOval(570 + (120 - (int)(slider_2.getValue() * 1.2)), 240 + (120 - (int)(slider_2.getValue() * 1.2)), (int)(slider_2.getValue() * 2.4), (int)(slider_2.getValue() * 2.4));
	}
}
