import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Options extends JFrame {

	private JPanel contentPane;

	public Options(Display d) 
	{
		setBounds(100, 100, 300, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Initial food amount: " + d.ga.initialFood);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 264, 56);
		contentPane.add(lblNewLabel);
		
		JSlider slider_2 = new JSlider();
		JSlider slider_1 = new JSlider();
		JSlider slider = new JSlider();
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setMinimum(10);
		slider.setMaximum(40);
		slider.setValue(d.ga.initialFood);
		slider.setMajorTickSpacing(5); 
	    slider.setMinorTickSpacing(1); 
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) 
			{
				lblNewLabel.setText("Initial food amount: " + slider.getValue());
				slider_2.setMaximum(slider.getValue());
				if (slider.getValue() == slider_2.getValue())
				{
					slider_1.setMaximum(0);
				}
				else
				{
					slider_1.setMaximum(30);
				}
				d.ga.initialFood = slider.getValue();
				d.ga.foodDec = slider_1.getValue();
				d.ga.finalFood = slider_2.getValue();
			}
		});
		slider.setBounds(42, 63, 200, 40);
		contentPane.add(slider);
		
		JLabel lblRemoveFood = new JLabel("Remove 1 food every " + d.ga.foodDec + " days");
		lblRemoveFood.setHorizontalAlignment(SwingConstants.CENTER);
		lblRemoveFood.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblRemoveFood.setBounds(10, 91, 264, 56);
		contentPane.add(lblRemoveFood);
		
		slider_1.setPaintLabels(true);
		slider_1.setPaintTicks(true);
		slider_1.setMinimum(0);
		slider_1.setMaximum(30);
		slider_1.setValue(d.ga.foodDec);
		slider_1.setMajorTickSpacing(5); 
		slider_1.setMinorTickSpacing(1); 
		if (slider_1.getValue() != 0)
		{
			lblRemoveFood.setText("Remove 1 food every " + slider_1.getValue() + " days");
		}
		else
		{
			lblRemoveFood.setText("Don't remove food");
		}
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) 
			{
				if (slider_1.getValue() != 0)
				{
					lblRemoveFood.setText("Remove 1 food every " + slider_1.getValue() + " days");
				}
				else
				{
					lblRemoveFood.setText("Don't remove food");
				}
				d.ga.initialFood = slider.getValue();
				d.ga.foodDec = slider_1.getValue();
				d.ga.finalFood = slider_2.getValue();
			}
		});
		slider_1.setBounds(42, 143, 200, 40);
		contentPane.add(slider_1);
		
		JLabel lblFinalFoodAmount = new JLabel("Final food amount: " + d.ga.finalFood);
		lblFinalFoodAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinalFoodAmount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFinalFoodAmount.setBounds(10, 171, 264, 56);
		contentPane.add(lblFinalFoodAmount);
		
		slider_2.setPaintLabels(true);
		slider_2.setPaintTicks(true);
		slider_2.setPaintLabels(true);
		slider_2.setPaintTicks(true);
		slider_2.setMinimum(0);
		slider_2.setMaximum(slider.getValue());
		slider_2.setValue(d.ga.finalFood);
		slider_2.setMajorTickSpacing(5); 
		slider_2.setMinorTickSpacing(1); 
		slider_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) 
			{
				lblFinalFoodAmount.setText("Final food amount: " + slider_2.getValue());
				if (slider.getValue() == slider_2.getValue())
				{
					slider_1.setMaximum(0);
				}
				else
				{
					slider_1.setMaximum(30);
				}
				d.ga.initialFood = slider.getValue();
				d.ga.foodDec = slider_1.getValue();
				d.ga.finalFood = slider_2.getValue();
			}
		});
		slider_2.setBounds(42, 223, 200, 40);
		contentPane.add(slider_2);
		
		JToggleButton tglbtnShowSenseCircle = new JToggleButton("Show sense circles");
		if (d.showSense)
		{
			tglbtnShowSenseCircle.setSelected(true);
		}
		else
		{
			tglbtnShowSenseCircle.setSelected(false);
		}
		tglbtnShowSenseCircle.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) 
			{
				if (tglbtnShowSenseCircle.isSelected())
				{
					d.showSense = true;
				}
				else
				{
					d.showSense = false;
				}
			}
		});
		tglbtnShowSenseCircle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tglbtnShowSenseCircle.setBounds(69, 274, 146, 34);
		contentPane.add(tglbtnShowSenseCircle);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) 
			{		 
			    d.o = null;
			    dispose();
			}
		});
	}
}
