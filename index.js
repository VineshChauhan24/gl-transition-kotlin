const transitions = require('gl-transitions');
const hbs = require('handlebars');
const { parseType, readAsString, typePairs, exportToFile, isPrimitive, getUniformType } = require('./utils');
const path = require('path');
const _ = require('lodash');
const exportDir = 'transitions';
const templateFile = 'transition-template.kt';
const packageName = 'com.seanghay.finalmovies.gles.transition';

const templateString = readAsString(templateFile);
const template = hbs.compile(templateString);

function spreads(value) {
    if (Array.isArray(value)) {
        return value.map(x => x + 'f').join(', ');
    } else return value;
}

function transformParams(transition) {
    const { defaultParams, paramsTypes } = transition;
    const paramNames = Object.keys(paramsTypes);
    return paramNames.map(key => {
        const defValue = defaultParams[key];
        const dataType = paramsTypes[key];
        const primitive = isPrimitive(dataType);
        const transformedKey = _.camelCase(key);
        const parsedType = parseType(dataType);

        let value = defValue;

        if (!primitive) {
            value = `${parsedType}(${spreads(value)})`
        }

        return {
            value: value,
            isPrimitive: isPrimitive(dataType),
            type: parseType(dataType),
            name: transformedKey,
            uType: getUniformType(dataType),
            key,
        }
    });
}

function transformParamsToString(params) {

    let suffix = ""
    if (params.isPrimitive && params.type === 'Float') {
        suffix = 'f';
    }
    const uniformName = `${params.name}Uniform`;

    const property = `open var ${params.name}: ${params.type} = ${params.value}${suffix}`;
    const uniform = `open var ${uniformName} = ${params.uType}("${params.key}").autoInit()`

    const setter = `${uniformName}.setValue(${params.name})`

    return {
        property, 
        uniformField: uniform,
        setter,
    }
}


function parseClassName(name) {
    return _.startCase(name).split(' ').join('');
}


function transformTransition(transition) {

    const className = parseClassName(transition.name);  
    const name = _.kebabCase(transition.name);

    const tp = transformParams(transition).map(transformParamsToString);

    const fields = tp.map(t => t.property + '\n' + t.uniformField).join('\n');
    const initFields = tp.map(t => t.setter).join('\n');

    const context = {
        fields,
        initFields,
        packageName,
        className: `${className}Transition`,
        source: transition.glsl,
        name: name,
        duration: '1000L'
    }
    
    
    const result = template(context);

    return {
        content: result,
        name: `${className}Transition`
    };    
}

transitions.forEach(t => {
    const result = transformTransition(t);
    exportToFile(`${exportDir}/${result.name}.kt`, result.content);
})

