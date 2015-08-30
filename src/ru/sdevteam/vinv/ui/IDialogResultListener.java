package ru.sdevteam.vinv.ui;

import ru.sdevteam.vinv.ui.MessageBox.DialogResult;

public interface IDialogResultListener
{	
	public void dialogResult(MessageBox sender, DialogResult result);
}
