package com.unq.viendasya.controller

import com.unq.viendasya.controller.apiModels.MiniClient
import com.unq.viendasya.controller.apiModels.MiniProvider
import com.unq.viendasya.model.Client
import com.unq.viendasya.model.Provider
import com.unq.viendasya.service.ProviderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class ProviderController(@Autowired val providerService: ProviderService) {

    @GetMapping("/providers")
    fun getProviders(pageable : Pageable) : Page<Provider> {
        return providerService.findAll(pageable)
    }

    @PostMapping("/providers")
    fun createProvider(@Valid @RequestBody provider : MiniProvider): Provider {
        return providerService.createProvider(provider)
    }

}