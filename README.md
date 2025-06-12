
# 📰 NewsApp-MVVM Architecture

This is a  Android News App built using Kotlin and the MVVM architecture pattern. The app fetches real-time news data from a public API and displays it in a clean and responsive UI.

Key highlights of the project:

- MVVM Architecture with proper separation of concerns
- Retrofit for networking and API integration
- Room Database for offline caching
- Navigation Component for seamless screen transitions
- Repository Pattern for clean data flow and testability.

This project demonstrates best practices in modern Android development and is ideal for learning how to structure scalable Android apps using Jetpack components.

## Branches

- main: Jetpack Compose using Dagger Hilt
- xml-dagger-hilt: XML using Dagger Hilt
- xml-dagger-2: XML using Dagger 2
## Features

- Instant search using Flow operators
- Debounce
  - Filter
  - DistinctUntilChanged
  - FlatMapLatest
- Offline news
- Pagination
- TopHeadlines of the news
- Country-wise news
- Multiple Languages selection-wise news
- Multiple sources wise news
  
## Project Structure

## Project Structure
``` bash
├── data
│   ├── api
│   │   ├── AuthInterceptor.kt
│   │   └── NetworkService.kt
│   ├── local
│   │   ├── AppDatabase.kt
│   │   ├── AppDatabaseService.kt
│   │   ├── DatabaseService.kt
│   │   ├── dao
│   │   │   └── ArticleDao.kt
│   │   └── entitiy
│   │       ├── Article.kt
│   │       └── Source.kt
│   ├── model
│   │   ├── Country.kt
│   │   ├── Language.kt
│   │   ├── NewsSourcesResponse.kt
│   │   └── TopHeadLineResponse.kt
│   ├── paging
│   │   └── TopHeadlinePagingSource.kt
│   └── repository
│       ├── GetCountryRepository.kt
│       ├── GetLanguageRepository.kt
│       ├── NewsRepository.kt
│       ├── NewsSourcesRepository.kt
│       ├── NewsTypeRepository.kt
│       ├── OfflineArticleRepository.kt
│       ├── SearchNewsRepository.kt
│       ├── TopHeadlinePagingSourceRepository.kt
│       └── TopHeadlinesRepository.kt
├── di
│   ├── DispatchersProvider.kt
│   ├── Qualifiers.kt
│   └── modules
│       └── ApplicationModule.kt
├── theme
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
├── ui
│   ├── base
│   │   ├── CommonUI.kt
│   │   ├── NewsNavigation.kt
│   │   ├── Route.kt
│   │   └── UIState.kt
│   ├── country
│   │   ├── CountriesViewModel.kt
│   │   └── CountrySelectionRoute.kt
│   ├── home
│   │   ├── HomeScreenRoute.kt
│   │   └── uicomponets
│   │       └── MainScreenItemSection.kt
│   ├── language
│   │   ├── LanguageSelectionRoute.kt
│   │   └── LanguageViewmodel.kt
│   ├── main
│   │   └── MainActivity.kt
│   ├── news
│   │   ├── NewsRoute.kt
│   │   └── NewsViewModel.kt
│   ├── newssources
│   │   ├── NewsSourcesRoute.kt
│   │   └── NewsSourcesViewModel.kt
│   ├── offline
│   │   ├── OfflineArticleRoute.kt
│   │   └── OfflineArticleViewModel.kt
│   ├── search
│   │   ├── SearchNewsRoute.kt
│   │   └── SearchNewsViewModel.kt
│   ├── toHeadlinePagination
│   │   ├── TopHeadlinePaginationRoute.kt
│   │   └── TopHeadlinePaginationViewModel.kt
│   └── topHeadline
│       ├── TopHeadlineRoute.kt
│       └── TopHeadlinesViewModel.kt
├── utils
│   ├── AppConstants.kt
│   ├── Extensions.kt
│   ├── Helper.kt
│   ├── NetworkHelper.kt
│   ├── NewsType.kt
│   ├── TimeUtil.kt
│   └── typealias.kt
└── worker
    └── NewsWorker.kt
```
![Untitled design (1)](https://github.com/user-attachments/assets/11556e2c-3344-4afb-a6de-74199c0c3422)




