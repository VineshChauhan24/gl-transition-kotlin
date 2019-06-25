package com.seanghay.finalmovies.gles.transition

class WindowblindsTransition: Transition("windowblinds", SOURCE, 1000L) {

    


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: Fabien Benetou
// License: MIT

vec4 transition (vec2 uv) {
  float t = progress;
  
  if (mod(floor(uv.y*100.*progress),2.)==0.)
    t*=2.-.5;
  
  return mix(
    getFromColor(uv),
    getToColor(uv),
    mix(t, progress, smoothstep(0.8, 1.0, progress))
  );
}

        """
    }
}


