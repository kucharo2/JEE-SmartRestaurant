package cz.kucharo2.filter;

import cz.kucharo2.data.enums.AccountRole;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static cz.kucharo2.data.enums.AccountRole.*;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Copyright 2017 IEAP CTU
 * Author: Jakub Begera (jakub.begera@cvut.cz)
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured {
    AccountRole[] roles() default {ANONYMOUS_CUSTOMER, REGISTERED_CUSTOMER, WAITER};
}
