Docker commands
 
`docker run -d --hostname my-rabbit --name some-rabbit -p 8080:15672 -p 5600:5672 rabbitmq:3-management`
`docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres`
