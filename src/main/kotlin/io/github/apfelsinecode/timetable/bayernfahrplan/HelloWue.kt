package io.github.apfelsinecode.timetable.bayernfahrplan

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

const val baseUrl = "https://www.bayern-fahrplan.de/de/abfahrt-ankunft/xhr_departures_fs"

val wuerzburg = "80001152"
val busbahnhof = "80029081"
val sanderring = "3700348"

val defaultData = mapOf(
    "is_fs" to "1",
    "is_xhr" to "True",
    "nameInfo_dm" to busbahnhof
)


fun main() {
    val doc = Jsoup.connect(baseUrl).data(defaultData).get()
    // println(doc)
    // println("#".repeat(32))
    val results = doc.getElementsByClass("results-tbody")
//    for (result in results) {
//        println(result)
//        println("#".repeat(64))
//    }
    val table = results.first()
    println(table)
    println("\n".repeat(8))
    if (table != null) {
        /*for (child in table.children()) {
            println(child)
            println("#".repeat(64))
        }*/
        table.children()
            .map { departureElementToDepartureObject(it) }
            .forEach { println(it) }
    }
    //println(results)
}

/**
 * <tr class="odd">
    <td colspan="5">
    <table class="trip">
    <colgroup>
    <col class="col1">
    <col class="col2">
    <col class="col3">
    <col class="col4">
    <col class="col5">
    </colgroup>
    <tbody>
    <tr>
    <td> <span> 00:49 </span> </td>
    <td class="icon"> <span class="mot-icon"> &nbsp; <span class="icon mot mot5" title="Stadtbus"></span> </span> <a href="javascript:void(0)" title="Alle Haltestellen mit Abfahrtszeiten" data-stopseq_linkdata="{&quot;line&quot;:&quot;wvv:10099:e:H:21a&quot;,&quot;stop&quot;:&quot;3700348&quot;,&quot;tripCode&quot;:&quot;1&quot;,&quot;date&quot;:&quot;20210823&quot;,&quot;time&quot;:&quot;00:49&quot;}" id="open-dlg-stop31CRDFQ9" onclick="beg.req_z_stopseq(this, '#dialog-open-dlg-stop31CRDFQ9')"> <span>99</span> </a> </td>
    <td> Hubl./ Mensa </td>
    <td class="dmPlatform"> <span></span> </td>
    </tr>
    <tr>
    <td class="additionalInfoRow" colspan="5"> <p class="box message">Nachtbus wegen Corona eingeschränkt</p> <p class="box message">Sperrung der Ludwigstraße / Bahnhofstraße in Fahrtrichtung Busbahnhof</p> <p class="box message">Fahrplanänderungen Busnetz+ ab 14.09.2021</p> </td>
    </tr>
    </tbody>
    </table> </td>
    </tr>
 */
fun departureElementToDepartureObject(element: Element): Departure? {
    var result: Departure? = null

    val tbody = element.getElementsByTag("tbody").first()
    val trs = tbody?.children()
    val firstRow = trs?.first()
    if (firstRow != null) {
        val tds = firstRow.getElementsByTag("td")
        if (tds.size == 4) {
            val time: String         = tds[0].text()  // includes delay
            val line: String         = tds[1].text()  // get more info about line here
            val destination: String  = tds[2].text()
            val platform: String     = tds[3].text()

            result = Departure(
                time = time,
                delay = "",
                line = line,
                destination = destination,
                platform = platform,
                additionalInfo = listOf(),
            )
        }

        if ((trs.size) > 2) {
            val secondRow = trs[1]
            /*
            example:
            <td class="additionalInfoRow" colspan="5">
                        <p class="box message">Nachtbus wegen Corona eingeschränkt</p>
                        <p class="box message">Sperrung der Ludwigstraße / Bahnhofstraße in Fahrtrichtung Busbahnhof</p>
                        <p class="box message">Fahrplanänderungen Busnetz+ ab 14.09.2021</p></td>
             */
            val infos: List<String>? = secondRow?.children()?.map { it.text() }
            if (infos != null) {
                result = result?.copy(additionalInfo = infos)
            }


        }

    }

    return result
}