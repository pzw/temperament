package temperament.musical;

public class WellKnownInterval {
	private double	ratio;
	private String	name;

	public WellKnownInterval(double ratio, String name) {
		this.ratio = ratio;
		this.name = name;
	}
	
	public double getRatio() {
		return ratio;
	}
	
	public String getName() {
		return name;
	}
}
