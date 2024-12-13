# Customer Rewards REST API

## Table of Contents
	1. Introduction
	2. Features
	3. About the Service
	4. Technologies Used
	5. Setup
	6. Usage
	7. API Endpoints
	9. Testing
	10. View MongoDB Database
	11. Technical Document

## Introduction

##### About Spring Boot

Spring Boot is an "opinionated" application bootstrapping framework that makes it easy to create new RESTful services (among other types of applications). It provides many of the usual Spring facilities that can be configured easily usually without any XML. In addition to easy set up of Spring Controllers, Spring Data, etc. Spring Boot comes with the Actuator module that gives the application the following endpoints helpful in monitoring and operating the service:

A brief description on the project:

## Spring Boot Customer Reward Points "Microservice" Example Project

This is a sample Java / Maven / Spring Boot (version 3.3.6) application that can be used as a starter for creating a REST API with MongoDB as datastore.

## Features
	* RESTful API Endpoints for Customer Rewards
	* MongoDB as the database.
	* Built-in validation and exception handling.
	* Configuration-based environment switching.
	* Containerization support through Docker.

## About the Service

The service is just a simple customer transactions rewarding REST service. It uses MongoDB NoSQL Datastore to store the data. If your database connection properties work, you can call REST endpoints defined in ```com.sandusky.retailer.controller.CustomerRewardController``` on **port 8080**. (see below)

You can use this sample service to understand the conventions and configurations that allow you to create a DB-backed RESTful service. Once you understand and get comfortable with the customer rewards app you can add your own services following the same patterns as the sample service.
 
Here is what this little application demonstrates: 

* Full integration with the latest **Spring** Framework: inversion of control, dependency injection, etc.
* Packaging as a single jar with embedded container (tomcat 10.1): No need to install a container separately on the host just run using the ``java -jar`` command
* Writing a RESTful service using annotation: supports JSON request / response; simply use desired ``Accept`` header in your request
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate and MongoDB with just a few lines of configuration and familiar annotations. 
* Automatic Add Document functionality against the data source using Spring *Repository* pattern
* Demonstrates MockMVC test framework with associated libraries

## Technologies Used

	|Technology   | Version |
	|----------- |----------|
	|Spring Boot | 3.3.x	|
	|MongoDB	 | 8.0		|
	|Maven		 | 3.9		|
	|Java		 | 21		|
	|Lombok	  	 | 1.18.x	|

## Setup
    	
##### Steps
	1. Clone the repository:
		git clone https://github.com/praveen7201/customer-rewards-springboot-application.git
		cd customer-rewards-springboot-application
	
	2. Prerequisites
		Install Java 21.
		Install Maven 3.9.
	    Install MongoDB in Docker Container or in Local.
	    Steps for installing MongoDB in Docker container:
	    	1. Ensure you have Docker Desktop installed and running in local
	    	2. Command to install MongoDb, docker-compose up -d
				This will install and MongoDB and Seed the database with initial data.
		Steps for installing MongoDB outside of Docker container:
			Open https://www.mongodb.com/try/download/community and download the software after selecting the desired Version and Platform
		After the above MongoDB steps are completed, ensure you have the "admin" and "retailerDatabase" existing before running the application.
## Usage	
	1. Build the project:
		mvn clean install

	2. Run the application:
		mvn spring-boot:run

	3. Access the API at http://localhost:8080.
