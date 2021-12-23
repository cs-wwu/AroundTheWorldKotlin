package aroundtheworld

import java.net.URL
import java.net.URLEncoder
import org.json.JSONObject
import org.json.JSONArray


val API = "https://restcountries.com/v2/"
val NAME_API = API + "name/"
val SUBREGION_API = API + "subregion/"

class Country {
    var name: String
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    var country: JSONObject

    constructor(name: String) {
        this.name = name

        // look up the country using REST API
        // var encoded = URLEncoder.encode(name, "UTF-8")
        // var url = URL(NAME_API + encoded)
        var url = URL(NAME_API + URLEncoder.encode(name, "UTF-8").replace("+", "%20"))
        var content = url.readText()
        var jsonarray = JSONArray(content)
        country = jsonarray.getJSONObject(0)

        // extract lat and lng
        var latlng = country.getJSONArray("latlng")
        latitude = latlng.getDouble(0)
        longitude = latlng.getDouble(1)
    }

    override fun toString(): String {
        return name
    }

    fun currencyCodes(): List<String> {
        // Find the currency codes
        var currencyArray = country.getJSONArray("currencies")
        var codes: MutableList<String> = mutableListOf<String>()
        for (i in 0..(currencyArray.length() - 1)) {
            var obj = currencyArray.getJSONObject(i)
            var code = obj.getString("code")
            codes.add(code)
        }
        return codes.toList()
    }

    fun neighbors(): List<Country> {
        // find the subregion
        var subregion = country.getString("subregion")
        var url = URL(SUBREGION_API + subregion)
        var content = url.readText()
        var countryArray = JSONArray(content)

        var countries: MutableList<Country> = mutableListOf<Country>()
        for (i in 0..(countryArray.length() - 1)) {
            var obj = countryArray.getJSONObject(i)
            var name = obj.getString("name")
            if (name != this.name) {
                var country = Country(name)
                countries.add(country)
            }
        }
        return countries.toList()

        // return listOf<Country>()
    }
}

