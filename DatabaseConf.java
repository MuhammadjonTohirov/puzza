package PServer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DatabaseConf extends JFrame {

	private JPanel contentPane;
	private JTextField txtPort;
	private JTextField txtRoot;
	private JPasswordField pwdPassword;

	/**
	 * Launch the application.
	 */
	public static DatabaseConf frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new DatabaseConf();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DatabaseConf() {
		setTitle("Configure Database");
		setType(Type.UTILITY);
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 310, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(10, 30, 46, 14);
		contentPane.add(lblPort);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(10, 54, 67, 14);
		contentPane.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 79, 67, 14);
		contentPane.add(lblPassword);
		
		txtPort = new JTextField();
		txtPort.setHorizontalAlignment(SwingConstants.CENTER);
		txtPort.setText("3306");
		txtPort.setBounds(121, 27, 162, 20);
		contentPane.add(txtPort);
		txtPort.setColumns(10);
		
		txtRoot = new JTextField();
		txtRoot.setHorizontalAlignment(SwingConstants.CENTER);
		txtRoot.setText("root");
		txtRoot.setColumns(10);
		txtRoot.setBounds(121, 51, 162, 20);
		contentPane.add(txtRoot);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pwdPassword.setEchoChar('*');
		pwdPassword.setText("Password");
		pwdPassword.setBounds(121, 76, 162, 20);
		contentPane.add(pwdPassword);
		
		JButton btnCheckConnection = new JButton("Check connection");
		btnCheckConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connection = new Connection(txtPort.getText(), "pizzashop", txtRoot.getText(), pwdPassword.getText());
				if(connection.isConnectedToDataBase()){
					try{
						Server.Port = txtPort.getText();
						Server.password = pwdPassword.getText();
						Server.UserName = txtRoot.getText();
						frame.dispose();
						MainFrame.frame = new MainFrame();
						MainFrame.frame.setVisible(true);
						MainFrame.frame.setEnabled(true);
						connection.CloseConnection();
					}catch(Exception ex)
					{
						System.out.println(ex.getMessage());
					}
				}
			}
		});
		btnCheckConnection.setBounds(121, 107, 162, 23);
		contentPane.add(btnCheckConnection);
		
		JButton btnSetConnection = new JButton("Set connection");
		btnSetConnection.setBounds(121, 141, 162, 23);
		contentPane.add(btnSetConnection);
	}
}
