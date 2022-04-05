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
public class ManageCashierCRUDServiceImpl implements ManageCashierCRUDService, Serializable, ContractInterface {
	private static final Genson genson = new Genson();
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageCashierCRUDServiceImpl() {
		services = new ThirdPartyServicesImpl();
	}

	
	//Shared variable from system services
	
	/* Shared variable from system services and get()/set() methods */
	private Object currentCashDeskPK;
	private CashDesk currentCashDesk;
	private Object currentStorePK;
	private Store currentStore;
			
	/* all get and set functions for temp property*/
	public CashDesk getCurrentCashDesk() {
		return EntityManager.getCashDeskByPK(getCurrentCashDeskPK());
	}

	private Object getCurrentCashDeskPK() {
		if (currentCashDeskPK == null)
			currentCashDeskPK = genson.deserialize(EntityManager.stub.getStringState("system.currentCashDeskPK"), Integer.class);

		return currentCashDeskPK;
	}	
	
	public void setCurrentCashDesk(CashDesk currentcashdesk) {
		if (currentcashdesk != null)
			setCurrentCashDeskPK(currentcashdesk.getPK());
		else
			setCurrentCashDeskPK(null);
		this.currentCashDesk = currentcashdesk;
	}

	private void setCurrentCashDeskPK(Object currentCashDeskPK) {
		String json = genson.serialize(currentCashDeskPK);
		EntityManager.stub.putStringState("system.currentCashDeskPK", json);
		//If we set currentCashDeskPK to null, the getter thinks this fields is not initialized, thus will read the old value from chain.
		if (currentCashDeskPK != null)
			this.currentCashDeskPK = currentCashDeskPK;
		else
			this.currentCashDeskPK = EntityManager.getGuid();
	}
	public Store getCurrentStore() {
		return EntityManager.getStoreByPK(getCurrentStorePK());
	}

	private Object getCurrentStorePK() {
		if (currentStorePK == null)
			currentStorePK = genson.deserialize(EntityManager.stub.getStringState("system.currentStorePK"), Integer.class);

		return currentStorePK;
	}	
	
	public void setCurrentStore(Store currentstore) {
		if (currentstore != null)
			setCurrentStorePK(currentstore.getPK());
		else
			setCurrentStorePK(null);
		this.currentStore = currentstore;
	}

	private void setCurrentStorePK(Object currentStorePK) {
		String json = genson.serialize(currentStorePK);
		EntityManager.stub.putStringState("system.currentStorePK", json);
		//If we set currentStorePK to null, the getter thinks this fields is not initialized, thus will read the old value from chain.
		if (currentStorePK != null)
			this.currentStorePK = currentStorePK;
		else
			this.currentStorePK = EntityManager.getGuid();
	}
				
	
	
	/* Generate inject for sharing temp variables between use cases in system service */
	
	
	/* Generate buiness logic according to functional requirement */
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean createCashier(final Context ctx, int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = createCashier(id, name);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean createCashier(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cashier
		Cashier cashier = null;
		//no nested iterator --  iterator: any previous:any
		for (Cashier cas : (List<Cashier>)EntityManager.getAllInstancesOf(Cashier.class))
		{
			if (cas.getId() == id)
			{
				cashier = cas;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cashier) == true) 
		{ 
			/* Logic here */
			Cashier cas = null;
			cas = (Cashier) EntityManager.createObject("Cashier");
			cas.setId(id);
			cas.setName(name);
			EntityManager.addObject("Cashier", cas);
			
			
			;
			// post-condition checking
			if (!(true && 
			cas.getId() == id
			 && 
			cas.getName() == name
			 && 
			StandardOPs.includes(((List<Cashier>)EntityManager.getAllInstancesOf(Cashier.class)), cas)
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
		//string parameters: [name]
		//all relevant vars : cas
		//all relevant entities : Cashier
	} 
	 
	static {opINVRelatedEntity.put("createCashier", Arrays.asList("Cashier"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public Cashier queryCashier(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = queryCashier(id);
		return res;
	}

	@SuppressWarnings("unchecked")
	public Cashier queryCashier(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cashier
		Cashier cashier = null;
		//no nested iterator --  iterator: any previous:any
		for (Cashier cas : (List<Cashier>)EntityManager.getAllInstancesOf(Cashier.class))
		{
			if (cas.getId() == id)
			{
				cashier = cas;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cashier) == false) 
		{ 
			/* Logic here */
			
			
			;
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			; return cashier;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean modifyCashier(final Context ctx, int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = modifyCashier(id, name);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean modifyCashier(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cashier
		Cashier cashier = null;
		//no nested iterator --  iterator: any previous:any
		for (Cashier cas : (List<Cashier>)EntityManager.getAllInstancesOf(Cashier.class))
		{
			if (cas.getId() == id)
			{
				cashier = cas;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cashier) == false) 
		{ 
			/* Logic here */
			cashier.setId(id);
			cashier.setName(name);
			
			
			;
			// post-condition checking
			if (!(cashier.getId() == id
			 && 
			cashier.getName() == name
			 && 
			EntityManager.saveModified(Cashier.class)
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
		//string parameters: [name]
		//all relevant vars : cashier
		//all relevant entities : Cashier
	} 
	 
	static {opINVRelatedEntity.put("modifyCashier", Arrays.asList("Cashier"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean deleteCashier(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = deleteCashier(id);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteCashier(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cashier
		Cashier cashier = null;
		//no nested iterator --  iterator: any previous:any
		for (Cashier cas : (List<Cashier>)EntityManager.getAllInstancesOf(Cashier.class))
		{
			if (cas.getId() == id)
			{
				cashier = cas;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cashier) == false && StandardOPs.includes(((List<Cashier>)EntityManager.getAllInstancesOf(Cashier.class)), cashier)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Cashier", cashier);
			
			
			;
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Cashier>)EntityManager.getAllInstancesOf(Cashier.class)), cashier)
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
		//all relevant vars : cashier
		//all relevant entities : Cashier
	} 
	 
	static {opINVRelatedEntity.put("deleteCashier", Arrays.asList("Cashier"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
