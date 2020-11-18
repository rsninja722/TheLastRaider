package main.entities.moveable.combat;

import java.util.ArrayList;

import engine.Input;
import engine.KeyCodes;
import engine.Utils;
import engine.audio.Sounds;
import engine.drawing.Camera;
import engine.drawing.Draw;
import engine.physics.Physics;
import engine.physics.Point;
import main.Constants;
import main.Main;
import main.Options;
import main.entities.Damage;
import main.entities.Entity;
import main.entities.moveable.Moveable;
import main.entities.projectiles.Arrow;

import java.awt.Color;

public class Player extends Moveable {

    enum Attacks {
        JAB, SWIPE, SPIN;
    }

    enum Weapons {
        SWORD,
        BOW
    }

    // angle of body and attacks
    double angle = 0;
    // angle of feet
    double walkAngle = 0;
    double walkCycle = 0;

    int dashCooldown = 0;

    boolean recordingAttack = false;
    Point lastMousePos;
    // list of angles 
    ArrayList<Double> angles;

    int attackTime;
    Attacks attack;

    Weapons weapon = Weapons.SWORD;

    boolean blocking = false;
    int blockTime;

    // used for debug, turns off collision
    boolean noClip = false;

    int lives = 3;

    public Player(double x, double y, int w, int h) {
        super(x, y, w, h);
        this.friction = Constants.PLAYER_FRICTION;
        setHP(20);
    }

