FROM node

RUN apt-get update && apt-get install -y maven \
    ca-certificates bison pkg-config wget git make libpng-dev tar

WORKDIR /app

COPY . ./

RUN wget https://github.com/gbdev/rgbds/releases/download/v0.5.1/rgbds-0.5.1.tar.gz
RUN tar -xvf rgbds-0.5.1.tar.gz
RUN cd rgbds && make && make install
RUN cd ..
RUN make build
RUN mkdir -pv ui/tmp ui/data
RUN git clone https://github.com/pret/pokered.git ui/tmp/pokered
CMD ["node", "ui/app.js"]
