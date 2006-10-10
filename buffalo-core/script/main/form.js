Buffalo.Form = {
	formToBean : function(form, boClass, ignoreButton) {
		var object = {};
		if (boClass) { object[Buffalo.BOCLASS] = boClass; } else{
			object[Buffalo.BOCLASS] = "java.util.Map";
		}
		if (typeof(ignoreButton) == "undefined" || ignoreButton == true) {
			ignoreButton = true;
		} else {
			ignoreButton = false;
		}
		
		form = $(form);
		var elements = form.elements;
		for (var i = 0; i < elements.length;i++) {
			var element = elements[i];
			switch (element.type) {
			case "radio" : 
				if (element.checked) { 
					object[element.name]=element.value
				} 
				break;
			case "checkbox" : 
				if (!form[element.name].length) {
					if (element.checked) object[element.name]=element.value ;
					else object[element.name]="";
				} else {
					if (!object[element.name]) {object[element.name] = new Array()};
    				if (element.checked) {object[element.name].push(element.value);}
				}
				break;
			case "select-one" : 
				var value = '', opt, index = element.selectedIndex;
				if (index >= 0) {
					opt = element.options[index];
					value = opt.value;
					if (!value && !('value' in opt)) value = opt.text;
				}
				object[element.name] = value;
				break;
			case "select-multiple" :
				if (!object[element.name]) {object[element.name] = new Array()};
				for (var j = 0; j < element.options.length; j++) {
					var opt = element.options[j];
					if (opt.selected) {
						var optValue = opt.value;
						if (!optValue && !('value' in opt)) optValue = opt.text;
						object[element.name].push(optValue);
					}
			    }
			    break;
			default : 
				if (ignoreButton) {
					if (element.type != "submit" && element.type != "button" 
						&& element.type != "reset") {
						object[element.name] = element.value;
					}
				} else {
					object[element.name] = element.value;
				}
				break;
			}
		}
		
		return object;
	},
	
	bindForm: function(form, data) {
		form = $(form);
		for (var i = 0; i < form.elements.length;i++) {
			var element = form.elements[i];
			if (!data[element.name]) continue;
			var val = data[element.name];
			switch (element.type) {
			case "text": ;
			case "hidden": ;
			case "password": element.value = val; break;
			case "radio" : 
			case "checkbox" : 
				if (val instanceof Array) element.checked = (val.indexOf(element.value) > -1);
				else element.checked = (element.value ==val);
				break;
			case "select-one" : 
			case "select-multiple" : 
				for (var j = 0; j < element.options.length; j++) {
					var option = element.options[j];
					if (val instanceof Array) {
						option.selected = (val.indexOf(option.value) > -1);
					} else {
						option.selected = (option.value == val);
					}
				}
				break;
			}
		}
	},
	
	validate : function(formId) {
		
	}
}
