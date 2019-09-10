package com.unq.viendasya.repository

import com.unq.viendasya.model.Provider
import org.springframework.data.jpa.repository.JpaRepository

interface ProviderRepository : JpaRepository<Provider, Int>