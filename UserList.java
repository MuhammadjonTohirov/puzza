package PServer;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Panel;

import javax.swing.JList;

import java.awt.Label;
import java.awt.TextField;
import java.awt.Button;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserList extends JFrame {

	private JPanel contentPane;
	public ResultSet resultSet;
	/**
	 * Launch the application.
	 */
	static public UserList frame;
	private final Panel panel = new Panel();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new UserList();
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
	public UserList() {

		setType(Type.UTILITY);
		setTitle("User list");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 661, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(261, 27, 394, 347);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		Label lblfname = new Label("First Name");
		lblfname.setBounds(23, 10, 100, 22);
		panel_1.add(lblfname);

		Label lbllname = new Label("Last Name");
		lbllname.setBounds(23, 53, 100, 22);
		panel_1.add(lbllname);

		Label lblusername = new Label("User Name");
		lblusername.setBounds(23, 102, 100, 22);
		panel_1.add(lblusername);

		Label lblemail = new Label("E-Mail");
		lblemail.setBounds(23, 150, 100, 22);
		panel_1.add(lblemail);

		Label lblpassword = new Label("Password");
		lblpassword.setBounds(23, 193, 100, 22);
		panel_1.add(lblpassword);

		Label lblregistered = new Label("Registered time");
		lblregistered.setBounds(23, 245, 100, 22);
		panel_1.add(lblregistered);

		Label lblseen = new Label("Last seen");
		lblseen.setBounds(23, 290, 80, 22);
		panel_1.add(lblseen);

		TextField txtFName = new TextField();
		txtFName.setBounds(143, 10, 241, 22);
		panel_1.add(txtFName);

		TextField txtLName = new TextField();
		txtLName.setBounds(143, 53, 241, 22);
		panel_1.add(txtLName);

		TextField txtEMail = new TextField();
		txtEMail.setBounds(143, 150, 241, 22);
		panel_1.add(txtEMail);

		TextField txtUName = new TextField();
		txtUName.setEditable(false);
		txtUName.setBounds(143, 107, 241, 22);
		panel_1.add(txtUName);

		TextField txtLastSeen = new TextField();
		txtLastSeen.setEditable(false);
		txtLastSeen.setBounds(143, 290, 241, 22);
		panel_1.add(txtLastSeen);

		TextField txtRegister = new TextField();
		txtRegister.setEditable(false);
		txtRegister.setBounds(143, 247, 241, 22);
		panel_1.add(txtRegister);

		TextField txtPassword = new TextField();
		txtPassword.setBounds(143, 193, 241, 22);
		panel_1.add(txtPassword);

		Button btnUpdate = new Button("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connectionToDatabase = new Connection();
				try {
					String a1 = txtFName.getText();
					String a2 = txtLName.getText();
					PreparedStatement pst = connectionToDatabase.getConnection()
							.prepareStatement("UPDATE users SET FName='" + a1 + "', LName='" + a2 + "', Email='"
									+ txtEMail.getText() + "', password='" + txtPassword.getText()
									+ "' WHERE UserName='" + txtUName.getText() + "'; ");
					pst.execute();
					for (ClientData obj : MainFrame.clients) {
						if (txtUName.getText().equals(obj.getLogo())) {
							obj.setFirstName(txtFName.getText());
							obj.setLastName(txtLName.getText());
							obj.setPassWord(txtPassword.getText());
							obj.seteMail(txtEMail.getText());
						}
					}
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		btnUpdate.setBounds(143, 318, 108, 22);
		panel_1.add(btnUpdate);

		Button btnDelete = new Button("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnDelete.setBounds(276, 318, 108, 22);
		panel_1.add(btnDelete);
		panel.setBounds(0, 10, 255, 364);
		contentPane.add(panel);
		panel.setLayout(null);

		JList<ClientData> ulist = new JList();
		ulist.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		ulist.setValueIsAdjusting(true);
		ulist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ulist.setBounds(10, 40, 235, 313);
		panel.add(ulist);

		TextField txtFInd = new TextField();
		txtFInd.setBounds(10, 10, 175, 24);
		panel.add(txtFInd);

		Button btnFind = new Button("FIND");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientData c = null;
				try {
					String a = (String) txtFInd.getText();
					c = getClient(new ClientData(), a);
					txtFName.setText(c.getFirstName());
					txtLName.setText(c.getLastName());
					txtUName.setText(c.getLogo());
					txtEMail.setText(c.geteMail());
					txtPassword.setText(c.getPassWord());
					txtLastSeen.setText("");
					txtRegister.setText("");
					txtLastSeen.setText(c.seen.toString());
					txtRegister.setText(c.reistered.toString());
				} catch (Exception ex) {
					System.out.println(ex.getMessage() + " there is no user");
					txtFInd.setText("no user");
				}
			}
		});
		btnFind.setBounds(191, 10, 54, 22);
		panel.add(btnFind);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					ulist.setModel(new AbstractListModel() {
						List<ClientData> values = MainFrame.clients;

						public int getSize() {
							if (values != null)
								return values.size();
							return 0;
						}
						@Override
						public Object getElementAt(int index) {
							if (values != null) {
								List<String> list = new ArrayList<String>();
								return MainFrame.clients.get(index).getLogo();
							}
							return "no user";
						}
					});
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});
		ulist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Object a = ulist.getSelectedValue();
					ClientData client = null;
					client = new ClientData();
					client = getClient(client, a.toString());
					txtFName.setText(client.getFirstName());
					txtLName.setText(client.getLastName());
					txtUName.setText(client.getLogo());
					txtEMail.setText(client.geteMail());
					txtPassword.setText(client.getPassWord());
					txtLastSeen.setText("");
					txtRegister.setText("");
					txtLastSeen.setText(client.seen.toString());
					txtRegister.setText(client.reistered.toString());
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});
	}

	public ClientData getClient(ClientData client, String username) throws SQLException {
		Connection connectionToDatabase = new Connection();
		resultSet = connectionToDatabase.getResultSet();
		resultSet = connectionToDatabase.getStatement()
				.executeQuery("select * from users where UserName='" + username + "'");
		while (resultSet.next()) {
			client.setFirstName(resultSet.getString("FName"));
			client.setLastName(resultSet.getString("LName"));
			client.setLogo(resultSet.getString("UserName"));
			client.seteMail(resultSet.getString("Email"));
			client.setPassWord(resultSet.getString("password"));
			client.setNumberOfOrder(resultSet.getInt("NofOrder"));
			client.reistered = resultSet.getDate("registered").toString() + " "
					+ resultSet.getTime("registered").toString();
			client.seen = resultSet.getDate("seen").toString() + " " + resultSet.getTime("seen").toString();
		}
		return client;
	}
}
