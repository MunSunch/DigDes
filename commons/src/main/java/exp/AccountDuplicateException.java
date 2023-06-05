package exp;

public class AccountDuplicateException extends Exception{
    public AccountDuplicateException() {
        super("Учетная запись уже существует");
    }
}
