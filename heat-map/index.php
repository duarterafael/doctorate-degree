
   <!DOCTYPE html>
   <html>
   <head>
       <title>simpleheat demo</title>
       <style>
           body { text-align: center; font: 16px/1.4 "Helvetica Neue", Arial, sans-serif; }
           a { color: #0077ff; }
           .container { width: 1000px; height: 600px; margin: 0 auto; position: relative; border: 1px solid #ccc; }
           .options { position: absolute; top: 0; right: 0; padding: 10px; background: rgba(255,255,255,0.6);
               border-bottom: 1px solid #ccc; border-left: 1px solid #ccc; line-height: 1; }
           .options input { width: 200px; }
           .options label { width: 60px; float: left; text-align: right; margin-right: 10px; color: #555; }
           .ghbtns { position: relative; top: 4px; margin-left: 5px; }
       </style>
   </head>
   <body>
   <p>
       <strong>simpleheat</strong> is a tiny and fast JS heatmap library.
       More on <a href="https://github.com/mourner/simpleheat">mourner / simpleheat</a>
       <iframe class="ghbtns" src="http://ghbtns.com/github-btn.html?user=mourner&amp;repo=simpleheat&amp;type=watch&amp;count=true"
     allowtransparency="true" frameborder="0" scrolling="0" width="90" height="20"></iframe>
   </p>
   <div class="container">
       <div class="options">
           <label>Radius </label><input type="range" id="radius" value="25" min="10" max="50" /><br />
           <label>Blur </label><input type="range" id="blur" value="15" min="10" max="50" />
       </div>
       <canvas id="canvas" width="1000" height="600"></canvas>
   </div>
   
   <script src="simpleheat.js"></script>
   <script src="data.js"></script>
   <script>
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
       heat.add([e.layerX, e.layerY, 1]);
       frame = frame || window.requestAnimationFrame(draw);
   };
   var radius = get('radius'),
       blur = get('blur'),
       changeType = 'oninput' in radius ? 'oninput' : 'onchange';
   radius[changeType] = blur[changeType] = function (e) {
       heat.radius(+radius.value, +blur.value);
       frame = frame || window.requestAnimationFrame(draw);
   };
   </script>
   </body>
   </html>
