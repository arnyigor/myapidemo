package com.arny.myapidemo.mvp.view.hello;

import com.hannesdorfmann.mosby.mvp.MvpView;
public interface HelloWorldView extends MvpView {
	// displays "Hello" greeting text in red text color
	void showHello(String greetingText);

	// displays "Goodbye" greeting text in blue text color
	void showGoodbye(String greetingText);
}
