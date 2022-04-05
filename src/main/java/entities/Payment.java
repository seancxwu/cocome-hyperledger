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
import com.owlike.genson.annotation.*;

@DataType()
public class Payment implements Serializable {

	// Without @JsonProperty, genson will not set this field during deserialization.
	@JsonProperty
	private final String guid = EntityManager.getGuid();
	public Object getPK() {
		return guid;
	}
	
	/* all primary attributes */
	@Property()
	private float amountTendered;
	
	/* all references */
	@JsonProperty
	private Object BelongedSalePK;
	private Sale BelongedSale; 
	
	/* all get and set functions */
	public float getAmountTendered() {
		return amountTendered;
	}	
	
	public void setAmountTendered(float amounttendered) {
		this.amountTendered = amounttendered;
	}
	
	/* all functions for reference*/
	@JsonIgnore
	public Sale getBelongedSale() {
		if (BelongedSale == null)
			BelongedSale = EntityManager.getSaleByPK(BelongedSalePK);
		return BelongedSale;
	}	
	
	public void setBelongedSale(Sale sale) {
		this.BelongedSale = sale;
		this.BelongedSalePK = sale.getPK();
	}			
	


}
