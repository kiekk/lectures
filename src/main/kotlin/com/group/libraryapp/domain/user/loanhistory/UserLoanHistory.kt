package com.group.libraryapp.domain.user.loanhistory

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.enums.user.UserLoanStatus
import com.group.libraryapp.enums.user.UserLoanStatus.LOANED
import com.group.libraryapp.enums.user.UserLoanStatus.RETURNED
import javax.persistence.*

@Entity
class UserLoanHistory(
    @ManyToOne
    val user: User,
    val bookName: String,
    @Enumerated(EnumType.STRING)
    var status: UserLoanStatus = LOANED,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    fun doReturn() {
        this.status = RETURNED
    }

    companion object {
        fun fixture(
            user: User,
            bookName: String = "운영체제",
            status: UserLoanStatus = LOANED,
            id: Long? = null,
        ): UserLoanHistory {
            return UserLoanHistory(
                user = user,
                bookName = bookName,
                status = status,
                id = id,
            )
        }
    }
}