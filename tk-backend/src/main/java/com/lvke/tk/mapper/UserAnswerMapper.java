package com.lvke.tk.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lvke.tk.model.dto.statistic.AppAnswerCountDTO;
import com.lvke.tk.model.dto.statistic.AppAnswerResultCountDTO;
import com.lvke.tk.model.entity.UserAnswer;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 针对表【user_answer(用户答题记录)】的数据库操作Mapper
 */
public interface UserAnswerMapper extends BaseMapper<UserAnswer> {

    @Select("SELECT appId, COUNT(userId) AS answerCount FROM (\n" +
            "    SELECT appId, userId, isDelete FROM user_answer_0\n" +
            "    UNION ALL\n" +
            "    SELECT appId, userId, isDelete FROM user_answer_1\n" +
            ") AS combined_answers\n" +
            "WHERE combined_answers.isDelete = 0\n" +
            "GROUP BY appId\n" +
            "ORDER BY answerCount DESC\n" +
            "LIMIT 10;")


    List<AppAnswerCountDTO> doAppAnswerCount();


    @Select("SELECT resultName, COUNT(resultName) AS resultCount FROM (\n" +
            "    SELECT resultName FROM user_answer_0\n" +
            "    UNION ALL\n" +
            "    SELECT resultName FROM user_answer_1\n" +
            ") AS combined_answers\n" +
            "WHERE appId = #{appId} AND combined_answers.isDelete = 0\n" +
            "GROUP BY resultName\n" +
            "ORDER BY resultCount DESC;")

    List<AppAnswerResultCountDTO> doAppAnswerResultCount(Long appId);
}
