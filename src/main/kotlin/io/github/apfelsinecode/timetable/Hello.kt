package io.github.apfelsinecode.timetable

import org.jsoup.Jsoup
import java.net.URL
// import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    val document = Jsoup.connect("https://apfelsinecode.github.io").get()
    println(document)
    println(document.title())
    println(document.body().classNames())

}