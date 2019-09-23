package com.unq.viendasya.service

import org.joda.time.LocalDateTime

class HolidayApiFakeAllHoliday: RestHolidaysAPI {
    override fun isHoliday(date: LocalDateTime): Boolean = true
}