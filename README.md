# JustQuiz
JustQuiz is a simple quiz app that allows you to play quizzes and practice failed questions. It is built using Kotlin and Jetpack Compose.

## Features
The available quizzes are saved in `res/raw/quizzes.json` file.
When the user starts the app, they can see the list of available quizzes. They can select a quiz to play.
The quiz consists of multiple-choice questions. The user can select an answer and submit it.
After submitting the answer, the user can see the correct answer.
After the quiz is completed, the user can see the score and repeat the quiz skipping the questions that were answered correctly.

The user can see the previous attempts from the History screen.

The user can delete the data from the app by clicking the delete button in the Settings screen.

## Installation
- Clone the repository
- Open the project in Android Studio
- Build and run the project

## Deployment
To deploy the app, you can use the following steps:
- Clone the repository
- Open the project in Android Studio
- Build -> Generate Signed Bundle / APK
- Follow the steps to generate a signed APK to publish on Google Play Store.
