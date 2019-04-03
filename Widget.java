import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Color;

public class Widget {

	JFrame frame;
	int posX,posY;
	Image backgroundImage;
	int teamId = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Widget window = new Widget();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Widget() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 450, 300);
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JComboBox<ComboItem> comboBox = new JComboBox<ComboItem>();
		comboBox.setBackground(Color.WHITE);
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox.setEditable(true);
		comboBox.setBounds(82, 79, 149, 32);
		comboBox.setSelectedItem("Select Team");
		comboBox.addItem(new ComboItem("Anaheim Ducks",24));
		comboBox.addItem(new ComboItem("Arizona Coyotes",53));
		comboBox.addItem(new ComboItem("Boston Bruins",6));
		comboBox.addItem(new ComboItem("Buffalo Sabres",7));
		comboBox.addItem(new ComboItem("Calgary Flames",20));
		comboBox.addItem(new ComboItem("Carolina Hurricanes",12));
		comboBox.addItem(new ComboItem("Chicago Blackhawks",16));
		comboBox.addItem(new ComboItem("Colorado Avalanche",21));
		comboBox.addItem(new ComboItem("Columbus Blue-Jackets",29));
		comboBox.addItem(new ComboItem("Dallas Stars",25));
		comboBox.addItem(new ComboItem("Detroit Red Wings",17));
		comboBox.addItem(new ComboItem("Edmonton Oilers",22));
		comboBox.addItem(new ComboItem("Florida Panthers",13));
		comboBox.addItem(new ComboItem("Los Angeles Kings",26));
		comboBox.addItem(new ComboItem("Minnesota Wild",30));
		comboBox.addItem(new ComboItem("Montreal Canadiens",8));
		comboBox.addItem(new ComboItem("Nashville Predators",18));
		comboBox.addItem(new ComboItem("New Jersey Devils",1));
		comboBox.addItem(new ComboItem("New York Islanders",2));
		comboBox.addItem(new ComboItem("New York Rangers",3));
		comboBox.addItem(new ComboItem("Ottawa Senators",9));
		comboBox.addItem(new ComboItem("Philadelphia Flyers",4));
		comboBox.addItem(new ComboItem("Pittsburgh Penguins",5));
		comboBox.addItem(new ComboItem("San Jose Sharks",28));
		comboBox.addItem(new ComboItem("St. Louis Blues",19));
		comboBox.addItem(new ComboItem("Tampa Bay Lightning",14));
		comboBox.addItem(new ComboItem("Toronto Maple Leafs",10));
		comboBox.addItem(new ComboItem("Vancouver Canucks",23));
		comboBox.addItem(new ComboItem("Vegas Golden Knights",54));
		comboBox.addItem(new ComboItem("Washington Capitals",15));
		comboBox.addItem(new ComboItem("Winnipeg Jets",52));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("you chose one!");
				ComboItem choice = (ComboItem)comboBox.getSelectedItem();
				teamId = choice.getId();
				comboBox.setVisible(false);
				System.out.println(teamId);
				HTTPRequest n = new HTTPRequest(teamId);
			}
		});
		frame.getContentPane().add(comboBox);
		
		
		JLabel background = new JLabel(new ImageIcon("img\\resize.jpg"));
		background.setBounds(167, 15, -1, -1);
		frame.getContentPane().add(background);
		background.setLayout(new FlowLayout());
		
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
			}
		});
		frame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent evt) {
				// sets frame position when mouse dragged
				frame.setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen() - posY);

			}
		});
		
		
	}

}
