var DemoApp = Class.create();

var demoInstance = null;
var processing = false;
var pageBuffalo = null;
var lastMenu = null;
var loadingEffect = null;

DemoApp.prototype = {

	initialize: function(endPoint, language) {
		this.endPoint = endPoint;
		this.language = language;
		demoInstance = this;
		$$("#nav a").each(function(menu){
			Event.observe(menu, "click", function(e) {
				if (processing) return;
				$$("#nav a").each(function(menu){Element.removeClassName(menu, "activelink")});
				Element.addClassName(menu, "activelink");
				if(processing) return;
				if (lastMenu == menu) return;
				lastMenu = menu;
				
				demoInstance.switchMenu(menu.getAttribute("id"), menu.href);
			});
		});
		$$("#subnav a").each(function(item){
			Event.observe(item, "click", function(e){
				pageBuffalo.switchView(item.href, "maincontent");
			});
		})
		pageBuffalo = new Buffalo(endPoint, true, {onLoading:this.onPageLoading.bind(this)});
		Nifty("div#content,div#subnav");
		Nifty("ul#nav a","small transparent top");
	},
	
	switchMenu: function(id, target) {
		processing = true;
		$$("#subnav div").each(function(item){
			if (item.style.display=="block") {
				item.hide();	
			}
		});

		var opened = false;
		var effect = new fx.Height($('subnav') , {duration: 200, onComplete: function() {
		  	if (!opened) {
				$("subnav-"+id).style.display="block";
				effect.toggle();
				opened = true;	
				processing = false;
				pageBuffalo.switchView(target, "maincontent");
			}
		  }
		});
		effect.toggle();
	},
	
	onPageLoading: function(state) {
		if (loadingEffect) loadingEffect.clearTimer();
		loadingEffect = new fx.Opacity($('content'), {duration:300});
		
		if (!state) {
			loadingEffect.custom(0.2,1);			
		} else {
			loadingEffect.custom(1,0.2);	
		}
	}
}
