#!/bin/bash

sudo docker build -t uirandomizer .
sudo docker run -p 3000:3000 uirandomizer
