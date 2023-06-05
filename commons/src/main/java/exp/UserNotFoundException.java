package exp;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("Пользователя не существует");
    }
}
