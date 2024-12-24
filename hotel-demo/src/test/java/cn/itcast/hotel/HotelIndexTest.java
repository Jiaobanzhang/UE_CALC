package cn.itcast.hotel;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static cn.itcast.hotel.constants.HotelIndexConstants.MAPPING_TEMPLATE;

@SpringBootTest
class HotelIndexTest {

    private RestHighLevelClient client;

    // 测试用来创建索引库
    @Test
    void testCreateIndex() throws IOException {
        // 1.准备Request      PUT /hotel
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        // 2.准备请求参数, 作用是将 MAPPING_TEMPLATE 中的内容以 JSON 格式 提交到 Elasticsearch。
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求, indices()方法返回的对象包含索引库操作的所有方法
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testExistsIndex() throws IOException {
        // 1.准备Request
        GetIndexRequest request = new GetIndexRequest("hotel");
        // 3.发送请求
        boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);

        System.out.println(isExists ? "存在" : "不存在");
    }
    @Test
    void testDeleteIndex() throws IOException {
        // 1.准备Request
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        // 3.发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }


    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://10.22.49.4:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }



}
