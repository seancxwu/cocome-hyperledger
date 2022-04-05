package services.impl;

import services.*;
import entities.*;
import java.util.List;
import java.util.LinkedList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Arrays;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import org.apache.commons.lang3.SerializationUtils;
import java.util.Iterator;
import org.hyperledger.fabric.shim.*;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.contract.*;
import com.owlike.genson.Genson;

@Contract
public class CoCoMESystemImpl implements CoCoMESystem, Serializable, ContractInterface {
	private static final Genson genson = new Genson();
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public CoCoMESystemImpl() {
		services = new ThirdPartyServicesImpl();
	}

				
	
	/* Generate buiness logic according to functional requirement */
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean openCashDesk(final Context ctx, int cashDeskID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = openCashDesk(cashDeskID);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean openCashDesk(int cashDeskID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cd
		CashDesk cd = null;
		//no nested iterator --  iterator: any previous:any
		for (CashDesk s : (List<CashDesk>)EntityManager.getAllInstancesOf(CashDesk.class))
		{
			if (s.getId() == cashDeskID)
			{
				cd = s;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cd) == false && cd.getIsOpened() == false && StandardOPs.oclIsundefined(currentStore) == false && currentStore.getIsOpened() == true) 
		{ 
			/* Logic here */
			this.setCurrentCashDesk(cd);
			cd.setIsOpened(true);
			
			
			;
			// post-condition checking
			if (!(this.getCurrentCashDesk() == cd
			 && 
			cd.getIsOpened() == true
			 && 
			EntityManager.saveModified(CashDesk.class)
			 &&
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : cd this
		//all relevant entities : CashDesk 
	} 
	 
	static {opINVRelatedEntity.put("openCashDesk", Arrays.asList("CashDesk",""));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean closeCashDesk(final Context ctx, int cashDeskID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = closeCashDesk(cashDeskID);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean closeCashDesk(int cashDeskID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cd
		CashDesk cd = null;
		//no nested iterator --  iterator: any previous:any
		for (CashDesk s : (List<CashDesk>)EntityManager.getAllInstancesOf(CashDesk.class))
		{
			if (s.getId() == cashDeskID)
			{
				cd = s;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cd) == false && cd.getIsOpened() == true && StandardOPs.oclIsundefined(currentStore) == false && currentStore.getIsOpened() == true) 
		{ 
			/* Logic here */
			this.setCurrentCashDesk(cd);
			cd.setIsOpened(false);
			
			
			;
			// post-condition checking
			if (!(this.getCurrentCashDesk() == cd
			 && 
			cd.getIsOpened() == false
			 && 
			EntityManager.saveModified(CashDesk.class)
			 &&
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : cd this
		//all relevant entities : CashDesk 
	} 
	 
	static {opINVRelatedEntity.put("closeCashDesk", Arrays.asList("CashDesk",""));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean openStore(final Context ctx, int storeID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = openStore(storeID);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean openStore(int storeID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get sto
		Store sto = null;
		//no nested iterator --  iterator: any previous:any
		for (Store s : (List<Store>)EntityManager.getAllInstancesOf(Store.class))
		{
			if (s.getId() == storeID)
			{
				sto = s;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(sto) == false && sto.getIsOpened() == false) 
		{ 
			/* Logic here */
			this.setCurrentStore(sto);
			sto.setIsOpened(true);
			
			
			;
			// post-condition checking
			if (!(this.getCurrentStore() == sto
			 && 
			sto.getIsOpened() == true
			 && 
			EntityManager.saveModified(Store.class)
			 &&
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : this sto
		//all relevant entities :  Store
	} 
	 
	static {opINVRelatedEntity.put("openStore", Arrays.asList("","Store"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean closeStore(final Context ctx, int storeID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = closeStore(storeID);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean closeStore(int storeID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get sto
		Store sto = null;
		//no nested iterator --  iterator: any previous:any
		for (Store s : (List<Store>)EntityManager.getAllInstancesOf(Store.class))
		{
			if (s.getId() == storeID)
			{
				sto = s;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(sto) == false && sto.getIsOpened() == true) 
		{ 
			/* Logic here */
			sto.setIsOpened(false);
			
			
			;
			// post-condition checking
			if (!(sto.getIsOpened() == false
			 && 
			EntityManager.saveModified(Store.class)
			 &&
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : sto
		//all relevant entities : Store
	} 
	 
	static {opINVRelatedEntity.put("closeStore", Arrays.asList("Store"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean changePrice(final Context ctx, int barcode, float newPrice) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = changePrice(barcode, newPrice);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean changePrice(int barcode, float newPrice) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get item
		Item item = null;
		//no nested iterator --  iterator: any previous:any
		for (Item i : (List<Item>)EntityManager.getAllInstancesOf(Item.class))
		{
			if (i.getBarcode() == barcode)
			{
				item = i;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(item) == false) 
		{ 
			/* Logic here */
			item.setPrice(newPrice);
			
			
			;
			// post-condition checking
			if (!(item.getPrice() == newPrice
			 && 
			EntityManager.saveModified(Item.class)
			 &&
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : item
		//all relevant entities : Item
	} 
	 
	static {opINVRelatedEntity.put("changePrice", Arrays.asList("Item"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean receiveOrderedProduct(final Context ctx, int orderID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = receiveOrderedProduct(orderID);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean receiveOrderedProduct(int orderID) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get op
		OrderProduct op = null;
		//no nested iterator --  iterator: any previous:any
		for (OrderProduct i : (List<OrderProduct>)EntityManager.getAllInstancesOf(OrderProduct.class))
		{
			if (i.getId() == orderID)
			{
				op = i;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
		/* service reference */
		/* service temp attribute */
		/* objects in definition */
		OrderProduct Pre_op = SerializationUtils.clone(op);

		/* check precondition */
		if (StandardOPs.oclIsundefined(op) == false) 
		{ 
			/* Logic here */
			op.setOrderStatus(OrderStatus.RECEIVED);
			//no nested iterator --  iterator: forAll
			for (OrderEntry oe : op.getContainedEntries())
			{
				oe.getItem().setStockNumber(oe.getItem().getStockNumber()+oe.getQuantity());
			}
			
			
			;
			// post-condition checking
			if (!(op.getOrderStatus() == OrderStatus.RECEIVED
			 && 
			((Predicate<List>) (list) -> {	
				Iterator<OrderEntry> oeIt =  list.iterator();
				Iterator<OrderEntry> Pre_oeIt =  Pre_op.getContainedEntries().iterator();
				OrderEntry oe = null;
				OrderEntry Pre_oe = null;
					while (oeIt.hasNext() && Pre_oeIt.hasNext()) {
					oe = oeIt.next();
					Pre_oe = Pre_oeIt.next();
					if (!(oe.getItem().getStockNumber() == Pre_oe.getItem().getStockNumber()+oe.getQuantity())) {
						return false;
					}
				}
				return true;
			}).test(op.getContainedEntries())
			 && 
			EntityManager.saveModified(OrderProduct.class)
			 &&
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : op oe
		//all relevant entities : OrderProduct OrderEntry
	} 
	 
	static {opINVRelatedEntity.put("receiveOrderedProduct", Arrays.asList("OrderProduct","OrderEntry"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public Supplier[] listSuppliers(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = listSuppliers();
		return res.toArray(Supplier[]::new);
	}

	@SuppressWarnings("unchecked")
	public List<Supplier> listSuppliers() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/

		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			
			
			;
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			; return ((List<Supplier>)EntityManager.getAllInstancesOf(Supplier.class));
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public Item[] showStockReports(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = showStockReports();
		return res.toArray(Item[]::new);
	}

	@SuppressWarnings("unchecked")
	public List<Item> showStockReports() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/

		/* check precondition */
		if (true) 
		{ 
			/* Logic here */
			
			
			;
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			; return ((List<Item>)EntityManager.getAllInstancesOf(Item.class));
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	
	
	
	/* temp property for controller */
	private CashDesk currentCashDesk;
	private Store currentStore;
			
	/* all get and set functions for temp property*/
	public CashDesk getCurrentCashDesk() {
		return currentCashDesk;
	}	
	
	public void setCurrentCashDesk(CashDesk currentcashdesk) {
		this.currentCashDesk = currentcashdesk;
	}
	public Store getCurrentStore() {
		return currentStore;
	}	
	
	public void setCurrentStore(Store currentstore) {
		this.currentStore = currentstore;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
