@EndToEnd
Feature: Test runs for finding the position of ISS
  Description: The purpose of these test to validate the API response we are getting from the ISS.

  Background: This API returns the list of satellites that this API has information about.
    Given user perform GET request to retrieve satellites information
    Then list of satellites are retrieved

  @SatelliteDetails
  Scenario Outline: The response received when retrieving the satellites details is 200
    Given user perform GET request to retrieve satellites information
    Then response code should be "<Response Code>"
    Examples:
      | Response Code |
      | 200           |

  @SatellitePositionResponse
  Scenario: This API returns position, velocity and other related information about satellite.
    Given user perform GET request to retrieve satellite position based on satellite id
    And the user get a valid response for the satellite position info
    Then the user is getting all the information about the satellite position, velocity and other details

  @SatellitePositionRespUnits @Valid
  Scenario Outline:Get position, velocity and other information and validate the units data based on the value provided.
    Given user perform GET request to retrieve satellite position based on satellite id
    And the user get a valid response for the satellite position info
    When the user provides the units parameter in "<Provided Units>"
    Then the user should get the response in the "<Expected Units>" based on the unit given as "<Provided Units>"

    Examples:
      | Provided Units | Expected Units |
      | kilometers     | kilometers     |
      | miles          | miles          |


  @SatellitePositionOnTimestamp @Valid
  Scenario Outline: This API returns position, velocity and other information based on the valid timestamps provided by user.
    Given user perform GET request to retrieve satellite position based on provided "<Time Stamps>"
    Then the user is getting the details based on the provided "<Time Stamps>"
    @Valid
    Examples:
      | Time Stamps |
      | 1857313740  |
      | 1415464140  |
      | 1005236940  |


##For this scenario, there is not response at all, in this case i will talk to the dev team and ask them to handle this kind of scenario.
#  @SatellitePositionOnTimestamp @Invalid
#  Scenario Outline: This API returns position, velocity and other related information about satellite.
#    Given user perform GET request to retrieve satellite position based on provided "<Time Stamps>"
#    Then the user is getting the details based on the provided invalid "<Time Stamps>"
#    Examples:
#      | Time Stamps   |
#      | 1857313740000 |

  @SatellitePositionOnTimestamp @Invalid
  Scenario Outline: This API returns and error message on providing the invalid timestamp.
    Given user perform GET request to retrieve satellite position based on provided invalid "<Time Stamps>"
    When the user is getting the details based on the provided invalid "<Time Stamps>"
    Then response code received should be "<Response Code>" for the timestamp "<Time Stamps>"
    Examples:
      | Time Stamps   | Response Code |
      | 185731374 ,   | 400           |
      | ***185731374  | 400           |


  @SatellitePositionOnMultipleTimestamp @Valid
  Scenario Outline: This API returns position, velocity and other details for multiple timestamps separated by comma.
    Given user perform GET request to retrieve satellite position based on provided "<Time Stamps>"
    When the user is getting the details based on the provided "<Time Stamps>"
    And the response for all the provided "<Time Stamps>" are received properly
    Then the user is getting the unit value in the "<User Provided Units>" for all the "<Time Stamps>"
    Examples:
      | Time Stamps                                                                                                          | User Provided Units |
      | 1857313740, 1415464140, 1005236940                                                                                   | kilometers          |
      | 1857313740, 1415464140, 1005236940, 1447180924, 1436518024, 1057826824, 2099292424                                   | miles               |
      | 1857313740, 1415464140, 1005236940, 1447180924, 1436518024, 1057826824, 2099292424, 2288594824, 426674824, 429407224 | miles               |


  @SatellitePositionRespTimestamp @Invalid
  Scenario Outline: This API returns position,velocity and other details for the current timestamp only.
    Given user perform GET request to retrieve satellite position based on satellite id
    And the user get a valid response for the satellite position info
    Then And the user is getting the details only for the current timestamp on providing other "<Time Stamps>"
    Examples:
      | Time Stamps |
      | 1415439116  |
      | 1825666316  |


  @SatellitePositionOnMultipleTimestamp @InValid
  Scenario Outline: This API returns the units in kilometers on providing the invalid units and it supports more than 10 timestamps as well.
    Given user perform GET request to retrieve satellite position based on provided "<Time Stamps>"
    When the user is getting the details based on the provided "<Time Stamps>"
    Then the user is getting the unit value as default on providing invalid "<User Provided Units>" for all the "<Time Stamps>"
    Examples:
      | Time Stamps                                                                                                          | User Provided Units |
      | 1857313740, 1415464140, 1005236940                                                                                   | killlometers        |
      | 1857313740, 1415464140, 1005236940, 1447180924, 1436518024, 1057826824, 2099292424                                   | milessss            |
      | 1857313740, 1415464140, 1005236940, 1447180924, 1436518024, 1057826824, 2099292424, 2288594824, 426674824, 429407224 | moles               |


  @SatellitePositionTLESData
  Scenario: This API returns the TLE data for a given satellite in either json or text format
    Given user perform GET request to retrieve satellite TLES data
    When the user is getting the TLES data in JASON format by default
    And the user is getting the TLES data in TEXT format by selecting the format as TEXT
    Then the TLES data is properly getting displayed in JSON format


  @SatellitePositionCoordinatesData  @Valid
  Scenario Outline: This API returns position, current time offset, country code, and timezone id for a given set of coordinates in the format of longitude,latitude in the format of longitude,latitude
    Given user perform GET request to retrieve satellite coordinate data with "<Lat Long Data>"
    And the user is getting the coordinates data in JASON format by default
    When the contents in the data is properly getting displayed as per the "<Lat Long Data>" provided
    And the "<Latitude>","<Longitude>","<Timezone Id>" and "<Country Code>" data is getting displayed based on "<Lat Long Data>"
    Then the user is getting the "<Response Code>" based on "<Lat Long Data>"

    Examples:
      | Lat Long Data         | Latitude  | Longitude   | Timezone Id         | Country Code | Response Code |
      | 37.795517,-122.393693 | 37.795517 | -122.393693 | America/Los_Angeles | US           | 200           |
      | 51.5072,0.1276        | 51.5072   | 0.1276      | Europe/London       | GB           | 200           |
      | 12.9716,77.5946       | 12.9716   | 77.5946     | Asia/Kolkata        | IN           | 200           |


  @SatellitePositionCoordinatesData @Invalid
  Scenario Outline: This API returns error response on providing the invalid data
    Given user perform GET request to retrieve satellite coordinate data with "<Lat Long Data>"
    Then the user is getting the "<Response Code>" based on "<Lat Long Data>"
    Examples:
      | Lat Long Data | Response Code |
      | 2132,32323    | 400           |
      | 434234,434    | 400           |


