package ru.netology.httpserver2.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class HttpRequestBuilderTest {

    private HttpRequestBuilder sut;

    @BeforeEach
    void setUp() {
        sut = new HttpRequestBuilder();
    }

    @Test
    void setUriWithParams() {
        String uri = "/params.html?param1=1&param1=2&rus=%D1%80%D1%83%D1%81";
        String undefinedName = "undefined_name";
        List<NameValuePair> expectedParams = List.of(
                new BasicNameValuePair("param1", "1"),
                new BasicNameValuePair("param1", "2"),
                new BasicNameValuePair("rus", "рус")
        );
        sut.setUri(uri);

        HttpRequest result = sut.build();

        Assertions.assertEquals("/params.html", result.getPath());
        Assertions.assertEquals("1", result.getQueryParam("param1").getValue());
        Assertions.assertNull(result.getQueryParam(undefinedName));
        Assertions.assertEquals(3, result.getQueryParams().size());
        List<NameValuePair> resultParams = result.getQueryParams();
        for (int i = 0; i < expectedParams.size(); i++) {
            Assertions.assertEquals(expectedParams.get(i).getName(), resultParams.get(i).getName());
            Assertions.assertEquals(expectedParams.get(i).getValue(), resultParams.get(i).getValue());
        }
    }

    @Test
    void setPostParams_XWwwUrlEncoded() {
        String params = "param1=1&param1=2&rus=%D1%80%D1%83%D1%81";
        String undefinedName = "undefined_name";
        List<NameValuePair> expectedParams = List.of(
                new BasicNameValuePair("param1", "1"),
                new BasicNameValuePair("param1", "2"),
                new BasicNameValuePair("rus", "рус")
        );
        sut.setPostParams(params);

        HttpRequest result = sut.build();

        Assertions.assertEquals("1", result.getPostParam("param1").getValue());
        Assertions.assertNull(result.getPostParam(undefinedName));
        Assertions.assertEquals(3, result.getPostParams().size());
        List<NameValuePair> resultParams = result.getPostParams();
        for (int i = 0; i < expectedParams.size(); i++) {
            Assertions.assertEquals(expectedParams.get(i).getName(), resultParams.get(i).getName());
            Assertions.assertEquals(expectedParams.get(i).getValue(), resultParams.get(i).getValue());
        }
    }
}