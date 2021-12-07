# Ryans Journal #

## Payments API & Cybersource ##
* I was assigned to work on our payments API and integrating it with cybersource. I imported the code from lab7 spring payments and updated it according to what was necessary for our project.

* I first started by creating a team cybersource account then generating a key to be used for our API.
```
cybersource.merchantkeyid=5bd25a12-0866-41b3-8032-d0f2cbdee79e
cybersource.merchantsecretkey=k6mXIYoPxYuu4m9WHZT+dfWlGzP0oWfk+2f3V/lVADs=
cybersource.merchantid=thebeaconsarelit
cybersource.apihost=apitest.cybersource.com
```
* I then implement the PaymentController by taking in the payments command which held all the billing information and the cart information since I needed the price.
```
 @GetMapping("api/payment/{userid}")
    public String getAction( PaymentsCommand command, PhilzCart cart, Model model) {

        return "payment" ;

    }

    @PostMapping("api/payment/{userid}")
    public String postAction(@Validated PaymentsCommand command, PhilzCart cart,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {

	public String postAction(@Validated /*@ModelAttribute("command")*/ PaymentsComma
        if(hasErrors){
            msgs.print();
            model.addAttribute("messages", msgs.getMessages());
            return "payment";
        }

        int min = 1239871;
        int max = 9999999;
        int random_int = (int) Math.floor(Math.random()*(max-min+1)+min);
        String order_num = String.valueOf(random_int);
        String total = String.valueOf(cart.getTotal());
        AuthRequest auth = new AuthRequest() ;
		auth.reference = order_num;
		auth.billToFirstName = command.firstname() ;
	public String postAction(@Validated /*@ModelAttribute("command")*/ PaymentsComma
        if (auth.cardType.equals("Error")){
            System.out.println("Unsupported Credit Card Type.");
            model.addAttribute("message", "Unsupported Credit Card Type.");
            return "payment";
        }
		boolean authValid = true ;
		AuthResponse authResponse = new AuthResponse() ;
	public String postAction(@Validated /*@ModelAttribute("command")*/ PaymentsComma
            authValid = false ;
            System.out.println(authResponse.message);
            model.addAttribute("message", authResponse.message);
            return "payment";
		}

        boolean captureValid = true ;
	public String postAction(@Validated /*@ModelAttribute("command")*/ PaymentsComma
                captureValid = false;
                System.out.println(captureResponse.message);
                model.addAttribute("message", captureResponse.message);
                return "payment";
            }
        }

	public String postAction(@Validated /*@ModelAttribute("command")*/ PaymentsComma
        repository.save(command);


        return "payment";
    }

}
```
* After some issues with trying to get the cart price we decided on merging the philz-payment into the philz-cart folder. This would make it easier to grab the price and update the cart status while making it easier for ngan to connect from front end.
https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/137e2fd60f1b5258494da462d590bf10df292f93

* We then ran into some issues with the cart and decided to change that idea to just buying a single product. With the help of Hieu this became our final paymentsController that we would use.

```
@PostMapping(value = "api/payment/{email}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makePayment(@PathVariable String email, @RequestBody PaymentsInfo paymentsInfo,    
                            Errors errors, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        try{
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
                jsonObject.put("message",  msgs.toString());
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
                jsonObject.put("message", "Unsupported Credit Card Type.");
            }
            boolean authValid = true ;
            AuthResponse authResponse = new AuthResponse() ;
            System.out.println("\n\nAuth Request: " + auth.toJson() ) ;
            authResponse = api.authorize(auth) ; //process payment to cybersource
            System.out.println("\n\nAuth Response: " + authResponse.toJson() ) ;
            if ( !authResponse.status.equals("AUTHORIZED") ) {
                authValid = false ;
                System.out.println(authResponse.message);
                jsonObject.put("message", authResponse.message);
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
                    jsonObject.put("message",captureResponse.message);
                }
            }

            if (authValid && captureValid){
                jsonObject.put("message","Successful Payment! for " + email);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
            }        



            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
        }catch(JSONException e){
            try {
				jsonObject.put("exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
        }
    }

}
```
## API TESTING ##
* Once the Payments API was finished I ran tests in postman locally using this data to confirm that it works.

```
{
    "firstname": "TheBeacons",
    "lastname": "AreLit",
    "address": "1 Washington Sq.",
    "city": "San Jose",
    "state": "CA",
    "zip": "12334",
    "phone": "(123) 456-7890",
    "cardnum": "4622-9431-2701-3705", 
    "cardexpmon":"December",
    "cardexpyear": "2022",
    "cardcvv": "838",
    "email": "foo@bar.com"
    }
```

* It did work successfully and the transactions can be seen in cybersource.

