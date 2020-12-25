package com.mut_jaeryo.saveaccount

import android.content.Context
import android.content.Intent

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class CategoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var device: UiDevice

    @Before
    fun startAccountActivityFromHomeScreen() {
        device = UiDevice.getInstance(getInstrumentation())

        //Home Screen에서 시작
        device.pressHome()

        //SaveAccount 앱 실행
        val context: Context = getApplicationContext()
        val intent = context.packageManager
            .getLaunchIntentForPackage(BASIC_PACKAGE)
        intent?.run {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(this)
        }

        //앱이 화면에 표시 될때까지 대기
        device.wait(Until.hasObject(By.pkg(BASIC_PACKAGE).depth(0)), LANCH_TIMEOUT)
    }

    @Test
    fun checkCategory_configurationChange() {
        //Before 클래스에 의해 현재 화면은 AccountsActivity
        device.findObject(By.res(BASIC_PACKAGE, "add_account_fab"))
            .click()
        //앱이 화면에 표시 될때까지 대기
        device.wait(Until.hasObject(By.pkg(BASIC_PACKAGE).depth(0)), LANCH_TIMEOUT)

        //아래 코드부터 화면은 AddEditAccountActivity
        device.findObject(By.res(BASIC_PACKAGE, "category_card"))
            .click()

        //앱이 화면에 표시 될때까지 대기
        device.wait(Until.hasObject(By.pkg(BASIC_PACKAGE).depth(0)), LANCH_TIMEOUT)

        val studyCategory = getApplicationContext<Context>().getString(R.string.category_study)

        device.findObject(
            UiSelector().text(studyCategory)
                .className("android.widget.TextView"))
            .click()

        //변경되었는지 확인
        var categoryText : UiObject2 =  device.findObject(By.res(BASIC_PACKAGE, "selected_category"))
        assertEquals(categoryText.text, studyCategory)

        device.setOrientationLeft()
        //앱이 화면에 표시 될때까지 대기
        device.wait(Until.hasObject(By.pkg(BASIC_PACKAGE).depth(0)), LANCH_TIMEOUT)

        //이전에 변경된 카테고리가 유지되었는지 확인
        categoryText = device.findObject(By.res(BASIC_PACKAGE, "selected_category"))

        assertEquals(categoryText.text, studyCategory)

    }


    companion object {
        val BASIC_PACKAGE = "com.mut_jaeryo.saveaccount"
        val LANCH_TIMEOUT = 5000L
    }
}