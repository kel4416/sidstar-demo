# sidstar-demo
This is a web application that is used to retrieve a list of airports from ATMS and also find waypoints that has the highest and second highest number of association with the SIDs/STARs of a particular aiport.

It is built on JAVA with MAVEN, containerised in Docker and then hosted on AWS Elastic Container Service.

## Architecture



## Software and Account Requirements

1) IntelliJ IDEA 2022.1.2 (Community Edition)
2) AWS Account
3) GitHub account

## Setup Guide

### Download and install Intellij

### Pull from repository

### Set up AWS and the necessary Services


## Building the solution

### Building the backend
The backend is built using JAVA with Spring Boot framework. It exposes two functions:

1) getAiports
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `apiKey` | `string` | **Required**. The API key to ATMS |

2) getTopTwoAssoWaypoints
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `apiKey` | `string` | **Required**. The API key to ATMS |
| `icao` | `string` | **Required**. The airport's icao for this query|
| `stdIntrutmentType` | `string` | **Required**. The choice of Standard Instrument for this query. Values accepted ["sids,"stars"] |