![image](https://user-images.githubusercontent.com/56413249/144986437-5cd854a8-ede3-4f1b-bf05-0ee215b9c770.png)

* It would also be able to reject invalid data like having an invalid cardexpyear.

![image](https://user-images.githubusercontent.com/56413249/144987517-0ad9fddd-f7aa-4086-848c-ac4c685fcf42.png)

## RABIITMQ IMPLEMENTATION ##
* After that was done Mary and I collaborated on getting rabbitmq to work locally. The first attempt consisted of attempting to send a JsonObject of our product through the queue for our ProductMessageListener to work.
https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/5bb1a306bb5905199b4221fed5c9f366f59c8041
```
public class ProductMessageListener {

    private PhilzProductRepository productRepository;

    private static final Logger log = LogManager.getLogger(ProductMessageListener.class);

    public ProductMessageListener(PhilzProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * This method is invoked whenever any new message is put in the queue.
     * @param message
     */
    public void receiveMessage(String message) {
        log.info("Received <" + message + ">");
        //Long id = Long.valueOf(message.get("id"));
        //PhilzProducts product = productRepository.findById(id).orElse(null);
      

        //productRepository.save(product);
        log.info("Message processed...");
    }
} 
```
```
public class PaymentsController {  

    @Value("${cybersource.merchantsecretkey}") private String merchantsecretKey ;
    @Value("${cybersource.merchantid}") private String merchantId ;

    @Autowired
    private RabbitTemplate template;

    private PhilzProductRepository repository;

    private CyberSourceAPI api = new CyberSourceAPI();

    private static Map<String, String> months = new HashMap<>();


    }

    public PaymentsController(RabbitTemplate rabbitTemplate, PhilzProductRepository repository) {
        this.template = rabbitTemplate;
        this.repository = repository;
    }

    @PostMapping(value = "api/payment/{productid}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> makePayment(@PathVariable String productid, @RequestBody PaymentsInfo paymentsInfo,    
                            Errors errors, HttpServletRequest request) {

        PhilzProducts products = repository.findByProductID(productid);

        JSONObject jsonProduct = new JSONObject();

        JSONObject jsonObject = new JSONObject();
        try{

            jsonProduct.put("product", products);

            CyberSourceAPI.setHost( apiHost );
            CyberSourceAPI.setKey( merchantKeyId );
            CyberSourceAPI.setSecret(merchantsecretKey);
	

            if (authValid && captureValid){
                jsonObject.put("message","Successful Payment! for " + paymentsInfo.email());
                template.convertAndSend(PhilzProductApplication.queueName, jsonProduct);
                return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
            }        




            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);

}
```
```
@SpringBootApplication
public class PhilzProductApplication {


	static final String topicExchangeName = "spring-boot-exchange";

	public static final String queueName = "spring-boot";
  

	@Bean
	Queue queue() {
	  return new Queue(queueName, false);
	}
  
	@Bean
	TopicExchange exchange() {
	  return new TopicExchange(topicExchangeName);
	}
  
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
	  return BindingBuilder.bind(queue).to(exchange).with("foobar");
	}
  
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
		MessageListenerAdapter listenerAdapter) {
	  SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	  container.setConnectionFactory(connectionFactory);
	  container.setQueueNames(queueName);
	  container.setMessageListener(listenerAdapter);
	  return container;
	}
  
	@Bean
	MessageListenerAdapter listenerAdapter(ProductMessageListener receiver) {
	  return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Profile("usage_message")
    @Bean
    public CommandLineRunner usage() {
        return args -> {
            System.out.println("This app uses Spring Profiles to control its behavior.\n");
                    System.out.println("Sample usage: java -jar spring-rabbitmq-helloworld-1.0.jar --spring.profiles.active=hello-world,sender");
        };
    }

    @Profile("!usage_message")
    @Bean
    public CommandLineRunner tutorial() {
        return new RabbitAmqpTutorialsRunner();
    }

	@Bean
	public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() {
		Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
		factory.setResources(new Resource[]{new ClassPathResource("json/drinks.json")});
		return factory;
	}

	public static void main(String[] args) {
		SpringApplication.run(PhilzProductApplication.class, args);
		//SpringApplication.run(PhilzPaymentApplication.class, args);
		//SpringApplication.run(MessagingRabbitmqApplication.class, args).close();
	}

}
```
* We ran into issues with sending a jsonObject so we changed it to just send the order number instead. That also didnt work at first and we realized the mistake was in PhilzProductApplication.java. We were using a dummy routing key instead of the name of our queue.

```
return BindingBuilder.bind(queue).to(exchange).with("foobar");
```
* Needed to be changed to

```
return BindingBuilder.bind(queue).to(exchange).with(queueName);
```
* This became our final version of rabbitmq and it worked locally. When a payment was successfully made it would send that order number in the queue for the ProductMessageListener to recieve the order.
https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/8c8a2d8cc1aa5bbe3743fe8803b1d823ee250dea

![image](https://user-images.githubusercontent.com/56413249/144986588-e62ffa44-5979-4253-a1e6-8e768f3d2b2f.png)

## MYSQL GKE ##

* Once that was done I tried to get MySQL instance running on GKE with our philzCustomer database. I had everything up and running but for some reason ingress would say "All backened states are unhealthy". This causes issues trying to create users and put them into the database and ultimately we could not get it to work.
