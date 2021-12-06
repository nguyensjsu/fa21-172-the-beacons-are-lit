package com.example.philzpayment;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import org.hibernate.annotations.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.example.cybersource.*;

import org.springframework.beans.factory.annotation.Value;


@Controller
public class PaymentsController {  

    private static boolean DEBUG = true ;

    @Value("${cybersource.apihost}") private String apiHost ;
    @Value("${cybersource.merchantkeyid}") private String merchantKeyId ;
    @Value("${cybersource.merchantsecretkey}") private String merchantsecretKey ;
    @Value("${cybersource.merchantid}") private String merchantId ;

    private CyberSourceAPI api = new CyberSourceAPI();

    private static Map<String, String> months = new HashMap<>();
    static{
        months.put("January", "01");
        months.put("February", "02");
        months.put("March", "03");
        months.put("April", "04");
        months.put("May", "05");
        months.put("June", "06");
        months.put("July", "07");
        months.put("August", "08");
        months.put("September", "09");
        months.put("October", "10");
        months.put("November", "11");
        months.put("December", "12");
    }

    private static Map<String, String> states = new HashMap<>();
    static{
        states.put("AL", "Alabama");
        states.put("AK", "Alaska");
        states.put("AZ", "Arizona");
        states.put("AR", "Arkansas");
        states.put("CA", "California");
        states.put("CO", "Colorado");
        states.put("CT", "Connecticut");
        states.put("DE", "Delaware");
        states.put("FL", "Florida");
        states.put("GA", "Georgia");
        states.put("HI", "Hawaii");
        states.put("ID", "Idaho");
        states.put("IL", "Illinois");
        states.put("IN", "Indiana");
        states.put("IA", "Iowa");
        states.put("KS", "Kansas");
        states.put("KY", "Kentucky");
        states.put("LA", "Louisiana");
        states.put("ME", "Maine");
        states.put("MD", "Maryland");
        states.put("MA", "Massachusetts");
        states.put("MI", "Michigan");
        states.put("MN", "Minnesota");
        states.put("MS", "Mississippi");
        states.put("MO", "Missouri");
        states.put("MT", "Montana");
        states.put("NE", "Nebraska");
        states.put("NV", "Nevada");
        states.put("NH", "New Hampshire");
        states.put("NJ", "New Jersey");
        states.put("NM", "New Mexico");
        states.put("NY", "New York");
        states.put("NC", "North Carolina");
        states.put("ND", "North Dakota");
        states.put("OH", "Ohio");
        states.put("OK", "Oklahoma");
        states.put("OR", "Oregon");
        states.put("PA", "Pennsylvania");
        states.put("RI", "Rhode Island");
        states.put("SC", "Sotuh Carolina");
        states.put("SD", "South Dakota");
        states.put("TN", "Tennessee");
        states.put("TX", "Texas");
        states.put("UT", "Utah");
        states.put("VT", "Vermont");
        states.put("VA", "Virginia");
        states.put("WA", "Washington");
        states.put("WV", "West Virginia");
        states.put("WI", "Wisconsin");
        states.put("WY", "Wyoming");
    }

    @Getter
    @Setter
    class Message{
        private String msg;
        public Message(String m) { msg = m ;}
    }

    class ErrorMessages{
        private ArrayList<Message> messages = new ArrayList<Message>();
        public void add( String msg ) { messages.add(new Message(msg)) ;}
        public ArrayList<Message> getMessages() { return messages ;}
        public void print(){
            for ( Message m: messages) {
                System.out.println(m.msg) ;
            }
        }

    }


