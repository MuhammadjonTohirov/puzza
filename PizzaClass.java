package PServer;



import javax.swing.ImageIcon;

public class PizzaClass 
{
	private String ID;
	private String Type;
	private int amount;
	private String Price;
	private ImageIcon pizzaImage100;
	private ImageIcon pizzaImage200;
	private byte[] pizzaImageByte100;
	private byte[] pizzaImageByte200;
	private String ingredients;
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public byte[] getPizzaImageByte100() {
		return pizzaImageByte100;
	}
	public void setPizzaImageByte100(byte[] pizzaImageByte100) {
		this.pizzaImageByte100 = pizzaImageByte100;
	}
	public byte[] getPizzaImageByte200() {
		return pizzaImageByte200;
	}
	public void setPizzaImageByte200(byte[] pizzaImageByte200) {
		this.pizzaImageByte200 = pizzaImageByte200;
	}
	public ImageIcon getPizzaImage100() {
		return pizzaImage100;
	}
	public void setPizzaImage100(ImageIcon pizzaImage100) {
		this.pizzaImage100 = pizzaImage100;
	}
	public ImageIcon getPizzaImage200() {
		return pizzaImage200;
	}
	public void setPizzaImage200(ImageIcon pizzaImage200) {
		this.pizzaImage200 = pizzaImage200;
	}
	
}
