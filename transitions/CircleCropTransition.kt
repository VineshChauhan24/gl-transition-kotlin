package com.seanghay.finalmovies.gles.transition

class CircleCropTransition: Transition("circle-crop", SOURCE, 1000L) {

    open var bgcolor: Vector4f = Vector4f(0f, 0f, 0f, 1f)
open var bgcolorUniform = uniform4f("bgcolor").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        bgcolorUniform.setValue(bgcolor)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// License: MIT
// Author: fkuteken
// ported by gre from https://gist.github.com/fkuteken/f63e3009c1143950dee9063c3b83fb88

uniform vec4 bgcolor; // = vec4(0.0, 0.0, 0.0, 1.0)

vec2 ratio2 = vec2(1.0, 1.0 / ratio);
float s = pow(2.0 * abs(progress - 0.5), 3.0);

vec4 transition(vec2 p) {
  float dist = length((vec2(p) - 0.5) * ratio2);
  return mix(
    progress < 0.5 ? getFromColor(p) : getToColor(p), // branching is ok here as we statically depend on progress uniform (branching won't change over pixels)
    bgcolor,
    step(s, dist)
  );
}

        """
    }
}


