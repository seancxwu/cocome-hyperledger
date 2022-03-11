package entities;

import services.impl.StandardOPs;

import java.util.*;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;
import org.hyperledger.fabric.contract.annotation.*;
import com.owlike.genson.annotation.*;

@DataType()
public class OrderEntry implements Serializable {

	// Without @JsonProperty, genson will not set this field during deserialization.
	@JsonProperty
	private final String guid = EntityManager.getGuid();
	public Object getPK() {
		return guid;
	}
	
	/* all primary attributes */
	@Property()
	private int quantity;
	@Property()
	private float subAmount;
	
	/* all references */
	@JsonProperty
	private Object ItemPK;
	private Item Item; 
	
	/* all get and set functions */
	public int getQuantity() {
		return quantity;
	}	
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getSubAmount() {
		return subAmount;
	}	
	
	public void setSubAmount(float subamount) {
		this.subAmount = subamount;
	}
	
	/* all functions for reference*/
	@JsonIgnore
	public Item getItem() {
		if (Item == null) {
			//PK is only of type GUID or int.
			//But genson deserializes numbers to long if not otherwise indicated, so we have to set back to int.
			if (ItemPK instanceof Long)
				ItemPK = Math.toIntExact((long) ItemPK);
			Item = EntityManager.getItemByPK(ItemPK);
		}
		return Item;
	}	
	
	public void setItem(Item item) {
		this.Item = item;
		this.ItemPK = item.getPK();
	}			
	

	public void prepareClone(HashSet<Object> prepared) {
		if (prepared.contains(this))
			return;
		prepared.add(this);
		if (getItem() != null)
			getItem().prepareClone(prepared);

	}


}
