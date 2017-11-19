package cz.kucharo2.common.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterNewAccountModelTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private RegisterNewAccountModel registerNewAccountModel;

    @Before
    public void setUp() throws Exception {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        registerNewAccountModel = new RegisterNewAccountModel();
    }

    @After
    public void tearDown() throws Exception {
        validatorFactory.close();
    }

    @Test
    public void shouldHaveNoViolations(){
        registerNewAccountModel.setPassword("heslo123");
        registerNewAccountModel.setUsername("Test");
        registerNewAccountModel.setPhone("+420123456789");
        registerNewAccountModel.setEmail("a@a.aa");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldDetectEmptyUsername(){
        registerNewAccountModel.setPassword("heslo123");
        registerNewAccountModel.setPhone("+420123456789");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals("Username must be fill", violation.getMessage());
        assertEquals("username", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void shouldDetectShortPassword(){
        registerNewAccountModel.setPassword("123");
        registerNewAccountModel.setUsername("Test");
        registerNewAccountModel.setPhone("+420123456789");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals("Size of password must be minimum 6", violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
        assertEquals("123", violation.getInvalidValue());
    }


    @Test
    public void shouldDetectEmptyPassword(){
        registerNewAccountModel.setUsername("Test");
        registerNewAccountModel.setPhone("+420123456789");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals("Password must be fill", violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void shouldDetectEmptyPhoneNumber(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("Test");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals("Phone must be fill", violation.getMessage());
        assertEquals("phone", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void shouldDetectShortPhoneNumber(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("Test");
        registerNewAccountModel.setPhone("+4201321");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        List<ConstraintViolation<RegisterNewAccountModel>> listViolations = new ArrayList(violations);
        listViolations.sort((o1, o2) -> o1.getMessage().compareTo(o2.getMessage()));
        assertEquals(2, listViolations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = listViolations.get(0);
        assertEquals("Size of phone must be between 9 and 13", violation.getMessage());
        assertEquals("phone", violation.getPropertyPath().toString());
        assertEquals("+4201321", violation.getInvalidValue());

        violation = listViolations.get(1);
        assertEquals("The specified phone number has bad format. Please enter in a similar format +420123456789", violation.getMessage());
        assertEquals("phone", violation.getPropertyPath().toString());
        assertEquals("+4201321", violation.getInvalidValue());
    }

    @Test
    public void shouldDetectLongPhoneNumber(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("Test");
        registerNewAccountModel.setPhone("+420132147258369");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        List<ConstraintViolation<RegisterNewAccountModel>> listViolations = new ArrayList(violations);
        listViolations.sort((o1, o2) -> o1.getMessage().compareTo(o2.getMessage()));
        assertEquals(2, listViolations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = listViolations.get(0);
        assertEquals("Size of phone must be between 9 and 13", violation.getMessage());
        assertEquals("phone", violation.getPropertyPath().toString());
        assertEquals("+420132147258369", violation.getInvalidValue());

        violation = listViolations.get(1);
        assertEquals("The specified phone number has bad format. Please enter in a similar format +420123456789", violation.getMessage());
        assertEquals("phone", violation.getPropertyPath().toString());
        assertEquals("+420132147258369", violation.getInvalidValue());
    }

    @Test
    public void shouldDetectBadFormatPhoneNumber(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("Test");
        registerNewAccountModel.setPhone("132147258369");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals("The specified phone number has bad format. Please enter in a similar format +420123456789", violation.getMessage());
        assertEquals("phone", violation.getPropertyPath().toString());
        assertEquals("132147258369", violation.getInvalidValue());
    }

    @Test
    public void shouldDetectShortEmail(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("Test");
        registerNewAccountModel.setPhone("+420147258369");
        registerNewAccountModel.setEmail("@a.aa");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        List<ConstraintViolation<RegisterNewAccountModel>> listViolations = new ArrayList(violations);
        listViolations.sort((o1, o2) -> o1.getMessage().compareTo(o2.getMessage()));
        assertEquals(2, listViolations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = listViolations.get(0);
        assertEquals("Size of email must be minimum 6", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("@a.aa", violation.getInvalidValue());

        violation = listViolations.get(1);
        assertEquals("The specified email has bad format. Please enter in a similar format a@a.cz", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("@a.aa", violation.getInvalidValue());
    }

    @Test
    public void shouldDetectBadFormatEmail(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("Test");
        registerNewAccountModel.setPhone("+420147258369");
        registerNewAccountModel.setEmail("aaa@aaa");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals("The specified email has bad format. Please enter in a similar format a@a.cz", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("aaa@aaa", violation.getInvalidValue());
    }

    @Test
    public void shouldDetectBadFormatEmail2(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("Test");
        registerNewAccountModel.setPhone("+420147258369");
        registerNewAccountModel.setEmail("aa.aa.aa");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals("The specified email has bad format. Please enter in a similar format a@a.cz", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("aa.aa.aa", violation.getInvalidValue());
    }
}