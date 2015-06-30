// collapses nodes with equal labels, assembling them into version sets
var collapseVersions=function(nodes)
{
	var assemblies={};
	
	for(var i=0; i<nodes.length; i++)
	{
		if(assemblies[nodes[i].label]==undefined)
		{
			assemblies[nodes[i].label]=[];
		}
		assemblies[nodes[i].label].push(nodes[i]);
	}
	
	// clearing nodes array
	while(nodes.pop()!=undefined);
	
	for(label in assemblies)
	{
		var arr=assemblies[label];
		var lastVer=arr[arr.length-1];
		for(var i=arr.length-2; i>=0; i--)
		{
			lastVer.data.versions.push({ label:arr[i].data.label, path:arr[i].data.path });
		}
		nodes.push(lastVer);
	}
}

// recursive data.xml parser
var processItem=function(base, itm)
{
	var label=itm.getAttribute("label");
	var type=itm.getAttribute("type");
	
	var path=null, nbase="";
	
	
	var verName=itm.getAttribute("v");
	if(typeof verName != typeof "") verName=Prefs.text.versionDefaultName;
	
	switch(type)
	{
	case "composite":
		type=Tree.NODE_COMPOSITE;
		nbase=itm.getAttribute("base");
		if(nbase==null || nbase==undefined) nbase="";
		
		path=base+nbase+itm.getAttribute("data");
		
		break;
	case "group":
		type=Tree.NODE_GROUP;
		nbase=itm.getAttribute("base");
		if(nbase==null || nbase==undefined) nbase="";
		
		break;
	case "final":
		type=Tree.NODE_FINAL;
		path=base+nbase+itm.getAttribute("data");
		
		break;
	}
	
	var node=Tree.createNode(label, type, {
		path: path,
		label: verName,
		versions: []
	});
	
	/*
	{
		label: String,
		type: Number,
		data:
		{
			path: String,		<- current version filename
			label: String,		<- current version label
			versions:			<- all versions array, ordered from newer to older ones
			[
				{				<- the first item is current version info
					label: String,
					path: String
				},
				...
			]
		}
	}
	*/
	
	if(type!=Tree.NODE_FINAL)
	{	
		var child=itm.firstElementChild;
		while(child!=null)
		{
			if(child.tagName=="item")
			{
				node.appendChild(processItem(base+nbase, child));
			}
			if(child.tagName=="ver" && type==Tree.NODE_COMPOSITE)
			{
				var versionName=child.getAttribute("v");
				if(typeof versionName != typeof "") versionName=Prefs.text.versionDefaultName;
				var vdata=base+nbase+child.getAttribute("data");
				// and new version to THE START of list
				node.data.versions.unshift({ label:versionName, path:vdata });
				// overwritting current version with a newer one
				// (up-to-date version is considered to be in the last <ver> node
				node.data.path=vdata;
				node.data.label=versionName;
			}
			child=child.nextElementSibling;
		}
		collapseVersions(node.childNodes);
	}
	
	if(node.nodeType!=Tree.NODE_GROUP)
	{
		// if only one version exists, it's now represented only in .data.path
		// so let's add it into versions array
		if(node.data.versions.length==0)
		{
			node.data.versions.push({ label:verName, path:path });
		}
	}
	
	return node;
}

// for xml docs parsing

