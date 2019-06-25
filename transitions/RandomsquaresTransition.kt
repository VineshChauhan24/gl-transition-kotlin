package com.seanghay.finalmovies.gles.transition

class RandomsquaresTransition: Transition("randomsquares", SOURCE, 1000L) {

    open var size: undefined = undefined(10f, 10f)
open var sizeUniform = Uniform<Any>("size").autoInit()
open var smoothness: Float = 0.5f
open var smoothnessUniform = uniform1f("smoothness").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        sizeUniform.setValue(size)
smoothnessUniform.setValue(smoothness)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: gre
// License: MIT

uniform ivec2 size; // = ivec2(10, 10)
uniform float smoothness; // = 0.5
 
float rand (vec2 co) {
  return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

vec4 transition(vec2 p) {
  float r = rand(floor(vec2(size) * p));
  float m = smoothstep(0.0, -smoothness, r - (progress * (1.0 + smoothness)));
  return mix(getFromColor(p), getToColor(p), m);
}

        """
    }
}


