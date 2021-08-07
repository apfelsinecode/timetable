package io.github.apfelsinecode.timetable.bayernfahrplan

data class Departure(
    val time: String,
    val delay: Int,
    val line: String,
    val destination: String,
    val platform: String,
    val additionalInfo: List<String>
)
