package ru.sdevteam.vinv.ui.controls;

import ru.sdevteam.vinv.main.KeyEvent;
import ru.sdevteam.vinv.main.MouseEvent;

public interface IControlListener
{	
	public void mouseMoved(MouseEvent ev);

	public void mousePressed(MouseEvent ev);
	public void mouseReleased(MouseEvent ev);
	
	public void mouseOver(MouseEvent ev);
	public void mouseOut(MouseEvent ev);
	
	public void mouseDragging(MouseEvent ev);
	public void mouseDraggingOutside(MouseEvent ev, Control dropTarget);
	public void mouseDragStart(MouseEvent ev);
	public void mouseDragEnd(MouseEvent ev, Control dragStarter);
	public void mouseDragDroppedOutside(MouseEvent ev, Control dropTarget);
	
	public void mouseDraggedInto(MouseEvent ev);
	public void mouseDraggedOut(MouseEvent ev);
	
	public void mouseScroll(MouseEvent ev);
	
	public void keyDown(KeyEvent ev);
	public void keyUp(KeyEvent ev);
	public void keyTyped(KeyEvent ev);
}
