package com.unq.viendasya.aux

import org.joda.time.LocalDateTime

interface RestHolidaysAPI {
    fun isHoliday(date: LocalDateTime) : Boolean
}