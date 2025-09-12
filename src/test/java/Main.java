import org.example.entity.Employee;
import org.example.mapper.EmployeeMapper;
import org.junit.jupiter.api.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Main {

    private SqlSessionFactory sqlSessionFactory;
    private EmployeeMapper employeeMapper;
    private SqlSession session;

    // 替代 @BeforeClass - 在类初始化时执行一次
    @BeforeAll
    void init() throws Exception {
        System.out.println("初始化测试环境...");

        // 加载MyBatis配置
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession  = sqlSessionFactory.openSession(false);
    }

    // 替代 @Before - 每个测试方法前执行
    @BeforeEach
    void setUp() {
        System.out.println("准备测试...");
        session = sqlSessionFactory.openSession();
        employeeMapper = session.getMapper(EmployeeMapper.class);
    }

    // 替代 @After - 每个测试方法后执行
    @AfterEach
    void tearDown() {
        System.out.println("清理测试...");
        if (session != null) {
            session.close();
        }
    }

    // 替代 @AfterClass - 所有测试完成后执行
    @AfterAll
    void cleanUp() {
        System.out.println("所有测试完成，清理环境...");
    }

    @Test
    @DisplayName("测试查询所有员工")
    void testSelectAllEmployees() {
        List<Employee> employees = employeeMapper.selectAllEmployees();
        employees.forEach(System.out::println);
    }

    @Test
    @DisplayName("测试根据ID查询员工")
    void testSelectEmployeeById() {
        Employee employee = employeeMapper.selectEmployeeById(1);
        assertNotNull(employee);
        assertEquals("张三", employee.getName());
        assertEquals(20, employee.getAge());
        assertEquals("员工", employee.getPosition());
    }

    @Test
    @DisplayName("测试插入员工")
    void testInsertEmployee() {
        Employee newEmployee = new Employee().setName("赵六").setAge(26).setPosition("员工");
        int result = employeeMapper.insertEmployee(newEmployee);
        session.commit();
        System.out.println(result);

    }

    @Test
    @DisplayName("测试更新员工信息")
    void testUpdateEmployee() {
        Employee employee = employeeMapper.selectEmployeeById(1);
        assertNotNull(employee);

        String originalName = employee.getName();
        employee.setName("修改后的张三");
        employee.setAge(22);

        int result = employeeMapper.updateEmployee(employee);
        session.commit();
        System.out.println(result);
    }

    @Test
    @DisplayName("测试删除员工")
    void testDeleteEmployeeById() {
        // 先插入一个测试员工
        Employee testEmployee = new Employee().setName("待删除员工").setAge(27).setPosition("测试");
        employeeMapper.insertEmployee(testEmployee);
        session.commit();

        int result = employeeMapper.deleteEmployeeById(testEmployee.getId());
        System.out.println(result);
        session.commit();
    }


    @Test
    @DisplayName("测试不存在的员工查询")
    void testSelectNonExistentEmployee() {
        Employee employee = employeeMapper.selectEmployeeById(9999);
        System.out.println(employee);
    }

    @Test
    @DisplayName("测试删除不存在的员工")
    void testDeleteNonExistentEmployee() {
        int result = employeeMapper.deleteEmployeeById(9999);
        System.out.println(result);
    }

}