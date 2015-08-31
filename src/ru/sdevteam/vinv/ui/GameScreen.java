package ru.sdevteam.vinv.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import ru.sdevteam.vinv.game.Decoration;
import ru.sdevteam.vinv.game.FlameThrower;
import ru.sdevteam.vinv.game.IWireConnectable;
import ru.sdevteam.vinv.game.MachineGun;
import ru.sdevteam.vinv.game.GameObject;
import ru.sdevteam.vinv.game.Level;
import ru.sdevteam.vinv.game.Tower;
import ru.sdevteam.vinv.game.WireHolder;
import ru.sdevteam.vinv.game.logics.LevelController;
import ru.sdevteam.vinv.main.GameCanvas;
import ru.sdevteam.vinv.main.MouseEvent;
import ru.sdevteam.vinv.main.ResourceManager;
import ru.sdevteam.vinv.ui.MessageBox.DialogResult;
import ru.sdevteam.vinv.ui.controls.Button;
import ru.sdevteam.vinv.ui.controls.Control;
import ru.sdevteam.vinv.ui.controls.FlowLayout;
import ru.sdevteam.vinv.ui.controls.FocusableControl;
import ru.sdevteam.vinv.ui.controls.IButtonPressedListener;
import ru.sdevteam.vinv.ui.controls.FlowLayout.LayoutType;
import ru.sdevteam.vinv.utils.Colors;
import ru.sdevteam.vinv.utils.DebugInfo;
import ru.sdevteam.vinv.utils.Fonts;

public class GameScreen extends Screen implements IButtonPressedListener
{
	private LevelController levelCtrl;
	
	// viewport
	private float viewportX;
	public float getViewportX() { return viewportX; }
	private float viewportY;
	public float getViewportY() { return viewportY; }
	private float viewportWidth;
	public float getViewPortWidth() { return viewportWidth; }
	private float viewportHeight;
	public float getViewPortHeight() { return viewportHeight; }
	private int scaleFactor;
	public int getScaleFactor() { return scaleFactor; }
	
	// images
	private static BufferedImage 
	panel=ResourceManager.getBufferedImage("ui/panel"),
	res_r=ResourceManager.getBufferedImage("ui/res_resources"),
	res_h=ResourceManager.getBufferedImage("ui/res_humans"),
	res_p=ResourceManager.getBufferedImage("ui/res_power");
	
	
	public GameScreen(int levelNum)
	{
		this.levelCtrl=new LevelController(this, Level.createLevel(levelNum));
		scaleFactor=1;
		viewportX=0;
		viewportY=0;
		viewportHeight=GameCanvas.getInstance().getCanvasHeight()/scaleFactor;
		viewportWidth=GameCanvas.getInstance().getCanvasWidth()/scaleFactor;
		
		setFont(Fonts.main(10));
		
		setSize((int)viewportWidth, (int)viewportHeight);
		
		// взаимодействие с уровнем
		addControl(this.new LevelWrapperControl(getWidth(), getHeight(), this));
		
		// кнопки панели
		pauseBtn=this.new SwitchableButton(getWidth()-31, getHeight()-31, 
						"ui/pause_r", "ui/pause_h", "ui/pause_p");
		pauseBtn.addButtonPressedListener(this);
		addControl(pauseBtn);
		
		buildBtn=this.new SwitchableButton(getWidth()-70, getHeight()-31, 
				"ui/build_r", "ui/build_h", "ui/build_p");
		buildBtn.addButtonPressedListener(this);
		addControl(buildBtn);
		
		// "меню" постройки
		buildMenu=new FlowLayout(LayoutType.VERTICAL);
		buildMenu.moveTo(buildBtn.getX()-10, buildBtn.getY()-10);
		buildMenu.setMargin(2);
		
		tower1Btn=this.new TowerBuildButton(new FlameThrower());
		tower1Btn.addButtonPressedListener(this);
		buildMenu.addControl(tower1Btn);
		
		tower2Btn=this.new TowerBuildButton(new MachineGun());
		tower2Btn.addButtonPressedListener(this);
		buildMenu.addControl(tower2Btn);
		
		wireHolderBtn=this.new TowerBuildButton(new WireHolder());
		wireHolderBtn.addButtonPressedListener(this);
		buildMenu.addControl(wireHolderBtn);
		
		buildMenu.setY(buildMenu.getY()-buildMenu.getHeight());
		buildMenu.setStartPoint(buildMenu.getX(), buildMenu.getY());
		this.addControl(buildMenu);
		
		switchBuildMode(false);
	}
	
