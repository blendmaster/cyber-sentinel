package sate.cybersentinel.input;

public class ClientState {
	public enum ReceptionType {
		BLOCK,
		BLOCK_LENGTH,
		SESSION,
		SESSION_LENGTH
	}
	
	private int length;
	private ReceptionType receptionType;
	private Session session;
	
	private ClientState(ReceptionType receptionType) {
		this.receptionType = receptionType;
	}
	
	private ClientState(ReceptionType receptionType, int length) {
		this.receptionType = receptionType;
		this.length = length;
	}
	
	private ClientState(ReceptionType receptionType, Session session) {
		this.receptionType = receptionType;
		this.session = session;
	}
	
	private ClientState(ReceptionType receptionType, Session session, int length) {
		this.receptionType = receptionType;
		this.session = session;
		this.length = length;
	}
	
	public static ClientState block(Session session, int length) {
		return new ClientState(ReceptionType.BLOCK, length);
	}
	
	public static ClientState blockLength(Session session) {
		return new ClientState(ReceptionType.BLOCK_LENGTH);
	}
	
	public static ClientState session(int length) {
		return new ClientState(ReceptionType.SESSION, length);
	}
	
	public static ClientState sessionLength() {
		return new ClientState(ReceptionType.SESSION_LENGTH);
	}
	
	public ReceptionType getReceptionType() {
		return this.receptionType;
	}
	
	public Session getSession() {
		return this.session;
	}
	
	public int getLength() {
		return this.length;
	}
}
