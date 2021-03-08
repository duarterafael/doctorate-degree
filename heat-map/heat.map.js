window.requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame ||
                                  window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
   function get(id) {
       return document.getElementById(id);
   }
   var heat = simpleheat('canvas').data(data).max(18),
       frame;
   function draw() {
       console.time('draw');
       heat.draw();
       console.timeEnd('draw');
       frame = null;
   }
   draw();
   get('canvas').onmousemove = function (e) {
       var newData =  [e.layerX, e.layerY, 1];
       data.push(newData);
       heat.add(newData);
       frame = frame || window.requestAnimationFrame(draw);
   };
   var radius = get('radius'),
       blur = get('blur'),
       changeType = 'oninput' in radius ? 'oninput' : 'onchange';
   radius[changeType] = blur[changeType] = function (e) {
       heat.radius(+radius.value, +blur.value);
       frame = frame || window.requestAnimationFrame(draw);
   };