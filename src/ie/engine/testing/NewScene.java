package ie.engine.testing;

import ie.engine.implementations.ParticleSystem;
import ie.engine.implementations.AudioEventLL.AudioEvent;
import ie.engine.interaction.AudioSync;
import ie.engine.loading.SongInfo;
import ie.engine.maths.Animation;
import ie.engine.maths.Coordinate;
import ie.engine.maths.KeyFrame;
import ie.engine.objects.Waves;
import processing.core.PApplet;

public class NewScene  extends PApplet{

    AudioSync audioSync;
    SongInfo songInfo; 
    AudioEvent tempEvent;
    boolean wasBeat;
    Debug debugger;
    Waves wavey;
    public void settings(){
        size(480, 480, P3D);
    }
    public void setup(){
        frameRate(60);
        String songName = "assets/audio/songs/nrgq.wav"; 
        audioSync = new AudioSync(this, songName);
        audioSync.play();
        songInfo = new SongInfo(songName);
        debugger = new Debug(this);
        wavey = new Waves(this);

    }

    float lerpValue;
    Animation testAnimation;
    float smoothBackground;
    public void draw(){
        debugger.start();
        //clear();
        camera();
        tempEvent = audioSync.isBeat();
        wasBeat = audioSync.wasBeat;
        background(0,0, smoothBackground);
        if(wasBeat){
            // runs if there was a beat provided
            smoothBackground = lerp(0.8f,tempEvent.volume, smoothBackground );

            audioSync.isBeat();
        } else {
            
            smoothBackground = lerp(0.8f,0, smoothBackground );
            
        }
        if(frameCount %2 == 1){
            wavey.update(1);

        }

        // debug camera
        camera();
        wavey.draw();
        hint(DISABLE_DEPTH_TEST); 
        noLights();
        if(debugger.doDebug){
            debugger.draw();
        }
    }
    public void keyPressed(){
        if(key == 'n' ){
            debugger.doDebug = !debugger.doDebug;
        }
    }
    static String[] gameArgs = {"Main"};
    public static void main(String[] args){
        PApplet.runSketch(gameArgs, new NewScene());
    }
}
