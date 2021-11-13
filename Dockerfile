FROM quay.io/quarkus/quarkus-distroless-image:1.0
COPY target/*-runner /app
EXPOSE 8080
USER nonroot

RUN apt-get update && apt-get install -y bison pkg-config wget make libpng-dev unzip

WORKDIR /app

RUN wget https://github.com/gbdev/rgbds/releases/download/v0.5.1/rgbds-0.5.1.tar.gz
RUN tar -xvf rgbds-0.5.1.tar.gz
RUN cd rgbds && make && make install
RUN cd ..
RUN mkdir -pv tmp data
RUN wget https://github.com/PalletTownDevelopers/pokered/archive/refs/heads/randomizer.zip && unzip randomizer.zip && mv pokered-randomizer tmp/pokered

CMD ["./app", "-Dquarkus.http.host=0.0.0.0"]