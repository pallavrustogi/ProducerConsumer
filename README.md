---
Getting Started with Kitchen
---
**Automatic
*Execute the shell script: ./kitchen-processor/start.sh

**Manual
* To compile and install prerequistes are Maven 3 and Java 8
* Navigate to kitchen-processor folder under
        cd kitchen-processor
* Install:
        mvn clean install
* Run:
        mvn spring-boot:run

* To change the input file, place the input file in resources folder
        cd ./kitchen-processor/src/main/resources
    And change the file name in application.properties file localed at
        cd ./kitchen-processor/src/main/resources

--
Design
--

Data is Read from input.json file in OrderRepository.java
Order is Received by kitchen in scheduled fashion @ on order per quarter second
Time to make order is Instant (0 sec) but this can be configured by changing value of
        kitchen.order.prepare.time: 0
Order is placed by kitchen on one of the four Shelves. Also refresh logic to calculate value is present in ShelfService.java
Shelves sizes are configurable in application.properties file:
        shelf.hot.size: 15
        shelf.cold.size: 15
        shelf.frozen.size: 15
        shelf.overflow.size: 20
Drivers are dispatched using Poisson distribution with average 3 per sec. Implementation in DriverService.java



