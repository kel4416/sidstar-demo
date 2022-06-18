# sidstar-demo
This is a web application that is used to retrieve a list of airports from ATMS and also find waypoints that has the highest and second highest number of association with the SIDs/STARs of a particular aiport.

It is built on JAVA with MAVEN, containerised in Docker and then hosted on AWS Elastic Container Service.

## Architecture



## Software and Account Requirements

1) IntelliJ IDEA 2022.1.2 (Community Edition)
2) AWS Account
3) GitHub account
4) ATMS API (https://open-atms.airlab.aero/public-api/)

## Setup Guide

### Download and install Intellij Community Edition
You can download and install Intellij Community Edition from here

https://www.jetbrains.com/idea/download/#section=windows

### Pull from repository
All the resources and dependencies are included in this project, so all you have to do is ask Intellij to clone this project.

Instructions can be found here:
https://blog.jetbrains.com/idea/2020/10/clone-a-project-from-github/

### Set up Github Account
Set up a github account and repository to host this project and enable the CI/CD pipeline.

1) Sign up for github account at https://github.com/join
2) After you log in, on the left plane, click "New"
3) Key in your desired repository name, description, private or public setting and click "Create Repository".

### Push project to your repository
1) In intellij, under the VCS tab, click 
2) Push this project to your repository

![This is an image]{assets/sidstardemo.jpg}



### Set up AWS and the necessary Services
To host this application on AWS, you need to create
1) Elastic Repository
   
2) Elastic Container service
   1) Cluster
   2) Tasking Definition
   3) Service

## Building the solution

### Framewokrs and API used


### Building the backend
The backend is built using JAVA with Spring Boot framework. It exposes two functions:

```http
POST /api/getAirports
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `apiKey` | `string` | **Required**. The API key to ATMS |

```http
POST /api/getTopTwoAssoWaypoints
```

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `apiKey` | `string` | **Required**. The API key to ATMS |
| `icao` | `string` | **Required**. The airport's icao for this query|
| `stdIntrutmentType` | `string` | **Required**. The choice of Standard Instrument for this query. Values accepted ["sids,"stars"] |

### Backend Functions in detail

**/getaiport**
This retrieves the list of airports from ATMS API and return the data in the following format:

```javascript
{
  "data" : [
    { 
      "uid":string,
      "iata":string,
      "name":string,
      "lat":double,
      "lng":double,
      "alt":double
    },
  ]
}
```

### Front end


