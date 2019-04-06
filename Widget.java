import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;

public class Widget {

	JFrame frame;
	int posX, posY;
	Image backgroundImage;
	int teamId = -1;
	JLabel homeTeam, awayTeam;
	JLabel homeScoreDisplay, awayScoreDisplay;
	String homeName = "Home Team";
	String awayName = "Away Name";
	int homeScore = 0;
	int awayScore = 0;
	boolean teamSelected = false;
	private JButton exitButton;
	// TODO return an array from http request and update global variables then
	// update the labels ...how to loop? maybe move class to this file

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
		
		ImageIcon img = new ImageIcon("img\\nhlLogo.png");
		frame.setIconImage(img.getImage());
		
		JButton menuButton = new JButton("Menu");
		menuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		menuButton.setForeground(new Color(240, 248, 255));
		menuButton.setBackground(new Color(47, 79, 79));
		menuButton.setBounds(10, 11, 89, 23);
		menuButton.setVisible(false);
		
		frame.getContentPane().add(menuButton);

		JComboBox<ComboItem> comboBox = new JComboBox<ComboItem>();
		comboBox.setBackground(new Color(47, 79, 79));
		comboBox.setForeground(new Color(240, 248, 255));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox.setEditable(true);
		comboBox.setBounds(82, 79, 149, 32);
		comboBox.setSelectedItem("Select Team");
		comboBox.addItem(new ComboItem("Anaheim Ducks", 24));
		comboBox.addItem(new ComboItem("Arizona Coyotes", 53));
		comboBox.addItem(new ComboItem("Boston Bruins", 6));
		comboBox.addItem(new ComboItem("Buffalo Sabres", 7));
		comboBox.addItem(new ComboItem("Calgary Flames", 20));
		comboBox.addItem(new ComboItem("Carolina Hurricanes", 12));
		comboBox.addItem(new ComboItem("Chicago Blackhawks", 16));
		comboBox.addItem(new ComboItem("Colorado Avalanche", 21));
		comboBox.addItem(new ComboItem("Columbus Blue-Jackets", 29));
		comboBox.addItem(new ComboItem("Dallas Stars", 25));
		comboBox.addItem(new ComboItem("Detroit Red Wings", 17));
		comboBox.addItem(new ComboItem("Edmonton Oilers", 22));
		comboBox.addItem(new ComboItem("Florida Panthers", 13));
		comboBox.addItem(new ComboItem("Los Angeles Kings", 26));
		comboBox.addItem(new ComboItem("Minnesota Wild", 30));
		comboBox.addItem(new ComboItem("Montreal Canadiens", 8));
		comboBox.addItem(new ComboItem("Nashville Predators", 18));
		comboBox.addItem(new ComboItem("New Jersey Devils", 1));
		comboBox.addItem(new ComboItem("New York Islanders", 2));
		comboBox.addItem(new ComboItem("New York Rangers", 3));
		comboBox.addItem(new ComboItem("Ottawa Senators", 9));
		comboBox.addItem(new ComboItem("Philadelphia Flyers", 4));
		comboBox.addItem(new ComboItem("Pittsburgh Penguins", 5));
		comboBox.addItem(new ComboItem("San Jose Sharks", 28));
		comboBox.addItem(new ComboItem("St. Louis Blues", 19));
		comboBox.addItem(new ComboItem("Tampa Bay Lightning", 14));
		comboBox.addItem(new ComboItem("Toronto Maple Leafs", 10));
		comboBox.addItem(new ComboItem("Vancouver Canucks", 23));
		comboBox.addItem(new ComboItem("Vegas Golden Knights", 54));
		comboBox.addItem(new ComboItem("Washington Capitals", 15));
		comboBox.addItem(new ComboItem("Winnipeg Jets", 52));
		frame.getContentPane().add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox.setVisible(false);
				ComboItem choice = (ComboItem) comboBox.getSelectedItem();
				teamId = choice.getId();
				setupTeamInfo();
				menuButton.setVisible(true);
			}
		});

		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideTeamInfo();
				comboBox.setVisible(true);
			}
		});
		
		// create labels for the scores and team names but dont show them yet
		homeTeam = new JLabel(homeName);
		homeTeam.setForeground(Color.WHITE);
		homeTeam.setFont(new Font("Tahoma", Font.BOLD, 14));
		homeTeam.setSize(159, 38);
		homeTeam.setLocation(0, 36);
		homeTeam.setVisible(false);
		frame.getContentPane().add(homeTeam);

		awayTeam = new JLabel(awayName);
		awayTeam.setForeground(Color.WHITE);
		awayTeam.setFont(new Font("Tahoma", Font.BOLD, 14));
		awayTeam.setLocation(157, 34);
		awayTeam.setSize(143, 34);
		awayTeam.setVisible(false);
		frame.getContentPane().add(awayTeam);

		homeScoreDisplay = new JLabel(homeScore + "");
		homeScoreDisplay.setForeground(Color.WHITE);
		homeScoreDisplay.setFont(new Font("Tahoma", Font.BOLD, 90));
		homeScoreDisplay.setLocation(33, 79);
		homeScoreDisplay.setSize(80, 80);
		homeScoreDisplay.setVisible(false);
		frame.getContentPane().add(homeScoreDisplay);

		awayScoreDisplay = new JLabel(awayScore + "");
		awayScoreDisplay.setForeground(Color.WHITE);
		awayScoreDisplay.setFont(new Font("Tahoma", Font.BOLD, 90));
		awayScoreDisplay.setLocation(187, 79);
		awayScoreDisplay.setSize(80, 80);
		awayScoreDisplay.setVisible(false);
		frame.getContentPane().add(awayScoreDisplay);
		
		exitButton = new JButton("Exit");
		exitButton.setForeground(new Color(240, 248, 255));
		exitButton.setBackground(new Color(47, 79, 79));
		exitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		exitButton.setBounds(201, 12, 89, 23);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(exitButton);
		
		

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
		
		Timer timer = new Timer(3000,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ( teamId != -1 ) {
					HTTPGetRequest(teamId);
				}
				
				
			}
		});
		
		timer.start();

	}
	
	public void hideTeamInfo() {
		homeTeam.setVisible(false);
		awayTeam.setVisible(false);
		homeScoreDisplay.setVisible(false);
		awayScoreDisplay.setVisible(false);
	}

	public void setupTeamInfo() {
		homeTeam.setVisible(true);
		awayTeam.setVisible(true);
		homeScoreDisplay.setVisible(true);
		awayScoreDisplay.setVisible(true);
	}

	public void setTeamInfo() {
		
		homeTeam.setText(homeName);
		homeScoreDisplay.setText(homeScore + "");
		awayTeam.setText(awayName);
		awayScoreDisplay.setText(awayScore + "");
	}

	public void HTTPGetRequest(int teamId) {
		
			try {

				String url = "https://statsapi.web.nhl.com/api/v1/schedule?teamId=" + teamId;

				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				int responceCode = con.getResponseCode();
				System.out.println("Sending GET request to URL: " + url);
				System.out.println("Responce code: " + responceCode);
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String inputLine;
				StringBuffer responce = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					responce.append(inputLine);
				}
				in.close();

				JSONObject myResponce = new JSONObject(responce.toString());

				JSONArray dates = (JSONArray) myResponce.get("dates");
				JSONObject games = null;
				try {
					games = (JSONObject) dates.get(0);
				}catch(Exception noElement) {
					//there is no game to pull stats from, so we leave the function
					return;
				}
				
				
				JSONArray g = (JSONArray) games.get("games");
				JSONObject gamePk = (JSONObject) g.get(0);
				JSONObject teams = (JSONObject) gamePk.get("teams");

				JSONObject homeTeam = (JSONObject) teams.get("home");
				JSONObject awayTeam = (JSONObject) teams.get("away");

				homeName = ((JSONObject) homeTeam.get("team")).getString("name");
				awayName = ((JSONObject) awayTeam.get("team")).getString("name");

				homeScore = homeTeam.getInt("score");
				awayScore = awayTeam.getInt("score");
				System.out.println(homeName+" vs. "+awayName);
				System.out.println(homeName+": "+homeScore);
				System.out.println(awayName+": "+awayScore);

				setTeamInfo();
				
				

			} catch (Exception c) {
				System.out.println(c.getMessage());
			}
		

	}
}
