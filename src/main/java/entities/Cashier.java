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
public class Cashier implements Serializable {
	
	/* all primary attributes */
	@Property()
	private int id;
	@Property()
	private String name;
	
	/* all references */
	private Store WorkedStore; 
	
	/* all get and set functions */
	public int getId() {
		return id;
	}	
	
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}	
	
	public void setName(String name) {
		this.name = name;
	}
	
	/* all functions for reference*/
	public Store getWorkedStore() {
		return WorkedStore;
	}	
	
	public void setWorkedStore(Store store) {
		this.WorkedStore = store;
	}			
	

	/* invarints checking*/
	public boolean Cashier_UniqueCashierID() {
		
		if (StandardOPs.isUnique(((List<Cashier>)EntityManager.getAllInstancesOf("Cashier")), "Id")) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Cashier_UniqueCashierID()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Cashier_UniqueCashierID"));

}
