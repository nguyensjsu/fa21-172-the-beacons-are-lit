# Team Project (The Beacons are Lit)

- “The Beacons of Minas Tirith! The Beacons are lit! Gondor calls for aid!”
- "And Rohan will answer!"

## Week of October 24th - 30th

#### 10/30/2021

- We decided we would model our project after Philz Coffee

## Week of November 21st-27th

#### 11/21/2021

- We held our first official Team meeting and decided what the structure of our project would look like 

- Commit 2c0c351:
  - https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/2c0c35165d7f0eb82ecc89d7e1a05cda583e792e:
  - We used Lab 5 as the base starter code for our project

#### 11/22/2021

- Added folders
- Renamed Package to fit with our Philz theme
- Added a react front-end

#### 11/23/2021

- We started divided up task and responsibilties into
  1. Database - Hieu
  2. Cybersource - Ryan
  3. Security + Frontend - Ngan
  4. API's + RabbitMQ - Mary

#### 11/24/2021

- The beginings of a frontend are starting to form
  - https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/b5b6b723868370626983329936d602ef4f6720d6

## Week of November 28th - December 4th

#### 11/28/2021

- Started implementing APIs for ordering
  - https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/d15334204be8b5a4beab259127ea18ec8c4a4c5a

#### 11/29/2021

- Minor edits to code

#### 11/30/2021

- CyberSource payments are implemented for Philz
  - https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/e3f529555ce976fe2d5f3add2ca1cf824bb0368f

#### 12/1/2021

#### 12/2/2021

- Relooked at project setup. Realized that setup was still monolithic. 
- Led team to re-make architecture to have 3 microservices and a frontend
- https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/8793e32de5247f19c783b9eaf37f6a33bb9b247f
  1. Philz Customers
  2. Philz Cart
  3. Philz Payment
     - https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/45eef4fc88fdcec14714e7b72f2b4b96867609dc

- Refactor and fix issues related to the new architecture

- ```markdown
  ## Lab references
  lab4 - security
  lab6 - SQL/DB
  lab7 - Cybersource payments
  lab8 - REST API + kong
  lab9 - Rabbit MQ
  
  ## Customer Flow notes
  //Customer Orders/Cart -> in memory database (temporary) | not recording order history
  Customers/User -> in SQL
  All product type -> Hard coded into codebase. 
  Credit card -> Enter everytime customer orders -> verify credit card upon order via cybersource. 
  
  
  ## Website:
  - have to create an account -> registered into the database
    - Reset
  - Order list. Pick an order. Order
  - After order, they add to cart
  - During cart/payment page. Add credit card. -> verified by cybersource
  - If all goes well. Go back to home page, "order finished/successful payment"
  
  ## API 
  @Get, @Post, @Delete, @Put
  
  ### Frontend
  "front end" - interactive interface, react. 
  
  ### Backend 1
  - Security Here
  api/login @Get
  api/login/reset @Get
  api/login/reset/{userid} @Put
  api/register @Get
  api/register/{createduserid} @Post
  - create the entry in ths sql db
  
  ### Backend 2
  api/products/{userid} @Get #every product will be on here. This is also the home page @Get
  api/cart/{userid} @Get
  api/cart/{userid}
  api/cart/{userid} @Delete #delete entire cart
  
  ### Backend 3
  - RabbitMQ Here
  - Cybersource here
  api/payment/{userid} @Get
  api/payment/{userid} @Post
  
  ## Database & Data Storage spec
  User - SQL
  - Long index id
  - String name
  - String email
  - String password
  - String loginToken (Indicate if a person is logged in or not)
  - String address
  - String mobile
  
  List products = new ArrayList<>() //all products for a specific user
  Hashmap<userid, productlist> map = new hashmap()
  map.get(userid).remove(add)
  
  product - class
  Class Product{
      String id; 
      String type
      ...
  
  }
  
  Carts - In memory
  - list of all carts in existence
  ```

- 

#### 12/3/2021

- More edits to reconfigure the arrangement of files
- PhilzCart and PhilzProducts are created

  ````java
  @Entity
  @Table(name = "CUSTOMER_CART")
  @ToString
  @Setter
  @EqualsAndHashCode
  @RequiredArgsConstructor
  @Getter
  public class PhilzCart {
  
      private @Id @GeneratedValue Long id;
  
      @OneToMany
      private List<PhilzProducts> order;
  
      private String userId;
  
      private Status status;
  
      private double total;
  
      public void addProduct(PhilzProducts product) {
          order.add(product);
      }
  }
  ````

  ```java
  @Entity
  @Table(name="PhilzProducts")
  @Data
  @RequiredArgsConstructor
  public class PhilzProducts {
  	@Id @GeneratedValue
  	long id;
  	String name;
  	String roast;
  	final double price = 18.50;
  
  } 
  ```


  - There is discussion about whether the Products should be hardcoded or stored in memory and both ways are implemented and tested to see which is easier to work with. The PhilzProducts are stored as an @Entiity in memory to make 

#### December 4, 2021

- Finished PhilzCart and Tested all the APIs with Postman
  - https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/26d4c4a8e30114047d56f217b9f1b814003fef06
- Started to work on porting H2 in mem database to SQL and adding reset functionality | Work on deleted branch. 

### December 5, 2021

- There were issues with PhilzCart working on the frontend so PhilzCart was merged with PhilzPayment so that we could consoladate the number of POST requests we needed.
- There were also issues sending cart information to PhilzPayment so merging the two made it easier to implement. 
- Decided to remove cart functionality entirely to make it simpler for the front end. Now only product browsing capability and a separate payments page
- Attempted to start consolidating for service simplification. Initial work done by Hieu but decided to leave to mary to modify previous work instead of starting from scratch. 
- https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/commit/e153ec78c04a389ea1af6453388d6e919ea9f702
- Finished reset
- Modified Payments to work with REST api
- Initial work with Kong. 
- Started work on RabbitMQ

### December 6th, 2021
- RabbitMQ works by morning, but only on local instance
- Continued work to get Kong running. Only worked on one service locally & on GKE
- Attempting to bring local SQL to use GKE instance. Failed with errors related to Ingress
- Had to change reset to work with api
- Deployed Cart service successfully however Rabbit failed to function. 
- Presentation

# Architecture

![](https://github.com/nguyensjsu/fa21-172-the-beacons-are-lit/blob/main/diagram.png)
