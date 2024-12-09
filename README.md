Overview
This Spring Boot application calculates reward points for customers based on their transactions as part of a retailer rewards program. The reward points calculation follows these rules:

Points Calculation:

2 points for every dollar spent over $100 in a single transaction.
1 point for every dollar spent between $50 and $100 in a single transaction.
No points for transactions below $50.
Example: A $120 purchase earns 2*20 + 1*50 = 90 points.
Features:

Calculate reward points earned by each customer per month and total.
Use a MongoDB database to store customer and transaction data.
Initialize MongoDB data using Docker Compose.
REST APIs to fetch monthly and total reward points for customers.
Technologies Used
Spring Boot (RESTful web services)
MongoDB (NoSQL database)
Docker & Docker Compose (Database containerization)
Java 21 (or compatible JDK version)
Maven (Build and dependency management)
Setup Instructions
1. Prerequisites
Ensure you have the following installed on your system:

Docker & Docker Compose
Java 17 or later
Maven
2. Clone the Repository
bash
Copy code
git clone <https://github.com/praveen7201/customer-rewards-springboot-application.git>
cd customer-rewards-springboot-application

3. Build the Application
Run the following Maven command to package the application:

bash
Copy code
mvn clean package

4. Setup MongoDB using Docker Compose
A docker-compose.yml file is included to set up a MongoDB instance. Run the following command to start MongoDB:

bash
Copy code
docker-compose up -d
This will Seed the database with initial data

Start a MongoDB container (mongo) on port 27017.

5. Run the Application
Start the Spring Boot application:

bash
Copy code
java -jar target/customer-rewards-springboot-application-0.0.1-SNAPSHOT.jar
The application will start on http://localhost:8080.

Application Endpoints
HTTP Method	Endpoint	Description
GET	/customerRewards/{customerId}/total	Fetch total rewards for a customer in a three month period
GET	/customerRewards/{customerId}/monthly/{month}/{year}	Fetch monthly rewards for a customer
POST	/customerRewards/addPoints	Add a new Customer transaction
Docker Compose Configuration
The docker-compose.yml file sets up a MongoDB container and initializes the database using transactions.json.

File: docker-compose.yml

yaml
Copy code
services:
  mongodb:
    image: mongo:latest
    container_name: mongodb
    ports:
      - "27017:27017" # Maps port 27017 on the host to port 27017 in the container
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: admin
      MONGO_INITDB_DATABASE: retailerDatabase
    volumes:
      - mongo-data:/data/db
      - ./mongo-init.js:/docker-entrypoint-initdb.d/mongo-init.js:ro

volumes:
  mongo-data:Database Initialization Place and the initialization script (init-mongo.js) in the init directory:

init-mongo.js:
javascript
Copy code
db.createUser({
  user: "retailer",
  pwd: "retailer",
  roles: [
    { role: "readWrite", db: "retailerDatabase" }
  ]
});
Rewards Calculation Logic
Fetch customer transactions from the MongoDB database for the customer and date range.
Apply the rewards formula:
Add 2 points for every dollar over $100.
Add 1 point for every dollar between $50 and $100.
Aggregate points by month and customer.
Sample Requests
Get Total Rewards for a Customer:

bash
Copy code
curl -X GET http://localhost:8080/customerRewards/{customerId}/monthly/{month}/{year}
Get Monthly Rewards for a Customer:

bash
Copy code
curl -X GET http://localhost:8080/customerRewards/{customerId}/monthly/{1}/{2024}
Add a Customer Transaction:

bash
Copy code
curl -X POST -H "Content-Type: application/json" \
-d '{"customerId": "abc123", "amountSpent": 150, "rewardPoints": 2}' \
http://localhost:8080/customerRewards/addPoints
Project Structure
bash
Copy code
src
├── main
│   ├── java
│   │   └── com.sandusky.retailer
|   |       |__ bean        # Request Bean
│   │       ├── controller   # REST Controllers
│   │       ├── service      # Business Logic
│   │       ├── repository   # Mongo Repositories
│   │       ├── model        # Data Models (CustomerTransaction)
│   │       └── exceptionHandler    # Exception Handling
|   |       |__ util         # Utility classes
│   └── resources
│       ├── application.properties # Spring Boot Configuration
└── test # Unit and Integration Tests
Customization
Update application.properties to adjust MongoDB configurations or application port.
