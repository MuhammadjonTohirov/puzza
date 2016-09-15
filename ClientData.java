package PServer;

import java.sql.Date;
import java.sql.Time;

public class ClientData 
{
	private int ID;
	private boolean isONLINE;
	private String FirstName;
	private String LastName;
	private String eMail;
	private String Logo;
	private String passWord;
	private int numberOfOrder = 0;
	public String log = "";
	public String reistered;
	public String seen;
	private String orders = null;
	public ClientData() 
	{
		
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getLogo() {
		return Logo;
	}
	public void setLogo(String logo) {
		Logo = logo;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public boolean isONLINE() {
		return isONLINE;
	}
	public void setONLINE(boolean isONLINE) {
		this.isONLINE = isONLINE;
	}
	public int getNumberOfOrder() {
		return numberOfOrder;
	}
	public void setNumberOfOrder(int numberOfOrder) {
		this.numberOfOrder = numberOfOrder;
	}

	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	
}
