package framework.util;

import java.lang.reflect.Field;

public class CommonLib {
	  	
	  public final String MailExistingPINMsg = "Combined verification and pin letter generated: doc_id = "
	  		              + " If a PIN confirmation letter doc id is displayed then a reminder PIN letter will be"
	  		              + " sent to the participant.  If \"No document to generate.\" displays then a"
	  		              + " reminder PIN letter will not be sent until PIN has been system generated."
	  		              + " CSR should not attempt to issue through ISIS.";	
	  public  final String OrderTempNoteMsg= "Note: this VRU PIN (or web passcode) is a single use PIN (passcode) "
	  											 + "that must be re-set after first use. Please direct participant accordingly.";
	  public  final String OrderTempPINMsgB= "The temporary PIN number is";
	  
	  public  final String MailExistWebPsCd = "An order to mail the participant's existing web passcode "
	  											  + "has been issued, confirmation #.";
	  public  final String MailExistVRUpin =  "An order to mail the participant's existing VRU PIN "
	  									 		  + "has been issued, confirmation #.";
	  public  final String OrderTempNote = "Note: this VRU PIN (or web passcode) is a single use PIN (passcode) that must "
	  										   + "be re-set after first use. "
	  										   + "Please direct participant accordingly.";
	  public  final String OrderTempWebPsCd = "The temporary web passcode number is";
	  
	  public  final String OrderTempVRUPIN = "The temporary VRU PIN number is";
	
	  public  Object getVarByName(String fieldName) throws Throwable {
	        Field field = this.getClass().getDeclaredField(fieldName);
	        return field.get(this);
	  }

	  
	  
}