	//
	// Обработка окончания игры
	//
	public void onVictory()
	{
		MessageBox victoryBox=new MessageBox("Победа!", "Все жуки уничтожены, единственный уровень в этой игре чист.");
		victoryBox.addButton(DialogResult.YES, "Выход");
		victoryBox.addButton(DialogResult.NO, "В меню");
		victoryBox.addDialogResultListener(new IDialogResultListener()
		{
			@Override
			public void dialogResult(MessageBox sender, DialogResult result)
			{
				if(result==DialogResult.YES)
				{
					System.exit(0);
				}
				if(result==DialogResult.NO)
				{
					GameCanvas.getInstance().setActiveScreen(new GameScreen(1));
				}
			}
		});
		this.showMessageBox(victoryBox);
	}
	public void onDefeat()
	{
		levelCtrl.pause();
		MessageBox defeatBox=new MessageBox("Поражение", "Ну что ты за днище такое, слил единственный уровень в игре!");
		defeatBox.addButton(DialogResult.YES, "Заново");
		defeatBox.addButton(DialogResult.NO, "В меню");
		defeatBox.addDialogResultListener(new IDialogResultListener()
		{
			@Override
			public void dialogResult(MessageBox sender, DialogResult result)
			{
				if(result==DialogResult.YES)
				{
					GameCanvas.getInstance().setActiveScreen(new GameScreen(1));
				}
				if(result==DialogResult.NO)
				{
					GameCanvas.getInstance().setActiveScreen(new GameScreen(1));
				}
			}
		});
		this.showMessageBox(defeatBox);
	}
	
	//
	// Интерфейс
	//
	private SwitchableButton pauseBtn, buildBtn;
	private MessageBox mb;
	// вспомогательное, для постройки
	public Tower towerToPlace=null;
	public Decoration decoToPlace=null;
	
	public void buttonPressed(Button sender)
	{
		if(sender==pauseBtn)
		{
			if(pauseBtn.switched)
			{
				levelCtrl.pause();
				/*
				mb=new MessageBox("Title", "Ву-ха-ха мсжбокс такс такс што тут у нас ага! Переносы строк такс такс не работает ничего жопа всё накрылось а-а-а-а-а-а!");
				mb.addButton(DialogResult.OK);
				mb.addButton(DialogResult.CANCEL);
				mb.addButton(DialogResult.NONE);
				showMessageBox(mb);*/
			}
			else
			{
				levelCtrl.unpause();
				//mb.close();
			}
			return;
		}
		
		if(sender==buildBtn)
		{
			if(buildBtn.switched)
			{
				//towerToPlace=new FlameThrower();
			}
			else
			{
				//towerToPlace=null;
			}
			switchBuildMode(buildBtn.switched);
		}
		
		if(sender==tower1Btn)
		{
			tower2Btn.switched=false;
			wireHolderBtn.switched=false;
			
			towerToPlace=new FlameThrower();
			decoToPlace=null;
		}
		
		if(sender==tower2Btn)
		{
			tower1Btn.switched=false;
			wireHolderBtn.switched=false;
			
			towerToPlace=new MachineGun();
			decoToPlace=null;
		}
		
		if(sender==wireHolderBtn)
		{
			tower1Btn.switched=false;
			tower2Btn.switched=false;
			
			towerToPlace=null;
			decoToPlace=new WireHolder();
		}
	}
	
