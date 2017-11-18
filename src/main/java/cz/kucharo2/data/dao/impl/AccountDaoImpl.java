package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.AccountDao;
import cz.kucharo2.data.entity.Account;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2017 IEAP CTU
 * Author: Jakub Begera (jakub.begera@cvut.cz)
 */
@ApplicationScoped
public class AccountDaoImpl extends AbstractGenericDaoImpl<Account> implements AccountDao {

    public AccountDaoImpl() {
        super(Account.class);
    }

    @Override
    public Account findByUsername(String username) {
        String query = Account.USERNAME + " = :username";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);

        return getByWhereConditionSingleResult(query, params);
    }
}
