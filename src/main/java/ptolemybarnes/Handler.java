package ptolemybarnes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.direct.DirectCallHttpServer;
import com.github.tomakehurst.wiremock.direct.DirectCallHttpServerFactory;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.http.RequestMethod.POST;
import static ptolemybarnes.MockRequest.mockRequest;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        LambdaLogger logger = context.getLogger();
        String inputPath = input.getPath();
        logger.log("REQUEST RECEIVED " + input);

        Response response = getWiremockResponse(input);

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(response.getStatus())
                .withBody(response.getBodyAsString());
    }

    private Response getWiremockResponse(APIGatewayProxyRequestEvent request) {
        DirectCallHttpServerFactory factory = new DirectCallHttpServerFactory();
        var config = wireMockConfig().httpServerFactory(factory);
        config.fileSource(new SingleRootFileSource("wiremock"));
        WireMockServer wm = new WireMockServer(config);
        wm.start();

        DirectCallHttpServer server = factory.getHttpServer();

        MockRequest requestToWiremock = mockRequest()
                .method(RequestMethod.fromString(request.getHttpMethod()))
                .url(request.getPath())
                .body(request.getBody());

        request.getHeaders().forEach(requestToWiremock::header);

        return server.stubRequest(requestToWiremock);
    }
}
