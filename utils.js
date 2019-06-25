const fs = require('fs');
const path = require('path');

const typePairs = {
    'float': 'Float',
    'int': 'Int',
    'bool': 'Boolean',
    'vec2': 'Vector2f',
    'vec3': 'Vector3f',
    'vec4': 'Vector4f',
    'mat3': 'Matrix3f',
    'mat4': 'Matrix4f'
}

const primitives = [
    'float', 'int', 'bool', 'double', 'long'
];

const uniformTypes = {
    'float': 'uniform1f',
    'int': 'uniform1i',
    'bool': 'uniform1b',
    'vec2': 'uniform2f',
    'vec3': 'uniform3f',
    'vec4': 'uniform4f',
    'mat4': 'uniformMat4f',
    'mat3': 'uniformMat3f'
};

function getUniformType(type) {
    const t = uniformTypes[type];
    if (t) return t;
    else return 'Uniform<Any>'
}

function isPrimitive(type) {
    return primitives.indexOf(type.toLowerCase()) !== -1;
}

function parseType(type) {
    if (typeof type !== 'string') return 'Any'
    const parsedType = typePairs[type.toLowerCase()];
    if (parseType) return parsedType
    else return 'Any'
}


function readAsString(filename) {
    return fs.readFileSync(path.join(__dirname, filename), {
        encoding: 'utf8'
    });    
}


function exportToFile(filename, content) {
    return fs.writeFileSync(path.join(__dirname, filename), content);
}

module.exports = {
    readAsString,
    parseType,
    typePairs,
    exportToFile,
    isPrimitive,
    getUniformType
}