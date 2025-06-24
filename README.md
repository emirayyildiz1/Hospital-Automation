# Hospital-Automation
# 🏥 Hospital Automation System

## Overview

This is a Java-based Hospital Automation System that allows users to interact as either **Admin** or **Patient**, with a range of functionalities designed to manage hospital operations efficiently. The system ensures thread-safe operations and includes unit testing via **JUnit**. All data is stored and read from files — no external database is used.

---

## Features

### 👩‍💼 Admin Functionality
- Create and manage **Doctors**
- Assign **available days** to doctors
- Add **Hospitals** and associate them with **Polyclinics**
- View and manage all appointments

### 🧑‍⚕️ Doctor Management
- Define specific available days for each doctor
- Prevents double-booking of a doctor using **Multithreading Synchronization**

### 🧑‍💻 Patient Functionality
- Register and log in as a patient
- **Book appointments** with available doctors
- **View existing appointments**
- Ensures no duplicate bookings for the same doctor and time

---

## Technologies Used
- 💻 Java
- 📂 File-based data storage (no database used)
- ✅ JUnit for unit testing
- 🔄 Multithreading for concurrency control

---

## Data Storage
All information — including users, doctors, hospitals, and appointments — is **stored in local files** using Java’s file I/O mechanisms. This enables persistence between sessions without requiring a database.

---

## Concurrency
The system uses Java's multithreading mechanisms to ensure that:
- No two patients can book the same doctor at the same date and time
- All booking operations are thread-safe and synchronized

---

## Testing
Unit tests are written using **JUnit** to verify:
- Doctor availability logic
- Appointment booking functionality
- System behavior under concurrent access

---

## Future Improvements
- GUI interface (JavaFX)
- Data encryption for file storage
- More advanced file structure or database integration
- Role-based access control
