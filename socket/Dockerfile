FROM mhart/alpine-node:9 AS build
WORKDIR /socket
ADD package.json .
RUN npm install
ADD . .

FROM mhart/alpine-node:base-9
COPY --from=build /socket .
EXPOSE 444
CMD [ "node", "server.js" ]