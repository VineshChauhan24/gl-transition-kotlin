package com.seanghay.finalmovies.gles.transition

class RippleTransition: Transition("ripple", SOURCE, 1000L) {

    open var amplitude: Float = 100f
open var amplitudeUniform = uniform1f("amplitude").autoInit()
open var speed: Float = 50f
open var speedUniform = uniform1f("speed").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        amplitudeUniform.setValue(amplitude)
speedUniform.setValue(speed)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: gre
// License: MIT
uniform float amplitude; // = 100.0
uniform float speed; // = 50.0

vec4 transition (vec2 uv) {
  vec2 dir = uv - vec2(.5);
  float dist = length(dir);
  vec2 offset = dir * (sin(progress * dist * amplitude - progress * speed) + .5) / 30.;
  return mix(
    getFromColor(uv + offset),
    getToColor(uv),
    smoothstep(0.2, 1.0, progress)
  );
}

        """
    }
}


