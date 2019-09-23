<h1>Introduction</h1>

This is an Android Application written in Kotlin, that mitigates Penn students' hunger for food and,arguably more important, recruitment. The app is split into two tabbed Fragments (Food Fragment and OCR Fragment) that are navigated through a ViewPager.

<h1>Package Structure</h1>

The package is organized into the following folders:
- activities
    - BuildingDetailActivity 
    - MainActivity
    - MapActivity
    - RestaurantDetailActivity
- adapters
    - FoodRecyclerViewAdapter
    - ViewPagerAdapter 
- fragments
    - FoodFragment 
    - OCRFragment 
- objects
    - Building 
    - Restaurant 

<h1>Features</h1>

<b> 1. FoodFragment </b>

- Users can view all the food trucks from the provided list. The restaurants are stored as an ArrayList of  Restaurant objects and populated into the RecyclerView. 
- Users can sort the food trucks by rating (highest first) and distance from the user (closest first)
- Users can click on the "MAP" button to switch to a map view of all the food trucks. MapActivity will be launched.
- Users can click on each list item to navigate to the corresponding RestaurantDetailActivity for more information

<b> 2. MapActivity </b>

- Users can view a map (populated from GoogleMaps API) with markers at each of the food truck locations.
- Clicking on a marker will provide the name of the food truck
- The user's current location is indicated by a blue dot

<b> 3. RestaurantDetailActivity </b>

- Users can see the food truck's rating, name, location, hours (if provided), and food-type 
- Users can see a map with a marker at the restaurant's location

<b> 4. OCRFragment </b>

- Users can search for a building name (using any of the 'keywords' listed in the JSON object or the building title) through the EditText
- Hitting the Enter key on the keyboard or hitting the Submit button will initiate the API call and navigate the user to BuildingDetailActivity with the resultant building

Test cases that work:

    - Search of “vet” returns the “Vet School Hill” building. The address, floors, and year built data is empty in this              case, so it is not shown. The map with a marker at the coordinates is shown. 
    - Search of “dining hall” returns Hill College House. The address, floors, and year built is shown in the detail                activity. The map with a marker at the coordinates is shown. 

<b> 5. BuildingDetailActivity </b>

- Users can see the building's name, location, year_built, and number of floors. I parsed only these values from the JSON for the purpose of this demo. 
- Users can see a map with a marker at the restaurant's location.

<h1>Design Notes</h1>

Given more time, I would simplify all the map functions into a single Map object, as all the same functions are utilized across  MapActivity, RestaurantDetailActivity, and BuildingDetailActivity. 

As of now, the OCRFragment does not return a RecyclerView after a building is searched for. It automatically goes into the BuildingDetailActivity. Given more time, I'd show the search results in a list.

<h1> Resources </h1>

 - Google Maps API for map view
 - OKHTTP for JSON parsing
