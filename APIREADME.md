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

