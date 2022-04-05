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
import com.owlike.genson.annotation.*;
import java.util.stream.*;

@DataType()
public class Store implements Serializable {
	public Object getPK() {
		return getId();
	}
	
	/* all primary attributes */
	@Property()
	private int id;
	@Property()
	private String name;
	@Property()
	private String address;
	@Property()
	private boolean isOpened;
	
	/* all references */
	@JsonProperty
	private List<Object> AssociationCashdeskesPKs = new LinkedList<>();
	private List<CashDesk> AssociationCashdeskes = new LinkedList<CashDesk>(); 
	@JsonProperty
	private List<Object> ProductcatalogsPKs = new LinkedList<>();
	private List<ProductCatalog> Productcatalogs = new LinkedList<ProductCatalog>(); 
	@JsonProperty
	private List<Object> ItemsPKs = new LinkedList<>();
	private List<Item> Items = new LinkedList<Item>(); 
	@JsonProperty
	private List<Object> CashiersPKs = new LinkedList<>();
	private List<Cashier> Cashiers = new LinkedList<Cashier>(); 
	@JsonProperty
	private List<Object> SalesPKs = new LinkedList<>();
	private List<Sale> Sales = new LinkedList<Sale>(); 
	
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
	public String getAddress() {
		return address;
	}	
	
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean getIsOpened() {
		return isOpened;
	}	
	
	public void setIsOpened(boolean isopened) {
		this.isOpened = isopened;
	}
	
	/* all functions for reference*/
	@JsonIgnore
	public List<CashDesk> getAssociationCashdeskes() {
		if (AssociationCashdeskes == null)
			AssociationCashdeskes = AssociationCashdeskesPKs.stream().map(EntityManager::getCashDeskByPK).collect(Collectors.toList());
		return AssociationCashdeskes;
	}	
	
	public void addAssociationCashdeskes(CashDesk cashdesk) {
		getAssociationCashdeskes();
		this.AssociationCashdeskesPKs.add(cashdesk.getPK());
		this.AssociationCashdeskes.add(cashdesk);
	}
	
	public void deleteAssociationCashdeskes(CashDesk cashdesk) {
		getAssociationCashdeskes();
		this.AssociationCashdeskesPKs.remove(cashdesk.getPK());
		this.AssociationCashdeskes.remove(cashdesk);
	}
	@JsonIgnore
	public List<ProductCatalog> getProductcatalogs() {
		if (Productcatalogs == null)
			Productcatalogs = ProductcatalogsPKs.stream().map(EntityManager::getProductCatalogByPK).collect(Collectors.toList());
		return Productcatalogs;
	}	
	
	public void addProductcatalogs(ProductCatalog productcatalog) {
		getProductcatalogs();
		this.ProductcatalogsPKs.add(productcatalog.getPK());
		this.Productcatalogs.add(productcatalog);
	}
	
	public void deleteProductcatalogs(ProductCatalog productcatalog) {
		getProductcatalogs();
		this.ProductcatalogsPKs.remove(productcatalog.getPK());
		this.Productcatalogs.remove(productcatalog);
	}
	@JsonIgnore
	public List<Item> getItems() {
		if (Items == null)
			Items = ItemsPKs.stream().map(EntityManager::getItemByPK).collect(Collectors.toList());
		return Items;
	}	
	
	public void addItems(Item item) {
		getItems();
		this.ItemsPKs.add(item.getPK());
		this.Items.add(item);
	}
	
	public void deleteItems(Item item) {
		getItems();
		this.ItemsPKs.remove(item.getPK());
		this.Items.remove(item);
	}
	@JsonIgnore
	public List<Cashier> getCashiers() {
		if (Cashiers == null)
			Cashiers = CashiersPKs.stream().map(EntityManager::getCashierByPK).collect(Collectors.toList());
		return Cashiers;
	}	
	
	public void addCashiers(Cashier cashier) {
		getCashiers();
		this.CashiersPKs.add(cashier.getPK());
		this.Cashiers.add(cashier);
	}
	
	public void deleteCashiers(Cashier cashier) {
		getCashiers();
		this.CashiersPKs.remove(cashier.getPK());
		this.Cashiers.remove(cashier);
	}
	@JsonIgnore
	public List<Sale> getSales() {
		if (Sales == null)
			Sales = SalesPKs.stream().map(EntityManager::getSaleByPK).collect(Collectors.toList());
		return Sales;
	}	
	
	public void addSales(Sale sale) {
		getSales();
		this.SalesPKs.add(sale.getPK());
		this.Sales.add(sale);
	}
	
	public void deleteSales(Sale sale) {
		getSales();
		this.SalesPKs.remove(sale.getPK());
		this.Sales.remove(sale);
	}
	

	/* invarints checking*/
	public boolean Store_UniqueStoreId() {
		
		if (StandardOPs.isUnique(((List<Store>)EntityManager.getAllInstancesOf("Store")), "Id")) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Store_UniqueStoreId()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Store_UniqueStoreId"));

}
