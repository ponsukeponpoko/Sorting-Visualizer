package Sort;

public class Exceptions {
	@SuppressWarnings("serial")
	static class InvalidInputException extends Exception {
		public InvalidInputException(String message) {
			super(message);
		}
	}
	
	@SuppressWarnings("serial")
	static class NullModeException extends Exception {
		public NullModeException(String message) {
			super(message);
		}
	}
	
	@SuppressWarnings("serial")
	static class NotPowerOfTwoException extends Exception{
		public NotPowerOfTwoException(String message) {
			super(message);
		}
	}
	
	@SuppressWarnings("serial")
	static class NotCorrectlySortedException extends Exception{
		public NotCorrectlySortedException(String message) {
			super(message);
		}
	}
	
	@SuppressWarnings("serial")
	static class NoExceptionCaught extends Exception{
		public NoExceptionCaught(String message) {
			super(message);
		}
	}
}
