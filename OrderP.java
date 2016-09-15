/**
 * 
 */
package PServer;

/**
 * @author Muhammadjon
 *
 */
public class OrderP {
	public OrderP(){
		setDone(false);
	}
	public OrderP(String ID,String pizzatype, String amount, String time, String address, String orderedBy, String trtime) {
		settPizza(pizzatype);
		setAmount(amount);
		setTime(time);
		setAddress(address);
		setOrderedBy(orderedBy);
		setDone(false);
		setTradeTime(trtime);
		setId(ID);
	}
	public String gettPizza() {
		return tPizza;
	}
	public void settPizza(String tPizza) {
		this.tPizza = tPizza;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOrderedBy() {
		return orderedBy;
	}
	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	private String tPizza;
	private String amount;
	private String time;
	private String address;
	private String orderedBy;
	private boolean done;
	private String id;
	private String tradeTime;
}
