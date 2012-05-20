package exceptions;

/**
 * The exception that is thrown when a non-fatal workflow error occurs
 */
public class WorkflowException extends Exception {
    /**
     * Initializes a new instance of the WorkflowException class
     */
    public WorkflowException() {
    }

    /**
     * Initializes a new instance of the WorkflowException class with a specified error message.
     *
     * @param message
     */
    public WorkflowException(String... message) {
    }

    /**
     * Initializes a new instance of the WorkflowException class with a specified error message and inner exception.
     *
     * @param message error message
     * @param inner   inner exception
     */
    public WorkflowException(String message, Exception inner) {
    }

    /**
     * Initializes a new instance of the WorkflowException class with a error message template.
     *
     * @param template message template
     * @param args     template parameters
     */
    public WorkflowException(String template, Object[] args) {
    }

}
