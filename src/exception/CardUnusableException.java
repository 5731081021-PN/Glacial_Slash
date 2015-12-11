package exception;

public class CardUnusableException extends Exception {

	private static final long serialVersionUID = 6541084900664503268L;
	
	private UnusableType type;
	
	public CardUnusableException(UnusableType type) {
		this.type = type;
	}
	
	public int getTypeNumber() {
		return type.typeNumber;
	}
	
	public String getMessage() {
		return type.message;
	}
	
	public static enum UnusableType {
		
		NO_SUCH_CARD_IN_HAND (1, "No such card in hand"),
		NOT_ENOUGH_MANA (2, "Not enough mana");
		
		private int typeNumber;
		private String message;
		
		private UnusableType(int typeNumber, String message) {
			this.typeNumber = typeNumber;
			this.message = message;
		}

	}

}
