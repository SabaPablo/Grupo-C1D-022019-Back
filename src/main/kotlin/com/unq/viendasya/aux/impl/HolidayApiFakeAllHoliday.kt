package com.unq.viendasya.aux.impl

import com.unq.viendasya.aux.RestHolidaysAPI
import org.joda.time.LocalDateTime

class HolidayApiFakeAllHoliday: RestHolidaysAPI {
    override fun isHoliday(date: LocalDateTime): Boolean = true
}