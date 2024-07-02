 FROM bellsoft/liberica-openjdk-alpine:17

 RUN apk add curl jq

 WORKDIR /home/selenium-docker

 ADD target/docker-resources        ./
 ADD runner.sh                      runner.sh

 RUN dos2unix runner.sh

 ENTRYPOINT sh runner.sh
