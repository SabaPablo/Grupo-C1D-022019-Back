package com.unq.viendasya.repository

import com.unq.viendasya.model.Menu
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<Menu, Int>