#!/usr/bin/env bash
#
# Sample usage:
#   ./test_all.bash start stop
#   start and stop are optional
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
# When not in Docker
#: ${HOST=localhost}
#: ${PORT=7000}

# When in Docker
# shellcheck disable=SC2223
: ${HOST=localhost}
# shellcheck disable=SC2223
: ${PORT=8080}

#array to hold all our test data ids
allTestOrdersIds=()
allTestRestaurantsIds=()
allTestClientsIds=()
allTestDeliveryDriversIds=()

function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  # shellcheck disable=SC2155
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

#have all the microservices come up yet?
function testUrl() {
    # shellcheck disable=SC2124
    url=$@
    if curl $url -ks -f -o /dev/null
    then
          echo "Ok"
          return 0
    else
          echo -n "not yet"
          return 1
    fi;
}

#prepare the test data that will be passed in the curl commands for posts and puts
function setupTestdata() {

#all use galleryId ea85d3ba-d708-4ff3-bbbb-dd9c5c77b8e8


body=\
'{"restaurantId":"055b4a20-d29d-46ce-bb46-2c15b8ed6526",
 "menuId":"8fb3d5f0-2ceb-4921-a978-736bb4d278b7",
 "totalPrice":50.0,
 "deliveryDriverId":"44907b55-727b-497e-936e-b319439c5752",
 "orderStatus":"MAKING_ORDER",
 "items": [
 	{
 		"itemName": "poutine",
 		"itemDesc": "1 mustard hotdog",
 		"itemCost": 4.99
 	}
 ],

 "estimatedDeliveryTime": "25 minutes",
 "orderDate":"2023-04-20"
 }}'
recreateOrderAggregate 1 "$body" "334b0040-af1c-4857-981c-acda5a362e9d"

#CREATE SOME GALLERY TEST DATA - THIS WILL BE USED FOR THE POST REQUEST
 #resto request model
 body1=\
'{
      "restaurantName" :"resto123",
      "countryName" :"Canada",
    "streetName":"street123",
     "cityName" : "city123",
     "provinceName": "province123",
      "postalCode": "12345"
 }'
 recreateRestaurantAggregate 1 "$body1"

#CREATE Client TEST DATA - THIS WILL BE USED FOR THE POST REQUEST
body2=\
'{

"userName": "userTest1",
"password": "password1",
"age": "30",
"emailAddress": "a@a.com",
"phoneNumber": "123456789",
"countryName": "Canada",
"streetName": "street123",
"cityName": "city123",
"provinceName": "province123",
"postalCode": "12345"
}'

recreateClient 1 "$body2"

#CREATE DeliveryDriver TEST DATA - THIS WILL BE USED FOR THE POST REQUEST

body3=\
'{

"firstName": "Michael",
"lastName": "Vacca",
"dateOfBirth": "2022-04-20",
"description": "Michael is a  deliveryDriver",
"employeeSince": "2022-04-20",
"countryName": "Canada",
"streetName": "street123",
"cityName": "city123",
"provinceName": "province123",
"postalCode": "12345"

}'
recreateDeliveryDriver 1 "$body3"

}