```
* Check the stdout or logs/application.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

2024-12-12 02:00:02 INFO  o.s.b.w.e.tomcat.TomcatWebServer - Tomcat initialized with port 8080 (http)
2024-12-12 02:00:02 INFO  o.a.catalina.core.StandardService - Starting service [Tomcat]
2024-12-12 02:00:02 INFO  o.a.catalina.core.StandardEngine - Starting Servlet engine: [Apache Tomcat/10.1.33]
2024-12-12 02:00:02 INFO  o.a.c.c.C.[Tomcat].[localhost].[/] - Initializing Spring embedded WebApplicationContext
2024-12-12 02:00:02 INFO  o.s.b.w.s.c.ServletWebServerApplicationContext - Root WebApplicationContext: initialization completed in 1226 ms
2024-12-12 02:00:02 INFO  org.mongodb.driver.client - MongoClient with metadata {"driver": {"name": "mongo-java-driver|sync|spring-boot", "version": "5.0.1"}, "os": {"type": "Windows", "name": "Windows 10", "architecture": "amd64", "version": "10.0"}, "platform": "Java/Oracle Corporation/21.0.4+8-LTS-274"} created with settings MongoClientSettings{readPreference=primary, writeConcern=WriteConcern{w=null, wTimeout=null ms, journal=null}, retryWrites=true, retryReads=true, readConcern=ReadConcern{level=null}, credential=MongoCredential{mechanism=null, userName='admin', source='admin', password=<hidden>, mechanismProperties=<hidden>}, transportSettings=null, commandListeners=[], codecRegistry=ProvidersCodecRegistry{codecProviders=[ValueCodecProvider{}, BsonValueCodecProvider{}, DBRefCodecProvider{}, DBObjectCodecProvider{}, DocumentCodecProvider{}, CollectionCodecProvider{}, IterableCodecProvider{}, MapCodecProvider{}, GeoJsonCodecProvider{}, GridFSFileCodecProvider{}, Jsr310CodecProvider{}, JsonObjectCodecProvider{}, BsonCodecProvider{}, EnumCodecProvider{}, com.mongodb.client.model.mql.ExpressionCodecProvider@74834afd, com.mongodb.Jep395RecordCodecProvider@75fd65c, com.mongodb.KotlinCodecProvider@d499c13]}, loggerSettings=LoggerSettings{maxDocumentLength=1000}, clusterSettings={hosts=[localhost:27017], srvServiceName=mongodb, mode=SINGLE, requiredClusterType=UNKNOWN, requiredReplicaSetName='null', serverSelector='null', clusterListeners='[]', serverSelectionTimeout='30000 ms', localThreshold='15 ms'}, socketSettings=SocketSettings{connectTimeoutMS=10000, readTimeoutMS=0, receiveBufferSize=0, proxySettings=ProxySettings{host=null, port=null, username=null, password=null}}, heartbeatSocketSettings=SocketSettings{connectTimeoutMS=10000, readTimeoutMS=10000, receiveBufferSize=0, proxySettings=ProxySettings{host=null, port=null, username=null, password=null}}, connectionPoolSettings=ConnectionPoolSettings{maxSize=100, minSize=0, maxWaitTimeMS=120000, maxConnectionLifeTimeMS=0, maxConnectionIdleTimeMS=0, maintenanceInitialDelayMS=0, maintenanceFrequencyMS=60000, connectionPoolListeners=[], maxConnecting=2}, serverSettings=ServerSettings{heartbeatFrequencyMS=10000, minHeartbeatFrequencyMS=500, serverListeners='[]', serverMonitorListeners='[]'}, sslSettings=SslSettings{enabled=false, invalidHostNameAllowed=false, context=null}, applicationName='null', compressorList=[], uuidRepresentation=JAVA_LEGACY, serverApi=null, autoEncryptionSettings=null, dnsClient=null, inetAddressResolver=null, contextProvider=null}
2024-12-12 02:00:02 INFO  org.mongodb.driver.cluster - Monitor thread successfully connected to server with description ServerDescription{address=localhost:27017, type=STANDALONE, state=CONNECTED, ok=true, minWireVersion=0, maxWireVersion=25, maxDocumentSize=16777216, logicalSessionTimeoutMinutes=30, roundTripTimeNanos=31616500}
2024-12-12 02:00:03 INFO  o.s.b.w.e.tomcat.TomcatWebServer - Tomcat started on port 8080 (http) with context path '/'
2024-12-12 02:00:03 INFO  c.s.r.SpringbootSanduskyRetailerCodingtestApplication - Started SpringbootSanduskyRetailerCodingtestApplication in 2.648 seconds (process running for 3.342)
```

## API Endpoints

### Add Reward Points to Customer

```
REQUEST TYPE: POST
URL: http://localhost:8080/customerRewards/addPoints
Accept: application/json
Content-Type: application/json
REQUEST JSON:

{
	"customerId" : "testCustomer1",
	"amountSpent" : 110,
}

RESPONSE: HTTP 201 (Created)
Message: Reward Points successfully added to Customer
Location header: http://localhost:8080/customerRewards/addPoints
```

### Retrieve Customer Rewards Points Total for last 3 months

```
REQUEST TYPE: GET
URL: http://localhost:8080/customerRewards/{customerId}/total

customerId is the Id of the customer you created in "addPoints" request.

Response: HTTP 200
Content: Rewards Total value for last 3 months 

```
### Retrieve Customer Rewards Points Total for the selected month

```
REQUEST TYPE: GET
URL: http://localhost:8080/customerRewards/{customerId}/monthly/{month}/{year}

customerId is the Id of the customer you created in "addPoints" request.
month is a value between 1-12 and year between 1900 and till current year

Response: HTTP 200
Content: Rewards Total value for the specified month. 

```
## Testing
	For testing the API to see the Integration Tests and Service tests:
	
	If you are using IDE like STS (Spring Tool Suite), right click on the Project in the Package Explorer and select "Run As" -> JUnit Test or to see the Coverage select "Coverage As" -> JUnit Test

## To view your MongoDB Database

```
Use Mongo Compass, freely available for download from https://www.mongodb.com/try/download/shell
For whichever platform you wish to download, please select the desired option and Download
After installing it, create a new Connection by using URI: mongodb://admin:admin@localhost:27017/

27017 is the default MongoDB port no and "admin" is the authentication database.

Once connection is created, you can browse the MongoDB collections in the "retailerDatabase".

```
## Technical Document
	The Document detailing this API's high level architecture and services with other details can be found under documentation folder in this project.