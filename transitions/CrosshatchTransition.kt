package com.seanghay.finalmovies.gles.transition

class CrosshatchTransition: Transition("crosshatch", SOURCE, 1000L) {

    open var center: Vector2f = Vector2f(0.5f, 0.5f)
open var centerUniform = uniform2f("center").autoInit()
open var threshold: Float = 3f
open var thresholdUniform = uniform1f("threshold").autoInit()
open var fadeEdge: Float = 0.1f
open var fadeEdgeUniform = uniform1f("fadeEdge").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        centerUniform.setValue(center)
thresholdUniform.setValue(threshold)
fadeEdgeUniform.setValue(fadeEdge)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// License: MIT
// Author: pthrasher
// adapted by gre from https://gist.github.com/pthrasher/04fd9a7de4012cbb03f6

uniform vec2 center; // = vec2(0.5)
uniform float threshold; // = 3.0
uniform float fadeEdge; // = 0.1

float rand(vec2 co) {
  return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}
vec4 transition(vec2 p) {
  float dist = distance(center, p) / threshold;
  float r = progress - min(rand(vec2(p.y, 0.0)), rand(vec2(0.0, p.x)));
  return mix(getFromColor(p), getToColor(p), mix(0.0, mix(step(dist, r), 1.0, smoothstep(1.0-fadeEdge, 1.0, progress)), smoothstep(0.0, fadeEdge, progress)));    
}

        """
    }
}


