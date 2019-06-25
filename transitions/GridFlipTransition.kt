package com.seanghay.finalmovies.gles.transition

class GridFlipTransition: Transition("grid-flip", SOURCE, 1000L) {

    open var size: undefined = undefined(4f, 4f)
open var sizeUniform = Uniform<Any>("size").autoInit()
open var pause: Float = 0.1f
open var pauseUniform = uniform1f("pause").autoInit()
open var dividerWidth: Float = 0.05f
open var dividerWidthUniform = uniform1f("dividerWidth").autoInit()
open var bgcolor: Vector4f = Vector4f(0f, 0f, 0f, 1f)
open var bgcolorUniform = uniform4f("bgcolor").autoInit()
open var randomness: Float = 0.1f
open var randomnessUniform = uniform1f("randomness").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        sizeUniform.setValue(size)
pauseUniform.setValue(pause)
dividerWidthUniform.setValue(dividerWidth)
bgcolorUniform.setValue(bgcolor)
randomnessUniform.setValue(randomness)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// License: MIT
// Author: TimDonselaar
// ported by gre from https://gist.github.com/TimDonselaar/9bcd1c4b5934ba60087bdb55c2ea92e5

uniform ivec2 size; // = ivec2(4)
uniform float pause; // = 0.1
uniform float dividerWidth; // = 0.05
uniform vec4 bgcolor; // = vec4(0.0, 0.0, 0.0, 1.0)
uniform float randomness; // = 0.1
 
float rand (vec2 co) {
  return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

float getDelta(vec2 p) {
  vec2 rectanglePos = floor(vec2(size) * p);
  vec2 rectangleSize = vec2(1.0 / vec2(size).x, 1.0 / vec2(size).y);
  float top = rectangleSize.y * (rectanglePos.y + 1.0);
  float bottom = rectangleSize.y * rectanglePos.y;
  float left = rectangleSize.x * rectanglePos.x;
  float right = rectangleSize.x * (rectanglePos.x + 1.0);
  float minX = min(abs(p.x - left), abs(p.x - right));
  float minY = min(abs(p.y - top), abs(p.y - bottom));
  return min(minX, minY);
}

float getDividerSize() {
  vec2 rectangleSize = vec2(1.0 / vec2(size).x, 1.0 / vec2(size).y);
  return min(rectangleSize.x, rectangleSize.y) * dividerWidth;
}

vec4 transition(vec2 p) {
  if(progress < pause) {
    float currentProg = progress / pause;
    float a = 1.0;
    if(getDelta(p) < getDividerSize()) {
      a = 1.0 - currentProg;
    }
    return mix(bgcolor, getFromColor(p), a);
  }
  else if(progress < 1.0 - pause){
    if(getDelta(p) < getDividerSize()) {
      return bgcolor;
    } else {
      float currentProg = (progress - pause) / (1.0 - pause * 2.0);
      vec2 q = p;
      vec2 rectanglePos = floor(vec2(size) * q);
      
      float r = rand(rectanglePos) - randomness;
      float cp = smoothstep(0.0, 1.0 - r, currentProg);
    
      float rectangleSize = 1.0 / vec2(size).x;
      float delta = rectanglePos.x * rectangleSize;
      float offset = rectangleSize / 2.0 + delta;
      
      p.x = (p.x - offset)/abs(cp - 0.5)*0.5 + offset;
      vec4 a = getFromColor(p);
      vec4 b = getToColor(p);
      
      float s = step(abs(vec2(size).x * (q.x - delta) - 0.5), abs(cp - 0.5));
      return mix(bgcolor, mix(b, a, step(cp, 0.5)), s);
    }
  }
  else {
    float currentProg = (progress - 1.0 + pause) / pause;
    float a = 1.0;
    if(getDelta(p) < getDividerSize()) {
      a = currentProg;
    }
    return mix(bgcolor, getToColor(p), a);
  }
}

        """
    }
}


