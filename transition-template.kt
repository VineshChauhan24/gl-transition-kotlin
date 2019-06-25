package {{packageName}}

class {{className}}: Transition("{{name}}", SOURCE, {{duration}}) {

    {{{fields}}}


    override fun onUpdateUniforms() {
        super.onUpdateUniforms()
        
        {{{initFields}}}
    }

    companion object {
        // language=glsl
        const val SOURCE = """
{{{source}}}
        """
    }
}


