package com.seanghay.finalmovies.gles.transition

class LumaTransition: Transition("luma", SOURCE, 1000L) {

    open var luma: undefined = undefined(null)
open var lumaUniform = Uniform<Any>("luma").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        lumaUniform.setValue(luma)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// Author: gre
// License: MIT

uniform sampler2D luma;

vec4 transition(vec2 uv) {
  return mix(
    getToColor(uv),
    getFromColor(uv),
    step(progress, texture2D(luma, uv).r)
  );
}

        """
    }
}


