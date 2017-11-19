package cz.kucharo2.rest.model;

/**
 * Object that holds form identification of form field error.
 *
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class FieldError {

    private String fieldName;
    private String errorMessage;

    public FieldError(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
