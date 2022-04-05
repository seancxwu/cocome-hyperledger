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
public class ManageProductCatalogCRUDServiceImpl implements ManageProductCatalogCRUDService, Serializable, ContractInterface {
	private static final Genson genson = new Genson();
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageProductCatalogCRUDServiceImpl() {
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
	public boolean createProductCatalog(final Context ctx, int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = createProductCatalog(id, name);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean createProductCatalog(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get productcatalog
		ProductCatalog productcatalog = null;
		//no nested iterator --  iterator: any previous:any
		for (ProductCatalog pro : (List<ProductCatalog>)EntityManager.getAllInstancesOf(ProductCatalog.class))
		{
			if (pro.getId() == id)
			{
				productcatalog = pro;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(productcatalog) == true) 
		{ 
			/* Logic here */
			ProductCatalog pro = null;
			pro = (ProductCatalog) EntityManager.createObject("ProductCatalog");
			pro.setId(id);
			pro.setName(name);
			EntityManager.addObject("ProductCatalog", pro);
			
			
			;
			// post-condition checking
			if (!(true && 
			pro.getId() == id
			 && 
			pro.getName() == name
			 && 
			StandardOPs.includes(((List<ProductCatalog>)EntityManager.getAllInstancesOf(ProductCatalog.class)), pro)
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
		//all relevant vars : pro
		//all relevant entities : ProductCatalog
	} 
	 
	static {opINVRelatedEntity.put("createProductCatalog", Arrays.asList("ProductCatalog"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public ProductCatalog queryProductCatalog(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = queryProductCatalog(id);
		return res;
	}

	@SuppressWarnings("unchecked")
	public ProductCatalog queryProductCatalog(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get productcatalog
		ProductCatalog productcatalog = null;
		//no nested iterator --  iterator: any previous:any
		for (ProductCatalog pro : (List<ProductCatalog>)EntityManager.getAllInstancesOf(ProductCatalog.class))
		{
			if (pro.getId() == id)
			{
				productcatalog = pro;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(productcatalog) == false) 
		{ 
			/* Logic here */
			
			
			;
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			; return productcatalog;
		}
		else
		{
			throw new PreconditionException();
		}
	} 
	 
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean modifyProductCatalog(final Context ctx, int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = modifyProductCatalog(id, name);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean modifyProductCatalog(int id, String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get productcatalog
		ProductCatalog productcatalog = null;
		//no nested iterator --  iterator: any previous:any
		for (ProductCatalog pro : (List<ProductCatalog>)EntityManager.getAllInstancesOf(ProductCatalog.class))
		{
			if (pro.getId() == id)
			{
				productcatalog = pro;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(productcatalog) == false) 
		{ 
			/* Logic here */
			productcatalog.setId(id);
			productcatalog.setName(name);
			
			
			;
			// post-condition checking
			if (!(productcatalog.getId() == id
			 && 
			productcatalog.getName() == name
			 && 
			EntityManager.saveModified(ProductCatalog.class)
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
		//all relevant vars : productcatalog
		//all relevant entities : ProductCatalog
	} 
	 
	static {opINVRelatedEntity.put("modifyProductCatalog", Arrays.asList("ProductCatalog"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean deleteProductCatalog(final Context ctx, int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = deleteProductCatalog(id);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean deleteProductCatalog(int id) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get productcatalog
		ProductCatalog productcatalog = null;
		//no nested iterator --  iterator: any previous:any
		for (ProductCatalog pro : (List<ProductCatalog>)EntityManager.getAllInstancesOf(ProductCatalog.class))
		{
			if (pro.getId() == id)
			{
				productcatalog = pro;
				break;
			}
				
			
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(productcatalog) == false && StandardOPs.includes(((List<ProductCatalog>)EntityManager.getAllInstancesOf(ProductCatalog.class)), productcatalog)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("ProductCatalog", productcatalog);
			
			
			;
			// post-condition checking
			if (!(StandardOPs.excludes(((List<ProductCatalog>)EntityManager.getAllInstancesOf(ProductCatalog.class)), productcatalog)
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
		//all relevant vars : productcatalog
		//all relevant entities : ProductCatalog
	} 
	 
	static {opINVRelatedEntity.put("deleteProductCatalog", Arrays.asList("ProductCatalog"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
