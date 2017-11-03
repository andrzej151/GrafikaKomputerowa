Sound = {
    active:false,
    init:function(){
        Sound.fx = new Howl({
            urls:['audio/asteroids.'+(Modernizr.audio.m4a ? 'm4a': 'ogg')],
            sprite:{
                bum1:[0,1100],
                bum2:[1125,1000],
                laser:[2150,290],
                win:[2475,575],
            },
            onload:Sound.loaded
        });
    },
    loaded:function(){
        Sound.active = true;
    },
    play:function(s){
        if(Sound.active){
            Sound.fx.play(s);
        }
        
    }
};