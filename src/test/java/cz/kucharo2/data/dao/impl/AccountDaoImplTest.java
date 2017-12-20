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
        Assert.assertNull(accountDao.findByUsername("a"));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistUsername() throws Exception {
        Assert.assertNotNull(accountDao.findByUsername("anonymous"));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testAccountFindByUsername() throws Exception {
        Account account = new Account();
        account.setUsername("anonymous");
        account.setId(1);

        Account testAccount = accountDao.findByUsername("anonymous");

        Assert.assertEquals(account.getId(), testAccount.getId());
        Assert.assertEquals(account.getUsername(), testAccount.getUsername());
    }
}