var XMLParsingToolkit=
{
	createTitle:function(text)
	{
		var title=document.createElement("h2");
		title.innerHTML=text;
		return title;
	},
	
	processXMLNode: function(xmlNode)
	{
		if(xmlNode.nodeType==3)
		{
			return document.createTextNode(xmlNode.nodeValue);
		}
		switch(xmlNode.tagName)
		{
		case "title":
			return XMLParsingToolkit.createTitle(xmlNode.innerHTML);
			
		case "listing":
			var lang=xmlNode.getAttribute("lang");
			if(lang==null || lang==undefined || lang=="") lang=Prefs.defaultLang;
			return XMLParsingToolkit.createListing(xmlNode.innerHTML, lang);
			
		case "ref":
			return XMLParsingToolkit.createRef(xmlNode.innerHTML, Prefs.dataRootUrl+xmlNode.getAttribute("to"));
			
		case "entry":
			return XMLParsingToolkit.processEntry(xmlNode);
			
		case "description":
			var wrapper=document.createElement("div");
			var entry=xmlNode.firstElementChild;
			while(entry!=null)
			{
				// description should contain only entry tags, so >>dev/null everything else
				if(entry.tagName=="entry")
				{
					wrapper.appendChild(XMLParsingToolkit.processEntry(entry));
				}
				entry=entry.nextElementSibling;
			}
			return wrapper;
			
		case "seealso":
			return XMLParsingToolkit.processSeeAlsoSection(xmlNode);
			
		default:
			var tag=document.createElement(xmlNode.tagName);
			tag.innerHTML=xmlNode.innerHTML;
			return tag;
		}
	},
	
	createEntry:function()
	{
		var div=document.createElement("div");
		div.classList.add("entry");
		return div;
	},
	
	processEntry: function(xmlEntry)
	{
		if(xmlEntry==null) return null;
		
		var htmlEntry=XMLParsingToolkit.createEntry();
		
		var sibling=xmlEntry.firstChild;
		while(sibling!=null)
		{
			htmlEntry.appendChild(XMLParsingToolkit.processXMLNode(sibling));
			
			sibling=sibling.nextSibling;
		}
		
		return htmlEntry;
	},
	
	createListing: function(text, lang)
	{
		var listing=document.createElement("div");
		listing.classList.add("listing");
		listing.innerHTML=SyntaxAnalyzer.processCode(text, lang);
		return listing;
	},
	
	createRef: function(text, to)
	{
		var ref=document.createElement("span");
		ref.classList.add("ref");
		ref.innerHTML=text;
		ref.setAttribute("data-to", to);
		ref.onclick=function()
		{
			Navigation.navigate(this.getAttribute("data-to"));
			Interface.navigator.activate(Navigation.getTreeElement(this.getAttribute("data-to")));
		}
		return ref;
	},
	
	createSeeAlsoBlock: function()
	{
		var sab=document.createElement("div");
		sab.classList.add("see-also");
		return sab;
	},
	
	processSeeAlsoSection: function(xmlSAElement)
	{
		var sab=XMLParsingToolkit.createSeeAlsoBlock();
		var ul=document.createElement("ul");
		sab.appendChild(ul);
		var child=xmlSAElement.firstElementChild;
		while(child!=null)
		{
			var li=document.createElement("li");
			li.appendChild(XMLParsingToolkit.processXMLNode(child));
			ul.appendChild(li);
			child=child.nextElementSibling;
		}
		return sab;
	}

}

ContentManager=
{
	init: function()
	{
		xml=new window.XMLHttpRequest();
		xml.open("GET", Prefs.pagesListUrl, false);
		xml.setRequestHeader('Content-Type', 'text/xml')
		xml.send(null);
		
		if(xml.status==200)
		{
			var nav=xml.responseXML.firstChild;
			
			var roots=[];
			
			var child=nav.firstElementChild;
			while(child!=null)
			{
				if(child.tagName=="item")
				{
					roots.push(processItem(Prefs.dataRootUrl, child));
				}
				child=child.nextElementSibling;
			}
			
			Navigation.init(Interface.navigator.getWrapper(), roots);
			//var navTree=NavTree.create(Interface.navigator.getWrapper(), roots);
		}
	},
	
	loadXML: function(filename, callback)
	{
		if(typeof callback != typeof eval) callback=function(){};
		
		xml=new window.XMLHttpRequest();
		xml.open("GET", filename, true);
		xml.setRequestHeader('Content-Type', 'text/xml')
		xml.send(null);
		
		xml.onreadystatechange=function()
		{
			if(xml.readyState==4)
			{
				if(xml.status==200)
				{
					callback(ContentManager.parseXML(xml.responseXML.firstElementChild));
				}
			}
		}
	},
	
	loadWiki: function(filename, callback)
	{
		if(typeof callback != typeof eval) callback=function(){};
		
		xhr=new window.XMLHttpRequest();
		xhr.open("GET", filename, true);
		xhr.setRequestHeader('Content-Type', 'text/plain');
		try
		{
			xhr.send(null);
		}
		catch(e)
		{
			// probably file not found
			console.log(e);
			xhr=new window.XMLHttpRequest();
			xhr.open("GET", Prefs.navigation.page404Url, true);
			xhr.setRequestHeader('Content-Type', 'text/plain');
			xhr.send(null);
		}
		
		xhr.onreadystatechange=function()
		{
			if(xhr.readyState==4)
			{
				if(xhr.status==200)
				{
					callback(xhr.responseText);
				}
				else
				{
					console.log(xhr.status);
				}
			}
		}
	},
	
	load: function(filename, callback, type)
	{
		if(typeof type != typeof "") type=Prefs.contentType;
		if(type=="xml") { this.loadXML(filename, callback); return; }
		if(type=="wiki") { this.loadWiki(filename, callback); return; }
		console.log("Unknown content type");
	},
	
	parseXML: function(xmlRoot)
	{
		var wrapper=document.createElement("div");
		
		var sibling=xmlRoot.firstElementChild;
		while(sibling!=null)
		{
			/*var name=sibling.tagName;
			switch(name)
			{
			case "title":
				wrapper.appendChild(createTitle(sibling.innerHTML));
				break;
			case "description":
				// processing each entry tag inside description tag
				// TODO
				
				break;
			case "seealso":
				wrapper.appendChild(processSeeAlsoSection(sibling));
				break;
			}*/
			
			wrapper.appendChild(XMLParsingToolkit.processXMLNode(sibling))
			
			sibling=sibling.nextElementSibling;
		}
		
		return wrapper;
	}
}

subscribeOnLoad(ContentManager.init);
