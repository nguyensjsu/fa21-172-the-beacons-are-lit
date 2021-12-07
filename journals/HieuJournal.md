## Before Week of November 28th - December 4th
- Initial commits to the entire code base based on lab work

## Week of November 28th - December 6th
- Realized setup was still monolithic, got team into a meeting to change up spec. 
  - Changed it so that there would be a front and and three services. One for login/user, another for products/cart and one for payments
  - Initial work done for login/user however delegated back to ngan to modify older files for new ones
  - Started work on user reset functionality. 
- Mary had issue with cart. Attempted to help by having product list autopopulate as an arraylist rather than in memory database
  - Delegated back to mary since decided to only use in memory db. 
- Started port of Login/User service to MYSQL on admin_sql branch. Later deleted to branch to avoid merge issues. Manually copied changes on branch to main later on. 
- Decided with Ngan to remove cart functionality entirely since she had issues implementing with front end. Started to simplify backend service to accomodate. But Mary decided to try to modify her existing work on cart to accomodate changes. 
- Added inital RabbbitMQ to codebase. 
- Ryan's Payment API had issues posting via rest API. Changed Payments to return JSON string and http status. 
- Did a lot of refactoring to remove unused api's to simplify testing. 
- Worked with Kong files to attempt to deploy both cart and user service. User service had issues with 415 error while cart pinged fine. Added Ping controller files for this. 
- Copied over SQL changes + reset from admin_sql to GKE branch to run user service on SQL + Deploy to GKE. 
- Added/Modified yaml files for GKE for deployment. However GKE had issues with ingress availability. 