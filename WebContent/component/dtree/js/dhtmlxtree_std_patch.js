/**
 * 拖动模式补丁
 */
dhtmlXTreeObject.prototype.setDragBehavior = function(mode, select) {
    this._sADnD = (!convertStringToBoolean(select));
    switch (mode) {
        case "child" :
            this.dadmode = 0;
            break;
        case "sibling" :
            this.dadmode = 1;
            break;
        case "complex" :
            this.dadmode = 2;
            break
    }
};
dhtmlXTreeObject.prototype._getNextNode = function(item, mode) {
    if ((!mode) && (item.childsCount))
        return item.childNodes[0];
    if (item == this.htmlNode)
        return -1;
    if ((item.tr) && (item.tr.nextSibling) && (item.tr.nextSibling.nodem))
        return item.tr.nextSibling.nodem;
    return this._getNextNode(item.parentObject, true)
};
dhtmlXTreeObject.prototype._lastChild = function(item) {
    if (item.childsCount)
        return this._lastChild(item.childNodes[item.childsCount - 1]);
    else
        return item
};
dhtmlXTreeObject.prototype._getPrevNode = function(node, mode) {
    if ((node.tr) && (node.tr.previousSibling) && (node.tr.previousSibling.nodem))
        return this._lastChild(node.tr.previousSibling.nodem);
    if (node.parentObject)
        return node.parentObject;
    else
        return -1
};
dhtmlXTreeObject.prototype._moveNode = function(itemObject, targetObject) {
    var mode = this.dadmodec;
    if (mode == 1) {
        var z = targetObject;
        if (this.dadmodefix < 0) {
            while (true) {
                z = this._getPrevNode(z);
                if ((z == -1)) {
                    z = this.htmlNode;
                    break
                };
                if ((z.tr == 0) || (z.tr.style.display == "") || (!z.parentObject))
                    break
            };
            var abe = z;
            var HU = targetObject
        } else {
            while (true) {
                z = this._getNextNode(z);
                if ((z == -1)) {
                    z = this.htmlNode;
                    break
                };
                if ((z.tr.style.display == "") || (!z.parentObject))
                    break
            };
            var HU = z;
            var abe = targetObject
        };
        if (this._getNodeLevel(abe, 0) > this._getNodeLevel(HU, 0)) {
            if (!this.dropLower)
                return this._moveNodeTo(itemObject, abe.parentObject);
            else if (HU.id != this.rootId)
                return this._moveNodeTo(itemObject, HU.parentObject, HU);
            else
                return this._moveNodeTo(itemObject, this.htmlNode, null)
        } else {
            return this._moveNodeTo(itemObject, HU.parentObject, HU)
        }
    } else
        return this._moveNodeTo(itemObject, targetObject)
};
dhtmlXTreeObject.prototype._clearMove = function() {
    if (this._lastMark) {
        this._lastMark.className = this._lastMark.className.replace(/dragAndDropRow/g, "");
        this._lastMark = null
    };
    this.selectionBar.style.display = "none";
    this.allTree.className = this.allTree.className.replace(" selectionBox", "")
};
dhtmlXTreeObject.prototype._setMove = function(htmlNode, x, y) {
    if (htmlNode.parentObject.span) {
        var a1 = getAbsoluteTop(htmlNode);
        var a2 = getAbsoluteTop(this.allTree);
        this.dadmodec = this.dadmode;
        this.dadmodefix = 0;
        if (this.dadmode == 2) {
            var z = y - a1 + this.allTree.scrollTop + (document.body.scrollTop || document.documentElement.scrollTop) - 2 - htmlNode.offsetHeight / 2;
            if ((Math.abs(z) - htmlNode.offsetHeight / 6) > 0) {
                this.dadmodec = 1;
                if (z < 0)
                    this.dadmodefix = 0 - htmlNode.offsetHeight
            } else
                this.dadmodec = 0
        };
        if (this.dadmodec == 0) {
            var zN = htmlNode.parentObject.span;
            zN.className += " dragAndDropRow";
            this._lastMark = zN
        } else {
            this._clearMove();
            this.selectionBar.style.top = (a1 - a2 + ((parseInt(htmlNode.parentObject.span.parentNode.previousSibling.childNodes[0].style.height) || 18) - 1) + this.dadmodefix) + "px";
            this.selectionBar.style.left = "5px";
            if (this.allTree.offsetWidth > 20)
                this.selectionBar.style.width = (this.allTree.offsetWidth - (_isFF ? 30 : 25)) + "px";
            this.selectionBar.style.display = ""
        };
        this._autoScroll(null, a1, a2)
    }
};