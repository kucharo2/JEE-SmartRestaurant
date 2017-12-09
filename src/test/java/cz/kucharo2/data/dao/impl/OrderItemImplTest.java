package cz.kucharo2.data.dao.impl;

import cz.kucharo2.data.dao.OrderItemDao;
import cz.kucharo2.data.entity.OrderItem;
import cz.kucharo2.data.enums.OrderStatus;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Copyright 2017 IEAP CTU
 * Author: Jakub Begera (jakub.begera@cvut.cz)
 */
@RunWith(Arquillian.class)
public class OrderItemImplTest {

    @Inject
    private OrderItemDao orderItemDao;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, OrderItemDao.class.getPackage())
                .addPackages(true, OrderItem.class.getPackage())
                .addPackages(true, OrderStatus.class.getPackage())
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testParentId() throws Exception {
        orderItemDao.getAll();
    }
}
