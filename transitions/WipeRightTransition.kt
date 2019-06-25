package com.seanghay.finalmovies.gles.transition

class WipeRightTransition: Transition("wipe-right", SOURCE, 1000L) {

    


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: Jake Nelson
// License: MIT

vec4 transition(vec2 uv) {
  vec2 p=uv.xy/vec2(1.0).xy;
  vec4 a=getFromColor(p);
  vec4 b=getToColor(p);
  return mix(a, b, step(0.0+p.x,progress));
}

        """
    }
}


