# HomeHaven 🏠

HomeHaven is a **household asset management application prototype** designed to help homeowners organize, monitor, and maintain important household items in one place. The project focuses on making it easy to track assets, warranties, maintenance tasks, and reminders through a clean and intuitive interface.

---

## ✨ Key Features & Functionality

The HomeHaven prototype includes the main features needed to organize and track household assets:

* **Dashboard with quick stats** for total assets, upcoming tasks, and alerts
* **Maintenance calendar** to visualize scheduled upkeep and inspections
* **Dedicated Assets screen** for viewing household items and their details
* **Reminders screen** for managing maintenance, inspection, and warranty alerts
* **Warranty tracking** with expiration date visibility
* **Status indicators** for overdue and upcoming tasks
* **Light and dark mode support** for improved usability and accessibility
* **CSV export functionality** for downloading asset records
* **Simple multi-screen navigation** for moving between dashboard, assets, and reminders

Overall, the prototype demonstrates the essential tools a homeowner needs to manage possessions and stay on top of important maintenance responsibilities.

---

## 🧪 Usability Testing

We performed usability testing on the HomeHaven prototype to ensure the interface was easy to navigate and the core features behaved as expected.

### Testing Workflow

We clicked through all major screens, including:

* Dashboard
* Asset list
* Reminders
* Maintenance calendar

We also tested common user actions such as:

* adding new assets
* creating reminders
* reviewing warranty information
* switching between light and dark mode
* navigating between screens

### Improvements Made

During testing, we identified and corrected several usability issues, including:

* spacing inconsistencies
* unclear layout flow
* UI elements that felt visually out of place
* navigation polish improvements

These updates made the prototype:

* cleaner
* easier to use
* more visually consistent
* more intuitive for first-time users

---

## 🚀 Project Goal

The long-term goal of HomeHaven is to evolve from a prototype into a full **Java + JavaFX desktop application** with:

* persistent asset storage
* reminder scheduling
* dashboard analytics
* CSV reporting
* warranty and maintenance KPI tracking

## 🧩 Architecture

HomeHaven follows the **Model-View-Controller (MVC)** design pattern.

### Model
Handles application data and business logic.
- Asset.java
- Reminder.java
- DataStore.java

### View
Contains all JavaFX FXML interface files.
- Main.fxml
- dashboard.fxml
- assets.fxml
- reminders.fxml

### Controller
Handles screen behavior and user interactions.
- MainController.java
- DashboardController.java
- AssetsController.java
- RemindersController.java

---

## 👥 Contributors

- Ryan Borrego
- Josue Alan Canales
- Lawrencia Tiwaa-Adjei
- Ahaan Vohra

---

## ⚙️ Technologies Used

- Java
- JavaFX
- FXML
- Maven
- GitHub

---

## ▶️ Running the Application

### Requirements
- Java 17 or newer
- Maven installed
- JavaFX configured in IntelliJ IDEA or Eclipse

### Steps
1. Clone the repository
2. Open the project in IntelliJ IDEA or Eclipse
3. Allow Maven dependencies to load
4. Run:
```text
src/main/java/edu/utsa/cs3443/HomeHaven/Main.java
```

The HomeHaven application window should launch automatically.

---

## 📁 CSV File Support

HomeHaven supports CSV export functionality for managing household asset data. CSV files were chosen because they are lightweight, easy to read, and simple to integrate with Java file I/O operations.

---

## ⚠️ Known Issues

- Some UI styling may appear slightly different depending on operating system
- Dark mode may not fully apply to all JavaFX components
- Certain add/edit features are still being refined
- Window resizing may slightly affect layout spacing

---

## 📊 UML Diagram

A UML diagram illustrating the MVC structure and relationships between project components is included in this repository.

---

## ✅ Testing & Validation

The application was tested through repeated navigation and interaction between all screens, including:

- Dashboard
- Assets
- Reminders
- Maintenance calendar
- Light/Dark mode switching

The repository was also tested through GitHub cloning and fresh project setup to ensure the application runs correctly outside the original development environment.
