package cz.kucharo2.service.impl;

import cz.kucharo2.common.model.RegisterNewAccountModel;
import cz.kucharo2.data.dao.AccountDao;
import cz.kucharo2.data.entity.Account;
import cz.kucharo2.data.enums.AccountRole;
import cz.kucharo2.rest.model.RequestContext;
import cz.kucharo2.service.AccountService;
import cz.kucharo2.utils.PasswordHashUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Base64;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class AccountServiceImpl implements AccountService {

    @Inject
    AccountDao accountDao;

    @Inject
    private RequestContext requestContext;

    @Override
    public void createNewAccount(RegisterNewAccountModel model) {
        Account account = new Account();
        account.setAccountRole(AccountRole.REGISTERED_CUSTOMER);
        account.setUsername(model.getUsername());

        String password = model.getPassword();
        account.setPassword(PasswordHashUtil.encrypt(password));

        account.setPhone(model.getPhone());
        account.setFirstName(model.getFirstName());
        account.setLastName(model.getLastName());
        account.setEmail(model.getEmail());

        accountDao.createOrUpdate(account);
    }

    @Override
    public Account checkCorrectCredentials(String base64Hash, boolean shouldBeWaiter) {
        String credentials = new String(Base64.getDecoder().decode(base64Hash));
        String[] split = credentials.split(":");
        String username = split[0];
        String pass = split[1];

        // find user by username
        Account account = accountDao.findByUsername(username);
        if (account != null && account.getPassword().equals(PasswordHashUtil.encrypt(pass))) {
            if(shouldBeWaiter && !account.getAccountRole().equals(AccountRole.WAITER)) {
                return null;
            }
            return account;
        } else {
            return null;
        }
    }

    @Override
    public Account findAccountByUsername(String username) {
        return accountDao.findByUsername(username);
    }

}
