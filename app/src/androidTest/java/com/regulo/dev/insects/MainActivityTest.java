package com.regulo.dev.insects;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.regulo.dev.insects.data.Insect;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by regulosarmiento on 30/05/2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private Insect mInsect = new Insect("Black Widow", "Latrodectus mactans", "Arachnida", "spider.png", 10);

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void shouldDisplayInsectDetailsOnInsectDetailsActivity() {
        onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //Displays name
        onView(withId(R.id.commonNameView)).check(matches(withText(mInsect.name)));

        //Displays scientific name.
        onView(withId(R.id.scientificNameView)).check(matches(withText(mInsect.scientificName)));

        //Displays classification
        onView(withId(R.id.classification)).check(matches(withText(String.format(getResourceString(R.string.classification), mInsect.classification))));
    }


    @Test
    public void shouldDisplayQuizActivityWhenFabButtonIsClicked(){
        //Click on the fab button
        onView(withId(R.id.fab)).perform(click());

        //Displays question
        onView(withId(R.id.text_question)).check(matches(isDisplayed()));

        //Displays answers
        onView(withId(R.id.answer_select)).check(matches(isDisplayed()));

        //Displays result
        onView(withId(R.id.text_correct)).check(matches(isDisplayed()));

    }

    private String getResourceString(int id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources().getString(id);
    }
}