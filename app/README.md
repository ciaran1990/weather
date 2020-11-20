# README - Weather App

## About

This app will be able to give you a weather update based on your location.
If you do not have network connectivity, you will receive your last weather update - so long as it
falls within a 24 hour period. If it you do not have any data stored you will be prompted to get
connected to a network to receive your weather update.

This application requires the use of location services, you will need to grant this permission to
use this app.

### Future work

In the future I would like to display a screen to show predicted weather based on a users location.
I would also like to give the user the option of searching by city/postcode for weather updates.
Notifications to provide users feedback on the weather within the next 30 mins.
Provide more info to the user i.e. real feel for temperature based on wind.
Update the UI to look more attractive.

### Trade offs

I would have liked to include A LOT more tests, however I wanted the UX of the app to be as close to
perfect as possible. Obviously if I had more time, I would have created more unit tests, UI tests and
integration tests.

I would have also liked to have created a Jenkins job for CI/CD purposes.

I went for a MVVM design and towards the end I believed that I could have introduced a lot more
livedata variables i.e. locationretriver class.