package io.github.apfelsinecode.timetable

import java.net.URL
// import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    println("Hello World")
    val coolWebsite = URL("https://apfelsinecode.github.io")
    val content = coolWebsite.readText()
    println(content)
    // val docBuildFactory = DocumentBuilderFactory.newInstance()
    // val builder = docBuildFactory.newDocumentBuilder()
    // val document = builder.parse(coolWebsite.openStream())
    // println(document)
}