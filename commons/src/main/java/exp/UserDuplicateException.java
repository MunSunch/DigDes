package exp;

public class UserDuplicateException extends Exception {
    public UserDuplicateException() {
        super("Пользователь уже существует");
    }
}
