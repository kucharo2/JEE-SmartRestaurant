package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.AccountDao;
import cz.kucharo2.data.entity.Account;
import cz.kucharo2.data.enums.AccountRole;
import cz.kucharo2.utils.PasswordHashUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Tests for {@link AccountDao}
 *
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@RunWith(Arquillian.class)
public class AccountDaoImplTest {

    @Inject
    private AccountDao accountDao;

    private static String USERNAME = "test";
    private static String FAIL_USERNAME = "a";
    private static String PHONE = "+420123456789";
    private static String PASSWORD = "test";

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, AccountDao.class.getPackage())
                .addPackages(true, Account.class.getPackage())
                .addPackages(true, AccountRole.class.getPackage())
                .addPackages(true, PasswordHashUtil.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldNotExistUsername() throws Exception {
        Assert.assertNull(accountDao.findByUsername(FAIL_USERNAME));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistUsername() throws Exception {
        Account account = new Account();
        account.setUsername(USERNAME);
        account.setPhone(PHONE);
        account.setPassword(PasswordHashUtil.encrypt(PASSWORD));
        account.setAccountRole(AccountRole.REGISTERED_CUSTOMER);

        accountDao.createOrUpdate(account);

        Account testAccount = accountDao.findByUsername(USERNAME);

        Assert.assertEquals(account.getUsername(), testAccount.getUsername());
    }
}
