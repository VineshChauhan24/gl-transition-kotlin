package com.seanghay.finalmovies.gles.transition

class DisplacementTransition: Transition("displacement", SOURCE, 1000L) {

    open var displacementMap: undefined = undefined(null)
open var displacementMapUniform = Uniform<Any>("displacementMap").autoInit()
open var strength: Float = 0.5f
open var strengthUniform = uniform1f("strength").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        displacementMapUniform.setValue(displacementMap)
strengthUniform.setValue(strength)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: Travis Fischer
// License: MIT
//
// Adapted from a Codrops article by Robin Delaporte
// https://tympanus.net/Development/DistortionHoverEffect

uniform sampler2D displacementMap;

uniform float strength; // = 0.5

vec4 transition (vec2 uv) {
  float displacement = texture2D(displacementMap, uv).r * strength;

  vec2 uvFrom = vec2(uv.x + progress * displacement, uv.y);
  vec2 uvTo = vec2(uv.x - (1.0 - progress) * displacement, uv.y);

  return mix(
    getFromColor(uvFrom),
    getToColor(uvTo),
    progress
  );
}

        """
    }
}


