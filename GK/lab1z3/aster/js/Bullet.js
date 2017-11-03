Bullet.max = 5;

Bullet.all = {};

Bullet.speed = 0.022;

Bullet.count = 0;
Bullet.active_count = 0;

Bullet.live = 35;

function Bullet(){
    if(Bullet.active_count<Bullet.max){
        Sound.play('laser');
        Bullet.count++;
        Game.bullets++;
        Bullet.active_count++;
        
        this.id = Bullet.count.toString();
        Bullet.all[this.id] = this;
        
        
        this.live = 0;
        this.a = Game.ship.a;
        
        this.x = Game.ship.points[0].x;
        this.y = Game.ship.points[0].y;
        
        this.modX = Math.sin(Math.PI/180*this.a)*Bullet.speed*VAR.d;
        this.modY = -Math.cos(Math.PI/180*this.a)*Bullet.speed*VAR.d;
    }
}

Bullet.draw = function(){
    for(var b in Bullet.all){
         for(var r in Rock.all){
                if(Rock.all[r].hitTest(Bullet.all[b].x, Bullet.all[b].y)){
                    Bullet.all[b].live += Bullet.live;
                    Rock.all[r].remove();
                    break;
                }
            };
        if(Bullet.all[b].live<Bullet.live){
                
            Bullet.all[b].live++;
            if(Bullet.all[b].x<0){
                Bullet.all[b].x+=VAR.W;
            }else if (Bullet.all[b].x>VAR.W){
                Bullet.all[b].x-=VAR.W;
            }
            if(Bullet.all[b].y<0){
                Bullet.all[b].y+=VAR.H;
            }else if (Bullet.all[b].y>VAR.H){
                Bullet.all[b].y-=VAR.H;
            }
            Bullet.all[b].x += Bullet.all[b].modX;
            Bullet.all[b].y += Bullet.all[b].modY;

            Game.ctx.beginPath();
            Game.ctx.arc(Bullet.all[b].x, Bullet.all[b].y, 3, 0, Math.PI/180*360);
            Game.ctx.closePath();
            Game.ctx.fill();
    
        }else{
            Bullet.active_count--;
            delete Bullet.all[b];
        }
        
    }
}