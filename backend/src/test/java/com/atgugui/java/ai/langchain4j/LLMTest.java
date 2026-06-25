package com.atgugui.java.ai.langchain4j;

import com.atguigu.java.ai.langchain4j.XiaozhiApp;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;

@SpringBootTest(classes = XiaozhiApp.class)
public class LLMTest {

    @Test
    public void testGPTDemo(){
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();

        String answer = model.chat("我能学会agent吗，我是学Java的，能直接学langchain4j而不是python那一套吗");
        System.out.println(answer);
    }

    @Autowired
    private OpenAiChatModel openAiChatModel;
    //private ChatModel chatModel;

    @Test
    public void testSpringBoot(){
        String answer = openAiChatModel.chat("你是谁？");
        System.out.println(answer);
    }

    @Autowired
    private OllamaChatModel ollamaChatModel;

    @Test
    public void testOllama(){
        String answer = ollamaChatModel.chat("双非研二末六月份后端开发零实习，传统java学完了现在学agent开发，是不是很晚");
        System.out.println(answer);

    }

    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    public void testDashScopeQwen(){
        //提问
        String answer = qwenChatModel.chat("你是谁？");
        //结果
        System.out.println(answer);
    }

    @Test
    public void testDashScopeWanx(){
        WanxImageModel wanxImageModel = WanxImageModel.builder()
                .modelName("wan2.2-t2i-plus")
                .apiKey(System.getenv("DASH_SCOPE_API_KEY"))
                .build();
        Response<Image> answer = wanxImageModel.generate("一个美女在吃巧乐兹");
        URI url = answer.content().url();
        System.out.println(url);
    }
}
