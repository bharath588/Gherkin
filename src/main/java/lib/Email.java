package lib;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
/**
 * Email - This class contains methods to establish mail server connection and send email
 * @author sndkmt
 *
 */
public class Email
{
	/**
	 * Method to send Email to the recipient
	 */
   public void send()
   {    
      // Recipient's email ID needs to be mentioned.
      String to = "sandeep.kamath@greatwest.com";

      // Sender's email ID needs to be mentioned
      String from = "sandeep.kamath@greatwest.com";

      // Assuming you are sending email from localhost
      //String host = "localhost";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", "den-outlook.its.corp.gwl.com");

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);
		try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Test Email From Java!");

	         // Now set the actual message
	         message.setText("Test EMail");
	        

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	   }
	}
