package cz.kucharo2.common.model;

import cz.kucharo2.common.constants.ErrorValidationMessages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
public class RegisterNewAccountModelTest {
    private ValidatorFactory validatorFactory;
    private Validator validator;
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
        registerNewAccountModel.setUsername("TestTest");
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
        assertEquals(ErrorValidationMessages.ERR_EMPTY_USERNAME, violation.getMessage());
        assertEquals("username", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void shouldDetectShortUsername(){
        registerNewAccountModel.setPassword("heslo123");
        registerNewAccountModel.setUsername("Test");
        registerNewAccountModel.setPhone("+420123456789");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals(ErrorValidationMessages.ERR_LENGTH_USERNAME, violation.getMessage());
        assertEquals("username", violation.getPropertyPath().toString());
        assertEquals("Test", violation.getInvalidValue());
    }

    @Test
    public void shouldDetectShortPassword(){
        registerNewAccountModel.setPassword("123");
        registerNewAccountModel.setUsername("TestTest");
        registerNewAccountModel.setPhone("+420123456789");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals(ErrorValidationMessages.ERR_LENGTH_PASSWORD, violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
        assertEquals("123", violation.getInvalidValue());
    }


    @Test
    public void shouldDetectEmptyPassword(){
        registerNewAccountModel.setUsername("TestTest");
        registerNewAccountModel.setPhone("+420123456789");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals(ErrorValidationMessages.ERR_EMPTY_PASSWORD, violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void shouldDetectEmptyPhoneNumber(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("TestTest");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals(ErrorValidationMessages.ERR_EMPTY_PHONE, violation.getMessage());
        assertEquals("phone", violation.getPropertyPath().toString());
        assertEquals(null, violation.getInvalidValue());
    }

    @Test
    public void shouldDetectBadFormatPhoneNumber(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("TestTest");
        registerNewAccountModel.setPhone("132147258369");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals(ErrorValidationMessages.ERR_BAD_FORMAT_PHONE, violation.getMessage());
        assertEquals("phone", violation.getPropertyPath().toString());
        assertEquals("132147258369", violation.getInvalidValue());
    }


    @Test
    public void shouldDetectNoDomainInEmail(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("TestTest");
        registerNewAccountModel.setPhone("+420147258369");
        registerNewAccountModel.setEmail("aaa@aaa");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals(ErrorValidationMessages.ERR_BAD_FORMAT_EMAIL, violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("aaa@aaa", violation.getInvalidValue());
    }

    @Test
    public void shouldDetectNoAtInEmail(){
        registerNewAccountModel.setPassword("123123");
        registerNewAccountModel.setUsername("TestTest");
        registerNewAccountModel.setPhone("+420147258369");
        registerNewAccountModel.setEmail("aa.aa.aa");

        Set<ConstraintViolation<RegisterNewAccountModel>> violations = validator.validate(registerNewAccountModel);
        assertEquals(1, violations.size());

        ConstraintViolation<RegisterNewAccountModel> violation = violations.iterator().next();
        assertEquals(ErrorValidationMessages.ERR_BAD_FORMAT_EMAIL, violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("aa.aa.aa", violation.getInvalidValue());
    }
}