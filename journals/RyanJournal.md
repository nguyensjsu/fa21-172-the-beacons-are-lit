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


