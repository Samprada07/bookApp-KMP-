# BookApp - Kotlin Multiplatform Internship Assignment

A Book Management App built with Kotlin Multiplatform (KMP) and Compose Multiplatform as part of a
Mobile Development Internship Assignment.

## Screenshots/ APK

> APK available in the submission.

## Tech Stack

| Layer                | Technology                 |
|----------------------|----------------------------|
| Language             | Kotlin                     |
| UI                   | Compose Multiplatform      |
| Networking           | Ktor                       |
| Local Database       | SQLDelight                 |
| Dependency Injection | Koin                       |
| State Management     | StateFlow + Coroutines     |
| Architecture         | Clean Architecture         |
| Navigation           | Jetpack Navigation Compose |

## Architecture

UI (Compose Screens)
↓
ViewModel (StateFlow)
↓
Use Cases (Business Logic)
↓
Repository (Interface)
↓
┌────┴────┐
Remote API Local DB
(Ktor)  (SQLDelight)

## Project Structure

shared/
├── data/
│ ├── local/ # SQLDelight database & local data source
│ ├── remote/ # Ktor API service & DTOs
│ └── repository/ # Repository implementation & mappers
├── domain/
│ ├── model/ # Book domain model
│ ├── repository/ # Repository interface
│ └── usecase/ # GetBooks, AddBook, DeleteBook, GetBookById
├── presentation/
│ ├── navigation/ # NavGraph & Screen routes
│ ├── screens/ # Splash, BookList, AddBook, BookDetail
│ └── viewmodel/ # BookListViewModel, AddBookViewModel, BookDetailViewModel
└── di/ # Koin modules (AppModule, AndroidModule)
androidApp/
└── MainActivity.kt # Android entry point, Koin initialization

## Features

### Core

- Splash Screen
- Book List Screen
- Add Book Screen
- Book Detail Screen
- Delete Book

### Bonus

- Pull to Refresh
- Search Books
- Dark Mode (follows system setting)
- Offline-first (SQLDelight caches API results)

## API

Base URL: `https://fakerestapi.azurewebsites.net`

| Method | Endpoint             | Description    |
|--------|----------------------|----------------|
| GET    | `/api/v1/Books`      | Get all books  |
| GET    | `/api/v1/Books/{id}` | Get book by ID |
| POST   | `/api/v1/Books`      | Add a book     |
| DELETE | `/api/v1/Books/{id}` | Delete a book  |

## Known Limitations

- This app uses a **fake REST API** (`fakerestapi.azurewebsites.net`) which does not persist changes
  server-side. Add and Delete operations are handled locally in the UI state for a seamless
  experience.
- **iOS** is not yet configured. The shared business logic is KMP-ready but requires native
  SQLDelight (`NativeSqliteDriver`) and Ktor Darwin engine for iOS targets.
- Initial load time depends on the fake API server response time.

## How to Run

1. Clone the repository

```bash
   git clone <your-repo-url>
```

2. Open in **Android Studio Panda** or newer
3. Let Gradle sync complete
4. Run on an Android emulator or device (**API 24+**)

## Platform Support

| Platform | Status      |
|----------|-------------|
| Android  | Supported   |
| iOS      | Coming soon |

## Author

**Samprada Das**  
Kotlin Multiplatform Mobile Development Intern Assignment