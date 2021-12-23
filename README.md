# Around the World
In this homework, we are going to explore using libraries on the Internet. In particular, we are going to use the 
[REST Countries API](https://restcountries.com/) for finding information about countries around the world. 
Follow the link to see how you can use the API to find out information about different countries around the world.

# Setup
This homework is set up using the Gradle build framework. It is the same framework that you are using in Android Studio,
except that it works on the command line for building command line apps. If you are interested, it was created using `gradle init`,
which you can read about in the [Gradle documentation](https://docs.gradle.org/current/samples/sample_building_kotlin_applications.html). 

## Build Settings
The Gradle build framework will help you do this assignment. You must make this change:
- Add importing dependencies for JSON in `app/build.gradle.kts`. You need to add a line that says `implementation("org.json:json:20171018")`. This brings in the JSON libraries from a central repo.

In addition:
- I added tests in `app/test/kotlin/aroundtheworld/CountryTest.kt`
- I added a main function in `app/src/kotlin/aroundtheworld/App.kt` that you can run to test your code

You can check these changes out if you're interested in how Gradle was used. 
There is also a Dockerfile in the root directory that is used to create a docker image in order to autograde your code.

## What you need to do
- Make the gradle build change to import JSON as described above
- You should write your code in the file `app/src/main/kotlin/aroundtheworld/Country.kt`. There's a skeleton there already.
- You should build your code by typing `./gradlew build` in the root directory. All the tests will fail initially, 
because you haven't written your code yet. When you write your code, the tests will pass as you get them correct. This is test-driven development, except that
I wrote the tests for you in advance.
- You should run your code by typing `./gradlew run` in the root directory
- You can also run the formal tests by typing `./gradlew test` in the root directory. 

# Country classes
In this homework, you're going to implement a class called *Country*. For example, to create a country
```
var country = Country("Belize")
```
This creates a country of the given name. You can assume that we will always use the full name of the country, 
i.e. you can assume that we will give "United States of America" rather than "usa".

## Fleshing out the country
Using the REST Countries API, get the relevant information about the country, including latitude and longitude. 
Remember that you'll be given the full name of the country, so look for the URL that is for finding the full name of a country.

## Accessors
The Country class should have accessors that give us information about the country.
- `name` -- return the name of the country
- `longitude` -- return the longitude of the country
- `latitude` -- return the latitude of the country

Be careful because some countries don't have the latitude and longitude specified in the database. In that case, just set the values to 0. 

## toString
To assist in debugging, write a `toString()` method that will allow you to print out a country.
- `fun toString(): String` -- return a String representation of a country

## Money
We want to plan a trip to this new country, so we need to know what kind of money is used. Extend the use of the API to now include country codes.

Write this method function to give us the list of currency codes that a country uses..

- `fun currencCodes(): List<String>` -- return the list of official currency codes that the country uses. E.g. the "USD" is the currency code for the United States. Some countries use more than one currency, hence you should return a `List` of the currency codes, e.g if a country uses the Canadian dollar and the US dollar both, you should return the list {"CAD", "USD"}

## Neighbors
We also want to know who our neighbors are. This is done through the neighbors method, which you should now write. Extend your use of the API to get this information. Our neighbors are countries in the **same sub region**. Look for and use the subregion API endpoint in the REST countries API.
- `fun neighbors(): List<Country>` -- Return a list of neighboring countries in the sub region. The list of neighboring countries should NOT include the country itself.
For example, since the US is in Northern America, the list of sub regional neighbors include Canada and Bermuda, but not does include the US.
Note: you must return a list of country objects, not a list of country names.

# Anywhere in the World
Your code should work for any country in the world, and the way to do this is to use the API. Read the online docs, and figure out how to use the API. The best way is to experiment by calling the API with different values and see what kind of JSON data is returned.

## Network and JSON
You make network calls using the java.net.URL libraries, and use the JSON library to convert the data into a dictionary or list we can access.
Kotlin adds to the Java URL class with a handy [`readText`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/java.net.-u-r-l/read-text.html)
method. Here's how to use it:

```
import java.net.URL
import org.json.JSONObject

var url = URL("http://...")                         // the URL to open
var text = url.readText()                           // retrieve the content at the URL
```

Most Internet APIs return [JSON objects](https://developer.android.com/reference/kotlin/org/json/JSONObject) or 
[JSON Arrays](https://developer.android.com/reference/kotlin/org/json/JSONArray). 
You can convert the text string representations that the API returns to you to objects
that you can manipulate in Kotlin.

If the API returns a JSON Object:
```
var obj = JSONObject(text)                         // convert to JSON object
```

Alternately, if the API returns a JSON Array:
```
var jsonarray = JSONArray(text)                    // convert to JSON array 
```
From then on, you can retrieve objects, arrays or values within the array or object by calling 
```getJSONObject```, ```getJSONArray```, ```getString```, ```getDouble```, ```getInt``` and so on.

For example, suppose that the API returns the text string
```
"[
   { 
       "title":"Vegetable-Pasta Oven Omelet", 
       "ingredients": [ "tomato", "onions" ]
   },
   { 
       "title":"Curry Chicken", 
       "ingredients": [ "curry paste", "chicken", "fennel" ]
   }
]"
```
This is a JSONArray, since the first character is a open square bracket `[`. There are two elements in the array at index 0 and 1.
Each array element is a JSON object. Each JSON object has two keys, `"title"` and `"ingredients"`. The value for `title` is a String, 
and the value for `ingredients` is a JSONArray.
```
var jsonarray = JSONArray(text)                        // convert text to Kotlin object
var element = jsonarray.getJSONObject(1)               // get the second element of the array
var title = element.getString("title")                 // title should now be "Curry Chicken"
var ingredients = element.getJSONArray("ingredients")  // ingredients is a JSONArray with "curry paste" and "chicken" as elements
var firstIngredient = ingredients.getString(0)         // firstIngredient is "curry paste"
var secondIngredient = incredients.getString(1)        // secondIngredient is "chicken"
```

# Submitting
Submit your file by adding the contents of this repo. The easiest way to do this is through the command line:
```
git add .
git commit -m "Submitting this homework assignment"
git push
```

This set of commands should add all your files to repo. The autograder will then run. You can view the progress of the autograder
by clicking on Pull Requests in GitHub.
