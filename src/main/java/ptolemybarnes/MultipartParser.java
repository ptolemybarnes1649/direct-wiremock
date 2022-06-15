package ptolemybarnes;

import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.servlet.WireMockHttpServletMultipartAdapter;
import com.google.common.base.Function;
import org.eclipse.jetty.util.MultiPartInputStreamParser;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.util.Collection;

import static com.github.tomakehurst.wiremock.common.Exceptions.throwUnchecked;

public class MultipartParser {

    @SuppressWarnings("unchecked")
    public static Collection<Request.Part> parse(byte[] body, String contentType) {
        MultiPartInputStreamParser parser =
                new MultiPartInputStreamParser(new ByteArrayInputStream(body), contentType, null, null);
        try {
            return parser.getParts().stream().map((Function<Part, Request.Part>) input -> WireMockHttpServletMultipartAdapter.from(input))
                    .toList();
        } catch (Exception e) {
            return throwUnchecked(e, Collection.class);
        }
    }
}