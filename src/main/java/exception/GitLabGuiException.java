package exception;

import java.util.Optional;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class GitLabGuiException extends RuntimeException {
    public GitLabGuiException(final String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public GitLabGuiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message1
     * @param message2
     * @param cause
     */
    public GitLabGuiException(String message1, String message2, Throwable cause) {
        super(Optional.ofNullable(message1).orElse("") + Optional.ofNullable(message2).orElse(""), cause);
    }
}
