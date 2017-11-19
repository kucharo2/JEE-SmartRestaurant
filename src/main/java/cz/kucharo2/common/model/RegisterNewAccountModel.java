package cz.kucharo2.common.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
public class RegisterNewAccountModel {
    @NotNull(message = "Username must be fill")
    private String username;

    @NotNull(message = "Password must be fill")
    @Size(min = 6, message = "Size of password must be minimum 6")
    private String password;

    private String firstName;

    private String lastName;

    @Size(min = 6, message = "Size of email must be minimum 6")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$", message = "The specified email has bad format. Please enter in a similar format a@a.cz")
    private String email;

    @NotNull(message = "Phone must be fill")
    @Size(min=9, max=13, message = "Size of phone must be between 9 and 13")
    @Pattern(regexp = "\\+420[0-9]{9}$", message = "The specified phone number has bad format. Please enter in a similar format +420123456789")
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
