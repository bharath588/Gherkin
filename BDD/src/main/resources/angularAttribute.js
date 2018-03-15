/**
 * Find elements by angular attribute and textual content.
 *
 * @param {string} attribute The angular attribute to match.
 * @param {string} attribValue The value to match.
 * @param {Element} using The scope of the search.
 *
 * @return {Array.<Element>} An array of matching elements.
 */

var findByAngularAttribute = function(attribute,attribValue,using){
	using = using || document;
	var rows = [];
	var prefixes = ['ng-', 'ng_', 'data-ng-', 'x-ng-'];
	for (var p = 0; p < prefixes.length; ++p) {
		var attr = prefixes[p] + attribute;
		var elements = using.querySelectorAll('[' + attr + ']');
		attr = attr.replace(/\\\\/g, '');
		for (var i = 0; i < elements.length; ++i) {
			if (elements[i].getAttribute(attr).indexOf(attribValue) != -1) {
				rows.push(elements[i]);
			}
		}

	}
	return rows;
};

var using = arguments[0] || document;
var attribute = arguments[1];
var attribValue = arguments[2];
return findByAngularAttribute(attribute,attribValue,using);