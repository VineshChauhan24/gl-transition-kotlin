package com.seanghay.finalmovies.gles.transition

class RotateScaleFadeTransition: Transition("rotate-scale-fade", SOURCE, 1000L) {

    open var center: Vector2f = Vector2f(0.5f, 0.5f)
open var centerUniform = uniform2f("center").autoInit()
open var rotations: Float = 1f
open var rotationsUniform = uniform1f("rotations").autoInit()
open var scale: Float = 8f
open var scaleUniform = uniform1f("scale").autoInit()
open var backColor: Vector4f = Vector4f(0.15f, 0.15f, 0.15f, 1f)
open var backColorUniform = uniform4f("backColor").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        centerUniform.setValue(center)
rotationsUniform.setValue(rotations)
scaleUniform.setValue(scale)
backColorUniform.setValue(backColor)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: Fernando Kuteken
// License: MIT

#define PI 3.14159265359

uniform vec2 center; // = vec2(0.5, 0.5);
uniform float rotations; // = 1;
uniform float scale; // = 8;
uniform vec4 backColor; // = vec4(0.15, 0.15, 0.15, 1.0);

vec4 transition (vec2 uv) {
  
  vec2 difference = uv - center;
  vec2 dir = normalize(difference);
  float dist = length(difference);
  
  float angle = 2.0 * PI * rotations * progress;
  
  float c = cos(angle);
  float s = sin(angle);
  
  float currentScale = mix(scale, 1.0, 2.0 * abs(progress - 0.5));
  
  vec2 rotatedDir = vec2(dir.x  * c - dir.y * s, dir.x * s + dir.y * c);
  vec2 rotatedUv = center + rotatedDir * dist / currentScale;
  
  if (rotatedUv.x < 0.0 || rotatedUv.x > 1.0 ||
      rotatedUv.y < 0.0 || rotatedUv.y > 1.0)
    return backColor;
    
  return mix(getFromColor(rotatedUv), getToColor(rotatedUv), progress);
}

        """
    }
}


