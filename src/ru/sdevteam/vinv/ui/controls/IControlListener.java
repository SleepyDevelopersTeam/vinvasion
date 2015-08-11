package ru.sdevteam.vinv.ui.controls;

import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;

public interface IControlListener
{	
	public void mouseMoved(MouseEvent ev, Control sender);

	public void mousePressed(MouseEvent ev, Control sender);
	public void mouseReleased(MouseEvent ev, Control sender);
	
	public void mouseOver(MouseEvent ev, Control sender);
	public void mouseOut(MouseEvent ev, Control sender);
	
	public void mouseDragging(MouseEvent ev, Control sender);
	public void mouseDraggingOutside(MouseEvent ev, Control sender, Control dropTarget);
	public void mouseDragStart(MouseEvent ev, Control sender);
	public void mouseDragEnd(MouseEvent ev, Control sender, Control dragStarter);
	public void mouseDragDroppedOutside(MouseEvent ev, Control sender, Control dropTarget);
	
	public void mouseDraggedInto(MouseEvent ev, Control sender);
	public void mouseDraggedOut(MouseEvent ev, Control sender);
	
	public void mouseScroll(MouseEvent ev, Control sender);
	
	public void keyDown(KeyEvent ev, Control sender);
	public void keyUp(KeyEvent ev, Control sender);
	public void keyTyped(KeyEvent ev, Control sender);
}
