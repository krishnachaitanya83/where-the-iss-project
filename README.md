# where-the-iss-project
# Getting Started Requirements
1. Pre-Installation
* Setup the most recent version of the intellij
*Install Gradle plugin if it isn't already setup in intellij
*Setup the most recent version of JAVA
  # Below are the tools/frameworks used 
    'org.junit.jupiter:junit-jupiter-api:5.7.0'
    'net.serenity-bdd:serenity-core:3.4.2'
    'net.serenity-bdd:serenity-rest-assured:3.4.2'
    'net.serenity-bdd:serenity-cucumber:3.4.2'
    'junit:junit:4.13.2'
    'org.junit.jupiter:junit-jupiter-engine:5.7.0'


2. Clone the code
##### GIT Repo Path (need to be updated) #######
It should now clone using SSH key only (no credentials required)



3. Build and test
This project is driven by Gradle
Open commandline on your intellij and type the command:
-gradlew clean clearReports test aggregate


## Sample execution and output:

 @SatellitePositionOnTimestamp @Valid
  Scenario Outline: This API returns position, velocity and other related information about satellite.
    Given user perform GET request to retrieve satellite position based on provided "<Time Stamps>"
    Then the user is getting the details based on the provided "<Time Stamps>"
    @Valid
    Examples:
      | Time Stamps |
      | 1857313740  |
      | 1415464140  |
      | 1005236940  |
  
  #Reports will be generated here.
  Please click on the link and open it in any browser.
 > Task :aggregate
Generating Serenity Reports
  - Main report: file:///C:/Users/krcha/IdeaProjects/where-the-iss/target/site/serenity/index.html



