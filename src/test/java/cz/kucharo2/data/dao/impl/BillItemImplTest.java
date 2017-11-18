//package cz.kucharo2.data.dao.impl;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
///**
// * Copyright 2017 IEAP CTU
// * Author: Jakub Begera (jakub.begera@cvut.cz)
// */
//@RunWith(Arquillian.class)
//public class BillItemImplTest {
//
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(BillItemImpl.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//
//    @Test
//    public void testParentId() throws Exception {
//
//    }
//}
