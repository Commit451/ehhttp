package com.commit451.ehhttp

import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

class TestTest {

    companion object {
        val AM_PM_DATE_FORMATTER: DateTimeFormatter by lazy {
            DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("h:mm a")
                    .toFormatter(Locale.US)
        }
    }

    @Test
    fun test() {
        val date = "06:23 PM"
        val localDate = LocalDate.of(1998, 1, 11)
        val timeZone = ZoneId.systemDefault()
        val zonedDateTime = ZonedDateTime.of(localDate.atStartOfDay(), timeZone)
        val instant = AM_PM_DATE_FORMATTER.parse(date)
        zonedDateTime.withHour(instant.get(ChronoField.HOUR_OF_DAY))
        zonedDateTime.withMinute(instant.get(ChronoField.MINUTE_OF_HOUR))
    }
}