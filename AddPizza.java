package PServer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.transform.stream.StreamSource;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dialog;

import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Button;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.BrokenBarrierException;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.awt.Label;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.mysql.jdbc.Blob;

public class AddPizza extends JFrame {

	private JPanel contentPane;
	private JTextField txtPrice;
	private JTextField txtType;

	/**
	 * Launch the application.
	 */
	public static AddPizza frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new AddPizza();
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
	String imglocation1 = "Resourse\\PizzaLib\\defaultpizzas\\artichoke-100.png";
	String imglocation2 = "Resourse\\PizzaLib\\defaultpizzas\\artichoke-200.png";

	public AddPizza() {

		setType(Type.UTILITY);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(AddPizza.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 466, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lPic100 = new JLabel("PUSH");
		lPic100.setBounds(10, 22, 102, 102);
		lPic100.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					FileDialog fd = new FileDialog(frame);
					fd.show();
					imglocation1 = fd.getDirectory() + fd.getFile();
					lPic100.setIcon(new ImageIcon(imglocation1));
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});
		contentPane.setLayout(null);
		lPic100.setOpaque(true);
		lPic100.setHorizontalAlignment(SwingConstants.CENTER);
		lPic100.setBorder(new EmptyBorder(1, 1, 1, 1));
		lPic100.setBackground(Color.LIGHT_GRAY);
		contentPane.add(lPic100);

		JLabel lPic200 = new JLabel("PUSH");
		lPic200.setBounds(10, 135, 200, 200);
		lPic200.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					FileDialog fd = new FileDialog(frame);
					fd.show();
					imglocation2 = fd.getDirectory() + fd.getFile();
					lPic200.setIcon(new ImageIcon(imglocation2));
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				lPic100.setIcon(new ImageIcon("Resourse\\PizzaLib\\defaultpizzas\\artichoke-100.png"));
				lPic200.setIcon(new ImageIcon("Resourse\\PizzaLib\\defaultpizzas\\artichoke-200.png"));
			}
		});

		JLabel label_1 = new JLabel("Ingredients");
		label_1.setBounds(233, 11, 207, 31);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Trajan Pro 3", Font.PLAIN, 14));
		contentPane.add(label_1);

		lPic200.setOpaque(true);
		lPic200.setHorizontalAlignment(SwingConstants.CENTER);
		lPic200.setBorder(new EmptyBorder(1, 1, 1, 1));
		lPic200.setBackground(Color.LIGHT_GRAY);
		contentPane.add(lPic200);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(233, 46, 207, 208);
		contentPane.add(scrollPane);

		JTextPane txtIngredients = new JTextPane();
		scrollPane.setViewportView(txtIngredients);

		txtPrice = new JTextField();
		txtPrice.setBounds(282, 310, 158, 20);
		txtPrice.setColumns(10);
		contentPane.add(txtPrice);

		JLabel label_3 = new JLabel("Prince:");
		label_3.setBounds(233, 309, 53, 14);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(label_3);

		Button button = new Button("Add");
		button.setBounds(355, 345, 85, 22);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection co = new Connection();
				PreparedStatement ps = null;
				try {
					Calendar datetime = Calendar.getInstance();
					String Date = String.format("%tF", datetime);
					String time = String.format("%tT", datetime);

					ps = co.getConnection()
							.prepareStatement("INSERT INTO `pizzashop`.`pizzas` (`TypeName`, `Price`, `ingredients`, `Picture100`, `Picture200`, `added`, `updated`) "
									+ "VALUES ('"+ txtType.getText().toString() + "', '" + (txtPrice.getText().toString()) + "', '"+ txtIngredients.getText().toString()+"', ?, ?, '"+Date+" "+time+"', '"+Date+" "+time+"');");
					File f = new File(imglocation1);
					InputStream a = new FileInputStream(f);
					ps.setBlob(1, a);
					f = new File(imglocation2);
					a = new FileInputStream(f);
					ps.setBlob(2, a);
					ps.execute();
					JOptionPane.showMessageDialog(null, "Pizza added");
					PizzaClass pc = new PizzaClass();
					System.out.println(MainFrame.pizzas.size());
					MainFrame.loadPizzas();
					System.out.println(MainFrame.pizzas.size());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Please fill blanks");
					System.out.println(e1.getMessage() + " error");
				}

			}
		});
		contentPane.add(button);

		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(233, 265, 53, 14);
		lblType.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblType);

		txtType = new JTextField();
		txtType.setBounds(282, 266, 158, 20);
		txtType.setColumns(10);
		contentPane.add(txtType);
	}
}
