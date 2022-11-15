class Boid {

    // Object constructor
    constructor(canvasX, canvasY) {

        this.canvasX = canvasX;
        this.canvasY = canvasY;

        this.speed = 5;
        this.torque = 0.2;
        this.sightDistance = 100;
        this.sightDegrees = 300;

        this.position = createVector(random(screen.width), random(screen.height));
        this.velocity = p5.Vector.random2D();
        this.velocity.setMag(random(2, 4));
        this.acceleration = createVector();
    }

    // Warps boid across the screen if out of bounds
    warp() {

        // Boid has left the x-axis
        if(this.position.x > canvasX) { this.position.x = 0; }
        else if(this.position.x < 0) { this.position.x = canvasX; }

        // Boid has left the y-axis
        if(this.position.y > canvasY) { this.position.y = 0; }
        else if(this.position.y < 0) { this.position.y = canvasY; }
    }

    applyRules(boids) {

        // Initializes computation variables (alignment, cohesion, separation)
        let directions = [createVector(), createVector(), createVector()];
        let count = 0;

        for(let boid of boids) {

            // We are not comparing to self
            if(boid != this) {

                // Determines distance between boids
                let distance = dist(this.position.x, this.position.y, boid.position.x, boid.position.y); // Need formula for this to use warp

                // Distance is within sightDistance
                if(distance <= this.sightDistance) {
                    directions[0].add(boid.velocity);

                    directions[1].add(boid.position); // Take warping into consideration
                    count += 1;
                }
            }
        }


        if(count > 0) {

            // Averages counts
            for(let direction of directions) {
                direction.div(count);
            }

            // Rule specific modifications
            directions[1].sub(this.position);

            // Generic modifications
            for(let direction of directions) {
                direction.setMag(this.speed);
                direction.sub(this.velocity);
                direction.limit(this.torque);
                this.acceleration.add(direction);
            }
        }

    }

    // Updates status of boid
    update(boids) {

        // Warps boid across the screen
        this.warp();

        // Reset acceleration and recalculate based on Boid rules
        this.acceleration.mult(0)
        this.applyRules(boids);

        // Update boid's position and velocity
        this.position.add(this.velocity);
        this.velocity.add(this.acceleration);
    }

    // Displays current status of boid
    display() {
        strokeWeight(8);
        stroke(255);
        point(this.position.x, this.position.y);
    }
}