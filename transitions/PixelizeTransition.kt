package com.seanghay.finalmovies.gles.transition

class PixelizeTransition: Transition("pixelize", SOURCE, 1000L) {

    open var squaresMin: undefined = undefined(20f, 20f)
open var squaresMinUniform = Uniform<Any>("squaresMin").autoInit()
open var steps: Int = 50
open var stepsUniform = uniform1i("steps").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        squaresMinUniform.setValue(squaresMin)
stepsUniform.setValue(steps)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: gre
// License: MIT
// forked from https://gist.github.com/benraziel/c528607361d90a072e98

uniform ivec2 squaresMin/* = ivec2(20) */; // minimum number of squares (when the effect is at its higher level)
uniform int steps /* = 50 */; // zero disable the stepping

float d = min(progress, 1.0 - progress);
float dist = steps>0 ? ceil(d * float(steps)) / float(steps) : d;
vec2 squareSize = 2.0 * dist / vec2(squaresMin);

vec4 transition(vec2 uv) {
  vec2 p = dist>0.0 ? (floor(uv / squareSize) + 0.5) * squareSize : uv;
  return mix(getFromColor(p), getToColor(p), progress);
}

        """
    }
}


