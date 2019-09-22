package com.unq.viendasya.service

import org.joda.time.LocalDateTime

interface RestHolidaysAPI {
    fun isHoliday(date: LocalDateTime) : Boolean
}