    @Override
    public boolean update() {
        // death
        if (hp <= 0) {
            if (--lives == 0) {
                Main.state = Main.State.GAMEOVER;
                return false;
            }
            setHP(20);
            Main.state = Main.State.TRANSITION;
        }

        // options
        if (Input.keyClick(KeyCodes.ESCAPE)) {
            Options.enterOptions();
        }

        // no clip
        if (Utils.debugMode) {
            if (Input.keyClick(KeyCodes.V)) {
                noClip = !noClip;
            }
        }

        // block
        if(Input.keyDown(KeyCodes.SHIFT) && attackTime == 0) {
            if(!blocking) {
                blocking = true;
                blockTime = 1;
            }
            if(--blockTime <= 0) {
                blockTime = 10;
                Damage.damages.add(new Damage(rect.x + Math.cos(angle-0.3f)*7.0, rect.y + Math.sin(angle-0.3f)*7.0, 8, 8, 0, false, true));
                Damage.damages.add(new Damage(rect.x + Math.cos(angle+0.3f)*7.0, rect.y + Math.sin(angle+0.3f)*7.0, 8, 8, 0, false, true));
            }
        } else {
            blocking = false;
        }

        // switch weapon
        if(Input.keyClick(KeyCodes.NUM1)) {
            if(weapon != Weapons.SWORD) {
                weapon = Weapons.SWORD;
            }
        }
        if(Input.keyClick(KeyCodes.NUM2)) {
            if(weapon != Weapons.BOW) {
                weapon = Weapons.BOW;
            }
        }

        // find speed
        double moveX = 0;
        double moveY = 0;
        // move if not attacking
        if (attackTime == 0) {
            if (Input.keyDown(KeyCodes.W)) {
                moveY = -Constants.PLAYER_SPEED;
            }
            if (Input.keyDown(KeyCodes.A)) {
                moveX = -Constants.PLAYER_SPEED;
            }
            if (Input.keyDown(KeyCodes.S)) {
                moveY = Constants.PLAYER_SPEED;
            }
            if (Input.keyDown(KeyCodes.D)) {
                moveX = Constants.PLAYER_SPEED;
            }

            // make diagonal same speed as sideways
            if (moveX != 0 && moveY != 0) {
                moveX /= 1.4;
                moveY /= 1.4;
            }

            if(blocking) {
                moveX /= 3;
                moveY /= 3;
            }

            // handle dash move
            if (dashCooldown > 0) {
                dashCooldown--;
            }

            if (Input.keyClick(KeyCodes.SPACE) && dashCooldown == 0) {
                dashCooldown = Constants.PLAYER_DASH_TIME;
                velX += Math.signum(moveX) * Constants.PLAYER_DASH;
                velY += Math.signum(moveY) * Constants.PLAYER_DASH;
                Sounds.play("dash");
            }

            // move if there isn't already a high velocity
            if (Math.abs(velX) < Constants.PLAYER_SPEED) {
                velX = moveX;
            }
            if (Math.abs(velY) < Constants.PLAYER_SPEED) {
                velY = moveY;
            }
        }

        // move
        if (noClip) {
            rect.x += moveX * 5.0;
            rect.y += moveY * 5.0;
        } else {
            move();
        }

        // walk animation
        if (moveX != 0 || moveY != 0) {
            walkCycle += 0.25;
            if (walkCycle == 3 || walkCycle == 6) {
                Sounds.play("walk" + Utils.rand(0, 2));
            }
            if (walkCycle >= 6) {
                walkCycle = 0;
            }

            // turning
            walkAngle = Utils.turnTo(walkAngle, Utils.pointTo(rect.x, rect.y, rect.x + moveX, rect.y + moveY), 0.2);
        }

        // turn with right click using motion controls
        if (Options.useMotionControl ? Input.mouseDown(2) : !recordingAttack) {
            angle = Utils.turnTo(angle, Utils.pointTo(rect.x, rect.y, Input.mousePos.x, Input.mousePos.y), 0.2);
        }

        // motion control attack handling
        if(!blocking) {
            if(weapon == Weapons.BOW) {
                if (Input.mouseClick(0)) {
                    recordingAttack = true;
                    lastMousePos = new Point(Input.mousePos.x, Input.mousePos.y);
                }

                if(recordingAttack && !Input.mouseDown(0)) {
                    double ang = Utils.pointTo(Input.mousePos, lastMousePos);
                    Entity.entities.add(new Arrow(rect.x + Math.cos(ang) * 10.0, rect.y + Math.sin(ang) * 10.0, ang));
                    recordingAttack = false;
                }

                if(recordingAttack) {
                    angle = Utils.turnTo(angle, Utils.pointTo(Input.mousePos.x, Input.mousePos.y, lastMousePos.x, lastMousePos.y), 0.2);
                }
            } else {
                if (Options.useMotionControl) {
                    // start tracking attack
                    if (Input.mouseClick(0)) {
                        recordingAttack = true;
                        angles = new ArrayList<Double>();
                    }
                    // track points 
                    if (recordingAttack) {
                        // stop tracking
                        if (!Input.mouseDown(0)) {
                            recordingAttack = false;
                            lastMousePos = null;
                        }

                        // first point
                        if (lastMousePos == null) {
                            lastMousePos = new Point(Input.mousePos.x, Input.mousePos.y);
                            // add points
                        } else {
                            double ang = Utils.pointTo(lastMousePos, Input.mousePos);
                            if (ang < 0) {
                                ang = Math.PI * 2 + ang;
                            }
                            angles.add(ang);
                            lastMousePos = new Point(Input.mousePos.x, Input.mousePos.y);

                            // start looking for attacks after enough points
                            if (angles.size() > 9) {
                                analyzeAttack();
                            }
                        }
                    }
                } else {
                    // attack using normal input
                    if (attackTime == 0) {
                        if (Input.mouseClick(0)) {
                            attack = Attacks.SWIPE;
                            attackTime = 20;
                        }
                        if (Input.mouseClick(2)) {
                            attack = Attacks.JAB;
                            attackTime = 10;
                        }
                        if (Input.mouseDown(0) && Input.mouseDown(2)) {
                            attack = Attacks.SPIN;
                            attackTime = 40;
                        }
                    }
                }
            }
        }

        // handle different attacks 
        if (attackTime > 0) {
            attackTime--;

            switch (attack) {
                case JAB:
                    if (attackTime == 8) {
                        Sounds.play("swordjab");
                    }
                    if (attackTime == 7 || attackTime == 3) {
                        double multi = 20 - attackTime;
                        Damage.damages.add(new Damage(rect.x + Math.cos(angle) * multi + Math.cos(angle - Math.PI / 2.0) * -4, rect.y + Math.sin(angle) * multi + Math.sin(angle - Math.PI / 2.0) * -4, 4, 4, 2, false, false));
                    }
                    break;
                case SWIPE:
                    if (attackTime == 13) {
                        Sounds.play("swordswipe");
                    }
                    if (attackTime == 17 || attackTime == 13 || attackTime == 8) {
                        double ang = angle + (13 - attackTime) / 10.0;
                        Damage.damages.add(new Damage(rect.x + Math.cos(ang) * 12.0, rect.y + Math.sin(ang) * 12.0, 4, 4, 2, false, false));
                    }
                    break;
                case SPIN:
                    if (attackTime == 28) {
                        Sounds.play("swordswoosh");
                    }
                    if (attackTime % 2 == 0 && attackTime > 20) {
                        double ang = angle + (40 - attackTime) * 0.31415;
                        Damage.damages.add(new Damage(rect.x + Math.cos(ang) * 14.0, rect.y + Math.sin(ang) * 14.0, 4, 4, 2, false, false));
                    }
                    if (attackTime == 20) {
                        Damage.damages.add(new Damage(rect.x + Math.cos(angle) * 14.0, rect.y + Math.sin(angle) * 14.0, 6, 6, 5, false, false));
                    }
                    break;
            }

        }

        // move camera
        Camera.zoom = 3;
        Camera.centerOn((int) rect.x, (int) rect.y);

        // debug
        Utils.putInDebugMenu("x", rect.x);
        Utils.putInDebugMenu("y", rect.y);
        Utils.putInDebugMenu("vx", velX);
        Utils.putInDebugMenu("vy", velY);
        Utils.putInDebugMenu("angle", angle);
        return false;
    }

