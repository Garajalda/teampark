var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

server.listen(8080, function(){
	forceNew: true;
    console.log("server is now running...");
});

io.on('connection', function(socket){
    console.log("player connected!");
	socket.emit();
    socket.on('disconnect', function(){
        console.log("player disconnected");
    });
});