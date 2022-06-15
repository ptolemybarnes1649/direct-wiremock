package ptolemybarnes;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.direct.DirectCallHttpServer;
import com.github.tomakehurst.wiremock.direct.DirectCallHttpServerFactory;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.http.RequestMethod.POST;
import static ptolemybarnes.MockRequest.mockRequest;

public class DirectWiremock {

    public static void main(String[] args) {
        // String path = args[0];
        String path = "/bid/";
        DirectCallHttpServerFactory factory = new DirectCallHttpServerFactory();
        var config = wireMockConfig().httpServerFactory(factory);
        config.fileSource(new SingleRootFileSource("src/resources"));
        WireMockServer wm = new WireMockServer(config);
        wm.start(); // no-op, not required

        DirectCallHttpServer server = factory.getHttpServer();

        Request request = mockRequest().method(POST).url(path);

        Response response = server.stubRequest(request);
        System.out.println(response.toString());
    }
}
