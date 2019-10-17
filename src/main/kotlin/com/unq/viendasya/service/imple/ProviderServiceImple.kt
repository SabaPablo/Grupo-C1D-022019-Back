package com.unq.viendasya.service.imple

import com.unq.viendasya.controller.apiModels.MiniProvider
import com.unq.viendasya.model.Provider
import com.unq.viendasya.repository.ProviderRepository
import com.unq.viendasya.service.ProviderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service

class ProviderServiceImple(@Autowired val dao: ProviderRepository): ProviderService {

    override fun findAll(pageable: Pageable): Page<Provider> {
        return dao.findAll(pageable)
    }

    override fun createProvider(data: MiniProvider): Provider {
        val provider = Provider.Builder().name(data.name).mail(data.mail).build()
        return dao.save(provider)
    }


}