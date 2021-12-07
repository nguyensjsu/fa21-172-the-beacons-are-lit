I was assigned to work on our payments API and integrating it with cybersource. I imported the code from lab7 spring payments and updated it according to what was necessary for our project.

I first started by creating a team cybersource account then generating a key to be used for our API.
```
cybersource.merchantkeyid=5bd25a12-0866-41b3-8032-d0f2cbdee79e
cybersource.merchantsecretkey=k6mXIYoPxYuu4m9WHZT+dfWlGzP0oWfk+2f3V/lVADs=
cybersource.merchantid=thebeaconsarelit
cybersource.apihost=apitest.cybersource.com
```
I then implement the PaymentController by taking in the payments command which held all the billing information and the cart information since I needed the price.
```
 @GetMapping("api/payment/{userid}")
    public String getAction( PaymentsCommand command, PhilzCart cart, Model model) {

        return "payment" ;

    }

    @PostMapping("api/payment/{userid}")
    public String postAction(@Validated PaymentsCommand command, PhilzCart cart,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {

	@@ -186,11 +183,14 @@ public String postAction(@Validated /*@ModelAttribute("command")*/ PaymentsComma
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
	@@ -211,7 +211,7 @@ public String postAction(@Validated /*@ModelAttribute("command")*/ PaymentsComma
        if (auth.cardType.equals("Error")){
            System.out.println("Unsupported Credit Card Type.");
            model.addAttribute("message", "Unsupported Credit Card Type.");
            return "payment";
        }
		boolean authValid = true ;
		AuthResponse authResponse = new AuthResponse() ;
	@@ -222,7 +222,7 @@ public String postAction(@Validated /*@ModelAttribute("command")*/ PaymentsComma
            authValid = false ;
            System.out.println(authResponse.message);
            model.addAttribute("message", authResponse.message);
            return "payment";
		}

        boolean captureValid = true ;
	@@ -240,7 +240,7 @@ public String postAction(@Validated /*@ModelAttribute("command")*/ PaymentsComma
                captureValid = false;
                System.out.println(captureResponse.message);
                model.addAttribute("message", captureResponse.message);
                return "payment";
            }
        }

	@@ -258,7 +258,7 @@ public String postAction(@Validated /*@ModelAttribute("command")*/ PaymentsComma
        repository.save(command);


        return "payment";
    }

}
```
After some issues with trying to get the cart price we decided on merging the philz-payment into the philz-cart folder. This would make it easier to grab the price and update the cart status while making it easier for ngan to connect from front end.
https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/137e2fd60f1b5258494da462d590bf10df292f93

We then ran into some issues with a cart and decided to change that idea to just buying a single product. Once the Payments API was finished I ran tests in postman locally using this data to confirm that it works.

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

It did work successfully and the transactions can be seen in cybersource.

After that was done Mary and I collaborated on getting rabbitmq to work locally. The first attempt consisted of attempting to send a JsonObject of our product through the queue for our ProductMessageListener to work.
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

We ran into issues with sending a jsonObject so we changed it to just send the order number instead. This became our final version of rabbitmq and it worked locally. When a payment was successfully made it would send that order number in the queue for the ProductMessageListener to recieve the order.
https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/8c8a2d8cc1aa5bbe3743fe8803b1d823ee250dea

Once that was done I tried to get MySQL instance running on GKE with our philzCustomer database. I had everything up and running but for some reason ingress would say "All backened states are unhealthy". This causes issues trying to create users and put them into the database and ultimely we could not get it to work.