#USING Order TEST DATA - EXECUTE POST REQUEST
function recreateOrderAggregate() {
    local testId=$1
    local aggregate=$2
    #local galleryId=$3
    local clientId=$3

    #create the clietns aggregates and record the generated cliendId
    orderId=$(curl -X POST http://$HOST:$PORT/api/v1/clients/$clientId/orders -H "Content-Type:
    application/json" --data "$aggregate" | jq '.orderId')
    echo "Response: $orderId"
    allTestOrdersIds[$testId]=$orderId
    echo "Added Order Aggregate with OrderId: ${allTestOrdersIds[$testId]}"
}

function recreateRestaurantAggregate(){
  local testId=$1
  local aggregate=$2


  restaurantId=$(curl -X POST http://$HOST:$PORT/api/v1/restaurants -H "Content-Type:
  application/json" --data "$aggregate" | jq '.restaurantId')
  echo "Response: $restaurantId"
  allTestRestaurantsIds[$testId]=$restaurantId
  echo "Added Restaurant with restaurantId: ${allTestRestaurantsIds[$testId]}"
}

function recreateDeliveryDriver() {
  local testId=$1
  local aggregate=$2

  deliveryDriverId=$(curl -X POST http://$HOST:$PORT/api/v1/deliveryDrivers -H "Content-Type: application/json" --data "$aggregate" | jq '.deliveryDriverId')
  echo "Response: $deliveryDriverId"
  allTestDeliveryDriversIds[$testId]=$deliveryDriverId
  echo "Added delivery driver with deliveryDriverId: ${allTestDeliveryDriversIds[$testId]}"
}


function recreateClient() {
    local testId=$1
    local aggregate=$2

    #create the clients aggregates and record the generated clientIds
    clientId=$(curl -X POST http://$HOST:$PORT/api/v1/clients -H "Content-Type:
    application/json" --data "$aggregate" | jq '.clientId')
    echo "Response: $clientId"
    allTestClientsIds[$testId]=$clientId
    echo "Added Client with clientId: ${allTestClientsIds[$testId]}"
}

#don't start testing until all the microservices are up and running
function waitForService() {
    # shellcheck disable=SC2124
    url=$@
    echo -n "Wait for: $url... "
    n=0
    until testUrl $url
    do
        n=$((n + 1))
        if [[ $n == 100 ]]
        then
            echo " Give up"
            exit 1
        else
            sleep 6
            echo -n ", retry #$n "
        fi
    done
}

#start of test script
set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

# shellcheck disable=SC2199
if [[ $@ == *"start"* ]]
then
    echo "Restarting the test environment..."
    echo "$ docker-compose down"
    docker-compose down
    echo "$ docker-compose up -d"
    docker-compose up -d
fi

#try to delete an entity/aggregate that you've set up but that you don't need. This will confirm that things are working
#I've set up an inventory with no menus in it
waitForService "curl -X DELETE http://$HOST:$PORT/api/v1/clients/df0b37ca-0f93-4830-8fd0-07239a7c3d46"

setupTestdata

#EXECUTE EXPLICIT TESTS AND VALIDATE RESPONSE
## Verify that a normal get by id of earlier posted order works

echo -e "\nTest 1: Verify that a normal get by id of earlier posted order works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/clients/334b0040-af1c-4857-981c-acda5a362e9d/orders/${allTestOrdersIds[1]} -s"
assertEqual ${allTestOrdersIds[1]} $(echo $RESPONSE | jq .orderId)
assertEqual "\"nbaudoux2\"" $(echo $RESPONSE | jq ".clientUsername")

#
#

#Verify clients get all
echo -e "\Test 2: Verify that a get all clients works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/clients -s"
assertEqual 6 $(echo $RESPONSE | jq '. | length')

##Verify that a normal post of client works
echo -e "\nTest 3: Verify that a normal post of client works"
#assertCurl 201 "curl -X POST http://$HOST:$PORT/api/v1/clients -H \"Content-Type: application/json\" --data '${body2}' -s"
assertCurl 201 "curl -X POST http://$HOST:$PORT/api/v1/clients -H \"Content-Type: application/json\" --data '{ \"userName\": \"userTest123\", \"password\": \"password1\", \"age\": \"30\", \"emailAddress\": \"a@a.com\", \"phoneNumber\": \"123456789\", \"countryName\": \"Canada\", \"streetName\": \"street123\", \"cityName\": \"city123\", \"provinceName\": \"province123\", \"postalCode\": \"12345\" }' -s"

assertEqual "\"userTest123\"" $(echo $RESPONSE | jq .userName)
assertEqual "\"password1\"" $(echo $RESPONSE | jq .password)
assertEqual "\"30\"" $(echo $RESPONSE | jq .age)
assertEqual "\"a@a.com\"" $(echo $RESPONSE | jq .emailAddress)
assertEqual "\"123456789\"" $(echo $RESPONSE | jq .phoneNumber)
assertEqual "\"Canada\"" $(echo $RESPONSE | jq .countryName)
assertEqual "\"street123\"" $(echo $RESPONSE | jq .streetName)
assertEqual "\"city123\"" $(echo $RESPONSE | jq .cityName)
assertEqual "\"province123\"" $(echo $RESPONSE | jq .provinceName)
assertEqual "\"12345\"" $(echo $RESPONSE | jq .postalCode)

#Post for restaurants
echo -e "\nTest 4: Verify that a normal post of restaurants works"
assertCurl 201 "curl -X POST http://$HOST:$PORT/api/v1/restaurants -H \"Content-Type: application/json\" --data '{ \"restaurantName\": \"resto123\", \"countryName\": \"Canada\", \"streetName\": \"street123\", \"cityName\": \"city123\", \"provinceName\": \"province123\", \"postalCode\": \"12345\" }' -s"

assertEqual "\"resto123\"" $(echo $RESPONSE | jq .restaurantName)
assertEqual "\"Canada\"" $(echo $RESPONSE | jq .countryName)
assertEqual "\"street123\"" $(echo $RESPONSE | jq .streetName)
assertEqual "\"city123\"" $(echo $RESPONSE | jq .cityName)
assertEqual "\"province123\"" $(echo $RESPONSE | jq .provinceName)
assertEqual "\"12345\"" $(echo $RESPONSE | jq .postalCode)

#Post for deliveryDrivers
echo -e "\nTest 5: Verify that a normal post of delivery drivers works"
assertCurl 201 "curl -X POST http://$HOST:$PORT/api/v1/deliveryDrivers -H \"Content-Type: application/json\" --data '{ \"firstName\": \"Michael\", \"lastName\": \"Vacca\", \"dateOfBirth\": \"2022-04-20\", \"description\": \"Driver\", \"employeeSince\": \"2022-04-20\", \"countryName\": \"Canada\", \"streetName\": \"street123\", \"cityName\": \"city123\", \"provinceName\": \"province123\", \"postalCode\": \"12345\" }' -s"

assertEqual "\"Michael\"" $(echo $RESPONSE | jq .firstName)
assertEqual "\"Vacca\"" $(echo $RESPONSE | jq .lastName)
assertEqual "\"2022-04-20\"" $(echo $RESPONSE | jq .dateOfBirth)
assertEqual "\"Driver\"" $(echo $RESPONSE | jq .description)
assertEqual "\"2022-04-20\"" $(echo $RESPONSE | jq .employeeSince)
assertEqual "\"Canada\"" $(echo $RESPONSE | jq .countryName)
assertEqual "\"street123\"" $(echo $RESPONSE | jq .streetName)
assertEqual "\"city123\"" $(echo $RESPONSE | jq .cityName)
assertEqual "\"province123\"" $(echo $RESPONSE | jq .provinceName)
assertEqual "\"12345\"" $(echo $RESPONSE | jq .postalCode)

clientId="334b0040-af1c-4857-981c-acda5a362e9d"

echo -e "\nTest 6: Verify the creation of a client order"
# Curl command to create a new order
assertCurl 201 "curl -X POST http://$HOST:$PORT/api/v1/clients/334b0040-af1c-4857-981c-acda5a362e9d/orders -H \"Content-Type: application/json\" --data '{
  \"restaurantId\": \"055b4a20-d29d-46ce-bb46-2c15b8ed6526\",
  \"menuId\": \"8fb3d5f0-2ceb-4921-a978-736bb4d278b7\",
  \"clientId\": \"334b0040-af1c-4857-981c-acda5a362e9d\",
  \"totalPrice\": 50.0,
  \"deliveryDriverId\": \"44907b55-727b-497e-936e-b319439c5752\",
  \"orderStatus\": \"MAKING_ORDER\",
  \"items\": [
    {
      \"itemName\": \"poutine\",
      \"itemDesc\": \"1 mustard hotdog\",
      \"itemCost\": 4.99
    }
  ],
  \"estimatedDeliveryTime\": \"25 minutes\",
  \"orderDate\": \"2023-04-20\"
}' -s"
#assertEqual ${allTestOrdersIds[2]} $(echo $RESPONSE | jq .orderId)
assertEqual "\"334b0040-af1c-4857-981c-acda5a362e9d\"" $(echo $RESPONSE | jq .clientId)
assertEqual "\"055b4a20-d29d-46ce-bb46-2c15b8ed6526\"" $(echo $RESPONSE | jq .restaurantId)
assertEqual "\"8fb3d5f0-2ceb-4921-a978-736bb4d278b7\"" $(echo $RESPONSE | jq .menuId)
assertEqual "\"44907b55-727b-497e-936e-b319439c5752\"" $(echo $RESPONSE | jq .deliveryDriverId)
assertEqual "\"Korey\"" $(echo $RESPONSE | jq .driverFirstName)
assertEqual "\"Burgise\"" $(echo $RESPONSE | jq .driverLastName)
assertEqual "\"nbaudoux2\"" $(echo $RESPONSE | jq .clientUsername)
assertEqual "\"cmcnutt2@ucoz.com\"" $(echo $RESPONSE | jq .clientEmail)
assertEqual "\"poutine\"" $(echo $RESPONSE | jq '.items[0].itemName')
assertEqual "\"1 mustard hotdog\"" $(echo $RESPONSE | jq '.items[0].itemDesc')
assertEqual "4.99" $(echo $RESPONSE | jq '.items[0].itemCost')
assertEqual "\"Savory Street\"" $(echo $RESPONSE | jq .restaurantName)
assertEqual "\"Appetizers\"" $(echo $RESPONSE | jq .typeOfMenu)
assertEqual "\"MAKING_ORDER\"" $(echo $RESPONSE | jq .orderStatus)
assertEqual "50.0" $(echo $RESPONSE | jq .finalPrice)
assertEqual "\"25 minutes\"" $(echo $RESPONSE | jq .estimatedDeliveryTime)
assertEqual "\"2023-04-20\"" $(echo $RESPONSE | jq .orderDate)











#Post for orders

orderData='{
  "restaurantId": "055b4a20-d29d-46ce-bb46-2c15b8ed6526",
  "menuId": "8fb3d5f0-2ceb-4921-a978-736bb4d278b7",
  "clientId": "334b0040-af1c-4857-981c-acda5a362e9d",
  "totalPrice": 50.0,
  "deliveryDriverId": "44907b55-727b-497e-936e-b319439c5752",
  "orderStatus": "MAKING_ORDER",
  "items": [
    {
      "itemName": "poutine",
      "itemDesc": "1 mustard hotdog",
      "itemCost": 4.99
    }
  ],
  "estimatedDeliveryTime": "25 minutes",
  "orderDate": "2023-04-20"
}'

echo -e "\nTest 77: Verify the creation of a client order"
assertCurl 201 "curl -X POST http://$HOST:$PORT/api/v1/clients/34b0040-af1c-4857-981c-acda5a362e9d/orders -H \"Content-Type: application/json\" --data '${orderData}' -s"

# Additional assertion for the response
assertEquals "true" $(echo $RESPONSE | jq .success)

# Rest of the assertions for specific fields in the response, modify as needed
assertEqual ${allTestOrdersIds[2]} $(echo $RESPONSE | jq .orderId)
assertEqual "\"055b4a20-d29d-46ce-bb46-2c15b8ed6526\"" $(echo $RESPONSE | jq .restaurantId)
assertEqual "\"8fb3d5f0-2ceb-4921-a978-736bb4d278b7\"" $(echo $RESPONSE | jq .menuId)
assertEqual "\"334b0040-af1c-4857-981c-acda5a362e9d\"" $(echo $RESPONSE | jq .clientId)
assertEqual "50.0" $(echo $RESPONSE | jq .totalPrice)
assertEqual "\"44907b55-727b-497e-936e-b319439c5752\"" $(echo $RESPONSE | jq .deliveryDriverId)
assertEqual "\"MAKING_ORDER\"" $(echo $RESPONSE | jq .orderStatus)
assertEqual "\"poutine\"" $(echo $RESPONSE | jq '.items[0].itemName')
assertEqual "\"1 mustard hotdog\"" $(echo $RESPONSE | jq '.items[0].itemDesc')
assertEqual "4.99" $(echo $RESPONSE | jq '.items[0].itemCost')
assertEqual "\"25 minutes\"" $(echo $RESPONSE | jq .estimatedDeliveryTime)
assertEqual "\"2023-04-20\"" $(echo $RESPONSE | jq .orderDate)

# shellcheck disable=SC2199
if [[ $@ == *"stop"* ]]
then
    echo "We are done, stopping the test environment..."
    echo "$ docker-compose down"
    docker-compose down
fi