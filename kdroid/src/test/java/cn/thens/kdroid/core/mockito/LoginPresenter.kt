package cn.thens.kdroid.core.mockito

class LoginPresenter(private val userManager: UserManager, private val validator: PasswordValidator) {
    fun login(username: String, password: String) {
        if (username.isEmpty()) return
        if (validator.verify(password)) return
        userManager.performLogin(username, password)
    }
}

class PasswordValidator {
    fun verify(password: String): Boolean {
        return "xiaochuang_is_handsome" == password
    }
}

class UserManager {
    fun performLogin(username: String, password: String) {
        println("username = $username, password = $password")
    }
}