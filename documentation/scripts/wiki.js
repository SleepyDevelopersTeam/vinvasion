/* 		WIKI PARSER
 *  Content of a wiki file is a mixture of regular text and command lines (cmdln).
 * Regular text also can contain command sequences. Both command lines and sequences
 * begin with '`' char that is named command char.
 *  Wiki file can be broken into 3 global blocks: header, content sections and SA block.
 *  Header gblock starts with the beginning of the file and lasts until the first 
 * content section. Its text content is a title of the page. Only commentary lines are
 * available here, no formattings and objects allowed.
 *  Content sections gblock appears as multiple content sections. Each of them is 
 * started with "`[%Title%]" command line - it means that here a new content section 
 * begins. %Title% would become a section title. Every char after ']' is ignored. All
 * control chars inside brackets are ignored. Content sections lasts until we meet next
 * section or SA gblock.
 *  Inside csection, the whole content can be broken into blocks of these types:
 * - Text block
 * - List
 * - Image
 * - Table
 * - Horizontal line
 * - Listing
 *  These appear as blocks - it means that each of them starts with a new line. 
 *  Text block is a mixture of plain text and formatted text (formattings, fmts). All 
 * fmts are inline entries - they don't start from a new line. So, the whole content of 
 * this block is separated into plain text entries and fmts. Fmts begin with a '`' char,
 * and end with the same. All chars inside ``-"brackets" are interpreted as fmt content.
 * Example fmts:
 * -	`*text`		defines bold text. '*' defines format that is used, and any other
 *  				char inside is just a text that should be formatted.
 * - 	`{1}2`		defines a reference. "1" becames an reference path, and 2 appears as
 *  				element's text content.
 *  List block begins with cmdln "`{?", where '?' can be '#' (for ordered list) or '*'
 * (for unordered one). Inside the block, every cmdln "`+" is considered to be a 
 * beginning of a new listitem. Listitem lasts until it meets next listitem or "`}" that
 * stops the list block. Every LI unites a number of blocks, shown in the list above.
 *  Image block is a single cmdln. It's syntax is "`i|%src%|%desc%", where %src% is a 
 * link to the image file, and %desc% is description of the picture.
 *  Table block starts with cmdln "`t|%cols%|%rows%". It means that next %rows% lines 
 * would be interpreted as table rows. Each row contents %cols% cells, that are separated
 * with "\t|" sequence.
 *  Horizontal line is cmdln "`=" that simply inserts horizontal separator.
 *  Listing is a block of code. Its content is transfered to the rendered page without 
 * any changes (except syntax hilighting). It also means that ANY CMDLNS EXCEPT ONE THAT
 * FINISHES LISTING BLOCK APPEARS IN RENDERED PAGE AS IS. Listing block starts with cmdln
 * "`(%lang%", where %lang% is language parameter that defines syntax hilighting. The 
 * block ends with "`)" cmdln.
 *  The page is finished with "see also"-section, that is formed with SA gblock. Its
 * content is interpreted as a list, and each line of SA gblock content appears as a 
 * single LI. Each line can content ONLY text block (see above). This gblock is finished
 * with the end of the file (eof).
 * 
 * SPECIAL FEATURES
 *  Intedation
 *  Every whitespace char (ws) after line start and before any non-ws char will be erased.
 * The exception is made for listings - their content appear as is.
 *  Escaping
 *  Every sequence "\`" is interpreted as literal '`' char. Also, you can escape '|' in
 * table cell definition. "\|" would be converted into '|' literal.
 *  Line breaks
 *  In the markup file, a single '\n' char is NOT considered as line break. To make it 
 * appear in the rendered page, use "\n\n" - that would insert a SINGLE line break. However,
 * a sequence of n '\n' chars would insert (n-1) line breaks into the page.
 */

