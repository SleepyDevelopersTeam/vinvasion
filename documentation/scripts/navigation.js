var navigateTo=function(ref)
{
	// `{::label}Sprite`								=> ref=="Sprite::label" (wiki.js corrects)
	// `{TowerSprite::l}Один из наследников Sprite`		=> ref=="TowerSprite::label"
	// possible flags: (TODO:)
	// ::ref - a simple ref, eq. of no flag
	// ::label - reference by label
	// ::search - perform a search with query
	// ::back - navigate back across history
	// ::fwd - similar
	// ::home - navigate home
	p=ref.split("::");
	Interface.content.show(Prefs.text.loading);
	
	var path=ref;
	
	if(p.length>1)
	{
		if(p[1]=="label")
		{
			// recursive function that findes path by label
			var r=function(n, l)
			{
				if(n.label==l && typeof n.data == typeof {})
				{
					return n.data.path;
				}
				if(typeof n.childNodes==typeof [])
				{
					for(var i=0; i<n.childNodes.length; i++)
					{
						var result=r(n.childNodes[i], l);
						if(result!=null) return result;
					}
				}
				return null;
			}
			for(var i=0; i<Navigation.navTree.roots.length; i++)
			{
				var result=r(Navigation.navTree.roots[i], p[0]);
				if(result!=null)
				{
					path=result;
					break;
				}
			}
		}
		else
		{
			path=p[0];
		}
	}
	
	ContentManager.load(path, function(response)
	{
		document.decoder.source=response;
		Interface.content.show(document.decoder.parse());
		
		Navigation.navigateToTop();
	}, "wiki");
	
	if(p.length>1)
	{
		// for in-page references
		// (mb will be implemented in future)
		console.log(p);
	}
}

var checkObsolete=function(treeNode, ref)
{
	if(treeNode==null) return false;

	if(typeof treeNode.data == typeof {})
	{
		if(treeNode.data.path==ref) return false;	
		for(var i=0; i<treeNode.data.versions.length; i++)
		{
			if(treeNode.data.versions[i].path==ref) return true;
		}
	}
	
	if(typeof treeNode.childNodes == typeof [])
	{
		for(var i=0; i<treeNode.childNodes.length; i++)
		{
			if(checkObsolete(treeNode.childNodes[i], ref)) return true;
		}
	}
	return false;
}

var findNode=function(treeNode, ref)
{
	if(treeNode==null) return null;

	if(typeof treeNode.data == typeof {})
	{
		if(treeNode.data.path==ref) return treeNode;	
		for(var i=0; i<treeNode.data.versions.length; i++)
		{
			if(treeNode.data.versions[i].path==ref) return treeNode;
		}
	}
	
	if(typeof treeNode.childNodes == typeof [])
	{
		for(var i=0; i<treeNode.childNodes.length; i++)
		{
			var found=findNode(treeNode.childNodes[i], ref);
			if(found!=null) return found;
		}
	}
	return null;
}

