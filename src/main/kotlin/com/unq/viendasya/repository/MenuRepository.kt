package com.unq.viendasya.repository

import com.unq.viendasya.model.Menu
import org.springframework.data.jpa.repository.JpaRepository
import javax.transaction.Transactional

@Transactional(Transactional.TxType.MANDATORY)
interface MenuRepository : JpaRepository<Menu, Int>{
}