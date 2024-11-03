# Deployment Guide

## Prerequisites

1. **Java Development Kit (JDK)**: Ensure JDK 23 is installed.
2. **Maven**: Ensure Maven is installed.
3. **MySQL**: Ensure MySQL is installed and running.

## Steps to Deploy

1. **Clone the Repository**:
    ```sh
    git clone <repository-url>
    cd physician-assistant-service
    ```

2. **Configure the Database**:
    - Create a MySQL database.
    - Update the `application.yml` file in `src/main/resources` with your database credentials:
        ```yaml
        spring:
          datasource:
            url: jdbc:mysql://localhost:3306/your_database_name
            username: your_username
            password: your_password
        ```

3. **Build the Project**:
    ```sh
    mvn clean install
    ```

4. **Run the Application**:
    ```sh
    mvn spring-boot:run
    ```

## Accessing the Application

### Endpoints

1. **Get All Appointments**:
    - **URL**: `http://localhost:8080/v1/appointments/all`
    - **Method**: GET
    - **Response**: List of all appointments

2. **Get Appointment by ID**:
    - **URL**: `http://localhost:8080/v1/appointments/{id}`
    - **Method**: GET
    - **Response**: Appointment details for the given ID

3. **Get Appointments by Physician ID**:
    - **URL**: `http://localhost:8080/v1/appointments/physician/{physicianId}`
    - **Method**: GET
    - **Response**: List of appointments for the given physician ID

4. **Create a New Appointment**:
    - **URL**: `http://localhost:8080/v1/appointments`
    - **Method**: POST
    - **Request Body**:
        ```json
        {
            "appointmentId": 1,
            "patientId": 201,
            "physicianId": 1,
            "appointmentTime": "2023-10-01T10:00:00",
            "status": "BOOKED",
            "rescheduledTime": null,
            "bookingTime": "2023-09-25T09:00:00",
            "appointmentEndTime": "2023-10-01T10:30:00"
        }
        ```
    - **Response**: Status of the creation operation

5. **Delete Appointment by ID**:
    - **URL**: `http://localhost:8080/v1/appointments/{id}`
    - **Method**: DELETE
    - **Response**: Status of the deletion operation

## Additional Information

- **Testing**: Run tests using:
    ```sh
    mvn test
    ```

For further reference, please check the `HELP.md` file in the project root.