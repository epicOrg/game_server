Messages Exchange
=================

The messages exchange between the client and the server it's build on the
interface [IService](src/services/IService.java). Every service provided by the
server must implements this interface. In the additional package `messages`
there are more specific classes who create the server messages.

Every message use the [JSON](http://www.json.org/) format. More specifically we
used [org.json](http://www.json.org/javadoc/) library.

Now we will see each service message in great detail.


Register
--------

Through the client you can create an account on the server. A registration
message from the client who comply with the fields rules specified in
[FieldsValues](src/check_fields/FieldsValues.java) has always the following
fields:

	{"service":"register","password":"MY_PASSWORD","email":"MY_EMAIL","username":"MY_USERNAME"}

If there are no errors the server returns the following response:

	{"errors":{},"service":"register","noErrors":true}

If a field is wrong, an error occurs:

* Invalid username

  - Too short
	
	```
    {"errors":{"username":["short"]},"service":"register","noErrors":false}
    ```

  - Too long

	```
    {"errors":{"username":["long"]},"service":"register","noErrors":false}
    ```
    
  - Invalid character
  
	```
    {"errors":{"username":["invalidChar"]},"service":"register","noErrors":false}
    ```

  - It not contains a needed character (actually not implemented)
    	  
	```
    {"errors":{"username":["invalidChar"]},"service":"register","noErrors":false}
    ```

  - Already used
  
    ```
    {"errors":{"username":["alreadyUsed"]},"service":"register","noErrors":false}
    ```

* Invalid password

  - Too short
    
    ```
    {"errors":{"password":["short"]},"service":"register","noErrors":false}
    ```

  - Too long
    
    ```
    {"errors":{"password":["long"]},"service":"register","noErrors":false}
    ```

  - Invalid character (actually not implemented)

    ```
    {"errors":{"password":["invalidChar"]},"service":"register","noErrors":false}
    ```

  - It not contains a needed character

    ```
    {"errors":{"password":["invalidChar"]},"service":"register","noErrors":false}
    ```

* Invalid email

  - Invalid format

    ```
    {"errors":{"email":["invalidField"]},"service":"register","noErrors":false}
    ```

  - Invalid domain

    ```
    {"errors":{"email":["invalidDomain"]},"service":"register","noErrors":false}
    ```

  - Already used

    ```
    {"errors":{"email":["alreadyUsed"]},"service":"register","noErrors":false}
    ```

Multiple errors can obviously occurs, like the following example:

	{"errors":{"username":["long"],"email":["invalidField"],"password":["short","invalidChar"]},"service":"register","noErrors":false}


Login
------

A login client request has always the following format:

	{"service":"login","password":"PASSWORD","username":"USERNAME"}

If all the fields are ok the response is the following:

	{"username":"USERNAME","service":"login","hashcode":HASHCODE,"noErrors":true}

In which the hashCode is a login-created code who identify an user. The
hashCode is generated in [OnlineUser](src/online_management/OnlineUser.java).

If a field is wrong, the server send always this response:

	{"username":"marx","service":"login","hashcode":0,"noErrors":false}

This error can occurs when:

* The password linked to the username is wrong
* The username is not registered
* The user is already signed in


Rooms
-----

When the login phase is completed, the client automatically send to the server
a request for the list of the available rooms:

	{"serviceType":"list","service":"rooms","username":"USERNAME","hashcode":HASHCODE}

The server respond with the list. For every room are specified the number of
the users actually in the room (`currentPlayers`), and the maximum number of
users in that room (`maxPlayers`). For example:

	{"service":"rooms","list":{"ROOM1 ":{"currentPlayers":X¹,"maxPlayers":Y¹},"ROOM2 ":{"currentPlayers":X²,"maxPlayers":Y²}},"serviceType":"list","noErrors":true}

If there are no rooms the server send an empty list:

	{"service":"rooms","list":{},"serviceType":"list","noErrors":true}

An user can create a room. In this case the client send the following message:

	{"service":"rooms","username":"USERNAME","hashcode":HASHCODE,"serviceType":"create","name":"NAME_OF_THE_ROOM"}

The response of the server is the list of available rooms.
But some errors can occour in the creation of a room:

* The choosen room's name is already used

	{"errors":{"errors":["createErrorAlreadyPresent"]},"service":"rooms","serviceType":"create","noErrors":false}

* The choosen room's name is invalid (too short, too long, etc.)

	{"errors":{"errors":["createErrorInvalidname"]},"service":"rooms","serviceType":"create","noErrors":false}


CurrentRoom
-----------

When a user join a room, the client send this message:

	{"service":"rooms","username":"USERNAME","hashcode":HASHCODE,"serviceType":"join","name":"NAME_OF_THE_ROOM"}

If the room is available, the server send this response:

	{"result":true,"name":"NAME_OF_THE_ROOM","service":"rooms","serviceType":"join","noErrors":true}

Then the client ask for some room's information (like the already presents players):

	{"service":"currentRoom","username":"USERNAME","hashcode":HASHCODE,"serviceType":"playerList","name":"NAME_OF_THE_ROOM"}

The response from the server is the following:

	{"maxPlayers":X,"service":"currentRoom","team":[{"teamColor":COLOR¹,"name":"Team 1","list":[{"username":"USERNAME1"},{"username":"USERNAME2"}]},{"teamColor":COLOR²,"name":"Team 2","list":[{"username":"USERNAME3"},{"username":"USERNAME4"}]}],"serviceType":"playerList"}

In this example there are two teams, and every team has two players. In the
following example there is only one team with three single players:

	{"maxPlayers":X,"service":"currentRoom","team":[{"teamColor":COLOR,"name":"Team 1","list":[{"username":"USERNAME1"},{"username":"USERNAME1"},{"username":"USERNAME3"}]}],"serviceType":"playerList"}

Some errors can occours:

* Full room or non-existent room

    {"result":false,"name":"NAME_OF_THE_ROOM","service":"rooms","serviceType":"join","noErrors":false}

* If the user try to join a room who has alredy joined, the server do nothing,
  and responds with the previous explained messages.


Audio
-----

When a room it's full, the game automatically starts, and the client send to
the server the port choosen for the audio streaming:

	{"service":"audio","username":"USERNAME","hashcode":HASHCODE,"audioPortClient":PORT,"name":"NAME_OF_THE_ROOM"}

The server responds with the server side port:

	{"audioPortServer":PORT,"service":"audio"}


Game
----

In addition to the audio port, when a room it's full the client send to the
server a request for the game map:

	{"service":"game","username":"USERNAME","hashcode":HASHCODE,"serviceType":"map","name":"NAME_OF_THE_ROOM"}

Then the server send to the client the map:

	{"height":"LENGTH_OF_THE_MAP","items":[{"position":"POSITION_ITEM¹","texture":"TEXTURE_ITEM¹","object":"ITEM¹","size":"SIZE_ITEM¹"},{"position":"POSITION_ITEM²","texture":"TEXTURE_ITEM²","object":"ITEM²","size":"SIZE_ITEM²"},{"position":"POSITION_ITEM³","texture":"TEXTURE_ITEM³","object":"ITEM³","size":"SIZE_ITEM³"}],"players":"SPAWN_POINT","width":"WIDTH_OF_THE_MAP","service":"game","serviceType":"map"}

In this example the map consists of three items.

If an user ask for a map of a room in which is not joined, the server send the
map however, with no errors.

Before the game really starts, there is a loading phase. When the client it's
ready it send to the server a message to say this:

	{"service":"game","username":"USERNAME","hashcode":HASHCODE,"serviceType":"status","ready":true,"name":"NAME_OF_THE_ROOM"}

Then the client starts to send the position of the player in the map, to update
other clients positions list:

	{"position":{"z":Z,"y":Y,"x":X},"service":"game","username":"USERNAME","serviceType":"positions","direction":{"z":Z,"y":Y,"x":X},"name":"NAME_OF_THE_ROOM"}

The server responds with the positions of the other players:

	{"players":[],"service":"game","serviceType":"positions"}
