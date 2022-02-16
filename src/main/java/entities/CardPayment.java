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
import converters.*;
import com.owlike.genson.annotation.*;

@DataType()
public class CardPayment extends Payment  implements Serializable {

	// Without @JsonProperty, genson will not set this field during deserialization.
	@JsonProperty
	private final String guid = EntityManager.getGuid();
	public Object getPK() {
		return guid;
	}
	
	/* all primary attributes */
	@Property()
	private String cardAccountNumber;
	@Property()
	private LocalDate expiryDate;
	
	/* all references */
	
	/* all get and set functions */
	public String getCardAccountNumber() {
		return cardAccountNumber;
	}	
	
	public void setCardAccountNumber(String cardaccountnumber) {
		this.cardAccountNumber = cardaccountnumber;
	}
	@JsonConverter(LocalDateConverter.class)
	public LocalDate getExpiryDate() {
		return expiryDate;
	}	
	
	@JsonConverter(LocalDateConverter.class)
	public void setExpiryDate(LocalDate expirydate) {
		this.expiryDate = expirydate;
	}
	
	/* all functions for reference*/
	


}
