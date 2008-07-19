package no.knubo.bok.client.util;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SuggestOracle;

public abstract class DelayedServerOracle extends SuggestOracle {

	protected Request currentRequest;
	protected Callback currentCallback;
	private Timer timer;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void requestSuggestions(Request request, Callback callback) {
		this.currentRequest = request;
		this.currentCallback = callback;

		startTimer();
	}

	abstract public void fetchSuggestions();

	private void startTimer() {
		if (timer == null) {
			timer = new Timer() {

				@Override
				public void run() {
					fetchSuggestions();
					timer = null;
				}

			};
			timer.schedule(500);
		}
	}

}