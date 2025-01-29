# TowertexTMDB
experimental architecture demo
features: 
- separated concerns
  - api module to handle network
  - model module to handle business logic
- api module 
  - test coverage via JUnit
  - networking via Ktor
  - services separated via delegation
- model module
  - test coverage via AndroidInstrumentation
  - persistence via Room
  - Single Source Of Truth architecture
- app module
  - dependency via Koin
  - scoped ViewModels
  - ResourceRepository pattern
  - ViewModel DI pattern 
  - Jetpack paging library
  - StateAdapter to handle paging edge cases
  - Navigator pattern