package com.unq.viendasya.service

import com.unq.viendasya.controller.apiModels.MiniProvider
import com.unq.viendasya.model.Provider
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProviderService {
    fun createProvider(data: MiniProvider): Provider
    fun findAll(pageable: Pageable): Page<Provider>
}