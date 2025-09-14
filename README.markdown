# Parking Lot Application

## Overview
The Parking Lot Application is a Java-based system designed to simulate and manage a parking lot. It allows users to create a parking lot with a specified number of slots, park and remove cars, check the status of parking slots, and handle basic validations. The application includes a user interface styled with CSS for an enhanced user experience.

## Features
- **Initialize Parking Lot**: Create a parking lot with a user-defined number of slots.
- **Park a Car**: Assign a car to the nearest available parking slot.
- **Remove a Car**: Free up a slot by removing a parked car.
- **View Parking Status**: Display the current state of the parking lot, including occupied and free slots.
- **Input Validation**: Handle edge cases such as attempting to park in a full lot or removing a car from an invalid slot.

## Technologies Used
- **Java**: Core logic for parking lot operations (78.6% of the codebase).
- **CSS**: Styling for the user interface (21.4% of the codebase).
- (Optional) Additional libraries or frameworks for logging or testing, if included in the project.

## Getting Started

### Prerequisites
- **Java JDK**: Version 11 or higher.
- **Git**: For cloning the repository.
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
4. Compile and run the application:
   ```bash
   javac src/*.java
   java src.Main  # Replace 'Main' with the entry point class
   ```

## Usage
1. Launch the application by running the main class.
2. Follow the prompts to:
   - Initialize the parking lot with a specified number of slots.
   - Park a car by providing car details (e.g., registration number).
   - Remove a car by specifying the slot number.
   - Check the parking lot status to view occupied and free slots.
3. Example commands (if the application supports a command-line interface):
   ```bash
   create_parking_lot 6
   park KA-01-HH-1234
   leave 2
   status
   ```

## Project Structure
- `/src`: Contains the Java source code for the parking lot logic.
- `/css`: Contains CSS files for styling the user interface.
- (Optional) `/tests`: Contains test files, if included.

## Contributing
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature/your-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m 'Add your feature'
   ```
4. Push to the branch:
   ```bash
   git push origin feature/your-feature
   ```
5. Open a pull request on GitHub.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
For questions or feedback, reach out to [FadyRafat0](https://github.com/FadyRafat0) on GitHub.