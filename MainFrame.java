package PServer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import java.awt.Panel;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.json.simple.JSONObject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;

import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;

import java.awt.Dialog.ModalExclusionType;

import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Toolkit;
import java.util.Locale;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

import javax.swing.JMenuItem;
import javax.swing.border.LineBorder;
import java.awt.Button;
import java.awt.event.KeyEvent;
import java.awt.TextField;
import java.awt.Label;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import java.awt.SystemColor;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Server server;
	private static Connection connectionToDatabase;
	public static List<ClientData> clients = null;
	public ClientData client;
	public static List<PizzaClass> pizzas;
	public JFileChooser chooser;
	public int idOfSendingUser = -1;
	public ClientData cd = null;
	public static MainFrame frame;
	private JTextArea textArea;
	public static java.awt.List list_new;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainFrame();
					frame.setVisible(true);
					clients = new ArrayList<ClientData>();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String Image100URL = "";
	public String Image200URL = "";
	private JLabel lblOnn;
	private JLabel label_1;
	public static Panel pnlInfo;

	public MainFrame() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setRootPaneCheckingEnabled(false);
		setResizable(false);
		setFocusTraversalKeysEnabled(false);
		setFocusTraversalPolicyProvider(true);
		setLocale(new Locale("en", "UZ"));
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(MainFrame.class.getResource("/com/sun/javafx/scene/web/skin/Paste_16x16_JFX.png")));
		setVisible(true);
		setFont(new Font("Bell MT", Font.PLAIN, 12));
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Main");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(30, 40, 869, 491);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenu mnStartshutdow = new JMenu("Start/Shutdow");
		mnFile.add(mnStartshutdow);

		JMenuItem mntmStart = new JMenuItem("Shutdown");

		mnStartshutdow.add(mntmStart);

		JMenuItem mntmShutdownServer = new JMenuItem("Start");
		mntmShutdownServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				frame.setVisible(true);
				saveLogs();
				textArea.setText("PuZZa is running succesfully");
				onStart();
				mntmStart.setEnabled(true);
				mntmShutdownServer.setEnabled(false);
			}
		});
		mnStartshutdow.add(mntmShutdownServer);

		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);

		JMenu mnTest = new JMenu("Configure Database");
		mnTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DatabaseConf.frame = new DatabaseConf();
				DatabaseConf.frame.setVisible(true);
			}
		});
		mnSettings.add(mnTest);

		JMenu mnUsers = new JMenu("Users");
		mnSettings.add(mnUsers);
		
		JMenuItem mntmList = new JMenuItem("List");
		mntmList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserList.frame = new UserList();
				UserList.frame.setVisible(true);
			}
		});
		mnUsers.add(mntmList);

		JMenu mnPizzas = new JMenu("Pizzas");
		mnSettings.add(mnPizzas);

		JMenuItem button_1 = new JMenuItem("List");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pizzalist.frame = new Pizzalist();
				Pizzalist.frame.setVisible(true);
			}
		});
		button_1.setSize(new Dimension(60, 23));
		mnPizzas.add(button_1);
		
		JMenuItem mntmAdd = new JMenuItem("Add");
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddPizza.frame = new AddPizza();
				AddPizza.frame.setVisible(true);
			}
		});
		mnPizzas.add(mntmAdd);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmDocumentation = new JMenuItem("Documentation");
		mntmDocumentation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
				    try {
				        File myFile = new File("Resourse\\docs\\documentation.txt");
				        Desktop.getDesktop().open(myFile);
				    } catch (IOException ex) {
				        // no application registered for PDFs
				    }
				}
			}
		});
		mnHelp.add(mntmDocumentation);
		contentPane = new JPanel();
		contentPane.setIgnoreRepaint(true);
		contentPane.setAutoscrolls(true);
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel panel = new Panel();
		panel.setBounds(10, 19, 506, 368);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 22, 506, 346);

		JLabel label = new JLabel("Process");
		label.setBounds(10, 0, 486, 20);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Calibri", Font.PLAIN, 16));
		panel.setLayout(null);
		panel.add(scrollPane);

		textArea = new JTextArea();
		textArea.setForeground(new Color(0, 0, 128));
		textArea.setFont(new Font("Consolas", Font.BOLD, 13));
		textArea.setEditable(false);
		textArea.setBackground(SystemColor.inactiveCaptionBorder);
		scrollPane.setViewportView(textArea);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(textArea, popupMenu);

		JMenuItem mntmSaveLogs = new JMenuItem("Save logs");
		mntmSaveLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveLogs();
			}
		});
		mntmSaveLogs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmSaveLogs.setSelected(true);
		mntmSaveLogs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		mntmSaveLogs.setIcon(
				new ImageIcon(MainFrame.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		mntmSaveLogs.setHorizontalAlignment(SwingConstants.LEFT);
		popupMenu.add(mntmSaveLogs);
		panel.add(label);
		contentPane.add(panel);

		Panel panel_1 = new Panel();
		panel_1.setBounds(10, 393, 240, 20);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblServerStatus = new JLabel("Server status:");
		lblServerStatus.setBounds(10, 5, 69, 14);
		panel_1.add(lblServerStatus);

		lblOnn = new JLabel("OFF");
		lblOnn.setBounds(84, 5, 40, 15);
		lblOnn.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOnn.setForeground(Color.RED);
		panel_1.add(lblOnn);

		JLabel lblNewLabel = new JLabel("Port:");
		lblNewLabel.setBounds(134, 5, 36, 14);
		panel_1.add(lblNewLabel);

		label_1 = new JLabel("1500");
		label_1.setBounds(184, 5, 46, 14);
		panel_1.add(label_1);

		Panel panel_2 = new Panel();
		panel_2.setBounds(522, 39, 331, 368);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		pnlInfo = new Panel();
		pnlInfo.setBounds(10, 175, 311, 183);
		panel_2.add(pnlInfo);
		pnlInfo.setLayout(null);

		TextField txtOrderedBy = new TextField();
		txtOrderedBy.setBounds(94, 10, 217, 22);
		pnlInfo.add(txtOrderedBy);

		TextField txtAmount = new TextField();
		txtAmount.setBounds(94, 48, 217, 22);
		pnlInfo.add(txtAmount);

		TextField txtAddress = new TextField();
		txtAddress.setBounds(94, 88, 217, 22);
		pnlInfo.add(txtAddress);

		TextField txtTime = new TextField();
		txtTime.setBounds(94, 129, 217, 22);
		pnlInfo.add(txtTime);

		Label label_2 = new Label("Ordered by");
		label_2.setBounds(0, 10, 76, 22);
		pnlInfo.add(label_2);

		Label label_3 = new Label("Amount");
		label_3.setBounds(0, 48, 76, 22);
		pnlInfo.add(label_3);

		Label label_4 = new Label("Address");
		label_4.setBounds(0, 88, 76, 22);
		pnlInfo.add(label_4);

		Label label_5 = new Label("Time");
		label_5.setBounds(0, 129, 76, 22);
		pnlInfo.add(label_5);

		Button button = new Button("Done !");
		
		button.setFont(new Font("Consolas", Font.PLAIN, 12));
		button.setBounds(213, 157, 88, 22);
		pnlInfo.add(button);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBounds(10, 0, 311, 169);
		panel_2.add(tabbedPane);

		list_new = new java.awt.List();
		list_new.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = list_new.getSelectedItem().split("|")[0];
				try {
					for (OrderP o : Server.orders) {
						if(id.equals(o.getId())){
							txtOrderedBy.setText(o.getOrderedBy());
							txtAmount.setText(o.getAmount());
							txtTime.setText(o.getTime());
							txtAddress.setText(o.getAddress());
						}
					}
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		tabbedPane.addTab("New", null, list_new, null);

		java.awt.List list_done = new java.awt.List();
		tabbedPane.addTab("Done", null, list_done, null);
		setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { contentPane, txtOrderedBy, txtAmount, txtAddress, txtTime }));
		addWindowListener(new WindowAdapter() {

			@SuppressWarnings("deprecation")
			@Override
			public void windowClosing(WindowEvent arg0) {
				{
					try {
						for (int i = 0; i < getServer().getThreads().size() - 1; i++) {
							if (!getServer().getSocetConn().get(i).isClosed()) {
								getServer().getOutputStream().get(i).writeUTF("EXIT");
								getServer().getOutputStream().get(i).close();
								getServer().getInputStream().get(i).close();
								getServer().getThreads().get(i).stop();
							}
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				mntmShutdownServer.setEnabled(false);
				onStart();

			}
		});
		try {

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		mntmStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(textArea.getText() + "\n" + "PUZZA starting shutdown");
				try {
					getServer().CloseConnection();
					lblOnn.setForeground(Color.red);
					mntmShutdownServer.setEnabled(true);
					;
					mntmStart.setEnabled(false);
					textArea.setText(textArea.getText() + "\n" + "PUZZA Server is stopped ...\n.:BYE BYE:.");
				} catch (Exception e1) {
					System.out.println(e1.getMessage() + " something wrong");
				}
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = list_new.getSelectedItem().split("|")[0];
				for (OrderP Data : server.orders) {
					if(a.equals(Data.getId())){
						list_done.add(list_new.getSelectedItem());
						try {
							getConnectionToDatabase().getConnection().prepareStatement("UPDATE `pizzashop`.`trade` SET `delivered`='1' WHERE `pizza`='"+Data.gettPizza()+"' and `takenby`='"+Data.getOrderedBy()+"' and `tradetime`='"+Data.getTradeTime()+"';").execute();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							System.out.println(e1.getMessage());
						}
						Data.setDone(true);
						list_new.delItem(list_new.getSelectedIndex());
					}
				}
			}
		});

	}

	protected void saveLogs() {
		File f = new File("logs.txt");
		try {
			FileWriter writer = null;
			Date date = new Date();
			int year = date.getYear();
			if (f.exists()) {
				writer = new FileWriter(f);
				writer.append(year + "\n" + textArea.getText());
			} else {
				f.createNewFile();
				writer = new FileWriter(f);
				writer.append(year + "\n" + textArea.getText());
			}
			writer.close();
			System.out.println("Saved");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static ResultSet resultSet;

	public void onStart() {
		try {
			if (Connection.connectedToDataBase == false) {
				System.out.println("Entered");
				connectionToDatabase = new Connection();
				resultSet = connectionToDatabase.getResultSet();
				resultSet = connectionToDatabase.getStatement().executeQuery("select * from users");
				clients.clear();
				while (resultSet.next()) {
					client = new ClientData();
					client.setFirstName(resultSet.getString("FName"));
					client.setLastName(resultSet.getString("LName"));
					client.setLogo(resultSet.getString("UserName"));
					client.seteMail(resultSet.getString("Email"));
					client.setPassWord(resultSet.getString("password"));
					client.setNumberOfOrder(resultSet.getInt("NofOrder"));
					client.setONLINE(false);
					clients.add(client);
				}
				loadPizzas();
				lblOnn.setForeground(Color.green);
				lblOnn.setText("ON");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + "   loading datas error ...");
		}

		setServer(new Server());
		getServer().setConnection(textArea);

		JLabel txtPort = new JLabel((String.format("%s", getServer().ServerPort)));
		label_1.setText(String.valueOf(getServer().ServerPort));

	}

	public static void loadPizzas() {
		connectionToDatabase = new Connection();
		resultSet = connectionToDatabase.getResultSet();
		try {
			resultSet = connectionToDatabase.getStatement().executeQuery("select * from pizzas");

			pizzas = new ArrayList<PizzaClass>();
			PizzaClass p = new PizzaClass();
			pizzas.clear();
			while (resultSet.next()) {
				p = new PizzaClass();
				p.setID(resultSet.getString("id"));
				p.setType(resultSet.getString("TypeName"));
				p.setPrice(resultSet.getString("Price"));
				p.setAmount(resultSet.getInt("Amount"));
				p.setIngredients(resultSet.getString("ingredients"));
				byte[] tempByte = resultSet.getBytes("Picture100");
				byte[] tempByte200 = resultSet.getBytes("Picture200");
				p.setPizzaImageByte100(tempByte);
				p.setPizzaImageByte200(tempByte200);
				if (tempByte != null)
					p.setPizzaImage100(new ImageIcon(tempByte));
				if (tempByte200 != null)
					p.setPizzaImage200(new ImageIcon(tempByte200));

				pizzas.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (pizzas == null)
			System.out.println("there is no pizza");

	}

	public static Server getServer() {
		return server;
	}

	public static void setServer(Server server) {
		MainFrame.server = server;
	}

	public Connection getConnectionToDatabase() {
		return connectionToDatabase;
	}

	public void setConnectionToDatabase(Connection connectionToDatabase) {
		this.connectionToDatabase = connectionToDatabase;
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
