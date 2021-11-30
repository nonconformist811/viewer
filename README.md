# viewer
Zendesk Ticket Viewer 

# Zendesk Sample Tickets Viewer with Spring Boot and Thymeleaf

## Steps to Setup

**1. Clone the application**

```bash
https://github.com/nonconformist811/viewer.git
```

**2. Build and run the app using maven**

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Alternatively, you can run the app using -

```bash
Go to project hierarchy
Run ViewerApplication.java
```

The app will start running at <http://localhost:8090/ticketViewer>.

## Explore Rest APIs

The app defines following  APIs.

    GET /ticketViewer
    
    GET /ticketViewer/tickets/{ticketId}
    
## Rationale behind calling the Zendesk api for each page:

**1. Page will take less time to get loaded since only 25 tickets are fetched at a time**

**2. There are few use cases only where user wants to load all the tickets**

**3. If time permits, I would have used Redis for storing tickets in-memory**



    
    
