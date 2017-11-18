package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.BillItemDao;
import cz.kucharo2.data.entity.BillItem;
import cz.kucharo2.data.enums.BillStatus;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Copyright 2017 IEAP CTU
 * Author: Jakub Begera (jakub.begera@cvut.cz)
 */
@RunWith(Arquillian.class)
public class BillItemImplTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
//                .addPackages(true, BillItemDao.class.getPackage())
//                .addPackages(true, BillItem.class.getPackage())
//                .addPackages(true, BillStatus.class.getPackage())
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testParentId() throws Exception {

    }
}
