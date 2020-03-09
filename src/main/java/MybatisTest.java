import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Dept;
import pojo.Employee;

import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.List;

/**
 * Created by liuhuan on 2020/3/8 13:05
 */
public class MybatisTest {

    Reader reader = null;
    SqlSession session = null;

    @Before
    public void open(){
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            session = sessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close(){
        session.close();
        try {
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insert(){
        try {
            Dept dept = new Dept();
            dept.setName("财务部");
            session.insert("DEPT.insertDept",dept);
            System.out.println(dept.getId());
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    @Test
    public void insertEmployee(){
        try {
            Employee emp = new Employee();
            emp.setName("张三");
            emp.setHireDate(new Date());
            //emp.setDeptId(1);
            emp.setSalary(10000F);
            session.insert("EMPLOYEE.addEmp",emp);
            System.out.println(emp.getId());
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    @Test
    public void update(){
        try {
/*            Dept dept = new Dept();
            dept.setName("运营部");
            dept.setId(6);
            session.update("DEPT.updateDept",dept);*/
            Employee emp = new Employee();
            emp.setName("李四");
            emp.setHireDate(new Date());
            //emp.setDeptId(2);
            emp.setSalary(25000F);
            emp.setId(1);
            session.update("EMPLOYEE.updateEmployee",emp);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    @Test
    public void delete(){
        try {
            session.delete("DEPT.deleteDept",4);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    @Test
    public void select(){
/*        List<Dept> depts = session.selectList("DEPT.getDeptList");
        for(Dept dept: depts){
            System.out.println(dept);
        }*/
        List<Employee> emps = session.selectList("EMPLOYEE.getEmpList");
        for(Employee emp: emps){
            System.out.println(emp);
        }
    }

    @Test
    public void getDeptWithEmp(){
        List<Dept> deptList = session.selectList("DEPT.getDeptWithEmp");
        for (Dept dept : deptList) {
            System.out.println(dept.getName());
            if(dept.getEmps() != null){
                for (Employee employee : dept.getEmps()) {
                    System.out.println(employee);
                }
                System.out.println("==========================");
            }
        }
    }
}
