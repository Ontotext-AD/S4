/*
 S4 Web Page Annotation Browser Plugin
 Copyright  2013, 2014, Ontotext AD

 This file is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License along
 with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

(function (module) {
    var createHighlightedArea = function (stringHexNumber) {
        return function (inputDomObject, data) {
            var colorLegend = [];

            function getTextNodesIn(node) {
                var textNodes = [];
                if (node.nodeType == 3) {
                    textNodes.push(node);
                } else {
                    var children = node.childNodes;
                    for (var i = 0, len = children.length; i < len; ++i) {
                        textNodes.push.apply(textNodes, getTextNodesIn(children[i]));
                    }
                }
                return textNodes;
            }

            function setSelectionRange(el, start, end) {
                if (document.createRange && window.getSelection) {
                    var range = document.createRange();
                    range.selectNodeContents(el);
                    var textNodes = getTextNodesIn(el);
                    var foundStart = false;
                    var charCount = 0, endCharCount;

                    for (var i = 0, textNode; textNode = textNodes[i++];) {
                        endCharCount = charCount + textNode.length;
                        if (!foundStart && start >= charCount && (start < endCharCount || (start == endCharCount && i < textNodes.length))) {
                            range.setStart(textNode, start - charCount);
                            foundStart = true;
                        }
                        if (foundStart && end <= endCharCount) {
                            range.setEnd(textNode, end - charCount);
                            break;
                        }
                        charCount = endCharCount;
                    }

                    var sel = window.getSelection();
                    sel.removeAllRanges();
                    sel.addRange(range);
                } else if (document.selection && document.body.createTextRange) {
                    var textRange = document.body.createTextRange();
                    textRange.moveToElementText(el);
                    textRange.collapse(true);
                    textRange.moveEnd("character", end);
                    textRange.moveStart("character", start);
                    textRange.select();
                }
            }

            function invertColor(hexTripletColor) {
                var color = hexTripletColor;
                color = color.substring(1); // remove #
                color = parseInt(color, 16); // convert to integer
                color = 0xFFFFFF ^ color; // invert three bytes
                color = color.toString(16); // convert to hex
                color = ("000000" + color).slice(-6); // pad with leading zeros
                color = "#" + color; // prepend #
                return color;
            }

            function makeEditableAndHighlight(colour, tooltipObj) {
                sel = window.getSelection();
                if (sel.rangeCount && sel.getRangeAt) {
                    range = sel.getRangeAt(0);
                }
                document.designMode = "on";
                if (range) {
                    sel.removeAllRanges();
                    sel.addRange(range);
                }
                // Use HiliteColor since some browsers apply BackColor to the
                // whole block
                if (!document.execCommand("HiliteColor", false, colour)) {
                    document.execCommand("BackColor", false, colour);
                }

                sel = window.getSelection();
                if (sel.rangeCount && sel.getRangeAt) {
                    var tooltip = '';

                    for (var i in tooltipObj) {
                        if (tooltipObj.hasOwnProperty(i)) {
                            tooltip = tooltip + i + ":" + tooltipObj[i] + "\n";
                        }
                    }

                    sel.focusNode.parentNode.title = tooltip;
                    sel.focusNode.parentNode.style.color = invertColor(colour);
                }

                document.designMode = "off";
                if (document.selection) {
                    document.selection.empty();
                } else if (window.getSelection) {
                    window.getSelection().removeAllRanges();
                }
            }

            function highlight(colour, toolTipObj) {
                var range, sel;
                if (window.getSelection) {
                    // IE9 and non-IE
                    try {
                        if (!document.execCommand("BackColor", false, colour)) {
                            makeEditableAndHighlight(colour, toolTipObj);
                        }
                    } catch (ex) {
                        makeEditableAndHighlight(colour, toolTipObj);
                    }
                } else if (document.selection && document.selection.createRange) {
                    // IE <= 8 case
                    range = document.selection.createRange();
                    range.execCommand("BackColor", false, colour);
                }
            }

            function selectAndHighlightRange(targetObject, start, end, color, toolTipObj) {
                setSelectionRange(targetObject, start, end);
                highlight(color, toolTipObj);
            }

                var inputObj = inputDomObject.find(".textContainer");
                inputObj.html(data.text);
                var stringToHexNumberHandler = stringHexNumber();
                				
                for (var i in data['entities']) {
                    if (data['entities'].hasOwnProperty(i)) {
						colorLegend.push([i,data['entities'][i]]);
                    }
                }
                
                colorLegend.sort(function(a, b) {
                    return b[1].length - a[1].length;
                });

                for (var i = 0; i < colorLegend.length; i++) {
                    var currentColor = stringToHexNumberHandler(colorLegend[i][0]);

                    for (var j = 0; j < colorLegend[i][1].length; j++) {
                        var currentObject = colorLegend[i][1][j];
                        var tooltipObject = angular.copy(currentObject);
                        if(j == 0) {
                            currentObject.color = currentColor;
                            currentObject.invertColor = invertColor(currentColor);
                        }

                        /**
                         * We don't need indices
                         * and type when constructing
                         *` tooltips.
                         */
                        delete tooltipObject['indices'];

                        if(typeof colorLegend[i][1][0]['s4HideItem'] == 'undefined') {
                            selectAndHighlightRange(inputObj.get(0),
                                currentObject['indices'][0],
                                currentObject['indices'][1],
                                currentColor,
                                tooltipObject);
                        }
                    }
                }
                
                
            return colorLegend;
        }
    };
    module.factory("createHighlightedArea", ['stringHexNumber', createHighlightedArea]);
}(angular.module("s4App")));
