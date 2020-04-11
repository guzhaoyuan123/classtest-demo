package com.example.classtestdemo.mapper;


import com.example.classtestdemo.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lenovo
 */
@Mapper
@Repository
public interface MessageMapper {
    @Select("select * from message")
    List<Message> selectAll();

    @Select("select * from message where msg_id=#{msgId}")
    Message selectById(Integer msgId);

    @Insert("insert into message(msg_text,msg_summary) values (#{msgText},#{msgSummary})")
    int insert(Message message);

    @Delete("DELETE FROM message WHERE msg_id=#{msgId}")
    int delete(Integer msgId);

    @Update({"<script>" +
            "update message set" +
            "<if test = 'msgText != null'>msg_text = #{msgText}</if>," +
            "<if test = 'msgSummary != null'>msg_summary = #{msgSummary}</if>" +
            "where msg_id = #{msgId}" +
            "</script>"})
    int update(Message message);

    @Update({"<script>",
            "update message",
            "<set>",
            "<if test='msgText != null'>msg_text=#{msgText},</if>",
            "<if test='msgSummary != null'>msg_summary=#{msgSummary},</if>",
            "</set>",
            "WHERE msg_id =#{msgId}",
            "</script>"})
    int updateText(Message message);

    @Insert({"<script>"+
            "INSERT INTO message (msg_text, msg_summary) values"+
            "<foreach collection = 'list' item='msg' index='index' separator=','>"+
            "(#{msg.msgText}, #{msg.msgSummary})"+
            "</foreach>"+
            "</script>"})
    int batchInsert(List<Message> messages);

    List<Message> selectByCondition(Message message);
}