FROM public.ecr.aws/lambda/java:11

COPY build/classes/java/main ${LAMBDA_TASK_ROOT}
COPY build/dependency/* ${LAMBDA_TASK_ROOT}/lib/
COPY build/resources/main/wiremock/* ${LAMBDA_TASK_ROOT}/wiremock/mappings

CMD [ "ptolemybarnes.Handler::handleRequest" ]