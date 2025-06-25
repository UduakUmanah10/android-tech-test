
Handling Unreliable Web Services
===================================

In real-world scenarios, network-based services may fail intermittently due to server downtime, 
API rate limits, or poor connectivity.
To build a resilient application, I adopted an offline-first approach:

The app first attempts to retrieve data from the remote API.

If the API responds successfully, the fetched data is cached into a local Room database.

This local database then acts as the source of truth for all data displayed in the user interface.

If the API request fails (e.g., due to network issues or server errors), 
the app gracefully falls back to local data, ensuring users can still access information without 
interruption.

This design guarantees that the app remains fully functional regardless of network reliability.

Managing Delayed or Slow Network Responses
============================================
To mimic real-world latency and server load, the test API introduces intentional response delays. 
To account for this:A progress/loading indicator is displayed immediately after initiating a data fetch.

This strategy helps maintain a responsive and user-friendly experience even under slow or
congested network conditions.


Synchronizing External Data Changes
======================================
Since the test API simulates data modifications from other users in the system 
(e.g., remote create, update, or delete operations), the app must stay consistent with the server.

To achieve this:

All local CRUD actions are flagged as pending when executed without a stable connection.

These flagged operations are stored in the local database and tracked persistently.

When connectivity is restored, a WorkManager task is triggered in the background. This worker:

Iterates over each flagged operation,

Executes the corresponding remote API call (create, update, or delete),

Removes the flag once synchronization is confirmed.

This ensures eventual consistency between the local database and the server, even if the device was
offline during the original action.

Data Validation and UI Reliability
====================================
To prevent invalid data entry and improve user experience:

Each form field is validated before submission.

Fields such as name, country, and coordinates are checked for format and completeness.

Real-time feedback is provided using TextInputLayout's error messaging, ensuring users know exactly 
what needs correction. This reduces backend errors and enhances data integrity across both local and 
remote databases.

Tools and Technologies Used
==============================
Purpose	Tool/Component
Local Storage	Room Database
Network Requests	Retrofit
Asynchronous Programming	Kotlin Coroutines and Flow
Background Sync and Retry Logic	WorkManager
Dependency Injection	Dagger Hilt
UI and Material Design	XML Layouts with Material Components
Form Validation	TextInputLayout
Lifecycle-Aware State Handling	ViewModel and StateFlow

OutStanding Task
=================
++ creating users from The mobile client