
import org.example.entity.Student;
import org.example.mapper.StudentMapper;
import org.junit.jupiter.api.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentTest {

    private SqlSessionFactory sqlSessionFactory;
    private StudentMapper studentMapper;
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
        studentMapper = session.getMapper(StudentMapper.class);
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

    /**
     * 测试多条件查询：根据姓名和专业动态查询
     */
    @Test
    public void findStudentByNameOrMajorTest() {
        System.out.println("=== 测试多条件查询 ===");

        // 测试场景1：仅输入姓名的查询
        System.out.println("\n1. 仅输入姓名的查询：");
        List<Student> students1 = studentMapper.findStudentByNameOrMajor("张", null);
        students1.forEach(System.out::println);

        // 测试场景2：仅输入专业的查询
        System.out.println("\n2. 仅输入专业的查询：");
        List<Student> students2 = studentMapper.findStudentByNameOrMajor(null, "计算机科学");
        students2.forEach(System.out::println);

        // 测试场景3：同时输入姓名和专业的查询
        System.out.println("\n3. 同时输入姓名和专业的查询：");
        List<Student> students3 = studentMapper.findStudentByNameOrMajor("李", "软件工程");
        students3.forEach(System.out::println);

        // 测试场景4：姓名和专业都为空的查询（查询所有）
        System.out.println("\n4. 姓名和专业都为空的查询（查询所有）：");
        List<Student> students4 = studentMapper.findStudentByNameOrMajor(null, null);
        students4.forEach(System.out::println);

        // 测试场景5：姓名为空字符串的查询
        System.out.println("\n5. 姓名为空字符串的查询：");
        List<Student> students5 = studentMapper.findStudentByNameOrMajor("", "数学");
        students5.forEach(System.out::println);
    }

    /**
     * 测试单条件查询：根据ID列表查询id小于5的学生
     */
    @Test
    public void findByListTest() {
        System.out.println("=== 测试单条件查询：根据ID列表查询id小于5的学生 ===");

        // 创建ID列表
        List<Integer> ids = Arrays.asList(1, 2, 3, 4, 5, 6);

        // 执行查询
        List<Student> students = studentMapper.findByList(ids);

        // 验证查询结果
        System.out.println("查询到的学生数量: " + students.size());
        System.out.println("学生列表:");
        students.forEach(student -> {
            System.out.println(student);
            // 验证id确实小于5
            assert student.getId() < 5 : "学生ID应该小于5";
        });

        // 输出统计信息
        System.out.println("\n=== 查询结果统计 ===");
        System.out.println("ID列表: " + ids);
        System.out.println("实际查询到的ID: " +
                students.stream().map(Student::getId).toList());
        System.out.println("所有查询到的学生ID都小于5: " +
                students.stream().allMatch(s -> s.getId() < 5));
    }

}