package cz.kucharo2.service.impl;

import cz.kucharo2.common.model.RegisterNewAccountModel;
import cz.kucharo2.data.dao.AccountDao;
import cz.kucharo2.data.entity.Account;
import cz.kucharo2.service.AccountService;
import cz.kucharo2.utils.PasswordHashUtil;

import javax.inject.Inject;
import java.util.Base64;

public class AccountServiceImpl implements AccountService {

    @Inject
    private AccountDao accountDao;

    @Override
    public Account createNewAccount(RegisterNewAccountModel model) {

        return null;
    }

    @Override
    public Account checkCorrectCredentials(String base64Hash) {
        String credentials = new String(Base64.getDecoder().decode(base64Hash));
        String[] split = credentials.split(":");
        String username = split[0];
        String pass = split[1];

        // find user by username
        Account account = accountDao.findByUsername(username);
        if (account != null && account.getPassword().equals(PasswordHashUtil.encrypt(pass))) {
            return account;
        } else {
            return null;
        }
    }
}
