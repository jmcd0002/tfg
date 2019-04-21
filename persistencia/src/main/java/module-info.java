open module persistencia {
    requires spring.beans;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires spring.context;
    requires org.slf4j;

    exports jmcd.tfg.persistencia.dao;
    exports jmcd.tfg.persistencia.pojo;
}