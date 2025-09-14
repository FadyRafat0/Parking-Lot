# Parking Lot Application

## Overview
The Parking Lot Application is a Java-based system designed to simulate and manage a parking lot. It allows users to create a parking lot with a specified number of slots, park and remove cars, check the status of parking slots, and handle basic validations. The application features a user-friendly interface built with JavaFX and styled using CSS, with the UI designed using Scene Builder for an enhanced user experience.

## Features
- **Initialize Parking Lot**: Create a parking lot with a user-defined number of slots.
- **Park a Car**: Assign a car to the nearest available parking slot.
- **Remove a Car**: Free up a slot by removing a parked car.
- **View Parking Status**: Display the current state of the parking lot, including occupied and free slots.
- **Input Validation**: Handle edge cases such as attempting to park in a full lot or removing a car from an invalid slot.

## Technologies Used
- **Java**: Core logic for parking lot operations (78.6% of the codebase).
- **JavaFX**: Framework for building the graphical user interface.
- **Scene Builder**: Tool for designing the JavaFX-based UI.
- **CSS**: Styling for the user interface (21.4% of the codebase).

## Getting Started

### Prerequisites
- **Java JDK**: Version 11 or higher (with JavaFX included or configured).
- **Git**: For cloning the repository.
- **Scene Builder**: For editing or viewing the FXML files used in the UI (optional).
- (Optional) A build tool like **Maven** or **Gradle**, if used in the project.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/FadyRafat0/Parking-Lot.git
   ```
2. Navigate to the project directory:
   ```bash
   cd Parking-Lot
   ```
3. (If applicable) Install dependencies using the build tool:
   ```bash
   mvn install  # If using Maven
   # OR
   gradle build  # If using Gradle
   ```
4. Configure JavaFX:
   - Ensure the JavaFX SDK is added to your project or included in your JDK.
   - If using Maven/Gradle, verify JavaFX dependencies are in the build file.
5. Compile and run the application:
   ```bash
   javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml src/main/java/*.java
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml src.main.java.Main  # Replace 'Main' with the entry point class
   ```

## Usage
1. Launch the application by running the main class.
2. Use the JavaFX interface to:
   - Initialize the parking lot by specifying the number of slots.
   - Park a car by entering car details (e.g., registration number).
   - Remove a car by selecting the slot number.
   - View the parking lot status to see occupied and free slots.
3. Example interaction:
   - Open the application to see the JavaFX UI.
   - Use buttons or input fields (designed via Scene Builder) to perform operations like `Create Parking Lot`, `Park`, `Leave`, or `Status`.

## Project Structure
```
Parking-Lot
├── src/
│   ├── main/
│   │   ├── java/           # Java source files (models, logic, main classes)
│   │   └── resources/      # FXML files and other resources for JavaFX
│   └── test/               # Test files, if any
├── css/                    # CSS files for styling the JavaFX UI
├── .gitignore
└── README.md
```

## Contributing
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and test thoroughly.
4. Submit a pull request on GitHub with a clear description of your changes.

## Contact
For questions or feedback, reach out to [FadyRafat0](https://github.com/FadyRafat0) on GitHub.
