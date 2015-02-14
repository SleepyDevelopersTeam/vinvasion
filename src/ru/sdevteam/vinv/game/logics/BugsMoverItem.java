package ru.sdevteam.vinv.game.logics;

import ru.sdevteam.vinv.game.Bug;
import ru.sdevteam.vinv.utils.Vector2F;
class BugsMoverItem 
{
	int section;
	Vector2F velocity;
	Bug bug;
	BugsMoverItem(Bug a)
	{
		section = 0; 
		bug = a;
	}
}
