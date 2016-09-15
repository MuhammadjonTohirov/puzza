package PServer;

import java.io.DataOutputStream;
import java.awt.Panel;
import java.awt.TextField;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JTextArea;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.sf.json.JSONString;
import pClient.Client;

public class Server extends Thread {

	public static String Port = "3306";
	public static int ServerPort = 1500;
	public static String UserName = "root";
	public static final String DatabaseName = "pizzashop";
	public static String password = "Pa$$w0rd";
	private ServerSocket server;
	private List<Socket> socetConn;
	private List<Thread> threads;
	private List<DataOutputStream> outputStream;
	private List<DataInputStream> inputStream;
	public String message;

	private List<Thread> readerThread;
	public int ids = 0;
	private static List<ClientData> userList;
	private static Connection connection;

	// Contsructor
	public Server() {
		threads = new ArrayList<Thread>();
		setReaderThread(new ArrayList<Thread>());
		message = new String();
		socetConn = new ArrayList<Socket>();
		outputStream = new ArrayList<DataOutputStream>();
		inputStream = new ArrayList<DataInputStream>();
	}

	// end of the Counstructor
	public static JTextArea textarea;

	public void setConnection(JTextArea area) {
		try {
			Connection connection = new Connection(Port, DatabaseName, UserName, password);
			if (connection.isConnectedToDataBase()) {
				setConnection(connection);
				textarea = area;
				server = new ServerSocket(ServerPort);
				setUserList(new ArrayList<ClientData>());
				area.setText(String.format("%s\nServer: Started\nPort: %s\n", area.getText(), ServerPort));

				threads.add(new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							area.setText(area.getText() + "\n" + "I'm wating . . . ");
							while (!message.startsWith("EXIT")) {

								Socket socket = server.accept();
								socetConn.add(socket);
								inputStream
										.add(new DataInputStream(socetConn.get(socetConn.size() - 1).getInputStream()));
								outputStream.add(
										new DataOutputStream(socetConn.get(socetConn.size() - 1).getOutputStream()));
								outputStream.get(outputStream.size() - 1)
										.writeUTF(String.format("Connection: Sucessfully\nPort: %s", ServerPort));
								outputStream.get(outputStream.size() - 1).writeUTF("ID|" + ids);
								String s = (inputStream.get(inputStream.size() - 1).readUTF());
								printF(s);
								ReadMessage(area, ids);
								ids++;
							}
						} catch (Exception e) {
							area.setText(area.getText() + "\n" + e.getMessage());

						}
					}

				}));

				threads.get(threads.size() - 1).start();
			} else {
				DatabaseConf.frame = new DatabaseConf();
				DatabaseConf.frame.setVisible(true);
				MainFrame.frame.dispose();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void CloseConnection() {
		try {
			try {
				broadCast("EXIT");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			connection.CloseConnection();

			for (Socket obj : socetConn) {
				if (!obj.isClosed())
					obj.close();
			}
			System.out.println("Exit3");
			for (Thread th : threads) {
				if (!th.isAlive())
					th.stop();
			}
			System.out.println("Exit2");
			for (DataInputStream stream : inputStream) {
				stream.close();
			}
			System.out.println("Exit1");
			for (DataOutputStream stream : outputStream) {
				stream.close();
			}
			server.close();
			System.out.println("Exit");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void ReadMessage(JTextArea are, int i) {
		getReaderThread().add(new Thread(new Runnable() {
			@Override
			public void run() {
				String s = "";
				while (!s.contains("EXIT")) {
					try {
						s = getInputStream().get(i).readUTF();
						decodeMessage(s, i);
						printF(s);
					} catch (Exception e) {
						MainFrame.clients.get(i).setONLINE(false);
						printF(e.getMessage());
						break;
					}
				}
			}
		}));
		getReaderThread().get(i).start();
	}

	public void printF(String Message) {
		textarea.setText(textarea.getText() + "\n" + Message + "\n------------------");
	}

	// template Datas of users
	public String TFirstName = "";
	public String TLastName = "";
	public String TUserPassowd = "";
	public String TUserLOGO = "";
	public String TUserEmail = "";
	public String TUsetID = "";
	public String TUData = "";

	public ResultSet rset;
	public Statement stat;
	public boolean userSigned = false;
	public String pName = "";
	public String pAmount = "";
	public String pCost = "";
	public String pTotalCost = "";
	public String pTime = "";
	public String pAddress = "";
	public int Amount = 0;
	public static List<OrderP> orders = new ArrayList<OrderP>();
	// end of template datas;

	public void sendMessageTo(String Message, int id) throws IOException {
		getOutputStream().get(id).writeUTF(Message);
	}

	public void broadCast(String message) throws IOException {
		for (DataOutputStream stream : getOutputStream()) {
			stream.writeUTF(message);
		}
	}

	public ClientData loggedUser = null;
	private static int orid=-1;
	public void decodeMessage(String MESS, int id) throws IOException {

		if (MESS.startsWith("EXIT")) {
			MESS = MESS.substring(4);
			for (int i = 0; i < MESS.length(); i++) {

				if (MESS.charAt(i) == '|') {
					TFirstName = MESS.substring(i + 1);
					break;
				} // else TUserLOGO+= MESS.charAt(i);
			}
			// TFirstName = TFirstName.replace("|", "");
			for (int i = 0; i < TFirstName.length(); i++) {
				if (TFirstName.charAt(i) == '|') {
					TUserLOGO = TFirstName.substring(i + 1);
					break;
				}
			}
			TUserLOGO = TUserLOGO.replace("|", "");
			printF("USER: " + TFirstName + "\n UserName: " + TUserLOGO + " is leaving ... \\done!");
			for (int i = 0; i < MainFrame.clients.size(); i++) {
				if ((TUserLOGO).contains(MainFrame.clients.get(i).getLogo())) {
					MainFrame.clients.get(i).setONLINE(false);
				}
			}
			try {
				Calendar datetime = Calendar.getInstance();
				String Date = String.format("%tF", datetime);
				String time = String.format("%tT", datetime);
				
				getConnection().getStatement().executeUpdate("UPDATE `pizzashop`.`users` SET `seen`='"+Date+" "+time+"' WHERE `UserName`='"+TUserLOGO+"';");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getSocetConn().get(id).shutdownInput();
			getSocetConn().get(id).shutdownOutput();
			getSocetConn().get(id).close();
		}

		TFirstName = "";
		TLastName = "";
		TUserLOGO = "";
		TUserEmail = "";

		if (MESS.contains("LOGIN|")) {
			TUserLOGO = "";
			TUserPassowd = "";
			TUData = "";
			TUData = MESS.substring(6);
			for (int i = 0; i < TUData.length() - 1; i++) {
				TUserLOGO += TUData.charAt(i);
				if (TUData.charAt(i) == '|') {
					TUserLOGO = TUserLOGO.replace("|", "");
					TUData = TUData.substring(i + 1);
					break;
				}
			}
			for (int i = 0; i < TUData.length(); i++) {
				TUserPassowd += TUData.charAt(i);
				if (TUData.charAt(i) == '|') {
					TUserPassowd = TUserPassowd.replace("|", "");
					TUsetID = TUData.substring(i + 1);
					break;
				}
			}
			try {
				{
					if (rset == null || stat == null) {

						rset = getConnection().getResultSet();
						stat = getConnection().getStatement();
					}
					for (int i = 0; i < MainFrame.clients.size(); i++) {
						if (MainFrame.clients.get(i).getLogo().equals(TUserLOGO)) {
							if (TUserPassowd.equals(MainFrame.clients.get(i).getPassWord())) {
								TFirstName = MainFrame.clients.get(i).getFirstName();
								TLastName = MainFrame.clients.get(i).getLastName();
								TUserLOGO = MainFrame.clients.get(i).getLogo();
								TUserEmail = MainFrame.clients.get(i).geteMail();
								printF("Client: " + TFirstName + " " + TLastName + " has logged in");
								JSONObject obj = new JSONObject();
								obj.put("FName", TFirstName);
								obj.put("LName", TLastName);
								obj.put("Email", TUserEmail);
								obj.put("UserName", TUserLOGO);
								sendMessageTo("credentials|" + obj, id);
								userSigned = true;

							} else
								userSigned = false;
						}
					}

					if (userSigned == false) {
						printF("WSORRY");
						sendMessageTo("WSORRY", id);
						// System.out.println("AFTER sending");
					} else {

						for (ClientData data : MainFrame.clients) {
							if (TUserLOGO.equals(data.getLogo()) && data.isONLINE() == true) {
								printF("ANOTHERUSERISUSINGFROMYOUaccounT");
								sendMessageTo("ANOTHERUSERISUSINGFROMYOUaccounT", id);
								userSigned = false;
								break;
							}
							if (TUserLOGO.equals(data.getLogo())) {
								printF("WELLCOME");
								sendMessageTo("WELLCOME", id);
								userSigned = true;
								break;
							}
						}

					}
				}
			} catch (Exception e) {
				printF(e.getMessage());
			}
			try {
				if (userSigned) {
					for (int k = 0; k < MainFrame.pizzas.size(); k++) {
						byte[] b = MainFrame.pizzas.get(k).getPizzaImageByte100();
						byte[] b1 = MainFrame.pizzas.get(k).getPizzaImageByte200();
						if (b != null && b1 != null) {
							sendMessageTo(String.format("PIZZALAR|%s|%s|%s|%s|%s|%s", MainFrame.pizzas.get(k).getType(),
									MainFrame.pizzas.get(k).getPrice(), MainFrame.pizzas.get(k).getIngredients(),
									b.length, b1.length, MainFrame.pizzas.size()), id);
							getOutputStream().get(id).write(b);
							getOutputStream().get(id).write(b1);
						}
					}
				}
				userSigned = false;
				System.out.println(TUserLOGO + " " + TUserPassowd);

				TFirstName = "";
				TLastName = "";
				TUserLOGO = "";
				TUserEmail = "";
			} catch (Exception e) {
				printF("ERROR when btn send clicked " + e.getMessage());
			}
		}
		if(MESS.startsWith("Update|")){
			MESS = MESS.substring(7);
			JSONParser jsp = new JSONParser();
			try {
				JSONObject obj = (JSONObject)(jsp.parse(MESS));
				String n = (String)obj.get("UserName");
				String n1 = (String)obj.get("FName");
				String n2 = (String)obj.get("LName");
				String n3 = (String)obj.get("Password");
				String n4 = (String)obj.get("Email");
				String old = (String)obj.get("OLDUName");
				getConnection().getStatement().execute("UPDATE `pizzashop`.`users` SET `FName`='"+n1+"', `LName`='"+n2+"', `UserName`='"+n+"', `password`='"+n3+"', `Email`='"+n4+"' WHERE `UserName`='"+old+"';");
				System.out.println("updated . . . . . ");
				for (ClientData data: MainFrame.clients) {
					if(data.getLogo().equals(old)){
						data.setFirstName(n1);
						data.setLastName(n2);
						data.setLogo(n);
						data.setPassWord(n3);
						data.seteMail(n3);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (MESS.startsWith("SENDPIZZA|")) {
			try {
				 {
					MESS = MESS.substring(10);
					JSONParser jp = new JSONParser();
					JSONObject obj = (JSONObject) jp.parse(MESS);
					System.out.println(obj);
					System.out.println("what is that");
					pAmount = String.format("%s", obj.get("amount"));
					pName = String.format("%s", obj.get("pizzaType"));
					String by = obj.get("orderedBy") + "";
					System.out.println(pAmount + " " + pName + "has been ordered by " + by);
					ResultSet r = getConnection().getResultSet();
					r = getConnection().getStatement()
							.executeQuery("select Amount from `pizzashop`.`pizzas` where `TypeName`='" + pName + "';");
////				--------------------------------------------------------
					Calendar c = Calendar.getInstance();
					orid++;
					MainFrame.list_new.add(orid+"|"+pName);
					TextField orby = (TextField) MainFrame.pnlInfo.getComponentAt(94, 10);
					orby.setText(pName);
					orby = (TextField) MainFrame.pnlInfo.getComponentAt(94, 48);
					orby.setText(pAmount);
					orby = (TextField) MainFrame.pnlInfo.getComponentAt(94, 88);
					String addr ="" + obj.get("ADDRESS");
					orby.setText(addr);
					orby = (TextField) MainFrame.pnlInfo.getComponentAt(94, 129);
					String time= "" + obj.get("TIME");
					orby.setText(time);
					String d = String.format("%tF", c);
					String t = String.format("%tT", c);
					OrderP or = new OrderP(""+orid, pName, pAmount, time, addr, by,d+" "+t);
					getConnection().getStatement().execute("INSERT INTO `pizzashop`.`trade` (`tradetime`, `amountpizza`, `pizza`, `takenby`, `delivered`, `delivered_to`) VALUES ('"+d+" "+t+"', '"+pAmount+"', '"+pName+"', '"+by+"', '0', '"+addr+"');");
					orders.add(or);
////				---------------------------------------------------------
					while (r.next()) {
						int b = r.getInt("Amount");
						b += Integer.parseInt(pAmount);
						pAmount = String.format("%s", b);
						System.out.println(b);
					}

					getConnection().getStatement().executeUpdate("UPDATE `pizzashop`.`pizzas` SET `Amount`='" + pAmount
							+ "' WHERE `TypeName`='" + pName + "';");
				}
			} catch (Exception e) {
				printF(e.getMessage());
			}

			pName = "";
			pAmount = "";
		}
		if (MESS.startsWith("IMREADY|")) {
			TUserLOGO = MESS.substring(8);
			for (ClientData c : MainFrame.clients) {
				if (c.getLogo().equalsIgnoreCase(TUserLOGO)) {
					c.setONLINE(true);
					c.setID(id);
					System.out.println(id);
					System.out.println(TUsetID);
					loggedUser = c;
				}
			}

			TUserLOGO = "";
		}
		if (MESS.contains("REG|")) {
			try {

				MESS = MESS.substring(4);
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(MESS);
				TFirstName = obj.get("FName") + "";
				TLastName = obj.get("LName") + "";
				TUserLOGO = obj.get("logo") + "";
				TUserEmail = obj.get("Email") + "";
				TUserPassowd = obj.get("psw") + "";

				Calendar datetime = Calendar.getInstance();
				String Date = String.format("%tF", datetime);
				String time = String.format("%tT", datetime);
				ClientData c = new ClientData();
				c.setFirstName(TFirstName);
				c.setLastName(TLastName);
				c.setLogo(TUserLOGO);
				c.seteMail(TUserEmail);
				c.setPassWord(TUserPassowd);
				c.reistered = Date + " " + time;
				c.seen = Date + " " + time;
				c.setONLINE(false);
				MainFrame.clients.add(c);

				if (Connection.connectedToDataBase == true) {
					// setConnection(new Connection());
					stat = getConnection().getStatement();
					rset = getConnection().getResultSet();
					rset = stat.executeQuery("select * from users");
					boolean userExist = false;
					while (rset.next()) {
						if (TUserLOGO.equals(rset.getString("UserName"))) {
							userExist = true;
							break;
						}
					}
					if (userExist == false) {
						sendMessageTo("REGSUCCESFULL", id);
						stat.execute(
								"INSERT INTO `pizzashop`.`users` (`FName`, `LName`, `UserName`, `password`, `Email`, `NofOrder`, `registered`, `seen`) VALUES ('"
										+ TFirstName + "', '" + TLastName + "', '" + TUserLOGO + "', '" + TUserPassowd
										+ "', '" + TUserEmail + "', '0', '" + Date + " " + time + "', '" + Date + " "
										+ time + "');");
						printF(String.format("New User added\nName: %s %s\n", TFirstName, TLastName));
					} else if (userExist == true)
						sendMessageTo("REGUNSUCCESFULL", id);
					userExist = false;

					// getConnection().CloseConnection();
				} else
					printF("NOT Connected to database");
			} catch (Exception e) {

				printF(e.getMessage());
			}

		}

		TFirstName = "";
		TLastName = "";
		TUserEmail = "";
		TUserLOGO = "";
		TUserPassowd = "";
	}

	public List<Socket> getSocetConn() {
		return socetConn;
	}

	public void setSocetConn(List<Socket> socetConn) {
		this.socetConn = socetConn;
	}

	public List<DataOutputStream> getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(List<DataOutputStream> outputStream) {
		this.outputStream = outputStream;
	}

	public List<DataInputStream> getInputStream() {
		return inputStream;
	}

	public void setInputStream(List<DataInputStream> inputStream) {
		this.inputStream = inputStream;
	}

	public List<Thread> getReaderThread() {
		return readerThread;
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public void setReaderThread(List<Thread> readerThread) {
		this.readerThread = readerThread;
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		Server.connection = connection;
	}

	public static List<ClientData> getUserList() {
		return userList;
	}

	public static void setUserList(List<ClientData> userList) {
		Server.userList = userList;
	}

	public ServerSocket getServerSocket() {
		return server;
	}
}
