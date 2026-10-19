# Neural Network Playground

Neural Network Playground is an Android app I built to experiment with neural networks and make it easier to create, train, and test models directly from a mobile device.

The neural network itself was implemented from scratch in Kotlin, separately from the Android application. I then integrated it into the app, where users can create their own models, train them on simple datasets, test their predictions, and inspect how their models are configured.

This is the first version of the project, so the available training datasets are currently limited to XOR, AND, NAND, and OR.

## Features

- **Create neural networks:** Create models with configurable network architectures.
- **Train models:** Train a model on one of the supported logical datasets.
- **Test predictions:** Try a trained model with input values and see its output.
- **Inspect models:** View the model's structure and configuration.
- **Save models:** Save trained models to Firebase so users can access their saved work.
- **Manage multiple models:** Create and keep track of different models while experimenting with different configurations.

## Tech Stack

- **Kotlin** — Main programming language.
- **Jetpack Compose** — Building the app's user interface.
- **Kotlin Coroutines** — Handling asynchronous operations.
- **Firebase Authentication** — User authentication.
- **Cloud Firestore** — Storing users' saved models.
- **Custom neural network library** — A neural network implementation written from scratch in Kotlin.

## The Neural Network

The most important part of this project is the neural network implementation itself.

Instead of relying on an existing machine learning framework for the core implementation, I built the neural network separately in Kotlin and then integrated it into the Android application.

The underlying library is a separate project and is designed to handle more than the four datasets currently available in this app. It is capable of training models on datasets such as Iris as well.

You can find the separate library in my **Neural Network Library** repository.

## Supported Datasets

Version 1 currently supports four logical datasets:

- XOR
- AND
- NAND
- OR

These datasets provide a simple way to experiment with training a neural network, changing its configuration, and testing its predictions.

The app currently focuses on these smaller problems rather than providing a full machine learning environment.

## Architecture and Data Storage

The Android application uses Jetpack Compose for the UI, with Kotlin handling the application logic and model interactions.

Firebase is used to associate saved models with individual users, allowing their models to be stored remotely rather than existing only on the device.

The neural network implementation is kept separate from the Android UI, making it possible to develop and improve the underlying library independently of the app.

## What I Learned

Working on this project gave me practical experience with:

- Implementing a neural network from scratch in Kotlin.
- Integrating a separately developed Kotlin library into an Android application.
- Building an interactive interface using Jetpack Compose.
- Managing asynchronous operations with Kotlin Coroutines.
- Integrating Firebase Authentication and Cloud Firestore.
- Saving and retrieving user-specific model data.
- Separating the machine learning logic from the Android application.

## What's Next

This is the first version of Neural Network Playground. For now, the focus is on creating, training, testing, and inspecting models using the four supported logical datasets.

The underlying neural network library is a separate project, so improvements to the library can be developed independently as the playground evolves.

## Project

Built as a personal Android and machine learning project using Kotlin, Jetpack Compose, Firebase, and a neural network implemented from scratch.

