package ru.sdevteam.vinv.main;

import java.awt.Frame;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame extends Frame implements MouseListener, KeyListener
{
	public GameCanvas canvas;
	public Timer timer;
	public ResourseManager resourseManager;
}
