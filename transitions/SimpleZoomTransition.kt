package com.seanghay.finalmovies.gles.transition

class SimpleZoomTransition: Transition("simple-zoom", SOURCE, 1000L) {

    open var zoomQuickness: Float = 0.8f
open var zoomQuicknessUniform = uniform1f("zoom_quickness").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        zoomQuicknessUniform.setValue(zoomQuickness)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: 0gust1
// License: MIT

uniform float zoom_quickness; // = 0.8
float nQuick = clamp(zoom_quickness,0.2,1.0);

vec2 zoom(vec2 uv, float amount) {
  return 0.5 + ((uv - 0.5) * (1.0-amount));	
}

vec4 transition (vec2 uv) {
  return mix(
    getFromColor(zoom(uv, smoothstep(0.0, nQuick, progress))),
    getToColor(uv),
   smoothstep(nQuick-0.2, 1.0, progress)
  );
}
        """
    }
}


