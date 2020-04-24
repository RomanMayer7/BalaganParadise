
import java.applet.*;
import java.awt.*;

import javax.swing.ImageIcon;

public class GameManager extends Applet implements Runnable {
	Thread animation;
	Graphics offscreen;
	Image image;
	static final int REFRESH_RATE = 80; // in ms
	int CurrentPlayer=1;
	Image ufoImages[] = new Image[3]; // 3 ufo Images
	Image attackImages[] = new Image[3]; // 3 attack Images
	Image explodeImages[] = new Image[18];// 18 explode Images
	Image ufo2Images[]=new Image[1];
	Image ufo2atckImages[]=new Image[1];
	Image playersImages[]=new Image[5];//5 player images
	Image playernamesImg[]=new Image[5];//5 player disigned name banners
	Image playerplanesImg[]=new Image[5];//5 player planes intro images
	Image introButtonsImg[]=new Image[2];
	Image cityBG;//Background Image
	Image Marburg;//Intro background image
	Image GameNameImg;
	Image walla;//  congratulation image
	
    int StartMsg=90;//milliseconds to show starting message
    String StartMessage="Yalla Balagan!!!";//starting message
    Image StartMessageImg;//alternative image starting message
    
	Image gunImages[]=new Image[5]; // gun image
	AudioClip expsound; // explosion sound
	AudioClip yallasound;// starting game message
	AudioClip introsound;//introduction music theme
	AudioClip level1sound;
	AudioClip congratulis;//congratulation when killing 7 enemies
	AudioClip shot; //when shooting
	
	// state of game
	private boolean playing; // if game playing
	private int screen; // which screen to show
	static final int INTRO = 0; // intro screen
	static final int GAME_OVER = 1; // game over screen
	static final int GAMEPLAY=2;
	

	GunManager gm;
	UFOManager um;
	static final int UFO_VALUE = 130; // 130 points
	private int score;
	static final int MAX_LANDED = 5; // at most 5 aliens
	// can land
	static final int MAX_LEVEL = 9; //
	static final int MAX_ENERGY = 113; //
	private int numLanded; // num of aliens landed
	
	String scoreString = "Score: ";
	String ufoLandedString = "Enemies passed: ";
	String gameOverString = " GAME OVER ";
	String gameName = "BALAGAN IN PARADISE";
	int stringWidth;
	String clickString = "Shift-Click to Continue";
	String introString[] = new String[11];
	
	// fonts
	Font smallFont = new Font("Helvetica",Font.BOLD,12);
	Font mediumFont = new Font("Helvetica",Font.BOLD,14);
	Font bigFont = new Font("Helvetica",Font.BOLD,18);
	

	
	FontMetrics fm; // use to center string
	int width, height; // applet dimensions
	
