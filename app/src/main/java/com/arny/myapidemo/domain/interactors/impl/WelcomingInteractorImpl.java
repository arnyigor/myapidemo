package com.arny.myapidemo.domain.interactors.impl;

import com.arny.myapidemo.domain.executor.Executor;
import com.arny.myapidemo.domain.executor.MainThread;
import com.arny.myapidemo.domain.interactors.WelcomeInteractor;
import com.arny.myapidemo.domain.interactors.base.AbstractInteractor;
import com.arny.myapidemo.domain.repository.MessageRepository;
public class WelcomingInteractorImpl extends AbstractInteractor implements WelcomeInteractor {

	private WelcomeInteractor.Callback mCallback;
	private MessageRepository mRepository;

	public WelcomingInteractorImpl(Executor threadExecutor, MainThread mainThread,Callback callback, MessageRepository messageRepository) {
		super(threadExecutor, mainThread);
		mCallback = callback;
		mRepository = messageRepository;
	}

	private void notifyError() {
		mMainThread.post(() -> mCallback.onShowFailed("Nothing to welcome you with :("));
	}

	private void postMessage(final String msg) {
		mMainThread.post(() -> mCallback.onMessageShow(msg));
	}

	@Override
	public void run() {
		// получение сообщения
		final String message = mRepository.getWelcomeMessage();
		// проверяем, получили ли мы сообщение
		if (message == null || message.length() == 0) {
			// уведомляем об ошибке основной поток
			notifyError();
			return;
		}
		// мы получили наше сообщение, уведомляем об этом UI в основном потоке
		postMessage(message);
	}
}