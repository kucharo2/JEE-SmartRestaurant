package cz.kucharo2.rest.validator;

import cz.kucharo2.common.model.RegisterNewAccountModel;
import cz.kucharo2.data.entity.Account;
import cz.kucharo2.rest.model.FieldError;
import cz.kucharo2.service.AccountService;

import javax.inject.Inject;
import java.util.List;

public class ValidatorUtil {

    @Inject
    AccountService accountService;

    public List<FieldError> validate(RegisterNewAccountModel model){
        List<FieldError> errors = FormValidatorUtil.validate(model);

        if (!errors.isEmpty()){
            return errors;
        }

        Account account = accountService.findAccountByUsername(model.getUsername());
        if(account.getUsername().equals(model.getUsername())){
            errors.add(new FieldError("username", "Username " + model.getUsername() + " all ready exist"));
            return errors;
        }

        return errors;
    }
}
