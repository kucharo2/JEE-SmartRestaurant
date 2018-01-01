# Environment preparation
How to prepare your environment to get this project working

## Database preparation
 - run [create database](database/createDB.sql) script to create project database and database user
 - connect to new created db and run [DDL script](database/ddl.sql) to create project schema and [database updates script](database/updates.sql) to apply updates during the development phase 
 - run [database fill](databasse/fill_db.sql) script to created a test data
    - be aware that database fill is dummy insert that rely on id's that will be given in the empty database.
      It will not work when you tried to run it again even after one failed run.
      If something will failed during the run i recommend to drop whole database and start the creation from the begging

### Install driver into WildFly
- download [postreSql driver](https://jdbc.postgresql.org/download.html) and copy it with [module setting](jbossSetup/module.xml) into this path

`%JBOSS_HOME%\modules\system\layers\base\org\postgresql\main\`

  You may need to create those directories

### Prepare WildFly datasource
 Now you need to specify a datasource in jboss that will be used by our application
 
 - open `%JBOSS_HOME\standalone\configuration\standalone.xml` and find part with datasources. You will see there a datasource configuration (e.g for ExampleDS)
 - add a definition for your datasource
```xml
        <datasource jta="true" jndi-name="java:/SmartRestaurant" pool-name="SmartRestaurant" enabled="true" use-java-context="true" use-ccm="true">
            <connection-url>jdbc:postgresql://localhost:5432/smart_restaurant</connection-url>
            <driver>org.postgresql</driver>
            <pool>
                <min-pool-size>1</min-pool-size>
                <max-pool-size>4</max-pool-size>
                <prefill>false</prefill>
                <use-strict-min>false</use-strict-min>
                <flush-strategy>FailingConnectionOnly</flush-strategy>
            </pool>
            <security>
                <user-name>restaurant_app_user</user-name>
                <password>DoIt4Tweety</password>
            </security>
            <validation>
                <check-valid-connection-sql>SELECT 1</check-valid-connection-sql>
                <validate-on-match>false</validate-on-match>
                <background-validation>false</background-validation>
                <use-fast-fail>false</use-fast-fail>
            </validation>
        </datasource>
```
- right under that you will see configuration of drivers, also add installed driver
```xml
<driver name="org.postgresql" module="org.postgresql"/>
```

## Configure JMS for WildFly using out of process ActiveMQ

### Download ActiveMQ

[Download](http://activemq.apache.org/activemq-5141-release.html) the version 5.14.1. and
run the script `ACTIVEMQ_LOCATION/bin/activemq start`.

Alternative [download](https://archive.apache.org/dist/activemq/5.14.1/).

### Setup resource adapter in WildFly
 
 - Download the [resource adapter](http://repo1.maven.org/maven2/org/apache/activemq/activemq-rar/5.14.1/activemq-rar-5.14.1.rar):

 - Extract the archive and copy its content into the following folder:
 
`%JBOSS_HOME%/modules/systems/layers/base/org/apache/activemq/activemq-rar/5.14.1`
 
 - Copy a file [module.xml](jsm/module.xml) in the destination above.

 - Copy```activemq-rar-5.14.1.rar``` into ```%JBOSS_HOME%/standalone/deployment```.

### Configure ActiveMQ in WildFly 

In standalone.xml, add the following snippets:

```xml
<extensions>
    ...
    <extension module="org.wildfly.extension.messaging-activemq"/>
</extensions>
```

```xml
<subsystem xmlns="urn:jboss:domain:ejb3:4.0">
...
<mdb>
    <resource-adapter-ref resource-adapter-name="activemq-rar"/>
    <bean-instance-pool-ref pool-name="mdb-strict-max-pool"/>
</mdb>
```

Replace the `<subsystem xmlns="urn:jboss:domain:resource-adapters:4.0">` block with: 

```xml
<subsystem xmlns="urn:jboss:domain:resource-adapters:4.0">
    <resource-adapters>
    <resource-adapter id="activemq-rar.rar">
    <archive>
        activemq-rar-5.14.1.rar
    </archive>
    <transaction-support>XATransaction</transaction-support>
        <config-property name="ServerUrl">
            tcp://localhost:61616
        </config-property>
        <config-property name="UserName">
            admin
        </config-property>
        <config-property name="UseInboundSession">
            false
        </config-property>
        <config-property name="Password">
            admin
        </config-property>
        <connection-definitions>
            <connection-definition class-name="org.apache.activemq.ra.ActiveMQManagedConnectionFactory" jndi-name="java:/ConnectionFactory" enabled="true" pool-name="ConnectionFactory">
                <xa-pool>
                    <min-pool-size>1</min-pool-size>
                    <max-pool-size>20</max-pool-size>
                    <prefill>false</prefill>
                    <is-same-rm-override>false</is-same-rm-override>
                </xa-pool>
            </connection-definition>
        </connection-definitions>
        <admin-objects>
            <admin-object class-name="org.apache.activemq.command.ActiveMQQueue" jndi-name="java:/jms/queue/confirmedOrdersQueue" use-java-context="true" pool-name="confirmedOrdersQueue">
                <config-property name="PhysicalName">
                    jms/queue/confirmedOrdersQueue
                </config-property>
            </admin-object>
            </admin-objects>
        </resource-adapter>
    </resource-adapters>
</subsystem>
```

```xml
<subsystem xmlns="urn:jboss:domain:messaging-activemq:1.0">
    <server name="default">
    <security-setting name="#">
        <role name="guest" delete-non-durable-queue="true" create-non-durable-queue="true" consume="true" send="true"/>
    </security-setting>
    <address-setting name="#" message-counter-history-day-limit="10" page-size-bytes="2097152" max-size-bytes="10485760" expiry-address="jms.queue.ExpiryQueue" dead-letter-address="jms.queue.DLQ"/>
    <http-connector name="http-connector" endpoint="http-acceptor" socket-binding="http"/>
    <http-connector name="http-connector-throughput" endpoint="http-acceptor-throughput" socket-binding="http">
        <param name="batch-delay" value="50"/>
    </http-connector>
    <in-vm-connector name="in-vm" server-id="0"/>
    <http-acceptor name="http-acceptor" http-listener="default"/>
    <http-acceptor name="http-acceptor-throughput" http-listener="default">
        <param name="batch-delay" value="50"/>
        <param name="direct-deliver" value="false"/>
    </http-acceptor>
    <in-vm-acceptor name="in-vm" server-id="0"/>
    <jms-queue name="ExpiryQueue" entries="java:/jms/queue/ExpiryQueue"/>
    <jms-queue name="DLQ" entries="java:/jms/queue/DLQ"/>
    <pooled-connection-factory name="activemq-ra" transaction="xa" entries="java:/JmsXA java:jboss/DefaultJMSConnectionFactory" connectors="in-vm"/>
    </server>
</subsystem>
```
