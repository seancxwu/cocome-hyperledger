package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;
import org.hyperledger.fabric.contract.annotation.*;

@DataType()
public class Item implements Serializable {
	
	/* all primary attributes */
	@Property()
	private int barcode;
	@Property()
	private String name;
	@Property()
	private float price;
	@Property()
	private int stockNumber;
	@Property()
	private float orderPrice;
	
	/* all references */
	private ProductCatalog BelongedCatalog; 
	
	/* all get and set functions */
	public int getBarcode() {
		return barcode;
	}	
	
	public void setBarcode(int barcode) {
		this.barcode = barcode;
	}
	public String getName() {
		return name;
	}	
	
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}	
	
	public void setPrice(float price) {
		this.price = price;
	}
	public int getStockNumber() {
		return stockNumber;
	}	
	
	public void setStockNumber(int stocknumber) {
		this.stockNumber = stocknumber;
	}
	public float getOrderPrice() {
		return orderPrice;
	}	
	
	public void setOrderPrice(float orderprice) {
		this.orderPrice = orderprice;
	}
	
	/* all functions for reference*/
	public ProductCatalog getBelongedCatalog() {
		return BelongedCatalog;
	}	
	
	public void setBelongedCatalog(ProductCatalog productcatalog) {
		this.BelongedCatalog = productcatalog;
	}			
	

	/* invarints checking*/
	public boolean Item_UniqueBarcode() {
		
		if (StandardOPs.isUnique(((List<Item>)EntityManager.getAllInstancesOf("Item")), "Barcode")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Item_PriceGreatThanEqualZero() {
		
		if (price >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Item_StockNumberGreatThanEqualZero() {
		
		if (stockNumber >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Item_UniqueBarcode() && Item_PriceGreatThanEqualZero() && Item_StockNumberGreatThanEqualZero()) {
			return true;
		} else {
			return false;
		}
	}	
	
	//check invariant by inv name
	public boolean checkInvariant(String INVname) {
		
		try {
			Method m = this.getClass().getDeclaredMethod(INVname);
			return (boolean) m.invoke(this);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	
	}	
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Item_UniqueBarcode","Item_PriceGreatThanEqualZero","Item_StockNumberGreatThanEqualZero"));

}
