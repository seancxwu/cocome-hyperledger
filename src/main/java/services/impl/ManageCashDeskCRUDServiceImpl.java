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

@Contract
public class ManageCashDeskCRUDServiceImpl implements ManageCashDeskCRUDService, Serializable, ContractInterface {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageCashDeskCRUDServiceImpl() {
		services = new ThirdPartyServicesImpl();
	}

	
	//Shared variable from system services
	
	/* Shared variable from system services and get()/set() methods */
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
				
	
	
	/* Generate inject for sharing temp variables between use cases in system service */
	public void refresh() {
		CoCoMESystem cocomesystem_service = (CoCoMESystem) ServiceManager.getAllInstancesOf(CoCoMESystem.class).get(0);
		cocomesystem_service.setCurrentCashDesk(currentCashDesk);
		cocomesystem_service.setCurrentStore(currentStore);
	}
	
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
			
			
			refresh();
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
			refresh();				
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
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return cashdesk;
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
			
			
			refresh();
			// post-condition checking
			if (!(cashdesk.getId() == id
			 && 
			cashdesk.getName() == name
			 && 
			cashdesk.getIsOpened() == isopened
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
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
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<CashDesk>)EntityManager.getAllInstancesOf(CashDesk.class)), cashdesk)
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
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