    // determines if a set of points can represent a jab, swipe, or spin 
    void analyzeAttack() {
        double size = (double) angles.size();
        double[] samples = new double[5];
        double average = 0;
        double differenceTotal = 0;

        // sample 5 points 
        if (size > 4) {
            for (double i = 0; i < 5; i++) {
                samples[(int) i] = angles.get((int) Math.floor(Utils.mapRange(i, 0, 4, 0, size - 1)));
                average += samples[(int) i];
            }
        }
        // find average angle of all points
        average /= 5;

        // find total change in angle across all samples
        for (int i = 1; i < 5; i++) {
            differenceTotal += angleDiff(samples[i - 1], samples[i]);
        }

        if (attackTime == 0) {
            // spin if the angle changed a lot
            if (differenceTotal > 4.5) {
                attack = Attacks.SPIN;
                attackTime = 20;
                recordingAttack = false;
                lastMousePos = null;
            } else {
                // find difference between player angle and attack angle average
                double diff = angleDiff(angle, average);
                // if the angle is close to perpendicular, swipe
                if (diff > 1.0 && diff < 2.0) {
                    attack = Attacks.SWIPE;
                    attackTime = 15;
                }
                // if angle is close to player angle, jab
                if (diff < 0.75 || diff > 2.25) {
                    attack = Attacks.JAB;
                    attackTime = 10;
                }
            }
        }
    }

    // calculates difference between angles accounting for when angles are locationally close and not numarically 
    double angleDiff(double a1, double a2) {
        double diff = Math.abs(a1 - a2);
        return (diff > Math.PI ? Math.PI * 2.0 - diff : diff);
    }

