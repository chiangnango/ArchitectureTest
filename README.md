# ArchitectureTest

This is a test application used to practice some ideas or libraries to fulfill some architecture patterns.

It's currently a browsing APOD(Astronomy Picture of the Day) app that there's a main page to display many astronomy pictures, 
and click the picture will navigate to detail page containing the description of the astronomy picture.
It uses the data from NASA APIs https://api.nasa.gov/planetary/apod.

App adopts **Android Architecture Components - ViewModel & LiveData** to fulfill MVVM pattern.
It also utilize **Kotlin Coroutine** and **OkHttp3** to help to fetch data from network, and **Glide** to load and show image.

In main page, there's a 2-dimensional recyclerView can scroll in both horizontal and vertical directions.
*MainRepository* fetches data and *MainViewModel* processes the data and set to LiveData, then *MainFragment* observes the changes and display it on RecyclerView.
When item is clicked, fragment will notify ViewModel, and ViewModel will call *Navigator* to notify *MainActivity* to launch *DetailFragment*.
DetailFragment share the same ViewModel with MainFragment to avoid additional data fetch.

