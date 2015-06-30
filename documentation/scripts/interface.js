var navWrapper=null, textWrapper=null;
var ribbonTab1=null, ribbonTab2=null;
var obsoleteIcon=null;

// external function, used in spoilers caption
function toggleSpoiler(element)
{
	element=element.parentNode;
	if(element.classList.contains("expanded"))
	{
		element.classList.remove("expanded");
	}
	else
	{
		element.classList.add("expanded");
	}
}

function refClicked(element)
{
	var ref=element.getAttribute("data-to");
	if(ref.indexOf("::")==-1 || ref.indexOf("::ref")!=-1)
	{
		ref=Prefs.dataRootUrl+ref;
	}
	Navigation.navigate(ref);
	Interface.navigator.activate(Navigation.getTreeElement(ref));
}

var getIcon=function(li)
{
	var divs=li.getElementsByTagName("div");
	for(var i=0; i<divs.length; i++)
	{
		if(divs[i].classList.contains("icon"))
		{
			return divs[i];
		}
	}
	return null;
}

var getLabel=function(li)
{
	var divs=li.getElementsByTagName("div");
	for(var i=0; i<divs.length; i++)
	{
		if(divs[i].classList.contains("label"))
		{
			return divs[i];
		}
	}
	return null;
}

var getUl=function(li)
{
	var uls=li.getElementsByTagName("ul");
	for(var i=0; i<uls.length; i++)
	{
		if(uls[i].parentNode==li) return uls[i];
	}
	return null;
}

Interface=
{
	init: function()
	{
		navWrapper=document.getElementById('nav');
		textWrapper=document.getElementById('text');
		ribbonTab1=document.getElementById('tab1');
		ribbonTab2=document.getElementById('tab2');
		obsoleteIcon=document.getElementById('obsolete-icon');
		
		Interface.colors.setColors(Prefs.appearance.defaultColors);
	},
	
	// Navigator
	
	navigator:
	{
		getWrapper: function()
		{ return navWrapper; },
		
		deactivateAll: function()
		{
			var lis=navWrapper.getElementsByTagName("li");
			for(var i=0; i<lis.length; i++)
			{
				/*if(lis[i].classList.conta("active")!=-1)
				{
					lis[i].className=lis[i].className.replace(/ active/g, "");
				}*/
				lis[i].classList.remove("active");
			}
		},
		
		activate: function(li)
		{
			Interface.navigator.deactivateAll();
			li.classList.add("active");
			Interface.navigator.bringIntoView(li);
		},
		
		bringIntoView: function(li)
		{
			var ptr=li;
			while(ptr.tagName.toLowerCase()!="div")
			{
				if(ptr.tagName.toLowerCase()!="ul")
				{
					ptr=ptr.parentNode;
					continue;
				}
				if(ptr.classList.contains("active")) break;
				
				ptr.classList.add("active");
				ptr=ptr.parentNode;
			}
			li.scrollIntoView();
		},
		
		isActive: function(li)
		{
			return li.classList.contains("active");
		},
		
		isExpanded: function(li)
		{
			var ul=getUl(li);
			if(ul==null) return true; // supposing leaves to be always expanded
			return ul.classList.contains("active");
		},
		
		collapseAll: function()
		{
			var items=navWrapper.firstChild.getElementsByTagName("li");
			for(var i=0; i<items.length; i++)
			{
				/*if(uls[i].classList.contains("active"))
				{
					uls[i].className=uls[i].className.replace(/ active/g, "");
				}*/
				Interface.navigator.collapse(items[i]);
			}
		},
		
		collapse: function(li)
		{
			if(!Interface.navigator.isExpanded(li)) return;
			if(li.classList.contains("final")) return;
			
			//var ul=li.getElementsByTagName("ul")[0];
			//var icon=li.firstChild;
			
			console.log(li);
			
			getUl(li).classList.remove("active");
			getIcon(li).className="icon dclsd-icon";
		},
		
		expandAll: function()
		{
			var items=navWrapper.getElementsByTagName("li");
			for(var i=0; i<items.length; i++)
			{
				/*if(!uls[i].classList.contains("active"))
				{
					uls[i].classList.add("active");
				}*/
				//uls[i].classList.add("active");
				Interface.navigator.expand(items[i]);
			}
		},
		
		expand: function(li)
		{
			if(Interface.navigator.isExpanded(li)) return;
			if(li.classList.contains("final")) return;
			
			//var ul=li.getElementsByTagName("ul")[0];
			//var icon=li.firstChild;
			
			getUl(li).classList.add("active");
			getIcon(li).className="icon dopen-icon";
		},
		
		toggle: function(li)
		{
			if(Interface.navigator.isExpanded(li))
				Interface.navigator.collapse(li);
			else
				Interface.navigator.expand(li);
		}
	},
	
	// Text
	
	content:
	{
		getWrapper: function()
		{ return textWrapper; },
		
		show: function(content)
		{
			if(typeof content == "string") textWrapper.innerHTML=content;
			else
			{
				textWrapper.innerHTML="";
				textWrapper.appendChild(content);
			}
		},
		
		getContent: function()
		{
			return textWrapper.innerHTML;
		},
		
		showObsoleteIcon: function(flag)
		{
			if(obsoleteIcon==null) return;
			obsoleteIcon.style.visibility=flag?"visible":"hidden";
		}
	},
	
	// Ribbon
	
	ribbon:
	{
		getTabWrapper: function(n)
		{
			if(n==2) return ribbonTab2;
			if(n==1) return ribbonTab1;
			return null;
		},
		
		refreshNavButtons: function()
		{
			if(Navigation.canGoBack()) Interface.ribbon.goBackButton.enable();
			else Interface.ribbon.goBackButton.disable();
			if(Navigation.canGoForward()) Interface.ribbon.goForwardButton.enable();
			else Interface.ribbon.goForwardButton.disable();
		}
	},
	
	colors:
	{
		setColors: function(file)
		{
			Prefs.appearance.defaultColors=file;
			file='colors/'+file+'.css';
			document.getElementById('colors-css').setAttribute('href', file);
			Prefs.save();
		}
	},
	
	createViewRibbon: function(wrapper)
	{
		Interface.viewRibbon=Ribbon.create(wrapper);
		
		// Section for colors switching
		var section=Interface.viewRibbon.createSection(Prefs.appearance.text.themes, -1);
		var list=section.addElement(Ribbon.createList(Prefs.appearance.text.availableColors, [], {
			checkable:false, 
			width: 200,
			defaultElementEnabled: false
		}));
		
		for(var i in Prefs.appearance.available)
			list.addElement(Prefs.appearance.available[i], true, false, function(){Interface.colors.setColors(this.name);});
			
		section=Interface.viewRibbon.createSection("", -1);
		var cb=section.addElement(Ribbon.createCheckBox(Prefs.appearance.text.openLastViewed, true,
														Prefs.appearance.openLastViewed, function(){
			// shamanstvo buben kostyl
			// onclick function is called twice when clicking checkbox
			// firstly when checked hasn't changed yet
			// secondly when it has
			Prefs.appearance.openLastViewed=this.isChecked();
			Prefs.save();
		}));
		
	}
}

subscribeOnLoad(Interface.init);
subscribeOnLoad(function(){Interface.createViewRibbon(Interface.ribbon.getTabWrapper(2));});