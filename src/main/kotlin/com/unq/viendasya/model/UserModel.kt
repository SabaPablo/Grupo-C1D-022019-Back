package com.unq.viendasya.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users")
class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id :Int = 0

    @NotBlank
    var name : String = ""

}