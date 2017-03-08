Function.prototype.method = function(name, func) {
	if(!this.prototype[name]){
		this.prototype[name] = func;
	}
	return this;
}
function Direction(targ) {
	if(typeof targ === "string") {
		var first_letter = targ.substr(0, 1),
			other_letter = targ.substr(1);
		switch (first_letter) {
		case "#":
			return document.getElementById(other_letter);
			break;
		case ".":
			if (document.querySelectorAll) {
				return document.querySelectorAll(targ);
			} else {
				var targArr = [];
				function getNode(elem){
					if(elem.className){
						var classArr = elem.className.split(" ");
						for(var i = 0; i < classArr.length; i++){
							if(classArr[i] == other_letter) {
								targArr.push(elem);
								break;
							}
						}
					}
					if(elem.childNodes.length){
						for(var i = 0; i < elem.childNodes.length; i++){
							if(elem.childNodes[i].nodeType == 1){
								getNode(elem.childNodes[i]);
							}
						}
					}
				}
				getNode(document.body);
				return targArr;
			}
			break;
		default: 
			return document.getElementsByTagName(targ);
		}
	}
}

Direction.getEvent = function(event){
	return event ? event : window.event;
};
//获得某标签的位置
Direction.get_pos = function(elem){
	if(!elem) return false;
	var left = elem.offsetLeft,
		top = elem.offsetTop,
		current = elem.offsetParent;
	while(current !== null){
		left += current.offsetLeft;
		top += current.offsetTop;
		current = current.offsetParent;
	}
	return {"left": left, "top": top};
};
//判断鼠标是从哪个方向移动到某标签上的
Direction.get_dir = function(elem, mouse_pos){
	if(!elem) return false;
	var pos = Direction.get_pos(elem),
		size = {"width": elem.offsetWidth, "height": elem.offsetHeight},
		dx = mouse_pos.x - pos.left - size.width/2,
		dy = (mouse_pos.y - pos.top - size.height/2)*-1,
		eve_tan = dy/dx,
		tan = size.height/size.width;
	if(dx != 0){
		if(eve_tan > tan*-1 && eve_tan < tan && dx < 0){
			return "left";
		}else if(eve_tan > tan*-1 && eve_tan < tan && dx > 0){
			return "right";
		}else if((eve_tan > tan || eve_tan < tan*-1) && dy > 0){
			return "top";
		}else if((eve_tan > tan || eve_tan < tan*-1) && dy <= 0){
			return "bottom";
		}
	}else if(dy > 0){
		return "top";
	}else {
		return "bottom";
	}
};