	// TODO: возможно, сделать массив кнопок
	private SwitchableButton tower1Btn, tower2Btn, wireHolderBtn;
	private FlowLayout buildMenu;
	private void switchBuildMode(boolean state)
	{
		buildMenu.setVisibility(state);
		
		if(!state)
		{
			towerToPlace=null;
			decoToPlace=null;
			
			tower1Btn.switched=false;
			tower2Btn.switched=false;
			wireHolderBtn.switched=false;
		}
	}
	
	
	
	@Override
	synchronized public void paint(Graphics g) 
	{
		//
		// Отрисовка уровня
		//
		((Graphics2D)g).scale(scaleFactor, scaleFactor);
		((Graphics2D)g).translate(-viewportX, -viewportY);
		levelCtrl.paint(g);
		((Graphics2D)g).translate(viewportX, viewportY);
		((Graphics2D)g).scale(1F/scaleFactor, 1F/scaleFactor);
		
		//
		// Отрисовка интерфейса
		//
		//((Graphics2D)g).scale(2F, 2F);
		int penx=0, peny=getHeight();
		g.setFont(getFont());
		
		//панель
		peny-=panel.getHeight();
		while(penx<getWidth())
		{
			g.drawImage(panel, penx, peny, null);
			penx+=panel.getWidth();
		}
		penx=4; peny+=3; g.setColor(Colors.white());
		
		g.drawImage(res_r, penx, peny, null); 
		g.drawString(levelCtrl.getPlayer().getResources()+"", penx+30, peny+10+getFontMetrics(g).getAscent());
		penx+=res_r.getWidth()+10;
		
		g.drawImage(res_p, penx, peny, null);
		g.drawString(levelCtrl.getPlayer().getBasePower()+"", penx+30, peny+10+getFontMetrics(g).getAscent());
		penx+=res_p.getWidth()+10;
		
		g.drawImage(res_h, penx, peny, null);
		g.drawString(levelCtrl.getPlayer().getHumansCount()+"", penx+30, peny+10+getFontMetrics(g).getAscent());
		penx+=res_h.getWidth()+10;
		
		paintChildren(g);
		
		//((Graphics2D)g).scale(0.5F, 0.5F);
		// endof Интерфейс
	}

	@Override
	synchronized public void update() 
	{
		super.update();
		
		levelCtrl.update();
	}
	
	
	private class LevelWrapperControl extends FocusableControl
	{
		GameScreen parent;
		boolean dragging;
		float oldx, oldy; // для перетаскивания окна просмотра
		Rectangle hilightedCell; // подсвеченная ячейка при постройке
		
		// провода и их отрисовка
		IWireConnectable clicked; // первый столб
		int mx, my; // мышь
		
		public LevelWrapperControl(int w, int h, GameScreen parent)
		{
			moveTo(0, 0);
			setSize(w, h);
			this.parent=parent;
			dragging=false;
			oldx=parent.viewportX; oldy=parent.viewportY;
		}
		
		@Override
		protected void onMouseDragStart(MouseEvent ev)
		{
			super.onMouseDragStart(ev);
			
			if(ev.getActualState().isLeftDown())
			{
				dragging=true;
				oldx=parent.viewportX; oldy=parent.viewportY;
				DebugInfo.addMessage("mds");
			}
		}
		@Override
		protected void onMouseDragging(MouseEvent ev)
		{
			super.onMouseDragging(ev);
			
			if(dragging)
			{
				viewportX=oldx-(ev.getMouseX()-this.getMouseDragStartX())/scaleFactor;
				viewportY=oldy-(ev.getMouseY()-this.getMouseDragStartY())/scaleFactor;
				
				DebugInfo.addMessage("vp "+parent.viewportX+"; "+parent.viewportY);
			}
		}
		@Override
		protected void onMouseDragEnd(MouseEvent ev, Control dragStarter)
		{
			super.onMouseDragEnd(ev, dragStarter);
			dragging=false;
		}
		@Override
		protected void onMouseDragDroppedOutside(MouseEvent ev,	Control dropTarget)
		{
			super.onMouseDragDroppedOutside(ev, dropTarget);
			dragging=false;
		}
		
