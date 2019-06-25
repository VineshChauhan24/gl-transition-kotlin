package com.seanghay.finalmovies.gles.transition

class SquareswireTransition: Transition("squareswire", SOURCE, 1000L) {

    open var squares: undefined = undefined(10f, 10f)
open var squaresUniform = Uniform<Any>("squares").autoInit()
open var direction: Vector2f = Vector2f(1f, -0.5f)
open var directionUniform = uniform2f("direction").autoInit()
open var smoothness: Float = 1.6f
open var smoothnessUniform = uniform1f("smoothness").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        squaresUniform.setValue(squares)
directionUniform.setValue(direction)
smoothnessUniform.setValue(smoothness)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: gre
// License: MIT
 
uniform ivec2 squares;// = ivec2(10,10)
uniform vec2 direction;// = vec2(1.0, -0.5)
uniform float smoothness; // = 1.6

const vec2 center = vec2(0.5, 0.5);
vec4 transition (vec2 p) {
  vec2 v = normalize(direction);
  v /= abs(v.x)+abs(v.y);
  float d = v.x * center.x + v.y * center.y;
  float offset = smoothness;
  float pr = smoothstep(-offset, 0.0, v.x * p.x + v.y * p.y - (d-0.5+progress*(1.+offset)));
  vec2 squarep = fract(p*vec2(squares));
  vec2 squaremin = vec2(pr/2.0);
  vec2 squaremax = vec2(1.0 - pr/2.0);
  float a = (1.0 - step(progress, 0.0)) * step(squaremin.x, squarep.x) * step(squaremin.y, squarep.y) * step(squarep.x, squaremax.x) * step(squarep.y, squaremax.y);
  return mix(getFromColor(p), getToColor(p), a);
}

        """
    }
}


