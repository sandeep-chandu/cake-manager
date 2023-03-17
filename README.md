## Cake Manager Micro Service

### Stack used:
   *  Java 17
   *  Springboot 2.7.9
   *  Junit5 and Mockito frameworks for unit and integration testing
   *  Maven build tool
   *  H2 database
   *  Open API Swagger for API specification
   *  Auth0 platform for Authentication and Authorisation. [Auth0](https://auth0.com/)
   *  GitHub actions for Continous Itegration
   *  DockerHub container registry
   *  Docker for containerisation
   
## How to run and test?

### Using docker image from DockerHub
Make sure you have docker installed on the machine.
  1.  Open cmd or temrnial and run the following command to pull the image from DockerHub
  
    docker pull sandeepchandu/cake-manager-1.0
  
  2.  Run the image using docker run command below, wait for the application to get deployed.
  
    docker run -p9999:8080 sandeepchandu/cake-manager-1.0
    
  3.  Access swagger at http://localhost:9999/swagger-ui/index.html
  
  4.  To access API end points you need to fetch the token, invoke the below Curl command to get the same
      
        **Read only token**: can perform GET operations only
        ```
          curl --request POST --url https://dev-l0bv5x78cpcytc8k.us.auth0.com/oauth/token  --header 'content-type: application/json' --data '{"client_id":"tddpCn7tmsoXwutJOkfDvw6ZT3EeHrU5","client_secret":"iZceSFR9EX5-uG2QEZSudSdOq6L6Tnhi8J4K4cLdQBf65KWgnFJQfOXkVYN6Gdmg","audience":"https://cake-manager/","grant_type":"client_credentials"}'
        ```
        
        **Read & Write token**: can invoke GET, POST, PATCH, PUT and DELETE operations on /cake endpoint
        ```
          curl --request POST --url https://dev-l0bv5x78cpcytc8k.us.auth0.com/oauth/token --header 'content-type: application/json' --data '{"client_id":"aejHscwlzZN4DOL8rwCtoeIh1XeMrkHH","client_secret":"8F6Jz3w5elWKAdUFyLBSUJ5jGqr3T-J4W0ORzwm2xG1CCssnc0zUlmSbfPsyGw2Q","audience":"https://cake-manager/","grant_type":"client_credentials"}'
        ```
        Copy the value of access_token from the response.
  5. Once the you have access token store/update it in the Authorize section of swagger and invoke the APIs to test.
  
### Checkout code, build and test using maven
  1.  Clone this repo (master) branch
  
  2.  Navigate to /cake-manager folder in the terminal and run the below command
  
      ```
        mvn spring-boot:run
      ```
     
  3.  Access swagger at http://localhost:8080/swagger-ui/index.html
  
  4.  Inorder to access API you need Bearer token, to get same refer to step 4 in previous method (Using docker image from DockerHub).
  
  5. Once the you have access token store/update it in the Authorize section of swagger and invoke the APIs to test.


### Checkout code, build build docker image and run the image
  You can build a docker image and run it as well.
  
Thanks!
