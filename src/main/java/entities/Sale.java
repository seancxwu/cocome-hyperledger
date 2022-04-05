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
public class Sale implements Serializable {
	
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
	private Store Belongedstore; 
	private CashDesk BelongedCashDesk; 
	private List<SalesLineItem> ContainedSalesLine = new LinkedList<SalesLineItem>(); 
	private Payment AssoicatedPayment; 
	
	/* all get and set functions */
	public LocalDate getTime() {
		return time;
	}	
	
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
	public Store getBelongedstore() {
		return Belongedstore;
	}	
	
	public void setBelongedstore(Store store) {
		this.Belongedstore = store;
	}			
	public CashDesk getBelongedCashDesk() {
		return BelongedCashDesk;
	}	
	
	public void setBelongedCashDesk(CashDesk cashdesk) {
		this.BelongedCashDesk = cashdesk;
	}			
	public List<SalesLineItem> getContainedSalesLine() {
		return ContainedSalesLine;
	}	
	
	public void addContainedSalesLine(SalesLineItem saleslineitem) {
		this.ContainedSalesLine.add(saleslineitem);
	}
	
	public void deleteContainedSalesLine(SalesLineItem saleslineitem) {
		this.ContainedSalesLine.remove(saleslineitem);
	}
	public Payment getAssoicatedPayment() {
		return AssoicatedPayment;
	}	
	
	public void setAssoicatedPayment(Payment payment) {
		this.AssoicatedPayment = payment;
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
