package com.seanghay.finalmovies.gles.transition

class KaleidoscopeTransition: Transition("kaleidoscope", SOURCE, 1000L) {

    open var speed: Float = 1f
open var speedUniform = uniform1f("speed").autoInit()
open var angle: Float = 1f
open var angleUniform = uniform1f("angle").autoInit()
open var power: Float = 1.5f
open var powerUniform = uniform1f("power").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        speedUniform.setValue(speed)
angleUniform.setValue(angle)
powerUniform.setValue(power)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: nwoeanhinnogaehr
// License: MIT

uniform float speed; // = 1.0;
uniform float angle; // = 1.0;
uniform float power; // = 1.5;

vec4 transition(vec2 uv) {
  vec2 p = uv.xy / vec2(1.0).xy;
  vec2 q = p;
  float t = pow(progress, power)*speed;
  p = p -0.5;
  for (int i = 0; i < 7; i++) {
    p = vec2(sin(t)*p.x + cos(t)*p.y, sin(t)*p.y - cos(t)*p.x);
    t += angle;
    p = abs(mod(p, 2.0) - 1.0);
  }
  abs(mod(p, 1.0));
  return mix(
    mix(getFromColor(q), getToColor(q), progress),
    mix(getFromColor(p), getToColor(p), progress), 1.0 - 2.0*abs(progress - 0.5));
}

        """
    }
}


