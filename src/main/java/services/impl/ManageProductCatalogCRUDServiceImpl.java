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
			
			
			refresh();
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
			refresh();				
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
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return productcatalog;
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
			
			
			refresh();
			// post-condition checking
			if (!(productcatalog.getId() == id
			 && 
			productcatalog.getName() == name
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
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<ProductCatalog>)EntityManager.getAllInstancesOf(ProductCatalog.class)), productcatalog)
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
		//all relevant vars : productcatalog
		//all relevant entities : ProductCatalog
	} 
	 
	static {opINVRelatedEntity.put("deleteProductCatalog", Arrays.asList("ProductCatalog"));}
	
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
