# Emotion Detecting System

## Overview
The **Emotion Detecting System** is a Java-based application that utilizes ONNX models to analyze text input and determine the emotion conveyed. The system provides a graphical user interface (GUI) for users to enter a post and receive an emotion classification.

## Features
- User-friendly GUI built with Java Swing
- Supports real-time emotion detection using an ONNX model
- Classifies text into six emotions: Sad, Happy, In Love, Angry, Scared, and Surprised
- Displays classified emotions in an interactive text area

## Technologies Used
- **Python** for Machine Learning Model Training
- **Java** (Swing for UI)
- **ONNX Runtime** (for running the machine learning model in java)
- **Gradients & Modern UI Design**

## Installation
### Prerequisites
Ensure you have the following installed:
- Java Development Kit (JDK 11 or higher)
- ONNX Runtime Library

### Steps to Run
1. Clone this repository:
   ```sh
   git clone https://github.com/yourusername/Emotion-Detecting-System.git
   cd Emotion-Detecting-System
   ```
2. Download the Kaggle Dataset: https://www.kaggle.com/datasets/nelgiriyewithana/emotions
3. Run The Python Script to Generate the model "E_2.onnx".
4. Compile and run the maven project with the dependencies in the "pom.xml" file:
   ```sh
   javac -cp .;onnxruntime.jar MainWindow.java
   java -cp .;onnxruntime.jar MainWindow
   ```

## How It Works
1. The user enters a text post and their name in the input fields.
2. Upon submission, the ONNX model processes the text and assigns an emotion category.
3. The detected emotion is displayed alongside the user's name.
4. The system handles various text formats and provides a user-friendly experience.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request with your improvements.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Dev Team
Usman Nisar, Abdul Basit Khan, Rameesha Wajid, Hamza Mughal, Husnain Khawaja, Neha Qasim, Hasan Butt
: SED @UET Taxila


