package com.unq.viendasya.controller

import com.unq.viendasya.model.UserModel
import com.unq.viendasya.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UserController(@Autowired private val  userRepository : UserRepository) {


    @GetMapping("/users")
    fun getUsers(pageable : Pageable) : Page<UserModel> {
        return userRepository.findAll(pageable)
    }

    @PostMapping("/users")
    fun createUser(@Valid @RequestBody user : UserModel) : UserModel {
        return userRepository.save(user)
    }

    @GetMapping("/test")
    fun test() : Int {
        return 1
    }
}