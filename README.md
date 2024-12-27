# Avancerad-java-Anton-Stansg-rd-slutprojekt


## Projektets mål

Mitt projekt är ett enkelt Todo-applikation som är byggd med **JavaFX** som frontend och en **Spring Boot**-baserad backend. Applikationen erbjuder följande funktionalitet:

- Visa en lista med todos i en tabell.
- Lägg till, hämta, redigera och ta bort todos.
- Synkronisera data mellan frontend och backend via REST API.

## Funktionalitet

- **TableView för Todo-listor:** Visar todo-uppgifter med ID, titel och beskrivning.
- **Rest-API operationer:**
  - **POST:** Lägg till nya todo-uppgifter.
  - **GET:** Visa existerande todo-uppgifter.
  - **PUT:** Uppdatera detaljer för befintliga todos.
  - **DELETE:** Ta bort en todo.
- **Backend med REST API:** Hanterar datalagring.

---

## Instruktioner för att starta applikationen

### Förutsättningar

- **Java 17** eller senare.
- **Maven** eller annan bygghanterare.
- **SceneBuilder** (valfritt för att redigera UI).
- **Spring Boot** installerat.

### Backend (Spring Boot-server)

1. Navigera till backend-projektmappen:
   ```bash
   cd backend

2. Bygg och starta servern:
   ```bash
   mvn spring-boot:run

3. Backend-servern startar på http://localhost:8081.

## Frontend (JavaFX-applikation)

1. Navigera till frontend-projektmappen:
   ```bash
   cd frontend

2. Bygg och starta frontend-applikationen:
   ```bash
   mvn javafx:run

3. Applikationen startar och visar GUI:t där du kan interagera med dina todos.

---

## Exempel på API-anrop

1. Hämta alla todos

   förfrågan:

```http
    GET /todos
Host: localhost:8081
Content-Type: application/json
```

Svar:

```json
[
  {
    "id": 1,
    "title": "Dricka kaffe",
    "description": "Ska lära mig mycket"
  },
  {
    "id": 2,
    "title": "Koda JavaFX",
    "description": "Implementera TableView och CRUD"
  }
]
```

2. Lägg till en ny todo

   förfrågan:

  ```http
    POST /todos
Host: localhost:8081
Content-Type: application/json
```

   Svar:

   ```json
{
  "id": 3,
  "title": "Ny Todo",
  "description": "Det här är en beskrivning"
}
```

3. Uppdatera en befintlig todo

   förfrågan:
   ```http
     PUT /todos/id
   Host: localhost:8081
   Content-Type: application/json
   ```

   Svar:

   ```json
   {
     "id": 1,
     "title": "Uppdaterad Todo",
     "description": "Ny beskrivning"
   }
   ```

   4.Radera en befintlig todo

      förfrågan:
      ```http
        DELETE /todos/id
      Host: localhost:8081
      Content-Type: application/json
      ```

      Svar: Tar bort den befintliga todon 
   
