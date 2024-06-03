FROM node:20 as build

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:alpine

COPY nginx.conf /etc/conf.d/default.conf

COPY --from=build /app/dist/train-together /usr/share/nginx/html

EXPOSE 4200