package control;

import java.io.Serializable;

public class Time implements Serializable {
	private static final long serialVersionUID = 1L;
	private long start;
	
	public Time() {
		this.start = System.currentTimeMillis();
	}
	
	public long getPastTime() {
		return System.currentTimeMillis() - start;
	}
	
	public long toSeconds() {
		return getPastTime() / 1000;
	}
	
	public void restart() {
		start = System.currentTimeMillis();
	}
}
