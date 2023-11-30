# CinemaFinal
## Introduction
Welcome to CinemaFinal, this project is the updated version of Cinema. Built with Java, this app offers a seamless and engaging way to explore, favorite, and book movie tickets. Utilizing Firebase Authentication, Google Maps API, and YouTube WebView integration, CinemaApp delivers a comprehensive and user-friendly experience.

## Features

### User Authentication
- **LoginAndRegisterActivity**: Manages user authentication with two fragments: `LoginFragment` and `SignupFragment`.
- **Firebase Authentication**: Securely handles user account management.

### Main Interface
- **MainActivity**: The heart of the app, containing four primary fragments:
  - **HomeFragment**: Discover movies across various genres like Action, Horror, etc.
  - **FavoriteFragment**: Keeps track of your favorite movies.
  - **SearchFragment**: Enables users to search for movies in the database.
  - **MapFragment**: Integrates Google Maps API to locate nearby cinemas.

### Advanced Functionality
- **RecyclerView**: Each fragment uses `ListAdapter RecyclerView` for displaying movies.
- **Favorite Management**: Long-press to add/remove movies from favorites.
- **Bottom Navigation**: Easily navigate between fragments.
- **MovieFragment**: Detailed information about movies, including YouTube trailers.
- **Booking Feature**: Book movie tickets through an external website link.

### MVVM Architecture
- **Model**: `FireBaseRepo` handles data and business logic.
- **ViewModel**: `MovieViewModel` class utilizing Android ViewModel and LiveData.
- **View**: Comprises all Activities and Fragments in the app.

### Additional Components
- **Movie Data Class**: Represents the movie data structure.
- **Animations**: Engaging animations using `ViewAnimation` for picture transitions.

## Getting Started
To run this app, clone the repo and open it in Android Studio. Configure it with your Firebase project and ensure all dependencies are properly set up.
