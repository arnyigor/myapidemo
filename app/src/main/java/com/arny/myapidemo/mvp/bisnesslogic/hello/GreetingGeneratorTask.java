package com.arny.myapidemo.mvp.bisnesslogic.hello;

import android.os.AsyncTask;
// "Business logic" component
public class GreetingGeneratorTask extends AsyncTask<Void, Void, Integer> {

	// Callback - Listener
	public interface GreetingTaskListener{
		public void onGreetingGenerated(String greetingText);
	}

	private String baseText;
	private GreetingTaskListener listener;

	public GreetingGeneratorTask(String baseText, GreetingTaskListener listener){
		this.baseText = baseText;
		this.listener = listener;
	}

	// Simulates computing and returns a random integer
	@Override
	protected Integer doInBackground(Void... params) {
		try {
			Thread.sleep(2000); // Simulate computing
		} catch (InterruptedException e) { }

		return (int) (Math.random() * 100);
	}

	@Override
	protected void onPostExecute(Integer randomInt){
		listener.onGreetingGenerated(baseText + " "+randomInt);
	}
}