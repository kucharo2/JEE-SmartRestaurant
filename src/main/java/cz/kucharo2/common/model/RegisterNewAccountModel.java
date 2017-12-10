package cz.kucharo2.common.model;

import cz.kucharo2.common.constants.ErrorValidationMessages;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
public class RegisterNewAccountModel {
    @NotNull(message = ErrorValidationMessages.ERR_EMPTY_USERNAME)
    @Size(min = 6, message = ErrorValidationMessages.ERR_LENGTH_USERNAME)
    private String username;

    @NotNull(message = ErrorValidationMessages.ERR_EMPTY_PASSWORD)
    @Size(min = 6, message = ErrorValidationMessages.ERR_LENGTH_PASSWORD)
    private String password;

    private String firstName;

    private String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$", message = ErrorValidationMessages.ERR_BAD_FORMAT_EMAIL)
    private String email;

    @NotNull(message = ErrorValidationMessages.ERR_EMPTY_PHONE)
    @Pattern(regexp = "(\\+[0-9]{2,3})? ?[0-9]{3} ?[0-9]{3} ?[0-9]{3}$", message = ErrorValidationMessages.ERR_BAD_FORMAT_PHONE)
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