	// customizable parameters
	int startLevel;
	int energyDec;
	boolean sound;
	GameFrame gf; //The applet frame
	//*******************************
	public void init() {
	showStatus("Loading Images -- WAIT!");
	setBackground(Color.black); // applet background
	width =610; // set applet dimensions
	height = 710;
	resize(width,height);
	
    gf=new GameFrame(this,width,height);
    
	loadImages();
	
	try {
		expsound = getAudioClip(getCodeBase(),"BOMB.WAV");
		yallasound=getAudioClip(getCodeBase(),"yalla.wav");
		introsound=getAudioClip(getCodeBase(),"the_prodigy_-_invaders_must_die_radio_edit.wav");
		level1sound=getAudioClip(getCodeBase(),"The Shadows - Riders In The Sky.wav");
		congratulis=getAudioClip(getCodeBase(),"walla.wav");
		shot=getAudioClip(getCodeBase(),"SHOT.wav");
		}
		catch (Exception e) {System.out.println("The problem with audioclip loading\n"+e.getMessage()); }
	
		um = new UFOManager(2,MAX_LEVEL,width,height,ufoImages,
				attackImages,explodeImages,ufo2Images,ufo2atckImages,
				this,expsound,congratulis,shot);
	gm = new GunManager(MAX_ENERGY,5,width,height,gunImages[CurrentPlayer],shot,
			um.getUFO(),um.getUFO2(),
			this);
	um.initialize(gm); // initialize gun parameters
	
	playing = false; // not playing
	screen = INTRO; // show intro screen
	MusicPlayer();//call Music Player to load audio track for the Intro

	
	image = createImage(width,height); // make offscreen buffer
	offscreen = image.getGraphics();
	offscreen.setFont(bigFont); // font for intro
	fm = offscreen.getFontMetrics(bigFont); // font metrics
	stringWidth = fm.stringWidth(gameName);
	introString[0] = "Your homecity of Marburg ";
	introString[1] = "was captured by invaders from Another World";
	introString[2] = "The suvivers have migrated to the Northern Quarter";
	introString[3] = "Army and police forces are based ";
	introString[4] = "in Northern towers ,but they are ";
	introString[5] = "not prepared for the massive air attack";
	introString[6] = "So you are the last hope of your";
	introString[7] = "city and the people of Marburg";
	introString[8]="YOUR MISSION TO STOP THE INVADERS!!! ";
	introString[9]="DON'T LET MORE THAN 5 ALLIEN SHIPS TO ENTER GUARDED ZONE!";
	introString[10]="DESTROY THE BASTARDS BY USING YOUR MOUSE OR KEYBOARD!";
	}
	
	public void  changePlaneImg(){
		gm.getGun().image=gunImages[CurrentPlayer]; // change the image of preferred player's plane
	}
	
	public void MusicPlayer(){
		
	switch(screen){
	case INTRO:introsound.play();
	break;
	case GAMEPLAY:introsound.stop();  level1sound.play();
	break;
	}
	}
	
	public void setCurrentPlayer(){
		CurrentPlayer = (CurrentPlayer + 1) % playersImages.length;
	}
	
	// CUSTOMIZE MANAGERS!
	public void setOptions(int startLevel,
	int energyDec,boolean sound) {
	if (startLevel >= 1 && startLevel < MAX_LEVEL) {
	this.startLevel = startLevel;
	um.setStartLevel(startLevel); // set startLevel
	}
	if (energyDec >= 0 && energyDec <= MAX_ENERGY) {
	this.energyDec = energyDec;
	gm.setEnergyDec(energyDec); // set energy lost
	}
	this.sound = sound;
	um.setSound(sound); // set sound
	}
	
	// increase score
	public void incrementScore() {
	score += UFO_VALUE;
	}

