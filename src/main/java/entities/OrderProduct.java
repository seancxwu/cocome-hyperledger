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
import converters.*;
import com.owlike.genson.annotation.*;
import java.util.stream.*;

@DataType()
public class OrderProduct implements Serializable {
	public Object getPK() {
		return getId();
	}
	
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
	@JsonProperty
	private Object SupplierPK;
	private Supplier Supplier; 
	@JsonProperty
	private List<Object> ContainedEntriesPKs = new LinkedList<>();
	private List<OrderEntry> ContainedEntries = new LinkedList<OrderEntry>(); 
	
	/* all get and set functions */
	public int getId() {
		return id;
	}	
	
	public void setId(int id) {
		this.id = id;
	}
	@JsonConverter(LocalDateConverter.class)
	public LocalDate getTime() {
		return time;
	}	
	
	@JsonConverter(LocalDateConverter.class)
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
	@JsonIgnore
	public Supplier getSupplier() {
		if (Supplier == null)
			Supplier = EntityManager.getSupplierByPK(SupplierPK);
		return Supplier;
	}	
	
	public void setSupplier(Supplier supplier) {
		this.Supplier = supplier;
		this.SupplierPK = supplier.getPK();
	}			
	@JsonIgnore
	public List<OrderEntry> getContainedEntries() {
		if (ContainedEntries == null)
			ContainedEntries = ContainedEntriesPKs.stream().map(EntityManager::getOrderEntryByPK).collect(Collectors.toList());
		return ContainedEntries;
	}	
	
	public void addContainedEntries(OrderEntry orderentry) {
		getContainedEntries();
		this.ContainedEntriesPKs.add(orderentry.getPK());
		this.ContainedEntries.add(orderentry);
	}
	
	public void deleteContainedEntries(OrderEntry orderentry) {
		getContainedEntries();
		this.ContainedEntriesPKs.remove(orderentry.getPK());
		this.ContainedEntries.remove(orderentry);
	}
	


}
