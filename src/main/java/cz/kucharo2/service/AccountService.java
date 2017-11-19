package cz.kucharo2.service;

import cz.kucharo2.common.model.RegisterNewAccountModel;
import cz.kucharo2.data.entity.Account;

/**
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
public interface AccountService {

    Account createNewAccount(RegisterNewAccountModel model);

    /**
     * Checks account credentials.
     *
     * @param base64Hash base 64 hashed credentials
     * @return user account if credentials was correct
     */
    Account checkCorrectCredentials(String base64Hash);
}
