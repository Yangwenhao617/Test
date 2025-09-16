package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.entity.Student;

import java.util.List;

public interface StudentMapper {
    /**
     * 多条件查询 - 根据姓名和专业动态查询
     */
    List<Student> findStudentByNameOrMajor(@Param("name") String name,
                                           @Param("major") String major);

    /**
     * 单条件查询 - 根据ID列表查询
     */
    List<Student> findByList(@Param("ids") List<Integer> ids);
}