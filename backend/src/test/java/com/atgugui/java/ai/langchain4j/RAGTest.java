package com.atgugui.java.ai.langchain4j;

import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import com.alibaba.dashscope.tokenizers.QwenTokenizer;
import com.alibaba.dashscope.tokenizers.Tokenizer;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.TokenCountEstimator;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenCountEstimator;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

public class RAGTest {

    @Test
    public void testReadDocument() {
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器TextDocumentParser对文档进行解析
        //加载单个文档
        Document document = FileSystemDocumentLoader.loadDocument("C:/Users/CH/Desktop/XiaoZhi/knowledge/knowledge/测试.txt");
        //从一个目录中加载所有文档
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("C:/knowledge", new TextDocumentParser());
        //从一个目录中加载所有的.txt文档
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob*.txt");
        List<Document> documents1 = FileSystemDocumentLoader.loadDocuments("C:/knowledge", pathMatcher, new TextDocumentParser());
        //从一个目录及其子目录中加载所有文档
        List<Document> documents2 = FileSystemDocumentLoader.loadDocumentsRecursively("C:/knowledge", new TextDocumentParser());
    }

    @Test
    public void testParsePDF() {
        Document documentPdf = FileSystemDocumentLoader.loadDocument("C:/Users/CH/Desktop/XiaoZhi/knowledge/knowledge/医院信息.pdf", new ApachePdfBoxDocumentParser());
        System.out.println(documentPdf.text());
        System.out.println(documentPdf.metadata());
    }

    @Test
    public void testReadDocumentAndStore() {
        //使用FileSystemDocumentLoader读取指定目录下的知识库文档
        //并使用默认的文档解析器对文档进行解析(TextDocmentParser)
        Document document = FileSystemDocumentLoader.loadDocument("C:/Users/CH/Desktop/XiaoZhi/knowledge/knowledge/人工智能.md");

        //为了简单起见，我们暂时使用基于内存的向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        //ingest
        //1.分割文档，默认使用递归分割器，将文档分割为多个文本片段，每个片段包含不超过300个token，并且有30个token的重叠部分保证连贯性
        //DocumentByParagraphSplitter(DocumentByLineSplitter(DocumentBySentenceSplitter(DocumentByWordSplitter)))
        //2.文本向量化，使用一个Langchian4j内置的轻量化向量模型对每个文本片段进行向量化
        //3.将原始文本和向量存储到向量数据库中(InMemoryEmbeddingStore)
        EmbeddingStoreIngestor.ingest(document, embeddingStore);

        //查看向量数据库内容
        System.out.println(embeddingStore);
    }


    @Test
    public void testDocumentSplitter2() {
        String text = "这是一个实力文本，用于测试token长度的计算。";
        UserMessage userMessage = UserMessage.userMessage(text);

        //计算token长度
        HuggingFaceTokenCountEstimator huggingFaceTokenCountEstimator = new HuggingFaceTokenCountEstimator();
        int count = huggingFaceTokenCountEstimator.estimateTokenCountInMessage(userMessage);
        System.out.println("token的长度：" + count);

    }
}
