package StudentHostelFeesSystem;

class AlphabetWithNumberException extends Exception {
    AlphabetWithNumberException(String messages) {
        super(messages);
    }
}
class EmailException extends Exception {
    EmailException(String message) {
        super(message);
    }
}
class PhoneNumberException extends Exception {
    PhoneNumberException(String message) {
        super(message);
    }
}