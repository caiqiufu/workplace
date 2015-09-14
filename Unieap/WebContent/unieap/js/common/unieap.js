/**
 * @modify zhang_rui@neusoft.com
 */
if (typeof UNIEAP == "undefined" || !UNIEAP) {
	var UNIEAP = {};
}
UNIEAP.namespace = function() {
	var a = arguments, o = null, i, j, d;
	for (i = 0; i < a.length; i = i + 1) {
		d = a[i].split(".");
		o = UNIEAP;

		for (j = (d[0] == "UNIEAP") ? 1 : 0; j < d.length; j = j + 1) {
			o[d[j]] = o[d[j]] || {};
			o = o[d[j]];
		}
	}
	return o;
};

