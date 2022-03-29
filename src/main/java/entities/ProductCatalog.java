package entities;

import services.impl.StandardOPs;

import java.util.*;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;
import org.hyperledger.fabric.contract.annotation.*;
import com.owlike.genson.annotation.*;
import java.util.stream.*;

@DataType()
public class ProductCatalog implements Serializable {
	public Object getPK() {
		return getId();
	}
	
	/* all primary attributes */
	@Property()
	private int id;
	@Property()
	private String name;
	
	/* all references */
	@JsonProperty
	private List<Object> ContainedItemsPKs = new LinkedList<>();
	private List<Item> ContainedItems;
	
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
	@JsonIgnore
	public List<Item> getContainedItems() {
		if (ContainedItems == null)
			ContainedItems = ContainedItemsPKs.stream().map(EntityManager::getItemByPK).collect(Collectors.toList());
		return ContainedItems;
	}	
	
	public void addContainedItems(Item item) {
		getContainedItems();
		this.ContainedItemsPKs.add(item.getPK());
		this.ContainedItems.add(item);
	}
	
	public void deleteContainedItems(Item item) {
		getContainedItems();
		this.ContainedItemsPKs.remove(item.getPK());
		this.ContainedItems.remove(item);
	}

	public void prepareClone(HashSet<Object> prepared) {
		if (prepared.contains(this))
			return;
		prepared.add(this);
		getContainedItems().stream().forEach(obj->obj.prepareClone(prepared));
	}

	/* invarints checking*/
	public boolean ProductCatalog_UniqueProductCatalogId() {
		
		if (StandardOPs.isUnique(((List<ProductCatalog>)EntityManager.getAllInstancesOf("ProductCatalog")), "Id")) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (ProductCatalog_UniqueProductCatalogId()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("ProductCatalog_UniqueProductCatalogId"));

}
