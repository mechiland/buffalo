Object.extend(Buffalo.prototype, {
	bindReply : function(service, params, bindElemId) {
		this.remoteCall(service, params, function(reply) {
			Buffalo.Bind.bind(bindElemId, reply.getResult());
		})
	}
});

Buffalo.Bind = {
	bind : function(elementId, bindValue) {
		var elem = $(elementId);
		switch(elem.tagName) {
			case "INPUT": 
				switch (elem.type.toLowerCase()) {
					
					case "text": ;
					case "hidden": ;
					case "password": Buffalo.BindFactory.bindText(elem, bindValue); break;

					case "checkbox": ;
					case "radio": Buffalo.BindFactory.bindRadioOrCheckbox(elem, bindValue); break;
				}
				break;
			case "TEXTAREA":
				Buffalo.BindFactory.bindText(elem, bindValue);
				break; 
			case "TABLE": 
				Buffalo.BindFactory.bindTable(elem, bindValue);
				break; 
			case "SELECT": 
				Buffalo.BindFactory.bindSelect(elem, bindValue);
				break; 
			case "DIV":
			case "SPAN":
				elem.innerHTML = bindValue;
				break;
			//TODO: add more bindings here for 
		}
	}
}

Buffalo.BindFactory = {
	reportError: function(elem, value, msg) { 
		throw "Data bind failed: "+msg;
	},
	
	bindText: function(elem, value) { 
		elem.value = value;
	},
	
	bindRadioOrCheckbox: function(elem, value) {
		elem.checked = Buffalo.BindFactory.checkTrue(value);
	},

	bindSelect : function(elem, value) {
		//TODO: Check the data type
		if (typeof(value) != "object" || value.constructor != Array) {
			this.reportError(elem,value,"Array Type Needed for binding select!");
		}
		// delete all the nodes.
		while (elem.childNodes.length > 0) {
			elem.removeChild(elem.childNodes[0]);
		}
		
		// bind data
		for (var i = 0; i < value.length; i++) {
			
			var option = document.createElement("OPTION");
			
			var data = value[i];
			if (typeof(data) != 'object') {
				option.value = data;
				option.text = data;
			} else {
				option.value = data[elem.getAttribute("jvalue")];
				option.text = data[elem.getAttribute("jtext")];
				if (Buffalo.BindFactory.checkTrue(data.selected)) {
					option.selected = true;	
				}
			}
			elem.options.add(option);
		}
	},

	bindTable: function(elem, value) {
		var jHeight = parseInt(elem.getAttribute("jheight"));
		var dataHeader = [];
		var tBody = elem.getElementsByTagName("TBODY")[0];
		
		// clear the generated rows
		if (elem.getElementsByTagName("TBODY").length > 0) {
			while (tBody.rows.length > jHeight) {
					tBody.deleteRow(jHeight);
			}
		}

		if (jHeight == 0) { // if table is null, push the data to the tables.

			for (var x in value[0] ) {
				dataHeader[dataHeader.length] = x;
			}

			var hTr = elem.insertRow(elem.rows.length);
			for (var i = 0; i < dataHeader.length; i++) {
				var td = hTr.insertCell(hTr.cells.length);
				td.innerHTML = dataHeader[i];
			}
			
			for (var i = 0; i < value.length; i++) {
				var tr = elem.insertRow(elem.rows.length);
				var data = value[i];
				for (x in data ) {
					var td = tr.insertCell(tr.cells.length);
					td.innerHTML = data[x];
				}
			}	
		}
		
		if (jHeight == 1) { // if there is only one line, first line is header(every td indicate by a jtext property)
			var headerTR = tBody.rows[0];

			for (var i = 0; i < headerTR.cells.length ; i++ ) {
				dataHeader[dataHeader.length] = headerTR.cells[i].getAttribute("jtext");
			}
			
			for (var i = 0; i < value.length; i++) {
				var tr = tBody.insertRow(tBody.rows.length);
				var data = value[i];
				for (var j = 0; j < dataHeader.length; j++ ) {
					var td = tr.insertCell(tr.cells.length);
					td.innerHTML = data[dataHeader[j]];
				}
			}	
		}

		if (jHeight == 2) { // two lines, first line is header, the second is style

			var headerTR = tBody.rows[0];

			for (var i = 0; i < headerTR.cells.length ; i++ ) {
				dataHeader[dataHeader.length] = headerTR.cells[i].getAttribute("jtext");
			}

			for (var i = 0; i < value.length; i++) {
				
				var tr;
				
				if (i == 0) { // if the first row
					tr = elem.rows[1];
				} else { // else copy the first row
					tr = elem.rows[1].cloneNode(true);
				}

				if (i > 0) 	{
					tBody.appendChild(tr);
				}

				var data = value[i];
				for (var j = 0; j < tr.cells.length; j++ ) {
					var td = tr.cells[j];
					
					td.innerHTML = data[dataHeader[j]];
				}
				
			}	
		}

		if (jHeight >= 3) { // more than 3 rows, first header, second and third is odd/even style, other lines ommited.
			var headerTR = tBody.rows[0];
			for (var i = 0; i < headerTR.cells.length ; i++ ) {
				dataHeader[dataHeader.length] = headerTR.cells[i].getAttribute("jtext");
			}
			for (var i = 0; i < value.length; i++) {
				var tr;
				
				if (i == 0) { // 1st row
					tr = tBody.rows[1];
				} else if (i == 1) 	{ // 2nd row
					tr = tBody.rows[2];
				} else if ( i % 2 == 0) { // get the 1st row
					tr = tBody.rows[1].cloneNode(true);
				} else if (i % 2 == 1) { // the 2nd row
					tr = tBody.rows[2].cloneNode(true);
				}

				
				if (i > 1) 	{
					tBody.appendChild(tr);
				}

				var data = value[i];
				
				for (var j = 0; j < tr.cells.length; j++ ) {
					var td = tr.cells[j];	
					td.innerHTML = data[dataHeader[j]];
				}
			}	
		}
		
	},
	
	bindRepeater:function(elem, value) {
		//TODO: implementation will be added.
	},
	
	checkTrue: function(value) {
		switch (typeof(value)) {
			case 'boolean': ret = value; break;
			case 'string': ret = (value == true || value == "1" || value == "true" || value == "yes"); break;
			case 'number': ret = (parseInt(value) == 1); break;
			default: ret = false;
		}
		return ret; 
	}
}
Buffalo.bind = Buffalo.Bind.bind; /*capable with the old version, deprecated*/
