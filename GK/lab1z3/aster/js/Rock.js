
Rock.all = {};

Rock.count = 0;

Rock.data = [
	{r: 0.025, speed:0.0005,    minAngle:60, maxAngle:90},
	{r: 0.08,  speed:0.00025,   minAngle:50, maxAngle:70},
	{r: 0.2,   speed:0.0000625, minAngle:30, maxAngle:45}
];
function Rock(size,x,y){//size może się równać 0, 1 lub 2 (dwa to największe klocki)
	Rock.count++;
	// 
	this.id = Rock.count;
	Rock.all[this.id] = this;
	// jeśli size jest podany to to size kamienia równa się size, jeśli nie równa się 2
	this.size = size!==undefined ? size : 2;
	// tablica ze wszystkimi punktami, które tworzą pojedynczy kamień
	this.points = [];
    
	// Promień
	this.r = Rock.data[this.size].r;
	// Jeśli x i y nie są podane, x i y są losowane 
	this.x = x!==undefined ? x : ((VAR.rand(0,1) ? VAR.rand(0,3) : VAR.rand(7,10))/10)*VAR.W;
	this.y = y!==undefined ? y : ((VAR.rand(0,1) ? VAR.rand(0,3) : VAR.rand(7,10))/10)*VAR.H;
	
	this.modX = VAR.rand(1,10)*Rock.data[this.size].speed*(VAR.rand(0,1) ? 1 : -1);
	this.modY = VAR.rand(1,10)*Rock.data[this.size].speed*(VAR.rand(0,1) ? 1 : -1);
	
	var a = VAR.rand(0,40);
	while(a<360){
		
		a+= VAR.rand(Rock.data[this.size].minAngle,Rock.data[this.size].maxAngle);

		this.points.push({
			x:Math.sin(Math.PI/180*a)*this.r, //jego względna pozycja x
			y:Math.cos(Math.PI/180*a)*this.r // jego względna pozycja y
		});
	}
}
Rock.prototype.remove = function(){
    Sound.play('bum'+VAR.rand(1,2));
    if(this.size>0){
        for(var i=0, j=VAR.rand(2,4); i<j; i++ ){
                new Rock(this.size-1, this.x, this.y);
            }
    }
    
    Game.points++;
    delete Rock.all[this.id];
    Dot.add(this.x, this.y);
}
Rock.prototype.hitTest = function(x,y){
    if(x>this.x-this.r*VAR.d && x<this.x+this.r*VAR.d && y>this.y-this.r*VAR.d && y<this.y+this.r*VAR.d)
    {
        Game.hit_ctx.clearRect(this.x-this.r*VAR.d, this.y-this.r*VAR.d, this.r*2*VAR.d, this.r*2*VAR.d);
        
        Game.hit_ctx.beginPath();
        for (var i = 0; i < this.points.length; i++) {
            Game.hit_ctx[i===0 ? 'moveTo' : 'lineTo'](this.points[i].x*VAR.d+this.x, this.points[i].y*VAR.d+this.y);
        }
        
        Game.hit_ctx.closePath();
	    Game.hit_ctx.fill();
        
        if(Game.hit_ctx.getImageData(x,y,1,1).data[0]==255){
            return true;
        }  
    }
    return false;
};
// Rysowanie pojedynczego kamienia
Rock.prototype.draw = function() {
	this.x+=this.modX*VAR.d;
	this.y+=this.modY*VAR.d;
    if(this.x+this.r*VAR.d<0){
		this.x+=(VAR.d*this.r*2)+VAR.W;
	}else if(this.x-this.r*VAR.d>VAR.W){
		this.x-=(VAR.d*this.r*2)+VAR.W;
	}
	if(this.y+this.r*VAR.d<0){
		this.y+=(VAR.d*this.r*2)+VAR.H;
	}else if(this.y-this.r*VAR.d>VAR.H){
		this.y-=(VAR.d*this.r*2)+VAR.H;
	}

  
	Game.ctx.beginPath();
	for (var i = 0; i < this.points.length; i++) {
		Game.ctx[i===0 ? 'moveTo' : 'lineTo'](this.points[i].x*VAR.d+this.x,  this.points[i].y*VAR.d+this.y);
	}
  
	Game.ctx.closePath();
	Game.ctx.stroke();
};
// rysowanie wszystkich kamieni
Rock.draw = function(){
	Rock.num = 0;
	for(var r in Rock.all){
		Rock.num ++;
		Rock.all[r].draw();
	}
     if(Rock.num===0 && !Game.success )
        {
            
            Sound.play('win');
            Game.nextRound();
        }
};