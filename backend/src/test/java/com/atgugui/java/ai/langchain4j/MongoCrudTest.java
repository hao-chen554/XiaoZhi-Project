package com.atgugui.java.ai.langchain4j;

import com.atguigu.java.ai.langchain4j.XiaozhiApp;
import com.atguigu.java.ai.langchain4j.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest(classes = XiaozhiApp.class)
public class MongoCrudTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入文档
     */

//    @Test
//    public void testInsert(){
//        mongoTemplate.insert(new ChatMessages(1L,"聊天记录"));
//    }

    @Test
    public void testInsert2(){
        ChatMessages chatMessages = new ChatMessages();
        chatMessages.setContent("聊天记录列表");
        mongoTemplate.insert(chatMessages);
    }

    @Test
    public void testFindById(){
        ChatMessages chat = mongoTemplate.findById("6a37e2f5e1a8de1680665c94", ChatMessages.class);
        System.out.println(chat);
    }

    @Test
    public void testUpdateById(){
        Criteria criteria = Criteria.where("_id").is("6a37e2f5e1a8de1680665c94");
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content","新的yabi聊天记录列表");

        mongoTemplate.upsert(query,update,ChatMessages.class);
    }

    @Test
    public void testDeleteById(){
        Criteria criteria = Criteria.where("_id").is("6a37e2f5e1a8de1680665c94");
        Query query = new Query(criteria);

        mongoTemplate.remove(query,ChatMessages.class);
    }

}
