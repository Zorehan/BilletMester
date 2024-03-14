# Ticket Master System

This project aims to develop an Event Management System that facilitates the creation, management, and ticketing of various events. The system provides functionalities for different types of users including Admins, Event Coordinators, and Customers. The system is developed in Java as a JavaFX desktop application using the IntelliJ IDE and utilizes MSSQL Server for database management.

# Functional Requirements
## Users
### Admin:
- Can create and manage all other users.
- Can delete events and assign coordinators.
- Cannot create and manage events.

### Event Coordinator:
- Can create, delete, and manage events.
- Can add tickets to events pertaining to that coordinator (set by the admin).
- Can assign other coordinators to have access.

### Customer:
- End customer who purchases tickets.
- Receives printed tickets on paper.
- Does not have access to the system.

## Events
Events consist of required and optional information including Time, Location, and Notes.
Optional information may include End date and time, Location guidance, etc.

## Printed Tickets and Admission
- Event coordinators can print tickets for customers.
- Each ticket is connected to the name and email of the customer who bought it.
- Customers buy tickets directly from event coordinators.
- Various types of tickets can be created (e.g., VIP tickets, Food included tickets) and customized for each event.
- Tickets contain a unique QR code and corresponding barcode resolving to a unique system-generated ID.

## Special Free or Discounted Food/Drinks Tickets
- Event coordinators can print separate free tickets for an event.
- These tickets contain their own valid QR/barcode for one-time use.
- These tickets are not connected to any specific customers but can be event-specific.

## Technical Requirements
- Implemented in Java as a JavaFX desktop application using IntelliJ IDE.
- Persistent data management using MSSQL Server.
- Proper documentation for each sprint including sprint-planning activities, product design issues, and important decisions.
- Utilization of design patterns in design and implementation, documented accordingly.
- At least one core class must be tested through automated JUnit testing.

## Getting Started
- Clone this repository.
- Set up VPN connected to school database.
- Open the project in IntelliJ IDE.
- Run the application and follow the user interface for functionalities.


## Authors
- [@VictorBjerrumvb](https://github.com/VictorBjerrumvb)
- [@Zorehan](https://github.com/Zorehan)
- [@Nicklas Kramer](https://github.com/NillasKA)
- [@RuneKrogh](https://github.com/RuneKrogh)

## Roadmap

- [Scrumwise Link](https://www.scrumwise.com/scrum/#/overview/project/billetmester_cs2023-dk/id-36893-38727-1)
![image](https://github.com/Zorehan/BilletMester/assets/143797601/7256fc66-76a4-4cf5-8069-e32caa51ea44)
