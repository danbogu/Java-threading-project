import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Label;

public class MyGui extends JFrame {
	
	private int num_of_tech_crews, time_for_secutiry;
	private JPanel contentPane;
	private JLabel lblTechNum, lblSecutiryWorkTime, lblYouHaveInserted, lblThankYouFor, titleWolcome;
	private JTextArea SecTime,NumOfTech;
	private JButton btnStart, btnExit;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	
		EventQueue.invokeLater(new Runnable() {
			
			MyGui frame;
			public void run() {
				try {
					frame = new MyGui();
					frame.setVisible(true);
				} catch (Exception e) {}
				
			}//run
			
			
		});
	}//main

	/**
	 * Create the frame.
	 */
	public MyGui() {
		createBackground();
		createTitle();
		createLables();
		createTextField();
		createButtons();
	}// Constructor
	
	private void createBackground() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}//createBackground
	
	private void createTitle() {
		titleWolcome = new JLabel ("Welcome To The Fatma Airport!");
		titleWolcome.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		titleWolcome.setBounds(6, 6, 438, 42);
		contentPane.add(titleWolcome);
	}//createTitle
	
	private void createLables() {
		lblTechNum = new JLabel("Number of technical crews");
		lblTechNum.setBounds(6, 72, 183, 16);
		contentPane.add(lblTechNum);
		
		lblSecutiryWorkTime = new JLabel("Work time of Secutiry");
		lblSecutiryWorkTime.setBounds(300, 72, 144, 16);
		contentPane.add(lblSecutiryWorkTime);
		
		lblYouHaveInserted = new JLabel("You have inserted illegal values");
		lblYouHaveInserted.setBounds(131, 128, 211, 16);
		contentPane.add(lblYouHaveInserted);
		lblYouHaveInserted.setVisible(false);
		
		lblThankYouFor = new JLabel("Enjoy using our airport!");
		lblThankYouFor.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblThankYouFor.setBounds(121, 156, 249, 34);
		contentPane.add(lblThankYouFor);
		lblThankYouFor.setVisible(false);
		
		
	}//createLables
	
	private void createTextField() {
		NumOfTech = new JTextArea();
		NumOfTech.setText("1");
		NumOfTech.setBounds(52, 100, 85, 16);
		contentPane.add(NumOfTech);
		
		SecTime = new JTextArea();
		SecTime.setText("2");
		SecTime.setBounds(327, 100, 85, 16);
		contentPane.add(SecTime);
	}//createTextField
	
	private void createButtons() {
		
		btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String time_of_sec = SecTime.getText();
				String num_of_tech = NumOfTech.getText();
				try {
					lblYouHaveInserted.setVisible(false);
					time_for_secutiry = Integer.valueOf(time_of_sec);
					num_of_tech_crews = Integer.valueOf(num_of_tech);
					Airport fatma = new Airport(num_of_tech_crews, time_for_secutiry);	
					lblThankYouFor.setVisible(true);
					}catch(NumberFormatException h) {
						lblYouHaveInserted.setVisible(true);
						}
				
			}//Button action
		});
		
		btnStart.setBounds(6, 230, 168, 29);
		contentPane.add(btnStart);
		
		btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					System.exit(0); 
			}//Button action
		});
		btnExit.setBounds(327, 230, 117, 29);
		contentPane.add(btnExit);

	}//createButtons
	
}//class

