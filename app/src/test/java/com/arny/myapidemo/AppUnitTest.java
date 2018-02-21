package com.arny.myapidemo;

import com.arny.arnylib.utils.Params;
import com.arny.arnylib.utils.Utility;
import com.arny.myapidemo.domain.executor.Executor;
import com.arny.myapidemo.domain.executor.MainThread;
import com.arny.myapidemo.domain.interactors.WelcomeInteractor;
import com.arny.myapidemo.domain.interactors.impl.WelcomingInteractorImpl;
import com.arny.myapidemo.domain.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AppUnitTest {
	private MainThread mMainThread;
	@Mock private Executor mExecutor;
	@Mock private MessageRepository mMessageRepository;
	@Mock private WelcomeInteractor.Callback mMockedCallback;


	@Test
	public void emails_isCorrect() throws Exception {
        List<String> list = Arrays.asList("","4844","@","a@m.r","a@.ru");
        for (String s : list) {
            assertTrue(!Utility.isEmailValid(s));
        }
    }

    @Test
    public void test_is_empty() throws Exception {
        assertTrue(Utility.empty(""));
        assertTrue(Utility.empty(null));
        assertTrue(Utility.empty("null"));
    }

	@Test
	public void testWelcomeMessageFound() throws Exception {

		String msg = "Welcome, friend!";
		when(mMessageRepository.getWelcomeMessage())
				.thenReturn(msg);

		WelcomingInteractorImpl interactor = new WelcomingInteractorImpl(
				mExecutor,
				mMainThread,
				mMockedCallback,
				mMessageRepository
		);
		interactor.run();
		Mockito.verify(mMessageRepository).getWelcomeMessage();
		Mockito.verifyNoMoreInteractions(mMessageRepository);
		Mockito.verify(mMockedCallback).onMessageShow(msg);
	}
}