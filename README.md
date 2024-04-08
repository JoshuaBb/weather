# How to Run

1. First generate an API key from https://home.openweathermap.org
2. Make sure API_KEY is environmental variable

```bash
echo "export WEATHER_API_KEY=$key" >> ~/.bash_profile
```
3. Create and run the docker image
```bash 
docker build . -t weather
docker run -it -p 8080:8080 -e WEATHER_API_KEY=$WEATHER_API_KEY -e HOST=0.0.0.0 weather 
```

# Improvements
1. I feel like there is a way with less boilerplate to serialize the application.conf into scala case classes using pureconfig.
2. For a real application, I would probably persist more information on the on the weather conditions property rather than just an enumeration
3. The unit tests are a bit underdeveloped. There might also be benefit in mocking calls 
4. It has been awhile since I have used cats-effect and I have only used it on the surface level. I need a deeper dive. I feel like I am still structuring the program in an OOP manner. 
5. The IO abstraction in Cats I need a deeper dive in. I think for a production application, it needs to be tinkered a bit
6. I would persist the api call information in the application.conf file. It would be nice in case the environment ever changes
7. I used Scala 3. I haven't used that in a production environment yet and suspect it would benefit me to learn about some of the changes. 
8. I have a logback file, but am using SL4J within the app. Probably need to add a file for that as well. 

# Things I liked
1. I have never used the pureconf library before. I liked it quite a bit for initializing an app
   2. **Side Note:** I liked how easy it was to create a custom serializer/deserializer. Other frameworks I have used required a bit more boilerplate
2. I haven't used the cats-effect or http4s libaries to this extend before. I think they have an elegant structure.

# Why I made certain decisions 
1. I used cats-effect and http4s purely because I wanted to try some stuff out. I have used Tapir, Cats, and Doobie in the past for building an application. Overall, I think I enjoy the environment of Http4s a bit more, but I would miss the automatic Open-API generation of Tapir  