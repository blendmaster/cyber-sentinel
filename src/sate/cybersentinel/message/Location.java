package sate.cybersentinel.message;

/** 
 * Represents a 3 dimensional location, when using the virtual world. The location is
 * immutable. If no coordinates are specified, defaults to (0, 0, 0).
 * 
 * @author Jared Hance
 */
public class Location {
	private String region;
	private double x;
	private double y;
	private double z;
	
	/**
	 * Creates a new Location at (0, 0, 0)
	 * @param region The region from which the chat comes
	 */
	public Location(String region) {
		this(region, 0, 0, 0);
	}
	
	/**
	 * Creates a Location using the specified coordinates
	 * @param region The region from which the chat comes
	 * @param x The X-coordinate
	 * @param y The Y-coordinate
	 * @param z The Z-coordinate
	 */
	public Location(String region, double x, double y, double z) {
		this.region = region;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * The region determines who can hear who; two people who are not in the same channel
	 * can not communicate this way.
	 * @return
	 */
	public String getRegion() {
		return region;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	/**
	 * Computes the distance between two Locations. This is used in order to determine
	 * if one person can hear another within the virtual world. This is only useful if the
	 * two locations are in the same region.
	 * @return The distance between the two locations.
	 */
	public static double distance(Location l1, Location l2) {
		double dx = l1.x - l2.x;
		double dy = l1.y - l2.y;
		double dz = l1.z - l2.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
}
