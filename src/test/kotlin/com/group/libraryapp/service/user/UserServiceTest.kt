package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    /*
    현재 getUsersTest 는 실패한다.
    saveUserTest 에서 이미 User 하나를 추가했기 때문에
    getUsersTest 에서 추가한 2명이 아닌 총 3명이 조회되어 테스트 실패
    각 테스트별로 테스트 종료 후 데이터를 초기화 해줄 필요가 있다.
     */
    @Test
    fun saveUserTest() {
        // given
        val request = UserCreateRequest("soono", null)

        // when
        userService.saveUser(request)

        // then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("soono")
        assertThat(results[0].age).isNull()
    }

    @Test
    fun getUsersTest() {
        // given
        userRepository.saveAll(
            listOf(
                User("A", 20),
                User("B", null)
            )
        )

        // when
        val results = userService.getUsers()

        // then
        assertThat(results).hasSize(2) // [UserResponse(), UserResponse()
        assertThat(results)
            .extracting("name") // ["A", "B"]
            .containsExactlyInAnyOrder("A", "B")

        assertThat(results)
            .extracting("age") // [20, null]
            .containsExactlyInAnyOrder(20, null)
    }

}