package com.unallapps.movieapp.Data

import com.unallapps.movieapp.Data.model.User

object Database {
    val users = mutableListOf<User>(
        User(1,  "mustafa@gmail.com", "123"),
        User(2,  "salih@gmail.com", "345"),
        User(3,  "oguz@gmail.com", "abc"),
        User(4,  "busra@gmail.com", "busra123"),
        User(5,  "elif@gmail.com", "cinar57"),
        User(6,  "aa", "aa")
    )

}