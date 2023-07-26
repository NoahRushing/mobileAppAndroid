package edu.ucsc.cse118.assignment3


import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions.typeTo
import edu.ucsc.cse118.assignment3.TestHelper.waitForText

import org.junit.Test
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AdvancedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private fun login() {
        typeTo(R.id.email, "molly@cse118.com")
        typeTo(R.id.password, "molly")
        clickOn("LOGIN")
    }
    private fun construction() {
        login()
        waitForText("Construction")
        clickOn("Construction")
    }
    private fun electrical() {
        construction()
        waitForText("Electrical")
        clickOn("Electrical")
    }
    private fun doors() {
        construction()
        waitForText("Doors, Frames & Hardware")
        clickOn("Doors, Frames & Hardware")
    }
    @Test
    fun construction_channel_message_count() {
        construction()
        waitForText("8 Messages")
    }
    @Test
    fun construction_channel_message_count2() {
        construction()
        waitForText("16 Messages")
    }
    @Test
    fun electrical_new_message() {
        electrical()
        clickOn(R.id.fab)
        waitForText("ADD")
    }
    @Test
    fun electrical_new_message2() {
        electrical()
        clickOn(R.id.fab)
        waitForText("New Message")
    }
    @Test
    fun electrical_new_messageTyping() {
        electrical()
        clickOn(R.id.fab)
        typeTo(R.id.content, "Adding a new message 123 123")
        clickOn("ADD")
        waitForText("Molly Member")
        waitForText("Adding a new message 123 123")
    }
    @Test
    fun electrical_new_messageTyping2() {
        electrical()
        clickOn(R.id.fab)
        typeTo(R.id.content, "Adding a new message test test")
        clickOn("ADD")
        waitForText("Molly Member")
        waitForText("Adding a new message test test")
    }
    @Test
    fun doors_new_messageTyping() {
        doors()
        clickOn(R.id.fab)
        typeTo(R.id.content, "Adding a new message test test")
        clickOn("ADD")
        waitForText("Molly Member")
        waitForText("Adding a new message test test")
    }
    @Test
    fun doors_new_messageTyping2() {
        doors()
        clickOn(R.id.fab)
        typeTo(R.id.content, "Adding a new message test test")
        clickOn("ADD")
        waitForText("Molly Member")
        waitForText("Adding a new message test test")
    }
    @Test
    fun doors_new_messageTyping3() {
        doors()
        clickOn(R.id.fab)
        typeTo(R.id.content, "BBBBBBBBBBBBBBBBBBBB BBB")
        clickOn("ADD")
        waitForText("Message Created")
    }
    @Test
    fun doors_new_messageFail() {
        doors()
        clickOn(R.id.fab)
        typeTo(R.id.content, "BBBBBBB")
        clickOn("ADD")
        waitForText("New Message")
    }
    @Test
    fun doors_new_messageFail2() {
        doors()
        clickOn(R.id.fab)
        typeTo(R.id.content, "AAA")
        clickOn("ADD")
        waitForText("ADD")
    }
    @Test
    fun doors_new_messageFail3() {
        electrical()
        clickOn(R.id.fab)
        typeTo(R.id.content, "Too short")
        clickOn("ADD")
        waitForText("ADD")
    }


}