const path = require('path');
console.log("webpack.config.js está siendo cargado");
./gradlew wasmJsBrowserRun

module.exports = {
    entry: './build/distributions/composeApp.js',  // Asegúrate de que este sea el archivo correcto
    output: {
        filename: 'composeApp.js',
        path: path.resolve(__dirname, 'dist'),
    },
    experiments: {
        asyncWebAssembly: true,  // Habilita WebAssembly
    },
    devServer: {
        static: {
            directory: path.join(__dirname, 'dist'),
        },
        compress: true,
        port: 8080,
    },
};