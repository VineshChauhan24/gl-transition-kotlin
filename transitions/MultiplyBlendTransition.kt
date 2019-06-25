package com.seanghay.finalmovies.gles.transition

class MultiplyBlendTransition: Transition("multiply-blend", SOURCE, 1000L) {

    


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: Fernando Kuteken
// License: MIT

vec4 blend(vec4 a, vec4 b) {
  return a * b;
}

vec4 transition (vec2 uv) {
  
  vec4 blended = blend(getFromColor(uv), getToColor(uv));
  
  if (progress < 0.5)
    return mix(getFromColor(uv), blended, 2.0 * progress);
  else
    return mix(blended, getToColor(uv), 2.0 * progress - 1.0);
}


        """
    }
}


