
var trip = trip || {};

(function (trip) {
    
    /**
     * Creates an HTML alert.
     * @param {string} msg The message to display.
     * @returns {void}
     */
    trip.message = function (msg) {
        alert(msg);
    };
    
})(trip);