FROM node:14

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

ENV PORT=8080

EXPOSE 8080

CMD ["npm", "start"]

## use sudo for adding image to user guhhammer.

# sudo docker build -t guhhammer/projectv1 .           

# sudo docker run -p 5000:8080 guhhammer/projectv1

# sudo docker ps -a