    @PostMapping(value = "api/payment/{email}")
    public String postAction(@PathVariable String email, @RequestBody PaymentsInfo paymentsInfo,    
                            Errors errors, HttpServletRequest request) {
    
        CyberSourceAPI.setHost( apiHost );
        CyberSourceAPI.setKey( merchantKeyId );
        CyberSourceAPI.setSecret(merchantsecretKey);
        CyberSourceAPI.setMerchant(merchantId);

        ErrorMessages msgs = new ErrorMessages() ;

        boolean hasErrors = false ;
        if( paymentsInfo.firstname().equals("")) { hasErrors = true; msgs.add( "First Name Required.") ;}
        if( paymentsInfo.lastname().equals("")) { hasErrors = true; msgs.add( "Last Name Required.") ;}
        if( paymentsInfo.address().equals("")) { hasErrors = true; msgs.add( "Address Required.") ;}
        if( paymentsInfo.city().equals("")) { hasErrors = true; msgs.add( "City Required.") ;}
        if( paymentsInfo.state().equals("")) { hasErrors = true; msgs.add( "State Required.") ;}
        if( paymentsInfo.zip().equals("")) { hasErrors = true; msgs.add( "Zip Required.") ;}
        if( paymentsInfo.phone().equals("")) { hasErrors = true; msgs.add( "Phone Required.") ;}
        if( paymentsInfo.cardnum().equals("")) { hasErrors = true; msgs.add( "Credit Card Number Required.") ;}
        if( paymentsInfo.cardexpmon().equals("")) { hasErrors = true; msgs.add( "Credit Card Expiration Month Required.") ;}
        if( paymentsInfo.cardexpyear().equals("")) { hasErrors = true; msgs.add( "Credit Card Expiration Year Required") ;}
        if( paymentsInfo.cardcvv().equals("")) { hasErrors = true; msgs.add( "Credit Card CVV Required.") ;}
        if( paymentsInfo.email().equals("")) { hasErrors = true; msgs.add( "Email Address Required.") ;}

        if(!paymentsInfo.zip().matches("\\d{5}")) { hasErrors = true; msgs.add("Invalid Zip Code: " + paymentsInfo.zip());}
        if(!paymentsInfo.phone().matches("[(]\\d{3}[)] \\d{3}-\\d{4}")) { hasErrors = true; msgs.add("Invalid Phone Number: " + paymentsInfo.phone());}
        if(!paymentsInfo.cardnum().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) { hasErrors = true; msgs.add("Invalid Card Number: " + paymentsInfo.cardnum());}
        if(!paymentsInfo.cardexpyear().matches("\\d{4}")) { hasErrors = true; msgs.add("Invalid Card Expiration Year " + paymentsInfo.cardexpyear());}
        if(!paymentsInfo.cardcvv().matches("\\d{3}")) { hasErrors = true; msgs.add("Invalid Card CVV: " + paymentsInfo.cardcvv());}

        if(months.get(paymentsInfo.cardexpmon()) == null) {hasErrors = true; msgs.add("Invalid Card Expiration Month: " + paymentsInfo.cardexpmon());}
        if(states.get(paymentsInfo.state()) == null) {hasErrors = true; msgs.add("Invalid State: " + paymentsInfo.state());}


        if(hasErrors){
            msgs.print();
            return "payment";
        }

        int min = 1239871;
        int max = 9999999;
        int random_int = (int) Math.floor(Math.random()*(max-min+1)+min);
        String order_num = String.valueOf(random_int); //Create random order number
        String total =("18.50");
        AuthRequest auth = new AuthRequest() ;
		auth.reference = order_num;
		auth.billToFirstName = paymentsInfo.firstname() ;
		auth.billToLastName = paymentsInfo.lastname()  ;
		auth.billToAddress = paymentsInfo.address() ;
		auth.billToCity = paymentsInfo.city()  ;
		auth.billToState = paymentsInfo.state()  ;
		auth.billToZipCode = paymentsInfo.zip()  ;
		auth.billToPhone = paymentsInfo.phone()  ;
		auth.billToEmail = paymentsInfo.email()  ;
		auth.transactionAmount = total;
		auth.transactionCurrency = "USD"  ;
		auth.cardNumnber = paymentsInfo.cardnum()  ;
		auth.cardExpMonth = months.get(paymentsInfo.cardexpmon());
		auth.cardExpYear = paymentsInfo.cardexpyear()  ;
		auth.cardCVV = paymentsInfo.cardcvv() ;
		auth.cardType = CyberSourceAPI.getCardType(auth.cardNumnber)  ;
        if (auth.cardType.equals("Error")){
            System.out.println("Unsupported Credit Card Type.");
            return "payment";
        }
		boolean authValid = true ;
		AuthResponse authResponse = new AuthResponse() ;
		System.out.println("\n\nAuth Request: " + auth.toJson() ) ;
		authResponse = api.authorize(auth) ; //process payment to cybersource
		System.out.println("\n\nAuth Response: " + authResponse.toJson() ) ;
		if ( !authResponse.status.equals("AUTHORIZED") ) {
            authValid = false ;
            System.out.println(authResponse.message);
            return "payment";
		}

        boolean captureValid = true ;
		CaptureRequest capture = new CaptureRequest() ;
		CaptureResponse captureResponse = new CaptureResponse();
        if ( authValid){
            capture.reference = order_num;
            capture.paymentId = authResponse.id;
            capture.transactionAmount = total;
            capture.transactionCurrency = "USD";
            System.out.println("\n\nCapture Request: " + capture.toJson());
            captureResponse = api.capture(capture);
            System.out.println("\n\nCapture Response: " + captureResponse.toJson());
            if( ! captureResponse.status.equals("PENDING")) {
                captureValid = false;
                System.out.println(captureResponse.message);
                return "payment";
            }
        }

        if (authValid && captureValid){
            return "Successful Payment! for " + email; 
        }        

     

        return "Payment failure";
    }

}