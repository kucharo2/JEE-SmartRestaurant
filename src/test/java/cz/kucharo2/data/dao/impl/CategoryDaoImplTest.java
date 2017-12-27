package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.CategoryDao;
import cz.kucharo2.data.entity.Category;
import cz.kucharo2.data.enums.CategoryType;
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
 * Tests for {@link CategoryDao}
 *
 * @Author Pavel Štíbal <stibapa1@fel.cvut.cz>.
 */
@RunWith(Arquillian.class)
public class CategoryDaoImplTest {

    @Inject
    CategoryDao categoryDao;

    private static int CATEGORY_DRINKS = 2;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, CategoryDao.class.getPackage())
                .addPackages(true, Category.class.getPackage())
                .addPackages(true, CategoryType.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    /**
     * because we have init db, so is the test done this way
     */
    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testShouldExistCategoryBYCode() throws Exception {
        Category category = categoryDao.getById(CATEGORY_DRINKS);

        Category testCategory = categoryDao.getCategoryByCode(CategoryType.DRINKS);

        Assert.assertNotNull(testCategory);
        Assert.assertEquals(category, testCategory);
    }
}
