package readWriteInfo;

import java.io.Serializable;

public class StockInfo implements Serializable {

	private static final long serialVersionUID = 360329064709989545L;
	public int id, price, quantity;
	public String name, date;
	public StockInfo(int id, String name, int price, int quantity, String date) {
		this.id=id;
		this.name=name;
		this.price=price;
		this.quantity=quantity;
		this.date=date;
	}
}
