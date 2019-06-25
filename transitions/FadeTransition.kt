package com.seanghay.finalmovies.gles.transition

class FadeTransition: Transition("fade", SOURCE, 1000L) {

    


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// author: gre
// license: MIT

vec4 transition (vec2 uv) {
  return mix(
    getFromColor(uv),
    getToColor(uv),
    progress
  );
}

        """
    }
}


