package com.unq.viendasya.aux.impl

import com.unq.viendasya.model.Holidays
import com.unq.viendasya.aux.RestHolidaysAPI
import org.joda.time.LocalDateTime
import org.springframework.web.client.RestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod


class RestHolidaysAPIImple: RestHolidaysAPI {

    override fun isHoliday(date: LocalDateTime): Boolean {
        val holidays = getAllHolidays(date.year.toString())
        holidays?.forEach { holiday ->
            if(holiday.day == date.dayOfMonth && holiday.month == date.monthOfYear){
              return true
            }
        }
        return false
    }

    private fun getAllHolidays(year: String): List<Holidays>? {

        val restTemplate = RestTemplate()
        val response = restTemplate.exchange<List<Holidays>>(
                "http://nolaborables.com.ar/api/v2/feriados/$year",
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<Holidays>>() {

                })
        return response.body
    }
}