package com.unq.viendasya.service.imple

import com.unq.viendasya.service.RestHolidaysAPI
import org.joda.time.LocalDateTime

class HolidayApiFakeAllNonHoliday: RestHolidaysAPI {
    override fun isHoliday(date: LocalDateTime): Boolean = false
}