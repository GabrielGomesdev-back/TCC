(function(b){var a=function(i,p){var j=i,g=b(j).attr("id"),d="-limitTextarea-",k={shift:{keyCode:16,down:false},ctrl:{keyCode:17,down:false},alt:{keyCode:18,down:false},altgr:{keyCode:225,down:false}},f=["afterInit","afterKeyDown","afterKeyUp","afterMouseDown","afterMouseUp","afterContextMenu","afterCut","afterPaste","afterEmpty","afterNotEmpty","afterWithinLimit","afterReachLimit"],l=["limit","disableContextMenu"],m=[8,9,16,17,19,20,27,35,36,37,38,39,40,45,46,144,145],o=[35,36,67,86,88,89,90,],c=[35,36,37,38,39,40];function n(q,e){var e=(e)?true:false;switch(q){case 16:k.shift.down=e;break;case 17:k.ctrl.down=e;break;case 18:k.alt.down=e;break;case 225:k.altgr.down=e;break;}}var h={init:function(){var e=this;if(!e.initialized){b(j).data("limittextareaLimit",(b(j).data("limittextareaLimit"))?b(j).data("limittextareaLimit"):(p&&p.limit)?p.limit:0);b(j).data("limittextareaCharactercount",(b(j).data("limittextareaCharactercount"))?b(j).data("limittextareaCharactercount"):0);b(j).data("limittextareaIsempty",(b(j).data("limittextareaIsempty"))?b(j).data("limittextareaIsempty"):false);b(j).data("limittextareaLimitreached",(b(j).data("limittextareaLimitreached"))?b(j).data("limittextareaLimitreached"):false);b(j).data("limittextareaDisablecontextmenu",(b(j).data("limittextareaDisablecontextmenu"))?b(j).data("limittextareaDisablecontextmenu"):"false");e.characterCount();e.drawCounter();e.addStyles();e.initialized=true;}e.afterInit();return e;},drawCounter:function(){var r=this,e=document.createElement("div"),q=document.createElement("span");q.id=g+d+"characterCount";e.id=g+d+"container";e.innerHTML=q.outerHTML+" of "+b(j).data("limittextareaLimit")+" characters remaining";b(j).after(e);r.updateCounter();return r;},addStyles:function(){var e=this;if(!b("#"+g+d+"container").hasClass("limitTextarea")){b("#"+g+d+"container").addClass("limitTextarea");}if(!b("#"+g+d+"container").hasClass("container")){b("#"+g+d+"container").addClass("container");}if(!b("#"+g+d+"characterCount").hasClass("limitTextarea")){b("#"+g+d+"characterCount").addClass("limitTextarea");}if(!b("#"+g+d+"characterCount").hasClass("counterContainer")){b("#"+g+d+"characterCount").addClass("counterContainer");}return e;},updateCounter:function(){var e=this,q=parseInt(b(j).data("limittextareaLimit"))-parseInt(b(j).data("limittextareaCharactercount"));b("#"+g+d+"characterCount").html(q);return e;},onKeyDown:function(s){var r=this,u=s.keyCode,q=String.fromCharCode(u),e=b.inArray(parseInt(u),m),w=b.inArray(parseInt(u),o),v=b.inArray(parseInt(u),c);if(b(j).data("limittextareaLimitreached")){if(!(k.ctrl.down&&w>=0)&&!(k.shift.down&&v>=0)&&(e<0)){s.preventDefault();}}window.setTimeout(function(){b(j).trigger("afterKeyDown");},0);return r;},onKeyUp:function(r){var q=this,s=r.keyCode,e=String.fromCharCode(s);window.setTimeout(function(){b(j).trigger("afterKeyUp");},0);return q;},onMouseDown:function(q){var e=this;window.setTimeout(function(){b(j).trigger("afterMouseDown");},0);return e;},onMouseUp:function(q){var e=this;window.setTimeout(function(){b(j).trigger("afterMouseUp");},0);return e;},onContextMenu:function(q){var e=this;if(b(j).data("limittextareaDisablecontextmenu")===true){q.preventDefault();}else{window.setTimeout(function(){b(j).trigger("afterContextMenu");},0);}return e;},onCut:function(q){var e=this;window.setTimeout(function(){b(j).trigger("afterCut");},0);return e;},onPaste:function(q){var e=this;window.setTimeout(function(){e.characterCount();},0);window.setTimeout(function(){b(j).trigger("afterPaste");},0);return e;},onEmpty:function(){var e=this;b(j).data("limittextareaIsempty",true);window.setTimeout(function(){b(j).trigger("afterEmpty");},0);return e;},onNotEmpty:function(){var e=this;b(j).data("limittextareaIsempty",false);window.setTimeout(function(){b(j).trigger("afterNotEmpty");},0);return e;},onWithinLimit:function(q){var e=this;b(j).data("limittextareaLimitreached",false);window.setTimeout(function(){b(j).trigger("afterWithinLimit");},0);return e;},onReachLimit:function(q){var e=this;b(j).data("limittextareaLimitreached",true);b(j).val(b(j).val().substr(0,b(j).data("limittextareaLimit")));if(b(j).data("limittextareaCharactercount")>b(j).data("limittextareaLimit")){b(j).data("limittextareaCharactercount",b(j).data("limittextareaLimit"));}window.setTimeout(function(){b(j).trigger("afterReachLimit");},0);return e;},afterInit:function(){var e=this;return e;},afterKeyDown:function(q){var e=this;return e;},afterKeyUp:function(q){var e=this;return e;},afterMouseDown:function(q){var e=this;return e;},afterMouseUp:function(q){var e=this;return e;},afterContextMenu:function(q){var e=this;return e;},afterCut:function(q){var e=this;return e;},afterPaste:function(q){var e=this;return e;},afterEmpty:function(q){var e=this;return e;},afterNotEmpty:function(q){var e=this;return e;},afterWithinLimit:function(q){var e=this;return e;},afterReachLimit:function(q){var e=this;return e;},characterCount:function(s){var e=this,r=e,q=0;if(s){b(j).data("limittextareaCharactercount",s);}else{b(j).data("limittextareaCharactercount",b(j).val().length);r=b(j).data("limittextareaCharactercount");if(b(j).data("limittextareaCharactercount")==0){b(j).trigger("empty");}else{b(j).trigger("notempty");}if(b(j).data("limittextareaLimit")){if(parseInt(b(j).data("limittextareaCharactercount"))+1>parseInt(b(j).data("limittextareaLimit"))){b(j).trigger("reachlimit");}else{b(j).trigger("withinlimit");}}else{}}if(e.initialized){e.updateCounter();}return r;},limit:function(e){return(e)?b(j).data("limittextareaLimit",e):b(j).data("limittextareaLimit");},disableContextMenu:function(e){return(e)?b(j).data("limittextareaDisablecontextmenu",e):b(j).data("limittextareaDisablecontextmenu");}};if(p){Object.keys(p).each(function(e){var q=p[e];if(b.inArray(e,f)>=0){h[e]=p[e];}else{if(b.inArray(e,l)>=0){h[e](q);}}});}b(j).on("keydown",function(e){n(e.keyCode,true);h.onKeyDown(e).characterCount();});b(j).on("keyup",function(e){n(e.keyCode);h.onKeyUp(e).characterCount();});b(j).on("mouseDown",function(e){h.afterKeyUp(e).characterCount();});b(j).on("mouseup",function(e){h.onMouseUp(e).characterCount();});b(j).on("contextmenu",function(e){h.onContextMenu(e).characterCount();});b(j).on("cut",function(e){h.onCut(e).characterCount();});b(j).on("paste",function(e){h.onPaste(e).characterCount();});b(j).on("empty",function(e){h.onEmpty(e);});b(j).on("notempty",function(e){h.onNotEmpty(e);});b(j).on("withinlimit",function(e){h.onWithinLimit(e);});b(j).on("reachlimit",function(e){h.onReachLimit(e);});b(j).on("afterKeyDown",function(e){h.afterKeyDown(e);});b(j).on("afterKeyUp",function(e){h.afterKeyUp(e);});b(j).on("afterMouseDown",function(e){h.afterKeyUp(e);});b(j).on("afterMouseUp",function(e){h.afterMouseUp(e);});b(j).on("afterContextMenu",function(e){h.afterContextMenu(e);});b(j).on("afterCut",function(e){h.afterCut(e);});b(j).on("afterPaste",function(e){h.afterPaste(e);});b(j).on("afterEmpty",function(e){h.afterEmpty(e);});b(j).on("afterNotEmpty",function(e){h.afterNotEmpty(e);});b(j).on("afterWithinLimit",function(e){h.afterWithinLimit(e);});b(j).on("afterReachLimit",function(e){h.afterReachLimit(e);});return h;};b.fn.extend({limitTextarea:function(c){if(!b(this).data("limittextarea")){var d=new a(this,c);b(this).data("limittextarea",d);d.init();return this;}else{return b(this).data("limittextarea");}}});})(jQuery);