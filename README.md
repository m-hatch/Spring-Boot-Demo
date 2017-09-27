# Spring Boot REST API
                                    
### Compile
`mvn clean package`
                                    
                                    
### Run project
`mvn clean install`
`mvn spring-boot:run`


### Endpoints
The API exposes the following resources:

* `GET /key_refresh` - A client API key is needed to call the resource endpoints. These keys are loaded into the API on initialization. This enpoint allows for a manual reload of the keys - should a new key be added after intialization.  

* `GET /lannisters` - Fetches all members of House Lannister.  

* `GET /starks` - Fetches all members of House Stark.  

* `GET /tournaments` - Fetches all Tournament info.  

* `GET /{tourneyId}/latest` - Fetches the latest winners for a particular Tournament.