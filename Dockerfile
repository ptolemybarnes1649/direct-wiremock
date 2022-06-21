FROM public.ecr.aws/lambda/java:11

COPY build/classes/java/main ${LAMBDA_TASK_ROOT}
COPY build/dependency/* ${LAMBDA_TASK_ROOT}

CMD [ "ptolemybarnes.Handler::handleRequest" ]
