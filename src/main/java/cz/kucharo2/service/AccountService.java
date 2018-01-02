package cz.kucharo2.service;

import cz.kucharo2.common.model.RegisterNewAccountModel;
import cz.kucharo2.data.entity.Account;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
public interface AccountService {

    /**
     * register new user and set him to db
     * @param model data model for create new Account
     * @return boolean true if was create user
     */
    void createNewAccount(RegisterNewAccountModel model);

    /**
     * Checks account credentials.
     *
     * @param base64Hash base 64 hashed credentials
     * @param shouldBeWaiter whether method should also check waiter role
     * @return user account if credentials was correct
     */
    Account checkCorrectCredentials(String base64Hash, boolean shouldBeWaiter);

    /**
     * Find account by username
     * @param username string
     * @return user account
     */
    Account findAccountByUsername(String username);

}
