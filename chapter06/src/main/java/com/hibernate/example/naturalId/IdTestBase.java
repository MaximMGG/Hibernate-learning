package com.hibernate.example.naturalId;


import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.example.util.SessionUtil;

public class IdTestBase {
    protected SimpleNaturalEmployee createSimpleEmployee(String name, int badge) {
        SimpleNaturalEmployee employee = new SimpleNaturalEmployee();
        employee.setName(name);
        employee.setBadge(Integer.valueOf(badge));
        employee.setRoyalty(10.2323);

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.save(employee);

            tx.commit();
        }
        return employee;
    }

    protected Employee createEmployee(String name, int section, int department) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setDepartment(department);
        employee.setSection(section);
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.save(employee);
            tx.commit();
        }
        return employee;
    }
}
