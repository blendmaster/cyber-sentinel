package sate.cybersentinel.message;

/** 
 * Represents a 3 dimensional location, when using the virtual world. The location is
 * immutable. If no coordinates are specified, defaults to (0, 0, 0).
 * 
 * @author Jared Hance
 */
public class Location {
	private double x;
	private double y;
	private double z;
	
	/**
	 * Creates a new Location at (0, 0, 0)
	 */
	public Location() {
		this(0, 0, 0);
	}
	
	/**
	 * Creates a Location using the specified coordinates
	 * @param x The X-coordinate
	 * @param y The Y-coordinate
	 * @param z The Z-coordinate
	 */
	public Location(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	 * if one person can hear another within the virtual world.
	 * @return The distance between the two locations.
	 */
	public static double distance(Location l1, Location l2) {
		double dx = l1.x - l2.x;
		double dy = l1.y - l2.y;
		double dz = l1.z - l2.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
}
