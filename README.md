# TowertexTMDB
experimental architecture demo
features: 
- separated concerns
  - api module to handle network
  - model module to handle business logic
- api module 
  - test coverage via JUnit
  - Retrofit
  - custom Call to handle non-standard responses
  - services separated via delegation
- model module
  - test coverage via AndroidInstrumentation
  - Room
  - SigleSourceOfTruth architecture
- Dependecy injection
  - Koin
  - scoped ViewModels
  - ResourceRepository pattern
  - ViewModel DI pattern
- Jetpack paging library
  - also with StateAdapter to handle edge cases
- Navigator pattern