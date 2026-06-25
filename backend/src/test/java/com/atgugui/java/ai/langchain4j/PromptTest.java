package com.atgugui.java.ai.langchain4j;

import com.atguigu.java.ai.langchain4j.XiaozhiApp;
import com.atguigu.java.ai.langchain4j.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = XiaozhiApp.class)
public class PromptTest {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testV(){
        String answer1 = separateChatAssistant.chat2(1,"我是吴彦祖");
        System.out.println(answer1);

        String answer2 = separateChatAssistant.chat2(1, "我是谁");
        System.out.println(answer2);
    }
}
