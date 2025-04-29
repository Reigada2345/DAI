# Administrator Project

## Overview
The Administrator Project is a Java application designed to manage bus stops, buses, and passenger information. It provides an interface for administrators to create, edit, and deactivate bus stops, as well as manage bus and passenger details.

## Project Structure
```
AdministratorProject
├── src
│   ├── Main.java
│   ├── models
│   │   ├── Administrador.java
│   │   ├── Paragem.java
│   │   ├── Autocarro.java
│   │   └── Passageiro.java
│   ├── services
│   │   ├── ParagemService.java
│   │   ├── AutocarroService.java
│   │   └── PassageiroService.java
│   └── utils
│       └── Validator.java
├── .gitignore
└── README.md
```

## Features
- **Administrator Management**: Administrators can create, edit, and deactivate bus stops.
- **Bus Management**: Administrators can create and manage bus details.
- **Passenger Management**: Administrators can update passenger information.

## Setup Instructions
1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Compile the Java files in the `src` directory.
4. Run the `Main.java` file to start the application.

## Usage
- Launch the application and follow the prompts to manage bus stops, buses, and passengers.
- Use the provided services to interact with the models effectively.

## Contributing
Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.