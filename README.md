# Rocket-Chat
Rocket-Chat is a real-time messagging app created using a very simple stack: **ReactJs** in the frontend, **Spring Boot** in the backend, and **Socket.io** as the live-socket based
messagging tool to communicate between clients and a **MongoDB** Database.

The app is then been deployed on *Google Cloud Platform (GCP)* where it responds to requests coming from the frontend 
deploy on *AWS Amplify*.

The whole app is made of 3 very simple domains: "User", "Conversation" and "Contact". Here we will be describing in detail
each one of them.

### User
The app has a User domain where it stores its basic user information, such as username, password and image.
This informations are stored directly in the MongoDB database through a simple login/signup page that shows up only
once when the user makes its first access to the app.

Once the user has successfully logged-in, its basic info (id, username, image) are stored as an object in the localstorage
of the clients browser, so everytime the user lands on the website url it doesn't need to re-enter its credentials.

### Contact
Once a user has been created it has the possibility to add some contacts to its contact list through the username. The username
is unique for all the users so  there cannot be 2 users with the same username. Once the logged user has found its contact he/she can subsequently
add it to its list. 

### Conversation
Once a user has added at least one user to its contacts, it can then create a conversation with one or more users. If he/she chooses to create
a conversation with just one User, no further informations are needed and a conversation can take place. If he/she chooses
to add more contacts to its conversation then a Group name and a Group image can be provided to identify various groups. 
There are no checks on the Group name so there can be groups with the same name.
When the group is created, a message written in such group will be received by all the group members
simultaneously.

##Socket.io

The real-time messagging is possible thanks to [Socket.io](https://socket.io/). It uses WebSockets in its server to listen to events 
coming from different clients. It is sufficient to declare a "topic" (much like Apache Kafka) and attach a payload
to it, so the server then receives the payload if it is prepared to receive it on the declared "topic".

In our case we just send to the socket.io server the conversationId and the recipients as our payload. This way we can
send from the server back to all the clients (the recipients) the conversationId. 

In doing so, we have implemented a **useEffect** hook in the client where everytime we receive this payload from 
the socket.io server we trigger a re-render of the page, making a new request to the Backend to retrieve the new
messages received on conversation with the correct conversationId.

### CI/CD

For the deployment of the application we used 2 simple CI/CD pipeline implemented in Jenkins: one for the Backend and
the other one for the socket.io server.
The pipelines get triggered everytime there is a push to the repository. 

#### Backend Pipeline
It runs the Junits and archives the artifact.
Once the Junits are correctly compiled, it then pushes the artifact (after it has renamed it using the %BUILD_NUMBER% 
environment variable it uniquely identify each build), to the GCP Cloud Storage service, much similar to the AWS S3.
After it has successfully completed such task, it builds the docker image described in the corresponding Dockerfile
and pushes such image to Docker Hub.

After that we login to our remote e2-micro linux Debian machine in GCP Compute Engine storage, where we
stop the running container, delete the old image and pull the newly pushed image from the Docker Hub.
Once it has successfully got the updated image it runs the image in the machine, making it available to be called.

#### Socket Server Pipeline
The socket server is deployed on the same machine as the backend, it just listens on a different port. The pipeline is 
triggered always by a push in the repository, and it skips the Junit test part and directly build a Node JS
Docker image and pushes it to the Docker Hub. 

Once there, it then connects through ssh in the remote machine, and like for the Backend, it stops and delete the
previous container to download the new image and run it.

#### Author: Jason Shuyinta (DotJson)

Widely inspired by [Whatsapp Clone](https://www.youtube.com/watch?v=tBr-PybP_9c)



