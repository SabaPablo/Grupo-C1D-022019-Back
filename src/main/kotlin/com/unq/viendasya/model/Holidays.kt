package com.unq.viendasya.model


import com.fasterxml.jackson.annotation.JsonProperty

data class Holidays(
    @JsonProperty("dia")
    val day: Int = 0,
    @JsonProperty("id")
    val id: String = "",
    @JsonProperty("mes")
    val month: Int = 0,
    @JsonProperty("motivo")
    val reason: String = "",
    @JsonProperty("original")
    val original: String = "",
    @JsonProperty("tipo")
    val type: String = ""
)