		@Override
		protected void onMouseScroll(MouseEvent ev)
		{
			super.onMouseScroll(ev);
			
			// <0 - приближение, >0 - отдаление
			
			int oldScf=scaleFactor;
			
			if(ev.getDelta()<0) 
			{
				if(scaleFactor<8) 
				{
					scaleFactor*=2;
					viewportWidth/=2;
					viewportHeight/=2;
					
					
				}
			}
			else 
				if(ev.getDelta()>0)
				{
					if(scaleFactor>1) 
					{
						scaleFactor/=2;
						viewportWidth*=2;
						viewportHeight*=2;
					}
				}
			
			if(ev.getDelta()!=0)
			{
				float k;
				if(ev.getDelta()<0) k=0.5F;
				else k=2F;
				
				// TODO: correct the formula
				//viewportX+=(1-k)*(ev.getMouseX()-viewportX);
				//viewportY+=(1-k)*(ev.getMouseY()-viewportY);
				/*float dx=(ev.getMouseX()*(1-k));
				float dy=(ev.getMouseY()*(1-k));
				viewportX+=dx;
				viewportY+=dy;*/
				//k=scaleFactor/oldScf;
				viewportX+=ev.getMouseX()/(1-k)/scaleFactor;
				viewportY+=ev.getMouseY()/(1-k)/scaleFactor;
				
				//int dx=(int)(x*(zoom/newZoom-1)/zoom);
				//int dy=(int)(y*(zoom/newZoom-1)/zoom);
				
				System.out.println("New viewport: ("+viewportX+"; "+viewportY+").(" +
				viewportWidth+"; "+viewportHeight+"), scf="+scaleFactor);
				
				DebugInfo.addMessage("("+viewportX+"; "+viewportY+").(" +
						viewportWidth+"; "+viewportHeight+"), scf="+scaleFactor +
						"mpos: ("+ev.getMouseX()+"; "+ev.getMouseY()+")");
			}

			//if(viewportX<0) viewportX=0;
			//if(viewportY<0) viewportY=0;
			// TODO: проверять две другие границы уровня
		}
		
		@Override
		protected void onMouseMoved(MouseEvent ev)
		{
			super.onMouseMoved(ev);
			if(towerToPlace!=null || decoToPlace!=null)
			{
				hilightedCell=levelCtrl.getCellBounds((int)viewportX+ev.getMouseX()/scaleFactor,
						(int)viewportY+ev.getMouseY()/scaleFactor);
			}
			else
			{
				hilightedCell=null;
			}
			
			mx=ev.getMouseX(); my=ev.getMouseY();
		}
		
