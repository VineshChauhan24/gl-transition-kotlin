package com.seanghay.finalmovies.gles.transition

class LinearBlurTransition: Transition("linear-blur", SOURCE, 1000L) {

    open var intensity: Float = 0.1f
open var intensityUniform = uniform1f("intensity").autoInit()


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        intensityUniform.setValue(intensity)
    }

    companion object {
        // language=glsl
        const val SOURCE = """
// author: gre
// license: MIT
uniform float intensity; // = 0.1
const int passes = 6;

vec4 transition(vec2 uv) {
    vec4 c1 = vec4(0.0);
    vec4 c2 = vec4(0.0);

    float disp = intensity*(0.5-distance(0.5, progress));
    for (int xi=0; xi<passes; xi++)
    {
        float x = float(xi) / float(passes) - 0.5;
        for (int yi=0; yi<passes; yi++)
        {
            float y = float(yi) / float(passes) - 0.5;
            vec2 v = vec2(x,y);
            float d = disp;
            c1 += getFromColor( uv + d*v);
            c2 += getToColor( uv + d*v);
        }
    }
    c1 /= float(passes*passes);
    c2 /= float(passes*passes);
    return mix(c1, c2, progress);
}

        """
    }
}


