const boids = [];

// Sets up initial program status
function setup() {

    // Creates a canvas sized to the screen
    createCanvas(window.innerWidth, window.innerHeight);

    // Initial boid population
    boids.push(new Boid(true));
    for(let i=0; i<200; i++){ boids.push(new Boid()); }

}

// Main program loop
function draw() {

    // Canvas background
    background(60, 60, 60, 160);

    for(let boid of boids) {
        if(boid.secondary) { boid.secondary = false; }
    }

    // Updates and draws all boids (Main boid background in bottom layer, main boid in top layer)
    boids[0].update(boids);
    boids[0].display(true);
    for (let boid=1; boid<boids.length; boid++) {
        boids[boid].update(boids);
        boids[boid].display();
    } boids[0].display();
}

// Expand menu on hover
function expand() {
    let i = select('#menu');
	i.style('bottom', '0');
}

// Contract menu when not hovering
function contract() {
    let i = select('#menu');
	i.style('bottom', '-' + window.innerHeight * 0.1 + 'px' );
}

// Handles movement slider
function speedSlider(value) {
    for(let boid of boids) {
        boid.speed =  window.innerHeight * value / 1000;
    }
}

// Handles torque slider
function torqueSlider(value) {
    for(let boid of boids) {
        boid.torque =  window.innerHeight * value / 100000;
    }
}

// Handles number of boids slider (Naive implementation avoids conflicts)
function boidsSlider(value) {

    if(boids.length < value) {
        while(boids.length < value) {
            boids.push(new Boid());
        }
    } else if(boids.length > value) {
        while(boids.length > value) {
            boids.splice(boids.length - 1, 1);
        }
    }
}

// Handles sight distance slider
function sightDistanceSlider(value) {
    for(let boid of boids) {
        boid.sightDistance =  window.innerHeight * value / 1000;
    }
}

// Handles alignment slider
function alignmentSlider(value) {
    for(let boid of boids) {
        boid.alignmentForce =  Math.round(value * 1000) / 1000;
        boid.updateForces();
    }
}

// Handles cohesion slider
function cohesionSlider(value) {
    for(let boid of boids) {
        boid.cohesionForce =  Math.round(value * 1000) / 1000;
        boid.updateForces();
    }
}

// Handles separation slider
function separationSlider(value) {
    for(let boid of boids) {
        boid.separationForce = Math.round(value * 1000) / 1000;
        boid.updateForces();
    }
}

// Handles sight line checkbox
function sightLineBoxClick(box, checked) {
    if(checked) {
        for(let boid of boids) { boid.displaySightLine = true; }
        box.checked = false;
    } else if(!checked) {
        for(let boid of boids) { boid.displaySightLine = false; }
        box.checked = true;
    }
}

// Handles highlight checkbox
function highlightBoxClick(box, checked) {
    if(checked) {
        boids[0].highlight = true;
        box.checked = false;
    } else if(!checked) {
        boids[0].highlight = false;
        box.checked = true;
    }
}

// Handles manual controls checkbox
function manualBoxClick(box, checked) {
    if(checked) {
        boids[0].manual = true;
        boids[0].velocity.x *= 0.00001;
        boids[0].velocity.y *= 0.00001;
        box.checked = false;
    } else if(!checked) {
        boids[0].manual = false;
        box.checked = true;
    }
}

document.addEventListener('keydown', (event) => {

    if(event.code == "KeyD" && boids[0].manual) { boids[0].pressed("D") }
    if(event.code == "KeyW" && boids[0].manual) { boids[0].pressed("W") }
    if(event.code == "KeyA" && boids[0].manual) { boids[0].pressed("A") }
    if(event.code == "KeyS" && boids[0].manual) { boids[0].pressed("S") }

});

document.addEventListener('keyup', (event) => {

    if(event.code == "KeyD" && boids[0].manual) { boids[0].released("D") }
    if(event.code == "KeyW" && boids[0].manual) { boids[0].released("W") }
    if(event.code == "KeyA" && boids[0].manual) { boids[0].released("A") }
    if(event.code == "KeyS" && boids[0].manual) { boids[0].released("S") }

});

// Handles manual controls checkbox
function accurateBoxClick(box, checked) {
    if(checked) {
        for(let boid of boids) { boid.accurate = true; }
        box.checked = false;
    } else if(!checked) {
        for(let boid of boids) { boid.accurate = false; }
        box.checked = true;
    }
}
