var getEmptyTreeNode=function(nodeType)
{
	// returns empty well-formed treenode (with all fields initialized)
	var node=
	{
		nodeType: nodeType,
		label: ""
	};

	if(nodeType!=Tree.NODE_GROUP)
	{
		node.data=null;
	}

	if(nodeType==Tree.NODE_FINAL)
	{
		return node;
	}
	
	node.childNodes=[];
	node.appendChild=function(childNode)
	{
		this.childNodes.push(childNode);
	}
	
	return node;
}

Tree=
{
	// node types:
	NODE_FINAL:1, NODE_GROUP:2, NODE_COMPOSITE:3,
	
	create: function()
	{
		var tree=
		{
			nodes: [],
			
			appendChild: function(childNode)
			{
				this.nodes.push(childNode);
			},
			
			clearTree: function()
			{
				nodes=[];
			}
		};
		
		return tree;
	},
	
	createNode: function(label, nodeType, data)
	{
		var node=getEmptyTreeNode(nodeType);
		node.label=label;
		if(nodeType!=Tree.NODE_GROUP)
		{
			node.data=data;
		}
		return node;
	}
}

var createListNode=function(node)
{
	var li=document.createElement("li");
	li.treeNode=node;
	var icon=document.createElement("div");
	var label=document.createElement("div");
	label.innerHTML=node.label;
	label.className="label";
	
	li.appendChild(icon);
	li.appendChild(label);
	
	// versions popup
	if(node.nodeType!=Tree.NODE_GROUP)
	{
		if(node.data.versions.length>1)
		{
			var verp=document.createElement("div");
			verp.classList.add("versions-popup");
			
			var verl=document.createElement("div");
			verl.classList.add("label");
			verl.innerHTML=Prefs.navigation.text.moreVersionsLabel;
			verp.appendChild(verl);
			
			var verul=document.createElement("ul");
			verp.appendChild(verul);
			
			var verli=null;
			for(var i=0; i<node.data.versions.length; i++)
			{
				verli=document.createElement("li");
				verli.innerHTML=node.data.versions[i].label;
				verli.setAttribute("data-ref", node.data.versions[i].path);
				
				verli.onclick=function()
				{
					Navigation.navigate(this.getAttribute("data-ref"));
					// now, we'll try to get outer li, containing this list
					// ok, the ierarchy seems to be verli==this < verul < verp <li
					Interface.navigator.activate(this.parentNode.parentNode.parentNode);
				}
				
				verul.appendChild(verli);
			}
			
			li.appendChild(verp);
		}
	}
	// endof versions popup
	
	var ul=null;
	
	switch(node.nodeType)
	{
	case Tree.NODE_FINAL:
		icon.className="icon file-icon";
		li.className="final";
		break;
	default:
		icon.className="icon dclsd-icon";
		ul=document.createElement("ul");
		for(var i=0; i<node.childNodes.length; i++)
		{
			var sibling=createListNode(node.childNodes[i]);
			ul.appendChild(sibling);
		}
		break;
	}
	if(ul!=null) li.appendChild(ul);
	
	// Handlers
	
	switch(node.nodeType)
	{
	case Tree.NODE_FINAL:
		label.onclick=function()
		{
			if(!Interface.navigator.isActive(this.parentNode))
				Navigation.navigate(this.parentNode.treeNode.data.path);
			Interface.navigator.activate(this.parentNode);
		}
		icon.onclick=function()
		{
			if(!Interface.navigator.isActive(this.parentNode))
				Navigation.navigate(this.parentNode.treeNode.data.path);
			Interface.navigator.activate(this.parentNode);
		}
		break;
	case Tree.NODE_GROUP:
		label.onclick=function()
		{
			Interface.navigator.toggle(this.parentNode);
		}
		icon.onclick=function()
		{
			Interface.navigator.toggle(this.parentNode);
		}
		break;
	case Tree.NODE_COMPOSITE:
		label.onclick=function()
		{
			if(!Interface.navigator.isActive(this.parentNode))
				Navigation.navigate(this.parentNode.treeNode.data.path);
			Interface.navigator.activate(this.parentNode);
			Interface.navigator.expand(this.parentNode);
		}
		icon.onclick=function()
		{
			Interface.navigator.toggle(this.parentNode);
		}
		break;
	}
	
	node.navListItem=li;
	
	return li;
}
NavTree=
{
	create: function(wrapper, roots)
	{
		var tree={ wrapper:wrapper, roots:roots, ul:null };
		tree.rebuild=function()
		{
			this.uls=[];
			for(var i=0; i<roots.length; i++)
			{
				this.ul=document.createElement("ul");
				this.ul.className="active";
				for(var i=0; i<roots.length; i++)
				{
					var sibling=createListNode(roots[i]);
					this.ul.appendChild(sibling);
				}
			}
			
			while(wrapper.firstChild!=null) wrapper.removeChild(wrapper.firstChild);
			wrapper.appendChild(this.ul);
		}
		tree.rebuild();
		return tree;
	}
}
