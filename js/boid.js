
// Can be controlled with wasd
// Directional line
// Optimizations
// Speed slider
// population slider
// fix scrollbars

class Boid {

    // Object constructor
    constructor(primary) {

        this.primary = primary;

        // Slider variables
        this.alignmentForce = 3;
        this.cohesionForce = 2.9;
        this.separationForce = 3;
        this.forces = [this.alignmentForce, this.cohesionForce, this.separationForce];

        this.speed = window.innerHeight * 0.005;
        this.torque = window.innerHeight * 0.0001;

        this.sightDistance = window.innerHeight * 0.15;
        this.sightDegrees = 300;

        this.position = createVector(random(screen.width), random(screen.height));
        this.velocity = p5.Vector.random2D();
        this.acceleration = createVector();
        this.rotation = 0;
    }

    updateForces() { this.forces = [this.alignmentForce, this.cohesionForce, this.separationForce]; }

    // Warps boids across the screen if out of bounds
    warp() {

        // Boid has left the x-axis
        if(this.position.x > width) { this.position.x = 0; }
        else if(this.position.x < 0) { this.position.x = width; }

        // Boid has left the y-axis
        if(this.position.y > height) { this.position.y = 0; }
        else if(this.position.y < 0) { this.position.y = height; }
    }

    // Applies basic motion rules
    applyRules(boids) {

        // Initializes computation variables (alignment, cohesion, separation)
        let directions = [createVector(), createVector(), createVector()];
        let count = 0;

        for(let boid of boids) {

            // We are not comparing to self
            if(boid != this) {

                // Determines distance between boids
                let distance = dist(this.position.x, this.position.y, boid.position.x, boid.position.y);

                // Distance is within sightDistance
                if(distance <= this.sightDistance) {
                    directions[0].add(boid.velocity);
                    directions[1].add(boid.position);
                    directions[2].add(p5.Vector.sub(this.position, boid.position).div(distance * distance));
                    count += 1;
                }
            }
        }


        // Applies directional changes
        if(count > 0) {

            // Averages directions
            for(let direction of directions) { direction.div(count); }
            directions[1].sub(this.position);

            // Changes acceleration
            for(let direction in directions) {
                directions[direction].setMag(this.speed);
                directions[direction].sub(this.velocity);
                directions[direction].limit(this.torque);
                this.acceleration.add(directions[direction].mult(this.forces[direction]));
            }
        }

    }

    // Updates status of boid
    update(boids) {

        // Warps boids across the screen
        this.warp();

        // Reset acceleration and recalculate based on Boid rules
        this.acceleration.mult(0);
        this.applyRules(boids);

        // Update boid's position and velocity
        this.position.add(this.velocity);
        this.rotation = Math.atan2(-this.velocity.x, -this.velocity.y) / (PI / 180);
        this.velocity.add(this.acceleration);

    }

    // Displays current status of boid
    display(drawBackground=false) {

        if(this.primary) {

            // Draws background if enabled
            if(drawBackground) {
                strokeWeight(3);
                stroke('rgba(0, 100, 255, 0.8)')
                fill('rgba(0, 100, 255, 0.11)')
                circle(this.position.x, this.position.y, this.sightDistance);
            }

            // Main boid visuals
            strokeWeight(2);
            stroke(210, 25, 50);
            fill('rgb(210, 25, 50)')
        }

        else {

            // Generic boid visuals
            strokeWeight(1);
            stroke(255);
            fill('rgba(255, 255, 255, 0.2)')
        }

        if(!drawBackground) {

            // Boid size
            let height = 6.5;
            let width = 5;

            // Boid position
            let p1 = this.axisRotation(this.position.x, this.position.y, this.position.x, this.position.y - height, this.rotation);
            let p2 = this.axisRotation(this.position.x, this.position.y, this.position.x + width, this.position.y + height, this.rotation);
            let p3 = this.axisRotation(this.position.x, this.position.y, this.position.x - width, this.position.y + height, this.rotation);

            // Draws boid
            triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        }
    }

    // Rotates a point across a given axis
    axisRotation(axisX, axisY, x, y, angle) {
        const radians = (PI / 180) * angle
        let cos = Math.cos(radians);
        let sin = Math.sin(radians);
        let finalX = (cos * (x - axisX)) + (sin * (y - axisY)) + axisX;
        let finalY = (cos * (y - axisY)) - (sin * (x - axisX)) + axisY;
        return createVector(finalX, finalY);
    }
}