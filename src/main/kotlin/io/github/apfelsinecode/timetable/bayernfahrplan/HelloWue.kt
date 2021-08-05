package io.github.apfelsinecode.timetable.bayernfahrplan

import org.jsoup.Jsoup

const val baseUrl = "https://www.bayern-fahrplan.de/de/abfahrt-ankunft/xhr_departures_fs"

val wuerzburg = "80001152"
val busbahnhof = "80029081"
val sanderring = "3700348"

val defaultData = mapOf(
    "is_fs" to "1",
    "is_xhr" to "True",
    "nameInfo_dm" to wuerzburg
)


fun main() {
    val doc = Jsoup.connect(baseUrl).data(defaultData).get()
    // println(doc)
    // println("#".repeat(32))
    val results = doc.getElementsByClass("results-tbody")
    for (result in results) {
        println(result)
        println("#".repeat(64))
    }
    //println(results)
}