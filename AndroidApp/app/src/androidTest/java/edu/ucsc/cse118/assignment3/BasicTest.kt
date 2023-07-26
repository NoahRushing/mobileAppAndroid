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
class BasicTest {
  /**
   * Create and launch the activity under test before each test,
   * and close it after each test.
   */
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
  private fun framing() {
    construction()
    waitForText("Framing (Wood)")
    clickOn("Framing (Wood)")
  }
  private fun movies() {
    login()
    waitForText("Movies")
    clickOn("Movies")
  }

  @Test
  fun header() {
    login()
    waitForText("Molly Member")
  }
  @Test
  fun workspace_construction() {
    login()
    waitForText("Construction")
  }
  @Test
  fun construction_channel_count() {
    login()
    waitForText("10 Channels")
  }
  @Test
  fun construction_channel_framing() {
    construction()
    waitForText("Framing (Wood)")
  }
  @Test
  fun construction_channel_framing_message_ranice() {
    framing()
    waitForText("Ranice Desantis")
  }
  @Test
  fun construction_channel_framing_message_ranice_date() {
    framing()
    waitForText("Jul 21, 2022, 10:42:36 AM")
  }
  @Test
  fun workspace_movies() {
    login()
    waitForText("Movies")
  }
  @Test
  fun movies_channel_count() {
    login()
    waitForText("3 Channels")
  }
  @Test
  fun movies_channel_drama() {
    movies()
    waitForText("Drama")
  }
}