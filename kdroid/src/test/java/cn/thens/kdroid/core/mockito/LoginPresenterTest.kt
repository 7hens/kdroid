package cn.thens.kdroid.core.mockito

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class LoginPresenterTest {
    @Spy
    lateinit var userManager: UserManager

    @Mock
    lateinit var validator: PasswordValidator

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testLogin() {
        `when`(validator.verify(anyString())).thenReturn(false)
        val loginPresenter = LoginPresenter(userManager, validator)
        loginPresenter.login("Jack", "123456")
        verify(userManager).performLogin("Jack", "123456")
        verify(userManager).performLogin(eq("Jack") ?: "", anyString())
    }
}