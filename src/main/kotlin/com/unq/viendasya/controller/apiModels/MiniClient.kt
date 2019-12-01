package com.unq.viendasya.controller.apiModels

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class MiniClient(
        @field:NotBlank
        var name: String,
                 var lastName: String,
                 @field:NotNull
                @field:Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
                 var mail: String,
                 var password: String,
                 var address: String,
                 var phone: String,
                 var city: String,
                 var zip: String,
                 var country: String)