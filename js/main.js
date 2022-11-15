const boids = [];

const canvasX = window.innerWidth;
const canvasY = window.innerHeight;


function setup() {

    createCanvas(canvasX, canvasY);

    for(let i=0; i<100; i++){ boids.push(new Boid(canvasX, canvasY)); }
    
}

function draw() {
    background(60);

    for (let boid of boids) {
        boid.update(boids);
        boid.display();
    }
}



function expand() {
    let i = select('#menu');
	i.style('bottom', '0');
}

function contract() {
    let i = select('#menu');
	i.style('bottom', '-60px' );
}
