package ptolemybarnes;

import static com.google.common.collect.Lists.newArrayList;
import com.github.tomakehurst.wiremock.http.Body;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.Request;
import java.util.List;
import java.util.Objects;

public class MockMultipart implements Request.Part {

    private String name;
    private List<HttpHeader> headers = newArrayList();
    private Body body;

    public static MockMultipart mockPart() {
        return new MockMultipart();
    }

    public MockMultipart name(String name) {
        this.name = name;
        return this;
    }

    public MockMultipart headers(List<HttpHeader> headers) {
        this.headers = headers;
        return this;
    }

    public MockMultipart header(String key, String... values) {
        headers.add(new HttpHeader(key, values));
        return this;
    }

    public MockMultipart body(String body) {
        this.body = new Body(body);
        return this;
    }

    public MockMultipart body(byte[] body) {
        this.body = new Body(body);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public HttpHeader getHeader(String key) {
        return getHeaders().getHeader(key);
    }

    @Override
    public HttpHeaders getHeaders() {
        return new HttpHeaders(headers);
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockMultipart that = (MockMultipart) o;
        return Objects.equals(name, that.name)
                && Objects.equals(headers, that.headers)
                && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, headers, body);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MockMultipart{");
        sb.append("name='").append(name).append('\'');
        sb.append(", headers=").append(headers);
        sb.append(", body=").append(body);
        sb.append('}');
        return sb.toString();
    }
}
