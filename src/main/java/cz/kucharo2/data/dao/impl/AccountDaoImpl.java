package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.AccountDao;
import cz.kucharo2.data.entity.Account;

import javax.enterprise.context.ApplicationScoped;

/**
 * Copyright 2017 IEAP CTU
 * Author: Jakub Begera (jakub.begera@cvut.cz)
 */
@ApplicationScoped
public class AccountDaoImpl extends AbstractGenericDaoImpl<Account> implements AccountDao {

    public AccountDaoImpl() {
        super(Account.class);
    }
}