    @Override
    public void draw() {
        // legs
        int walk = (int) walkCycle;
        walk = walk > 3 ? 6 - walk : walk;
        Draw.image("playerLegs" + walk, (int) rect.x, (int) rect.y, walkAngle, 1.0);

        // attack animations
        if (attackTime > 0) {
            double rotOff;
            switch (attack) {
                case SPIN:
                    rotOff = attackTime > 20 ? Utils.mapRange(attackTime, 0, 20, Math.PI * 2.0, 0) : 0.0;
                    drawOff("playerShield", rect.x, rect.y, angle + rotOff, 5, 3, angle + rotOff);
                    drawOff("playerArmLeft", rect.x, rect.y, angle + rotOff, 5, 1, angle + rotOff);
                    drawOff("playerSword1", rect.x, rect.y, angle + rotOff, -5, 3, angle + rotOff);
                    drawOff("playerArmRight", rect.x, rect.y, angle + rotOff, -5, 1, angle + rotOff);
                    Draw.image("playerBody", (int) rect.x, (int) rect.y, angle + rotOff, 1.0);
                    Draw.image("playerHead", (int) rect.x, (int) rect.y, angle + rotOff, 1.0);
                    break;
                case SWIPE:
                    rotOff = Utils.mapRange(attackTime, 0, 15, Math.PI / 3.0, -Math.PI / 3.0);
                    drawOff("playerShield", rect.x, rect.y, angle + rotOff, 5, 3, angle);
                    drawOff("playerArmLeft", rect.x, rect.y, angle + rotOff, 5, 1, angle);
                    drawOff("playerSword1", rect.x, rect.y, angle + rotOff, -5, 3, angle + rotOff);
                    drawOff("playerArmRight", rect.x, rect.y, angle + rotOff, -5, 1, angle);
                    Draw.image("playerBody", (int) rect.x, (int) rect.y, angle + rotOff / 2.0, 1.0);
                    Draw.image("playerHead", (int) rect.x, (int) rect.y, angle, 1.0);
                    break;
                case JAB:
                    rotOff = Utils.mapRange(attackTime, 0, 10, Math.PI / 4.0, -Math.PI / 4.0);
                    drawOff("playerShield", rect.x, rect.y, angle + rotOff, 5, 3 - attackTime / 3, angle);
                    drawOff("playerArmLeft", rect.x, rect.y, angle + rotOff, 5, 1 - attackTime / 3, angle);
                    drawOff("playerSword1", rect.x, rect.y, angle, -5, (int) (3 + attackTime / 1.5), angle);
                    drawOff("playerArmRight", rect.x, rect.y, angle, -5, (int) (1 + attackTime / 1.5), angle);
                    Draw.image("playerBody", (int) rect.x, (int) rect.y, angle + rotOff / 2.0, 1.0);
                    Draw.image("playerHead", (int) rect.x, (int) rect.y, angle, 1.0);
                    break;
            }  
        } else if(blocking) {
            drawOff("playerShield", rect.x, rect.y, angle, 3, 5, angle);
            drawOff("playerArmLeft", rect.x, rect.y, angle, 3, 3, angle);
            drawOff("playerSword0", rect.x, rect.y, angle, -5, 1, angle);
            drawOff("playerArmRight", rect.x, rect.y, angle, -5, -1, angle);
            Draw.image("playerBody", (int) rect.x, (int) rect.y, angle + ((walkCycle - 3) / 10.0), 1.0);
            Draw.image("playerHead", (int) rect.x, (int) rect.y, angle, 1.0);
        } else if(weapon == Weapons.BOW) {
            if(recordingAttack) {
                double dist = Physics.dist(lastMousePos, Input.mousePos);
                if(dist < 50) {
                    drawOff("playerBow1", rect.x, rect.y, angle-Math.PI/2.0, -3, -3, angle);
                    drawOff("playerArmRight", rect.x, rect.y, angle, -2, -3, angle+Math.PI/5.0);
                } else if(dist < 100) {
                    drawOff("playerBow2", rect.x, rect.y, angle-Math.PI/2.0, -3, -3, angle);
                    drawOff("playerArmRight", rect.x, rect.y, angle, -2, -3, angle+Math.PI/4.0);
                } else {
                    drawOff("playerBow3", rect.x, rect.y, angle-Math.PI/2.0, -3, -3, angle);
                    drawOff("playerArmRight", rect.x, rect.y, angle, -2, -3, angle+Math.PI/2.0);
                }
                drawOff("playerArmLeft", rect.x, rect.y, angle, -2, 6, angle+Math.PI/2.0);
                Draw.image("playerBody", (int) rect.x, (int) rect.y, angle + 1.5, 1.0);
            } else {
                drawOff("playerBow0", rect.x, rect.y, angle, 5, 3, angle);
                drawOff("playerArmLeft", rect.x, rect.y, angle, 5, 1, angle);
                drawOff("playerArmRight", rect.x, rect.y, angle, -5, 1, angle);
                Draw.image("playerBody", (int) rect.x, (int) rect.y, angle + ((walkCycle - 3) / 10.0), 1.0);
            }
            Draw.image("playerHead", (int) rect.x, (int) rect.y, angle, 1.0);
        } else {
            drawOff("playerShield", rect.x, rect.y, angle, 5, 3, angle);
            drawOff("playerArmLeft", rect.x, rect.y, angle, 5, 1, angle);
            drawOff("playerSword0", rect.x, rect.y, angle, -5, 3, angle);
            drawOff("playerArmRight", rect.x, rect.y, angle, -5, 1, angle);
            Draw.image("playerBody", (int) rect.x, (int) rect.y, angle + ((walkCycle - 3) / 10.0), 1.0);
            Draw.image("playerHead", (int) rect.x, (int) rect.y, angle, 1.0);
        }

        // health bar
        Draw.setColor(new Color(36, 60, 112, 200));
        Draw.rect((int) rect.x, (int) rect.y + 12, 16, 2);

        int size = (int) Utils.mapRange(hp, 0, maxHp, 0, 16);
        Draw.setColor(new Color(43, 104, 237, 200));
        Draw.rect((int) rect.x - (8 - size / 2), (int) rect.y + 12, size, 2);

        // line
        if(weapon == Weapons.BOW && recordingAttack) {
            Draw.setColor(new Color(230, 237, 21, 100));
            Draw.line(lastMousePos, Input.mousePos);
        }

        // hit box
        if (Utils.debugMode) {
            Draw.setColor(Color.WHITE);
            Draw.rectOutline(rect);
        }

    }

    // draw with an angled offset
    static void drawOff(String name, double x, double y, double offAng, int offX, int offY, double angle) {
        double sideX = Math.cos(offAng - Math.PI / 2.0) * offX;
        double sideY = Math.sin(offAng - Math.PI / 2.0) * offX;
        double frontX = Math.cos(offAng) * offY;
        double frontY = Math.sin(offAng) * offY;
        Draw.image(name, (int) (x + sideX + frontX), (int) (y + sideY + frontY), angle, 1.0);
    }

}
