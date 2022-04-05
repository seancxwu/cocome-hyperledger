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
public class ManageCashDeskCRUDServiceImpl implements ManageCashDeskCRUDService, Serializable, ContractInterface {
	private static final Genson genson = new Genson();
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageCashDeskCRUDServiceImpl() {
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
	public boolean createCashDesk(final Context ctx, int id, String name, boolean isopened) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = createCashDesk(id, name, isopened);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean createCashDesk(int id, String name, boolean isopened) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cashdesk
		CashDesk cashdesk = null;
		//no nested iterator --  iterator: any previous:any
		for (CashDesk cas : (List<CashDesk>)EntityManager.getAllInstancesOf(CashDesk.class))
		{
			if (cas.getId() == id)
			{
				cashdesk = cas;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cashdesk) == true) 
		{ 
			/* Logic here */
			CashDesk cas = null;
			cas = (CashDesk) EntityManager.createObject("CashDesk");
			cas.setId(id);
			cas.setName(name);
			cas.setIsOpened(isopened);
			EntityManager.addObject("CashDesk", cas);
			
			
			;
			// post-condition checking
			if (!(true && 
			cas.getId() == id
			 && 
			cas.getName() == name
			 && 
			cas.getIsOpened() == isopened
			 && 
			StandardOPs.includes(((List<CashDesk>)EntityManager.getAllInstancesOf(CashDesk.class)), cas)
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
		//all relevant entities : CashDesk
	} 
	 
	static {opINVRelatedEntity.put("createCashDesk", Arrays.asList("CashDesk"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public CashDesk queryCashDesk(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = queryCashDesk(id);
		return res;
	}

	@SuppressWarnings("unchecked")
	public CashDesk queryCashDesk(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cashdesk
		CashDesk cashdesk = null;
		//no nested iterator --  iterator: any previous:any
		for (CashDesk cas : (List<CashDesk>)EntityManager.getAllInstancesOf(CashDesk.class))
		{
			if (cas.getId() == id)
			{
				cashdesk = cas;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cashdesk) == false) 
		{ 
			/* Logic here */
			
			
			;
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			; return cashdesk;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean modifyCashDesk(final Context ctx, int id, String name, boolean isopened) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = modifyCashDesk(id, name, isopened);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean modifyCashDesk(int id, String name, boolean isopened) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cashdesk
		CashDesk cashdesk = null;
		//no nested iterator --  iterator: any previous:any
		for (CashDesk cas : (List<CashDesk>)EntityManager.getAllInstancesOf(CashDesk.class))
		{
			if (cas.getId() == id)
			{
				cashdesk = cas;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cashdesk) == false) 
		{ 
			/* Logic here */
			cashdesk.setId(id);
			cashdesk.setName(name);
			cashdesk.setIsOpened(isopened);
			
			
			;
			// post-condition checking
			if (!(cashdesk.getId() == id
			 && 
			cashdesk.getName() == name
			 && 
			cashdesk.getIsOpened() == isopened
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
		//string parameters: [name]
		//all relevant vars : cashdesk
		//all relevant entities : CashDesk
	} 
	 
	static {opINVRelatedEntity.put("modifyCashDesk", Arrays.asList("CashDesk"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean deleteCashDesk(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = deleteCashDesk(id);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteCashDesk(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get cashdesk
		CashDesk cashdesk = null;
		//no nested iterator --  iterator: any previous:any
		for (CashDesk cas : (List<CashDesk>)EntityManager.getAllInstancesOf(CashDesk.class))
		{
			if (cas.getId() == id)
			{
				cashdesk = cas;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(cashdesk) == false && StandardOPs.includes(((List<CashDesk>)EntityManager.getAllInstancesOf(CashDesk.class)), cashdesk)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("CashDesk", cashdesk);
			
			
			;
			// post-condition checking
			if (!(StandardOPs.excludes(((List<CashDesk>)EntityManager.getAllInstancesOf(CashDesk.class)), cashdesk)
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
		//all relevant vars : cashdesk
		//all relevant entities : CashDesk
	} 
	 
	static {opINVRelatedEntity.put("deleteCashDesk", Arrays.asList("CashDesk"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
