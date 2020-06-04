FROM nginx:latest

LABEL maintainer "ekfrl2815@gmail.com"

RUN rm /etc/nginx/conf.d/default.conf

COPY ./front-app.conf /etc/nginx/conf.d/default.conf

COPY ./build /usr/share/nginx/html 