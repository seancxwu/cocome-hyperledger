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
public class SalesLineItem implements Serializable {
	
	/* all primary attributes */
	@Property()
	private int quantity;
	@Property()
	private float subamount;
	
	/* all references */
	private Sale BelongedSale; 
	private Item BelongedItem; 
	
	/* all get and set functions */
	public int getQuantity() {
		return quantity;
	}	
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getSubamount() {
		return subamount;
	}	
	
	public void setSubamount(float subamount) {
		this.subamount = subamount;
	}
	
	/* all functions for reference*/
	public Sale getBelongedSale() {
		return BelongedSale;
	}	
	
	public void setBelongedSale(Sale sale) {
		this.BelongedSale = sale;
	}			
	public Item getBelongedItem() {
		return BelongedItem;
	}	
	
	public void setBelongedItem(Item item) {
		this.BelongedItem = item;
	}			
	


}
