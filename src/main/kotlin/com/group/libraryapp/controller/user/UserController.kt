package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.service.user.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController(
    private val userService: UserService
) {
    @PostMapping
    fun saveUser(@RequestBody request: UserCreateRequest) {
        userService.saveUser(request)
    }

    @GetMapping
    fun getUsers(): List<UserResponse> {
        return userService.getUsers()
    }
    // 코틀린은 이런 형식의 코드도 가능하다.
//    fun getUsers(): List<UserResponse> = userService.getUsers()

    @PutMapping
    fun updateUserName(@RequestBody request: UserUpdateRequest) {
        userService.updateUserName(request)
    }

    @DeleteMapping
    fun deleteUser(@RequestParam name: String) {
        userService.deleteUser(name)
    }
}