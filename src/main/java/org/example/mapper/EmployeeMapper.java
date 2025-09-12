package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Employee;

import java.util.List;

public interface EmployeeMapper {

    /**
     * 增加员工
     * @param employee 员工对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO employee (name, age, position) VALUES (#{name}, #{age}, #{position})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertEmployee(Employee employee);

    /**
     * 根据ID删除员工
     * @param id 员工ID
     * @return 影响的行数
     */
    @Delete("DELETE FROM employee WHERE id = #{id}")
    int deleteEmployeeById(Integer id);

    /**
     * 修改员工信息
     * @param employee 员工对象
     * @return 影响的行数
     */
    @Update("UPDATE employee SET name = #{name}, age = #{age}, position = #{position} WHERE id = #{id}")
    int updateEmployee(Employee employee);

    /**
     * 根据ID查询单个员工
     * @param id 员工ID
     * @return 员工对象
     */
    @Select("SELECT id, name, age, position FROM employee WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age"),
            @Result(property = "position", column = "position")
    })
    Employee selectEmployeeById(Integer id);

    /**
     * 查询全部员工
     * @return 员工列表
     */
    @Select("select *from employee order by id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age"),
            @Result(property = "position", column = "position")
    })
    List<Employee> selectAllEmployees();
}