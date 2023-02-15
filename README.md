# Boid Flock Emergent AI
<p>
Boid Flock Emergent AI is an implementation of Craig Reynolds' "Flocks, Herds, and Schools: A Distributed Behavioral Model".
Link to that paper: https://www.cs.toronto.edu/~dt/siggraph97-course/cwr87/. It simulates the natural movement of birds, fish, or any other animals with similar behavior.
</p>

<h2>Index</h2>
<ul>
  <li><a href="#Overview">Overview</a></li>
  <li><a href="#HowtoUse">How to Use</a></li>
  <li><a href="#Settings">Settings</a></li>
</ul>

<h2 id="Overview">Overview</h2>
<p>
Boid Flock Emergent AI is the implementation of a 2D flocking algorithm. It simulates the natural movement of birds, fish, or any other animals with similar behavior. This is done by controlling the intensity of 3 different forces: Alignment (which makes boids look toward the direction of neighbors), Cohesion (which makes boids move toward the center of the pack), and Separation (which makes the boids want to avoid one another).<br><br>
My version was written in JavaScript (I also included a Java version) and allows for the control of all 3 variables, along with other useful visualization and control tools. For specifics on this, check the "Settings" section. Boid Flocks Emergent AI is not necessarily meant to work as a standalone project, but as code that can be reused or adapted in future projects that need to simulate this kind of movement. Below is a short video of the program being used:
</p>
<img src="https://user-images.githubusercontent.com/116522220/219087278-e27ea14e-2340-40ff-a49b-4b09f02589d1.gif">

<h2 id="HowtoUse">How to Use</h2>
<p>
To use the program you must simply download the included files and run index.html with any browser. There is a separate older Java version included that can be used by opening it with a Java IDE, do this if you want more speed than what can be achieved using your browser. 
</p>

<h2 id="Settings">Controls</h2>
<p>

The settings list can be accessed by hovering the Settings divider at the bottom of the screen.<br>

Sliders:
<ul>
  <li>Speed: Defines the speed of movement of boids.</li>
  <li>Torque: Torque: Defines the maximum rotation any one boid can exert at any given moment.</li>
  <li>Boids: Defines the number of boids on screen.</li>
  <li>Sight Distance: Defines the maximum distance boids can interact with one another.</li>
  <li>Alignment: Defines the strength of the alignment rule.</li>
  <li>Cohesion: Defines the strength of the cohesion rule.</li>
  <li>Separation: Defines the strength of the separation rule.</li>
</ul>
<br>
Checkboxes:
<ul>
  <li>Debug: Displays all debug visuals, other settings are dependent on this.</li>
  <li>Manual: Stops main boid movement and enables WASD controls.</li>
  <li>Sigh Distance (check): Displays the distance to visible boids.</li>
  <li>Accurate (Slow): Makes every boid consider every other boid in calculations making for a slower but more accurate algorithm.</li>
  <li>Sight Line: Displays a line showing where boids are heading.</li>
  <li>Highlight: Highlights boids being observed by the main boid.</li>
</ul>
<br>
<img src="https://user-images.githubusercontent.com/116522220/219089774-52950719-3755-4cae-adea-041c0a1201e1.png">
</p>
