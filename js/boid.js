
class Boid {

    // Object constructor
    constructor(primary, accurate=false, alignmentForce=3.2, cohesionForce=3.2,
    separationForce=3.1, speed=0.006, torque=0.2, sightDistance=0.2) {

        this.primary = primary;
        this.secondary = false;
        this.thrustX = [0, 0];
        this.thrustY = [0, 0];

        // Slider variables
        this.alignmentForce = alignmentForce;
        this.cohesionForce = cohesionForce;
        this.separationForce = separationForce;
        this.forces = [this.alignmentForce, this.cohesionForce, this.separationForce];
        this.speed = window.innerHeight * speed;
        this.torque = torque;
        this.sightDistance = window.innerHeight * sightDistance;

        // Checkbox variables
        this.debug = primary;
        this.manual = false;
        this.displaySightDistance = true;
        this.accurate = accurate;
        this.displaySightLine = true;
        this.highlight = true;

        // Position information
        this.position = createVector(random(screen.width), random(screen.height));
        this.velocity = p5.Vector.random2D();
        this.acceleration = createVector();
        this.rotation = 0;
    }

    // Handles movement key press
    pressed(key) {
        if(key=="D") { this.thrustX[1] = 1; }
        if(key=="A") { this.thrustX[0] = -1; }
        if(key=="W") { this.thrustY[1] = -1; }
        if(key=="S") { this.thrustY[0] = 1; }
    }

    // Handles movement key release
    released(key) {
        if(key=="D") { this.thrustX[1] = 0; }
        if(key=="A") { this.thrustX[0] = 0; }
        if(key=="W") { this.thrustY[1] = 0; }
        if(key=="S") { this.thrustY[0] = 0; }
    }

    // Updates forces based on components
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

        if(!this.manual) {

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

                        // Highlights visible boids if current is primary and limits comparisons for performance
                        if(this.primary && this.highlight) { boid.secondary = true; }
                        if(!this.accurate && count > 8) { break; }
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

        } else {
            for(let boid of boids) {

                // We are not comparing to self
                if(boid != this) {

                    // Determines distance between boids
                    let distance = dist(this.position.x, this.position.y, boid.position.x, boid.position.y);

                    // Distance is within sightDistance
                    if(distance <= this.sightDistance) {
                        count += 1;

                        // Highlights visible boids if current is primary and limits comparisons for performance
                        if(this.primary && this.highlight) { boid.secondary = true; }
                        if(!this.accurate && count > 8) { break; }
                    }
                }
            }

            // Forces generated by key presses (manual)
            this.acceleration.x = this.thrustX[0] + this.thrustX[1];
            this.acceleration.y = this.thrustY[0] + this.thrustY[1];
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

        // Boid is being controlled manually
        if(this.manual) {

            // Limits boid speed
            if(this.velocity.x > this.speed) { this.velocity.x = this.speed; }
            if(this.velocity.x < -this.speed) { this.velocity.x = -this.speed; }
            if(this.velocity.y > this.speed) { this.velocity.y = this.speed; }
            if(this.velocity.y < -this.speed) { this.velocity.y = -this.speed; }

            // Breaks on X axis
            if(this.velocity.x != 0 && this.thrustX[0] + this.thrustX[1] == 0) {
                if(this.velocity.x > 0) {
                    this.velocity.x = Math.max(this.velocity.x - 0.5, 0);
                } else if(this.velocity.x < 0) {
                    this.velocity.x = Math.min(this.velocity.x + 0.5, 0);
                }
            }

            // Breaks on Y axis
            if(this.velocity.y != 0 && this.thrustY[0] + this.thrustY[1] == 0) {
                if(this.velocity.y > 0) {
                    this.velocity.y = Math.max(this.velocity.y - 0.5, 0);
                } else if(this.velocity.y < -0.1) {
                    this.velocity.y = Math.min(this.velocity.y + 0.5, 0);
                }
            }
        }

        this.velocity.add(this.acceleration);

    }

    // Displays current status of boid
    display(boids, drawBackground=false) {

        if(this.primary && this.debug) {

            // Draws background if enabled
            if(drawBackground) {
                strokeWeight(window.innerHeight / 300);
                stroke('rgba(0, 100, 255, 0.5)')
                fill('rgba(0, 100, 255, 0.05)')

                // Draws vision
                circle(this.position.x, this.position.y, this.sightDistance);
            }

            // Main boid visuals
            strokeWeight(window.innerHeight / 400);
            stroke(210, 25, 50);
            fill('rgb(210, 25, 50)')
        }
        
        else if(this.secondary && boids[0].debug) {
            if(this.displaySightDistance) {

                let distance = dist(this.position.x, this.position.y, boids[0].position.x, boids[0].position.y);

                if(distance <= this.sightDistance) {
                    strokeWeight(window.innerHeight / 300);
                    stroke('rgba(0, 100, 255, 0.5)')
                    fill('rgba(0, 100, 255, 0.05)')

                    // Draws distance
                    line(boids[0].position.x, boids[0].position.y, this.position.x, this.position.y)
                }
            }

            // Secondary boid visuals
            strokeWeight(window.innerHeight / 800);
            stroke(180, 25, 50);
            fill('rgb(150, 50, 210)')
        }

        else {

            // Generic boid visuals
            strokeWeight(window.innerHeight / 800);
            stroke(255);
            fill('rgba(255, 255, 255, 0.2)')
        }

        if(!drawBackground) {

            // Boid size
            let height = window.innerHeight / 125;
            let width = window.innerHeight / 200;

            // Boid position
            let p1 = this.axisRotation(this.position.x, this.position.y, this.position.x, this.position.y - height, this.rotation);
            let p2 = this.axisRotation(this.position.x, this.position.y, this.position.x + width, this.position.y + height, this.rotation);
            let p3 = this.axisRotation(this.position.x, this.position.y, this.position.x - width, this.position.y + height, this.rotation);

            // Draws boid
            triangle(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);

            // Draws sight line if enabled
            if(this.displaySightLine) {
                let p4 = this.axisRotation(this.position.x, this.position.y, this.position.x, this.position.y - height * 3.5, this.rotation);
                line(p1.x, p1.y, p4.x, p4.y);
            }
        }
    }

    // Rotates a point across a given axis
    axisRotation(axisX, axisY, x, y, angle) {
        const radians = this.degToRad(angle)
        let cos = Math.cos(radians);
        let sin = Math.sin(radians);
        let finalX = (cos * (x - axisX)) + (sin * (y - axisY)) + axisX;
        let finalY = (cos * (y - axisY)) - (sin * (x - axisX)) + axisY;
        return createVector(finalX, finalY);
    }

    // Conversion functions
    degToRad(angle) { return (PI / 180) * angle }
    radToDeg(angle) { return angle * (180 / Math.PI) }
}
