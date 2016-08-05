// Based on the firebug plugin

(function() {
	var log,
		PageRecorder,
		addCSSStyle,
		createCommand,
		getInputElementsByTypeAndValue,
		getPageXY,
		getCssSelectorPath,
		getElementId,
		getXPath,
		mouseHoverOver,
		prev = void 0,
		preventEvent,
		getCommandGuid,
		rightClickHandler;

	var ELEMENT_NODE = Node.ELEMENT_NODE;

	log = function(something) {
		if (typeof console !== "undefined" && console !== null) {
			return console.log(something);
		}
	};

	getCommandGuid = function() {
		var result;
		result = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx';
		result = result.replace(/[xy]/g, function(re_match) {
			var random_value, replacement;
			random_value = Math.random() * 16 | 0;
			replacement = re_match === 'x' ? random_value : random_value & 0x3 | 0x8;
			return replacement.toString(16);
		});
		log("getCommandGuid:" + result);
		return result;
	};

	getInputElementsByTypeAndValue = function(inputType, inputValue) {
		var allDocumentInputElements, inputElement, result, _i, _len;
		log("getInputElementsByTypeAndValue");
		allDocumentInputElements = document.getElementsByTagName('input');
		result = new Array();
		for (_i = 0, _len = allDocumentInputElements.length; _i < _len; _i++) {
			inputElement = allDocumentInputElements[_i];
			if (inputElement.type === inputType && inputElement.value === inputValue) {
				result.push(inputElement);
			}
		}
		log("getInputElementsByTypeAndValue");
		return result;
	};
	getElementId = function(element) {
		var selector = '';
		log('getElementId');
		if (element instanceof Element && element.nodeType === ELEMENT_NODE && element.id) {
			selector = element.id;
		}
		log('getElementId');
		return selector;
	};

	isTagNameUnique = function(node) {
		var tag = node.tagName.toLowerCase();
		var doc = node.ownerDocument;
		return doc.getElementsByTagName(tag).length == 1;
	}
	getNodePosition = function(node) {
		if (!node.parentElement)
			return '';

		var siblings = node.parentElement.children;
		var count = 0;
		var position;

		for (var i = 0; i < siblings.length; i++) {
			var object = siblings[i];
			if (object.nodeType == node.nodeType && object.nodeName == node.nodeName) {
				count++;
				if (object == node) position = count;
			}
		}

		if (count > 1)
			return position;
		else
			return '';
	}
	const PageRecorderClass = /\s*PageRecorder\s*/g;

	isClassNameUnique = function(node) {
		if (!node.className || !node.className.replace(PageRecorderClass, '')) {
			return '';
		}
		var classname = node.className.replace(PageRecorderClass, '');
		var doc = node.ownerDocument;
		//avoid to use classname contains too many spaces
		if (classname.split(' ').length > 3) {
			return '';
		}
		if (doc.getElementsByClassName(classname).length == 1) {
			return classname;
		}
		return '';
	}
	getUniqueClassPath = function(node) {

		if (!node.className || !node.className.replace(PageRecorderClass, '')) {
			return '';
		}
		if (node.className.toLowerCase().indexOf('active') > 0) {
			return '';
		}
		var className = node.className.replace(PageRecorderClass, '').split(' ');
		var len = className.length;
		var selector = '.' + className[len - 1];
		var document = node.ownerDocument;
		for (i = len - 1; i >= 0; i--) {
			if (document.querySelectorAll(selector).length == 1)
				return selector;
			selector = '.' + className[i] + selector;

		}
		return '';
	}
	isInForm = function(node) {
		if (!node.form) {
			return false;
		}
		return true;
	}

	getForAttribute = function(node) {
		var toReturn = ''
		var document = node.ownerDocument;
		if (node.hasAttribute('for')) {
			toReturn = node.attributes['for'].value;
			if (document.querySelectorAll("[for='" + toReturn + "']").length != 1) {
				toReturn = '';
			}

		}

		return toReturn;
	}

	getPageObjectElementName = function(element) {
		var element_sibling, siblingTagName, siblings, cnt, sibling_count;

		log("getPageObjectElementName");
		var elementTagName = element.tagName.toLowerCase();
		//log("element id:"+element.id)
		if (element.id != '') {
			//log("not empty elementid")
			return elementTagName + '_' + element.id;
			// alternative : 
			// return '*[@id="' + element.id + '"]';
		} else if (element.name && document.getElementsByName(element.name).length === 1) {
			return elementTagName + '_' + element.name;
		}
		if (element === document.body) {
			return 'html_' + elementTagName;
		}
		sibling_count = 0;
		siblings = element.parentNode.childNodes;
		siblings_length = siblings.length;
		for (cnt = 0; cnt < siblings_length; cnt++) {
			var element_sibling = siblings[cnt];
			if (element_sibling.nodeType !== ELEMENT_NODE) { // not ELEMENT_NODE
				continue;
			}
			if (element_sibling === element) {
				return getPageObjectElementName(element.parentNode) + '_' + elementTagName + (sibling_count + 1);
			}
			if (element_sibling.nodeType === 1 && element_sibling.tagName.toLowerCase() === elementTagName) {
				sibling_count++;
			}
		}
		return log("getPageObjectElementName");
	};


	getCssSelectorPath = function(node) {

		var parent = node.ownerDocument,
			stop = false,
			path = [];
		var classpath = '';
		if (!(node instanceof Element))
			return;
		while (node && node != parent && !stop) {
			if (node.nodeType === ELEMENT_NODE) {
				var selector = node.tagName.toLowerCase();
				if (node.id) {
					if (node.id.indexOf(' ') > 0) {
						selector += "[id='" + node.id + "']";
					} else {
						selector += '#' + node.id;
					}
					stop = true;
				} else if (selector == 'label' && isInForm(node) && (_for = getForAttribute(node))) {
					selector += "[for='" + _for + "']";
					stop = true;
				} else if (classpath = getUniqueClassPath(node)) {
					selector += classpath;
					stop = true;
				} else if (isTagNameUnique(node)) {
					selector += '';
					stop = true;
				} else {
					var position = getNodePosition(node);
					if (position) {
						selector += ':nth-of-type(' + position + ')';
					} else {
						selector += '';
					}
				} //end node.id
				path.unshift(selector);

			} //end node.nodeType === ELEMENT_NODE	
			if (node instanceof Attr) {
				node = node.ownerElement;
			} else {
				node = node.parentNode;
			}
			selector = node.tagName.toLowerCase();
			if (selector == 'body') {
				stop = true;
			}

		} //end node && node != parent && !stop

		return path.join('>');
	};

	getAbsoluteXPath = function(element) {
		var element_sibling, siblingTagName, siblings, cnt, sibling_count;
		var elementTagName = element.tagName.toLowerCase();
		if (element === document.body) {
			return '/html/' + elementTagName;
		}
		sibling_count = 0;
		siblings = element.parentNode.childNodes;
		siblings_length = siblings.length;
		for (cnt = 0; cnt < siblings_length; cnt++) {
			var element_sibling = siblings[cnt];
			if (element_sibling.nodeType !== Node.ELEMENT_NODE) { // not ELEMENT_NODE
				continue;
			}
			if (element_sibling === element) {
				return getAbsoluteXPath(element.parentNode) + '/' + elementTagName + '[' + (sibling_count + 1) + ']';
			}
			if (element_sibling.nodeType === 1 && element_sibling.tagName.toLowerCase() === elementTagName) {
				sibling_count++;
			}
		}
		return log("finish the getAbsolutePath");
	};

	getXPath = function(node) {

		var result = "";
		var stop = false;
		var usingClass = true;
		var absolute = getAbsoluteXPath(node);
		var classname = '';

		var parent = node.ownerDocument;
		while (node && node != parent && !stop) {
			var str = "";
			var position = getNodePosition(node);
			switch (node.nodeType) {
				case Node.DOCUMENT_NODE:
					break;
				case Node.ATTRIBUTE_NODE:
					str = "@" + node.name;
					break;
				case Node.COMMENT_NODE:
					str = "comment()";
					break;
				case Node.TEXT_NODE:
					str = "text()";
					break;
				case Node.ELEMENT_NODE:

					var name = node.tagName.toLowerCase();

					if (node.id && node.id != "") {
						str = ".//*[@id='" + node.id + "']";
						position = null;
						stop = true;
					} else if (name == 'label' && isInForm(node) && (_for = getForAttribute(node))) {
						str = ".//*[@for='" + _for + "']";
						stop = true;
					} else if (classname = isClassNameUnique(node) && usingClass) {
						str = ".//*[@class='" + classname + "']";
						position = null;
						stop = true;
					} else if (isTagNameUnique(node)) {
						str = ".//" + name;
						position = null;
						stop = true;
					} else {
						str = name;
					}

					break;
			}

			result = str + (position ? "[" + position + "]" : "") + (result ? "/" : "") + result;

			node = node.parentElement;
			var selector = node.tagName.toLowerCase();
			if (selector == 'body') {
				stop = true;
			}
		}


		return result;

	};

	getPageXY = function(element) {
		var x, y;
		log("getPageXY");
		x = 0;
		y = 0;
		while (element) {
			x += element.offsetLeft;
			y += element.offsetTop;
			element = element.offsetParent;
		}
		log("getPageXY");
		return [x, y];
	};

	createCommand = function(jsonData) {
		var myJSONText;
		log("createCommand");
		myJSONText = JSON.stringify(jsonData, null, 2);
		document.selenium_data = myJSONText;
		return log("selenium_command is: " + myJSONText);
	};

	addCSSStyle = function(css) {
		var head, style;
		log("addCSSStyle");
		head = document.getElementsByTagName('head')[0];
		style = document.createElement('style');
		style.type = 'text/css';
		if (style.styleSheet) {
			style.styleSheet.cssText = css;
		} else {
			style.appendChild(document.createTextNode(css));
		}
		head.appendChild(style);
		return log("addCSSStyle");
	};

	preventEvent = function(event) {
		log("preventEvent");
		if (event.preventDefault) {
			event.preventDefault();
		}
		event.returnValue = false;
		if (event.stopPropagation) {
			event.stopPropagation();
		} else {
			event.cancelBubble = true;
		}
		log("preventEvent");
		return false;
	};


	document.pr_prevActiveElement = void 0;

	mousedownEvent = function(event) {

		if (document.PageRecorder == null) {
			return;
		}
		document.getElementById('PageRecorder_PopUp').style.display = 'none';
		log("mousedownEvent is:" + event.target)
	};

	isPopupWindow = function(event) {
		var element = 'target' in event ? event.target : event.srcElement;
		var elementTagName = element.tagName.toLowerCase();
		if (element === document.body) {
			return '/html/' + elementTagName;
		}
		sibling_count = 0;
		siblings = element.parentNode.childNodes;
		siblings_length = siblings.length;
		for (cnt = 0; cnt < siblings_length; cnt++) {
			var element_sibling = siblings[cnt];
			if (element_sibling.nodeType !== Node.ELEMENT_NODE) { // not ELEMENT_NODE
				continue;
			}
			if (element_sibling === element) {
				return getAbsoluteXPath(element.parentNode) + '/' + elementTagName + '[' + (sibling_count + 1) + ']';
			}
			if (element_sibling.nodeType === 1 && element_sibling.tagName.toLowerCase() === elementTagName) {
				sibling_count++;
			}
		}
	};
	//https://developer.mozilla.org/en-US/docs/Web/API/MouseEvent
	mouseHoverOver = function(event) {
		//log("mouseHoverOver");
		//log("mouseHoverOver event is: " + event.target + ",keycode is: " + event.ctrlKey)
		//event.stopPropagation();
		if (document.PageRecorder == null) {
			return;
		}
		if (prev != event.target && !isPopupWindow) {
			document.getElementById('PageRecorder_PopUp').style.display = 'none'
			return;
		}
		if (prev) {
			log("prev the event to occurred")
			//prev.style.background='';
			prev.className = prev.className.replace(/\s?\PageRecorder_highlight\b/, '');
			prev = void 0;
		}

		return log("mouseHoverOver");
	};

	rightClickHandler = function(event) {
		var JsonData, body, eventPreventingResult, mxy, path, root, target, txy, xpath, css_selector, id;
		log("rightClickHandler");
		if (document.PageRecorder == null) {
			return;
		}
		//remove the prvious step's event
		if (prev) {
			log("prev the event to occurred")
			//prev.style.background='';
			prev.className = prev.className.replace(/\s?\PageRecorder_highlight\b/, '');
			prev = void 0;
		}
		prev = event.target;
		//prev.style.background='#A9A9A9';
		prev.className += " PageRecorder_highlight";

		//if (event.ctrlKey) {
		eventPreventingResult = preventEvent(event);
		if (event == null) {
			event = window.event;
		}
		target = 'target' in event ? event.target : event.srcElement;
		root = document.compatMode === 'CSS1Compat' ? document.documentElement : document.body;
		mxy = [event.clientX + root.scrollLeft, event.clientY + root.scrollTop];
		name = getPageObjectElementName(target);
		path = getXPath(target);
		txy = getPageXY(target);
		css_selector = getCssSelectorPath(target);
		id = getElementId(target);
		body = document.getElementsByTagName('body')[0];
		xpath = path;
		JsonData = {
			"Command": "rightClickHandler",
			"Caller": "EventListener : mousedown",
			"CommandId": getCommandGuid(),
			"ElementId": id,
			"CSS": css_selector,
			"XPath": xpath
		};
		createCommand(JsonData);
		document.PageRecorder.showPos(event, name, xpath, css_selector, id);

		log("rightClickHandler");
		return eventPreventingResult;
		//}
	};

	PageRecorder = (function() {
		function PageRecorder() {}

		PageRecorder.prototype.getMainWinElement = function() {
			return document.getElementById('PageRecorder_PopUp');
		};

		PageRecorder.prototype.displayPageRecorderForm = function(x, y) {
			var el;
			log("displayPageRecorderForm");
			el = this.getMainWinElement();
			el.style.background = "#A9A9A9";
			el.style.position = "absolute";
			el.style.left = x + "px";
			el.style.top = y + "px";
			el.style.display = "block";
			el.style.border = "5px solid black";
			el.style.padding = "20px 20px 20px 20px";
			el.style.zIndex = 2147483647;
			el.style.fontweight = 'bold';
			//el.style.backgroundcolor="#A9A9A9";
			return log("displayPageRecorderForm");
		};

		PageRecorder.prototype.showPos = function(event, name, xpath, css_selector, id) {
			var x, y;
			log("showPos");
			if (window.event) {
				x = window.event.clientX + document.documentElement.scrollLeft + document.body.scrollLeft;
				y = window.event.clientY + document.documentElement.scrollTop + document.body.scrollTop;
			} else {
				x = event.clientX + window.scrollX;
				y = event.clientY + window.scrollY;
			}
			x += 10;
			y -= 2;
			y = y + 15;
			this.displayPageRecorderForm(x, y);
			//document.getElementById("PageRecorder_PopUp_XPathLocator").innerHTML = xpath;
			document.getElementById("PageRecorder_PopUp_XPathLocator").innerHTML = xpath;
			document.getElementById("PageRecorder_PopUp_CssSelector").innerHTML = css_selector;
			document.getElementById("PageRecorder_PopUp_ElementId").innerHTML = id;
			document.getElementById("PageRecorder_PopUp_ElementText").innerHTML = getCommandGuid();
			document.getElementById("PageRecorder_PopUp_CodeIDText").value = name;
			log(x + ";" + y);
			return log("showPos");
		};

		PageRecorder.prototype.closeForm = function() {
			return document.getElementById('PageRecorder_PopUp').style.display = 'none';
		};

		PageRecorder.prototype.createFormHtmlCode = function() {
			var closeClickHandler, element;
			log("createFormHtmlCode");
			element = document.createElement("div");
			element.id = 'PageRecorder_PopUp';
			if (document.body != null) {
				document.body.appendChild(element);
			} else {
				log("createFormHtmlCode Failed to inject element PageRecorder_PopUp. The document has no body");
			}
			closeClickHandler = "";
			element.innerHTML = '\
        <table id="PageRecorder_Table">\
        <thead>\
         <tr>\
            <th>Web Element</th>\
            <th>Value</th>\
          </tr>\
        </thead>\
        <tbody>\
            <tr>\
              <td>Web Element Name</td>\
              <td>\
                    <div id="PageRecorder_PopUp_Element_Name">\
                        <span id="PageRecorder_PopUp_CodeID">\
                            <input type="text" id="PageRecorder_PopUp_CodeIDText">\
                        </span>\
                        <span id="PageRecorder_PopUp_CodeClose"></span>\
                        <span id="PageRecorder_PopUp_CloseButton" onclick="document.PageRecorder.closeForm()">X</span>\
                     </div>\
              </td>\
            </tr>\
            <tr>\
              <td>Web Element</td>\
              <td><span id="PageRecorder_PopUp_ElementName">WebElement</span></td>\
            </tr>\
            <tr>\
              <td>Web Element Id</td>\
              <td><span id="PageRecorder_PopUp_ElementId">Element</span></td>\
            </tr>\
            <tr>\
              <td>Web Element Text</td>\
              <td><span id="PageRecorder_PopUp_ElementText">Element</span></td>\
            </tr>\
            <tr>\
              <td>Web Element XPath</td>\
              <td><span id="PageRecorder_PopUp_XPathLocator">Element</span></td>\
            </tr>\
            <tr>\
              <td>Web Element CSS</td>\
              <td><span id="PageRecorder_PopUp_CssSelector">Element</span></td>\
            </tr>\
            </tbody>\
            </table>\
            <input type="button" class="addElementbtn" value="Add tWo" onclick="document.PageRecorder.addElement()"> &nbsp;\
            <input type="button" class="addElementbtn" value="Add Element" onclick="document.PageRecorder.addElement()">\
        ';
			return log("createFormHtmlCode");
		};

		PageRecorder.prototype.addElement = function() {
			var JsonData, XPathLocatorElement, codeIDTextElement, htmlIdElement;
			log("addElement");
			codeIDTextElement = document.getElementById("PageRecorder_PopUp_CodeIDText");
			htmlIdElement = document.getElementById("PageRecorder_PopUp_ElementId");
			CssSelectorElement = document.getElementById("PageRecorder_PopUp_CssSelector");
			XPathLocatorElement = document.getElementById("PageRecorder_PopUp_XPathLocator");
			JsonData = {
				"Command": "AddElement",
				"Caller": "addElement",
				"CommandId": getCommandGuid(),
				"ElementName": codeIDTextElement.value,
				"ElementId": (htmlIdElement.hasChildNodes()) ? htmlIdElement.firstChild.nodeValue : "",
				"CSS": CssSelectorElement.firstChild.nodeValue,
				"XPath": XPathLocatorElement.firstChild.nodeValue
			};
			createCommand(JsonData);
			return log("PageRecorder");
		};

		return PageRecorder;

	})();

	addCSSStyle(".PageRecorder_highlight { outline: 1px dashed #A9A9A9;background:#A9A9A9; }");

	addCSSStyle("table#PageRecorder_Table { border-collapse:collapse; }   table#PageRecorder_Table,table#PageRecorder_Table th, table#PageRecorder_Table td  { font-family: Verdana, Arial; font-size: 10pt;   padding-left:10pt; padding-right:10pt;  border-bottom: 1px solid black;  }");

	// addCSSStyle("table#PageRecorder_Table input { background: white !important; } ");

	addCSSStyle("input#PageRecorder_PopUp_CodeIDText { display:table-cell;width:95%; background: white ;}");

	addCSSStyle("span#PageRecorder_PopUp_CloseButton {display:table-cell;-moz-border-radius: 4px;-webkit-border-radius: 4px;            -o-border-radius: 4px;            border-radius: 4px;            border: 1px solid #ccc;            color: white;            background-color: #980000;            cursor: pointer;            font-size: 10pt;            padding: 0px 2px;            font-weight: bold;            position: absolute;            right: 20px;            top: 20px;          }");

	addCSSStyle("div#PageRecorder_PopUp {display:none;} div#PageRecorder_PopUp_Element_Name { display:table; width: 100%; }");

	addCSSStyle(".addElementbtn {position: relative;left: 50%;top:15px; transform: translate(-50%, 0);text-transform: uppercase;background-color: #eaeff2;}")
		/* 
		    Important!
		    It wont work if the document has no body, such as top frameset pages.
		*/
		//log("document.body:"+document.body)
	if (document.body != null) {
		if (document.body.addEventListener) {
			log("<addEventListener></addEventListener>");
			document.body.addEventListener('mouseover', mouseHoverOver, false);
			document.addEventListener('contextmenu', rightClickHandler, false);
			//document.body.addEventListener('mouseleave', mousedownEvent, false);
		} else if (document.body.attachEvent) {
			//log("attachEvent");
			document.body.attachEvent('mouseover', function(e) {
				return mouseHoverOver(e || window.event);
			});
			document.body.attachEvent('oncontextmenu', function(e) {
				return rightClickHandler(e || window.event);
			});
		} else {
			document.body.onmouseover = mouseHoverOver;
			document.body.onmouseover = rightClickHandler;
		}
		document.PageRecorder = new PageRecorder();
		document.PageRecorder.createFormHtmlCode();
	} else {
		log("Document has no body tag... Injecting empty SWD");
		document.PageRecorder = "STUB. Document has no body tag :(";
	}

}).call(this);