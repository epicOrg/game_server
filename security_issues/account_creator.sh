#!/usr/bin/expect -f

# This script create an arbitrary number of accounts on the server.
# Created by Noris on 2015/05/24

# Number of account to create
set NUM 100

# Server parameters
set SERVER_ADDR "127.0.0.1"
set SERVER_PORT "7007"

# Registration parameters
set USERNAME "user"
set PASSWORD "password123"
set EMAIL "email"
set DOMAIN "@epic.org"

# Registration message in json format
set R1 "{\"service\":\"register\",\"password\":\""
set R2 "\",\"email\":\""
set R3 "\",\"username\":\""
set R4 "\"}"

# Connection to the server
spawn telnet $SERVER_ADDR $SERVER_PORT

# Accounts creation
for {set i 0} {$i < $NUM} {incr i 1} {
	send -- "$R1$PASSWORD$R2$EMAIL$i$DOMAIN$R3$USERNAME$i$R4\r"
}

# Close the connection
expect close