function createWikiDecoder(text)
{
	return {
		source: text,
		pos: -1,
		line: -1,
		parsed: "",
		errors: [],
		
		eof: function() { return this.pos>=this.source.length; },
		eol: function() { return this.source[this.pos]=='\n'; },
		ch: function(i) // current char with i offset or null if out of bounds
		{
			if(typeof i != typeof 0) i=0;
			if(i==0) return this.source[this.pos];
			var index=i+this.pos;
			if(index<0) return null;
			if(index>this.source.length) return null;
			return this.source[index];
		},
		bol: function() { return this.ch(-1)=='\n'; }, //we are on the start of a new line
		//we are on the line start (considering tabulation)
		tbol: function()
		{
			var i=0;
			var c;
			do
			{
				--i;
				c=this.ch(i);
				if(c==' ') continue;
				if(c=='\t') continue;
				if(c=='\n') return true;
				// found non-ws and not a newline char
				break;
			}
			while(c!=null);
			return false;
		},
		incp: function() { if(this.eol()) { this.line++; } this.pos++; }, // increases .pos and (if needed) .line
		isws: function() // checks if the current char is whitespace
		{
			if(this.ch()==' ') return true;
			if(this.ch()=='\t') return true;
			if(this.ch()=='\n') return true;
			if(this.ch()=='\r') return true;
			return false;
		},
		
		mv: function()
		{
			this.incp();
			return this.ch(-1);
		},
		
		apd: function(str)
		{
			if(str=='<') str="&lt;";
			if(str=='>') str="&gt;";
			if(str=='&') str="&amp;";
			this.parsed+=str;
		},
		
		// is this.ch() a control char?
		isctrl: function()
		{
			if(this.ch()!='`') return false;
			var i=-1;
			while(this.ch(i)=='\\')
			{
				--i;
			}
			i=-i-1;
			// i%2 == 0 => smth like "\\\\`" => not escaped
			// i%2 == 1 => smth like "\\\`" => escaped
			return i%2==0;
		},
		
		// is this.ch() escaped?
		escaped: function()
		{
			var i=-1;
			while(this.ch(i)=='\\')
			{
				--i;
			}
			i=-i-1;
			// i%2 == 0 => smth like "\\\\?" => not escaped
			// i%2 == 1 => smth like "\\\?" => escaped
			return i%2!=0;
		},
		
		parse: function()
		{
			this.pos=0;
			this.line=1;
			this.parsed="";
			this.errors=[];
			if(!this.ch(this.source.length-1)!='\n') this.source+='\n';
			this.source=this.source.replace(/\r/g, "");
			
			// reading gblocks
			
			this.readHeader();
			
			this.apd("<div>"); // content sections
			
			this.skipWhiteSpace();
			
			while(!this.eof())
			{
				while(this.ch()!='`')
				{
					this.skipLine();
					this.skipWhiteSpace();
					if(this.eof()) break;
				}
				if(this.ch(1)=='[') this.readSection();
				else if(this.ch(1)=='@')
				{
					this.parsed+="</div>"; // endof content sections
					this.readSABlock();
				}
				else this.skipLine(); // on this level should not be anything except `[, `@ and header
			}
			return this.parsed;
		},
		
		// skips line and stops on the 1st char at next line
		skipLine: function()
		{
			while(!this.eol() && !this.eof())
			{
				this.incp();
			}
			this.incp();
		},
		
		// skips all whitespace (including \n) and stops on 1st nwhite char
		skipWhiteSpace: function()
		{
			if(!this.isws()) return;
			while(!this.eof() && this.isws())
			{
				this.incp();
			}
			//this.incp();
		},
		
		// reads header gblock, then stops at the beginning of the next line
		readHeader: function()
		{
			this.apd("<h2 id='content-header'>");
			
			var title="";
			
			// keep reading until we get cmdln (or eof)
			while(!this.eof())
			{	
				if(this.ch()=='`')
				{
					if(this.ch(1)=='`')
					{
						//it's a comment
						this.skipLine();
						continue;
					}
					// cmdln found
					break;
				}
				title+=this.ch();
				this.incp();
			}
			this.apd(title+"</h2>");
		},
		
		// reads csection, stops when gets start of the next csection or SA block
		readSection: function()
		{
			if(this.ch()!='`')
			{
				this.error("Wrong section start!");
				this.skipLine();
				return;
			}
			if(this.ch(1)!='[')
			{
				this.error("Wrong section title start!");
				this.skipLine();
				return;
			}
			
			//firstly, read a title
			while(this.ch()!='[') this.incp();
			this.incp();
			var title="";
			while(this.ch()!=']' && !this.eol())
			{
				title+=this.ch();
				this.incp();
			}
			// that condition means we stopped because of eol
			if(this.eol()) this.error("Unexpected section title end");
			this.skipLine(); // moving to the next line
			
			this.apd("<div class='entry'><h2>"+title+"</h2>");
			
			//ok, next'll be content
			
			while(!this.eof())
			{
				this.readBlock();
				this.skipWhiteSpace();
				if(this.ch()=='`')
				{
					if(this.ch(1)=='[' || this.ch(1)=='@')
					{
						//endof section
						this.apd("</div>");
						return;
					}
				}
			}
		},
		
		// reads ANY block type (redirecting it to special function)
		readBlock: function()
		{
			this.skipWhiteSpace();
			if(this.ch()=='`')
			{
				switch(this.ch(1))
				{
				case '`':
					// comment
					this.skipLine();
					break;
				case '{':
					// list or ref
					if(this.ch(2)=='#')
					{
						this.readOLBlock();
						break;
					}
					if(this.ch(2)=='*')
					{
						this.readULBlock();
						break;
					}
					// surely ref
					this.readTextBlock();
					break;
				case 'i':
					// image
					this.readImageBlock();
					break;
				case 't':
					// table
					this.readTableBlock();
					break;
				case 's':
					// spoiler block (whether start or end, it doesn't make sense
					this.readSpoilerBlock();
					break;
				case '"':
					// quote
					this.readQuoteBlock();
					break;
				case '(':
					// listing
					this.readListingBlock();
					break;
				case '=':
					// horizontal line
					this.readHRBlock();
					break;
				case '+':
					// it's LI? ok, that means we're inside list.
					// it means error, but don't touch it
					this.error("Unexpected list item block");
					return;
				case '@': case '[':
					// must be a section
					return;
				default:
					// mb, that is fmt or just error
					this.readTextBlock();
					break;
				}
			}
			else
			{
				this.readTextBlock();
			}
		},
		
		// reads plain & formatted text, stops on first char of cmdln
		readTextBlock: function()
		{
			this.skipWhiteSpace();
			while(!this.eof())
			{
				// reading entries
				if(this.isctrl())
				{
					//this whould be a fmt
					switch(this.ch(1))
					{
					case '*': case '/': case '_': case '-': case '.':
						//bold, italic, underlined or skrikeout
						this.incp(); this.incp();
						this.readFmtEntry(this.ch(-1));
						break;
					case '{':
						if(this.ch(2)=='*' || this.ch(2)=='#')
						{
							// list begins here.
							return;
						}
						this.incp(); this.incp();
						this.readRefEntry();
						break;
					case '<':
						this.incp(); this.incp();
						this.readLinkEntry();
						break;
					default:
						if(this.tbol())
						{
							// it is the beginning of the line, so it means we
							// have a cmdln here; and this means the end of text block
							return;
						}
						this.error("Unknown formatter");
						this.incp();
						break;
					}
				}
				else
				{
					
					if(this.ch()=='\\' && !this.escaped())
					{
						// that is escaping char, miss it
						this.incp();
						continue;
					}
					//just a char
					this.apd(this.ch());
					if(this.ch()=='\n')
					{
						if(this.ch(1)=='\n')
						{
							this.apd("<br />");
						}
					}
					this.incp();
				}
			}
		},
		
		// reads a formatted entry
		readFmtEntry: function(fmt)
		{
			// `?text`
			//   ^
			if(fmt=='*') fmt='b';
			if(fmt=='/') fmt='i';
			if(fmt=='-') fmt='s';
			if(fmt=='_') fmt='u';
			if(fmt=='.') fmt='code';
			
			this.apd('<'+fmt+'>');
			while(!this.eof())
			{
				if(this.isctrl())
				{
					//end of fmt
					this.incp();
					this.apd('</'+fmt+'>');
					return;
				}
				if(this.ch()=='\\' && !this.escaped())
				{
					// ch() is '\' and it's not escaped => it's escaping char
					this.incp();
					continue;
				}
				this.apd(this.ch());
				if(this.ch()=='\n')
				{
					if(this.ch(1)=='\n')
					{
						this.apd("<br />");
					}
				}
				this.incp();
			}
			this.error("Unexpected eof while formatting");
		},
		
		readRefEntry: function()
		{
			var text=false;
			var ref="", label="";
			while(!this.eof())
			{
				var c=this.ch();
				if(c=='\\' && !this.escaped())
				{
					this.incp();
					continue;
				}
				if(c=='}')
				{
					text=true;
					this.incp();
					continue;
				}
				if(this.isctrl())
				{
					if(!text)
					{
						this.error("Unexpected ref end");
						this.incp();
						break;
					}
					this.incp();
					break;
				}
				if(text) label+=c;
				else ref+=c;
				this.incp();
			}
			
			// function refClicked is defined in interface.js
			this.apd("<span class='ref' onclick='refClicked(this);' data-to='");
			
			// ~ .endsWith("::label"), for possible future features
			/* if(ref.substring(ref.length-7, ref.length)=="::label") */
			if(ref.substring(ref.length-3, ref.length)=="::l")
			{
				// "::l" flag should be converted into "::label"
				ref+="abel";
			}
			if(ref=="::label")
			{
				//e.g. `{::label}Sprite` - should be converted into `{Sprite::label}Sprite`
				ref=label+ref;
			}
			
			this.apd(ref); this.apd("'>");
			this.apd(label); this.apd("</span>");
		},
		
		readLinkEntry: function()
		{
			this.apd("<a target='_blank' href='");
			var text=false;
			while(!this.eof())
			{
				var c=this.ch();
				if(c=='>')
				{
					text=true;
					this.apd("'>");
					this.incp();
					continue;
				}
				if(this.isctrl())
				{
					if(!text)
					{
						this.error("Unexpected link end");
						this.apd("'></a>");
						this.incp();
						return;
					}
					this.apd("</a>");
					this.incp();
					return;
				}
				if(c=='\\' && !this.escaped())
				{
					this.incp();
					continue;
				}
				this.apd(c);
				this.incp();
			}
		},
		
		readULBlock: function()
		{
			this.readListBlock("ul");
		},
		
		readOLBlock: function()
		{
			this.readListBlock("ol");
		},
		
		readListBlock: function(type)
		{
			// here we are now:
			// `{*
			// ^
			this.skipLine();
			
			this.apd("<"+type+">");
			
			while(!this.eof())
			{
				this.skipWhiteSpace();
				if(this.isctrl())
				{
					if(this.ch(1)=='+')
					{
						// it's LI
						this.incp();
						this.incp();
						this.readLIBlock();
						continue;
					}
					else if(this.ch(1)=='}')
					{
						// endof ul
						this.skipLine();
						break;
					}
					// mb that is a comment?
					else if(this.ch(1)=='`')
					{
						this.skipLine();
					}
					// nope. even don't know what it is
					this.error("Wrong cmdln inside list block");
					this.skipLine();
				}
				else
				{
					// there should be nothing except LIs and comments!
					this.error("Unexpected entry inside list block");
					this.skipLine();
				}
			}
			this.apd("</"+type+">");
		},
		
		readLIBlock: function()
		{
			this.apd("<li>");
			while(!this.eof())
			{
				this.skipWhiteSpace();
				if(this.isctrl())
				{
					if(this.ch(1)=='+')
					{
						// next LI found, abort
						break;
					}
					if(this.ch(1)=='}')
					{
						// list end
						break;
					}
					// any other block (including comment)
					document.char=this.ch(1);
					this.readBlock();
				}
				else
				{
					this.readTextBlock();
				}
			}
			this.apd("</li>");
		},
		
		readImageBlock: function()
		{
			// `i|src|desc
			// ^
			this.incp(); this.incp();
			if(this.ch()!='|')
			{
				this.error("Wrong image block format");
				this.skipLine();
				return;
			}
			this.incp();
			this.apd("<div class='figure'><img src='"+Prefs.dataRootUrl);
			var text=false;
			while(!this.eof())
			{
				if(this.ch()=='|')
				{
					// means the end of src
					text=true;
					this.incp();
					this.apd("' /><div class='label'>");
				}
				else if(this.ch()=='\n')
				{
					if(!text)
					{
						this.error("Unexpected image block end");
						this.apd("' /><div class='label'>");
						this.apd("Interpretation error</div></div>");
						this.skipLine();
						return;
					}
					this.apd("</div></div>");
					break;
				}
				else
				{
					this.apd(this.ch());
					this.incp();
				}
			}
		},
		
		readTableBlock: function()
		{
			// `t|c|r
			// ^
			this.incp(); this.incp();
			if(this.ch()!='|')
			{
				this.error("Wrong table block format");
				this.skipLine();
				return;
			}
			this.incp();
			var cols="", rows="";
			while(!this.eof())
			{
				if(this.ch()=='|')
				{
					this.incp();
					break;
				}
				if(this.ch()=='\n')
				{
					this.error("Unexpected table block end");
					this.skipLine();
					return;
				}
				cols+=this.ch();
				this.incp();
			}
			while(!this.eof())
			{
				if(this.ch()=='\n')
				{
					this.incp();
					break;
				}
				rows+=this.ch();
				this.incp();
			}
			cols=cols*1;
			rows=rows*1;
			if(isNaN(cols))
			{
				this.error("Unparsable number of columns: "+cols);
				return;
			}
			if(isNaN(rows))
			{
				this.error("Unparsable number of rows: "+rows);
				return;
			}
			
			this.apd("<table>");
			while(!this.eof() && rows>0)
			{
				var colsRead=0;
				this.apd("<tr><td>");
				while(!this.eof())
				{
					if(this.eol())
					{
						// end of line, == end of table row
						this.apd("</td>");
						colsRead++;
						this.incp();
						break;
					}
					else if(this.ch()=='|' && !this.escaped())
					{
						if(this.ch(-1)=='\t')
						{
							// table cells separator found
							this.apd("</td><td>");
							colsRead++;
							this.incp();
							continue;
						}
						// single '|' can't be here
						this.error("Unexpected '|' char inside table cell");
						this.incp();
					}
					else if(this.ch()=='\\' && !this.escaped())
					{
						//escaping char
						this.incp();
					}
					else
					{
						// regular char
						this.apd(this.ch());
						this.incp();
					}
				}
				// line ended
				while(colsRead<cols)
				{
					// not enough cols yet
					this.apd("<td></td>");
					colsRead++;
				}
				this.apd("</tr>");
				--rows;
			}
			this.apd("</table>");
		},
		
		readHRBlock: function()
		{
			// `=
			// ^
			this.skipLine();
			this.apd("<hr />");
		},
		
		readListingBlock: function()
		{
			// `(lang
			// ^
			this.incp(); this.incp();
			
			var lang="";
			while(!this.eof())
			{
				if(this.eol()) break;
				lang+=this.ch();
				this.incp();
			}
			
			this.apd("<div class='listing'>");
			
			var code="";
			while(!this.eof())
			{
				if(this.ch()=='`')
				{
					if(this.ch(1)==')')
					{
						// end of listing
						this.skipLine();
						break;
					}
				}
				code+=this.ch();
				this.incp();
			}
			
			this.apd(SyntaxAnalyzer.processCode(code, lang));
			
			this.apd("</div>");
		},
		
		readSpoilerBlock: function()
		{
			// `s|%title%
			// ^
			this.incp(); this.incp();
			if(this.ch()=='|')
			{
				// spoiler block begins here, and everything before '\n'
				// is it's title
				this.apd("<div class='spoiler'><div class='label' onclick='toggleSpoiler(this);'>");
				// toggleSpoiler(HTMLElement) is a function from interface.js
				this.incp();
				while(!this.eof())
				{
					//reading spoiler title
					if(this.ch()=='\n')
					{
						// spoiler title end
						this.apd("</div><div class='content'>");
						this.incp();
						break;
					}
					this.apd(this.ch());
					this.incp();
				}
			}
			else
			{
				//it's the end of spoiler block
				this.apd("</div></div>");
			}
		},
		
		readQuoteBlock: function()
		{
			// `"
			// ^
			this.incp(); this.incp();
			this.skipWhiteSpace();
			
			this.apd("<div class='quote'><div class='content'>");
			while(!this.eof())
			{
				if(this.isctrl())
				{
					this.incp();
					if(this.ch()=='"')
					{
						// seems to be the end
						this.incp();
						break;
					}
					else
					{
						this.error("Unexpected control char inside quote block");
						continue;
					}
				}
				this.apd(this.ch());
				this.incp();
			}
			this.apd("</div><div class='label'>");
			while(!this.eof())
			{
				//now, read until '\n' for the author
				if(this.ch()=='\n')
				{
					break;
				}
				this.apd(this.ch());
				this.incp();
			}
			this.apd("</div></div>");
		},
		
		// reads SA, stops on eof
		readSABlock: function()
		{
			// `@
			// ^
			this.skipLine();
			
			//for the case of empty SA
			this.skipWhiteSpace();
			if(this.eof()) return;
			
			this.apd("<div class='see-also'><ul><li>");
			
			while(!this.eof())
			{
				if(this.eol())
				{
					// eol is a separator between LIs in SA gblock
					this.apd("</li><li>");
					this.skipWhiteSpace();
					continue;
				}
				if(this.ch()=='`')
				{
					switch(this.ch(1))
					{
					case '`':
						// comment
						this.skipLine();
						this.skipWhiteSpace();
						break;
					case '*': case '/': case '_': case '-': case '.':
						// fmt
						this.incp(); this.incp();
						this.readFmtEntry(this.ch(-1));
						break;
					case '{':
						// ref
						this.incp(); this.incp();
						this.readRefEntry();
						break;
					case '<':
						// link
						this.incp(); this.incp();
						this.readLinkEntry();
						break;
					default:
						// unallowed control seq
						this.error("Unexpected control sequence in see-also block");
						this.skipLine();
						this.skipWhiteSpace();
						break;
					}
					continue;
				}
				// regular char
				this.apd(this.ch());
				this.incp();
			}
			
			this.apd("</li></ul></div>");
		},
		
		
		getParsedText: function()
		{
			return this.parsed;
		},
		
		getErrorsLog: function()
		{
			return this.errors;
		},
		
		error: function(msg)
		{
			this.errors.push("["+(new Date().toLocaleString())+"; "+this.line+"]: "+msg);
		}
	};
}