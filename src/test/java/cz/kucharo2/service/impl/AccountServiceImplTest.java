package cz.kucharo2.service.impl;

import cz.kucharo2.common.model.RegisterNewAccountModel;
import cz.kucharo2.data.dao.AccountDao;
import cz.kucharo2.data.entity.Account;
import cz.kucharo2.service.AccountService;
import cz.kucharo2.utils.PasswordHashUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Base64;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link AccountService}
 *
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
public class AccountServiceImplTest {

    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private AccountService accountService = new AccountServiceImpl();

    private Account account;

    private static String USERNAME = "test";
    private static String PASSWORD = "test";
    private static String NEW_USERNAME = "Temp";
    private static String NEW_PASSWORD = "Temp";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        account = new Account();
        account.setUsername(USERNAME);
        account.setPassword(PasswordHashUtil.encrypt(PASSWORD));
    }

    @Test
    public void testCreateNewAccount() throws Exception {
        doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                Account currentAccount = (Account) arguments[0];
                account.setUsername(currentAccount.getUsername());
                account.setPassword(currentAccount.getPassword());
            }
            return null;
        }).when(accountDao).createOrUpdate(any(Account.class));

        RegisterNewAccountModel model = new RegisterNewAccountModel();
        model.setUsername(NEW_USERNAME);
        model.setPassword(NEW_PASSWORD);

        accountService.createNewAccount(model);

        verify(accountDao).createOrUpdate(any(Account.class));

        Assert.assertEquals(model.getUsername(), account.getUsername());
    }

    @Test
    public void testCheckCorrectCredentials() throws Exception {
        when(accountDao.findByUsername(any(String.class))).thenReturn(account);

        String string = Base64.getEncoder().encodeToString(String.valueOf(USERNAME + ":" + PASSWORD).getBytes());
        Account testAccount = accountService.checkCorrectCredentials(string, false);

        verify(accountDao).findByUsername(any(String.class));

        Assert.assertNotNull(testAccount);
        Assert.assertEquals(account.getUsername(), testAccount.getUsername());
    }

    @Test
    public void testCheckCorrectCredentialsWithBadUsername() throws Exception {
        when(accountDao.findByUsername(any(String.class))).thenReturn(null);

        String string = Base64.getEncoder().encodeToString(String.valueOf(USERNAME + ":" + PASSWORD).getBytes());
        Account testAccount = accountService.checkCorrectCredentials(string, false);

        verify(accountDao).findByUsername(any(String.class));

        Assert.assertNull(testAccount);
    }

    @Test
    public void testFindAccountByUsername() throws Exception {
        when(accountDao.findByUsername(any(String.class))).thenReturn(account);

        Account testAccount = accountService.findAccountByUsername(USERNAME);

        verify(accountDao).findByUsername(any(String.class));

        Assert.assertNotNull(testAccount);
        Assert.assertEquals(account.getUsername(), testAccount.getUsername());
    }
}