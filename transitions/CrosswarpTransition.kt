package com.seanghay.finalmovies.gles.transition

class CrosswarpTransition: Transition("crosswarp", SOURCE, 1000L) {

    


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: Eke Péter <peterekepeter@gmail.com>
// License: MIT
vec4 transition(vec2 p) {
  float x = progress;
  x=smoothstep(.0,1.0,(x*2.0+p.x-1.0));
  return mix(getFromColor((p-.5)*(1.-x)+.5), getToColor((p-.5)*x+.5), x);
}

        """
    }
}


