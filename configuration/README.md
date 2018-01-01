# Environment preparation
How to prepare your environment to get this project working

## Database preparation
 - run ![create database](database/createDB.sql) script to create project database and database user
 - connect to new created db and run ![DDL script](database/ddl.sql) to create project schema and ![database updates script](database/updates.sql) to aply updates during the development phase 
 - run ![database fill](databasse/fill_db.sql) script to created a test data
    - be aware that database fill is dummy insert that rely on id's that will be given in the empty database.
      It will not work when you tried to run it again even after one failed run.
      If something will failed during the run i recommend to drop whole database and start the creation from the begging

### Install driver into JBOSS
- download [postreSql driver](https://jdbc.postgresql.org/download.html) and copy it with [module setting](jbossSetup/module.xml) into this path
```bash
%JBOSS_HOME%\modules\system\layers\base\org\postgresql\main\
```
  You may need to create those directories

### Prepare JBOSS datasource
 Now you need to specify a datasource in jboss that will be used by our application
 
 - open %JBOSS_HOME\standalone\configuration\standalone.xml and find part with datasources. You will see there a datasource configuration (e.g for ExampleDS)
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