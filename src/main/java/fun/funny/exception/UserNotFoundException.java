package fun.funny.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("Given userId " + userId + " is not found");
    }
    public UserNotFoundException(String userName) {
        super("Given userMame " + userName + " is not found");
    }
}
