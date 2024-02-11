/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	//** Angle of motion */
	private static double ANGLE = 0.3*Math.PI;
	
	//PADDLE
	private static GRect PADDLE;
    //RANDOM GENERATOR
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	private int NumberOfBricks = 100;
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		gameInit();
	}
	
	
	
	private double getDx(int speed, double angle){
		return speed*Math.cos(angle);
	}
	
	private double getDy(int speed, double angle){
		return speed*Math.sin(angle);
	}
	
	private GOval createOval(){
		int r = BALL_RADIUS;
		GOval oval = new GOval(2*r,2*r);
		oval.setFilled(true);
		oval.setFillColor(Color.RED);
		add(oval,180,270);
		return oval;
}
	// process of the game
	private void gameProcess(GOval oval, int speed, double velX, double velY){
		AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
		addMouseListeners();
		//ball is in motion in this loop
		while(true){
			
			oval.move(velX, velY);
			oval.pause(10);
			
			if(oval.getY()<=0){
				velY= - velY;
				bounceClip.play();
			}
			if(oval.getX() + oval.getWidth() >= getWidth() || oval.getX()+oval.getWidth()<=oval.getWidth()){
				velX = -velX;
				bounceClip.play();
			}
			if(getCollidingObject(oval.getX(),oval.getY())!=null){
				velY=-velY;
				bounceClip.play();
			}
			if(getCollidingObject(oval.getX(),oval.getY()+ 2*BALL_RADIUS)!=null ){
				velY=-velY;
				bounceClip.play();
			}
			if(getCollidingObject(oval.getX()+BALL_RADIUS+1,oval.getY())!=null){
				velY=-velY;
				bounceClip.play();
			}
			if(getCollidingObject(oval.getX()+BALL_RADIUS+1,oval.getY()+2*BALL_RADIUS)!=null){
				velY=-velY;
				bounceClip.play();
			}
			
			if(getCollidingObject(oval.getX()+2*BALL_RADIUS,oval.getY()+2*BALL_RADIUS)!=null){
				velY=-velY;
				bounceClip.play();
			}
			if(NumberOfBricks==0){
				break;
			}
			if(oval.getY()>=getHeight()){
				break;
				
			}
			if(oval.getBounds().intersects(PADDLE.getBounds())){
				velY=-velY;
				bounceClip.play();
			}
			
			
		}
	}
	//generating one piece of brick
	 private GRect createBrick(){
		 return new GRect(BRICK_WIDTH,BRICK_HEIGHT);
	 }
	 // generating bricks
	 private void createBricks(){
		 for(int i =0; i<NBRICK_ROWS;i++){
			 for(int j =0; j<NBRICKS_PER_ROW;j++){
				 GRect rect = createBrick();
				 rect.setFilled(true);
				 rect.setFillColor(Color.RED);
				 if(i>7){
					 rect.setFillColor(Color.CYAN);
				 }
				 else if(i>5){
					 rect.setFillColor(Color.GREEN);
				 }
				 else if(i>3){
					 rect.setFillColor(Color.YELLOW);
				 }
				 else if(i>1){
					 rect.setFillColor(Color.ORANGE);
				 }
				 
				 add(rect,(BRICK_SEP+rect.getWidth())*j,(BRICK_SEP+rect.getHeight())*i+BRICK_Y_OFFSET);
			 }
		 }
	 }
	 
	 private GRect createPaddle(){
		 return new GRect(170,getHeight()-PADDLE_Y_OFFSET, PADDLE_WIDTH,PADDLE_HEIGHT);
	 }
	 
	 //mouse moving event for controlling paddle
	 public void mouseMoved(MouseEvent e) {
		if(e.getX()< getWidth()-PADDLE.getWidth() && e.getX()>=0){
			PADDLE.setLocation(e.getX(),PADDLE.getY());
		}
	 }
	 
	 private GObject getCollidingObject(double x, double y){
		GObject obj = getElementAt(x,y);
		if(obj !=null && obj !=PADDLE){
			GObject temp = obj;
			remove(obj);
			NumberOfBricks--;
			return temp;
		}
		return null;
	 }
	 
	//initialization of game
	private void gameInit(){
		
		
		//creating paddle
		PADDLE = createPaddle();
		add(PADDLE);
		// creating bricks
		createBricks();
		NumberOfBricks=100;
	 //creating ball and its horizontal and vertical velocities
		GOval oval = createOval();
		int speed = 10;
		double velY=4;
		double velX = rgen.nextDouble(1.0, 4.0);
		if (rgen.nextBoolean(0.5)) velX = -velX;
		gameProcess(oval,speed,velX,velY);
	}
	}
	
