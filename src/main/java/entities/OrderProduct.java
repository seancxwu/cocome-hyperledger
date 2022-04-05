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
public class OrderProduct implements Serializable {
	
	/* all primary attributes */
	@Property()
	private int id;
	@Property()
	private LocalDate time;
	@Property()
	private OrderStatus orderStatus;
	@Property()
	private float amount;
	
	/* all references */
	private Supplier Supplier; 
	private List<OrderEntry> ContainedEntries = new LinkedList<OrderEntry>(); 
	
	/* all get and set functions */
	public int getId() {
		return id;
	}	
	
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getTime() {
		return time;
	}	
	
	public void setTime(LocalDate time) {
		this.time = time;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}	
	
	public void setOrderStatus(OrderStatus orderstatus) {
		this.orderStatus = orderstatus;
	}
	public float getAmount() {
		return amount;
	}	
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	/* all functions for reference*/
	public Supplier getSupplier() {
		return Supplier;
	}	
	
	public void setSupplier(Supplier supplier) {
		this.Supplier = supplier;
	}			
	public List<OrderEntry> getContainedEntries() {
		return ContainedEntries;
	}	
	
	public void addContainedEntries(OrderEntry orderentry) {
		this.ContainedEntries.add(orderentry);
	}
	
	public void deleteContainedEntries(OrderEntry orderentry) {
		this.ContainedEntries.remove(orderentry);
	}
	


}
