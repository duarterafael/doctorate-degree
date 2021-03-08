
  
   <!DOCTYPE html>
   <html>
   <?php include 'data.php';?>
   <head>
       <title>simpleheat demo</title>
       <style>
           body { text-align: center; font: 16px/1.4 "Helvetica Neue", Arial, sans-serif; }
           a { color: #0077ff; }
           .model {background-image: url("model-sample.png"); width: 100%; height: 100%}
           .container { width: 1000px; height: 600px; margin: 0 auto; position: relative; border: 1px solid #ccc; }
           .options { position: absolute; top: 0; right: 0; padding: 10px; background: rgba(255,255,255,0.6);
               border-bottom: 1px solid #ccc; border-left: 1px solid #ccc; line-height: 1; }
           .options input { width: 200px; }
           .options label { width: 60px; float: left; text-align: right; margin-right: 10px; color: #555; }
           .ghbtns { position: relative; top: 4px; margin-left: 5px; }
       </style>
   </head>
   <body>
   <div class="model">
    <div class="container">
        <div class="options">
            <label>Radius </label><input type="range" id="radius" value="25" min="10" max="50" /><br />
            <label>Blur </label><input type="range" id="blur" value="15" min="10" max="50" />
        </div>
        <canvas id="canvas" width="1260" height="1820"></canvas>
    </div>
   </div>
  
   <script src="simpleheat.js"></script>
   <script src="heat.map.js"></script>
   </body>
   </html>
