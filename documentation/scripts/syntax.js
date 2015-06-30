var escapeRegExpEntities=function(str)
{
	return str.replace(/\//g, "\\/").replace(/\*/g, "\\*").replace(/\./g, "\\.").replace(/\n/g, "\\n");
}
var escapeHtmlEntities=function(str)
{
	return str.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
}

SyntaxAnalyzer=
{
	keyWords: {},
	
	init: function()
	{
		SyntaxAnalyzer.keyWords["Java"]=
		{
			comments: [ { start:"//", end: "\n" }, { start:"/*", end: "*/" } ],
			strong:
			[
				"public", "protected", "private", "class", "interface", "enum", "int",
				"float", "double", "byte", "package", "import", "new", "return", "if",
				"for", "while", "do", "try", "catch", "finally", "assert", "long",
				"else", "throw", "break", "continue", "boolean", "extends",
				"implements", "this", "true", "false", "null", "super"
			],
			embeded: [ "static", "final" ],
			colored: [ "void", "abstract", "throws" ],
			strings: [ { start: "\"", end: "\"" } ]
		}
	},
	
	processCode: function(code, lang, numLines)
	{
		if(typeof numLines != typeof false) numLines=true;
		// <, >, & and chars
		//code=escapeHtmlEntities(code);
		
		//html eats spaces
		code=code.replace(/ /g, "&nbsp;");
		
		//hilighting keywords
		keyWords=SyntaxAnalyzer.keyWords[lang];
		if(keyWords!=null && keyWords!=undefined)
		{
			for(var i=0; i<keyWords.strong.length; i++)
			{
				code=code.replace(	eval("/([^A-z_0-9])("+escapeRegExpEntities(keyWords.strong[i])+")([^A-z_0-9])/g"),
									"$1<strong>$2</strong>$3");
			}
			for(var i=0; i<keyWords.embeded.length; i++)
			{
				code=code.replace(	eval("/([^A-z_0-9])("+escapeRegExpEntities(keyWords.embeded[i])+")([^A-z_0-9])/g"),
									"$1<em>$2</em>$3");
			}
			for(var i=0; i<keyWords.colored.length; i++)
			{
				code=code.replace(	eval("/([^A-z_0-9])("+escapeRegExpEntities(keyWords.colored[i])+")([^A-z_0-9])/g"),
									"$1<i>$2</i>$3");
			}
			for(var i=0; i<keyWords.strings.length; i++)
			{
				var regexp=	"/"+escapeRegExpEntities(keyWords.strings[i].start)+
							".*?"+escapeRegExpEntities(keyWords.strings[i].end)+"/g";
				regexp=eval(regexp);
				
				code=code.replace(regexp, "<span class='string'>$&</span>");
			}
			for(var i=0; i<keyWords.comments.length; i++)
			{
				// TODO: form regexp for comment and apply it
				var regexp=	"/"+escapeRegExpEntities(keyWords.comments[i].start)+
							".*?"+escapeRegExpEntities(keyWords.comments[i].end)+"/g";
				regexp=eval(regexp);
				
				code=code.replace(regexp, "<span class='comment'>$&</span>");
				
				//code=eval();
			}
		}
		else console.log("Your language '"+lang+"' was not found!");
		
		// cutting first empty symbols
		// TODO: maybe do the same with ending?
		while(code[0]=='\n' || code[0]==' ')
		{
			code=code.substr(1);
		}
		
		// transfering to html chars
		code=code.replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;");
		code=code.replace(/(^)([^\n]*?)($)/gm, "<div class='line'>$2</div>");
		//code=code.replace(/\n/g, "<br />\n");
		return code;
	}
}

subscribeOnLoad(SyntaxAnalyzer.init);