		@Override
		protected void onMouseReleased(MouseEvent ev)
		{
			super.onMouseReleased(ev);
			
			if(towerToPlace!=null)
			{
				levelCtrl.placeTower(towerToPlace,
						(int)viewportX+ev.getMouseX()/scaleFactor,
						(int)viewportY+ev.getMouseY()/scaleFactor);
				buildBtn.press();
			}
			else if(decoToPlace!=null)
			{
				levelCtrl.placeDecoration(decoToPlace,
						(int)viewportX+ev.getMouseX()/scaleFactor,
						(int)viewportY+ev.getMouseY()/scaleFactor);
				buildBtn.press();
			}
			else
			{
				try
				{
					IWireConnectable deco=(IWireConnectable)levelCtrl.getDecoUnderCursor(
							(int)viewportX+ev.getMouseX()/scaleFactor,
							(int)viewportY+ev.getMouseY()/scaleFactor);
					
					if(deco==null)
					{
						// декораций нет; может, есть башня?
						
						deco=(IWireConnectable)levelCtrl.getTowerUnderCursor(
								(int)viewportX+ev.getMouseX()/scaleFactor,
								(int)viewportY+ev.getMouseY()/scaleFactor);
					}
					if(deco==null)
					{
						// мдас. ну что же, кто-то не попал
						clicked=null;
						return;
					}
					
					if(clicked==null)
					{
						// первый столб (или не столб) выбран
						DebugInfo.addMessage("New 1st obj!");
						clicked=deco;
					}
					else
					{
						// второй столб (или не столб) выбран
						if(clicked==deco)
						{
							// == клик по пустому месту
							clicked=null;
							DebugInfo.addMessage("clicked==deco");
						}
						else
						{
							// проводим питание сюда
							boolean succeed=false;
							if(clicked.isConductor())
							{
								// первый тыкнутый - столб
								((WireHolder)clicked).connectTo(deco);
								succeed=true;
							}
							else if(deco.isConductor())
							{
								// нет, тогда второй тыкнутый - столб
								((WireHolder)deco).connectTo(clicked);
								succeed=true;
							}
							
							// ну и в любом случае, вот так
							clicked=null;
						}
					}
				}
				catch(ClassCastException ex)
				{
					// не IWireConnectable, == клик по пустому месту
					clicked=null;
					DebugInfo.addMessage("not a IWireConnectable");
				}
			}
		}
		
		@Override
		public void update(){}
		
		@Override
		public void paint(Graphics g)
		{
			if(hilightedCell!=null)
			{
				g.setColor(Colors.blue());
				g.drawRect((hilightedCell.x-(int)viewportX)*scaleFactor,
						(hilightedCell.y-(int)viewportY)*scaleFactor,
						hilightedCell.width*scaleFactor, hilightedCell.height*scaleFactor);
			}
			
			if(clicked!=null)
			{
				g.setColor(Colors.black());
				g.drawLine(
						(int)((((GameObject)clicked).getX()-(int)viewportX)*scaleFactor),
						(int)((((GameObject)clicked).getY()-(int)viewportY)*scaleFactor),
						mx, my);
			}
		}
	}
	
	private class SwitchableButton extends Button
	{
		BufferedImage r, h, p;
		boolean switched;
		
		public SwitchableButton(int x, int y, String rname, String hname, String pname)
		{
			moveTo(x, y);
			r=ResourceManager.getBufferedImage(rname);
			h=ResourceManager.getBufferedImage(hname);
			p=ResourceManager.getBufferedImage(pname);
			
			setSize(r.getWidth(), r.getHeight());
		}
		
		@Override
		public void paint(Graphics g)
		{
			if(switched)
			{
				g.drawImage(p, getX(), getY(), null);
				return;
			}
			if(isHovered())
			{
				g.drawImage(h, getX(), getY(), null);
				return;
			}
			g.drawImage(r, getX(), getY(), null);
		}
		
		@Override
		protected void onPressed()
		{
			super.onPressed();
			switched=!switched;
		}
	}
	
	private class TowerBuildButton extends SwitchableButton
	{
		BufferedImage d;
		GameObject icon;
		
		
		public TowerBuildButton(GameObject icon)
		{
			super(0, 0, "ui/tower_button_r", "ui/tower_button_h", "ui/tower_button_p");
			this.icon=icon;
			d=ResourceManager.getBufferedImage("ui/tower_button_d");
			
			icon.moveTo(getX()+getWidth()/2, getY()+getHeight()/2);
		}
		
		
		@Override
		public void paint(Graphics g)
		{
			if(!isEnabled())
				g.drawImage(d, getX(), getY(), null);
			else
				super.paint(g);
			
			icon.getSprite().paint(g);
		}
		
		// поскольку экземпляры класса будут переразмещаться после их создания
		// внутри соотв. алгоритма FlowLayout, в который их предстоит добавить,
		// то такая штука нам точно не помешает
		@Override
		protected void onLocationChanged(int oldx, int oldy)
		{
			if(icon!=null) icon.moveTo(getX()+getWidth()/2, getY()+getHeight()/2);
		}
	}
}
