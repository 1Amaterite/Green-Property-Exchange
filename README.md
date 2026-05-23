# Green Property Exchange

An interactive Object-Oriented Programming (OOP) Java application developed for **CCPROG3** (Computer Programming 3). This application simulates a green-themed property management and reservation system, applying the **Model-View-Controller (MVC)** architectural pattern and swing GUI components.

---

## 📖 Table of Contents
1. [Project Overview](#-project-overview)
2. [Key Features](#-key-features)
3. [Architectural Design (MVC)](#-architectural-design-mvc)
4. [Class Structure](#%EF%B8%8F-class-structure)
5. [Pricing Model](#%EF%B8%8F-pricing-model)
6. [Compilation & Execution](#%EF%B8%8F-compilation--execution)
7. [API Documentation](#-api-documentation)

---

## 🔍 Project Overview

The **Green Property Exchange** is a platform for managing eco-friendly accommodations and simulating customer reservations. Property owners can add, delete, and manage properties, update base pricing, and adjust daily **environmental modifiers** to reflect seasonal or conservation-related price changes. Guests can view property calendars, book multi-day stays, and cancel reservations.

---

## ✨ Key Features

- **Eco-Friendly Accommodation Types**: Support for multiple sustainable housing styles, each carrying a different price multiplier.
- **Dynamic 30-Day Booking Calendar**: Check real-time date availability, view detailed daily prices, and see booking ranges.
- **Advanced Pricing System**: Computes overnight stays using a combination of the property's base price, type multiplier, and day-specific environmental adjustments.
- **Reservation & Booking Engine**:
  - Book ranges of dates (1-30).
  - Generates detailed, day-by-day itemized receipts with a final sum.
  - Check date range availability at once.
  - Cancel existing reservations, returning the dates to "Available".
- **Interactive Swing GUI**: Complete GUI with transitions, interactive calendar grids, form inputs, and dialogue boxes.

---

## 🏛️ Architectural Design (MVC)

The system adheres strictly to the **Model-View-Controller (MVC)** architecture to separate business logic from the user interface.

```
                   ┌──────────────┐
                   │    Model     │
                   └──────┬───────┘
                          │ (Notifies/Reads)
                          ▼
┌──────────────┐   ┌──────────────┐
│  Controller  ├──►│     View     │
└──────────────┘   └──────────────┘
```

- **Model**: Represents the data and core business rules (`Property`, `PropertyManager`, `Reservation`, `Date`, `Calendar`, `Guest`). It has no knowledge of Swing or the UI layout.
- **View**: Renders the application GUI elements (`PropertyManagerView`, `PropertyDetailsView`, `CreatePropertyView`, `ManagePropertyView`, `OpeningScreen`). It reports user interactions (clicks, text input) back to the Controller.
- **Controller**: Coordinates actions (`PropertyManagerController`). It responds to user input from the View, updates the Model, and refreshes the View with the latest data.

---

## 🛠️ Class Structure

### 📦 Core Model
- **`PropertyManager`**: Coordinates the collection of properties, ensuring names remain unique and handling additions/deletions.
- **`Property`** *(Abstract)*: Base class representing a property. It holds the name, base price, calendar, and reservation records.
  - **`EcoApartment`** (Multiplier: `1.00x`): A sustainable urban apartment.
  - **`SustainableHouse`** (Multiplier: `1.20x`): A modern green residential house.
  - **`GreenResort`** (Multiplier: `1.35x`): An eco-luxury resort.
  - **`EcoGlamping`** (Multiplier: `1.50x`): A luxury eco-tent close to nature.
- **`Calendar`**: Manages a cycle of 30 `Date` objects starting on a random or defined day of the week.
- **`Date`**: Represents a single day in the calendar with reservation status and an environment price modifier.
- **`Reservation`**: Holds check-in/check-out dates, guest details, and computes the pricing breakdown for a stay.
- **`Guest`**: Holds guest profile details (name).

### 🖥️ View & Controllers
- **`GreenPropertyExchange`**: Application entry point containing the `main` method that instantiates the MVC components.
- **`PropertyManagerController`**: Glues the views to the model, handling button clicks, input forms, and error checks.
- **`PropertyManagerView`**: Main frame containing panels for the opening screen, main menu, creation menu, and property details.

---

## 🏷️ Pricing Model

The nightly rate for a property on any given day is computed dynamically using:

$$\text{Nightly Price} = \text{Base Price} \times \text{Property Multiplier} \times \text{Environmental Modifier}$$

### Variables
1. **Base Price**: Defaults to `PHP 1,500.00` per night. Can be modified by the host (minimum `PHP 100.00`) provided there are no active reservations.
2. **Property Multiplier**: Determined by the accommodation type:
   | Property Type | Multiplier |
   |---|---|
   | **Eco-Apartment** | 1.00x |
   | **Sustainable House** | 1.20x |
   | **Green Resort** | 1.35x |
   | **Eco-Glamping** | 1.50x |
3. **Environmental Modifier**: A day-specific rate modifier (from `80%` to `120%` / `0.8` to `1.2`) that allows managers to adjust for weekend peaks, seasons, or green promotions.

---

## 🚀 Compilation & Execution

To run this application locally, ensure you have **Java Development Kit (JDK) 17 or higher** installed.

### 1. Compilation
From the root of the project directory, compile all Java source files:
```bash
javac *.java
```

### 2. Execution
Run the main program:
```bash
java GreenPropertyExchange
```

---

## 📄 API Documentation

Standard Javadocs are compiled and available in the repository. Open the file [Documentation/index.html](file://Documentation/index.html) (or double click it in your file explorer) in your web browser to browse the detailed class documentation.
