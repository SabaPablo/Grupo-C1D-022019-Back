package com.unq.viendasya.aux.impl

import com.unq.viendasya.aux.RestHolidaysAPI
import org.joda.time.LocalDateTime

class HolidayApiFakeAllNonHoliday: RestHolidaysAPI {
    override fun isHoliday(date: LocalDateTime): Boolean = false
}