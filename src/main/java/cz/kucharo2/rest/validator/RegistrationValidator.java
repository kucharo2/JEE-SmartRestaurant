package cz.kucharo2.rest.validator;

import cz.kucharo2.common.constants.ErrorValidationMessages;
import cz.kucharo2.common.model.RegisterNewAccountModel;
import cz.kucharo2.data.entity.Account;
import cz.kucharo2.rest.model.FieldError;
import cz.kucharo2.service.AccountService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.util.Base64;
import java.util.List;

public class RegistrationValidator {

    @Inject
    AccountService accountService;

    @Inject
    private Logger logger;


    public List<FieldError> validate(RegisterNewAccountModel model){
        List<FieldError> errors = FormValidatorUtil.validate(model);

        String password = new String(Base64.getDecoder().decode(model.getPassword()));
        model.setPassword(password);

        if (!errors.isEmpty()){
            for (FieldError error : errors) {
                logger.info("Validator error: " + error.getFieldName() + " and error message: " + error.getErrorMessage());
            }

            return errors;
        }

        Account account = accountService.findAccountByUsername(model.getUsername());
        if(account != null){
            logger.info("Validator error: username and error message: Username " + model.getUsername() + " already exist");
            errors.add(new FieldError("username", ErrorValidationMessages.ERR_SAME_USERNAME));
            return errors;
        }

        return errors;
    }
}
