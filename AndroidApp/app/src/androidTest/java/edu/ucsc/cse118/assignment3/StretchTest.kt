package edu.ucsc.cse118.assignment3

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions.typeTo
import edu.ucsc.cse118.assignment3.TestHelper.waitForText
import edu.ucsc.cse118.assignment3.TestHelper.waitForNoText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class StretchTest {

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
    fun electrical_delete_new_message() {
        electrical()
        onView((withId(R.id.recyclerview))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeRight()))
        waitForText("No")
    }
    @Test
    fun electrical_delete_new_message2() {
        electrical()
        onView((withId(R.id.recyclerview))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeRight()))
        waitForText("Yes")
    }
    @Test
    fun electrical_delete_new_message3() {
        electrical()
        clickOn(R.id.fab)
        typeTo(R.id.content, "test test test test test test")
        clickOn("ADD")
        onView((withId(R.id.recyclerview))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeRight()))
        clickOn("Yes")
        waitForNoText("test test test test test test")
    }
    @Test
    fun electrical_dont_delete() {
        electrical()
        clickOn(R.id.fab)
        typeTo(R.id.content, "Adding a new message 123 123")
        clickOn("ADD")
        onView((withId(R.id.recyclerview))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeRight()))
        clickOn("No")
        waitForText("Adding a new message 123 123")
    }
    @Test
    fun doors_delete_new_message() {
        doors()
        onView((withId(R.id.recyclerview))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeRight()))
        waitForText("No")
    }
    @Test
    fun doors_delete_new_message2() {
        doors()
        onView((withId(R.id.recyclerview))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeRight()))
        waitForText("Yes")
    }
    @Test
    fun doors_delete_new_message3() {
        doors()
        clickOn(R.id.fab)
        typeTo(R.id.content, "DDDDDDDDDDDDDDDDDDDDDDD")
        clickOn("ADD")
        onView((withId(R.id.recyclerview))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeRight()))
        clickOn("Yes")
        waitForNoText("DDDDDDDDDDDDDDDDDDDDDDD")
    }
    @Test
    fun doors_dont_delete() {
        doors()
        clickOn(R.id.fab)
        typeTo(R.id.content, "Adding a new message 123 123")
        clickOn("ADD")
        onView((withId(R.id.recyclerview))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeRight()))
        clickOn("No")
        waitForText("Adding a new message 123 123")
    }

}