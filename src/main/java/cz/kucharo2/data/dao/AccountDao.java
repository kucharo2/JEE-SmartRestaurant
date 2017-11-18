package cz.kucharo2.data.dao;

import cz.kucharo2.data.entity.Account;

import javax.validation.constraints.NotNull;

/**
 * Copyright 2017 IEAP CTU
 * Author: Jakub Begera (jakub.begera@cvut.cz)
 */
public interface AccountDao extends AbstractGenericDao<Account> {

    Account findByUsername(@NotNull String username);
}
