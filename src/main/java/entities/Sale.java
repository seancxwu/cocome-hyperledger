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
public class Sale implements Serializable {

	// Without @JsonProperty, genson will not set this field during deserialization.
	@JsonProperty
	private final String guid = EntityManager.getGuid();
	public Object getPK() {
		return guid;
	}
	
	/* all primary attributes */
	@Property()
	private LocalDate time;
	@Property()
	private boolean isComplete;
	@Property()
	private float amount;
	@Property()
	private boolean isReadytoPay;
	
	/* all references */
	@JsonProperty
	private Object BelongedstorePK;
	private Store Belongedstore; 
	@JsonProperty
	private Object BelongedCashDeskPK;
	private CashDesk BelongedCashDesk; 
	@JsonProperty
	private List<Object> ContainedSalesLinePKs = new LinkedList<>();
	private List<SalesLineItem> ContainedSalesLine = new LinkedList<SalesLineItem>(); 
	@JsonProperty
	private Object AssoicatedPaymentPK;
	private Payment AssoicatedPayment; 
	
	/* all get and set functions */
	@JsonConverter(LocalDateConverter.class)
	public LocalDate getTime() {
		return time;
	}	
	
	@JsonConverter(LocalDateConverter.class)
	public void setTime(LocalDate time) {
		this.time = time;
	}
	public boolean getIsComplete() {
		return isComplete;
	}	
	
	public void setIsComplete(boolean iscomplete) {
		this.isComplete = iscomplete;
	}
	public float getAmount() {
		return amount;
	}	
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public boolean getIsReadytoPay() {
		return isReadytoPay;
	}	
	
	public void setIsReadytoPay(boolean isreadytopay) {
		this.isReadytoPay = isreadytopay;
	}
	
	/* all functions for reference*/
	@JsonIgnore
	public Store getBelongedstore() {
		if (Belongedstore == null)
			Belongedstore = EntityManager.getStoreByPK(BelongedstorePK);
		return Belongedstore;
	}	
	
	public void setBelongedstore(Store store) {
		this.Belongedstore = store;
		this.BelongedstorePK = store.getPK();
	}			
	@JsonIgnore
	public CashDesk getBelongedCashDesk() {
		if (BelongedCashDesk == null)
			BelongedCashDesk = EntityManager.getCashDeskByPK(BelongedCashDeskPK);
		return BelongedCashDesk;
	}	
	
	public void setBelongedCashDesk(CashDesk cashdesk) {
		this.BelongedCashDesk = cashdesk;
		this.BelongedCashDeskPK = cashdesk.getPK();
	}			
	@JsonIgnore
	public List<SalesLineItem> getContainedSalesLine() {
		if (ContainedSalesLine == null)
			ContainedSalesLine = ContainedSalesLinePKs.stream().map(EntityManager::getSalesLineItemByPK).collect(Collectors.toList());
		return ContainedSalesLine;
	}	
	
	public void addContainedSalesLine(SalesLineItem saleslineitem) {
		getContainedSalesLine();
		this.ContainedSalesLinePKs.add(saleslineitem.getPK());
		this.ContainedSalesLine.add(saleslineitem);
	}
	
	public void deleteContainedSalesLine(SalesLineItem saleslineitem) {
		getContainedSalesLine();
		this.ContainedSalesLinePKs.remove(saleslineitem.getPK());
		this.ContainedSalesLine.remove(saleslineitem);
	}
	@JsonIgnore
	public Payment getAssoicatedPayment() {
		if (AssoicatedPayment == null)
			AssoicatedPayment = EntityManager.getPaymentByPK(AssoicatedPaymentPK);
		return AssoicatedPayment;
	}	
	
	public void setAssoicatedPayment(Payment payment) {
		this.AssoicatedPayment = payment;
		this.AssoicatedPaymentPK = payment.getPK();
	}			
	

	/* invarints checking*/
	public boolean Sale_AmountGreatAndEqualZero() {
		
		if (amount >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Sale_AmountGreatAndEqualZero()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Sale_AmountGreatAndEqualZero"));

}
