FROM node

RUN apt-get update && apt-get install -y maven \
    ca-certificates bison pkg-config wget make libpng-dev unzip

WORKDIR /app

COPY . ./

RUN wget https://github.com/gbdev/rgbds/releases/download/v0.5.1/rgbds-0.5.1.tar.gz
RUN tar -xvf rgbds-0.5.1.tar.gz
RUN cd rgbds && make && make install
RUN cd ..
RUN make build
RUN cd ui && mkdir -pv tmp data && npm install
RUN wget https://github.com/PalletTownDevelopers/pokered/archive/refs/heads/randomizer.zip && unzip randomizer.zip && mv pokered-randomizer ui/tmp/pokered
CMD ["node", "ui/app.js"]
