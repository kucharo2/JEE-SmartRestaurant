package cz.kucharo2.rest.model;

import java.util.List;

/**
 * Object for form endpoint response.
 *
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
public class FormResponse {

    private Object responseData;
    private List<FieldError> formErrors;

    public FormResponse(Object responseData, List<FieldError> formErrors) {
        this.responseData = responseData;
        this.formErrors = formErrors;
    }

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    public List<FieldError> getFormErrors() {
        return formErrors;
    }

    public void setFormErrors(List<FieldError> formErrors) {
        this.formErrors = formErrors;
    }
}
