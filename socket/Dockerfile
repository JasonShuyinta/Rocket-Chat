FROM mhart/alpine-node:9 AS build
WORKDIR /socket
ADD package.json .
RUN npm install
ADD . .

FROM mhart/alpine-node:base-9
COPY --from=build /srv .
EXPOSE 5000
CMD [ "node", "server.js" ]