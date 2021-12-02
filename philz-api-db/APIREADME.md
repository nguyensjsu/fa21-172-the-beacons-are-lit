lab4 - security
lab6 - SQL/DB
lab7 - Cybersource payments
lab8 - REST API + kong
lab9 - Rabbit MQ

Customer Orders -> in memory database (temporary) | not recording order history
Customers/User -> in SQL
All Order types -> Hard coded into codebase. 
Credit card -> Enter everytime customer orders -> verify credit card upon order via cybersource. 


Website:
- have to create an account -> registered into the database
  - Reset
- Order list. Pick an order. Order
- After order, they add to cart
- During cart/payment page. Add credit card. -> verified by cybersource
- If all goes well. Go back to home page, "order finished/successful payment"

api/addtocart/addProduct
- only add to cart

api/addtocart/updateQtyForCart
- get amount of items in the cart