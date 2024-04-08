FROM sbtscala/scala-sbt:openjdk-17.0.2_1.8.0_3.2.0
WORKDIR /app
ENV WEATHER_API_KEY=$WEATHER_API_KEY
COPY .. /app
RUN ["sbt", "compile"]
EXPOSE 8080
CMD ["sbt", "run"]