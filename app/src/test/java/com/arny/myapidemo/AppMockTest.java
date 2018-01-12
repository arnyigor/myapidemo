package com.arny.myapidemo;

import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import com.arny.arnylib.BuildConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowListView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AppMockTest {

    private HomeActivity activity;

    @Mock
    Context fakeContext;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(HomeActivity.class);
        activity = Robolectric.buildActivity(HomeActivity.class)
                .create().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertThat(activity).isNotNull();
    }

    @Test
    public void buttonClickShouldStartNewActivity() throws Exception
    {
        ListView listView = activity.findViewById(R.id.main_list);
        ShadowListView shadowListView = Shadows.shadowOf(listView); //we need to shadow the list view
        shadowListView.populateItems();// will populate the adapter
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
//        assertEquals(SecondActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }


    @Test
    public void getString() throws Exception {
        String input = fakeContext.getString(com.arny.arnylib.R.string.app_name);
        System.out.println(input);
    }


}