Navigation=
{
	navTree: null,
	ribbon: null,
	cpos: -1,
	
	init: function(wrapper, roots)
	{
		Navigation.navTree=NavTree.create(wrapper, roots);
	},
	
	history:[],
	pushToHistory: function(ref)
	{
		Navigation.history.push({ ref:ref, date:new Date() });
	},
	
	navigate: function(ref)
	{
		navigateTo(ref);
		this.checkObsolete(ref);
		if(Navigation.cpos!=Navigation.history.length-1)
		{
			// We've travelled (far) back and need to erase history
			// that is after us
			Navigation.history.splice(Navigation.cpos+1, Navigation.history.length-Navigation.cpos-1);
		}
		Navigation.pushToHistory(ref);
		Navigation.cpos++;
		
		Interface.ribbon.refreshNavButtons();
		this.activateNavItem(ref);
		
		Prefs.navigation.lastPageUrl=ref;
		Prefs.save();
	},
	
	checkObsolete: function(ref)
	{
		for(var i=0; i<this.navTree.roots.length; i++)
		{
			if(checkObsolete(this.navTree.roots[i], ref))
			{
				Interface.content.showObsoleteIcon(true);
				return;
			}
		}
		Interface.content.showObsoleteIcon(false);
	},
	
	activateNavItem: function(ref)
	{
		for(var i=0; i<this.navTree.roots.length; i++)
		{
			var found=findNode(this.navTree.roots[i], ref);
			if(found!=null)
			{
				Interface.navigator.activate(found.navListItem);
				return;
			}
		}
		// nothing appropriate was found, inactivate all
		Interface.navigator.deactivateAll();
	},
	
	canGoBack: function()
	{ return Navigation.cpos>0; },
	goBack: function()
	{
		if(!Navigation.canGoBack()) return;
		Navigation.cpos--;
		navigateTo(Navigation.history[Navigation.cpos].ref);
		this.checkObsolete(Navigation.history[Navigation.cpos].ref);
		
		Interface.ribbon.refreshNavButtons();
		this.activateNavItem(Navigation.history[Navigation.cpos].ref);
	},
	
	canGoForward: function()
	{ return Navigation.cpos<Navigation.history.length-1; },
	goForward: function()
	{
		if(!Navigation.canGoForward()) return;
		Navigation.cpos++;
		navigateTo(Navigation.history[Navigation.cpos].ref);
		this.checkObsolete(Navigation.history[Navigation.cpos].ref);
		
		Interface.ribbon.refreshNavButtons();
		this.activateNavItem(Navigation.history[Navigation.cpos].ref);
	},
	
	navigateHome: function()
	{
		Navigation.navigate(Prefs.navigation.homePageUrl);
	},
	
	navigateToTop: function()
	{
		//document.getElementById("content-header").scrollIntoView();
		document.getElementById('text').scrollIntoView();
		//window.scrollTo(0, 0);
	},
	
	
	getTreeElement: function(data)
	{
		if(Navigation.navTree==null) return null;
		
		var liArr=Navigation.navTree.wrapper.getElementsByTagName("li");
		for(var i=0; i<liArr.length; i++)
		{
			if(liArr[i].treeNode!=null && liArr[i].treeNode!=undefined)
			{
				if(liArr[i].treeNode.data==data)
					return liArr[i];
			}
		}
		return null;
	},
	
	createRibbon: function(wrapper)
	{
		Navigation.ribbon=Ribbon.create(wrapper);
		
		// Section for scroll-to-top button
		var section=Navigation.ribbon.createSection("", -1);
		section.addElement(Ribbon.createButton(Prefs.navigation.text.scrollToTop, {
			iconChar: Prefs.navigation.text.scrollToTopIcon, enabled:true, onclick:function()
			{
				Navigation.navigateToTop();
			}
		}));
		
		// History navigation section
		section=Navigation.ribbon.createSection(Prefs.navigation.text.historyNavSection, -1);
		
		Interface.ribbon.goBackButton=Ribbon.createButton(Prefs.navigation.text.goBack, {
			iconChar: Prefs.navigation.text.goBackIcon, enabled:true, onclick:function()
			{
				Navigation.goBack();
			}
		});
		section.addElement(Interface.ribbon.goBackButton);
		
		Interface.ribbon.goForwardButton=Ribbon.createButton(Prefs.navigation.text.goForward, {
			iconChar: Prefs.navigation.text.goForwardIcon, enabled:true, onclick:function()
			{
				Navigation.goForward();
			}
		});
		section.addElement(Interface.ribbon.goForwardButton);
		
		section.addElement(Ribbon.createSpaceBlock(-1));
		
		section.addElement(Ribbon.createButton(Prefs.navigation.text.homePage, {
			iconChar: Prefs.navigation.text.homePageIcon, enabled:true, onclick:function()
			{
				Navigation.navigateHome();
			}
		}));
		
		section.addElement(Ribbon.createSpaceBlock(-1));
		
		section.addElement(Ribbon.createButton(Prefs.navigation.text.browseHistory, {
			iconChar: Prefs.navigation.text.browseHistoryIcon, enabled:false, onclick:function()
			{
				console.log("История");
			}
		}));
		
		// Search section
		section=Navigation.ribbon.createSection(Prefs.navigation.text.searchNavSection, -1);
		
		var group=Ribbon.createGroup(-1);
		var stb=group.addElement(Ribbon.createTextBox("Search", true, 200, Prefs.search.text.searchInvitation));
		var caseCb=group.addElement(Ribbon.createCheckBox(	Prefs.search.text.caseSensitive,
															true, Prefs.search.caseSensitiveSearch));
		
		var titlesCb=group.addElement(Ribbon.createCheckBox(Prefs.search.text.onlyInTitles,
															false, Prefs.search.searchOnlyInTitles));
		
		var sbtn=group.addElement(Ribbon.createButton(Prefs.search.text.search, {
			//
			// SEARCH BUTTON ONCLICK
			//
			enabled:true, onclick:function()
			{
				var query=this.searchBox.getText();
				if(query=="") return;
				var casesns=this.caseSensitiveBox.isChecked();
				var results=Navigation.searchHeadings(query, casesns);
				
				this.resultsList.clear(results.length==0);
				for(var i=0; i<results.length; i++)
				{
					this.resultsList.addElement(results[i].label, true, false, function(){
						Navigation.navigate(this.itemPath);
						
					}).itemPath=results[i].path;
				}
			}
		}));
		sbtn.searchBox=stb;
		sbtn.caseSensitiveBox=caseCb;
		sbtn.titlesBox=titlesCb;
		
		section.addElement(group);
		
		var searchResults=section.addElement(Ribbon.createList(Prefs.search.text.searchResults, [], {
			checkable:false, 
			width: 200,
			defaultElementEnabled: true,
			defaultElementContent: Prefs.search.text.nothingWasFound
		}));
		
		sbtn.resultsList=searchResults;
	},
	
	searchHeadings: function(keyword, regsns)
	{
		var results=[];
		
		var r=function(node, word, regsns, sres)
		{	
			var cnd=node.data;
			if(typeof cnd == typeof {})
			{
				var index=-1;
				if(regsns) index=node.label.toLowerCase().indexOf(word);
				else index=node.label.indexOf(word);
				if(index!=-1)
				{
					sres.push({ position:index, label:node.label, path:node.data.path });
				}
				
				/*for(var j=0; j<cnd.versions.length; j++)
				{
					var index=-1;
					if(regsns) index=cnd.versions[j].label.toLowerCase().indexOf(word);
					else index=cnd.versions[j].label.indexOf(word);
					if(index!=-1)
					{
						sres.push({ position:index, label:cnd.versions[j].label, path:cnd.versions[j].path });
					}
				}*/
			}
			if(typeof node.childNodes != typeof []) return;
			for(var i=0; i<node.childNodes.length; i++)
			{
				r(node.childNodes[i], word, regsns, sres);
			}
			
			//console.log(sres);
		}
		
		if(regsns) keyword=keyword.toLowerCase();
		for(var i=0; i<this.navTree.roots.length; i++)
		{
			r(this.navTree.roots[i], keyword, regsns, results);
		}
		
		return results;
	}
}

subscribeOnLoad(function(){Navigation.createRibbon(Interface.ribbon.getTabWrapper(1));});