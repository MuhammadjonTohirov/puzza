package PServer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Panel;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.List;
import java.awt.Font;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextPane;
import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Pizzalist extends JFrame {

	private JPanel contentPane;
	private JTextField txtAmount;
	private JTextField txtPrice;

	/**
	 * Launch the application.
	 */
	public static Pizzalist frame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Pizzalist();
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
	public Pizzalist() {
		setType(Type.UTILITY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 545, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel panel = new Panel();
		panel.setLayout(null);
		panel.setBackground(new Color(250, 250, 210));
		panel.setBounds(214, 10, 316, 368);
		contentPane.add(panel);

		JLabel lPic100 = new JLabel("PUSH");
		lPic100.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FileDialog fld = new FileDialog(frame);
				fld.show();
			}
		});
		lPic100.setOpaque(true);
		lPic100.setHorizontalAlignment(SwingConstants.CENTER);
		lPic100.setBorder(new EmptyBorder(1, 1, 1, 1));
		lPic100.setBackground(Color.LIGHT_GRAY);
		lPic100.setBounds(10, 11, 100, 100);
		panel.add(lPic100);

		Panel panel_1 = new Panel();
		panel_1.setLayout(null);
		panel_1.setBounds(130, 10, 176, 243);
		panel.add(panel_1);

		JLabel label_1 = new JLabel("Ingredients");
		label_1.setFont(new Font("Trajan Pro 3", Font.PLAIN, 14));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(0, 0, 176, 31);
		panel_1.add(label_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 35, 176, 208);
		panel_1.add(scrollPane);
		
		JTextPane txtIngrediets = new JTextPane();
		scrollPane.setViewportView(txtIngrediets);

		JLabel label_2 = new JLabel("Sold:");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(10, 259, 124, 25);
		panel.add(label_2);

		txtAmount = new JTextField();
		txtAmount.setEnabled(false);
		txtAmount.setColumns(10);
		txtAmount.setBounds(10, 284, 124, 20);
		panel.add(txtAmount);

		JLabel label_3 = new JLabel("Prince:");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(165, 262, 127, 14);
		panel.add(label_3);

		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(165, 284, 127, 20);
		panel.add(txtPrice);

		JLabel lPic200 = new JLabel("PUSH");
		lPic200.setOpaque(true);
		lPic200.setHorizontalAlignment(SwingConstants.CENTER);
		lPic200.setBorder(new EmptyBorder(1, 1, 1, 1));
		lPic200.setBackground(Color.LIGHT_GRAY);
		lPic200.setBounds(5, 121, 121, 121);
		panel.add(lPic200);

		Button button = new Button("Update");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection connectionToDatabase = new Connection();
				try {
					String price = txtPrice.getText();
					String  ing = txtIngrediets.getText();
					//InputStream ips = new 
					PreparedStatement pst = connectionToDatabase.getConnection()
							.prepareStatement("UPDATE pizzas SET WHERE UserName=''; ");
					pst.execute();
					/*for (PizzaClass obj : MainFrame.pizzas) {
						if (txtUName.getText().equals(obj.getLogo())) {
							obj.setFirstName(txtFName.getText());
							obj.setLastName(txtLName.getText());
							obj.setPassWord(txtPassword.getText());
							obj.seteMail(txtEMail.getText());
						}
					}*/
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		button.setBounds(10, 336, 70, 22);
		panel.add(button);

		Button button_1 = new Button("Add");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddPizza.frame = new AddPizza();
				AddPizza.frame.setVisible(true);
				frame.dispose();
			}
		});
		button_1.setBounds(222, 336, 70, 22);
		panel.add(button_1);

		Panel panel_2 = new Panel();
		panel_2.setBounds(10, 10, 198, 368);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		List pizzalist = new List();
		pizzalist.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				PizzaClass pc = getPizza(pizzalist.getSelectedItem());
				txtIngrediets.setText(pc.getType()+"\n"+pc.getIngredients());
				txtPrice.setText(pc.getPrice());
				txtAmount.setText(String.valueOf(pc.getAmount()));
				lPic100.setIcon(new ImageIcon(pc.getPizzaImageByte100()));
				lPic200.setIcon(new ImageIcon(pc.getPizzaImageByte200()));
			}
		});
		pizzalist.setBounds(0, 0, 198, 368);
		panel_2.add(pizzalist);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					for (int i = 0; i <= MainFrame.pizzas.size(); i++) {
						pizzalist.add(MainFrame.pizzas.get(i).getType(), Integer.parseInt(MainFrame.pizzas.get(i).getID()));
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});
	}
	public PizzaClass getPizza(String TypeName){
		for (PizzaClass obj : MainFrame.pizzas) {
			if(obj.getType().equals(TypeName)){
				return obj;
			}
		}
		return null;
	}
}
