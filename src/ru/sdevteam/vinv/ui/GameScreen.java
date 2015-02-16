package ru.sdevteam.vinv.ui;

import java.awt.Graphics;

import ru.sdevteam.vinv.game.Level;
import ru.sdevteam.vinv.game.logics.LevelController;

public class GameScreen extends Screen
{
	private LevelController levelCtrl;
	public GameScreen(int levelNum)
	{
		this.levelCtrl=new LevelController(this, Level.createLevel(levelNum));
	}
	

	@Override
	public void paint(Graphics g) 
	{	
		levelCtrl.paint(g);
	}

	@Override
	public void update() 
	{
		levelCtrl.update();
	}

}
