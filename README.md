# HomeHaven 🏠

HomeHaven is a JavaFX-based household asset management application designed to help homeowners organize, monitor, and maintain important household items in one place. The application makes it easy to track assets, warranties, maintenance tasks, and reminders through a clean and intuitive interface.

---

# ✨ Key Features & Functionality

The HomeHaven application includes the main features needed to organize and track household assets:

- Dashboard with quick stats for total assets, upcoming tasks, and alerts
- Maintenance calendar to visualize scheduled upkeep and inspections
- Dedicated Assets screen for viewing household items and their details
- Reminders screen for managing maintenance, inspection, and warranty alerts
- Warranty tracking with expiration date visibility
- Status indicators for overdue and upcoming tasks
- Light and dark mode support for improved usability and accessibility
- CSV export functionality for downloading asset records
- CSV import functionality for loading asset data
- Simple multi-screen navigation between dashboard, assets, and reminders
- Add, edit, and remove functionality for assets and reminders

Overall, the application demonstrates the essential tools a homeowner needs to manage possessions and stay on top of important maintenance responsibilities.

---

# 🧪 Usability Testing

We performed usability testing on the HomeHaven application to ensure the interface was easy to navigate and the core features behaved as expected.

## Testing Workflow

We clicked through all major screens, including:

- Dashboard
- Asset list
- Reminders
- Maintenance calendar

We also tested common user actions such as:

- adding new assets
- editing and removing assets
- creating reminders
- reviewing warranty information
- importing and exporting CSV files
- switching between light and dark mode
- navigating between screens

## Improvements Made

During testing, we identified and corrected several usability issues, including:

- spacing inconsistencies
- unclear layout flow
- duplicate data loading
- UI elements that felt visually out of place
- navigation polish improvements
- dashboard refresh synchronization

These updates made the application:

- cleaner
- easier to use
- more visually consistent
- more intuitive for first-time users

---

# 🚀 Project Goal

The long-term goal of HomeHaven is to evolve into a complete Java + JavaFX desktop application with:

- persistent asset storage
- reminder scheduling
- dashboard analytics
- CSV reporting
- warranty and maintenance KPI tracking

This repository currently serves as the foundation for that transition.

---

# 🧩 Architecture

HomeHaven follows the **Model-View-Controller (MVC)** design pattern.

## Model

Handles application data and business logic.

- Asset.java
- Reminder.java
- DataStore.java
- AssetStatus.java
- ReminderType.java

## View

Contains all JavaFX FXML interface files.

- Main.fxml
- dashboard.fxml
- assets.fxml
- reminders.fxml

## Controller

Handles screen behavior and user interactions.

- MainController.java
- DashboardController.java
- AssetsController.java
- RemindersController.java

---

# 👥 Contributors

- Ryan Borrego
- Josue Alan Canales
- Lawrencia Tiwaa-Adjei
- Ahaan Vohra

This project was developed collaboratively as part of a JavaFX software engineering course project focused on applying MVC architecture, GUI development, and data management concepts.

---

# ⚙️ Technologies Used

- Java
- JavaFX
- FXML
- Maven
- GitHub

---

# ▶️ Running the Application

## Requirements

- Java 17 or newer
- Maven installed
- JavaFX configured in IntelliJ IDEA or Eclipse

## Steps

1. Clone the repository
2. Open the project in IntelliJ IDEA or Eclipse
3. Allow Maven dependencies to load
4. Run:

```text
src/main/java/edu/utsa/cs3443/HomeHaven/Main.java
```

The HomeHaven application window should launch automatically.

---

# 📁 CSV File Support

HomeHaven supports CSV import and export functionality for managing household asset data. CSV files were chosen because they are lightweight, easy to read, and simple to integrate with Java file I/O operations.

---

# ⚠️ Known Issues

- Some UI styling may appear slightly different depending on operating system
- Dark mode may not fully apply to all JavaFX components
- Window resizing may slightly affect layout spacing
- CSV import expects properly formatted files

---

# 📊 UML Diagram

A UML diagram illustrating the MVC structure and relationships between project components is included in this repository.

<img width="540" height="1113" alt="image" src="https://github.com/user-attachments/assets/74686aad-8dc7-4c50-a635-02df119962ec" />

---

# ✅ Testing & Validation

The application was tested through repeated navigation and interaction between all screens, including:

- Dashboard
- Assets
- Reminders
- Maintenance calendar
- Light/Dark mode switching
- CSV import/export functionality

The repository was also tested through GitHub cloning and fresh project setup to ensure the application runs correctly outside the original development environment.
