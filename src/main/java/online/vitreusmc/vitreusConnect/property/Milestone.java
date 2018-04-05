package online.vitreusmc.vitreusConnect.property;

public enum Milestone {
	HOUR_12 (12),
	HOUR_24 (24),
	HOUR_100 (100),
	HOUR_200 (200),
	HOUR_300 (300),
	HOUR_400 (400),
	HOUR_500 (500);
	
	private int hoursRequired;
	
	Milestone(int hoursRequired) {
		this.hoursRequired = hoursRequired;
	}
	
	public int getHoursRequired() {
		return hoursRequired;
	}
	
	public int getMinutesRequired() {
		return hoursRequired * 60;
	}
	
	public int getSecondsRequired() {
		return getMinutesRequired() * 60;
	}
	
	public int getTicksRequired() {
		return getSecondsRequired() * 20;
	}
}