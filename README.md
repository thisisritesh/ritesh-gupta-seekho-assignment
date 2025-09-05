

# Seekho App Assignment - Ritesh Gupta

This Android application fetches and displays information about anime series using the public Jikan API (v4). It allows users to browse a list of top anime, view detailed information about each series, including trailers, and enjoy offline access to previously fetched data.

## Screen Recording / Demo
https://github.com/user-attachments/assets/464eaefe-d116-4d9d-a334-2d45cb3d7dab


## Assumptions Made

*   The Jikan API (v4 - `https://api.jikan.moe/v4/`) is available and responsive.
*   Users have a stable internet connection for initial data fetching and periodic synchronization.
*   The primary unique identifier for anime series is their `mal_id` (MyAnimeList ID) as provided by the Jikan API.
*   Trailer URLs provided by the Jikan API are primarily YouTube links and can be handled by a standard YouTube player component.
*   "Rating" refers to the MyAnimeList score.
*   "Main Cast" for the detail page refers to the primary characters.
*   For the design constraint of not showing "profile images," it's assumed this refers to character or user profile images. The current implementation focuses on poster images for anime series. If character images are considered profile images, their display would need to be conditional or removed.

## Features Implemented

**1. Anime List Page (Home Screen):**
 - 	**Fetches Top Anime:** Retrieves a list of top-rated anime series from `https://api.jikan.moe/v4/top/anime`.
 -  **Displays Key Information:** For each anime in the list, shows:
			- Poster Image
			- Title
			- Number of Episodes
			- Rating/Score
 -   **Navigation:** Tapping an anime item navigates to its Detail Page.

**2. Anime Detail Page:**

 - **Comprehensive Details:** Displays detailed information for a selected anime, fetched from `https://api.jikan.moe/v4/anime/{anime_id}` and related endpoints (e.g., characters). Information includes:
	  - Title
       - Plot/Synopsis
       - Genre(s)
        - Main Cast (Characters - main characters displayed)
        - Number of Episodes
        - Rating
        - Score
        - Poster Image
  *   **Trailer Playback:**
        *   Integrates a video player to play the anime trailer if a valid trailer URL is available.
        *   If no trailer is available, the poster image is displayed prominently in its place.

**3. Local Database & Offline Mode (Room):**
   *   **Single Source of Truth:** Made Room DB as Single Source of Truth, and using WorkManager to sync data.
   *   **Offline Caching:** All fetched anime list and detail data is stored locally in a Room database.
    *   **Offline Viewing:** Users can browse previously viewed anime lists and details even without an internet connection.
    *   **Data Synchronization:**
        *   A background worker (using WorkManager) periodically syncs the top anime list with the server when the device is online and meets appropriate conditions (e.g., network available).
        *   Details for an anime are fetched and cached when the user visits the detail page for the first time if the detail is not already fully cached or is considered incomplete.

**4. Architecture & Design:**
   *   **MVVM Architecture:** Utilizes the Model-View-ViewModel pattern for a clean separation of concerns.
    *   **Clean Architecture Principles:** Organized into UI, Domain, and Data layers.
        *   **UI Layer:** Jetpack Compose for declarative UI, ViewModels for UI logic and state management.
        *   **Domain Layer:** Use Cases to encapsulate business logic.
        *   **Data Layer:** Repository pattern to abstract data sources, Retrofit for networking, Room for local database.
    *   **Reactive Programming:** Uses Kotlin Coroutines and StateFlow for asynchronous operations and reactive UI updates.
    *   **Dependency Injection:** Hilt is used for managing dependencies.

**5. Robustness & User Experience:**
   * Gracefully handles errors, Users are informed via UI states.
    * Displays loading indicators while data is being fetched.
    *    Shows informative messages if a list is empty (e.g., "No anime found").
    *   Proactively checks for network connectivity before certain operations and relies on WorkManager for robust background syncing.

**6. Modern Android Development Practices:**
   *   Written entirely in Kotlin.
    *   Utilizes modern Jetpack libraries (Compose, Room, WorkManager, Hilt, ViewModel, Navigation).
    *   Image loading handled by Coil.

## Known Limitations

*   Background sync with WorkManager runs every 15 minutes. If the initial data fetch fails due to a network error, the next attempt will only happen after 15 minutes, since Room is treated as the single source of truth.
*   The cast list gets erased after every data sync because it is fetched from a separate endpoint.
*   No "Retry" button, after network failure.
* We’re not receiving real-time updates for network connectivity, so data is not fetched immediately when the network becomes available. Instead, we rely on WorkManager constraints.
