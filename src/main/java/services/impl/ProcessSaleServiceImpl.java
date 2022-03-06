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
import java.util.logging.Logger;

import org.hyperledger.fabric.shim.*;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.contract.*;
import converters.*;
import com.owlike.genson.GensonBuilder;
import com.owlike.genson.Genson;

@Contract
public class ProcessSaleServiceImpl implements ProcessSaleService, Serializable, ContractInterface {
	private static final Genson genson = new Genson();

	private static final Logger logger = Logger.getLogger("ProcessSaleServiceImpl");
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ProcessSaleServiceImpl() {
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
	public boolean makeNewSale(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = makeNewSale();
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean makeNewSale() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/

		logger.info(String.format("%s", StandardOPs.oclIsundefined(getCurrentCashDesk())));
		logger.info(String.format("%s", getCurrentCashDesk().getIsOpened()));
		logger.info(String.format("%s", currentSale));

		/* check precondition */
		if (StandardOPs.oclIsundefined(getCurrentCashDesk()) == false && getCurrentCashDesk().getIsOpened() == true && (StandardOPs.oclIsundefined(getCurrentSale()) == true || (StandardOPs.oclIsundefined(getCurrentSale()) == false && getCurrentSale().getIsComplete() == true))) 
		{ 
			/* Logic here */
			Sale s = null;
			s = (Sale) EntityManager.createObject("Sale");
			s.setBelongedCashDesk(getCurrentCashDesk());
			getCurrentCashDesk().addContainedSales(s);
			s.setIsComplete(false);
			s.setIsReadytoPay(false);
			EntityManager.addObject("Sale", s);
			this.setCurrentSale(s);
			
			
			;
			// post-condition checking
			if (!(true && 
			s.getBelongedCashDesk() == getCurrentCashDesk()
			 && 
			StandardOPs.includes(getCurrentCashDesk().getContainedSales(), s)
			 && 
			s.getIsComplete() == false
			 && 
			s.getIsReadytoPay() == false
			 && 
			StandardOPs.includes(((List<Sale>)EntityManager.getAllInstancesOf(Sale.class)), s)
			 && 
			this.getCurrentSale() == s
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
		//all relevant vars : s this
		//all relevant entities : Sale 
	} 
	 
	static {opINVRelatedEntity.put("makeNewSale", Arrays.asList("Sale",""));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean enterItem(final Context ctx, int barcode, int quantity) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = enterItem(barcode, quantity);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean enterItem(int barcode, int quantity) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
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
		/* service reference */
		/* service temp attribute */
		/* objects in definition */
		Item Pre_item = SerializationUtils.clone(item);

		/* check precondition */
		if (StandardOPs.oclIsundefined(getCurrentSale()) == false && getCurrentSale().getIsComplete() == false && StandardOPs.oclIsundefined(item) == false && item.getStockNumber() > 0) 
		{ 
			/* Logic here */
			SalesLineItem sli = null;
			sli = (SalesLineItem) EntityManager.createObject("SalesLineItem");
			this.setCurrentSaleLine(sli);
			sli.setBelongedSale(getCurrentSale());
			getCurrentSale().addContainedSalesLine(sli);
			sli.setQuantity(quantity);
			sli.setBelongedItem(item);
			item.setStockNumber(item.getStockNumber()-quantity);
			sli.setSubamount(item.getPrice()*quantity);
			EntityManager.addObject("SalesLineItem", sli);
			
			
			;
			// post-condition checking
			if (!(true && 
			this.getCurrentSaleLine() == sli
			 && 
			sli.getBelongedSale() == getCurrentSale()
			 && 
			StandardOPs.includes(getCurrentSale().getContainedSalesLine(), sli)
			 && 
			sli.getQuantity() == quantity
			 && 
			sli.getBelongedItem() == item
			 && 
			item.getStockNumber() == Pre_item.getStockNumber()-quantity
			 && 
			sli.getSubamount() == item.getPrice()*quantity
			 && 
			StandardOPs.includes(((List<SalesLineItem>)EntityManager.getAllInstancesOf(SalesLineItem.class)), sli)
			 && 
			EntityManager.saveModified(Sale.class) && EntityManager.saveModified(Item.class)
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
		//all relevant vars : sli item this
		//all relevant entities : SalesLineItem Item 
	} 
	 
	static {opINVRelatedEntity.put("enterItem", Arrays.asList("SalesLineItem","Item",""));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public float endSale(final Context ctx) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = endSale();
		return res;
	}

	@SuppressWarnings("unchecked")
	public float endSale() throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get sls
		List<SalesLineItem> sls = new LinkedList<>();
		sls = getCurrentSale().getContainedSalesLine();
		//Get sub
		List<Float> sub = new LinkedList<>();
		//no nested iterator --  iterator: collect previous:collect
		for (SalesLineItem s : sls)
		{
			sub.add(s.getSubamount());
		}
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(getCurrentSale()) == false && getCurrentSale().getIsComplete() == false && getCurrentSale().getIsReadytoPay() == false) 
		{ 
			/* Logic here */
			getCurrentSale().setAmount(StandardOPs.sum(sub));
			getCurrentSale().setIsReadytoPay(true);
			
			
			;
			// post-condition checking
			if (!(getCurrentSale().getAmount() == StandardOPs.sum(sub)
			 && 
			getCurrentSale().getIsReadytoPay() == true
			 && 
			EntityManager.saveModified(Sale.class)
			 &&
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			;				
			return getCurrentSale().getAmount();
		}
		else
		{
			throw new PreconditionException();
		}
		//all relevant vars : currentSale
		//all relevant entities : 
	} 
	 
	static {opINVRelatedEntity.put("endSale", Arrays.asList(""));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean makeCashPayment(final Context ctx, float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var res = makeCashPayment(amount);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean makeCashPayment(float amount) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(getCurrentSale()) == false && getCurrentSale().getIsComplete() == false && getCurrentSale().getIsReadytoPay() == true && amount >= getCurrentSale().getAmount()) 
		{ 
			/* Logic here */
			CashPayment cp = null;
			cp = (CashPayment) EntityManager.createObject("CashPayment");
			cp.setAmountTendered(amount);
			cp.setBelongedSale(getCurrentSale());
			getCurrentSale().setAssoicatedPayment(cp);
			getCurrentSale().setBelongedstore(getCurrentStore());
			getCurrentStore().addSales(getCurrentSale());
			getCurrentSale().setIsComplete(true);
			getCurrentSale().setTime(LocalDate.now());
			cp.setBalance(amount-getCurrentSale().getAmount());
			EntityManager.addObject("CashPayment", cp);
			
			
			;
			// post-condition checking
			if (!(true && 
			cp.getAmountTendered() == amount
			 && 
			cp.getBelongedSale() == getCurrentSale()
			 && 
			getCurrentSale().getAssoicatedPayment() == cp
			 && 
			getCurrentSale().getBelongedstore() == getCurrentStore()
			 && 
			StandardOPs.includes(getCurrentStore().getSales(), getCurrentSale())
			 && 
			getCurrentSale().getIsComplete() == true
			 && 
			getCurrentSale().getTime().isEqual(LocalDate.now())
			 && 
			cp.getBalance() == amount-getCurrentSale().getAmount()
			 && 
			StandardOPs.includes(((List<CashPayment>)EntityManager.getAllInstancesOf(CashPayment.class)), cp)
			 && 
			EntityManager.saveModified(Sale.class) && EntityManager.saveModified(Store.class)
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
		//all relevant vars : currentSale cp
		//all relevant entities :  CashPayment
	} 
	 
	static {opINVRelatedEntity.put("makeCashPayment", Arrays.asList("","CashPayment"));}
	
	
	@Transaction(intent = Transaction.TYPE.SUBMIT)
	public boolean makeCardPayment(final Context ctx, String cardAccountNumber, String expiryDate, float fee) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		ChaincodeStub stub = ctx.getStub();
		EntityManager.setStub(stub);

		var genson = new GensonBuilder().withConverters(new LocalDateConverter()).create();
		var res = makeCardPayment(cardAccountNumber, genson.deserialize("\"" + expiryDate + "\"", LocalDate.class), fee);
		return res;
	}

	@SuppressWarnings("unchecked")
	public boolean makeCardPayment(String cardAccountNumber, LocalDate expiryDate, float fee) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* previous state in post-condition*/

		/* check precondition */
		if (StandardOPs.oclIsundefined(getCurrentSale()) == false && getCurrentSale().getIsComplete() == false && getCurrentSale().getIsReadytoPay() == true && services.thirdPartyCardPaymentService(cardAccountNumber, expiryDate, fee)) 
		{ 
			/* Logic here */
			CardPayment cdp = null;
			cdp = (CardPayment) EntityManager.createObject("CardPayment");
			cdp.setAmountTendered(fee);
			cdp.setBelongedSale(getCurrentSale());
			getCurrentSale().setAssoicatedPayment(cdp);
			cdp.setCardAccountNumber(cardAccountNumber);
			cdp.setExpiryDate(expiryDate);
			EntityManager.addObject("CardPayment", cdp);
			getCurrentSale().setBelongedstore(getCurrentStore());
			getCurrentStore().addSales(getCurrentSale());
			getCurrentSale().setIsComplete(true);
			getCurrentSale().setTime(LocalDate.now());
			
			
			;
			// post-condition checking
			if (!(true && 
			cdp.getAmountTendered() == fee
			 && 
			cdp.getBelongedSale() == getCurrentSale()
			 && 
			getCurrentSale().getAssoicatedPayment() == cdp
			 && 
			cdp.getCardAccountNumber() == cardAccountNumber
			 && 
			cdp.getExpiryDate() == expiryDate
			 && 
			StandardOPs.includes(((List<CardPayment>)EntityManager.getAllInstancesOf(CardPayment.class)), cdp)
			 && 
			getCurrentSale().getBelongedstore() == getCurrentStore()
			 && 
			StandardOPs.includes(getCurrentStore().getSales(), getCurrentSale())
			 && 
			getCurrentSale().getIsComplete() == true
			 && 
			getCurrentSale().getTime().isEqual(LocalDate.now())
			 && 
			EntityManager.saveModified(Sale.class) && EntityManager.saveModified(Store.class)
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
		//string parameters: [cardAccountNumber]
		//all relevant vars : currentSale cdp
		//all relevant entities :  CardPayment
	} 
	 
	static {opINVRelatedEntity.put("makeCardPayment", Arrays.asList("","CardPayment"));}
	
	
	
	
	/* temp property for controller */
	private Object currentSaleLinePK;
	private SalesLineItem currentSaleLine;
	private Object currentSalePK;
	private Sale currentSale;
	private PaymentMethod currentPaymentMethod;
			
	/* all get and set functions for temp property*/
	public SalesLineItem getCurrentSaleLine() {
		return EntityManager.getSalesLineItemByPK(getCurrentSaleLinePK());
	}

	private Object getCurrentSaleLinePK() {
		if (currentSaleLinePK == null)
			currentSaleLinePK = genson.deserialize(EntityManager.stub.getStringState("ProcessSaleServiceImpl.currentSaleLinePK"), String.class);

		return currentSaleLinePK;
	}	
	
	public void setCurrentSaleLine(SalesLineItem currentsaleline) {
		if (currentsaleline != null)
			setCurrentSaleLinePK(currentsaleline.getPK());
		else
			setCurrentSaleLinePK(null);
		this.currentSaleLine = currentsaleline;
	}

	private void setCurrentSaleLinePK(Object currentSaleLinePK) {
		String json = genson.serialize(currentSaleLinePK);
		EntityManager.stub.putStringState("ProcessSaleServiceImpl.currentSaleLinePK", json);
		//If we set currentSaleLinePK to null, the getter thinks this fields is not initialized, thus will read the old value from chain.
		if (currentSaleLinePK != null)
			this.currentSaleLinePK = currentSaleLinePK;
		else
			this.currentSaleLinePK = EntityManager.getGuid();
	}
	public Sale getCurrentSale() {
		return EntityManager.getSaleByPK(getCurrentSalePK());
	}

	private Object getCurrentSalePK() {
		if (currentSalePK == null)
			currentSalePK = genson.deserialize(EntityManager.stub.getStringState("ProcessSaleServiceImpl.currentSalePK"), String.class);

		return currentSalePK;
	}	
	
	public void setCurrentSale(Sale currentsale) {
		if (currentsale != null)
			setCurrentSalePK(currentsale.getPK());
		else
			setCurrentSalePK(null);
		this.currentSale = currentsale;
	}

	private void setCurrentSalePK(Object currentSalePK) {
		String json = genson.serialize(currentSalePK);
		EntityManager.stub.putStringState("ProcessSaleServiceImpl.currentSalePK", json);
		//If we set currentSalePK to null, the getter thinks this fields is not initialized, thus will read the old value from chain.
		if (currentSalePK != null)
			this.currentSalePK = currentSalePK;
		else
			this.currentSalePK = EntityManager.getGuid();
	}
	public PaymentMethod getCurrentPaymentMethod() {
		return currentPaymentMethod;
	}	
	
	public void setCurrentPaymentMethod(PaymentMethod currentpaymentmethod) {
		this.currentPaymentMethod = currentpaymentmethod;
	}
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
