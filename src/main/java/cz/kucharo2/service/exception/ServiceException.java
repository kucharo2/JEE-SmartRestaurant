package cz.kucharo2.service.exception;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
