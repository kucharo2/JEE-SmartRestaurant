package cz.kucharo2.rest.model;

import cz.kucharo2.data.entity.Account;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * @Author Roman Kuchár <kucharrom@gmail.com>.
 */
@SessionScoped
public class SessionContext implements Serializable{

    private static final long serialVersionUID = -5039880007004887337L;

    private Account loggedAccount;

    public Account getLoggedAccount() {
        return loggedAccount;
    }

    public void setLoggedAccount(Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }
}
