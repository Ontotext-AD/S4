/**
 AnnoMarket Web Page Annotation Browser Plugin
 Copyright (c) 2013, 2014, Ontotext AD
 
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

var stringHexNumber = function (inputString) {
    var stringHexNumber = (
        parseInt(
            parseInt(inputString, 36)
                .toExponential()
                .slice(2, -5)
            , 10) & 0xFFFFFF
        ).toString(16).toUpperCase();

    return '#' + ('000000' + stringHexNumber).slice(-6);
};

var createHighlightedArea = function (data) {
    var colorLegend = {};

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

    var inputObj = document.getElementById("resultPlace");
    inputObj.innerHTML = "";
    inputObj.textContent = data['text'];

    var ownProperties = Object.getOwnPropertyNames(data['entities']);

    (function handleEntities(index, entityIndex) {
        if (typeof ownProperties[index] == 'undefined') {
            return;
        }

        var currentObject = data['entities'][ownProperties[index]][entityIndex];

        var tooltipObject = JSON.parse(JSON.stringify(currentObject));

        /**
         * We don't need indices
         * and type when costructing
         * tooltips.
         */
        delete tooltipObject['indices'];

        var currentColor = stringHexNumber(currentObject['type']);
        colorLegend[currentObject['type']] = currentColor;

        selectAndHighlightRange(inputObj,
            currentObject['indices'][0],
            currentObject['indices'][1],
            currentColor,
            tooltipObject);

        var nextIndex = index + 1;
        var nextEntityIndex = entityIndex + 1;

        if (typeof data['entities'][ownProperties[index]][nextEntityIndex] != 'undefined') {
            setTimeout(function () {
                handleEntities(index, nextEntityIndex);
            }, 0);
        } else if (typeof data['entities'][ownProperties[index]][nextEntityIndex] == 'undefined' &&
            typeof data['entities'][ownProperties[index]][entityIndex] != 'undefined' &&
            typeof ownProperties[nextIndex] != 'undefined' &&
            typeof data['entities'][ownProperties[nextIndex]][0] != 'undefined'
            ) {
            setTimeout(function () {
                handleEntities(nextIndex, 0);
            }, 0);
        } else {
            self.postMessage(colorLegend);
        }

    })(0, 0);
};

self.port.on('annoMarketMessage', function (data) {
    createHighlightedArea(data);
});