	// count number of ufo's landed
	public void alienLanded() {
	numLanded++;
	if (numLanded == MAX_LANDED) {
	gameOver();
	}
	}
	public void loadImages() {
	MediaTracker t = new MediaTracker(this);
	cityBG=getImage(getCodeBase(),"city.jpg");
	t.addImage(cityBG, 0);
	Marburg=getImage(getCodeBase(),"city-rg.jpg");
	t.addImage(Marburg, 0);
	StartMessageImg=getImage(getCodeBase(),"StartMessageImg.gif");
	t.addImage(StartMessageImg, 0);
	walla=getImage(getCodeBase(),"walla.jpg");
	GameNameImg=getImage(getCodeBase(),"GameName.jpg");
	t.addImage(GameNameImg, 0);
	for (int i=0; i<3; i++) {
	ufoImages[i] = getImage(getCodeBase(),"ufo"+(i+1)+".gif");
	t.addImage(ufoImages[i],0);
	attackImages[i] = getImage(getCodeBase(),
			"attack" +(i+1)+ ".gif");
			t.addImage(attackImages[i],0);
			}
			for (int i=0; i<explodeImages.length; i++) {
			explodeImages[i] = getImage(getCodeBase(),
			"Explosion" + (i+1) + ".gif");
			t.addImage(explodeImages[i],0);}
			for(int i=0;i<1;i++){
				ufo2Images[i]=getImage(getCodeBase(),"ufo_gun"+i+".gif");
				t.addImage(ufo2Images[i], 0);
			}
			for(int i=0;i<1;i++){
				ufo2atckImages[i]=getImage(getCodeBase(),"ufo_gun_atck"+i+".gif");
				t.addImage(ufo2Images[i], 0);
			}
			for (int i=0;i<playersImages.length;i++){
				playersImages[i]=getImage(getCodeBase(),"player"+(i+1)+".jpg");
				t.addImage(playersImages[i], 0);
				playerplanesImg[i]=getImage(getCodeBase(),"playerplane"+(i+1)+".gif");
				t.addImage(playerplanesImg[i], 0);
				playernamesImg[i]=getImage(getCodeBase(),"playername"+(i+1)+".gif");
				t.addImage(playernamesImg[i],0);
				gunImages[i] = getImage(getCodeBase(),"gun"+(i+1)+".gif");
				t.addImage(gunImages[i],0);
			}
			for (int i=0;i<introButtonsImg.length;i++){
				introButtonsImg[i]=getImage(getCodeBase(),"button"+(i+1)+".gif");
				t.addImage(introButtonsImg[i],0);}
	// wait for all images to finish loading //
	try {
	t.waitForAll();
	} catch (InterruptedException e) {
	}
	// check for errors //
	if (t.isErrorAny()) {
	System.out.println("Error Loading Images!"+t.getErrorsAny());	
	showStatus("Error Loading Images!");
	}
	else if (t.checkAll()) {
	System.out.println("Images successfully loaded");		
	showStatus("Images successfully loaded");
	}
	
	// initialize the BitmapLoop
	}

	// GameManager’s newGame():
	// initialize params for new game
	public void newGame() {
	changePlaneImg();
	screen=GAMEPLAY;
	playing=true;
	score = 0; // no score
	numLanded = 0; // no aliens landed
    StartMsg=90;//milliseconds to show starting message
	gm.newGame(); // call newGame in
	um.newGame(); // manager classes
	yallasound.play();//play start new game welcome message
	MusicPlayer();
	offscreen.setFont(smallFont); // set font in game
	
	
	}
	
