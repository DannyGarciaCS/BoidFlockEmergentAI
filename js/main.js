const boids = [];

// Sets up initial program status
function setup() {

    // Creates a canvas sized to the screen
    createCanvas(window.innerWidth, window.innerHeight);

    // Initial boid population
    boids.push(new Boid(true));
    for(let i=0; i<400; i++){ boids.push(new Boid()); }
    
}

// Main program loop
function draw() {

    // Canvas background
    background(60);

    // Updates and draws all boids
    for (let boid of boids) {
        boid.update(boids);
        boid.display();
    }
}

// Expand menu on hover
function expand() {
    let i = select('#menu');
	i.style('bottom', '0');
}

// Contract menu when not hovering
function contract() {
    let i = select('#menu');
	i.style('bottom', '-60px' );
}

// Handles sight distance slider
function sightDistanceSlider(value) {
    for(let boid of boids) {
        boid.sightDistance = value;
    }
}

// Handles sight degrees slider
function sightDegreesSlider(value) {
    for(let boid of boids) {
        boid.sightDegrees = value;
    }
}

// Handles alignment slider
function alignmentSlider(value) {
    for(let boid of boids) {
        boid.alignmentForce = value;
        boid.updateForces();
    }
}

// Handles cohesion slider
function cohesionSlider(value) {
    for(let boid of boids) {
        boid.cohesionForce = value;
        boid.updateForces();
    }
}

// Handles separation slider
function separationSlider(value) {
    for(let boid of boids) {
        boid.separationForce = value;
        boid.updateForces();
    }
}
