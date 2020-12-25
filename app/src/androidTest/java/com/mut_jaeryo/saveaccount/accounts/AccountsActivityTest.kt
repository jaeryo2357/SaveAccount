package com.mut_jaeryo.saveaccount.accounts

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.mut_jaeryo.saveaccount.R
import com.mut_jaeryo.saveaccount.data.source.AccountRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class AccountsActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var accountRepository : AccountRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun clearTestAccounts() = runBlocking {
        accountRepository.deleteAllAccounts()
    }

    @Test
    fun createAccount() {
        val activityScenario = ActivityScenario.launch(AccountsActivity::class.java)

        onView(withId(R.id.add_account_fab)).perform(click())

        //click on the '+' button, add detail, save
        onView(withId(R.id.add_account_site_edit_text))
            .perform(typeText("testAccount"), closeSoftKeyboard())
        onView(withId(R.id.add_account_userid_edit_text))
            .perform(typeText("test"), closeSoftKeyboard())
        onView(withId(R.id.add_account_userpwd_edit_text)).perform(typeText("123"), closeSoftKeyboard())
        onView(withId(R.id.add_account_save_btn)).perform(click())

        Espresso.pressBack()
        // Then verify Account is displayed on screen
        onView(withText("testAccount")).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun createAccount_deleteAccount() {
        val activityScenario = ActivityScenario.launch(AccountsActivity::class.java)

        onView(withId(R.id.add_account_fab)).perform(click())

        //click on the '+' button, add detail, save
        onView(withId(R.id.add_account_site_edit_text))
            .perform(typeText("createAccount"), closeSoftKeyboard())
        onView(withId(R.id.add_account_userid_edit_text))
            .perform(typeText("lol"), closeSoftKeyboard())
        onView(withId(R.id.add_account_userpwd_edit_text)).perform(typeText("456"), closeSoftKeyboard())
        onView(withId(R.id.add_account_save_btn)).perform(click())

        Espresso.pressBack()

        onView(withText("createAccount")).perform(longClick())
        //show Delete Dialog, click delete button
        onView(withText(R.string.delete_dialog_action)).perform(click())

        onView(withText("createAccount")).check(doesNotExist())

        activityScenario.close()
    }
}