	// handle game over
	public void gameOver() {
	if (playing) {
	playing = false;
	screen = GAME_OVER;
	gf.gameOver(); // restore menu items
	}
	}
	public boolean mouseMove(Event e,int x,int y) {
	gm.moveGun(x);
	return true;
	}
	public boolean mouseDrag(Event e,int x,int y) {
	gm.moveGun(x);
	return true;
	}
	public boolean mouseDown(Event e,int x,int y) {
		if (playing) {
		gm.fireMissile(x);
		}
		else if (screen == INTRO) { // start game for mouse
		// down on intro screen
		if((((2*width/3)+5)<=e.x)&&(e.x<=((2*width/3)+5+127))&&(((height/3)+260)<=e.y)&&(e.y<=((height/3)+260+36))){
		setCurrentPlayer();	
		}
		else{
		playing = true;
		gf.newItem.setEnabled(false);
		gf.abortItem.setEnabled(true);
		newGame();}
		}
		else if (screen == GAME_OVER) { // else go back to intro
		if (e.shiftDown()) { // if shift-click
		screen = INTRO;
		}
		}
		return true;
		}
	//check if (x,y) is inside square
	//public boolean inside(int x,int y) {
	//return (locx <= x && locy <= y &&
	//(locx + width >= x) &&
	//(locy + height >= y));
	//}
	public void start() {
	showStatus("Starting Game!");
	animation = new Thread(this);
	if (animation != null) {
		animation.start();
		}
		}
	// update managers. only update gun if playing
	public void updateManagers() {
	if (playing) {
	gm.update();
	}
	
	um.update();
	}
		// override update so it doesn't erase screen
		public void update(Graphics g) {
		paint(g);// paint the applet depending on mode of game
		}
		//
		public void paint(Graphics g) {
		if((playing)||(screen==GAMEPLAY)){
		offscreen.setColor(Color.white);
		offscreen.fillRect(0,0,width,height); // clear buffer
		offscreen.drawImage(cityBG, 0, 0, this);//draw the background image
		// tell UFOManager and GunManager to paint
		gm.paint(offscreen);
		um.paint(offscreen);
		
		// draw status info
		offscreen.setFont(mediumFont);
		offscreen.setColor(Color.red);
		offscreen.drawString(scoreString+score,width - 130,13);
		offscreen.drawString(ufoLandedString+numLanded,
		width - 130,27);
		offscreen.drawString("Level:"+" "+(um.level-1),width/2,13);
		//draw starting message
		if (StartMsg>0){
		//offscreen.drawString(StartMessage, width/2,height/2);
		offscreen.drawImage(StartMessageImg, (width/2)-130, (height/2)-60, this);
		StartMsg--; //decreasing the time to show message
		}
		g.drawImage(image,0,0,this);
		if (um.ufosKilled%7==0){
			g.drawImage(walla, width/2, 0, this);
			}
		}
		
		else if (screen == INTRO) {
			offscreen.setColor(Color.black);
			offscreen.fillRect(0,0,width,height); // clear buffer
			offscreen.setFont(smallFont);
			offscreen.setColor(Color.cyan);
			offscreen.drawString(scoreString+score,width - 113,13);
			offscreen.drawString(ufoLandedString+numLanded,
			width - 113,27);
			offscreen.drawImage(Marburg, 0, 110, this);
			um.paint(offscreen);
			//offscreen.setFont(bigFont);
			//offscreen.setColor(Color.green);
			//offscreen.drawString(gameName,
			//(width-stringWidth)/2,
			//height/6);
			offscreen.drawImage(GameNameImg, 0, 0, this);
			offscreen.drawImage(introButtonsImg[0],(2*width/3)+5,(height/3)+260,this);
			offscreen.drawImage(introButtonsImg[1],(2*width/3)+5,(height/3)+291,this);
			offscreen.drawImage(playerplanesImg[CurrentPlayer],(600-126),0, this);
			offscreen.drawImage(playersImages[CurrentPlayer],2*width/3,height/3,this);
			offscreen.drawImage(playernamesImg[CurrentPlayer], (2*width/3)+5,(height/3)+291+40,this);
			offscreen.setColor(Color.red);
			offscreen.setFont(mediumFont);
			// draw instructions
			for (int i=0; i<introString.length; i++) {
			offscreen.drawString(introString[i],13,(15+i)*height/26);
			}
			//offscreen.setColor(Color.green);
			//offscreen.drawString(introString[7],
			//(width-stringWidth)/2,
			//height*11/12);
			g.drawImage(image,0,0,this);
		}
		else if (screen == GAME_OVER) {
			offscreen.setColor(Color.black);
			offscreen.fillRect(0,0,width,height); // clear buffer
			// draw status info
			offscreen.setFont(smallFont);
			offscreen.setColor(Color.cyan);
			offscreen.drawString(scoreString+score,width - 113,13);
			offscreen.drawString(ufoLandedString+numLanded,
			width - 113,27);
			um.paint(offscreen);
			gm.paint(offscreen);
			offscreen.setFont(bigFont);
			offscreen.setColor(Color.red);
			offscreen.drawString(gameOverString,
					(width-stringWidth)/2,
					height/2);
					offscreen.setFont(mediumFont);
					offscreen.setColor(Color.green);
					offscreen.drawString(clickString,
					(width-stringWidth-17)/2,
					height*11/12);
					g.drawImage(image,0,0,this);
					}
}
		
		// the Video Game Loop
		public void run() {
		while (true) {
		repaint();
		updateManagers();
		Thread.currentThread().yield();
		try {
		Thread.sleep (REFRESH_RATE);
		} catch (Exception exc) { };
		}
		}
		public void stop() {
		showStatus("Game Stopped");
		if (animation != null) {
		animation.stop();
		animation = null;
		}
		}
		}