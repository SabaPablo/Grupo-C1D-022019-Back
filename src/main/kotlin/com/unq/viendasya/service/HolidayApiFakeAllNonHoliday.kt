package com.unq.viendasya.service

import org.joda.time.LocalDateTime

class HolidayApiFakeAllNonHoliday: RestHolidaysAPI {
    override fun isHoliday(date: LocalDateTime): Boolean = false
}