var tabs=[];
Tabs=
{
	createTab: function(button, page)
	{
		button.tabPage=page;
		button.onclick=function()
		{
			Tabs.selectTab(this);
		}
		tabs.push({btn:button, page:page});
	},
	
	selectTab: function(button)
	{
		for(var i=0; i<tabs.length; i++)
		{
			tabs[i].page.setAttribute("class", "tab");
			tabs[i].btn.setAttribute("class", "tab-button");
		}
		button.tabPage.setAttribute("class", "tab active");
		button.setAttribute("class", "tab-button active");
	}
}
