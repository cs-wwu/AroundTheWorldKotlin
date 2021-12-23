/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package aroundtheworld

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CountryTest {
    @Test
    fun testCountryAccessors() {
        var country = Country("Belize")
        assertEquals(country.name, "Belize")
        assertEquals(country.longitude, -88.75)
        assertEquals(country.latitude, 17.25)
    }

    @Test
    fun testCurrencyCodes() {
        var cuba = Country("Cuba")
        assertNotNull(cuba)
        assertEquals(cuba.name, "Cuba")
        assertEquals(cuba.longitude, -80.0)
        assertEquals(cuba.latitude, 21.5)

        var codes = cuba.currencyCodes()
        assertTrue(codes.contains("CUC"))
        assertTrue(codes.contains("CUP"))
    }

    fun contains(countries: List<Country>, country: Country): Boolean {
        for (i in 0..countries.size - 1) {
            var c = countries[i]
            if (country.name == c.name && country.longitude == c.longitude &&
                country.latitude == c.latitude) {
                return true;
            }
        }
        return false
    }

    @Test
    fun testNeighbors() {
        val subregion = listOf("Anguilla", "Antigua and Barbuda", "Aruba",
            "Bahamas", "Barbados", "Bonaire, Sint Eustatius and Saba",
            "Virgin Islands (British)", "Virgin Islands (U.S.)",
            "Cayman Islands", "Curaçao", "Dominica",
            "Dominican Republic", "Grenada", "Guadeloupe", "Haiti",
            "Jamaica", "Martinique", "Montserrat", "Puerto Rico",
            "Saint Barthélemy", "Saint Kitts and Nevis",
            "Saint Lucia", "Saint Martin (French part)",
            "Saint Vincent and the Grenadines",
            "Sint Maarten (Dutch part)", "Trinidad and Tobago",
            "Turks and Caicos Islands")
        var country = Country("Cuba")
        var neighbors = country.neighbors()
        assertEquals(neighbors.size, subregion.size)

        subregion.forEach {
            var country = Country(it)
            assertTrue(contains(neighbors, country))
        }
    }
}